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
#include "Stack.h"
#include <pthread.h>

/* ========== THREAD POOL =========== */

void * process(void * arg);

typedef struct ThreadPool{
  int freeThreads;
  int totalThreads;
  pthread_t * threads;
  Stack * stack;
  pthread_mutex_t mut;
  pthread_cond_t empty;
  pthread_cond_t full;
} ThreadPool;

ThreadPool * init(int threads){
  if (threads<1) threads = 1;
  ThreadPool * tp = malloc(sizeof(ThreadPool));
  if(tp!=NULL){
    Stack * s = createStack();
    if(s==NULL){
#ifdef SERVER_DEBUG
      perror("Could not create stack");
#endif
      free(tp);
      return NULL;
    }
    pthread_t * pt = malloc(threads*sizeof(pthread_t));
    if(pt==NULL){
#ifdef SERVER_DEBUG
      perror("Could not create thread array");
#endif
      free(s);
      free(tp);
      return NULL;
    }
    int i;
    pthread_mutex_t mutex = PTHREAD_MUTEX_INITIALIZER;
    pthread_cond_t cond = PTHREAD_COND_INITIALIZER;
    pthread_cond_t ful = PTHREAD_COND_INITIALIZER;
    tp->mut = mutex;
    tp->empty = cond;
    tp->full= ful;
    tp->stack = s;
    tp->freeThreads = threads;
    tp->totalThreads = threads;
    tp->threads = pt;
    for (i=0;i<threads;i++){
      pthread_create(&pt[i],NULL,process,tp);
    }
  }
#ifdef SERVER_DEBUG
  printf("Created Thread Pool\n");
#endif
  return tp;
}

int addWaiting(ThreadPool * TP,int conn){
  pthread_mutex_lock(&TP->mut);
  while(TP->freeThreads<=0){
    pthread_cond_wait(&TP->full,&TP->mut);
  }
  int i = stackAdd((TP->stack),conn);
  TP->freeThreads--;
  pthread_cond_signal(&TP->empty);
  pthread_mutex_unlock(&TP->mut);
  return i;
}

int removeWaiting(ThreadPool * TP){
  pthread_mutex_lock(&TP->mut);
  int conn;
  TP->freeThreads++;
  pthread_cond_signal(&TP->full);
  pthread_mutex_unlock(&TP->mut);
  while((conn=stackRemove(TP->stack))<0){
    pthread_cond_wait(&TP->empty,&TP->mut);
  }
  if (conn==0){
    stackAdd(TP->stack,0);
  }
  pthread_mutex_unlock(&TP->mut);
#ifdef SERVER_DEBUG
  printf("Removed new connection: %d\n",conn);
#endif
  return conn;
}

void destroyTP(ThreadPool * TP){
  int i;
  for(i=0;i<TP->totalThreads;i++){
    pthread_join(TP->threads[i],NULL);
  }
  free(TP->threads);
  pthread_mutex_destroy(&TP->mut);
  pthread_cond_destroy(&TP->empty);
  destroyStack(TP->stack);
  free(TP);
}

/* ========== Processing ========== */

int connsock(int port){
  struct sockaddr_in servaddr;
  int fd;
  /* Create a socket */
  if((fd = socket(AF_INET,SOCK_STREAM,0))==-1){
    perror("Socket not connected");
    return -1;
  }
  servaddr.sin_family = AF_INET;
  servaddr.sin_addr.s_addr = INADDR_ANY;
  servaddr.sin_port = htons(port);
  
  /* Bind Socket */
  if(bind(fd, (struct sockaddr *)&servaddr, sizeof(servaddr))==-1){
    perror("Bind not made");
    close(fd);
    return -1;
  }
  
  /* Listen on socket */
  if(listen(fd, 64) == -1){
    perror("Listen failed");
    close (fd);
    return -1;
  }
  return fd;
}

int sendSuccess(int connfd, FILE * fd, char * ctype, int * size){
  /* Create the headers for responding */
  char stdresp[] = "HTTP/1.1 200 OK\r\nContent-Type: \r\nContent-Length: \r\n\r\n\0";
  char * resp = malloc((strlen(stdresp)+strlen(ctype)+20)*sizeof(char));
  size_t count;
  int resplen;
  
  memset(resp,'\0',(strlen(stdresp)+strlen(ctype)+20)*sizeof(char));
  resplen = sprintf(resp,"HTTP/1.1 200 OK\r\nContent-Type: %s\r\nContent-Length: %d\r\n\r\n",ctype,*size);
#ifdef SERVER_DEBUG
  fprintf(stderr,"RESPONSE HEADER: %s\n",resp);
#endif
  /* Send the headers and keep with the connection kept alive*/
  if((write(connfd,resp,resplen))==-1){
    perror("No Write Success");
    return 0;
  }
  /* Get a fresh buffer for sending the file contents */
  free(resp);
  resp = malloc(512*sizeof(char));
  memset(resp,'\0',512);
  while((count=fread(resp,1,512,fd))!=0){
    if((write(connfd,resp,count))==-1){
      perror("No Write Content");
      return 0;
    }
  }
  printf("Sent File!\n");
  free(resp);
  return 1;
}

