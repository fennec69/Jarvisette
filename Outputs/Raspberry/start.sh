#!/usr/bin/env bash

UUID=`hostname`

./main.py --host 192.168.101.144 --uuid ${UUID} --gpio 3
