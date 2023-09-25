/*
 * Author: Aaron Chan <develop@aeccue.com>
 * Copyright (c) 2021 aeccue. All rights reserved.
 */

#ifndef BLUETOOTH_RECEIVER_HPP
#define BLUETOOTH_RECEIVER_HPP

#include <HardwareSerial.h>

namespace aeccue {

    typedef enum CommandID {
        COMMAND_ID_LED = 0
    } CommandID;


    class BluetoothReceiver {

        private:

            const HardwareSerial *serial;

        public:

            BluetoothReceiver(HardwareSerial *serial);
            ~BluetoothReceiver() {}

            void receive(); 
    };
}

#endif // BLUETOOTH_RECEIVER_HPP

