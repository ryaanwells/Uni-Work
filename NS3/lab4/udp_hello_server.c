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
	socklen_t alen=sizeof(addr);
	int rlen;
	
	if(argc!=1){
		fprintf(stderr,"Usage './udp_hello_server'\n");
		return 1;
	}
	
	if((fd = socket(AF_INET,SOCK_DGRAM,0))<0){
		perror("Could not connect to socket");
		return -1;
	}
	
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = INADDR_ANY;
	servaddr.sin_port = htons(5003);
	
	if(bind(fd, (struct sockaddr *)&servaddr, sizeof(servaddr))<0){
		perror("Bind not made");
		close(fd);
		return -1;
	}
	
	rlen = recvfrom(fd,buffer,BUF_LEN,0,&addr,&alen);
	if (rlen<0){
		perror("Could not receive a packet");
		return -1;
	}
	fprintf(stdout,"Message:\n%*.*s\n",rlen,rlen,buffer);
	close(fd);
	return 1;
}
