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
#include <unistd.h>
#include "networkdevice.h"

BoundedBuffer sendBuffer;
BoundedBuffer * recvArray;
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
      if(send_packet(ND,PD)) break;
      DIAGNOSTICS("NETWORK DRIVER: Backing off for 0.%d seconds. Packet for PID %d", 100*i, packet_descriptor_get_pid(&PD));
      sleep(100*i);
    }
    if(i==3) DIAGNOSTICS("NETWORK DRIVER: Could not send packet for PID %d", packet_descriptor_get_pid(&PD));
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
      nonblockingWriteBB(recvArray[packet_descriptor_get_pid(&PD)],PD);
      PD = EMERGENCY;
    }
  }
}

void blocking_send_packet(PacketDescriptor PD){
  DIAGNOSTICS("NETWORK DRIVER: Attempting to add blocking for PID:%d\n",packet_descriptor_get_pid(&PD));
  blockingWriteBB(sendBuffer,PD);
}

int  nonblocking_send_packet(PacketDescriptor PD){
  DIAGNOSTICS("NETWORK DRIVER: Attempting to add non blocking for PID:%d\n",packet_descriptor_get_pid(&PD));
  return nonblockingWriteBB(sendBuffer,PD);
}

void blocking_get_packet(PacketDescriptor* PD, PID pid){
  DIAGNOSTICS("NETWORK DRIVER: Attempting to get blocking for PID:%d\n",pid);
  *PD = blockingReadBB(recvArray[pid]);
}

int  nonblocking_get_packet(PacketDescriptor* PD, PID pid){
  DIAGNOSTICS("NETWORK DRIVER: Attempting to get non blocking for PID:%d\n",pid);
  return nonblockingReadBB(recvArray[pid],PD);
}

void init_network_driver(NetworkDevice               nd, 
                         void *                      mem_start, 
                         unsigned long               mem_length,
                         FreePacketDescriptorStore * fpds_ptr){
  int i;
  DIAGNOSTICS("INIT: Creating FPDS\n");
  FPDS = create_fpds();
  create_free_packet_descriptors(FPDS,mem_start,mem_length);
  *fpds_ptr = FPDS;
  DIAGNOSTICS("INIT: Creating Buffers\n");
  sendBuffer = createBB(MAX_PID+1);
  recvArray = (BoundedBuffer*)malloc(sizeof(BoundedBuffer)*(MAX_PID+1));
  for(i=0;i<MAX_PID+1;i++){
    recvArray[i] = createBB(1);
  }
  ND = nd;
  DIAGNOSTICS("INIT: Creating Threads\n");
  pthread_create(&sendThread,NULL,sendToSocket,NULL);
  pthread_create(&readThread1,NULL,recvFromSocket,NULL);
  pthread_create(&readThread2,NULL,recvFromSocket,NULL);
  pthread_detach(sendThread);
  pthread_detach(readThread1);
  pthread_detach(readThread2);
  DIAGNOSTICS("INIT: Init complete.\n");
}
