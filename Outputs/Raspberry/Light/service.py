#!/usr/bin/env python3

import argparse
import json
import socket
from abc import ABCMeta, abstractmethod

parser = argparse.ArgumentParser(description='Raspberry Light service')
parser.add_argument('--ip', type=str, default="127.0.0.1", help='Server IP')
parser.add_argument('--port', type=int, default=8080, help='Server Port')
parser.add_argument('--gpio', type=int, default=2, help='Server Port')


class Service(metaclass=ABCMeta):
    def __init__(self, ip, port):
        self.sock = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
        self.ip = ip
        self.port = port

    def run_forever(self):
        try:
            self.sock.connect((self.ip, self.port))
        except ConnectionRefusedError:
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
            func(**order)
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


class LightService(Service):
    def __init__(self, gpio, *args, **kwargs):
        super().__init__(*args, **kwargs)

    @property
    def supported_services(self):
        return ["light"]

    def light_cmd(self, power):
        print("LIGHT_CMD :%s" %  power)


if __name__ == "__main__":
    args = parser.parse_args()
    service = LightService(gpio=args.gpio, ip=args.ip, port=args.port)
    service.run_forever()
