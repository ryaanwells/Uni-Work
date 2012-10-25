#ifndef _MLIST_H_
#define _MLIST_H_

#include "mentry.h"
#include "stdlib.h"
#define  STARTSIZE 10000

typedef struct mlist{
	unsigned int size;
	struct mlisthead *buckets;
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
	MListHead *bkts =(MListHead *) ml->buckets;
	unsigned int total = ml->size;
	MListNode *ptr;
	MListHead *hptr;
	MEntry *other;
	unsigned int i = 0;
	for (i;i<total;i++){
	
		
		ptr =(MListNode *) bkts[i].node;
		while (ptr!= NULL){
			if (me_compare(me,(MEntry *) ptr->ment) == 0){
				return ((MEntry *)ptr->ment);
			}
			ptr =(MListNode *) ptr->next;
		}
	}
	return NULL;
}

static MList *ml_create_size(unsigned int size){
	MList *ml =(MList *) malloc(sizeof(MList)); 
	if (ml == NULL) return NULL;
	MListHead *bkts =(MListHead *) malloc(sizeof(MListHead)*size);
	if (bkts = NULL) return NULL;
	int i = 0;
	MListHead *ptr = bkts;
	MListHead *mlnew;
	while(i<size){
		mlnew = malloc(sizeof(MListHead));
		mlnew->node = NULL;
		mlnew->entryTotal = 0;
		mlnew->isFull = 0;
		*ptr = *mlnew;
		i++;
		ptr++;
	}
	ml->size = size;
	ml->buckets = bkts;
	return ml;
}

/* ml_create - created a new mailing list */
MList *ml_create(void){
	return ml_create_size((unsigned int) STARTSIZE);
}



/* ml_add - adds a new MEntry to the list;
 * returns 1 if successful, 0 if error (malloc)
 * returns 1 if it is a duplicate */
int ml_add(MList **ml, MEntry *me){
	MList *mlpointer = *ml;
	MEntry *MEpoint = ml_lookup(mlpointer,me);
	if (MEpoint != NULL){ return 1;}
	unsigned int mlsize = mlpointer->size;
	unsigned long hval;
	hval = (me,mlsize);
	MListHead *buckets =(MListHead *) mlpointer->buckets;
	MListHead *location = &buckets[hval];
	MListNode *newnode = malloc(sizeof(MListNode));
	if (newnode == NULL){ return 0;}
	newnode->ment = me;
	newnode->next = NULL;
	if (location->node == NULL){
		location->node = newnode;
		location->entryTotal = 1;
		location->isFull = 0;
		return 1;
	}
	MListNode *npoint = location->node;
	while (npoint->next != NULL){
		npoint = npoint->next;
	}
	npoint->next = newnode;
	location->entryTotal++;
	return 1;
}



/* ml_destroy - destroy the mailing list */
void ml_destroy(MList *ml){
	return;
}

#endif /* _MLIST_H_ */
