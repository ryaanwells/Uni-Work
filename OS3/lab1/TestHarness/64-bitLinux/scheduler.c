#include "generic_queue.h"
#include "diagnostics.h"
#include "scheduler.h"

GQueue *gq;
TCB *current;
TCB *idle;

void init(){
	DIAGNOSTICS("Starting");
	gq = create_gqueue();
	current = NULL;
	idle = get_idle_task();
}

void add_runnable_TCB(TCB *t){
	DIAGNOSTICS("Scheduler: Adding new runnable TCB\n");
	if(!(gqueue_enqueue(gq, t))){
		DIAGNOSTICS("Scheduler: Unable to add to queue\n");
	}
	if (current == idle){
		scheduler();
	}
}

void block(){
	DIAGNOSTICS("Scheduler: Received blocking notification\n");
	move_from_CPU_to_TCB(current);
	DIAGNOSTICS("Scheduler: Adding current task to blocked queue\n");
	add_blocked_TCB(current);
	DIAGNOSTICS("Scheduler: Changing current to first on ready queue\n");
	if(!(gqueue_dequeue(gq,(void **) &current))){
		DIAGNOSTICS("Scheduler: Unable to remove from queue, current task is now IDLE\n");
		current = idle;
	}
	DIAGNOSTICS("Scheduler: Running current program\n");
	move_from_TCB_to_CPU(current);
}

void scheduler(){
	DIAGNOSTICS("Scheduler: Timeslice expired!\n");
	if(!(current == NULL)){
		DIAGNOSTICS("Scheduler: Removing current program from CPU\n");
		move_from_CPU_to_TCB(current);
		DIAGNOSTICS("Scheduler: Adding to end of run queue\n");
		if(!(gqueue_enqueue(gq,current))){
			DIAGNOSTICS("Scheduler: Unable to add to queue\n");
		}
	}
	DIAGNOSTICS("Scheduler: Removing next run task\n");
	if(!(gqueue_dequeue(gq,(void **)&current))){
		DIAGNOSTICS("Scheduler: Unable to remove from queue, current task is now IDLE\n");
		current = idle;
	}
	DIAGNOSTICS("Scheduler: Running current program\n");
	move_from_TCB_to_CPU(current);
}
