#include<stdio.h>
#include<sys/time.h>
int main(int argc, char *argv[]){
	int i, j, nthread;
	unsigned long block, limit;
	block = 1;
	limit = 100;
	nthread = 1;
	for (i = 1; i < argc; ) {
		if ((j = i + 1) == argc) {
			fprintf(stderr,"usage: ./mtprimes [-b block] [-l limit] [-t nthread]\n");
			return -1;
		}
		if (strcmp(argv[i], "-b") == 0){
			sscanf(argv[j], "%lu", &block);
		}
		else if (strcmp(argv[i], "-l") == 0){
			sscanf(argv[j], "%lu", &limit);
		}
		else if (strcmp(argv[i], "-t") == 0){
			sscanf(argv[j], "%d", &nthread);
		}
		else {
			fprintf(stderr, "Unknown flag: %s %s\n", argv[i], argv[j]);
			return -1;
		}
		i = j + 1;
	}
	
	/* GENERATION AND STARTING THREADS HERE */

	struct timeval start, stop;
	unsigned long msec;
	double msperprime;
	gettimeofday(&start,NULL);
	gettimeofday(&stop,NULL);
	if(stop.tv_usec < start.tv_usec){
		stop.tv_usec += 1000000;
		stop.tv_sec--;
	}
	msec = 1000 * (stop.tv_sec - start.tv_sec) +
		(stop.tv_usec - start.tv_usec) / 1000;
	msperprime= (double) msec / (double) limit;
	fprintf(stderr, "%lu primes computed in %lu.%03lu seconds, %.3f ms/prime\n",
			limit, msec/1000, msec%1000, msperprime);

}
