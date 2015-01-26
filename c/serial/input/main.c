#include <stdio.h>
#include <stdint.h>
#include <wiringPi.h>

#include "../common.h"

int read = 0;

inline int read_bit()
{
	while (digitalRead(P_WREN) == read);
	read = !read;
	digitalWrite(P_READ, read);
	return digitalRead(P_DATA);
}

int main()
{
	//printf("Reading %d bytes ...\n", SIZE);

	if (wiringPiSetup() == -1)
		return 1;

	pinMode(P_DATA, INPUT);
	pinMode(P_WREN, INPUT);
	pinMode(P_READ, OUTPUT);
	digitalWrite(P_READ, read);


	//printf("waiting for P_WREN to be 0 ...\n");
	while (digitalRead(P_WREN) != 0);

	//printf("reading data ...\n");
	//printf("byte #  byte val\n");
	//printf("------  --------\n");
	for (int i = 0; i < SIZE; ++i) {
		uint8_t byte = 0;
		for (int bit = 7; bit >= 0; --bit) {
			byte <<= 1;
			byte += read_bit();
		}
		data[i] = byte;
		//printf("%6d  ", i);
		//printf("%6d\n", byte);
	}

	printf("verfying data ...\n");
	uint32_t bad_bytes = 0;
	for (int i = 0; i < SIZE; ++i) {
		if (data[i] != (i & 0xFF)) {
			++bad_bytes;
			printf("discrepancy: byte %d should be %d, but is %d\n", i, i & 0xFF, data[i]);
		}
	}
	printf("%d / %d bad bytes\n", bad_bytes, SIZE);

	//printf("setting P_READ to 0 ...\n");
	digitalWrite(P_READ, 0);

	//printf("done!\n");

	return 0;
}
