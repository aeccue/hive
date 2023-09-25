;
; Author: Aaron Chan <develop@aeccue.com>
; Copyright (c) 2021 aeccue. All rights reserved.
;

#include <avr/io.h>

; extern void ws2815b_light_strip(const void *strip);
.global ws2815b_light_strip
ws2815b_light_strip:
    lds  r21, SREG        ; saves status register
    push r21              ; push status register to stack
    cli                   ; disable interrupts
    push r29              ; save r29
    push r28              ; save r28
    movw r30, r24         ; r30:31 = Z = config
    ld   r28, Z+          ; r28:29 = Y = data
    ld   r29, Z+          ;
    ld   r25, Z+          ; r24:25 = length
    ld   r24, Z+          ;
    ld   r26, Z+          ; r26:27 = X = port
    ld   r27, Z+          ;
    ld   r21, Z+          ; r21 = pins
    ld   r23, X           ; high output
    or   r23, r21         ; 
    ld   r22, X           ; low output
    neg  r21              ;
    and  r22, r21         ; 
    ld   r21, Y+          ; load first byte
    ldi  r20, 7           ; counter for 7 bit
send_pixel:
    st   X, r23           ; 2    0   send high bit
    lsl  r21              ; 1    2   left-shift MSB into C
    brcs send_high        ; 1|2  3   branch if bit is 1
    st   X, r22           ; 2    0   send low bit
    subi r20, 1           ; 1    2   subtract 7 bit counter
    nop                   ; 1    3
    nop                   ; 1    4
    nop                   ; 1    5
    nop                   ; 1    6
    breq next_byte        ; 1|2  7   if last bit, load next byte
    rjmp send_pixel       ; 2    8   send next bit
send_high:
    subi r20, 1           ; 1    5   subtract 7 bit counter
    nop                   ; 1    6
    nop                   ; 1    7
    nop                   ; 1    8
    nop                   ; 1    9
    st   X, r22           ; 2    0   send low bit
    brne send_pixel       ; 1|2  2   send next bit if not last bit
next_byte:
    ldi  r20, 7           ; 1    9|3 reset 7 bit counter
    st   X, r23           ; 2    0   send high bit
    lsl  r21              ; 1    2   left-shift MSB into C 
    brcs send_last_high   ; 1|2  3   branch if bit is 1
    st   X, r22           ; 2    0   send low bit
    nop                   ; 1    2
    nop                   ; 1    3
    ld   r21, Y+          ; 2    4   load next pixel 
    sbiw r24, 1           ; 2    6   decrement data counter
    brne send_pixel       ; 1|2  8   send next pixel
    rjmp finish           ; wrap up
send_last_high:
    nop                   ; 1    5
    ld   r21, Y+          ; 2    6   load next pixel 
    sbiw r24, 1           ; 2    8   decrement data counter
    st   X, r22           ; 2    0   send low bit
    brne send_pixel       ; 1|2  2   send next pixel  
finish:
    pop  r28              ; reload saved r28
    pop  r29              ; reload saved r29
    pop  r21              ; reload saved status register
    sts  SREG, r21        ; restore status register
    ret

; extern void ws2815b_off(const void *strip);
.global ws2815b_off
ws2815b_off:
    lds  r21, SREG        ; saves status register
    push r21              ; push status register to stack
    cli                   ; disable interrupts
    push r29              ; save r29
    push r28              ; save r28
    movw r30, r24         ; r30:31 = Z = config
    ld   r28, Z+          ; r28:29 = Y = data
    ld   r29, Z+          ;
    ld   r25, Z+          ; r24:25 = length
    ld   r24, Z+          ;
    ld   r26, Z+          ; r26:27 = X = port
    ld   r27, Z+          ;
    ld   r21, Z+          ; r21 = pins
    ld   r23, X           ; high output
    or   r23, r21         ; 
    ld   r22, X           ; low output
    neg  r21              ;
    and  r22, r21         ; 
    ldi  r20, 7           ; counter for 7 bit
off_send_pixel:
    st   X, r23           ; 2    0   send high bit
    nop                   ; 1    1
    nop                   ; 1    2
    nop                   ; 1    3
    st   X, r22           ; 2    0   send low bit
    subi r20, 1           ; 1    2   subtract 7 bit counter
    nop                   ; 1    3
    nop                   ; 1    4
    nop                   ; 1    5
    nop                   ; 1    6
    breq off_next_byte    ; 1|2  7   if last bit, load next byte
    rjmp off_send_pixel   ; 2    8   send next bit
off_next_byte:
    ldi  r20, 7           ; 1    9   reset 7 bit counter
    st   X, r23           ; 2    0   send high bit
    nop                   ; 1    2
    nop                   ; 1    3
    st   X, r22           ; 2    0   send low bit
    nop                   ; 1    2
    nop                   ; 1    3
    nop                   ; 1    4
    nop                   ; 1    5
    sbiw r24, 1           ; 2    6   decrement data counter
    brne off_send_pixel   ; 1|2  8   send next pixel
    rjmp finish           ; wrap up

