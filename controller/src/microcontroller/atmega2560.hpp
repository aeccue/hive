/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef ATMEGA2560_H
#define ATMEGA2560_H

#include <Arduino.h>
#include <HardwareSerial.h>

#include "microcontroller_base.h"

const MicrocontrollerPortPin get_port_pin(uint8_t mapped_pin);

uint16_t read_uint16_t(HardwareSerial *serial);

#ifdef LOG_ENABLED

#define LOG(TAG, MSG) \
    do { \
        Serial.println(String("[") + TAG + "] " + MSG); \
    } while (0)

#else

#define LOG(TAG, MSG)

#endif // LOG_ENABLED

#endif // ATMEGA2560_H

