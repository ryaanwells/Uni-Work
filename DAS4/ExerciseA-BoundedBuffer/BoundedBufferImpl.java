public class BoundedBufferImpl implements BoundedBuffer {

	private Object[] buffer;
	private int size = 20;
	
	private int head = 0;
	private int tail = 0;
	
	BoundedBufferImpl(int size) {
		buffer = new Object[size];
		this.size = size;
	}
	
	BoundedBufferImpl(){
		buffer = new Object[size];
	}

	@Override
	public synchronized void insert(Object o) {
		while ((head-tail)%size !=0 && buffer[head]!=null){
			try{
				wait();
			}
			catch (InterruptedException e){}
		}
		buffer[head] = o;
		head = (head + 1) % size;
		notifyAll();
	}

	@Override
	public synchronized Object remove() {
		while((head-tail)%size ==0 && buffer[tail]==null){
			try{
				wait();
			}
			catch (InterruptedException e){}
		}
		Object justRemoved = buffer[tail];
		buffer[tail] = null;
		tail = (tail + 1) % size;
		notifyAll();
		return justRemoved;
	}

}
