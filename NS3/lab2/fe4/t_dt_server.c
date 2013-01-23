#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <time.h>
#include <string.h>
#include <pthread.h>

#define BUFLEN 8
#define RESPLEN 20

struct tm *get_time(){
	time_t ct;
	time(&ct);
	
	if(ct == ((time_t)-1)){
		fprintf(stderr, "%s", "Time not accessable");
		return NULL;
	}
	
	return localtime(&ct);
}

int makeDNSTCP(int port){

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

void *processConn(void *args){

	const char *dateform = "%x\r\n";
	const char *timeform = "%X\r\n";
	const char datest[] = "DATE\r\n";
	const char timest[] = "TIME\r\n";

	char buf[BUFLEN];
	char resp[RESPLEN];
	int resplength;
	ssize_t rcount;
	struct tm *timeinfo;

	while((rcount=read(*(int *) args,buf,BUFLEN))>=0){
		if(strncmp(buf,datest,(BUFLEN-1)) == 0){
			timeinfo = get_time();
			if((strftime(resp,RESPLEN,dateform,timeinfo)) == 0){
				fprintf(stderr,"%s\n", "Could not get date");
			}
		}
		else if(strncmp(buf,timest,(BUFLEN-1)) == 0){
			timeinfo = get_time();
			if((strftime(resp,RESPLEN,timeform,timeinfo)) == 0){
				fprintf(stderr,"%s\n","Could not get time");
			}
		}
		
		resplength = strlen(resp);
		write(*(int *) args,resp,resplength);
	}
	//Close
	close(*(int *)args);
	return NULL;
}

int main(int argc, char* argv[])
{
  int fd, connfd;

  struct sockaddr_in cliaddr;
  socklen_t cliaddrlen = sizeof(cliaddr);

  pthread_t t1;

  fd = makeDNSTCP(5001);
  //Connect
  
  if((connfd = accept(fd, (struct sockaddr *) &cliaddr, &cliaddrlen)) ==-1){
    //deal with failure
    fprintf(stderr,"%s\n","accept failed");
    fprintf(stderr,"%i\n",errno);
    close (fd);
    return -1;
  }
  
  if(pthread_create(&t1,NULL,processConn, (void*)&connfd)){
	  fprintf(stderr,"%s\n","cannot create thread");
	  return -1;
  }

  pthread_join(t1,NULL);
  //Read
  
  
  return 0;
}
