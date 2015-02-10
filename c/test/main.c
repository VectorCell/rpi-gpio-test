/*
 * main.c
 */

#include <stdlib.h>
#include <stdio.h>
#include <stdint.h>
#include <signal.h>
#include <time.h>
#include <wiringPi.h>

#define DELAY 250

#define P_BUZZ 15

void sig_handler(int sig)
{
	if (sig == SIGINT || sig == SIGTERM) {
		printf("\nsetting all pins to low ... ");
		digitalWrite(P_BUZZ, 0);
		printf("exiting");
		exit(0);
	}
}

int main (void)
{
	signal(SIGINT, sig_handler);
	signal(SIGTERM, sig_handler);

	time_t t;
	srand((unsigned) time(&t));

	printf("Running with delay %d ...\n", DELAY);

	if (wiringPiSetup() == -1)
		return 1;

	pinMode(P_BUZZ, OUTPUT);
	digitalWrite(P_BUZZ, 0);

	for (uint32_t i = 0; i < UINT32_MAX; ++i) {
		if (i & 1) {
			printf("buzz %d\n", (i - 1) / 2);
		}
		digitalWrite(P_BUZZ, i & 1);
		delay(DELAY);
	}

	return 0 ;
}
