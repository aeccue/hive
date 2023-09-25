/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#define CONFIGURATION_DESK

#include "config.h"
#include "src/led/led_strip.hpp"
#include "src/microcontroller/microcontroller.h"

using namespace aeccue;

void setup() {
    #ifdef LOG_ENABLED
        initialize(true);
    #else
        initialize(false);
    #endif // LOG_ENABLED
}

void loop() {
    bluetooth_receiver->receive();
}
