#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>

#define BUFLEN 1500

int main(int argc, char* argv[])
{
  int fd, connfd;
  struct sockaddr_in servaddr;
  struct sockaddr_in cliaddr;
  socklen_t cliaddrlen = sizeof(cliaddr);
  ssize_t i, rcount;
  char buf[BUFLEN];
  char resp[] = "Message Recieved";
  int resplen = strlen(resp);

  // Make Socket
  
  if((fd = socket(AF_INET,SOCK_STREAM,0))==-1){
    //deal with no socket
    fprintf(stderr,"%s\n","Socket not connected");
    fprintf(stderr,"%i\n",errno);
    return -1;
  }
  servaddr.sin_family = AF_INET;
  servaddr.sin_addr.s_addr = INADDR_ANY;
  servaddr.sin_port = htons(5000);
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
  //Connect
  if((connfd = accept(fd, (struct sockaddr *) &cliaddr, &cliaddrlen)) ==-1){
    //deal with failure
    fprintf(stderr,"%s\n","accept failed");
    fprintf(stderr,"%i\n",errno);
    close (fd);
    return -1;
  }
  //Read
  if((rcount=read(connfd,buf,BUFLEN))==-1){
    //deal with failure
    fprintf(stderr,"%s\n","read failed");
    fprintf(stderr,"%i\n",errno);
  }
  write(connfd,resp,resplen);
  for(i=0; i < rcount; i++){
    //if(buf[i] == EOF){
    //close(connfd);
      //}
    fprintf(stdout,"%c",buf[i]);
  }
  fprintf(stdout,"\n");
  //Close
  close(connfd);
  return 0;
}

