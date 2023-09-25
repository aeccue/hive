/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#include "bluetooth_receiver.hpp"

#include <Arduino.h>

#include "../microcontroller/microcontroller.h"


#define TAG "Bluetooth Receiver"
#define BAUD_RATE 115200 

using namespace aeccue;

BluetoothReceiver::BluetoothReceiver(HardwareSerial *serial) : serial(serial) {
    serial->begin(BAUD_RATE);
    serial->setTimeout(5000);
    delay(100);    

    LOG(TAG, "Created");
}

void BluetoothReceiver::receive() {
    if (serial->available()) {
        CommandID id = serial->read();

        switch (id) {
            case COMMAND_ID_LED:
                LOG(TAG, "Command: LED");
                led_controller->parse(serial);
                break;
            default:
                LOG(TAG, "Invalid command");
                break;
        }
    }
}

