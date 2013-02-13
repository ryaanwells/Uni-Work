#define _GNU_SOURCE

#include <stdlib.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netdb.h>
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
  if(argc==-2){
    fprintf(stdout,"Usage %s port\n",argv[1]);
    return 1;
  }
  int fd, connfd;
  struct sockaddr_in cliaddr;
  socklen_t cliaddrlen = sizeof(cliaddr);
  
  char *buf =(char *) malloc(sizeof(char)*(256+1));
  int offset = 0;
  char *resp = (char *) malloc(sizeof(char)*(256));
  char hostname[275];
  char *cwd;
  char *file;
  const char badresp[] = "400 BAD REQUEST\r\n";
  int resplength;
  ssize_t rcount;
  char *getp=NULL, *http=NULL, *host=NULL, *eomp=NULL, *ptr;
  char *newhn;
  
  FILE *fp;
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
  
  while((rcount = read(connfd,buf+offset,256))>=0){
	  fprintf(stderr,"%zu\n",rcount);
	  *(buf+offset+rcount)='\0';
	  offset=offset+rcount;
	  fprintf(stderr,"%s\n",buf);
	  eomp = strcasestr(buf,"\r\n\r\n");
	  if(eomp!=NULL){ break;}
	  buf = realloc(buf,(strlen(buf)+257)*sizeof(char));
  }
  
  getp = strstr(buf,"GET");
  http = strcasestr(buf,"HTTP/1.1");
  host = strcasestr(buf,"Host:");
  eomp = strcasestr(buf,"\r\n\r\n");
  *eomp='\0';
  
  fprintf(stderr,"BUF: %s\n",buf);
  fprintf(stderr,"GETP:%c HTTP:%c HOST:%c EOMP:%c\n",*getp,*http,*host,*eomp);

  if((cwd=get_current_dir_name()) != NULL){
      fprintf(stdout,"CWD: %s:%zu\n",cwd,strlen(cwd));
  }
  if((file=malloc((strlen(cwd)+40*sizeof(char))))!=NULL){
    *(--http)='\0';
    file=strcat(file,(cwd));
    file=strcat(file,(getp+4));
    }
  /* DEBUG HERE */
  
  fprintf(stdout,"FILE:%s :%zu\n",file,strlen(file));
  
  /* If this is a valid start line */
  if(getp!=NULL&&http!=NULL&&host!=NULL&&eomp!=NULL){
	  
	  /* Print the requested filename */
	  ptr = getp+4;
	  while(ptr++!=http-2){
		  fprintf(stdout,"%c",*ptr);
	  }
	  fprintf(stdout,"\n");
	  
	  /* Get the Hostname */
	  if(gethostname(hostname,255)==-1){
		  fprintf(stdout,"%s\n","unable to get hostname");
	  }
	  fprintf(stdout,"%s:%zu\n",hostname,strlen(hostname));
	  
	  /* If the hostname matches the current host */
	  if((strncmp(host+5,hostname,strlen(hostname)))==0 ){
		  fprintf(stdout,"%s\n","matches!");
		  fprintf(stdout,"%s\n",file);
		  if((fp=fopen(file,"r"))!=NULL){
			  fprintf(stdout,"File Exists!\n");
		  }
		  else{
			  fprintf(stderr,"%s: %d\n","ERROR OPENING FILE", errno);
		  }
		  
	  }
	  else{
		  newhn=strcat(hostname,".dcs.gla.ac.uk");
		  fprintf(stdout,"%s\n",newhn);
		  if((strncmp(host+5,newhn,strlen(newhn)))==0){
			  fprintf(stdout,"%s\n","Matches full!");
			  if((fp=fopen(file,"rb"))!=NULL){
				  fprintf(stdout,"%s\n","File exists!");
			  }
		  }
	  }
	  fprintf(stdout,"%s\n","got to here");
	  offset=0;
	  while((fgets(resp+offset,256,fp))!=NULL){
	    offset=offset+256;
	    fprintf(stderr,"%s\n",resp);
	    resp = realloc(resp,(strlen(resp)+256)*sizeof(char));
	  }
	  resplength = strlen(resp);
	  if((write(connfd,resp,resplength)) == -1){
		  fprintf(stderr,"%s\n","no write");
	  }
	  if(fp!=NULL){
	    fclose(fp);
	  }
  } 
  else{
	  resplength=strlen(badresp);
	  if((write(connfd,badresp,resplength)) == -1){
		  fprintf(stderr,"%s\n","no write");
	  }
	  close(connfd);
  }
  fprintf(stdout,"%s\n","Ended");
  close(connfd);
  free(cwd);
  free(file);
  free(buf);
  return 1;
}


