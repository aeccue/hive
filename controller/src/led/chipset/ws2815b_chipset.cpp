/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#include "ws2815b_chipset.hpp"

#include "../../microcontroller/microcontroller.h"

using namespace aeccue;

#define DELAY_LATCH 300

void WS2815BChipset::set_led(LED led, uint8_t r, uint8_t g, uint8_t b) {
    led[0] = g;
    led[1] = r;
    led[2] = b;
}

void WS2815BChipset::set_led(LED led, LED source) {
    led[0] = source[0];
    led[1] = source[1];
    led[2] = source[2];
}

// assembly code
extern "C" { void ws2815b_light_strip(const void *led_strip); }

void WS2815BChipset::light_leds(const LED *led,
                                uint16_t length,
                                uint8_t *port,
                                uint8_t pins) {
    struct LEDStrip {
        const LED *led;
        uint16_t length;
        uint8_t *port;
        uint8_t pins;
    };

    LEDStrip strip = {
        .led = led,
        .length = length,
        .port = port,
        .pins = pins
    };

    ws2815b_light_strip((const void *) &strip);
    delayMicroseconds(DELAY_LATCH);
}


extern "C" { void ws2815b_off(const void *led_strip); }

void WS2815BChipset::off(uint16_t length, 
                         uint8_t *port, 
                         uint8_t pins) {
    struct LEDStrip {
        const LED *led;
        uint16_t length;
        uint8_t *port;
        uint8_t pins;
    };

    LEDStrip strip = {
        .led = nullptr,
        .length = length,
        .port = port,
        .pins = pins
    };

    ws2815b_off((const void *) &strip);
    delayMicroseconds(DELAY_LATCH);
}

