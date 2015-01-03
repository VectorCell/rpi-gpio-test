/*
 * blink.c:
 *      blinks the first LED
 *      Gordon Henderson, projects@drogon.net
 */

#include <stdio.h>
#include <wiringPi.h>

int main (void)
{
	printf("Running ...");

	if (wiringPiSetup() == -1)
		return 1;

	int pin_map[] = {8, 9, 7, 15, 16, 1, 0, 2, 3, 4, 5, 12, 13, 14, 6, 10};
	int pin_map_length = 16;
	int pin_clock = 11;

	for (int k = 0; k < pin_map_length; ++k) {
		pinMode(pin_map[k], OUTPUT);
	}
	pinMode(pin_clock, OUTPUT);

	for (;;)
	{
		for (int k = 0; k < pin_map_length; ++k) {
			digitalWrite(pin_map[k], 1);
		}
		delay(500);
		digitalWrite(pin_clock, 1);
		delay(500);
		for (int k = 0; k < pin_map_length; ++k) {
			digitalWrite(pin_map[k], 0);
		}
		delay(500);
		digitalWrite(pin_clock, 0);
		delay(500);
	}
	return 0 ;
}
