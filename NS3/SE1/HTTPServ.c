#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

int connsock(int port){
	struct sockaddr_in servaddr;
	int fd;
	// Make Socket
	if((fd = socket(AF_INET,SOCK_STREAM,0))==-1){
		//deal with no socket
		fprintf(stderr,"%s\n","Socket not connected");
		fprintf(stderr,"%i\n",errno);
		return -1;
	}
	servaddr.sin_family = AF_INET;
	servaddr.sin_addr.s_addr = INADDR_ANY;
	servaddr.sin_port = htons(port);
	
	//Bind
	if(bind(fd, (struct sockaddr *)&servaddr, sizeof(servaddr))==-1){
		//deal with no connection
		fprintf(stderr,"%s\n","Bind not made");
		fprintf(stderr,"%i\n",errno);
		close(fd);
		return -1;
	}
	
	//Listen
	if(listen(fd, 1) == -1){
		//deal with failure
		fprintf(stderr,"%s\n","Listen failed");
		fprintf(stderr,"%i\n",errno);
		close (fd);
		return -1;
	}
	return fd;
}

int main(int argc, char* argv[]){
  int fd, connfd;
  struct sockaddr_in cliaddr;
  socklen_t cliaddrlen = sizeof(cliaddr);
  
  char buf[512];
  char resp[] = "HELLO!\n";
  int resplength;
  ssize_t rcount;
  char *start, *end, *ptr;
  resplength = strlen(resp);
  
  if((fd = connsock(8080)) == -1){
    fprintf(stderr,"%s\n%i\n","setup failed.",errno);
    return -1;
  }
  
  
  if((connfd = accept(fd, (struct sockaddr *) &cliaddr, &cliaddrlen)) == -1){
    fprintf(stderr, "%s\n%i\n","accept failed.",errno);
    close(fd);
    return -1;
  }
  
  if((rcount = read(connfd,buf,512))>=0){
    
    start = strstr(buf,"GET");
    end = strstr(buf,"HTTP/1.1");
    /* DEBUG HERE */
    fprintf(stdout,"%s\n",buf);
    if(start){
      if(end){
	ptr = start+3;
	while(ptr++!=end-2){
	  fprintf(stdout,"%c",*ptr);
	}
	fprintf(stdout,"\n");
	if((write(connfd,resp,resplength)) == -1){
	  fprintf(stderr,"%s\n","no write");
	}
      }
    }
  }

}

