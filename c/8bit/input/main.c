/*
 * blink.c:
 *      blinks the first LED
 *      Gordon Henderson, projects@drogon.net
 */

#include <stdio.h>
#include <stdint.h>
#include <wiringPi.h>

// pins 0-7 are data bits 0-7
// pin 0 is the least significant bit
#define WIDTH 8
#define P_CLK 8  // clock (toggle when data is ready to be read)
#define P_CON 9  // confirmation from receiver (matches clock when data is read)

int change_count = 0;
void read_pins()
{
	printf("CLOCK CHANGE %d\n", ++change_count);
	digitalWrite(P_CON, digitalRead(P_CLK));
}

int main (void)
{
	printf("Running ...\n");

	if (wiringPiSetup() == -1)
		return 1;

	for (int p = 0; p < WIDTH; ++p) {
		pinMode(p, INPUT);
	}
	//pinMode(P_CLK, INPUT);
	pinMode(P_CON, OUTPUT);

	wiringPiISR(P_CLK, INT_EDGE_BOTH, &read_pins);

	while (1) {
		delay(10000);
	}

	return 0;
}
