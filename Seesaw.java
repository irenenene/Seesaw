import java.util.concurrent.*;

public class Seesaw {
    static class Counter {
      public volatile int count = 20;
      public volatile boolean leftUp = true;
      public volatile float velocity = 1;
    }

    static class Fred implements Runnable {
      public float VELOCITY = 1;
      //public int MAX_HEIGHT = 7;
      public Semaphore p;
      public Counter c;
      public float position = 1;

      public Fred (Semaphore p, Counter c) {
        this.c = c;
        this.p = p;
      }

      public void run() {
        while (c.count > 0) {
          try {
            Thread.sleep(1000);
            if (position <= 1.0) {
              c.leftUp = true;
              c.velocity = VELOCITY;
            }
            if(c.leftUp)
              position += c.velocity;
            else {
              position -= c.velocity;
            }
            System.out.println("Fred: " + position);
            c.count--;
            p.release();
          }
          catch (InterruptedException e) {

          }
        }
      }
    }

    static class Wilma implements Runnable {
      public float VELOCITY = (float)1.5;
      //public int MAX_HEIGHT = 7;
      public Semaphore p;
      public Counter c;
      public float position = 7;

      public Wilma (Semaphore p, Counter c) {
        this.p = p;
        this.c = c;
      }

      public void run() {
        while(c.count > 0) {
          try {
            p.acquire();
            if(c.leftUp) {
              position -= c.velocity;
            }
            else {
              position += c.velocity;
            }
            if (position <= 1.0) {
              c.leftUp = false;
              c.velocity = VELOCITY;
            }
            System.out.println("Wilma: " + position);
            //p.acquire();
          }
          catch (InterruptedException e) {

          }
        }
      }
    }

    public static void main(String args[]) {
      final Counter c = new Counter();
      Semaphore pSemaphore = new Semaphore(0);
      //Semaphore cSemaphore;
      Thread fThread = new Thread(new Fred(pSemaphore, c));
      Thread wThread = new Thread(new Wilma(pSemaphore, c));

      fThread.start();
      wThread.start();

      try {
        fThread.join();
        wThread.join();
      }
      catch (InterruptedException e) {
        throw new IllegalStateException(e);
      }
    }
}
