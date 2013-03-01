#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define BUF_LEN 1001

void clean(char * string){
	int charTot = strlen(string);
	int i;
	for(i=0;i<charTot;i++){
		if(!isprint(string[i]))string[i]='?';
	}
}

int main(int argc, char* argv[]){
	
	int fd;
	char buffer[BUF_LEN];
	char fullmsg[BUF_LEN+50];
	char * from, * msg;
	char dformat[] = "%c - %%s - %%s";
	struct sockaddr addr;
	struct sockaddr_in servaddr;
	struct ip_mreq imr;
	socklen_t alen=sizeof(addr);
	int rlen;
	time_t ct;
	struct tm *timeinfo;
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
		from = msg = NULL;
		rlen = recvfrom(fd,buffer,BUF_LEN,0,(struct sockaddr *)&addr,&alen);
		if (rlen<0){
			fprintf(stderr,"RLEN<0\n");
			continue;
		}
		buffer[rlen] = '\0';
		time(&ct);
		timeinfo = localtime(&ct);
		strftime(fullmsg,BUF_LEN+50,dformat,timeinfo);
		from = strstr(buffer,"FROM");
		msg = strstr(buffer,"\n");
		if(from!=NULL&&msg!=NULL){
			printf("first Inner\n");
			from = from+5;
			if((msg-from)<=16){
				*(msg++)='\0';
				clean(from);
				clean(msg+1);
				buffer[rlen]='\0';
				printf("second Inner\n");
				sprintf(fullmsg,fullmsg,from,msg);
				fprintf(stdout,"%-*.*s\n",rlen+50,rlen+50,fullmsg);
			}
		}
		buffer[0]='\0';
		printf("here\n");
	}
	return 1;
}

