#ifndef STACK_H
#define STACK_H

typedef struct Stack Stack;

Stack * createStack();
int stackAdd(Stack * S, int e);
int stackRemove(Stack * S);
int stackClear(Stack * S);
void destroyStack(Stack *S);

#endif
