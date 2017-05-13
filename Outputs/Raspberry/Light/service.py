#!/usr/bin/env python3

import json
import socket
from abc import ABCMeta, abstractmethod


class Service(metaclass=ABCMeta):
    def __init__(self, ip, port):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.ip = ip
        self.port = port

    def run_forever(self):
        try:
            self.sock.connect((self.ip, self.port))
        except socket.error:
            print("Can't connect to %s:%d" % (self.ip, self.port))
            return
        self.sockf = self.sock.makefile("rw")
        self.publish_services()
        for data in self.sockf:
            order = json.loads(data)
            action = order.get("action")
            if action is None:
                self.send_result("error", "action key is needed")
                continue
            del(order["action"])
            try:
                func = getattr(self, "%s_cmd" % action)
            except AttributeError:
                self.send_result("error", "%s isn't supported" % action)
                continue
            try:
                func(**order)
            except TypeError as err:
                self.send_result("error", str(err))
                continue
            self.send_result("ok", "%s success" % action)

    def send(self, msg):
        self.sockf.write(json.dumps(msg))
        self.sockf.flush()

    def send_result(self, exit_code, msg):
        self.send({"exit_code": exit_code, "msg": msg})

    @property
    @abstractmethod
    def supported_services(self):
        pass

    def publish_services(self):
        self.send({"services": self.supported_services})
