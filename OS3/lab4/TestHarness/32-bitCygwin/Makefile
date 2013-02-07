CFLAGS=-W -Wall -pedantic
OBJS=BackingStoreAndPhysicalMemoryEmulator.o diagnostics.o

all: testSimple.exe testVM.exe

testSimple.exe: testVM.o simpleVM.o ${OBJS}
	gcc -Wall -g -o testSimple.exe testVM.o simpleVM.o ${OBJS}

testVM.exe: testVM.o VM.o ${OBJS}
	gcc -Wall -g -o testVM.exe testVM.o VM.o ${OBJS}

clean:
	rm -f *.exe *.o
