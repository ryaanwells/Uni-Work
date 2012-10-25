all: test

test: finddupl.o mentry.o mlist.o 
	gcc -o test finddupl.o mentry.o mlist.o

finddupl.o: finddupl.c
	gcc -g -w -Wall -c finddupl.c

mentry.o: mentry.c
	gcc -g -w -Wall -c mentry.c

mlistLL.o: mlist.c
	gcc -g -w -Wall -c mlist.c