#ifndef _MENTRY_H_
#define _MENTRY_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>

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
  scanf(fpointer, "%d",&h_number);
  while(*pplace != '\n'){
    pplace++;
  }
  *pplace = *pplace + fulladdrpla;
  while(fulladdrpla<=*pplace){
    fulladdr[fulladdrpla] = fpointer[fulladdrpla];
    fulladdrpla++;
  }
  ment->house_number = h_number;
  fgets(fpointer,BUFFERSIZE,fd);
  pplace = fpointer;
  while (*pplace != ' '){
    pplace++;
  }
  pplace = pplace+3;
  pcode = malloc(sizeof(fpointer-pplace));
  *(pplace++) = '\0';
  strcpy(pcode,fpointer);
  ment->postcode = pcode;
  *pplace = *pplace + fulladdrpla;
  while(fulladdrpla<=*pplace){
    fulladdr[fulladdrpla] = fpointer[fulladdrpla];
    fulladdrpla++;
  }
  f_address = malloc(sizeof(pplace));
  strcpy(f_address,fulladdr);
  ment->full_address = f_address;
}


/* me_hash computes a hash of the MEntry, mod size */
unsigned long me_hash(MEntry *me, unsigned long size);

/* me_print prints the full address on fd */
void me_print(MEntry *me, FILE *fd);

/* me_compare compares two mail entries, returning <0, 0, >0 if
 * me1<me2, me1==me2, me1>me2
 */
int me_compare(MEntry *me1, MEntry *me2);

/* me_destroy destroys the mail entry
 */
void me_destroy(MEntry *me);

#endif /* _MENTRY_H_ */
