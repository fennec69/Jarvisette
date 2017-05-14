#!/usr/bin/bash

for device in RASP-LIGHT-1 RASP-LIGHT-2 RASP-LIGHT-3; do
    scp -r * ${device}:
    ssh ${device} "sudo pip3 install websockets"
    ssh ${device} "sudo pip3 install gTTS"
    ssh ${device} "sudo apt-get install sox libsox-fmt-all"
done