int send404(int connfd){
  char resp[] = "HTTP/1.1 404 Not Found\r\nConnection: close\r\nConnection-Length = 13\r\nContent-Type: text/html\r\n\r\n404 Not Found";
  if((write(connfd,resp,strlen(resp)))==-1){
    perror("No Write 404\n");
    return 0;
  }
  return 1;
}

int send400(int connfd){
  char resp[] = "HTTP/1.1 400 Bad Request\r\nConnection: close\r\nConnection-Length = 15\r\nContent-Type: text/html\r\n\r\n400 Bad Request";
  if((write(connfd,resp,strlen(resp)))==-1){
    perror("No Write 400\n");
    return 0;
  }
  return 1;
}

int send500(int connfd){
  char resp[] = "HTTP/1.1 500 Internal Server Error\r\nConnection: close\r\nConnection-Length = 25\r\nContent-Type: text/html\r\n\r\n500 Internal Server Error";
  if((write(connfd,resp,strlen(resp)))==-1){
    perror("No Write 500");
    return 0;
  }
  return 1;
}


/* NEED TO CHECK HOSTNAME CORRECTLY */
FILE * getFile(char * host,char * file, int * size){
  struct stat fs;
  int fd = open(file,O_RDONLY);
  char hostname[100];
  char * dcs;
  FILE *fp = NULL;

  /* Get length of file and give this to the user */
  if(fstat(fd,&fs) == -1) perror("Unable to get length of file");
  *(size)=(int)fs.st_size;
  close(fd);
  
  /* Get the hostname */
  if(gethostname(hostname,99)==-1) fprintf(stdout,"%s\n","unable to get hostname");

#ifdef SERVER_DEBUG
  printf("File size = %d\n",(int) fs.st_size);
  printf("%s:%zu\n",hostname,strlen(hostname));
#endif

  /* Find out if either hostname contains domain name and remove it */
  dcs = strcasestr(hostname,".dcs.gla.ac.uk");
  if(dcs != NULL) *dcs='\0';
  dcs = strcasestr(host,".dcs.gla.ac.uk");
  if(dcs!= NULL) *dcs='\0';
  
  if((strncasecmp(host,hostname,strlen(hostname)))==0){
#ifdef SERVER_DEBUG
    printf("Host names match\n");
#endif
    if((fp=fopen(file,"r"))!=NULL){
#ifdef SERVER_DEBUG
      fprintf(stdout,"File Exists!\n");
#endif
    }
    else{
      perror("Cannot open file");
      fp = NULL;
    }	
  }
  return fp;
}

