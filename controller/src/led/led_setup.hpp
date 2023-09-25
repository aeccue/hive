/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef LED_SETUP_HPP
#define LED_SETUP_HPP

#include "led_strip.hpp"
#include "led_section.hpp"

namespace aeccue {
    
    class LEDSetup {
        
        private:

            const uint8_t strip_count;
            const int8_t section_count;

            LEDStrip **led_strips;
            LEDSection **led_sections;

            LEDSection *get_section(LEDSectionId id);

        public:

            LEDSetup(uint8_t strip_count, int8_t section_count);
            ~LEDSetup();

            void add_led_strip(uint8_t index,
                               LEDChip chip,
                               uint16_t length,
                               uint8_t pin_data,
                               uint8_t pin_backup);

            void add_led_section(LEDSectionId id,
                                 uint8_t led_strip_index,
                                 uint16_t length,
                                 uint16_t start_index);

            void off(LEDSectionId id);
            void light(LEDSectionId id);

            void set_led(LEDSectionId id,
                         uint16_t index,
                         uint8_t r,
                         uint8_t g,
                         uint8_t b);

            void set_all(LEDSectionId id,
                         uint8_t r,
                         uint8_t g,
                         uint8_t b);
    };
}

#endif // LED_SETUP_HPP

