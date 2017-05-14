#!/usr/bin/env python3

import argparse
from service import ServiceManager
from light.dim import DimLightService
from TTS.simple import TTSService

parser = argparse.ArgumentParser(description='Raspberry Dimmable Light service')
parser.add_argument('--uuid', type=str, help='Uniq ID of the service', required=True)
parser.add_argument('--host', type=str, default="127.0.0.1", help='Server Host')
parser.add_argument('--port', type=int, default=8080, help='Server Port')
parser.add_argument('--gpio', type=int, default=2, help='Server Port')


if __name__ == "__main__":
    from service import ServiceManager
    args = parser.parse_args()
    manager = ServiceManager(host=args.host, port=args.port, uuid=args.uuid)
    manager.add_service(DimLightService(gpio=args.gpio))
    manager.add_service(TTSService())
    manager.run_forever()
