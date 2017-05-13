#!/usr/bin/env python3

import asyncio
import json
import websockets
from abc import ABCMeta, abstractmethod


class Service(metaclass=ABCMeta):
    def __init__(self, host, port):
        self.host = host
        self.port = port
        self.uuid = "TODO"

    async def send(self, websocket, msg):
        await websocket.send(json.dumps(msg))

    async def register(self, websocket):
        await self.send(websocket, {"services": self.supported_services})

    async def send_result(self, websocket, exit_code, msg):
        await self.send(websocket, {"exit_code": exit_code, "msg": msg})

    async def get_message(self, websocket):
        data = await websocket.recv()
        return json.loads(data)

    async def run(self):
        url = "ws://{host}:{port}/output/{uuid}".format(host=self.host,
                                                        port=self.port,
                                                        uuid=self.uuid)
        async with websockets.connect(url) as websocket:
            await self.register(websocket)
            #if True:
            while True:
                try:
                    order = await self.get_message(websocket)
                    action = order["action"]
                    del(order["action"])
                    func = getattr(self, "%s_cmd" % action)
                    func(**order)
                    await self.send_result(websocket, "ok", "%s success" % action)
                except Exception as err:
                    print(err)
                    await self.send_result(websocket, "error", "%s - %s" % (type(err), str(err)))

    def run_forever(self):
        asyncio.get_event_loop().run_until_complete(self.run())

    @property
    @abstractmethod
    def supported_services(self):
        pass
