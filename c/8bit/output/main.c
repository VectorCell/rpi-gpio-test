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

#define DELAY 100

int main (void)
{
	printf("Running ...\n");

	if (wiringPiSetup() == -1)
		return 1;

	for (int p = 0; p < WIDTH; ++p) {
		pinMode(p, OUTPUT);
	}
	pinMode(P_CLK, OUTPUT);
	pinMode(P_CON, INPUT);

	for (int i = 0; i < 256; ++i) {
//		for (int p = WIDTH - 1; p >= 0; --p) {
//			digitalWrite(p, (i >> WIDTH - 1 - p) & 1);
//		}
		digitalWrite(P_CLK, i % 2);
		while (digitalRead(P_CON) != i % 2);
	}

	return 0 ;
}
