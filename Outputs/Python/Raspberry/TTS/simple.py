import subprocess
from service import Service
from gtts import gTTS
from tempfile import NamedTemporaryFile


class TTSService(Service):
    def __init__(self, lang, *args, **kwargs):
        self.lang = lang

    @property
    def supported_services(self):
        return ["tts"]

    def tts_cmd(self, message):
        tts = gTTS(text=message, lang=self.lang)
        tts.save("./toto.mp3")
        cmd = ['play', '-t', 'mp3', "./toto.mp3"]
        subprocess.check_call(cmd, stdout=subprocess.DEVNULL, stderr=subprocess.DEVNULL)
