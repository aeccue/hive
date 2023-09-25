/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef LED_H
#define LED_H

#include <stdint.h>

namespace aeccue {

    typedef uint8_t LED[3];

    typedef int8_t LEDSetupId;
    typedef int8_t LEDSectionId;

    #define LED_ID_UNSPECIFIED -1
    
    typedef enum LEDChip {
        WS2815B = 0
    } LEDChip;
}

#endif // LED_H

