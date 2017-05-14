from service import Service


class TTSService(Service):
    @property
    def supported_services(self):
        return ["tts"]

    def tts_cmd(self, message):
        print(message)
