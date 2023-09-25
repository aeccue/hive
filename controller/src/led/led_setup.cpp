/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#include "led_setup.hpp"

#include "../microcontroller/microcontroller.h"

#define TAG "LED Setup"

using namespace aeccue;

LEDSetup::LEDSetup(uint8_t strip_count, 
                   int8_t section_count) 
    : strip_count(strip_count), section_count(section_count) {
    led_strips = new LEDStrip *[strip_count];
    for (LEDSectionId i = 0; i < strip_count; i++) {
        led_strips[i] = nullptr;
    }

    led_sections = new LEDSection *[section_count];
    for (uint8_t i = 0; i < section_count; i++) {
        led_sections[i] = nullptr;
    }

    LOG(TAG, "Created");
}

LEDSetup::~LEDSetup() {
    for (LEDSectionId i = 0; i < section_count; i++) {
       delete led_sections[i];
    }
    delete[] led_sections;

    for (uint8_t i = 0; i < strip_count; i++) {
        delete led_strips[i];
    }
    delete[] led_strips;
}

LEDSection *LEDSetup::get_section(LEDSectionId id) {
    if (id < 0 || id >= section_count) return nullptr;

    return led_sections[id];
}

void LEDSetup::add_led_strip(uint8_t index,
                             LEDChip chip,
                             uint16_t length,
                             uint8_t pin_data,
                             uint8_t pin_backup) {
    if (index < 0 || index >= strip_count) return;

    led_strips[index] = new LEDStrip(chip, length, pin_data, pin_backup);
}

void LEDSetup::add_led_section(LEDSectionId id,
                               uint8_t led_strip_index,
                               uint16_t length,
                               uint16_t start_index) {
    if (id < 0 || 
            id >= section_count || 
            led_strip_index < 0 || 
            led_strip_index >= strip_count) {
        return;
    }

    led_sections[id] = new LEDSection(led_strip_index, length, start_index);
}

void LEDSetup::off(LEDSectionId id) {
    if (id == LED_ID_UNSPECIFIED) {
        LEDStrip *strip;
        for (uint8_t i = 0; i < strip_count; i++) {
            strip = led_strips[i];
            strip->off();
        }

        LOG(TAG, "Turned off LED for entire setup");
    } else {
        LEDSection *section = get_section(id);
        if (section == nullptr) return;

        LEDStrip *strip = led_strips[section->led_strip_index];
        strip->off(section->start_index, section->start_index + section->length);

        LOG(TAG, "Turned off LED for section: " + String(id)); 
    }
}

void LEDSetup::light(LEDSectionId id) {
    if (id == LED_ID_UNSPECIFIED) {
        LEDStrip *strip;
        for (uint8_t i = 0; i < strip_count; i++) {
            strip = led_strips[i];
            strip->light();
        }
        LOG(TAG, "Lighting LED for entire setup");
    } else {
        LEDSection *section = get_section(id);
        if (section == nullptr) return;

        LEDStrip *strip = led_strips[section->led_strip_index];
        strip->light(section->start_index, section->start_index + section->length);

        LOG(TAG, "Lighting LED for section: " + String(id));
    }
}

void LEDSetup::set_led(LEDSectionId id,
                       uint16_t index,
                       uint8_t r,
                       uint8_t g,
                       uint8_t b) {
    LEDSection *section = get_section(id);
    if (section == nullptr) return;

    LEDStrip *strip = led_strips[section->led_strip_index];
    strip->set(section->start_index + index, r, g, b);
}

void LEDSetup::set_all(LEDSectionId id,
                       uint8_t r,
                       uint8_t g,
                       uint8_t b) {
    if (id == LED_ID_UNSPECIFIED) {
        LEDStrip *strip;
        for (uint8_t i = 0; i < strip_count; i++) {
            strip = led_strips[i];
            strip->set_all(r, g, b);
        }

        LOG(TAG, "Setting all LED to same color for entire setup");
    } else {
        LEDSection *section = get_section(id);
        if (section == nullptr) return;

        LEDStrip *strip = led_strips[section->led_strip_index];
        strip->set(section->start_index, section->start_index + section->length, r, g, b);

        LOG(TAG, "Setting all LED to same color for section: " + String(id));
    }
}

