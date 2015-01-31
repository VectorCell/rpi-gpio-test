#include <stdio.h>
#include <stdint.h>
#include <wiringPi.h>

#include "../common.h"

uint8_t data[BLOCKSIZE];
int read = 0;

inline int read_bit()
{
	while (digitalRead(P_WREN) == read) {}
	read = !read;
	digitalWrite(P_READ, read);
	return digitalRead(P_DATA);
}

int main()
{
	printf("Reading %d bytes (in %d-byte blocks) with %d-bit redundancy ...\n", SIZE, BLOCKSIZE, REDUNDANCY);

	if (wiringPiSetup() == -1)
		return 1;

	pinMode(P_DATA, INPUT);
	pinMode(P_WREN, INPUT);
	pinMode(P_READ, OUTPUT);
	digitalWrite(P_READ, read);


	//printf("waiting for P_WREN to be 0 ...\n");
	while (digitalRead(P_WREN) != 0);

	//printf("reading data ...\n");
	uint32_t bad_bytes_total = 0;
	for (int block = 0; block < SIZE; block += BLOCKSIZE) {
		//printf("reading block %d / %d\n", block / BLOCKSIZE, SIZE / BLOCKSIZE);
		for (int i = 0; i < BLOCKSIZE; ++i) {
			uint8_t byte = 0;
			for (int bit = 7; bit >= 0; --bit) {
				byte <<= 1;
				if (REDUNDANCY >= 3) {
					int bit = 0;
					for (int r = 0; r < REDUNDANCY; ++r)
						bit += read_bit();
					if (bit > REDUNDANCY / 2)
						++byte;
				} else if (REDUNDANCY == 2) {
					read_bit();
					byte += read_bit();
				} else {
					byte += read_bit();
				}
			}
			data[i] = byte;
			//printf("%6d  ", i);
			//printf("%6d\n", byte);
		}
		//printf("verfying data ...\n");
		//printf("byte #    exp  act  diff\n");
		//printf("--------  ---  ---  ---\n");
		uint32_t bad_bytes = 0;
		for (int i = 0; i < BLOCKSIZE; ++i) {
			if (data[i] != (i & 0xFF)) {
				++bad_bytes;
				int diff = data[i] - (i & 0xFF);
				if (diff < 0)
					diff *= -1;
				//printf("%8d  %3d  %3d  %3d\n", i, i & 0xFF, data[i], diff);
			}
		}
		// if (bad_bytes > 0) printf("%d / %d bad bytes\n\n", bad_bytes, BLOCKSIZE);
		bad_bytes_total += bad_bytes;
	}
	printf("%d / %d bad bytes in entire stream\n", bad_bytes_total, SIZE);

//	printf("verfying data ...\n");
//	printf("byte #    exp  act  diff\n");
//	printf("--------  ---  ---  ---\n");
//	uint32_t bad_bytes = 0;
//	for (int i = 0; i < SIZE; ++i) {
//		if (data[i] != (i & 0xFF)) {
//			++bad_bytes;
//			int diff = data[i] - (i & 0xFF);
//			if (diff < 0)
//				diff *= -1;
//			printf("%8d  %3d  %3d  %3d\n", i, i & 0xFF, data[i], diff);
//		}
//	}
//	printf("%d / %d bad bytes\n", bad_bytes, SIZE);

	//printf("setting P_READ to 0 ...\n");
	digitalWrite(P_READ, 0);

	//printf("done!\n");

	return 0;
}
