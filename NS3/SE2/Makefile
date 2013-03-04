CFLAGS = -W -Wall -Wextra -g -c

all: chirp_listener chirp basic_listener black_chirp

chirp: chirp.o
	gcc chirp.o -o chirp

chirp.o: chirp.c
	gcc $(CFLAGS) chirp.c

black_chirp: black_chirp.o
	gcc black_chirp.o -o black_chirp

black_chirp.o: black_chirp.c
	gcc $(CFLAGS) black_chirp.c

chirp_listener: chirp_listener.o
	gcc chirp_listener.o -o chirp_listener

chirp_listener.o: chirp_listener.c
	gcc $(CFLAGS) chirp_listener.c

basic_listener: basic_listener.o
	gcc basic_listener.o -o basic_listener

basic_listener.o: basic_listener.c
	gcc $(CFLAGS) basic_listener.c

clean:
	rm -f *.o
	rm *~