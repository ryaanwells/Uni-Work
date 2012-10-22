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
  
  char firstline[BUFFERSIZE];
  char secondline[BUFFERSIZE];
  char thirdline[BUFFERSIZE];
  char fulladdr[BUFFERSIZE];
  char *sname;
  char *ptr;
  char *pcode;
  char *fadd;
  int count = 0;
  int doornum = 0;
  fgets(firstline,BUFFERSIZE,fd);
  ptr = firstline;
   
  while (*ptr != ','){
	  fulladdr[count] = *ptr;
	  ptr++;
	  count++;
  }
  sname = malloc(sizeof(char)*(ptr-firstline) + 1);
  strncat(sname,firstline,(sizeof(char)*(ptr-firstline)));
  ment->surname = sname;
  while (*ptr != '\n'){
	  fulladdr[count] = *ptr;
	  ptr++;
	  count++;
  }
  fulladdr[count] = *ptr;
  fulladdr[++count] = '\n';

  fgets(secondline,BUFFERSIZE,fd);
  ptr = secondline;
  doornum = atoi(ptr);
  ment->house_number = doornum;
  while (*ptr != '\n'){
	  fulladdr[count] = *ptr;
	  ptr++;
	  count++;
  }
  fulladdr[count] = *ptr;
  fulladdr[++count] = '\n';
  fgets(thirdline,BUFFERSIZE,fd);
  ptr = thirdline;
  while (*ptr != '\n'){
	  fulladdr[count] = *ptr;
	  ptr++;
	  count++;
  }
  fulladdr[count] = *ptr;
  fulladdr[++count] = '\0';
  pcode = malloc(sizeof(char)*(ptr-thirdline)+1);
  strncat(pcode,thirdline,(sizeof(char)*(ptr-thirdline)));
  ment->postcode = pcode;
  ptr = &fulladdr[count];
  fadd = malloc(sizeof(char)*(ptr-fulladdr)+1);
  strncat(fadd,fulladdr,(sizeof(char)*(ptr-fulladdr)));
  printf("%s",fulladdr);
  ment->full_address = fadd;
  return ment;
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
