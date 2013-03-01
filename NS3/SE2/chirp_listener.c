#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

#define BUF_LEN 1000

int main(int argc, char* argv[]){
	
	int fd;
	char buffer[BUF_LEN];
	struct sockaddr addr;
	struct sockaddr_in servaddr;
	struct ip_mreq imr;
	socklen_t alen=sizeof(addr);
	int rlen;
	memset(&servaddr,0,sizeof(servaddr));
	if(argc!=1){
		fprintf(stderr,"Usage './chirp_listener'\n");
		return 1;
	}
	
	if((fd = socket(AF_INET,SOCK_DGRAM,0))<0){
		perror("Could not connect to socket");
		return -1;
	}
	
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
	servaddr.sin_port = htons(5010);
	
	if(bind(fd, (struct sockaddr *)&servaddr, sizeof(servaddr))<0){
		perror("Bind not made");
		close(fd);
		return -1;
	}

	inet_pton(AF_INET,"224.0.0.22",&(imr.imr_multiaddr.s_addr));
	imr.imr_interface.s_addr = INADDR_ANY;

	if(setsockopt(fd,IPPROTO_IP,IP_ADD_MEMBERSHIP,&imr,sizeof(imr))<0){
		perror("Could not connect to multicast");
		close(fd);
		return -1;
	}
	while(1){
		rlen = recvfrom(fd,buffer,BUF_LEN,0,(struct sockaddr *)&addr,&alen);
		if (rlen<0){
			fprintf(stderr,"RLEN<0\n");
			continue;
		}
		buffer[rlen] = '\0';
		fprintf(stdout,"Message:\n%-*.*s\n",rlen,rlen,buffer);
		buffer[0]='\0';
	}
	return 1;
}

