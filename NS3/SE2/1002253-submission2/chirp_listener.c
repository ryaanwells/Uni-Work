#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <stdlib.h>
#include <errno.h>
#include <string.h>
#include <ctype.h>
#include <time.h>

#define BUF_LEN 1001

void clean(char * string){
  int charTot = strlen(string);
  int i;
  /* Replace every non printable char with '?' */
  for(i=0;i<charTot;i++){
    if(!isprint(string[i]))string[i]='?';
  }
}

int main(int argc, char* argv[]){
  
  int fd;
  char buffer[BUF_LEN];
  char * from, * msg, *eom; 
  char * fullmsg = (char*) malloc(strlen(buffer)+sizeof(char)*50);
  char dformat[] = "%d-%m-%Y %T - %%s - %%s\n";
  struct sockaddr addr;
  struct sockaddr_in servaddr;
  struct ip_mreq imr;
  socklen_t alen=sizeof(addr);
  int rlen;
  time_t ct;
  struct tm *timeinfo;

  if(argc!=1){
    fprintf(stderr,"Usage './chirp_listener'\n");
    return 1;
  }
  
  /* Connect to socket */
  if((fd = socket(AF_INET,SOCK_DGRAM,0))<0){
    perror("Could not connect to socket");
    return -1;
  }
  
  /* Establish port information  */
  memset(&servaddr,0,sizeof(servaddr));
  servaddr.sin_family = AF_INET;
  servaddr.sin_addr.s_addr = htonl(INADDR_ANY);
  servaddr.sin_port = htons(5010);
  
  /* Bind to the port */
  if(bind(fd, (struct sockaddr *)&servaddr, sizeof(servaddr))<0){
    perror("Bind not made");
    close(fd);
    return -1;
  }
  
  /* Establish multicast information */
  inet_pton(AF_INET,"224.0.0.22",&(imr.imr_multiaddr.s_addr));
  imr.imr_interface.s_addr = INADDR_ANY;
  
  /* Set the socket with the multicast information */
  if(setsockopt(fd,IPPROTO_IP,IP_ADD_MEMBERSHIP,&imr,sizeof(imr))<0){
    perror("Could not connect to multicast");
    close(fd);
    return -1;
  }
  
  
  while(1){
    /* Clean out data from the previous message */
    msg = from = NULL;
    buffer[0]='\0';
    *fullmsg='\0';
    /* Attempt to receive from the socket */
    if ((rlen = recvfrom(fd,buffer,BUF_LEN,0,(struct sockaddr *)&addr,&alen))<0){
      perror("Failed to receive from socket");
      continue;
    }
    /* Null terminate the buffer at the number of characters read.
       This will be either the final '\n' char of a well formed message
       or if 1001 characters are read in (max allowed), or a badly formed
       message, then this will clip off the final character.
    */
    buffer[rlen] = '\0';
    /* Get the time */
    time(&ct);
    timeinfo = localtime(&ct);
    /* Format the time as per the char[] "dformat". This contains further
       string formatters that will stay in the output string "fullmsg" 
       to be utilized later on.
    */
    strftime(fullmsg,strlen(buffer)+50,dformat,timeinfo);
    /* Find the "FROM" and first '\n' char in the message */
    from = strstr(buffer,"FROM");
    msg = strchr(buffer,'\n');
    /* If these exist */
    if((from!=NULL)&&(msg!=NULL)){
      /* Move the "from" pointer to the beginning of the username */
      from = from+5;
      /* If the message is formatted correctly */
      if(((msg-from)<=16)&&(msg>from)){
	/* Split the Username and Message body up */
	*(msg++)='\0';
	/* Find the end of the message */
	eom = strchr(msg,'\n');
	/* If there is no content or if it is badly formatted
	   then skip the message 
	*/
	if(eom==msg || eom==NULL) continue;
	/* Null terminate the end of message */
	*(eom)='\0';
	/* clean both the username and message content */
	clean(from);
	clean(msg);
	/* Print the message out using the formatters left in from
	   strftime
	*/
	fprintf(stdout,fullmsg,from,msg);
      }
    }
  }
  /* If for any reason the program breaks out of the while(1) loop
     then clean up and return gracefully.
     This should never actually happen
  */
  free(fullmsg);
  return 1;
}

