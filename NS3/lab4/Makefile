CFLAGS = -W -Wall -g -c

all: udp_hello_server udp_hello_client

udp_hello_server: udp_hello_server.o
	gcc udp_hello_server.o -o udp_hello_server

udp_hello_client: udp_hello_client.o
	gcc udp_hello_client.o -o udp_hello_client

udp_hello_client.o: udp_hello_client.c
	gcc $(CFLAGS) udp_hello_client.c

udp_hello_server.o: udp_hello_server.c
	gcc $(CFLAGS) udp_hello_server.c

clean:
	rm -f *.o
	rm -f *~