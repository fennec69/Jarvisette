#!/usr/bin/bash

for device in W-RASP-LIGHT-1 W-RASP-LIGHT-2 W-RASP-LIGHT-3; do
    scp -r * ${device}:
    ssh ${device} "sudo pip3 install websockets"
    ssh ${device} "sudo pip3 install gTTS"
    ssh ${device} "sudo apt-get install sox libsox-fmt-all"
    ssh ${device} sync
    ssh ${device} sudo systemctl restart jarvisette
done
