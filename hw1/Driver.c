#include <stdio.h>
#include <stdlib.h>
#include "Primes.h"
#include <math.h>

int main(int argc, char *argv[]) {

   int seed = 0;
   if (argc > 1){seed = atoi(argv[1]);}
   else {seed = 1000;}
   
   printf("[Driver]: With seed: %d\n", seed);

   srand(seed); //rng with seed


   float running_ratio = get_running_ratio();
   printf("[Driver]: AVG prime/composite ratio: %f\n", running_ratio);
   return 0;
}