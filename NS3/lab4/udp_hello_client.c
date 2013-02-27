#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <stdlib.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

int main(int argc, char* argv[]){
	
	if (argc != 2){
		fprintf(stderr,"Usage: ./udp_hello_client host\n");
		return -1;
	}

	int fd;
	char resp[] = "HELLO, WORLD!";
	int resplen = strlen(resp);
	struct addrinfo *ai, hints;
	int i;
	i=1;
	memset(&hints, 0, sizeof(hints));
	hints.ai_family = PF_UNSPEC;;
	hints.ai_socktype = SOCK_DGRAM;
	if((i = getaddrinfo(argv[1], "5003", &hints, &ai)) !=0){
		fprintf(stderr, "Unable to lookup IP: %s\n", gai_strerror(i));
		exit(1);
	}
	if((fd = socket(ai->ai_family,ai->ai_socktype,ai->ai_protocol))==-1){
		perror("Unable to create socket\n");
		return -1;
	}
	if(sendto(fd,resp,resplen,0,ai->ai_addr,ai->ai_addrlen)==-1){
		perror("Unable to send packet.\n");
		return -1;
	}
	close(fd);
	return 1;
}
