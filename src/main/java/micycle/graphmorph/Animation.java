package micycle.graphmorph;

class Animation extends Thread {
  GraphMorphNonIntersect ref;
  
  int delay = 40;
  
  int d = 0;
  
  boolean keepRunning = true;
  
  Timer t;
  
  Animation(GraphMorphNonIntersect paramGraphMorphNonIntersect) {
    this.ref = paramGraphMorphNonIntersect;
    this.t = new Timer(this);
    this.t.setPriority(1);
    this.t.start();
  }
  
  public void run() {
    while (this.keepRunning) {
      this.d = 0;
      if (this.ref.doAnimate)
        this.ref.tick(); 
      if (this.ref.doAnimate)
        this.ref.curFrame += this.d; 
    } 
    this.t.keepRunning = false;
  }
}
