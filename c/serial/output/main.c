#include <stdio.h>
#include <stdint.h>
#include <stdlib.h>
#include <time.h>
#include <wiringPi.h>

#include "../common.h"

uint8_t data[BLOCKSIZE];
int wren = 0;

inline void write_bit(int bit)
{
	digitalWrite(P_DATA, bit);
	wren = !wren;
	digitalWrite(P_WREN, wren);
	while (digitalRead(P_READ) != wren) {}
}

int main()
{
	printf("Sending %d bytes (in %d-byte blocks) with %d-bit redundancy ...\n", SIZE, BLOCKSIZE, REDUNDANCY);

	time_t start = time(NULL);

	if (wiringPiSetup() == -1)
		return 1;

	pinMode(P_DATA, OUTPUT);
	pinMode(P_WREN, OUTPUT);
	pinMode(P_READ, INPUT);
	digitalWrite(P_WREN, wren);

	//printf("generating data ...\n");
	srand(time(NULL));
	for (int i = 0; i < BLOCKSIZE; ++i) {
		//data[i] = rand() & 0xFF;
		data[i] = i & 0xFF;
	}

	//printf("waiting for P_READ to be 0 ...\n");
	while (digitalRead(P_READ) != 0);

	//printf("sending data ...\n");
	//printf("byte #  byte val\n");
	//printf("------  --------\n");
	for (int block = 0; block < SIZE; block += BLOCKSIZE) {
		for (int i = 0; i < BLOCKSIZE; ++i) {
			for (int bit = 7; bit >= 0; --bit) {
				for (int r = 0; r < REDUNDANCY; ++r) {
					write_bit((data[i] >> bit) & 1);
				}
			}
		}
	}

//	printf("data:\n");
//	printf("byte #  byte val\n");
//	printf("------  --------\n");
//	for (int i = 0; i < SIZE; ++i) {
//		printf("%6d  %8d\n", i, data[i]);
//	}

	//printf("setting P_WREN to 0 ...\n");
	digitalWrite(P_WREN, 0);

	time_t end = time(NULL);
	int elapsed = end - start;
	printf("sent %d bytes in %d seconds (%d kB/s)\n", SIZE, elapsed, SIZE / 1024 / elapsed);

	return 0 ;
}
