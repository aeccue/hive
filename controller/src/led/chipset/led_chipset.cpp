/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#include "led_chipset.hpp"

#include "ws2815b_chipset.hpp"

using namespace aeccue;

const LEDChipset * aeccue::getChipset(LEDChip chip) {
    switch(chip) {
        case WS2815B:
            return new WS2815BChipset();
        default:
            return nullptr;
    }
}

