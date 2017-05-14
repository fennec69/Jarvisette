import subprocess
import random
import time
from service import Service


class MusicService(Service):
    @property
    def supported_services(self):
        return ["music"]

    def music_cmd(self):
        path = random.choices(["./music1.mp3", "./music2.mp3"])[0]

        cmd = ['play', '-t', 'mp3', path]
        p = subprocess.Popen(cmd)
        time.sleep(10)
        p.terminate()
