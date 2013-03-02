#ifndef TSARRAY_H
#define TSARRAY_H

#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include "packetdescriptor.h"

typedef struct TSArray TSArray;

TSArray * createTSArray(int qty);
void destroyTSArray(TSArray * TSA);
void addToTSA(TSArray * TSA, int index, PacketDescriptor PD);
PacketDescriptor getFromTSA(TSArray * TSA, int index);
PacketDescriptor getFromTSABlocking(TSArray * TSA, int index);
#endif
