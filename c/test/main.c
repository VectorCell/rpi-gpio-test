/*
 * main.c
 */

#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <signal.h>
#include <time.h>
#include <wiringPi.h>

#define DELAY 250

#define P_LED 12
const int pins[] = {8, 9, 7, 15, 16, 1, 0, 2, 3, 4, 5, 12, 13, 14, 6, 10, 11, 21, 22, 23};
const int n_pins = sizeof(pins) / sizeof(pins[0]);

void sig_handler(int sig) {
	if (sig == SIGINT || sig == SIGTERM) { 
		digitalWrite(P_LED, 0);
		printf("exiting");
		exit(0);
	}
}

int n_event = 0;
void pin_handler(int pin) {
	int index = -1;
	for (int p = 0; p < n_pins; ++p) {
		if (pins[p] == pin) {
			index = p;
			break;
		}
	}
	printf("event %08X :: read signal on pin %2d (index %2d)\n", n_event++, pin, index);
	if (n_event % 20 == 0) {
		printf("\n");
	}
	//if (n_event >= 20) exit(0);
}
void pin_handler_0(void) { pin_handler(0); }
void pin_handler_1(void) { pin_handler(1); }
void pin_handler_2(void) { pin_handler(2); }
void pin_handler_3(void) { pin_handler(3); }
void pin_handler_4(void) { pin_handler(4); }
void pin_handler_5(void) { pin_handler(5); }
void pin_handler_6(void) { pin_handler(6); }
void pin_handler_7(void) { pin_handler(7); }
void pin_handler_8(void) { pin_handler(8); }
void pin_handler_9(void) { pin_handler(9); }
void pin_handler_10(void) { pin_handler(10); }
void pin_handler_11(void) { pin_handler(11); }
void pin_handler_12(void) { pin_handler(12); }
void pin_handler_13(void) { pin_handler(13); }
void pin_handler_14(void) { pin_handler(14); }
void pin_handler_15(void) { pin_handler(15); }
void pin_handler_16(void) { pin_handler(16); }
void pin_handler_21(void) { pin_handler(21); }
void pin_handler_22(void) { pin_handler(22); }
void pin_handler_23(void) { pin_handler(23); }

int main (void) {
	signal(SIGINT, sig_handler);
	signal(SIGTERM, sig_handler);

	printf("Running ...\n");

	if (wiringPiSetup() == -1)
		return 1;

	printf("Setting up %d pins:\n", n_pins);
	for (int p = 0; p < n_pins; ++p) {
		printf("Setting up pin %2d ... ", pins[p]);
		pinMode(pins[p], INPUT);
		printf("done!\n");
	}

//	for (;;) {
//		for (int p = 0; p < n_pins; ++p) {
//			printf("blinking pin %d\n", pins[p]);
//			digitalWrite(pins[p], 1);
//			delay(DELAY);
//			digitalWrite(pins[p], 0);
//			delay(DELAY);
//		}
//	}

	printf("Waiting for input ...\n");

	wiringPiISR(pins[0], INT_EDGE_RISING, pin_handler_0);
	wiringPiISR(pins[1], INT_EDGE_RISING, pin_handler_1);
	wiringPiISR(pins[2], INT_EDGE_RISING, pin_handler_2);
	wiringPiISR(pins[3], INT_EDGE_RISING, pin_handler_3);
	wiringPiISR(pins[4], INT_EDGE_RISING, pin_handler_4);
	wiringPiISR(pins[5], INT_EDGE_RISING, pin_handler_5);
	wiringPiISR(pins[6], INT_EDGE_RISING, pin_handler_6);
	wiringPiISR(pins[7], INT_EDGE_RISING, pin_handler_7);
	wiringPiISR(pins[8], INT_EDGE_RISING, pin_handler_8);
	wiringPiISR(pins[9], INT_EDGE_RISING, pin_handler_9);
	wiringPiISR(pins[10], INT_EDGE_RISING, pin_handler_10);
	wiringPiISR(pins[11], INT_EDGE_RISING, pin_handler_11);
	wiringPiISR(pins[12], INT_EDGE_RISING, pin_handler_12);
	wiringPiISR(pins[13], INT_EDGE_RISING, pin_handler_13);
	wiringPiISR(pins[14], INT_EDGE_RISING, pin_handler_14);
	wiringPiISR(pins[15], INT_EDGE_RISING, pin_handler_15);
	wiringPiISR(pins[16], INT_EDGE_RISING, pin_handler_16);
	wiringPiISR(pins[21], INT_EDGE_RISING, pin_handler_21);
	wiringPiISR(pins[22], INT_EDGE_RISING, pin_handler_22);
	wiringPiISR(pins[23], INT_EDGE_RISING, pin_handler_23);
	for (;;) delay(5000);

	return 0 ;
}
