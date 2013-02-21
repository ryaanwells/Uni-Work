#include <stdlib.h>
#include <string.h>
#include <errno.h>

typedef struct Element{
  void * content;
  struct Element * next;
} Element;

typedef struct {
  int count;
  struct Element * head;
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
  Element * elem = malloc(sizeof(Element));
  if(elem==NULL) return -1;
  elem->content = e;
  elem->next = S->head;
  S->count++;
  return 1;
}

int remove(Stack * S, void * e){
  if(S->count==0){
    if (e!=NULL) e=NULL;
    return -1;
  }
  Element * elem = S->head;
  e = elem->content;
  S->head=S->head->next;
  free(elem);
  S->count--;
  return 1;
}

int clear(Stack *S){
  if(S==NULL) return 1;
  
  return 0;
}
