CFLAGS = -lpthread -W -Wall -g -c

all: Stack.o web-server

web-server: web-server.o Stack.o
	gcc  web-server.o Stack.o -lpthread -o web-server

web-server.o: web-server.c
	gcc $(CFLAGS) web-server.c

Stack.o: Stack.c
	gcc $(CFLAGS) Stack.c

clean:
	rm -f *.o
	rm -f *~