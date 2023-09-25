/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef LED_STRIP_HPP
#define LED_STRIP_HPP

#include <stdint.h>

#include "led.h"
#include "chipset/led_chipset.hpp"

namespace aeccue {

    class LEDStrip {
        private:

            LED *leds;
            LED *buffer;
    
            uint8_t *port;
            uint8_t pins; 
    
            LEDChipset *chipset;
    
        public:
        
            bool initialized = false;
            uint16_t length;
        
            /*
             * pin_data and pin_backup must be on the same port
             */
            LEDStrip(LEDChip chip,
                     uint16_t length,
                     uint8_t pin_data,
                     uint8_t pin_backup);
    
            ~LEDStrip();

            void reset();

            void off();
            void off(uint16_t start, uint16_t end);

            void set(uint16_t start, uint16_t end, uint8_t r, uint8_t g, uint8_t b);
            void set(uint16_t index, uint8_t r, uint8_t g, uint8_t b);
            void set_all(uint8_t r, uint8_t g, uint8_t b);
    
            void light();
            void light(uint16_t start, uint16_t end);
    };

}

#endif // LED_STRIP_HPP

