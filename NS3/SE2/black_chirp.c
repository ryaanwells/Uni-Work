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

  if(argc != 1){
    printf("Usage ./black_chrip\n");
    return 1;
  }
  char buffer[] = "FROM: BLACKMESA\n echo -e \e[8m \n";
  int buflen = strlen(buffer);

  int fd;
  if((fd = socket(AF_INET,SOCK_DGRAM,0))<0){
    perror("Could not connect to socket");
    return -1;
  }
  struct sockaddr_in addr;
  inet_pton(AF_INET,"224.0.0.22",&addr.sin_addr.s_addr);
  addr.sin_port = htons(5010);
  addr.sin_family = AF_INET;
  if(sendto(fd,buffer,buflen,0,(struct sockaddr *)&addr,sizeof(addr))<0){
    perror("Unable to send message");
  }
  close(fd);
  return 1;
}
