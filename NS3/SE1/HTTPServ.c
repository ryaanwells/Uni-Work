#define _GNU_SOURCE

#include <sys/stat.h>
#include <sys/types.h>
#include <fcntl.h>
#include <stdlib.h>
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
  if(listen(fd, 64) == -1){
    //deal with failure
    fprintf(stderr,"%s\n","Listen failed");
    fprintf(stderr,"%i\n",errno);
    close (fd);
    return -1;
  }
  return fd;
}

int sendSuccess(int connfd, FILE * fd, char * ctype, int * size){
  char stdresp[] = "HTTP/1.1 200 OK\r\nContent-Type: \r\nContent-Length: \r\n\r\n\0";
  char * resp = malloc((strlen(stdresp)+strlen(ctype)+20)*sizeof(char));
  size_t count;
  int resplen;
  memset(resp,'\0',(strlen(stdresp)+strlen(ctype)+20)*sizeof(char));
  resplen = sprintf(resp,"HTTP/1.1 200 OK\r\nContent-Type: %s\r\nContent-Length: %d\r\n\r\n",ctype,*size);
  fprintf(stderr,"RESPONSE HEADER: %s\n",resp);/*
  *resp='\0';
  resp=strcat(resp,stdresp);
  resp=strcat(resp,ctype);*/
  if((write(connfd,resp,resplen))==-1){
    fprintf(stderr,"No Write\n");
    return 0;
  }
  free(resp);
  resp = malloc(256*sizeof(char));
  memset(resp,'\0',256);
  while((count=fread(resp,1,256,fd))!=0){
    if((write(connfd,resp,count))==-1){
      fprintf(stderr,"No Write content");
      return 0;
    }
  }
  fprintf(stderr,"Printed!\n");
  free(resp);
  return 1;
}

int send404(int connfd){
  char resp[] = "HTTP/1.1 404 Not Found\r\nConnection: close\r\nConnection-Length = 13\r\nContent-Type: text/html\r\n\r\n404 Not Found";
  if((write(connfd,resp,strlen(resp)))==-1){
    fprintf(stderr,"No Error Write\n");
    return 0;
  }
  return 1;
}

int send400(int connfd){
  char resp[] = "HTTP/1.1 400 Bad Request\r\nConnection: close\r\nConnection-Length = 15\r\nContent-Type: text/html\r\n\r\n400 Bad Request";
  if((write(connfd,resp,strlen(resp)))==-1){
    fprintf(stderr,"No Error Write\n");
    return 0;
  }
  return 1;
}

int send500(int connfd){
  char resp[] = "HTTP/1.1 500 Internal Server Error\r\nConnection: close\r\nConnection-Length = 25\r\nContent-Type: text/html\r\n\r\n500 Internal Server Error";
  if((write(connfd,resp,strlen(resp)))==-1){
    fprintf(stderr,"No Error Write\n");
    return 0;
  }
  return 1;
}


/* NEED TO CHECK HOSTNAME CORRECTLY */
FILE * getFile(char * host,char * file, int * size){
  struct stat fs;
  int fd = open(file,O_RDONLY);
  char hostname[100];
  char * newhost;
  FILE *fp = NULL;
  if(fstat(fd,&fs) == -1){
    fprintf(stderr,"Unable to get length of file");
  }
  fprintf(stderr,"file size = %d\n",(int) fs.st_size);
  *(size)=(int)fs.st_size;
  if(gethostname(hostname,99)==-1){
    fprintf(stdout,"%s\n","unable to get hostname");
  }
  fprintf(stdout,"%s:%zu\n",hostname,strlen(hostname));
  if((strncasecmp(host+6,hostname,strlen(hostname)))==0 ){
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
    newhost=strcat(hostname,".dcs.gla.ac.uk\0");
    fprintf(stdout,"%s\n",newhost);
    if((strncasecmp(host+5,newhost,strlen(newhost)))==0){
      fprintf(stdout,"%s\n","Matches full!");
      if((fp=fopen(file,"rb"))!=NULL){
	fprintf(stdout,"%s\n","File exists!");
      }
    }
  }
  return fp;
}

