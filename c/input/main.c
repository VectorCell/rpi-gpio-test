/*
 * blink.c:
 *      blinks the first LED
 *      Gordon Henderson, projects@drogon.net
 */

#include <stdio.h>
#include <stdint.h>
#include <wiringPi.h>

int main (void)
{
	printf("Running ...\n");

	if (wiringPiSetup() == -1)
		return 1;

	int pin_map[] = {8, 9, 7, 15, 16, 1, 0, 2, 3, 4, 5, 12, 13, 14, 6, 10};
	int pin_map_length = 16;
	int pin_clock = 11;

	for (int k = 0; k < pin_map_length; ++k) {
		pinMode(pin_map[k], INPUT);
	}
	pinMode(pin_clock, INPUT);

	int clock_state = 0;
	int last_clock;
	uint16_t bits;
	for (;;)
	{
		last_clock = clock_state;
		bits = 0;
		while (last_clock == clock_state) {
			clock_state = digitalRead(pin_clock);
		}
		// printf("%d  ::  ", clock_state);
		for (int k = pin_map_length - 1; k >= 0; --k) {
			bits <<= 1;
			bits += digitalRead(pin_map[k]);
			// printf("pin %2d :: %d\n", k, digitalRead(pin_map[k]));
			printf("%d ", digitalRead(pin_map[k]));
		}
		printf(" ::  %5d\n", bits);
		// printf("clock  :: %d\n", digitalRead(pin_clock));
	}
	return 0;
}
