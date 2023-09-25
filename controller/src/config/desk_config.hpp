/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef DESK_CONFIG_HPP
#define DESK_CONFIG_HPP

#include "../led/led_controller.hpp"
#include "../bluetooth/bluetooth_receiver.hpp"

#define AVR_ATMEGA2560

namespace aeccue {

    #define LED_SETUP_COUNT 2

    //-- MONITOR --//
    #define MONITOR 1
    #define MONITOR_STRIP_COUNT 2
    #define MONITOR_SECTION_COUNT 3

    #define MONITOR_STRIP_0_LENGTH 45 
    #define MONITOR_STRIP_0_PIN_DATA 49
    #define MONITOR_STRIP_0_PIN_BACKUP 48 
    #define MONITOR_STRIP_1_LENGTH 54
    #define MONITOR_STRIP_1_PIN_DATA 30 
    #define MONITOR_STRIP_1_PIN_BACKUP 31 

    // MONITOR: DELL
    #define SECTION_DELL 0
    #define DELL_STRIP_INDEX 0
    #define DELL_LENGTH 45 
    #define DELL_START_INDEX 0

    // MONITOR: STAND
    #define SECTION_STAND 1
    #define STAND_STRIP_INDEX 1
    #define STAND_LENGTH 27 
    #define STAND_START_INDEX 0

    // MONITOR: TEST
    #define SECTION_TEST 2
    #define TEST_STRIP_INDEX 1
    #define TEST_LENGTH 27 
    #define TEST_START_INDEX 27

    extern LEDController *led_controller;
    extern BluetoothReceiver *bluetooth_receiver;

    void initialize(bool log_enabled);
}

#endif // DESK_CONFIG_HPP

