#define _GNU_SOURCE


#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <unistd.h>
#include <stdio.h>
#include <errno.h>
#include <string.h>

#define BUFLEN 

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
  char resp[] = "HELLO!\r\n";
  char *hostname[255];
  char *cwd;
  char *file;
  char badresp[] = "400 BAD REQUEST\r\n";
  int resplength;
  ssize_t rcount;
  char *getp=NULL, *http=NULL, *host=NULL, *eomp=NULL, *ptr;
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
    
    getp = strstr(buf,"GET");
    http = strcasestr(buf,"HTTP/1.1");
    host = strcasestr(buf,"Host:");
    eomp = strcasestr(buf,"\r\n\r\n");
    
    if((cwd=get_current_dir_name()) != NULL){
      fprintf(stdout,"CWD: %s:%d\n",cwd,strlen(cwd));
    }
    if((file=realloc(cwd,(strlen(cwd)+((http-2)-(getp+4)-5)*sizeof(char))))!=NULL){
      file=strncat(file,(getp+4),((http-2)-(getp+4)+1));
    }

    /* DEBUG HERE 
       fprintf(stderr,"BUF: %s\n",buf);
       fprintf(stderr,"GETP:%c HTTP:%c HOST:%c\n",*getp,*http,*host);
       fprintf(stdout,"FILE: %s:%d\n",file,strlen(file));/*
    */
    /* If this is a valid start line */
    if(getp!=NULL&&http!=NULL&&host!=NULL&&eomp!=NULL){
      ptr = getp+4;
      while(ptr++!=http-2){
	fprintf(stdout,"%c",*ptr);
	}
      fprintf(stdout,"\n");
      if(gethostname(hostname,255)==-1){
	fprintf(stdout,"%s\n","unable to get hostname");
      }
      fprintf(stdout,"%s:%d\n",hostname,strlen(hostname));
      if((write(connfd,resp,resplength)) == -1){
	fprintf(stderr,"%s\n","no write");
      }
    } 
    else{
      resplength=strlen(badresp);
      if((write(connfd,badresp,resplength)) == -1){
	fprintf(stderr,"%s\n","no write");
      }
    }
  }
  free(cwd);
}

