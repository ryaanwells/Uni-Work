CFLAGS = -pthread -W -Wall -g -c

all: TSStack.o HTTPServ HTTPClient

HTTPServ: HTTPServ.o
	gcc HTTPServ.o -o HTTPServ

HTTPClient: HTTPClient.o
	gcc HTTPClient.o -o HTTPClient

TSStack.o:
	gcc $(CFLAGS) TSStack.c

HTTPServ.o:
	gcc $(CFLAGS) HTTPServ.c

HTTPClient.o:
	gcc $(CFLAGS) HTTPClient.c

clean:
	rm -f *.o
	rm -f *~