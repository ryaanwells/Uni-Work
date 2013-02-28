CFLAGS=-W -Wall -pedantic -g -c
OBJECTS=BoundedBuffer.o diagnostics.o fakeapplications.o \
        freepacketdescriptorstore.o generic_queue.o \
        networkdevice.o networkdriver.o packetdescriptor.o \
        packetdescriptorcreator.o testharness.o TSArray.c

demo: $(OBJECTS)
	cc -o demo $(OBJECTS) -lpthread

clean:
	rm -f demo.o demo
	rm -f *~