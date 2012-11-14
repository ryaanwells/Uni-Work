public class Producer implements Runnable {

   private String name;
   private BoundedBuffer bb;
   private int nmsgs;

   public Producer(int i, BoundedBuffer bb, int nmsgs) {
      this.name = "Producer"+i;
      this.bb = bb;
      this.nmsgs = nmsgs;
   }

   public void run() {
      for (int i = 0; i < nmsgs; i++) {
         String msg = new String(name+": message "+i);
         bb.put(msg);
      }
   }
}
