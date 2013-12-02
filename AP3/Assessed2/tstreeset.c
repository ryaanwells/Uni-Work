#include <stdlib.h>
#include <pthread.h>
#include <stdio.h>
#include "tstreeset.h"
#include "treeset.h"
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



#define LOCK(ts)&((ts)->lock)

struct tstreeset{
  TreeSet *ts;
  pthread_mutex_t lock;
};

TSTreeSet *tstreeset_create(int (*cmpFunction)(void *, void *)) {
  TSTreeSet *tsts = (TSTreeSet *) malloc(sizeof(TSTreeSet));
  if(tsts != NULL){
    TreeSet *ts = ts_create(cmpFunction);
    if(ts == NULL){
      free(tsts);
    }else{
      pthread_mutexattr_t ma;
      pthread_mutexattr_init(&ma);
      pthread_mutexattr_settype(&ma,PTHREAD_MUTEX_RECURSIVE_NP);
      tsts->ts = ts;
      pthread_mutex_init(LOCK(tsts),&ma);
      pthread_mutexattr_destroy(&ma);
    }
  }
  return tsts;
}

void tstreeset_lock(TSTreeSet *tsts){
  pthread_mutex_lock(LOCK(tsts));
}

void tstreeset_unlock(TSTreeSet *tsts){
  pthread_mutex_unlock(LOCK(tsts));
}

void tstreeset_destroy(TSTreeSet *tsts, void (*userFunction)(void *element)){
  pthread_mutex_lock(LOCK(tsts));
  ts_destroy(tsts->ts,userFunction);
  pthread_mutex_unlock(LOCK(tsts));
  pthread_mutex_destroy(LOCK(tsts));
  free(tsts);
}

int tstreeset_add(TSTreeSet *tsts, void *element){
  int result;
  pthread_mutex_lock(LOCK(tsts));
  result = ts_add(tsts->ts,element);
  pthread_mutex_unlock(LOCK(tsts));
  return result;
}

Iterator *tsts_it_create(TSTreeSet *tsts){
  Iterator *it;
  TreeSet *ts = tsts->ts;
  pthread_mutex_lock(LOCK(tsts));
  it = (ts_it_create(ts));
  pthread_mutex_unlock(LOCK(tsts));
  if (it == NULL)  fprintf(stderr, "failed to create iterator");
  return it;
}
