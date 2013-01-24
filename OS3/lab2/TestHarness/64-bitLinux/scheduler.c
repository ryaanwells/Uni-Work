#include "generic_queue.h"
#include "diagnostics.h"
#include "scheduler.h"

GQueue* Queue[33];
TCB *current;
TCB *idle;

void init(){
	int i;
	DIAGNOSTICS("Starting");
	for (i=0;i<33;i++){
		Queue[i] = create_gqueue();
	}
	current = NULL;
	idle = get_idle_task();
}

void add_runnable_TCB(TCB *t){
	int priority;
	DIAGNOSTICS("Scheduler: Adding new runnable TCB\n");
	priority = get_static_priority(t);
	DIAGNOSTICS("Scheduler: Priority of new task is %d\n",priority);
	if(gqueue_enqueue(Queue[priority],t)){
		DIAGNOSTICS("Scheduler: Added to Priority Queue: %d\n",priority);
	}
}

void block(){
	int i;
	DIAGNOSTICS("Scheduler: Received blocking notification\n");
	move_from_CPU_to_TCB(current);
	DIAGNOSTICS("Scheduler: Adding current task to blocked queue\n");
	add_blocked_TCB(current);
	DIAGNOSTICS("Scheduler: Changing current to first on ready queue\n");
	for(i = 32;i>=0;i--){
		if(gqueue_dequeue(Queue[i],(void**)&current)){
			break;
		}
		current = idle;
	}
	DIAGNOSTICS("Scheduler: Running current program\n");
	move_from_TCB_to_CPU(current);
}

void scheduler(){
	int priority,i;
	DIAGNOSTICS("Scheduler: Timeslice expired!\n");
	if(!(current == NULL)){
		DIAGNOSTICS("Scheduler: Removing current program from CPU\n");
		move_from_CPU_to_TCB(current);
		if(!(current == idle)){
			priority = get_static_priority(current);
			if(!(gqueue_enqueue(Queue[priority],current))){
				DIAGNOSTICS("Scheduler: Addition failed\n");
			}
		}
	}
		for(i = 32;i>=0;i--){
			if(gqueue_dequeue(Queue[i],(void**)&current)){
				break;
				DIAGNOSTICS("Success find\n");
			}
			current = idle;
		}
		DIAGNOSTICS("Scheduler: Running current program\n");
		move_from_TCB_to_CPU(current);
	
}
