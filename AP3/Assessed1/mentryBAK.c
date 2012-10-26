#ifndef _MENTRY_H_
#define _MENTRY_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include "mentry.h"

#define BUFFERSIZE 2056

typedef struct mentry {
	char *surname;
	int house_number;
	char *postcode;
	char *full_address;
} MEntry;

/* me_get returns the next file entry, or NULL if end of file*/
MEntry *me_get(FILE *fd){
  MEntry* ment = (MEntry*) malloc(sizeof(MEntry));
  if(ment==NULL){
	  printf("%s", "Malloc Error");
	  return NULL;
  }
  
  char fulladdr[BUFFERSIZE];
  

  /*
  char fpointer[BUFFERSIZE];
  char spointer[BUFFERSIZE];
  char tpointer[BUFFERSIZE];
  char fulladdr[BUFFERSIZE];
  int fulladdrpla = 0;
  int count = 0;
  int offset = 0;
  char* pplace = fpointer;
  char* sname = NULL;
  char* pcode = NULL;
  char* f_address = NULL;
  char* f_begin_adr = NULL;
  int h_number = 0;
  // FIRST NAME 
  fgets(fpointer,BUFFERSIZE,fd);
  while (*pplace != ','){
    if (*pplace == '\n'){
   
    }
    pplace++;
	count++;
  }
  sname = malloc(sizeof(pplace-fpointer));
  *pplace = '\0';
  sname = strcpy(sname, fpointer);
  ment->surname = sname;
  *pplace = ',';
  while(*pplace != '\n'){
    pplace++;
	count++;
  }
  while(offset<=count){
    fulladdr[fulladdrpla] = fpointer[offset];
    fulladdrpla++;
	offset++;
  }
  offset = 0;
  count = 0;
  
  /* DOOR NUMBER 
  fgets(spointer,BUFFERSIZE,fd);
  pplace = spointer;
  while(*pplace == ' '){pplace++;}
  char* intfind = pplace;
  char* inthold;
  count = 0;
  while(*pplace != ' '){
	  pplace++;
	  count++;
  }
  inthold = malloc(sizeof(pplace-intfind));
  h_number = atoi(strcpy(inthold,intfind));
  while(*pplace != '\n'){
    pplace++;
	count++;
  }
  offset = 0;
  while(offset<=count){
	  fulladdr[fulladdrpla] = spointer[offset];
	  fulladdrpla++;
	  offset++;
  }
  ment->house_number = h_number;
  offset = 0;
  count = 0;
  
  /* POST CODE 
  fgets(tpointer,BUFFERSIZE,fd);
  pplace = fpointer;
  while (*pplace != '\n'){
    pplace++;
	count++;
  }
  pcode = malloc(sizeof(pplace-tpointer));
  *pplace = '\0';
  strcpy(pcode,tpointer);
  ment->postcode = pcode;
  while(offset<=count){
    fulladdr[fulladdrpla] = tpointer[offset];
    fulladdrpla++;
    offset++;
  }

  f_begin_adr = fulladdr;
  pplace = f_begin_adr + fulladdrpla;
  f_address = malloc(sizeof(pplace-f_begin_adr));
  strcpy(f_address,fulladdr);
  ment->full_address = f_address;
  return ment; */
}


/* me_hash computes a hash of the MEntry, mod size */
unsigned long me_hash(MEntry *me, unsigned long size){
  return 0l;
}

/* me_print prints the full address on fd */
void me_print(MEntry *me, FILE *fd){
	char* output;
	output = me->full_address;
	fputs(output,fd);
	fputc('\n',fd);
  return;
}

/* me_compare compares two mail entries, returning <0, 0, >0 if
 * me1<me2, me1==me2, me1>me2
 */
int me_compare(MEntry *me1, MEntry *me2){
	char* me1p = me1->full_address;
	char* me2p = me2->full_address;
  return 0;
}

/* me_destroy destroys the mail entry
 */
void me_destroy(MEntry *me){
  return;
}

#endif /* _MENTRY_H_ */
