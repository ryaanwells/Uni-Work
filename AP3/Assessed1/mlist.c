#ifndef _MLIST_H_
#define _MLIST_H_

#include "mentry.h"
#define STARTSIZE 10000

typedef struct mlist{
  long size;
  MListHead *buckets;
} MList;

typedef struct mlisthead{
  struct MListNode node;
  int entryTotal = 0;
  int isFull = 0;
} MListHead;

typedef struct mlistnode{
  struct MEntry ment = NULL;
  MEntry *next = NULL;
} MListNode;

extern int ml_verbose;		/* if true, prints diagnostics on stderr */

/* ml_create - created a new mailing list */
MList *ml_create(void){
  return ml_create_size(STARTSIZE);
}

MList *ml_create_size(int size){
  MList *ml = (MList *) malloc(sizeof(MList)); 
  if (ml == NULL) return NULL;
  MListHead bkts[size];
  int i = 0;
  for(i;i<size;i++){
    bkts[i] = malloc(sizeof(MListNode));
    if (bkts[i] == NULL){
      free(ml);
      int j=0;
      for(j;j<i:j++){
	free(bkts[j]);
      }
      return NULL:
    }
  }
  ml->size = size;
  *ml->buckets = bkts;
}

/* ml_add - adds a new MEntry to the list;
 * returns 1 if successful, 0 if error (malloc)
 * returns 1 if it is a duplicate */
int ml_add(MList **ml, MEntry *me){
  return 0;
}

/* ml_lookup - looks for MEntry in the list, returns matching entry or NULL */
MEntry *ml_lookup(MList *ml, MEntry *me){
  return NULL;
}

/* ml_destroy - destroy the mailing list */
void ml_destroy(MList *ml){
  return;
}

#endif /* _MLIST_H_ */
