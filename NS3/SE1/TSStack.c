#include <stdlib.h>
#include <string.h>
#include <errno.h>

typedef struct Element{
  void * content;
  struct element * next;
} element;

typedef struct {
  int count;
  struct element * head;
} Stack;

Stack * createStack(){
  Stack * S = malloc(sizeof(Stack));
  if(S!=NULL){
    S->count = 0;
    S->head = NULL;
  }
  return S;
}

int add(Stack * S,void * e){
  element * elem = malloc(sizeof(element));
  if(elem==NULL) return -1;
  elem->content = e;
  elem->next = S->head;
  S->count++;
  return 1;
}

int remove(Stack * S, void * e){
  if(S->count==0) return -1;
  struct element * elem = S->head;
  e = elem->content;
  S->head=S->head->next;
  return 1;
}
