/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#include "led_strip.hpp"

#include <Arduino.h>
#include <stdlib.h>

#include "chipset/led_chipset.hpp"
#include "../microcontroller/microcontroller.h"

#define TAG "LED Strip"

using namespace aeccue;

LEDStrip::LEDStrip(LEDChip chip,
                   uint16_t length,
                   uint8_t pin_data,
                   uint8_t pin_backup)
    : length(length) {

    leds = new LED[length];
    buffer = new LED[length];

    chipset = getChipset(chip);
    if (chipset == nullptr) {
        delete[] leds;
        delete[] buffer;
        return;
    }


    const MicrocontrollerPortPin port_pin_data = get_port_pin(pin_data);
    const MicrocontrollerPortPin port_pin_backup = get_port_pin(pin_backup);

    if (port_pin_data.port != port_pin_backup.port) {
        delete[] leds;
        delete[] buffer;
        delete chipset;
        return;
    }

    port = port_pin_data.port;
    pins = (1 << port_pin_data.pin) | (1 << port_pin_backup.pin);

    pinMode(pin_data, OUTPUT);
    pinMode(pin_backup, OUTPUT);


    reset();

    initialized = true;
}

LEDStrip::~LEDStrip() {
    delete[] leds;
    delete[] buffer;
    delete chipset;
}

void LEDStrip::reset() {
    set_all(0, 0, 0);
}

void LEDStrip::off() {
    chipset->off(length, port, pins);
}

void LEDStrip::off(uint16_t start, uint16_t end) {
    for (uint16_t i = start; i < end; i++) {
        chipset->set_led(buffer[i], 0, 0, 0);
    }

    chipset->light_leds(buffer, length, port, pins);
}

void LEDStrip::set(uint16_t start, uint16_t end, uint8_t r, uint8_t g, uint8_t b) {
    if (end > length) return;

    LOG(TAG, "Setting LED[" + String(start) + "-" + String(end) + "] to (" + String(r) + ", " + String(g) + ", " + String(b) + ")");

    for (uint16_t i = start; i < end; i++) {
        chipset->set_led(leds[i], r, g, b);
    }
}

void LEDStrip::set(uint16_t index, uint8_t r, uint8_t g, uint8_t b) {
    if (index >= length) return;
    
    chipset->set_led(leds[index], r, g, b);
}

void LEDStrip::set_all(uint8_t r, uint8_t g, uint8_t b) {
    for (uint16_t i = 0; i < length; i++) {
        chipset->set_led(leds[i], r, g, b);
    }
}

void LEDStrip::light() {
    for (uint16_t i = 0; i < length; i++) {
        chipset->set_led(buffer[i], 0, 0, 0);
    }

    chipset->light_leds(buffer, length, port, pins);
}

void LEDStrip::light(uint16_t start, uint16_t end) {
    if (end > length) return;

    for (uint16_t i = start; i < end; i++) {
        chipset->set_led(buffer[i], leds[i]);
    }

    chipset->light_leds(buffer, length, port, pins);
}

