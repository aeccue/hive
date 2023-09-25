/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#include "desk_config.hpp"

#include <Arduino.h>

#define LOGGING_BAUD_RATE 9600

using namespace aeccue;

aeccue::LEDController *aeccue::led_controller;
aeccue::BluetoothReceiver *aeccue::bluetooth_receiver;

static void initialize_logging() {
    Serial.begin(LOGGING_BAUD_RATE);
    delay(100);
}


static void initialize_bluetooth() {
    bluetooth_receiver = new BluetoothReceiver(&Serial1);
}

static void initialize_led_monitor() {
    LEDSetup *setup= new LEDSetup(MONITOR_STRIP_COUNT, MONITOR_SECTION_COUNT);
    setup->add_led_strip(0, 
                         WS2815B,
                         MONITOR_STRIP_0_LENGTH,
                         MONITOR_STRIP_0_PIN_DATA,
                         MONITOR_STRIP_0_PIN_BACKUP);
    setup->add_led_strip(1, 
                         WS2815B,
                         MONITOR_STRIP_1_LENGTH,
                         MONITOR_STRIP_1_PIN_DATA,
                         MONITOR_STRIP_1_PIN_BACKUP);
    setup->add_led_section(SECTION_DELL,
                           DELL_STRIP_INDEX,
                           DELL_LENGTH,
                           DELL_START_INDEX);
    setup->add_led_section(SECTION_STAND,
                           STAND_STRIP_INDEX,
                           STAND_LENGTH,
                           STAND_START_INDEX);
    setup->add_led_section(SECTION_TEST,
                           TEST_STRIP_INDEX,
                           TEST_LENGTH,
                           TEST_START_INDEX);

    led_controller->add_led_setup(MONITOR, setup);
}

static void initialize_led_controller() {
    led_controller = new LEDController(LED_SETUP_COUNT);
    initialize_led_monitor();
}

void aeccue::initialize(bool log_enabled) {
    if (log_enabled) {
        initialize_logging();
    }

    initialize_bluetooth();
    initialize_led_controller();
}

