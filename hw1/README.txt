1. Without leak (With seed 12345)
honolulu:~/CS370/CS370/hw1$ valgrind run 12345
==156239== Memcheck, a memory error detector
==156239== Copyright (C) 2002-2022, and GNU GPL'd, by Julian Seward et al.
==156239== Using Valgrind-3.22.0 and LibVEX; rerun with -h for copyright info
==156239== Command: run 12345
==156239== 
[Driver]: With seed: 12345
[Primes]: Number of iterations is: 99
[Primes]: Iteration with MAX prime count: 56
[Driver]: AVG prime/composite ratio: 0.257423
==156239== 
==156239== HEAP SUMMARY:
==156239==     in use at exit: 0 bytes in 0 blocks
==156239==   total heap usage: 99 allocs, 99 frees, 70,360 bytes allocated
==156239== 
==156239== All heap blocks were freed -- no leaks are possible
==156239== 
==156239== For lists of detected and suppressed errors, rerun with: -s
==156239== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)

2. With leaks (with seed 12345)
honolulu:~/CS370/CS370/hw1$ valgrind Driver 12345
==160791== Memcheck, a memory error detector
==160791== Copyright (C) 2002-2022, and GNU GPL'd, by Julian Seward et al.
==160791== Using Valgrind-3.22.0 and LibVEX; rerun with -h for copyright info
==160791== Command: Driver 12345
==160791== 
[Driver]: With seed: 12345
[Primes]: Number of iterations is: 99
[Primes]: Iteration with MAX prime count: 56
[Driver]: AVG prime/composite ratio: 0.257423
==160791== 
==160791== HEAP SUMMARY:
==160791==     in use at exit: 69,336 bytes in 98 blocks
==160791==   total heap usage: 99 allocs, 1 frees, 70,360 bytes allocated
==160791== 
==160791== LEAK SUMMARY:
==160791==    definitely lost: 69,336 bytes in 98 blocks
==160791==    indirectly lost: 0 bytes in 0 blocks
==160791==      possibly lost: 0 bytes in 0 blocks
==160791==    still reachable: 0 bytes in 0 blocks
==160791==         suppressed: 0 bytes in 0 blocks
==160791== Rerun with --leak-check=full to see details of leaked memory
==160791== 
==160791== For lists of detected and suppressed errors, rerun with: -s
==160791== ERROR SUMMARY: 0 errors from 0 contexts (suppressed: 0 from 0)