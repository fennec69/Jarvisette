#!/usr/bin/env python3

import argparse
import RPi.GPIO as GPIO

from service import Service


GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM) 

parser = argparse.ArgumentParser(description='Raspberry Light service')
parser.add_argument('--host', type=str, default="127.0.0.1", help='Server Host')
parser.add_argument('--port', type=int, default=8080, help='Server Port')
parser.add_argument('--gpio', type=int, default=2, help='Server Port')


class LightService(Service):
    def __init__(self, gpio, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.gpio = gpio
        GPIO.setup(self.gpio, GPIO.OUT, initial=GPIO.LOW) 

    @property
    def supported_services(self):
        return ["light"]

    def light_cmd(self, power):
        if power:
            GPIO.output(self.gpio, True) 
        else:
            GPIO.output(self.gpio, False) 


if __name__ == "__main__":
    args = parser.parse_args()
    service = LightService(gpio=args.gpio, host=args.host, port=args.port)
    service.run_forever()