void *processConn(int connfd){
  char *buf=NULL,*holder;
  int offset = 0;
  char *file;
  ssize_t rcount;
  /*
    "getp" is where in the "Working buffer" the "GET" string is found. 
    Case sensitive.
    "http" is where in the "Working buffer" the "HTTP/1.1" string is found. 
    Case insensitive.
    "host" is where in the "Working buffer" the "HOST" string is found.
    Case insensitive.
    "eomp" is where in the "Holding buffer" the "\r\n\r\n" string is found.
   */
  char *getp=NULL, *http=NULL, *host=NULL, *eomp=NULL, *ptr;
  
  int i = 0;
  int *filesize=&i;
  FILE *fp=NULL;
  
  /* Holder is the "Holding buffer" where the input from the connection
     is read into until the end of message "\r\n\r\n" is found. 
  */
  holder = (char*) malloc(sizeof(char)*(512+1));
  holder = memset(holder,'\0',512);
  
 read:
  /* While the connection has more to give, or when the connection has
     nothing left to give but the "Holding buffer" still has content. 
     Offset is the current offset into the "Holding Buffer" after every read.
  */
  while(((rcount = read(connfd,holder+offset,512))>0)||((rcount==0)&&(strlen(holder)>0))){
    offset = offset + rcount;
    /* Null terminate what we have just read in */
    *(holder+offset)='\0';
#ifdef SERVER_DEBUG
    fprintf(stderr,"Characters Read: %zu\n",rcount);
    fprintf(stderr,"Holding Buffer: %s\n",holder);
#endif
    eomp = strcasestr(holder,"\r\n\r\n");
    if(eomp!=NULL){ 
      /*
	We have found a finished header! Move this into the "Working Buffer". 
	The "Working Buffer" is "buf".
      */
      *eomp='\0';
      buf = (char *) malloc(eomp-holder);
      buf = memcpy(buf,holder,eomp-holder);
      /*
	Slide the rest of the "Holding Buffer" after where the end of the header
	was found to the beginning of the header with the null terminator still on
	the end.
      */
      *holder='\0';
      holder = memmove(holder,(eomp+4), (holder+offset)-(eomp+3));
      offset = strlen(holder);
#ifdef SERVER_DEBUG
      fprintf(stderr,"FOUND HEADER\n------------\nBUFFER: %s\nHOLDER: %s\nOFFSET: %d\n\n",buf,holder,offset);
#endif
      break;
    }
    /* 
       if nothing was read, but the "Holding Buffer" still had contents and the 
       "Holding Buffer" did not contain the end of message then send a bad
       request.
    */
    else if(rcount==0) goto badReq;
    /*
      End of message has not yet been found, expand the buffer and continue trying
      to read.
     */
    holder = realloc(holder,(strlen(holder)+513)*sizeof(char));
  }
  /*
    Connection was established and then closed, so no read. Close the connection
  */
  if(buf==NULL) goto end;
  
  /*
    Check if the required contents are present
  */
  getp = strstr(buf,"GET");
  http = strcasestr(buf,"HTTP/1.1");
  host = strcasestr(buf,"Host:");
  
  /*
    Get the current working directory and add on the requested file name
   */
  file=malloc(sizeof(char)*180);
  if((file=getcwd(file,180)) != NULL){
#ifdef SERVER_DEBUG
    fprintf(stdout,"CWD: %s:%zu\n",file,strlen(file));
#endif
  }
  if((file=realloc(file,(strlen(file)+(http-(getp+4))*sizeof(char))))!=NULL){
    *(--http)='\0';
    file=strcat(file,(getp+4));
  }
  
#ifdef SERVER_DEBUG
  fprintf(stdout,"BUF: %s\n",buf);
  fprintf(stdout,"GETP:%c HTTP:%c HOST:%c EOMP:%c\n",*getp,*http,*host,*eomp);
  fprintf(stdout,"FILE:%s :%zu\n",file,strlen(file));
#endif
  
  /* If this is a valid start line */
  if(getp!=NULL&&http!=NULL&&host!=NULL){

    fp = getFile(host+6,file,filesize);
    
    if(fp==NULL){
      /* File does not exist */
      if(send404(connfd)==-1) perror("Unable to send 404");
    }
    else{
      /* File exists, attempt to send it and then close the file */
      if((ptr=strstr(file,".htm"))!=NULL){
	if(!sendSuccess(connfd,fp,"text/html",filesize)){
	  fprintf(stderr,"Failed to send %s\n",file);
	}
	fclose(fp);
      }
      else if ((ptr=strstr(file,".txt"))!=NULL){
	if(!sendSuccess(connfd,fp,"text/plain",filesize)){
	  fprintf(stderr,"Failed to send %s\n",file);
	}
	fclose(fp);
      }
      else if ((ptr=strstr(file,".jpeg"))||(ptr=strstr(file,".jpg"))!=NULL){
	if(!sendSuccess(connfd,fp,"image/jpeg",filesize)){
	  fprintf(stderr,"Failed to send %s\n",file);
	}
	fclose(fp);
      }
      else if ((ptr=strstr(file,".gif"))!=NULL){
	if(!sendSuccess(connfd,fp,"image/gif",filesize)){
	  fprintf(stderr,"Failed to send %s\n",file);
	}
	fclose(fp);
      }
      else{
	if(!sendSuccess(connfd,fp,"application/octet-stream",filesize)){
	  fprintf(stderr,"Failed to send %s\n",file);
	}
	fclose(fp);
      }
    }
  } 
  else{
  badReq:
    if(send400(connfd)==-1) perror("Failed to send 404");
    goto end;
  }
  free(file);
  free(buf);
  file = buf = NULL;
  /* If there is still contents in the "Holding Buffer" */
  if(strlen(holder)!=0) goto read;
 end:
  close(connfd);
#ifdef SERVER_DEBUG
  printf("Connection Closed\n");
#endif
  return NULL;
}

void *process(void * arg){
  ThreadPool * tp = (ThreadPool*)arg; 
  int connfd;
  /* Run until "Poison Pill" is found */
  while(1){
    /* Blocks here until a connection is available */
    connfd = removeWaiting(tp);
    /* "Poison Pill is found */
    if (connfd<0) break;
    printf("THREAD: Working new connection %d\n",connfd);
    processConn(connfd);
    printf("THREAD: Finished working connection %d\n",connfd);
  }
  return NULL;
}

int main(){
  int fd, connfd;
  struct sockaddr_in cliaddr;
  socklen_t cliaddrlen = sizeof(cliaddr);
  
  /* Attempt to connect to socket */
  if((fd = connsock(8080)) == -1){
    perror("Connection to socket failed\n");
    return -1;
  }
  
  /*Create thread pool with 64 threads */
  ThreadPool * tp = init(1);
  if (tp == NULL){
#ifdef SERVER_DEBUG
    perror("Could not create ThreadPool");
#endif
    return -1;
  }
  
  for(;;){
    /* Try to accept a connection */
    if((connfd = accept(fd, (struct sockaddr *) &cliaddr, &cliaddrlen)) == -1){
      perror("Could not accept a connection");
      close(fd);
      return -1;
    }
    /* Add connection to stack inside Thread Pool for processing */
    if (addWaiting(tp,connfd)<0){
      perror("Could not add connection to stack");
    }
#ifdef SERVER_DEBUG
    printf("MAIN: New client added to work queue\n\n");
#endif
  }
  return 1;
}


  
