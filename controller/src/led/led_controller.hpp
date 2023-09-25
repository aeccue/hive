/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef LED_CONTROLLER_HPP
#define LED_CONTROLLER_HPP

#include "led_setup.hpp"

#include <HardwareSerial.h>

namespace aeccue {

    typedef enum LEDAction {
        ACTION_OFF = 0,
        ACTION_ON = 1,
        ACTION_SET = 2,
        ACTION_SET_ALL = 3
    } LEDAction;


    class LEDController {

        private:

            const int8_t count;
            LEDSetup **led_setups;

            bool setup_exists(LEDSetupId id);

            void set_led(LEDSetupId setup_id, LEDSectionId section_id, HardwareSerial *serial);
            void set_range(LEDSetupId setup_id, LEDSectionId section_id, HardwareSerial *serial);
            void set_all(LEDSetupId setup_id, LEDSectionId section_id, HardwareSerial *serial);
            void send_ack(HardwareSerial *serial);

        public:

            LEDController(int8_t setup_count);
            ~LEDController();

            void add_led_setup(LEDSetupId id, LEDSetup *setup);

            void off(LEDSetupId setup_id, LEDSectionId section_id);
            void light(LEDSetupId setup_id, LEDSectionId section_id);

            void set_led(LEDSetupId setup_id,
                         LEDSectionId section_id,
                         uint16_t index,
                         uint8_t r,
                         uint8_t g,
                         uint8_t b);

            void set_all(LEDSetupId setup_id,
                         LEDSectionId section_id,
                         uint8_t r,
                         uint8_t g,
                         uint8_t b);
                         
            void parse(HardwareSerial *serial);            
    };
}

#endif // LED_CONTROLLER_HPP

