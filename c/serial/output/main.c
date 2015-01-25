#include <stdio.h>
#include <stdint.h>
#include <wiringPi.h>

// pin assignments
#define P_DATA 9  // data
#define P_WREN 2  // write enable
#define P_READ 12 // read acknowledge

#define SIZE 800000 // number of bits to be sent

int main (void)
{
	printf("Sending %d bits ...\n", SIZE);

	if (wiringPiSetup() == -1)
		return 1;

	int wren = 0;
	pinMode(P_DATA, OUTPUT);
	pinMode(P_WREN, OUTPUT);
	pinMode(P_READ, INPUT);
	digitalWrite(P_WREN, wren);

	printf("waiting for P_READ to be 0 ...\n");
	while (digitalRead(P_READ) != 0);

	printf("bit #   bit val\n");
	printf("------  ------\n");
	for (int i = 0; i < SIZE; ++i) {
		//if (i % 1000 == 0) printf("%d / %d\n", i, SIZE);
		uint8_t bit = rand() & 1;
		//printf("%6d  %6d\n", i, bit);
		digitalWrite(P_DATA, bit);
		wren = !wren;
		digitalWrite(P_WREN, wren);
		while (digitalRead(P_READ) != wren);
	}

	printf("setting P_WREN to 0 ...\n");
	digitalWrite(P_WREN, 1);
	digitalWrite(P_WREN, 0);

	printf("done!\n");

	return 0 ;
}
