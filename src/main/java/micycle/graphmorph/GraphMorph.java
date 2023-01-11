package micycle.graphmorph;

import java.awt.Color;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

class GraphMorph extends Frame implements WindowListener {
  GraphMorphNonIntersect np;
  
  public void main() {
    GraphMorph graphMorph = new GraphMorph();
    graphMorph.setSize(1000, 700);
    graphMorph.doJob();
    graphMorph.setTitle("Graph Morphing");
    graphMorph.show();
  }
  
  void doJob() {
    Color color = new Color(200, 200, 200);
    setBackground(color);
    addWindowListener(this);
    this.np = new GraphMorphNonIntersect(this);
    setLayout(null);
    this.np.setBounds(10, 50, getWidth() - 20, getHeight() - 50);
    add(this.np);
  }
  
  public void windowActivated(WindowEvent paramWindowEvent) {}
  
  public void windowClosed(WindowEvent paramWindowEvent) {}
  
  public void windowClosing(WindowEvent paramWindowEvent) {
    dispose();
  }
  
  public void windowDeactivated(WindowEvent paramWindowEvent) {}
  
  public void windowDeiconified(WindowEvent paramWindowEvent) {}
  
  public void windowIconified(WindowEvent paramWindowEvent) {}
  
  public void windowOpened(WindowEvent paramWindowEvent) {}
}
