#include <stdio.h>
#include "string.h"

#define MAXLINE 1000 /* maximum input line length */

/* print all lines from standard input that match pattern */
int main(int argc, char* argv[])
{
	if (argc<2 || argc>3){
		printf("%s\n", "usage: find [-x] pattern");
		return -1;
	}
	char* pattern = argv[1];
	char line[MAXLINE];
	int found = 0;
	if (argc==3){
		if(strstr(pattern,"-x") ==NULL){
			printf("%s\n", "usage: find [-x] pattern");
			return -1;
		}
		char* pattern = argv[2];
		while(fgets(line,MAXLINE,stdin) != NULL)
			if(strstr(line,pattern) == NULL){
				printf("%s",line);
				found++;
			}
		return found;
	}
	while (fgets(line, MAXLINE, stdin) != NULL)
		if (strstr(line, pattern) != NULL) {
			printf("%s", line);
			found++;
		}
	return found;
}
