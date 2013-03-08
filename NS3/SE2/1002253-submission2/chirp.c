#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>
#include <stdlib.h>
#include <ctype.h>

#define BUF_LEN 1000

int main(int argc, char* argv[]){
  char * send, * buffer;
  int buflen, fd;
  struct sockaddr_in addr;
  
  if(argc != 2){
    printf("Usage ./chrip \"Message\" (Message must be in quotes)\n");
    return 1;
  }
  /* Get the message from the command line */
  send = argv[1];
  /* Check constraints */
  if(strlen(send)>=BUF_LEN){
    printf("Message too long, message must be less than 1000 characters\n");
    return -1;
  }
  
  /* Allocate enough space for the entire send message and form the message */
  buffer = (char*) malloc(strlen(send)+sizeof(char)*24);
  sprintf(buffer,"FROM %s\n%s\n",getlogin(),send);
  buflen = strlen(buffer);

  /* Create the socket to send to */
  if((fd = socket(AF_INET,SOCK_DGRAM,0))<0){
    perror("Could not connect to socket");
    free(buffer);
    return -1;
  }
  
  /* Establish the port information */
  inet_pton(AF_INET,"224.0.0.22",&addr.sin_addr.s_addr);
  addr.sin_port = htons(5010);
  addr.sin_family = AF_INET;
  /* Send to the multicast group */
  if(sendto(fd,buffer,buflen,0,(struct sockaddr *)&addr,sizeof(addr))<0){
    perror("Unable to send message");
  }
  /* Clean up and exit */
  close(fd);
  free(buffer);
  return 1;
}
