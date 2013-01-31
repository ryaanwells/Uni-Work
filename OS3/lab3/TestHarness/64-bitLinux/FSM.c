#include <stdio.h>
#include <stdlib.h>


/*
 * Author: Peter Dickman and Joe Sventek
 * Edited: 15-11-96
 * Version 1.01
 *
 * This is the header for the free space manager exercise
 */

typedef void *FSM;

struct run{
	unsigned long start;
	unsigned long end;
	struct run *next;
};

struct fsm{
	unsigned long free_size;
	struct run *Occupied;
	struct run *Holes;
};

/* arguments indicate lower & upper limits of indices to be managed */
/* it will be normal, but not required, for 'first' to be 0       */
/* assume that initially all indices are free */
FSM createFSM(unsigned long first, unsigned long last){
	struct fsm *newFSM;
	struct run *holes;
	newFSM = malloc(sizeof(struct fsm));
	holes = malloc(sizeof(struct run));
	holes->start=first;
	holes->end=last;
	holes->next=NULL;
	newFSM->free_size=(last-first);
	newFSM->Occupied=NULL;
	newFSM->Holes=holes;
	return newFSM;
}

/* discards FSM data structure regardless of current status of indices */
void destroyFSM(FSM f){
	struct fsm *fs = (fsm *)f;
	struct run *next;
	next = *fs.Occupied;
}

/* request that 'number' adjoining free locations be marked as in use */
/* returns 0 if this is not possible */
/* returns 1 and sets *first to contain the first index, if it is possible */
int allocate(FSM f, unsigned long number, unsigned long *first){
	unsigned long result;
	f=NULL;
	first=NULL;
	result = number;
	return 0;
}

/* 'number' locations starting at index 'first' are no longer in use */
void deallocate(FSM f, unsigned long first, unsigned long number){
	unsigned long result;
	f=NULL;
	result = first-number;
}


