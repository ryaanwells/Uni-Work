#include <stdlib.h>
#include <stdio.h>
#include "BackingStorePager.h"
#include "PhysicalMemory.h"
#include "diagnostics.h"
#include "SystemParameters.h"
#include "VM.h"

typedef struct frame{
	unsigned long pid;
	unsigned long page;
}Frame;

/* called once at start, used to initialise datastructures */
void  initialise_VM(void){
	int maxpages = get_PM_size();
	int i;
	Frame *allFrames[maxpages]; 
	for(i=0;i<maxpages;i++){
		allFrames[i]=malloc(sizeof(Frame));
	}
	fprintf(stderr,"%d\n",maxpages);
	
	exit(1);
	return;
}

/* takes a ProcessId, a VM address and a datum */
void  VMWrite(unsigned long pid, unsigned long vmaddr, unsigned long datum){
	return;
}

/* takes a ProcessId and a VM address, returns the datum */
unsigned long  VMRead(unsigned long pid, unsigned long vmaddr){
	return 0L;
}

