all: hello_server hello_client

hello_server: hello_server.o
	gcc hello_server.o -o hello_server

hello_client: hello_client.o
	gcc hello_client.o -o hello_client

hello_server.o: hello_server.c
	gcc -W -Wall -g -c hello_server.c

hello_client.o: hello_client.c
	gcc -W -Wall -g -c hello_client.c