#include <pthread.h>
#include <stdlib.h>
#include <stdio.h>
#include "packetdescriptor.h"
#include "TSArray.h"

typedef struct TSArray{
  PacketDescriptor *array;
  pthread_mutex_t lock;
  pthread_cond_t new;
} TSArray;

TSArray * createTSArray(int qty){
  TSArray * TSA = malloc(sizeof(TSArray));
  int i;
  pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
  pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
  if(TSA!=NULL){
    PacketDescriptor * array = malloc(sizeof(PacketDescriptor)*qty);
    if(array==NULL){
      free(TSA);
      return NULL;
    }
    for(i=0; i<qty;i++){
      array[i]=NULL;
    }
    TSA->array=array;
    TSA->lock=mutex;
    TSA->new=cond;
  }
  return TSA;
}

void destroyTSArray(TSArray * TSA){
  pthread_mutex_lock(&TSA->lock);
  free(TSA->array);
  pthread_mutex_unlock(&TSA->lock);
  pthread_mutex_destroy(&TSA->lock);
  free(TSA);
}

void addToTSA(TSArray * TSA, int index, PacketDescriptor PD){
  pthread_mutex_lock(&TSA->lock);
  PacketDescriptor current = TSA->array[index];
  if(current == NULL){ 
    TSA->array[index]=PD;
    pthread_cond_signal(&TSA->new);
  }
  pthread_mutex_unlock(&TSA->lock);
}

PacketDescriptor getFromTSA(TSArray * TSA, int index){
  pthread_mutex_lock(&TSA->lock);
  PacketDescriptor PD = TSA->array[index];
  TSA->array[index] = NULL;
  pthread_mutex_unlock(&TSA->lock);
  return PD;
}

PacketDescriptor getFromTSABlocking(TSArray * TSA, int index){
  PacketDescriptor PD;
  pthread_mutex_lock(&TSA->lock);
  while ((PD = TSA->array[index])==NULL){
    pthread_cond_signal(&TSA->new);
    pthread_cond_wait(&TSA->new,&TSA->lock);
  }
  pthread_mutex_unlock(&TSA->lock);
  return PD;
}
