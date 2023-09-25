/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#include "led_controller.hpp"

#include "../microcontroller/microcontroller.h"

#define TAG "LED Controller"

using namespace aeccue;

LEDController::LEDController(int8_t setup_count) : count(setup_count) {
    led_setups = new LEDSetup *[count];
    for (LEDSetupId i = 0; i < count; i++) {
        led_setups[i] = nullptr;
    }
    
    LOG(TAG, "Created");
}

LEDController::~LEDController() {
    for (LEDSetupId i = 0; i < count; i++) {
        delete led_setups[i];
    }
    delete[] led_setups;
}

bool LEDController::setup_exists(LEDSetupId id) {
    return id >= 0 && id < count && led_setups[id] != nullptr;
}

void LEDController::add_led_setup(LEDSetupId id, LEDSetup *setup) {
    if (id < 0 || id >= count) return;

    led_setups[id] = setup;
}
            
void LEDController::off(LEDSetupId setup_id, LEDSectionId section_id) {
    if (setup_id == LED_ID_UNSPECIFIED) {
        for (LEDSetupId i = 0; i < count; i++) {
            if (setup_exists(i)) {
                led_setups[i]->off(section_id);
            }
        }
    } else if (setup_exists(setup_id)){
        led_setups[setup_id]->off(section_id);
    }
}

void LEDController::light(LEDSetupId setup_id, LEDSectionId section_id) {
    if (setup_id == LED_ID_UNSPECIFIED) {
        for (LEDSetupId i = 0; i < count; i++) {
            if (setup_exists(i)) {
                led_setups[i]->light(section_id);
            }
        }
    } else if (setup_exists(setup_id)){
        led_setups[setup_id]->light(section_id);
    }
}

void LEDController::set_led(LEDSetupId setup_id,
                            LEDSectionId section_id,
                            uint16_t index,
                            uint8_t r,
                            uint8_t g,
                            uint8_t b) {
    if (setup_exists(setup_id)){
        led_setups[setup_id]->set_led(section_id, index, r, g, b);
    }
}

void LEDController::set_all(LEDSetupId setup_id,
                            LEDSectionId section_id,
                            uint8_t r,
                            uint8_t g,
                            uint8_t b) {
    if (setup_id == LED_ID_UNSPECIFIED) {
        for (LEDSetupId i = 0; i < count; i++) {
            if (setup_exists(i)) {
                led_setups[i]->set_all(section_id, r, g, b);
            }
        }
    } else if (setup_exists(setup_id)){
        led_setups[setup_id]->set_all(section_id, r, g, b);
    }
}

// TODO: use interrupts to time out serial
void LEDController::parse(HardwareSerial *serial) {
    while (!serial->available());
    LEDSetupId setup_id = serial->read();
    LOG(TAG, "LED Setup ID: " + String(setup_id));

    while(!serial->available());
    LEDSectionId section_id = serial->read();
    LOG(TAG, "LED Section ID: " + String(section_id));
    
    while(!serial->available());
    LEDAction action = serial->read();

    send_ack(serial);

    switch (action) {
        case ACTION_OFF:
            LOG(TAG, "Action: OFF");
            off(setup_id, section_id);
            break;
        case ACTION_ON:
            LOG(TAG, "Action: ON");
            light(setup_id, section_id);
            break;
        case ACTION_SET:
            LOG(TAG, "Action: SET");
            set_led(setup_id, section_id, serial);
            break;
        case ACTION_SET_ALL:
            LOG(TAG, "Action: SET ALL");
            set_all(setup_id, section_id, serial);
            break;
    }

    send_ack(serial);
}

void LEDController::set_led(LEDSetupId setup_id, LEDSectionId section_id, HardwareSerial *serial) {
    while(!serial->available());
    uint16_t length = read_uint16_t(serial);
    LOG(TAG, "Length to set: " + String(length));

    while(!serial->available());
    uint16_t index = read_uint16_t(serial);
    LOG(TAG, "Starting index: " + String(index));

    send_ack(serial);

    while(index < length) {
        while(!serial->available());
        uint8_t r = serial->read();
        while(!serial->available());
        uint8_t g = serial->read();
        while(!serial->available());
        uint8_t b = serial->read();

        set_led(setup_id, section_id, index, r, g, b);
        index++;

        send_ack(serial);
    }
    
    light(setup_id, section_id);
}

void LEDController::set_all(LEDSetupId setup_id, LEDSectionId section_id, HardwareSerial *serial) {
    while(!serial->available());
    uint8_t r = serial->read();
    while(!serial->available());
    uint8_t g = serial->read();
    while(!serial->available());
    uint8_t b = serial->read();

    set_all(setup_id, section_id, r, g, b);
    light(setup_id, section_id);
}

void LEDController::send_ack(HardwareSerial *serial) {
    serial->write(255);
}

