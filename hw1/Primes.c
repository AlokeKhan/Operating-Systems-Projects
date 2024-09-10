#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include "Primes.h"

int random_in_range(int lower_bound, int upper_bound){
    return ((rand() % (upper_bound - lower_bound)) + lower_bound);
}

void populate (int *arr, int size, int lower_bound, int upper_bound){
    for (int i = 0; i < size; i++){
        arr[i] = random_in_range(lower_bound, upper_bound);
    }
}

int get_prime_count (int *array, int arraySize) {
   int prime_count = 0; 
   
   for (int i = 0; i < arraySize; i++) {
       int num = array[i];
       if (num <= 1) {
           continue;  
       }

       int FLAG = 1; // Assume number is prime
       for (int j = 2; j <= sqrt(num); j++) {
           if (num % j == 0) { //Check if divisible
               FLAG = 0; 
               break;
           }
       }
       
       if (FLAG == 1) {
           prime_count++; // Increase prime count if not divisible
       }
   }
   
   return prime_count;
}
float get_running_ratio(){
    int maxPrimeCount = 0;
    int currentPrimeCount = 0;
    int maxCountIteration = 0;
    float finalRatio = 0;

    int arr_size = 0; 

    int num_iterations = random_in_range(50, 101);

    for (int i = 1; i < num_iterations; i++){
        arr_size = random_in_range(150, 200);

        int *array = (int *) malloc(sizeof(int) * arr_size);

        populate(array, arr_size, 50, 200);

        currentPrimeCount = get_prime_count(array, arr_size); //call prime count
        int currentCompositeCount = arr_size - currentPrimeCount;
        float ratio = (float)currentPrimeCount / (currentCompositeCount); //calculate ratio
        finalRatio += ratio; //add to running ratio

        if (currentPrimeCount > maxPrimeCount){
            maxPrimeCount = currentPrimeCount;
            maxCountIteration = i -1;
        }

        //free(array);
    }
    printf("[Primes]: Number of iterations is: %d\n", num_iterations);
    printf("[Primes]: Iteration with MAX prime count: %d\n", maxCountIteration);

    return finalRatio / num_iterations; //return average ratio

}