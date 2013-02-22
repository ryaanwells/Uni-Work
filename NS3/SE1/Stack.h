#ifndef STACK_H
#define STACK_H

typedef void * Stack;

Stack * createStack();
int addElem(Stack * S, void * e);
int removeElem(Stack * S, void * e);
int clearAll(Stack * S);

#endif
