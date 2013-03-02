#include "packetdescriptor.h"
#include "packetdescriptorcreator.h"
#include "destination.h"
#include "pid.h"
#include "freepacketdescriptorstore.h"
#include "freepacketdescriptorstore__full.h"
#include "BoundedBuffer.h"
#include "diagnostics.h"
#include "TSArray.h"
#include <stdlib.h>
#include <pthread.h>
#include "networkdevice.h"

BoundedBuffer sendBuffer;
TSArray * recvArray;
FreePacketDescriptorStore FPDS;
NetworkDevice ND;
pthread_mutex_t sockLock = PTHREAD_MUTEX_INITIALIZER;
pthread_t sendThread;
pthread_t readThread1;
pthread_t readThread2;

void *sendToSocket(void * arg){
  PacketDescriptor PD;
  int i;
  for(;;){
    PD = blockingReadBB(sendBuffer);
    for(i=0;i<3;i++){
      send_packet(ND,PD);
    }
    if(i==3) DIAGNOSTICS("Could not send packet\n");
    blocking_put_pd(FPDS,PD);
  }
}

void *recvFromSocket(void * arg){
  PacketDescriptor PD;
  PacketDescriptor EMERGENCY;
  int new;
  blocking_get_pd(FPDS,&PD);
  for(;;){
    init_packet_descriptor(&PD);
    pthread_mutex_lock(&sockLock);
    register_receiving_packetdescriptor(ND,&PD);
    await_incoming_packet(ND);
    pthread_mutex_unlock(&sockLock);
    if((new = nonblocking_get_pd(FPDS,&EMERGENCY))==1){
      addToTSA(recvArray,packet_descriptor_get_pid(&PD),PD);
      PD = EMERGENCY;
    }
  }
}

void blocking_send_packet(PacketDescriptor PD){
  DIAGNOSTICS("Attempting to add blocking for PID:%d\n",packet_descriptor_get_pid(&PD));
  blockingWriteBB(sendBuffer,PD);
}

int  nonblocking_send_packet(PacketDescriptor PD){
  DIAGNOSTICS("Attempting to add non blocking for PID:%d\n",packet_descriptor_get_pid(&PD));
  return nonblockingWriteBB(sendBuffer,PD);
}
/* These calls hand in a PacketDescriptor for dispatching */
/* The nonblocking call must return promptly, indicating whether or */
/* not the indicated packet has been accepted by your code          */
/* (it might not be if your internal buffer is full) 1=OK, 0=not OK */
/* The blocking call will usually return promptly, but there may be */
/* a delay while it waits for space in your buffers.                */
/* Neither call should delay until the packet is actually sent      */

void blocking_get_packet(PacketDescriptor* PD, PID pid){
  DIAGNOSTICS("Attempting to get blocking for PID:%d\n",pid);
  *PD = getFromTSABlocking(recvArray,pid);
}

int  nonblocking_get_packet(PacketDescriptor* PD, PID pid){
  DIAGNOSTICS("Attempting to get blocking for PID:%d\n",pid);
  *PD = getFromTSA(recvArray,pid);
  return(PD == NULL? 0:1);
}
/* These represent requests for packets by the application threads */
/* The nonblocking call must return promptly, with the result 1 if */
/* a packet was found (and the first argument set accordingly) or  */
/* 0 if no packet was waiting.                                     */
/* The blocking call only returns when a packet has been received  */
/* for the indicated process, and the first arg points at it.      */
/* Both calls indicate their process number and should only be     */
/* given appropriate packets. You may use a small bounded buffer   */
/* to hold packets that haven't yet been collected by a process,   */
/* but are also allowed to discard extra packets if at least one   */
/* is waiting uncollected for the same PID. i.e. applications must */
/* collect their packets reasonably promptly, or risk packet loss. */

void init_network_driver(NetworkDevice               nd, 
                         void *                      mem_start, 
                         unsigned long               mem_length,
                         FreePacketDescriptorStore * fpds_ptr){
  DIAGNOSTICS("INIT: Creating FPDS\n");
  FPDS = create_fpds();
  create_free_packet_descriptors(FPDS,mem_start,mem_length);
  fpds_ptr = FPDS;
  DIAGNOSTICS("INIT: Creating Buffers\n");
  sendBuffer = createBB(6);
  recvArray = createTSArray(MAX_PID+1);
  ND = nd;
  DIAGNOSTICS("INIT: Creating Threads\n");
  pthread_create(&sendThread,NULL,sendToSocket,NULL);
  pthread_create(&readThread2,NULL,recvFromSocket,NULL);
  pthread_create(&readThread1,NULL,recvFromSocket,NULL);
  DIAGNOSTICS("INIT: Init complete.\n");
}
/* Called before any other methods, to allow you to initialise */
/* data structures and start any internal threads.             */ 
/* Arguments:                                                  */
/*   nd: the NetworkDevice that you must drive,                */
/*   mem_start, mem_length: some memory for PacketDescriptors  */
/*   fpds_ptr: You hand back a FreePacketDescriptorStore into  */
/*             which you have put the divided up memory        */
/* Hint: just divide the memory up into pieces of the right size */
/*       passing in pointers to each of them                     */ 
