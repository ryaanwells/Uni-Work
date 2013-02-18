#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>

#define BUFLEN 1500

int main(int argc, char* argv[])
{
	
	if(argc !=2 ){
		fprintf(stdout,"%s\n","Usage: ./HTTPClient address");
		return -1;
	}
	int fd;
	char buf[BUFLEN];
	char resp[] = "GET /index.html HTTP/1.1\r\nHost: Javert\r\n\r\n";
	int resplen = strlen(resp);
	ssize_t rcount;
	struct addrinfo hints, *ai, *ai0;
	int i, j;
	i=1;
	memset(&hints, 0, sizeof(hints));
	hints.ai_family = PF_UNSPEC;
	hints.ai_socktype = SOCK_STREAM;
	if((i = getaddrinfo(argv[1], "8080", &hints, &ai0)) !=0){
		fprintf(stderr, "%s%s\n","Unable to lookup IP: ", gai_strerror(i));
		exit(1);
	}
	for(ai = ai0; ai != NULL; ai = ai->ai_next){
		if((fd = socket(ai->ai_family,ai->ai_socktype,ai->ai_protocol))==-1){
			//deal with no socket
			fprintf(stderr,"%s\n","Socket not connected");
			fprintf(stderr,"%i\n",errno);
			continue;
		}
		
		if(connect(fd, ai->ai_addr, ai->ai_addrlen) == -1){
			fprintf(stderr,"%s\n", "Unable to connect");
			close(fd);
			continue;
		}
		
		break;
	}
	
	if(ai == NULL){
		fprintf(stderr,"%s\n","No connection able to be made");
		return -1;
	}
	
	//Write
	for(j=0;j<1;j++){
		if((write(fd,resp,resplen)) == -1){
			fprintf(stderr,"%s\n","could not write");
		}
		
		//Read
		if((rcount=read(fd,buf,BUFLEN))==-1){
			//deal with failurex
			fprintf(stderr,"%s\n","read failed");
			fprintf(stderr,"%i\n",errno);
		}
		
		for(i=0;i<rcount;i++){
			fprintf(stderr,"%c",buf[i]);
		}
		/*wait(10);*/
	}
	close(fd);
	freeaddrinfo(ai0);
	//Close
	return 0;
}
