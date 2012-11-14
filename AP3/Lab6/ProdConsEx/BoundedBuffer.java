public class BoundedBuffer {

   private int count;
   private int in;
   private int out;
   private int size;
   String[] buffer;

   public BoundedBuffer(int N) {
      count = 0;
      in = 0;
      out = 0;
      size = N;
      buffer = new String[N];
   }

   public synchronized void put(String s) {
      while (count == size)
         try {
            wait();
	 } catch (Exception e) {};
      buffer[in] = s;
      in = (in + 1) % size;
      count++;
      notifyAll();
   }

   public synchronized String get() throws InterruptedException {
      while (count == 0)
         wait();
      String s = buffer[out];
      out = (out + 1) % size;
      count--;
      notifyAll();
      return s;
   }
}
