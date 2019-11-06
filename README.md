# Project 2
Irene Liu  
irliu@chapman.edu  
2313260  
CPSC 380  


*To run: compile and run Seesaw.java*

## Design:
Two binary semaphores were used in order to synchronize the output and calculations between the Fred and Wilma threads. 
Each thread is responsible for releasing the semaphore required for the other thread to do work. The ordering of these semaphores ensures that the loop in the Fred thread executes before the loop in the Wilma thread.

To ensure that Fred starts at the beginning, the 1st semaphore is initialized to 1 while the 2nd is initialized to 0.

Fred acquires the 1st semaphore before entering an iteration of the while loop. Fred will then release the 2nd semaphore upon completion of one iteration of its while loop. Wilma is blocked until Fred has finished calculating and printing because the 2nd semaphore must be acquired before executing any statements in Wilma's loop. Afterward, Wilma releases the 1st semaphore which ensures that the Fred thread is unblocked and can resume processing.
