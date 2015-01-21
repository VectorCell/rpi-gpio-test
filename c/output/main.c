/*
 * blink.c:
 *      blinks the first LED
 *      Gordon Henderson, projects@drogon.net
 */

#include <stdio.h>
#include <stdint.h>
#include <wiringPi.h>

#define DELAY 100

int main (void)
{
	printf("Running ...\n");

	if (wiringPiSetup() == -1)
		return 1;

	int pin_map[] = {8, 9, 7, 15, 16, 1, 0, 2, 3, 4, 5, 12, 13, 14, 6, 10};
	int pin_map_length = 16;
	int pin_clock = 11;
	int clock_status = 0;

	for (int k = 0; k < pin_map_length; ++k) {
		pinMode(pin_map[k], OUTPUT);
	}
	pinMode(pin_clock, OUTPUT);

	for (int i = 0; i < 2; ++i)
	{
		for (int k = 0; k < pin_map_length; ++k) {
			digitalWrite(pin_map[k], 1);
		}
		clock_status = !clock_status;
		digitalWrite(pin_clock, clock_status);
		delay(DELAY);
		for (int k = 0; k < pin_map_length; ++k) {
			digitalWrite(pin_map[k], 0);
		}
		clock_status = !clock_status;
		digitalWrite(pin_clock, clock_status);
		delay(DELAY);
	}

	uint16_t val = 0;
	uint16_t tmp;
	do {
		for (int k = 0; k < pin_map_length; ++k) {
			tmp = val >> k;
			// printf("%d ", tmp & 1);
			digitalWrite(pin_map[k], tmp & 1);
		}
		clock_status = !clock_status;
		digitalWrite(pin_clock, clock_status);
		// printf(" ::  %d\n", val);
		++val;
	} while (val != 0);

	return 0 ;
}
