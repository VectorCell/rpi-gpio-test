#include <stdint.h>

// pin assignments
#define P_DATA 9  // data
#define P_WREN 2  // write enable
#define P_READ 12 // read acknowledge

#define SIZE 1024 // (1024 * 1024) // number of bytes to be read
uint8_t data[SIZE];
