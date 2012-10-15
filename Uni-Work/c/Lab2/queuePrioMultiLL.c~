#include "queue.h"
#include <stdlib.h>
#include <string.h>
#include <stdio.h>

/*
 * implementation of a Priority queue using a linked list
 */

struct q_element {
  struct q_element *next;
  void *value;
  void *prio;
  
};

struct queue {
  struct q_element *head;
  struct q_element *tail;
};

/*
 * create a Queue that holds Items
 * returns NULL if the create call failed (malloc failure)
 */
Queue *q_create(void) {
  struct queue *p;
  
  if ((p = (struct queue *)malloc(sizeof(struct queue))) != NULL) {
    p->head = NULL;
    p->tail = NULL;
  }
  return p;
}

/*
 * add Item to the Queue; 3rd arg is priority in MIN_PRIO..MAX_PRIO;
 * return 1/0 if successful/not-successful
 */
int q_add(Queue *q, Item i, int prio) {
  struct q_element *p,*qp,*qc;
  int qval;
  int* itemp = malloc(sizeof(int));
  *itemp = prio;
  p = (struct q_element *)malloc(sizeof(struct q_element));
  p->value = i;
  p->prio = itemp;
  if (p != NULL) {
    qp = q->head;
    qc = q->head;
    if(qp==NULL){
      q->head = p;
      q->tail = p;
      p->next = NULL;
      return 1;
    }
    itemp = qp->prio;
    while ((*itemp)<=prio){
      qc = qp;
      qp = qp->next;
      if (qp == NULL){
	qc->next = p;
	q->tail = p;
	p->next = NULL;
	return 1;
      }
      itemp = qp->prio;
    }
    if (qp == q->head){
      p->next = q->head;
      q->head = p;
      return 1;
    }
    qc->next = p;
    p->next = qp;
    return 1;
  }
  return 0;
}

/*
 * remove next Item from queue; returns NULL if queue is empty
 */
Item q_remove(Queue *q) {
	struct q_element *p;
	Item i;

	if (q->head == NULL)
		return NULL;
	p = q->head;
	q->head = p->next;
	if (q->head == NULL)
		q->tail = NULL;
	i = p->value;
	free(p);
	return i;
}
