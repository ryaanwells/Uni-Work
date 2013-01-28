CFLAGS = -pthread -w -Wall -pedantic -g -c

all: TCPFunct HTTPServ HTTPClient
	rm -f *.o
	rm -f *~

TCPFunct: TCPFunct.o
	gcc TCPFunct.o -o TCPFunct

HTTPServ: HTTPServ.o
	gcc HTTPServ.o -o HTTPServ

HTTPClient: HTTPClient.o
	gcc HTTPClient.o -o HTTPClient

TCPFunct.o:
	gcc $(CFLAGS) TCPFunct.c

HTTPServ.o:
	gcc $(CFLAGS) HTTPServ.c

HTTPClient.o:
	gcc $(CFLAGS) HTTPClient.c

cleanup:
	rm -f *.o
	rm -f *~