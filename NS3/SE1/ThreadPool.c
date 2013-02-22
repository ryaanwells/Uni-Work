
#include <pthread.h>
#include <stdlib.h>
#include "Stack.h"

typedef struct ThreadPool{
	int freeThreads;
	pthread_t * threads;
	Stack * stack;
} ThreadPool;

ThreadPool * init(int threads){
	ThreadPool * tp = malloc(sizeof(ThreadPool));
	if(tp!=NULL){
		Stack * s = createStack();
		if(s==NULL){
			free(tp);
			return NULL;
		}
		pthread_t * pt = malloc(threads*sizeof(pthread_t));
		if(pt==NULL){
			free(s);
			free(tp);
			return NULL;
		}
		tp->stack = s;
		tp->freeThreads = threads;
		tp->threads = pt;
	}
	return tp;
}
