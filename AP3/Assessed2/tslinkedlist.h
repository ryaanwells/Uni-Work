#ifndef _TSLINKEDLIST_H_
#define _TSLINKEDLIST_H_
#include <pthread.h>
#include "linkedlist.h"
/*
 *interface definition for generic threadsafe
 *linked list implementation
 */



typedef struct tslinkedlist TSLinkedList; /*opaque type definition */

TSLinkedList *tslinkedlist_create(void);
void tsll_destroy(TSLinkedList *tsll, void (*userFunction)(void *element));
void tslinkedlist_lock(TSLinkedList *tsll);
void tslinkedlist_unlock(TSLinkedList *tsll);
int tslinkedlist_put(TSLinkedList *tsll, void *element);
int tslinkedlist_take(TSLinkedList *tsll, void **element);

#endif /*_TSLINKEDLIST_H_*/
