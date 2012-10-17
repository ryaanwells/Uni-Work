#ifndef _MENTRY_H_
#define _MENTRY_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "mentry.h"

#define BUFFERSIZE 1024

typedef struct mentry {
	char *surname;
	int house_number;
	char *postcode;
	char *full_address;
} MEntry;

/* me_get returns the next file entry, or NULL if end of file*/
MEntry *me_get(FILE *fd){
  MEntry* ment = (MEntry*) malloc(sizeof(MEntry));
  char fpointer[BUFFERSIZE];
  char fulladdr[BUFFERSIZE];
  int fulladdrpla = 0;
  char* pplace = fpointer;
  char* sname;
  char* pcode;
  char* f_address;
  int h_number;

  fgets(fpointer,BUFFERSIZE,fd);
  while (*pplace != ','){
    if (*pplace == '\n'){
      /* DO SOMETHING TO DEAL WITH NO SURNAME */
    }
    pplace++;
  }
  sname = malloc(sizeof(pplace-fpointer));;
  *pplace = '\0';
  sname = strcpy(sname, fpointer);
  ment->surname = sname;
  *pplace = ',';
  while(*pplace != '\n'){
    pplace++;
  }
  while(fulladdrpla<=*pplace){
    fulladdr[fulladdrpla] = fpointer[fulladdrpla];
    fulladdrpla++;
  }
  fgets(fpointer,BUFFERSIZE,fd);
  pplace = fpointer;
  while(*pplace == ' '){pplace++;}
  char* intfind = pplace;
  char* inthold;
  while(*pplace != ' '){pplace++;}
  inthold = malloc(sizeof(pplace-intfind));
  h_number = atoi(strcpy(inthold,intfind));
  while(*pplace != '\n'){
    pplace++;
  }
  int offset = 0;
  while(offset<=*pplace){
    fulladdr[fulladdrpla] = fpointer[offset];
    fulladdrpla++;
    offset++;
  }
  ment->house_number = h_number;
  fgets(fpointer,BUFFERSIZE,fd);
  pplace = fpointer;
  while (*pplace != ' '){
    pplace++;
  }
  pplace = pplace+4;
  pcode = malloc(sizeof(fpointer-pplace));
  *(pplace++) = '\0';
  strcpy(pcode,fpointer);
  ment->postcode = pcode;
  offset = 0;
  while(offset<=*pplace){
    fulladdr[fulladdrpla] = fpointer[offset];
    fulladdrpla++;
    offset++;
  }
  *pplace= fulladdr[fulladdrpla];
  *fpointer = fulladdr[0];
  f_address = malloc(sizeof(pplace-fpointer));
  strcpy(f_address,fulladdr);
  ment->full_address = f_address;
  /*free(pplace); */
  printf("%s\n%i\n%s\n%s\n",sname,h_number,pcode,f_address);
  return ment;
}


/* me_hash computes a hash of the MEntry, mod size */
unsigned long me_hash(MEntry *me, unsigned long size){
  return 0l;
}

/* me_print prints the full address on fd */
void me_print(MEntry *me, FILE *fd){
  return;
}

/* me_compare compares two mail entries, returning <0, 0, >0 if
 * me1<me2, me1==me2, me1>me2
 */
int me_compare(MEntry *me1, MEntry *me2){
  return 0;
}

/* me_destroy destroys the mail entry
 */
void me_destroy(MEntry *me){
  return;
}

#endif /* _MENTRY_H_ */
