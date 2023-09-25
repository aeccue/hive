/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef LED_CHIPSET_HPP
#define LED_CHIPSET_HPP

#include "../led.h"

namespace aeccue {


    class LEDChipset {
        public:
    
            const LEDChip chip;

            LEDChipset(LEDChip chip): chip(chip) {}
            virtual ~LEDChipset() {}
        
            virtual void set_led(LED led, uint8_t r, uint8_t g, uint8_t b) = 0;
            virtual void set_led(LED led, LED source) = 0;
    
            virtual void light_leds(const LED *leds, 
                                    uint16_t length, 
                                    uint8_t *port, 
                                    uint8_t pins) = 0;

            virtual void off(uint16_t length, 
                             uint8_t *port, 
                             uint8_t pins) = 0;
    };

    const LEDChipset * getChipset(LEDChip chip);
}

#endif // LED_CHIPSET_HPP

