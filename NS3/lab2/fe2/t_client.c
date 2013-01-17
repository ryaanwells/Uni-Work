#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <netdb.h>
#include <string.h>
#include <stdlib.h>

#define BUFLEN 1500

int main(int argc, char* argv[])
{

  if(argc != 2){
    fprintf(stdout,"%s\n","Usage: ./t_client address");
	return -1;
  }
  int fd, connfd;
  char buf[BUFLEN];
  char resp[] = "TIME\r\n";
  int resplen = strlen(resp);
  ssize_t rcount;
  struct addrinfo *ai, *ai0, hints;
  int i;
  memset(&hints, 0, sizeof(hints));
  hints.ai_family = PF_UNSPEC;;
  hints.ai_socktype = SOCK_STREAM;

  if((i = getaddrinfo(argv[1], "5001", &hints, &ai0)) !=0){
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
  if((write(fd,resp,resplen)) == -1){
    fprintf(stderr,"%s\n","could not write");
  }
  //close(fd);
  
  //Read
  if((rcount=read(fd,buf,BUFLEN))==-1){
    //deal with failure
    fprintf(stderr,"%s\n","read failed");
    fprintf(stderr,"%i\n",errno);
  }
  for(i=0;i<rcount;i++){
    fprintf(stdout,"%c",buf[i]);
  }
  fprintf(stdout,"\n");
  close(fd);
  //Close
  return 0;
}

