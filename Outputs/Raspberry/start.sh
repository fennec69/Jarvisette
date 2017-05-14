#!/usr/bin/env bash

UUID=`hostname`

while true;do
    ./dimlight_service.py --host 192.168.101.144 --uuid ${UUID} --gpio 3
done
