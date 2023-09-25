/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef MICROCONTROLLER_BASE_H
#define MICROCONTROLLER_BASE_H

typedef struct MicrocontrollerPortPin {
    uint8_t *port;
    uint8_t pin;
} MicrocontrollerPortPin;

#endif // MICROCONTROLLER_BASE_H
