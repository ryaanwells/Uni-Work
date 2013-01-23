#include <sys/types.h>
#include <sys/socket.h>
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

int linelength(char *start, char *eol){
	int count = 0;
	char *linestart = start;
	char *eolstart = eol;
	return count;
}


int main(int argc, char* argv[])
{
	FILE *pFile;
	char mystring [100];

	pFile = fopen ("testData.txt" , "r");
	if (pFile == NULL) perror ("Error opening file");
	else {
		if ( fgets (mystring , 100 , pFile) != NULL )
			fprintf(stdout,"%s",mystring);
		fclose (pFile);
	}
	return 0;
}

