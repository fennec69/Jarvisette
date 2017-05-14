#import RPi.GPIO as GPIO
from service import Service


#GPIO.setwarnings(False)
#GPIO.setmode(GPIO.BCM) 


class DimLightService(Service):
    def __init__(self, gpio, *args, **kwargs):
        super().__init__(*args, **kwargs)
        #GPIO.setup(gpio, GPIO.OUT, initial=GPIO.LOW) 
        #self.pwm = GPIO.PWM(gpio, 100)  # frequency=100Hz
        #self.pwm.start(0)

    @property
    def supported_services(self):
        return ["light", "dimlight"]

    def light_cmd(self, power):
        pass
        #if power:
        #    self.pwm.ChangeDutyCycle(100)
        #else:
        #    self.pwm.ChangeDutyCycle(0)

    def dimlight_cmd(self, percent):
        pass
        #self.pwm.ChangeDutyCycle(percent)
