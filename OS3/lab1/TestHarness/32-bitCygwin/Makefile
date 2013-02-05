CFLAGS=-W -Wall -pedantic
OBJS= diagnostics.o generic_collection.o generic_queue.o tcb_collection.o TCB.o testscheduler.o

scheduler.exe: scheduler.o ${OBJS}
	gcc -Wall -g -o scheduler.exe scheduler.o ${OBJS}

clean:
	rm -f scheduler.exe *.o
