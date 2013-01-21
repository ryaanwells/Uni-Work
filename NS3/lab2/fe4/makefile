CFLAGS = -pthread -w -Wall -pedantic -g  -c 

all: dt_server d_client t_client t_dt_server
	rm *.o
	rm *~

t_dt_server: t_dt_server.o
	gcc t_dt_server.o -pthread -o t_dt_server

dt_server: dt_server.o
	gcc dt_server.o -o dt_server

d_client: d_client.o
	gcc d_client.o -o d_client

t_client: t_client.o
	gcc t_client.o -o t_client

t_dt_server.o:
	gcc $(CFLAGS) t_dt_server.c

dt_server.o: dt_server.c
	gcc $(CFLAGS) dt_server.c

d_client.o: d_client.c
	gcc $(CFLAGS) d_client.c

t_client.o: t_client.c
	gcc $(CFLAGS) t_client.c

cleanup: *.o
	rm -rf *.o dt_server d_client t_client
	rm -rf *~