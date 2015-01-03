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
		pinMode(pin_map[k], INPUT);
	}
	pinMode(pin_clock, INPUT);

	int last_clock = 0;
	int clock_state = 0;
	for (;;)
	{
		while (last_clock == clock_state) {
			clock_state = digitalRead(pin_clock);
		}
		for (int k = 0; k < pin_map_length; ++k)
			printf("pin %2d :: %d\n", k, digitalRead(pin_map[k]));
		// printf("clock  :: %d\n", digitalRead(pin_clock));
		last_clock = clock_state;
	}
	return 0;
}
