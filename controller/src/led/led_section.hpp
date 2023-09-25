/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) aeccue 2021. All rights reserved. 
 */

#ifndef LED_SECTION_HPP
#define LED_SECTION_HPP

namespace aeccue {

    struct LEDSection {
        uint8_t led_strip_index;
        uint16_t length;
        uint16_t start_index;

        LEDSection(uint8_t led_strip_index, 
                   uint16_t length, 
                   uint16_t start_index
        ) : led_strip_index(led_strip_index), length(length), start_index(start_index) {}
    };
}

#endif // LED_SECTION_HPP

