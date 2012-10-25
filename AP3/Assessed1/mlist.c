#ifndef _MLIST_H_
#define _MLIST_H_

#include "mentry.h"
#include "stdlib.h"
#define  STARTSIZE 10000

typedef struct mlist{
	unsigned int size;
	struct mlisthead **buckets;
} MList;

typedef struct mlisthead{
	struct mlistnode *node;
	int entryTotal;
	int isFull;
} MListHead;

typedef struct mlistnode{
	struct mentry *ment;
	struct mlistnode *next;
} MListNode;

int ml_verbose = 0;/* if true, prints diagnostics on stderr */


/* ml_lookup - looks for MEntry in the list, returns matching entry or NULL */
MEntry *ml_lookup(MList *ml, MEntry *me){
  MListHead *bkts =(MListHead *) *(ml->buckets);
  unsigned int total = ml->size;
  MListNode *ptr;
  MListHead *hptr;
  MEntry *other;
  unsigned int i = 0;
  for (i;i<total;i++){
    if(ml->buckets[i]->entryTotal != 0){
      ptr = ml->buckets[i]->node;
      while (ptr!= NULL){
	other = ptr->ment;
	if (other != NULL && me_compare(me,other) == 0){
	  return ((MEntry *)ptr->ment);
	}
	ptr =(MListNode *) ptr->next;
      }
    }
  }
  return NULL;
}

MList *ml_create_size(unsigned int sz){
	int sizee = sz;
	MList *ml =(MList *) malloc(sizeof(MList)); 
	if (ml == NULL) return NULL;
	ml->buckets = (MListHead **) malloc(sizee*sizeof(MListHead *));
	if (ml->buckets == NULL){
	  free(ml);
	  return NULL;
	}
	ml->size = sizee;
	int i = 0;
	for(i;i<sizee;i++){
		ml->buckets[i] =(MListHead *)malloc(sizeof(MListHead));
		if(ml->buckets[i] == NULL){
		  i=i--;
		  while(i>=0){
		    free(ml->buckets[i]->node);
		    free(ml->buckets[i]);
		    i--;
		  }
		  free(ml->buckets);
		  free(ml);
		  return NULL;
		}
		ml->buckets[i]->node =(MListNode *) malloc(sizeof(MListNode));
		if (ml->buckets[i]->node == NULL){
		  free(ml->buckets[i]);
		  i=i--;
		  while(i>=0){
		    free(ml->buckets[i]->node);
		    free(ml->buckets[i]);
		  }
		}
		ml->buckets[i]->node->ment = NULL;
		ml->buckets[i]->node->next = NULL;
		ml->buckets[i]->entryTotal = 0;
		ml->buckets[i]->isFull = 0;
	}
	return ml;
}

/* ml_create - created a new mailing list */
MList *ml_create(void){
	return ml_create_size(STARTSIZE);
}


/* ml_add - adds a new MEntry to the list;
 * returns 1 if successful, 0 if error (malloc)
 * returns 1 if it is a duplicate */
int ml_add(MList **ml, MEntry *me){
  MList *mlpointer = *ml;
  MEntry *MEpoint = ml_lookup(mlpointer,me);
  if (MEpoint != NULL){ return 1;}
  unsigned long mlsize =(unsigned long) mlpointer->size;
  unsigned long hval;
  hval = me_hash(me,mlsize);
  MListHead *buckets =(MListHead *) mlpointer->buckets[hval];
  MListNode *newnode = malloc(sizeof(MListNode));
  if (newnode == NULL){ return 0;}
  newnode->ment = me;
  newnode->next = NULL;
  if (buckets->node == NULL){
    buckets->node = newnode;
    buckets->entryTotal = 1;
    buckets->isFull = 0;
    return 1;
  }
  MListNode *npoint = buckets->node;
  while (npoint->next != NULL){
    npoint = npoint->next;
  }
  npoint->next = newnode;
  buckets->entryTotal++;
  return 1;
}



/* ml_destroy - destroy the mailing list */
void ml_destroy(MList *ml){
  unsigned int sz = ml->size;
  int i = 0;
  MListHead *hp;
  MListNode *np;
  MListNode *nnp;
  for(i;i<sz;i++){
    np = ml->buckets[i]->node;
    while (np!=NULL){
      me_destroy(np->ment);
      nnp = np->next;
      free(np);
      np = nnp;
    }
  free(ml->buckets[i]);
  }
  free(ml->buckets);
  free(ml);
  return;
}

#endif /* _MLIST_H_ */
