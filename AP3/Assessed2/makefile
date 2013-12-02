CFLAGS=-W -Wall -g -pedantic -pthread -D_GNU_SOURCE

multiThreadFC: multiThreadFC.o libTSADTS.a
	gcc $(CFLAGS) -o multiThreadFC multiThreadFC.o libTSADTS.a

libADTS.a: iterator.o linkedlist.o treeset.o re.o
	rm -f libADTS.a
	ar r libADTS.a iterator.o linkedlist.o treeset.o re.o
	ranlib libADTS.a

libTSADTS.a: iterator.o tslinkedlist.o tstreeset.o linkedlist.o treeset.o re.o
	rm -f libTSADTS.a
	ar r libTSADTS.a iterator.o tslinkedlist.o tstreeset.o linkedlist.o treeset.o re.o
	ranlib libTSADTS.a

tslinkedlist.o: tslinkedlist.c tslinkedlist.h linkedlist.h iterator.h
tstreeset.o: tstreeset.c tstreeset.h treeset.h iterator.h
iterator.o: iterator.c iterator.h
linkedlist.o: linkedlist.c linkedlist.h iterator.h
treeset.o: treeset.c treeset.h iterator.h

re.o: re.c re.h
fileCrawler.o: fileCrawler.c iterator.h linkedlist.h treeset.h re.h
multiThreadFC.o: multiThreadFC.c iterator.h tslinkedlist.h tstreeset.h linkedlist.h treeset.h re.h