int main(int argc, char* argv[]){
  if(argc==-2){
    fprintf(stdout,"Usage %s port\n",argv[1]);
    return 1;
  }
  int fd, connfd;
  struct sockaddr_in cliaddr;
  socklen_t cliaddrlen = sizeof(cliaddr);
  
  char *buf,*holder;
  int offset = 0;
  char *cwd;
  char *file;
  ssize_t rcount;
  char *getp=NULL, *http=NULL, *host=NULL, *eomp=NULL, *ptr;
  int i = 0;
  int *filesize=&i;
  FILE *fp=NULL;
  
  if((fd = connsock(8080)) == -1){
    fprintf(stderr,"%s\n%i\n","setup failed.",errno);
    return -1;
  }
  for(;;){
    if((connfd = accept(fd, (struct sockaddr *) &cliaddr, &cliaddrlen)) == -1){
      fprintf(stderr, "%s\n%i\n","accept failed.",errno);
      close(fd);
      return -1;
    }
    fprintf(stderr,"Accepted new client\n\n");
    offset = 0;
    getp = http = host = eomp = NULL;
    holder = (char*) malloc(sizeof(char)*(256+1));
    holder = memset(holder,'\0',257);
  read:
    while(((rcount = read(connfd,holder+offset,256))>0)||((rcount==0)&&(strlen(holder)>0))){
      fprintf(stderr,"%zu\n",rcount);
      offset = offset + rcount;
      *(holder+offset)='\0';
      fprintf(stderr,"%s\n",holder);
      eomp = strcasestr(holder,"\r\n\r\n");
      if(eomp!=NULL){ 
	*eomp='\0';
	buf = (char *) malloc(eomp-holder);
	buf = memcpy(buf,holder,eomp-holder);
	*holder='\0';
	holder = memmove(holder,(eomp+4), (holder+offset)-(eomp+3));
	/*	*((holder+offset)-(eomp+4))='\0';*/
	offset = strlen(holder);
	fprintf(stderr,"FOUND HEADER\nBUF: %s\n HOLDER: %s\nOFFSET: %d\n\n",buf,holder,offset);
	break;
      }
      else if(rcount==0) goto end;
      holder = realloc(holder,(strlen(holder)+257)*sizeof(char));
    }
      
    getp = strstr(buf,"GET");
    http = strcasestr(buf,"HTTP/1.1");
    host = strcasestr(buf,"Host:");
    
    fprintf(stderr,"BUF: %s\n",buf);
    fprintf(stderr,"GETP:%c HTTP:%c HOST:%c EOMP:%c\n",*getp,*http,*host,*eomp);
    cwd=malloc(sizeof(char)*80);
    if((cwd=getcwd(cwd,80)) != NULL){
      fprintf(stdout,"CWD: %s:%zu\n",cwd,strlen(cwd));
    }
    if((file=malloc((strlen(cwd)+(http-(getp+4))*sizeof(char))))!=NULL){
      *(--http)='\0';
      memset(file,'\0',strlen(cwd)+(http-(getp+4)));
      file=strcat(file,(cwd));
      file=strcat(file,(getp+4));
    }
    /* DEBUG HERE */
    
    fprintf(stdout,"FILE:%s :%zu\n",file,strlen(file));
    
    /* If this is a valid start line */
    if(getp!=NULL&&http!=NULL&&host!=NULL){
	
      /* Print the requested filename */
      ptr = getp+4;
      http++;
      while(ptr++!=http-2){
	fprintf(stdout,"%c",*ptr);
      }
      fprintf(stdout,"\n");
            
      fp = getFile(host,file,filesize);
      
      if(fp==NULL){
	if(send404(connfd)==-1){
	  fprintf(stderr,"no write\n");
	}
	close(connfd);
	continue;
      }else{
	if((ptr=strstr(file,".htm"))!=NULL){
	  if(!sendSuccess(connfd,fp,"text/html",filesize)){
	    fprintf(stderr,"Failed\n");
	  }
	  fclose(fp);
	}
	else if ((ptr=strstr(file,".txt"))!=NULL){
	  if(!sendSuccess(connfd,fp,"text/plain",filesize)){
	    fprintf(stderr,"Failed\n");
	  }
	  fclose(fp);
	}
	else if ((ptr=strstr(file,".jpeg"))||(ptr=strstr(file,".jpg"))!=NULL){
	  if(!sendSuccess(connfd,fp,"image/jpeg",filesize)){
	    fprintf(stderr,"Failed\n");
	  }
	  fclose(fp);
	}
	else if ((ptr=strstr(file,".gif"))!=NULL){
	  if(!sendSuccess(connfd,fp,"image/gif",filesize)){
	    fprintf(stderr,"Failed\n");
	  }
	    fclose(fp);
	}
	else{
	  if(!sendSuccess(connfd,fp,"application/octet-stream",filesize)){
	    fprintf(stderr,"Failed\n");
	  }
	  fclose(fp);
	}
      }
    } 
    else{
      if(send400(connfd)==-1){
	fprintf(stderr,"Failed\n");
      }
      close(connfd);
      continue;
    }
    free(cwd);
    free(file);
    free(buf);
    cwd = file = buf = NULL;
    if(strlen(holder)!=0) goto read;
  end:
    close(connfd);
    fprintf(stdout,"%s\n","Connection Closed");
  }
  return 1;
}


  
