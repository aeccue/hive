/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef WS2815B_HPP
#define WS2815B_HPP

#include "led_chipset.hpp"

namespace aeccue {

    class WS2815BChipset : public LEDChipset {
        public:
            WS2815BChipset() : LEDChipset(WS2815B) {}

            void set_led(LED led, uint8_t r, uint8_t g, uint8_t b);
            void set_led(LED led, LED source);

            void light_leds(const LED *leds, 
                            uint16_t length, 
                            uint8_t *port, 
                            uint8_t pins);

            void off(uint16_t length, 
                     uint8_t *port, 
                     uint8_t pins);
    };

}

#endif // WS2815B_HPP

