CFLAGS = -pthread -w -Wall -pedantic -g -c

all: TCPFunct HTTPServ
	rm -f *.o
	rm -f *~

TCPFunct: TCPFunct.o
	gcc TCPFunct.o -o TCPFunct

HTTPServ: HTTPServ.o
	gcc HTTPServ.o -o HTTPServ

TCPFunct.o:
	gcc $(CFLAGS) TCPFunct.c

HTTPServ.o:
	gcc $(CFLAGS) HTTPServ.c

cleanup:
	rm -f *.o
	rm -f *~