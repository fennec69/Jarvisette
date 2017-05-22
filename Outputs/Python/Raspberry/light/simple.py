import RPi.GPIO as GPIO

from service import Service


GPIO.setwarnings(False)
GPIO.setmode(GPIO.BCM) 


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
