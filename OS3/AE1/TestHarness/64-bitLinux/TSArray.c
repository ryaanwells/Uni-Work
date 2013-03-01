#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include "packetdescriptor.h"

typedef struct TSArray{
	PacketDescriptor *array;
} TSArray;

TSArray * createTSArray(int qty){
	TSArray * TSA = malloc(sizeof(TSArray));
	if(TSA!=NULL){
		PacketDescriptor * array = malloc(sizeof(PacketDescriptor)*qty);
		if(array==NULL){
			free(TSA);
			return NULL;
		}
		int i;
		for(i=0; i<qty;i++){
			array[i]=NULL;
		}
		TSA->array=array;
	}
	return TSA;
}

void destroyTSArray(TSArray * TSA){
	free(TSA->array);
	free(TSA);
}

void addToTSA(TSArray * TSA, int index, PacketDescriptor PD){
	PacketDescriptor current = TSA->array[index];
	if(current == NULL) TSA->array[index]=PD;
}

PacketDescriptor getFromTSA(TSArray * TSA, int index){
	PacketDescriptor PD = TSA->array[index];
	TSA->array[index] = NULL;
	return PD;
}
