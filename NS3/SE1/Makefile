CFLAGS = -lpthread -W -Wall -g -c

all: Stack.o ThreadPool.o HTTPServ HTTPClient

HTTPServ: HTTPServ.o
	gcc  HTTPServ.o Stack.o -lpthread -o HTTPServ

HTTPClient: HTTPClient.o
	gcc HTTPClient.o -o HTTPClient

Stack.o:
	gcc $(CFLAGS) Stack.c

ThreadPool.o:
	gcc $(CFLAGS) ThreadPool.c

HTTPServ.o:
	gcc $(CFLAGS) HTTPServ.c

HTTPClient.o:
	gcc $(CFLAGS) HTTPClient.c

clean:
	rm -f *.o
	rm -f *~