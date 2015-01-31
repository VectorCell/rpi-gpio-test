#include <stdint.h>

// pin assignments
#define P_DATA 9  // data
#define P_WREN 16 // write enable
#define P_READ 2  // read acknowledge

#define SIZE (1024 * 1024) // number of bytes to be read in total
#define BLOCKSIZE 1024 // data to read all at once

// THIS VALUE SHOULD NEVER BE EVEN
#define REDUNDANCY 3 // the number of times to write/read each bit
