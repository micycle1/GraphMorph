package micycle.graphmorph;

class Timer extends Thread {
  Animation ref;
  
  boolean keepRunning = true;
  
  Timer(Animation paramAnimation) {
    this.ref = paramAnimation;
  }
  
  public void run() {
    while (this.keepRunning) {
      if (this.ref.ref.doAnimate)
        this.ref.d += this.ref.ref.dir; 
      try {
        Thread.sleep(this.ref.delay);
      } catch (InterruptedException interruptedException) {}
    } 
  }
}
