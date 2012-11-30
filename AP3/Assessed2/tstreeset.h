#ifndef _TSTREESET_H_
#define _TSTREESET_H_

#include "iterator.h"

/*
 *interface definition for generic threadsafe
 *treeset implementation
 */

typedef struct tstreeset TSTreeSet; /*opaque type definition */

TSTreeSet *tstreeset_create(int (*cmpFunction)(void *, void *));
void tstreeset_lock(TSTreeSet *tsts);
void tstreeset_unlock(TSTreeSet *tsts);
void tstreeset_destroy(TSTreeSet *tsts, void (*userFunction)(void *element));
int tstreeset_add(TSTreeSet *tsts, void *element);
Iterator *tsts_it_create(TSTreeSet *tsts);

#endif /*_TSTREESET_H */
