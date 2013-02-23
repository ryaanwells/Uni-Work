#include <stdlib.h>
#include <string.h>
#include <errno.h>

typedef struct Element{
  int content;
  struct Element * next;
} Element;

typedef struct Stack {
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

int stackAdd(Stack * S,int e){
  Element * elem = malloc(sizeof(Element));
  if(elem==NULL) return -1;
  elem->content = e;
  elem->next = S->head;
  S->head = elem;
  S->count++;
  return 1;
}

int stackRemove(Stack * S){
  if(S->count==0){
    return -1;
  }
  Element * elem = S->head;
  int cont = elem->content;
  S->head=S->head->next;
  S->count--;
  return cont;
}

int stackClear(Stack *S){
  Element * elem = NULL;
  if(S==NULL) return 1;
  while(S->head != NULL){
    elem = S->head;
    S->head=S->head->next;
    free(elem);
  }
  S->count = 0;
  return 1;
}

void destroyStack(Stack *S){
  stackClear(S);
  free(S);
}
