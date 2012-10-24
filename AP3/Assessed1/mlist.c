#ifndef _MLIST_H_
#define _MLIST_H_

#include "mentry.h"
#define  STARTSIZE 10000

typedef struct mlist{
	unsigned int size;
	struct MListHead *buckets;
} MList;

typedef struct mlisthead{
	struct MListNode *node;
	int entryTotal;
	int isFull;
} MListHead;

typedef struct mlistnode{
  struct MEntry *ment;
  MEntry *next;
} MListNode;

extern int ml_verbose;		/* if true, prints diagnostics on stderr */

/* ml_create - created a new mailing list */
MList *ml_create(void){
	return ml_create_size((unsigned int) STARTSIZE);
}

MList *ml_create_size(unsigned int size){
	MList *ml = (MList *) malloc(sizeof(MList)); 
	if (ml == NULL) return NULL;
	MListHead *bkts = malloc(sizeof(MListHead)*size);
	if (bkts = NULL) return NULL;
	ml->size = size;
	ml->buckets = &bkts;
	return &ml;
}

/* ml_add - adds a new MEntry to the list;
 * returns 1 if successful, 0 if error (malloc)
 * returns 1 if it is a duplicate */
int ml_add(MList **ml, MEntry *me){
	MList *mlpointer = *ml;
	if (ml_lookup(mlpointer,me) != NULL){ return 1;}
	unsigned int mlsize = mlpointer->size;
	unsigned long hval;
	hval = (me,mlsize);
	MListHead *buckets = &mlpointer->buckets;
	MListHead *location = &buckets[hval];
	MListNode *newnode = malloc(sizeof(MListNode));
	if (newnode == NULL){ return 0;}
	*newnode->ment = me;
	if (location->node == NULL){
		location->node = &newnode;
		location->entryTotal = 1;
		location->isFull = 0;
		return 1;
	}
	MListNode *npoint = location->node;
	while (npoint.next != NULL){
		npoint = npoint->next;
	}
	npoint->next = me;
	location->entryTotal++;
	return 1;
}

/* ml_lookup - looks for MEntry in the list, returns matching entry or NULL */
MEntry *ml_lookup(MList *ml, MEntry *me){
	MListHead *bkts = ml->buckets;
	unsigned int total = ml->size;
	MListNode *ptr;
	MEntry *other;
	unsigned int i = 0;
	for (i;i<total;i++){
		ptr = *bkts[i]->node;
		if (ptr!=NULL){
			other = &ptr->ment;
			while(other!=NULL){
				if (me_compare(me, other) == 0){
					return other;
				}
				other = &other->next;
			}
		}
	}
	return NULL;
}

/* ml_destroy - destroy the mailing list */
void ml_destroy(MList *ml){
  return;
}

#endif /* _MLIST_H_ */
