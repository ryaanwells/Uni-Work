CFLAGS = -W -Wall -Wextra -g -c

all: chirp chirp_listener

chirp: chirp.o
	gcc chirp.o -o chirp

chirp.o: chirp.c
	gcc $(CFLAGS) chirp.c

chirp_listener: chirp_listener.o
	gcc chirp_listener.o -o chirp_listener

chirp_listener.o: chirp_listener.c
	gcc $(CFLAGS) chirp_listener.c

clean:
	rm -f *.o
	rm -f *~