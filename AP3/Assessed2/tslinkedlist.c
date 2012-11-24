
#include "tslinkedlist.h"
#include "linkedlist.h"
/*
 * Disclaimer for immediately previous file.
 *
 * Copyright (c) 2012, Court of the University of Glasgow
 * All rights reserved.
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * - Redistributions of source code must retain the above copyright notice,
 *   this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * - Neither the name of the University of Glasgow nor the names of its
 *   contributors may be used to endorse or promote products derived from this
 *   software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
#include <stdlib.h>
#include <pthread.h>
 
#define LOCK(ll)&((ll)->lock)

struct tslinkedlist{
  LinkedList *ll;
  pthread_mutex_t lock;
};

TSLinkedList *tslinkedlist_create(void){
  TSLinkedList *tsll = (TSLinkedList *) malloc(sizeof(TSLinkedList));
  if (tsll != NULL){
    LinkedList *ll = ll_create();
    if (ll == NULL){
      free(tsll);
      tsll = NULL;
    }else{
      pthread_mutexattr_t ma;
      pthread_mutexattr_init(&ma);
      pthread_mutexattr_settype(&ma,PTHREAD_MUTEX_RECURSIVE_NP);
      tsll->ll = ll;
      pthread_mutex_init(LOCK(tsll),&ma);
      pthread_mutexattr_destroy(&ma);
    }
  }
  return tsll;
}

void tsll_destroy(TSLinkedList *tsll, void (*userFunction)(void *element)){
  pthread_mutex_lock(LOCK(tsll));
  ll_destroy(tsll->ll,userFunction);
  pthread_mutex_unlock(LOCK(tsll));
  pthread_mutex_destroy(LOCK(tsll));
  free(tsll);
}

void tslinkedlist_lock(TSLinkedList *tsll){
  pthread_mutex_lock(LOCK(tsll));
}

void tslinkedlist_unlock(TSLinkedList *tsll){
  pthread_mutex_unlock(LOCK(tsll));
}

int tslinkedlist_put(TSLinkedList *tsll, void *element){
  int result;
  pthread_mutex_lock(LOCK(tsll));
  result = ll_addLast(tsll->ll,element);
  pthread_mutex_unlock(LOCK(tsll));
  return result;
}

int tslinkedlist_take(TSLinkedList *tsll, void **element){
  int result = 0;
  pthread_mutex_lock(LOCK(tsll));
  result = ll_removeFirst(tsll->ll,element);
  pthread_mutex_unlock(LOCK(tsll));
  return result;
}
