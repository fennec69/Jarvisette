#!/usr/bin/env python3

import asyncio
import json
import websockets
from abc import ABCMeta, abstractmethod


class Service(metaclass=ABCMeta):
    @property
    @abstractmethod
    def supported_services(self):
        pass


class ServiceManager:
    def __init__(self, host, port, uuid):
        self.host = host
        self.port = port
        self.uuid = uuid
        self.services = {}

    def add_service(self, servive_handler):
        for service in servive_handler.supported_services:
            self.services[service] = servive_handler

    @staticmethod
    async def send(websocket, msg):
        await websocket.send(json.dumps(msg))

    async def register(self, websocket):
        await self.send(websocket, {"services": list(self.services.keys())})

    @staticmethod
    async def send_result(websocket, exit_code, msg):
        await self.send(websocket, {"exit_code": exit_code, "msg": msg})

    @staticmethod
    async def get_message(websocket):
        data = await websocket.recv()
        return json.loads(data)

    async def run(self):
        url = "ws://{host}:{port}/output/{uuid}".format(host=self.host,
                                                        port=self.port,
                                                        uuid=self.uuid)
        while True:
            try:
                async with websockets.connect(url) as websocket:
                    await self.register(websocket)
                    while True:
                        try:
                            order = await self.get_message(websocket)
                            action = order["action"]
                            del(order["action"])
                            handler = self.services[action]
                            func = getattr(handler, "%s_cmd" % action)
                            func(**order)
                            await self.send_result(websocket, "ok", "%s success" % action)
                        except Exception as err:
                            print(err)
                            await self.send_result(websocket, "error", "%s - %s" % (type(err), str(err)))
            except websockets.exceptions.ConnectionClosed:
                pass

    def run_forever(self):
        asyncio.get_event_loop().run_until_complete(self.run())
