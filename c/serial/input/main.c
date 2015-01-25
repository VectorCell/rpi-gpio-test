#include <stdio.h>
#include <stdint.h>
#include <wiringPi.h>

// pin assignments
#define P_DATA 9  // data
#define P_WREN 2  // write enable
#define P_READ 12 // read acknowledge

#define SIZE 800000 // number of bits to be read

int main (void)
{
	printf("Reading %d bits ...\n", SIZE);

	if (wiringPiSetup() == -1)
		return 1;

	int read = 0;
	pinMode(P_DATA, INPUT);
	pinMode(P_WREN, INPUT);
	pinMode(P_READ, OUTPUT);
	digitalWrite(P_READ, read);

	printf("waiting for P_WREN to be 0 ...\n");
	while (digitalRead(P_WREN) != 0);

	printf("bit #   bit val\n");
	printf("------  ------\n");
	uint8_t bits[SIZE];
	for (int i = 0; i < SIZE; ++i) {
		//if (i % 1000 == 0) printf("%d / %d\n", i, SIZE);
		while (digitalRead(P_WREN) == read);
		bits[i] = digitalRead(P_DATA);
		//printf("%6d  %6d\n", i, bits[i]);
		read = !read;
		digitalWrite(P_READ, read);
	}

	printf("setting P_READ to 0 ...\n");
	digitalWrite(P_READ, 1);
	digitalWrite(P_READ, 0);

	printf("done!\n");

	return 0;
}
