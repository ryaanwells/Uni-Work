#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <time.h>
#include <string.h>

#define BUFLEN 7
#define RESPLEN 20

void get_time(const struct tm *ti, time_t *ct){
   time(&ct);
  
  if(ct == ((time_t)-1)){
	  fprintf(stderr, "%s", "Time not accessable");
	  return -1;
  }

  ti = localtime(&ct);
}

int main(int argc, char* argv[])
{
  int fd, connfd;
  struct sockaddr_in servaddr;
  struct sockaddr_in cliaddr;
  socklen_t cliaddrlen = sizeof(cliaddr);
  ssize_t i, rcount;
  const char dateform[] = "%x\r\n";
  const char timeform[] = "%X\r\n";
  const char timest[] = "DATE\r\n";
  const char datest[] = "TIME\r\n";
  char buf[BUFLEN];
  char *resp;
  int resplength;
  time_t ct;
  struct tm *timeinfo;

  // Make Socket
  if((fd = socket(AF_INET,SOCK_STREAM,0))==-1){
    //deal with no socket
    fprintf(stderr,"%s\n","Socket not connected");
    fprintf(stderr,"%i\n",errno);
    return -1;
  }
  servaddr.sin_family = AF_INET;
  servaddr.sin_addr.s_addr = INADDR_ANY;
  servaddr.sin_port = htons(5001);

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

  if (strncmp(buf,datest,BUFLEN) == 0){
	  time(&ct);
	  fprintf(stderr,"here");
	  if(ct == ((time_t)-1)){
		  fprintf(stderr, "%s", "Time not accessable");
		  return -1;
	  }
	  
	  timeinfo = localtime(&ct);
	  fprintf(stderr,"here");
	  if((strftime(resp,RESPLEN,dateform,timeinfo)) == 0){
		  fprintf(stderr,"%s\n", "Could not get date");
	  }
  }
  else if (strncmp(buf,timest,BUFLEN) == 0){
	  time(&ct);
	  if(ct == ((time_t)-1)){
		  fprintf(stderr, "%s", "Time not accessable");
		  return -1;
	  }
	  
	  timeinfo = localtime(&ct);
	  if((strftime(resp,RESPLEN,timeform,timeinfo)) == 0){
		  fprintf(stderr,"%s\n","Could not get time");
	  }
  }
  resplength = strlen(resp);
  write(connfd,resp,resplength);
  fprintf(stdout,"%s\n", resp);

  //Close
  close(connfd);
  return 0;
}
