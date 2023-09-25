# Hive

This repository contains code that will run on a microcontroller to control an individually addressable LED strip. At the moment only the MEGA2560 microcontroller and WS2812b LED strip are supported. 

The microcontroller can be controlled via commands sent to it via Bluetooth. At the moment, the only bluetooth module tested is the HC-05 Bluetooth module.

The controller is an Android app that communicates directly with the Bluetooth module and is able to send commands for color patterns, light intensity and `lighting patterns.
