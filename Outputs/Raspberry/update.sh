#!/usr/bin/bash

for device in RASP-LIGHT-1 RASP-LIGHT-2 RASP-LIGHT-3; do
    scp -r * ${device}:
    #echo ${device}
done
