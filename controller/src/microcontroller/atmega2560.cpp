/*
 * Author: Aarhigh Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#include "atmega2560.hpp"

static const MicrocontrollerPortPin PIN22 = { .port = &PORTA, .pin = PA0 };
static const MicrocontrollerPortPin PIN23 = { .port = &PORTA, .pin = PA1 };
static const MicrocontrollerPortPin PIN24 = { .port = &PORTA, .pin = PA2 };
static const MicrocontrollerPortPin PIN25 = { .port = &PORTA, .pin = PA3 };
static const MicrocontrollerPortPin PIN26 = { .port = &PORTA, .pin = PA4 };
static const MicrocontrollerPortPin PIN27 = { .port = &PORTA, .pin = PA5 };
static const MicrocontrollerPortPin PIN28 = { .port = &PORTA, .pin = PA6 };
static const MicrocontrollerPortPin PIN29 = { .port = &PORTA, .pin = PA7 };
static const MicrocontrollerPortPin PIN30 = { .port = &PORTC, .pin = PC7 };
static const MicrocontrollerPortPin PIN31 = { .port = &PORTC, .pin = PC6 };
static const MicrocontrollerPortPin PIN32 = { .port = &PORTC, .pin = PC5 };
static const MicrocontrollerPortPin PIN33 = { .port = &PORTC, .pin = PC4 };
static const MicrocontrollerPortPin PIN34 = { .port = &PORTC, .pin = PC3 };
static const MicrocontrollerPortPin PIN35 = { .port = &PORTC, .pin = PC2 };
static const MicrocontrollerPortPin PIN36 = { .port = &PORTC, .pin = PC1 };
static const MicrocontrollerPortPin PIN37 = { .port = &PORTC, .pin = PC0 };
static const MicrocontrollerPortPin PIN38 = { .port = &PORTD, .pin = PD7 };
static const MicrocontrollerPortPin PIN39 = { .port = &PORTG, .pin = PG2 };
static const MicrocontrollerPortPin PIN40 = { .port = &PORTG, .pin = PG1 };
static const MicrocontrollerPortPin PIN41 = { .port = &PORTG, .pin = PG0 };
static const MicrocontrollerPortPin PIN42 = { .port = &PORTL, .pin = PL7 };
static const MicrocontrollerPortPin PIN43 = { .port = &PORTL, .pin = PL6 };
static const MicrocontrollerPortPin PIN44 = { .port = &PORTL, .pin = PL5 };
static const MicrocontrollerPortPin PIN45 = { .port = &PORTL, .pin = PL4 };
static const MicrocontrollerPortPin PIN46 = { .port = &PORTL, .pin = PL3 };
static const MicrocontrollerPortPin PIN47 = { .port = &PORTL, .pin = PL2 };
static const MicrocontrollerPortPin PIN48 = { .port = &PORTL, .pin = PL1 };
static const MicrocontrollerPortPin PIN49 = { .port = &PORTL, .pin = PL0 };
static const MicrocontrollerPortPin INVALID_PIN = { .port = 0, .pin = 0 };

const MicrocontrollerPortPin get_port_pin(uint8_t mapped_pin) {

    switch (mapped_pin) {
        case 22: return PIN22;
        case 23: return PIN23;
        case 24: return PIN24;
        case 25: return PIN25;
        case 26: return PIN26;
        case 27: return PIN27;
        case 28: return PIN28;
        case 29: return PIN29;
        case 30: return PIN30;
        case 31: return PIN31;
        case 32: return PIN32;
        case 33: return PIN33;
        case 34: return PIN34;
        case 35: return PIN35;
        case 36: return PIN36;
        case 37: return PIN37;
        case 38: return PIN38;
        case 39: return PIN39;
        case 40: return PIN40;
        case 41: return PIN41;
        case 42: return PIN42;
        case 43: return PIN43;
        case 44: return PIN44;
        case 45: return PIN45;
        case 46: return PIN46;
        case 47: return PIN47;
        case 48: return PIN48;
        case 49: return PIN49;
        default: return INVALID_PIN;
    };
}

uint16_t read_uint16_t(HardwareSerial *serial) {
    while(!serial->available());
    uint16_t low = serial->read() & 0xFF;
    while(!serial->available());
    uint16_t high = serial->read() & 0xFF;

    return (uint16_t) (high << 8 | low);
}

