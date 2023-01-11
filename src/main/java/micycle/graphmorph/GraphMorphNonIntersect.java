package micycle.graphmorph;

import java.awt.BasicStroke;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Choice;
import java.awt.Color;
import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Panel;
import java.awt.Scrollbar;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Arrays;

class GraphMorphNonIntersect extends Panel implements MouseListener, MouseMotionListener, AdjustmentListener, ItemListener, ActionListener {

	final int SIZE = 20;
	final int MAX_NODES = 200;
	final int MAX_LINKS = 800;
	final int MAX_BOUND = 100;
	Color background;
	Font font12;
	Font font12b;
	Font font12i;
	Font font10;
	Checkbox initialGraph;
	Checkbox finalGraph;
	CheckboxGroup viewGraph;
	Scrollbar opacity;
	Scrollbar frames;
	Scrollbar frameRate;
	Scrollbar totFrames;
	Scrollbar red;
	Scrollbar green;
	Scrollbar blue;
	TextField opacityText;
	TextField framesText;
	TextField totFramesText;
	TextField frameRateText;
	TextField redText;
	TextField greenText;
	TextField blueText;
	TextField idText;
	Button playBackward;
	Button playForward;
	Button pause;
	Button finish;
	Button start;
	Button id;
	Button animate;
	Button reset;
	Color[][] colors;
	Color curColor;
	Color[][] nodeColor;
	Color custColor;
	boolean selectNode;
	boolean selectArc;
	boolean drawing;
	boolean animateMode;
	boolean doAnimate;
	boolean changed;
	int curGraph;
	int[] curNode;
	int[][] nodeID;
	int[][] nodeX;
	int[][] nodeY;
	int arcStart;
	int[][] aNodeX;
	int[][] anodeY;
	int blinkColor;
	int curFrame;
	int dir;
	int downX;
	int downY;
	int downNode;
	int[] nodeCount;
	int[][][] link;
	int[] curLink;
	Animation t;
	Image bi;
	double[][] rad;
	double[][] theta;
	double uX0;
	double uY0;
	double uY1;
	double uX1;
	boolean down;
	Choice ch;
	Choice bound;
	double a11;
	double a12;
	double a13;
	double a21;
	double a22;
	double a23;
	double q11;
	double q12;
	double q21;
	double q22;
	double s11;
	double s12;
	double s21;
	double s22;
	double rtheta;
	double cx;
	double cy;
	FontMetrics fm;
	double[][] lamda0;
	double[][] lamda1;
	int nc;
	int[] curNodes;
	int[] edgeCount;
	int correctEc;
	int correctNc;
	int bc;
	int[] borderNodes;
	int[][] extraNodes;
	int extraNodeCount;
	Checkbox showSteiner;
	Button findPolygon;
	Button save;
	Button open;
	GraphMorph ref;

	GraphMorphNonIntersect(final GraphMorph ref) {
		this.viewGraph = new CheckboxGroup();
		this.colors = new Color[6][5];
		this.nodeColor = new Color[2][200];
		this.selectNode = false;
		this.animateMode = false;
		this.doAnimate = false;
		this.changed = false;
		this.curGraph = 0;
		this.curNode = new int[2];
		this.nodeID = new int[2][200];
		this.nodeX = new int[2][200];
		this.nodeY = new int[2][200];
		this.arcStart = -1;
		this.aNodeX = new int[2][200];
		this.anodeY = new int[2][200];
		this.blinkColor = 100;
		this.curFrame = 0;
		this.nodeCount = new int[2];
		this.link = new int[2][800][2];
		this.curLink = new int[2];
		this.rad = new double[2][100];
		this.theta = new double[2][100];
		this.down = false;
		this.lamda0 = new double[200][200];
		this.lamda1 = new double[200][200];
		this.curNodes = new int[200];
		this.edgeCount = new int[2];
		this.borderNodes = new int[200];
		this.extraNodes = new int[100][2];
		this.extraNodeCount = 0;
		this.ref = ref;
		this.setBackground(this.background = new Color(200, 200, 200));
		this.font10 = new Font("Arial", 0, 10);
		this.font12 = new Font("Arial", 0, 12);
		this.font12b = new Font("Arial", 1, 12);
		this.font12i = new Font("Arial", 2, 12);
		this.fm = this.getFontMetrics(this.font10);
		this.bi = this.createImage(600, 550);
		for (int i = 5; i > 0; --i) {
			this.colors[0][i - 1] = new Color(250 * i / 5, 0, 0);
		}
		for (int j = 5; j > 0; --j) {
			this.colors[1][j - 1] = new Color(0, 250 * j / 5, 0);
		}
		for (int k = 5; k > 0; --k) {
			this.colors[2][k - 1] = new Color(0, 0, 250 * k / 5);
		}
		for (int l = 5; l > 0; --l) {
			this.colors[3][l - 1] = new Color(250 * l / 5, 250 * l / 5, 0);
		}
		for (int n = 5; n > 0; --n) {
			this.colors[4][n - 1] = new Color(0, 250 * n / 5, 250 * n / 5);
		}
		for (int n2 = 5; n2 > 0; --n2) {
			this.colors[5][n2 - 1] = new Color(250 * n2 / 5, 0, 250 * n2 / 5);
		}
		this.makePanel();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.curColor = this.colors[0][4];
		this.curNode[0] = (this.curNode[1] = 0);
		this.nodeCount[0] = (this.nodeCount[0] = 0);
		this.custColor = new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue());
		for (int n3 = 0; n3 < 200; ++n3) {
			this.nodeColor[0][n3] = (this.nodeColor[1][n3] = Color.white);
		}
		((Thread) (this.t = new Animation(this))).setPriority(1);
		((Thread) this.t).start();
	}

	public void destroy() {
		this.t.keepRunning = false;
	}

	void makePanel() {
		(this.initialGraph = new Checkbox("Initial Graph", this.viewGraph, true)).addItemListener(this);
		(this.finalGraph = new Checkbox("Final Graph", this.viewGraph, false)).addItemListener(this);
		(this.opacity = new Scrollbar(0, 30, 1, 0, 101)).addAdjustmentListener(this);
		(this.opacityText = new TextField("30%")).setEditable(false);
		(this.framesText = new TextField("0")).setEditable(false);
		(this.animate = new Button("Animate")).addActionListener(this);
		(this.reset = new Button("Reset")).addActionListener(this);
		(this.id = new Button("Update")).addActionListener(this);
		this.idText = new TextField();
		this.red = new Scrollbar(0, 0, 1, 0, 256);
		this.green = new Scrollbar(0, 0, 1, 0, 256);
		this.blue = new Scrollbar(0, 0, 1, 0, 256);
		this.redText = new TextField("0");
		this.greenText = new TextField("0");
		this.blueText = new TextField("0");
		this.redText.setEditable(false);
		this.greenText.setEditable(false);
		this.blueText.setEditable(false);
		this.red.addAdjustmentListener(this);
		this.green.addAdjustmentListener(this);
		this.blue.addAdjustmentListener(this);
		(this.start = new Button("<<")).addActionListener(this);
		this.start.setEnabled(false);
		(this.playBackward = new Button("<")).addActionListener(this);
		this.playBackward.setEnabled(false);
		(this.pause = new Button("||")).addActionListener(this);
		this.pause.setEnabled(false);
		(this.playForward = new Button(">")).addActionListener(this);
		this.playForward.setEnabled(false);
		(this.finish = new Button(">>")).addActionListener(this);
		this.finish.setEnabled(false);
		(this.totFrames = new Scrollbar(0, 100, 10, 10, 1010)).addAdjustmentListener(this);
		(this.frameRate = new Scrollbar(0, 25, 1, 1, 101)).addAdjustmentListener(this);
		this.totFramesText = new TextField("100");
		this.frameRateText = new TextField("25");
		this.totFramesText.setEditable(false);
		this.frameRateText.setEditable(false);
		(this.frames = new Scrollbar(0, 0, 1, 0, 101)).addAdjustmentListener(this);
		this.frames.setEnabled(false);
		(this.ch = new Choice()).addItem("Linear");
		this.ch.addItem("Rigid");
		this.ch.addItem("Convex");
		this.ch.addItem("final");
		this.ch.select(3);
		this.bound = new Choice();
		for (int i = 0; i < 18; ++i) {
			this.bound.addItem("" + (i + 3));
		}
		this.bound.select(0);
		(this.showSteiner = new Checkbox("Show Steiner points")).setState(true);
		this.showSteiner.addItemListener(this);
		(this.findPolygon = new Button("Triangulate")).addActionListener(this);
		(this.save = new Button("Save File")).addActionListener(this);
		(this.open = new Button("Load File")).addActionListener(this);
		this.setLayout(null);
		this.addComponents();
	}

	void addComponents() {
		this.addToPanel(this.initialGraph, 650, 35, 100, 20);
		this.addToPanel(this.finalGraph, 650, 60, 100, 20);
		this.addToPanel(this.opacity, 800, 55, 150, 20);
		this.addToPanel(this.opacityText, 850, 30, 40, 18);
		this.addToPanel(this.animate, 650, 195, 100, 20);
		this.addToPanel(this.reset, 770, 195, 70, 20);
		this.addToPanel(this.red, 780, 320, 130, 20);
		this.addToPanel(this.green, 780, 350, 130, 20);
		this.addToPanel(this.blue, 780, 380, 130, 20);
		this.addToPanel(this.redText, 920, 320, 30, 20);
		this.addToPanel(this.greenText, 920, 350, 30, 20);
		this.addToPanel(this.blueText, 920, 380, 30, 20);
		this.addToPanel(this.start, 650, 440, 40, 20);
		this.addToPanel(this.playBackward, 714, 440, 40, 20);
		this.addToPanel(this.pause, 778, 440, 40, 20);
		this.addToPanel(this.playForward, 842, 440, 40, 20);
		this.addToPanel(this.finish, 905, 440, 40, 20);
		this.addToPanel(this.totFrames, 730, 470, 150, 20);
		this.addToPanel(this.totFramesText, 900, 470, 40, 20);
		this.addToPanel(this.frameRate, 730, 500, 150, 20);
		this.addToPanel(this.frameRateText, 900, 500, 40, 20);
		this.addToPanel(this.frames, 700, 530, 180, 20);
		this.addToPanel(this.framesText, 900, 530, 40, 20);
		this.addToPanel(this.ch, 860, 195, 80, 20);
		this.addToPanel(this.bound, 860, 125, 80, 20);
		this.addToPanel(this.findPolygon, 750, 125, 80, 20);
		this.addToPanel(this.showSteiner, 750, 160, 150, 20);
		this.addToPanel(this.save, 670, 240, 100, 20);
		this.addToPanel(this.open, 820, 240, 100, 20);
	}

	void addToPanel(final Component comp, final int x, final int y, final int width, final int height) {
		this.add(comp);
		comp.setBounds(x, y, width, height);
	}

	void drawBox(final Graphics graphics, final int x, final int y, final int n, final int n2) {
		graphics.setColor(Color.gray);
		graphics.drawRect(x, y, n - 1, n2 - 1);
		graphics.setColor(Color.white);
		graphics.drawRect(x + 1, y + 1, n - 1, n2 - 1);
	}

	void drawNode(final Graphics graphics, final int n, final int n2, Color color, final int n3, final int n4) {
		final Color color2 = new Color(255 - 255 * n4 / 100, 255 - 255 * n4 / 100, 255 - 255 * n4 / 100);
		color = new Color(255 - (255 - color.getRed()) * n4 / 100, 255 - (255 - color.getGreen()) * n4 / 100,
				255 - (255 - color.getBlue()) * n4 / 100);
		graphics.setColor(color);
		graphics.fillArc(n, n2, 20, 20, 0, 360);
		graphics.setColor(color2);
		graphics.drawArc(n, n2, 20, 20, 0, 360);
		graphics.setFont(this.font10);
		graphics.setColor(Color.white);
		graphics.drawString("" + n3, n + 10 - this.fm.stringWidth("" + n3) / 2 - 1, n2 + 10 + this.fm.getHeight() / 2 - 2);
		graphics.setFont(this.font12);
	}

	@Override
	public void paint(final Graphics graphics) {
		graphics.setColor(Color.gray);
		graphics.fillRect(8, 8, 602, 552);
		graphics.setColor(Color.black);
		graphics.fillRect(10, 10, 602, 552);
		graphics.setColor(Color.white);
		graphics.fillRect(10, 10, 600, 550);
		for (int i = 0; i < 5; ++i) {
			for (int j = 0; j < 6; ++j) {
				this.drawBox(graphics, 640 + j * 20, 300 + i * 20, 17, 17);
				graphics.setColor(this.colors[j][i]);
				graphics.fillRect(642 + j * 20, 302 + i * 20, 14, 14);
			}
		}
		this.drawBox(graphics, 940, 295, 17, 17);
		graphics.setColor(this.custColor);
		graphics.fillRect(942, 297, 14, 14);
		if (!this.animateMode) {
			this.drawNode(graphics, 650, 120, this.curColor, 0, 100);
			graphics.setColor(Color.black);
			graphics.fillRect(650, 170, 20, 2);
		}
		graphics.setColor(Color.black);
		graphics.setFont(this.font12);
		graphics.drawString("Opacity", 800, 45);
		graphics.drawString("Intensities Red, Green, Blue", 780, 310);
		graphics.drawString("Total Frames", 650, 485);
		graphics.drawString("Frame Rate", 650, 515);
		graphics.drawString("Frames", 650, 545);
		graphics.setFont(this.font12b);
		graphics.drawString("View graph", 630, 20);
		graphics.drawString("Drawing Tools", 630, 105);
		graphics.drawString("Colors", 630, 285);
		graphics.drawString("Animation", 630, 425);
		if (this.selectNode) {
			graphics.drawRect(645, 115, 30, 30);
		}
		if (this.selectArc) {
			graphics.drawRect(645, 155, 30, 30);
		}
		this.drawBox(graphics, 630, 25, 335, 60);
		this.drawBox(graphics, 630, 110, 335, 115);
		this.drawBox(graphics, 630, 290, 335, 115);
		this.drawBox(graphics, 630, 430, 335, 130);
		while (this.bi == null) {
			this.bi = this.createImage(600, 550);
		}
		final Graphics graphics2 = this.bi.getGraphics();
		this.redrawGraph(graphics2);
		graphics.drawImage(this.bi, 10, 10, this);
		graphics2.dispose();
	}

	void redrawGraph(final Graphics graphics) {
		final Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(Color.white);
		graphics2D.fillRect(0, 0, 600, 550);
		final int n = 10;
		final int value = this.opacity.getValue();
		graphics2D.setColor(new Color(255 - 255 * value / 100, 255 - 255 * value / 100, 255 - 255 * value / 100));
		final int n2 = 1 - this.curGraph;
		graphics2D.setStroke(new BasicStroke(2.0f));
		for (int i = 0; i < this.edgeCount[n2]; ++i) {
			graphics2D.drawLine(this.nodeX[n2][this.link[n2][i][0]] + n, this.nodeY[n2][this.link[n2][i][0]] + n,
					this.nodeX[n2][this.link[n2][i][1]] + n, this.nodeY[n2][this.link[n2][i][1]] + n);
		}
		final int n3 = 8;
		if (this.showSteiner.getState()) {
			graphics2D.setStroke(new BasicStroke(1.0f));
			graphics2D.setColor(new Color(255 - 255 * value / 100, 255 - 255 * value / 100, 255));
			for (int j = this.edgeCount[n2]; j < this.curLink[n2]; ++j) {
				graphics2D.drawLine(this.nodeX[n2][this.link[n2][j][0]] + n, this.nodeY[n2][this.link[n2][j][0]] + n,
						this.nodeX[n2][this.link[n2][j][1]] + n, this.nodeY[n2][this.link[n2][j][1]] + n);
			}
			graphics2D.setColor(new Color(255 - 255 * value / 100, 200 + 55 * (100 - value) / 100, 255 - 255 * value / 100));
			for (int k = this.nodeCount[n2]; k < this.curNode[n2]; ++k) {
				graphics2D.fillArc(this.nodeX[n2][k] + n - n3 / 2, this.nodeY[n2][k] + n - n3 / 2, n3, n3, 0, 360);
			}
		}
		graphics2D.setStroke(new BasicStroke(1.0f));
		for (int l = 0; l < this.nodeCount[n2]; ++l) {
			this.drawNode(graphics2D, this.nodeX[n2][l], this.nodeY[n2][l], this.nodeColor[n2][l], this.nodeID[n2][l], value);
		}
		final int curGraph = this.curGraph;
		graphics2D.setColor(Color.black);
		graphics2D.setStroke(new BasicStroke(2.0f));
		for (int n4 = 0; n4 < this.edgeCount[curGraph]; ++n4) {
			graphics2D.drawLine(this.nodeX[curGraph][this.link[curGraph][n4][0]] + n, this.nodeY[curGraph][this.link[curGraph][n4][0]] + n,
					this.nodeX[curGraph][this.link[curGraph][n4][1]] + n, this.nodeY[curGraph][this.link[curGraph][n4][1]] + n);
		}
		if (this.showSteiner.getState()) {
			graphics2D.setStroke(new BasicStroke(1.0f));
			graphics2D.setColor(Color.blue);
			for (int n5 = this.edgeCount[curGraph]; n5 < this.curLink[curGraph]; ++n5) {
				graphics2D.drawLine(this.nodeX[curGraph][this.link[curGraph][n5][0]] + n,
						this.nodeY[curGraph][this.link[curGraph][n5][0]] + n, this.nodeX[curGraph][this.link[curGraph][n5][1]] + n,
						this.nodeY[curGraph][this.link[curGraph][n5][1]] + n);
			}
			graphics2D.setColor(new Color(0, 200, 0));
			for (int n6 = this.nodeCount[curGraph]; n6 < this.curNode[curGraph]; ++n6) {
				graphics2D.fillArc(this.nodeX[curGraph][n6] + n - n3 / 2, this.nodeY[curGraph][n6] + n - n3 / 2, n3, n3, 0, 360);
			}
		}
		graphics2D.setStroke(new BasicStroke(1.0f));
		for (int n7 = 0; n7 < this.nodeCount[curGraph]; ++n7) {
			this.drawNode(graphics2D, this.nodeX[curGraph][n7], this.nodeY[curGraph][n7], this.nodeColor[curGraph][n7],
					this.nodeID[curGraph][n7], 100);
		}
	}

	public void mouseClicked(final MouseEvent mouseEvent) {
	}

	public void mouseEntered(final MouseEvent mouseEvent) {
	}

	public void mouseExited(final MouseEvent mouseEvent) {
	}

	public void mouseReleased(final MouseEvent mouseEvent) {
		this.down = false;
	}

	public void mouseDragged(final MouseEvent mouseEvent) {
		final int downX = mouseEvent.getX() - 10;
		final int downY = mouseEvent.getY() - 10;
		if (!this.down || this.downNode == -1) {
			return;
		}
		final int[] array = this.nodeX[this.curGraph];
		final int downNode = this.downNode;
		array[downNode] += downX - this.downX;
		final int[] array2 = this.nodeY[this.curGraph];
		final int downNode2 = this.downNode;
		array2[downNode2] += downY - this.downY;
		this.downX = downX;
		this.downY = downY;
		final Graphics graphics = this.getGraphics();
		final Graphics graphics2 = this.bi.getGraphics();
		this.redrawGraph(graphics2);
		graphics.drawImage(this.bi, 10, 10, this);
		graphics2.dispose();
		graphics.dispose();
	}

	public void mouseMoved(final MouseEvent mouseEvent) {
		final int n = mouseEvent.getX() - 10;
		final int n2 = mouseEvent.getY() - 10;
		if (this.bi == null) {
			return;
		}
		final Graphics graphics = this.getGraphics();
		final Graphics graphics2 = this.bi.getGraphics();
		if (this.selectNode && n > 15 && n < 580 && n2 > 15 && n2 < 530) {
			this.redrawGraph(graphics2);
			this.drawNode(graphics2, n - 10, n2 - 10, this.curColor, this.curNode[this.curGraph], 100);
			graphics.drawImage(this.bi, 10, 10, this);
			this.changed = true;
		} else if (this.drawing && n > 15 && n < 580 && n2 > 15 && n2 < 530) {
			this.redrawGraph(graphics2);
			graphics2.setColor(Color.black);
			graphics2.drawLine(this.nodeX[this.curGraph][this.arcStart] + 10, this.nodeY[this.curGraph][this.arcStart] + 10, n - 10 + 10,
					n2 - 10 + 10);
			graphics.drawImage(this.bi, 10, 10, this);
			this.changed = true;
		} else if (this.changed) {
			this.redrawGraph(graphics2);
			graphics.drawImage(this.bi, 10, 10, this);
			this.changed = false;
		}
		graphics2.dispose();
		graphics.dispose();
	}

	public void mousePressed(final MouseEvent mouseEvent) {
		final int x = mouseEvent.getX();
		final int y = mouseEvent.getY();
		final Graphics graphics = this.getGraphics();
		if ((mouseEvent.getModifiers() & 0x10) == 0x10) {
			if (!this.animateMode && x > 650 && x < 670 && y > 120 && y < 140) {
				this.selectNode = true;
				this.selectArc = false;
				graphics.setColor(this.background);
				graphics.drawRect(645, 155, 30, 30);
				graphics.setColor(Color.black);
				graphics.drawRect(645, 115, 30, 30);
			} else if (!this.animateMode && x > 650 && x < 670 && y > 160 && y < 180) {
				this.selectArc = true;
				this.selectNode = false;
				graphics.setColor(this.background);
				graphics.drawRect(645, 115, 30, 30);
				graphics.setColor(Color.black);
				graphics.drawRect(645, 155, 30, 30);
			} else if (!this.animateMode && this.selectArc && x - 10 > 15 && x - 10 < 580 && y - 10 > 15 && y - 10 < 530) {
				this.drawArc(graphics, x, y);
			} else if (!this.animateMode && this.selectNode && x - 10 > 15 && x - 10 < 580 && y - 10 > 15 && y - 10 < 530) {
				this.nodeX[this.curGraph][this.curNode[this.curGraph]] = x - 10 - 10;
				this.nodeY[this.curGraph][this.curNode[this.curGraph]] = y - 10 - 10;
				this.nodeColor[this.curGraph][this.curNode[this.curGraph]] = this.curColor;
				this.nodeID[this.curGraph][this.curNode[this.curGraph]] = this.curNode[this.curGraph];
				final int[] curNode = this.curNode;
				final int curGraph = this.curGraph;
				++curNode[curGraph];
				final int[] nodeCount = this.nodeCount;
				final int curGraph2 = this.curGraph;
				++nodeCount[curGraph2];
			} else if (!this.animateMode && x > 640 && x < 760 && y > 300 && y < 400) {
				this.drawNode(graphics, 650, 120, this.curColor = this.colors[(x - 640) / 20][(y - 300) / 20], 0, 100);
			} else if (!this.animateMode && x > 940 && x < 960 && y > 295 && y < 315) {
				this.drawNode(graphics, 650, 120, this.curColor = this.custColor, 0, 100);
			}
			if (!this.selectNode && !this.selectArc) {
				this.down = true;
				this.downX = x - 10;
				this.downY = y - 10;
				this.downNode = this.getNode(this.downX, this.downY);
			}
		} else if (this.selectNode) {
			this.selectNode = false;
			if (x - 10 > 15 && x - 10 < 580 && y - 10 > 15 && y - 10 < 530) {
				final Graphics graphics2 = this.bi.getGraphics();
				this.redrawGraph(graphics2);
				graphics.drawImage(this.bi, 10, 10, this);
				graphics2.dispose();
			}
			graphics.setColor(this.background);
			graphics.drawRect(645, 115, 30, 30);
		} else if (this.selectArc) {
			this.selectArc = false;
			this.drawing = false;
			final int curGraph3 = this.curGraph;
			final int arcStart = this.arcStart;
			if (arcStart > -1) {
				this.drawNode(graphics, this.nodeX[curGraph3][arcStart] + 10, this.nodeY[curGraph3][arcStart] + 10,
						this.nodeColor[curGraph3][arcStart], this.nodeID[curGraph3][arcStart], 100);
			}
			this.arcStart = -1;
			graphics.setColor(this.background);
			graphics.drawRect(645, 155, 30, 30);
		} else {
			this.eraseNode(graphics, x, y);
		}
		graphics.dispose();
	}

	int getNode(final int n, final int n2) {
		for (int i = 0; i < this.curNode[this.curGraph]; ++i) {
			final int n3 = this.nodeX[this.curGraph][i] - 10 + 10;
			final int n4 = this.nodeY[this.curGraph][i] - 10 + 10;
			if (n > n3 && n < n3 + 20 && n2 > n4 && n2 < n4 + 20) {
				return i;
			}
		}
		return -1;
	}

	void eraseNode(final Graphics graphics, final int n, final int n2) {
		if (this.animateMode) {
			return;
		}
		if (n - 10 <= 15 || n - 10 >= 580 || n2 - 10 <= 15 || n2 - 10 >= 530) {
			return;
		}
		int i;
		for (i = 0; i < this.curNode[this.curGraph]; ++i) {
			final int n3 = this.nodeX[this.curGraph][i] + 10;
			final int n4 = this.nodeY[this.curGraph][i] + 10;
			if (n > n3 && n < n3 + 20 && n2 > n4 && n2 < n4 + 20) {
				break;
			}
		}
		if (i == this.curNode[this.curGraph]) {
			return;
		}
		for (int j = i; j < this.curNode[this.curGraph] - 1; ++j) {
			this.nodeX[this.curGraph][j] = this.nodeX[this.curGraph][j + 1];
			this.nodeY[this.curGraph][j] = this.nodeY[this.curGraph][j + 1];
			this.nodeColor[this.curGraph][j] = this.nodeColor[this.curGraph][j + 1];
		}
		for (int k = 0; k < this.curLink[this.curGraph]; ++k) {
			if (this.link[this.curGraph][k][0] == i || this.link[this.curGraph][k][1] == i) {
				final int[] curLink = this.curLink;
				final int curGraph = this.curGraph;
				--curLink[curGraph];
				for (int l = k; l < this.curLink[this.curGraph]; ++l) {
					this.link[this.curGraph][l][0] = this.link[this.curGraph][l + 1][0];
					this.link[this.curGraph][l][1] = this.link[this.curGraph][l + 1][1];
				}
				--k;
			} else {
				if (this.link[this.curGraph][k][0] > i) {
					final int[] array = this.link[this.curGraph][k];
					final int n5 = 0;
					--array[n5];
				}
				if (this.link[this.curGraph][k][1] > i) {
					final int[] array2 = this.link[this.curGraph][k];
					final int n6 = 1;
					--array2[n6];
				}
			}
		}
		final int[] curNode = this.curNode;
		final int curGraph2 = this.curGraph;
		--curNode[curGraph2];
		final int[] nodeCount = this.nodeCount;
		final int curGraph3 = this.curGraph;
		--nodeCount[curGraph3];
		final Graphics graphics2 = this.bi.getGraphics();
		this.redrawGraph(graphics2);
		graphics.drawImage(this.bi, 10, 10, this);
		graphics2.dispose();
	}

	public void adjustmentValueChanged(final AdjustmentEvent adjustmentEvent) {
		final Scrollbar scrollbar = (Scrollbar) adjustmentEvent.getSource();
		final int value = adjustmentEvent.getValue();
		if (scrollbar == this.red || scrollbar == this.green || scrollbar == this.blue) {
			this.changeColor();
		} else if (scrollbar == this.opacity) {
			this.opacityText.setText(value + "%");
			final Graphics graphics = this.getGraphics();
			final Graphics graphics2 = this.bi.getGraphics();
			this.redrawGraph(graphics2);
			graphics.drawImage(this.bi, 10, 10, this);
			graphics2.dispose();
			graphics.dispose();
		} else if (scrollbar == this.totFrames) {
			this.totFramesText.setText("" + this.totFrames.getValue());
			this.frames.setMaximum(this.totFrames.getValue() + 1);
			if (this.curFrame > this.totFrames.getValue()) {
				this.curFrame = this.totFrames.getValue();
			}
			this.framesText.setText("" + this.curFrame);
		} else if (scrollbar == this.frameRate) {
			this.frameRateText.setText("" + this.frameRate.getValue());
			this.t.delay = 1000 / this.frameRate.getValue();
		} else if (scrollbar == this.frames) {
			this.captureFrame();
		}
	}

	void changeColor() {
		this.redText.setText("" + this.red.getValue());
		this.greenText.setText("" + this.green.getValue());
		this.blueText.setText("" + this.blue.getValue());
		final Graphics graphics = this.getGraphics();
		this.custColor = new Color(this.red.getValue(), this.green.getValue(), this.blue.getValue());
		this.drawBox(graphics, 940, 295, 17, 17);
		graphics.setColor(this.custColor);
		graphics.fillRect(942, 297, 14, 14);
		graphics.dispose();
	}

	public void itemStateChanged(final ItemEvent itemEvent) {
		final Checkbox checkbox = (Checkbox) itemEvent.getSource();
		if (checkbox == this.initialGraph || checkbox == this.finalGraph) {
			this.curGraph = (this.initialGraph.getState() ? 0 : 1);
			final Graphics graphics = this.getGraphics();
			final Graphics graphics2 = this.bi.getGraphics();
			this.redrawGraph(graphics2);
			graphics.drawImage(this.bi, 10, 10, this);
			graphics2.dispose();
			graphics.dispose();
		} else if (checkbox == this.showSteiner) {
			final Graphics graphics3 = this.getGraphics();
			final Graphics graphics4 = this.bi.getGraphics();
			this.redrawGraph(graphics4);
			graphics3.drawImage(this.bi, 10, 10, this);
			graphics4.dispose();
		}
	}

	void drawArc(final Graphics graphics, int n, int n2) {
		boolean b = false;
		n -= 10;
		n2 -= 10;
		int i;
		for (i = 0; i < this.curNode[this.curGraph]; ++i) {
			final int n3 = this.nodeX[this.curGraph][i];
			final int n4 = this.nodeY[this.curGraph][i];
			if (n > n3 && n < n3 + 20 && n2 > n4 && n2 < n4 + 20) {
				break;
			}
		}
		if (this.drawing) {
			if (i == this.curNode[this.curGraph]) {
				this.nodeX[this.curGraph][this.curNode[this.curGraph]] = n - 10;
				this.nodeY[this.curGraph][this.curNode[this.curGraph]] = n2 - 10;
				this.nodeColor[this.curGraph][this.curNode[this.curGraph]] = Color.white;
				this.nodeID[this.curGraph][this.curNode[this.curGraph]] = this.curNode[this.curGraph];
				final int[] curNode = this.curNode;
				final int curGraph = this.curGraph;
				++curNode[curGraph];
				b = true;
				this.curNodes[this.nc++] = i;
			}
			if (this.arcStart != i) {
				this.link[this.curGraph][this.curLink[this.curGraph]][0] = this.arcStart;
				this.link[this.curGraph][this.curLink[this.curGraph]][1] = i;
				final int[] curLink = this.curLink;
				final int curGraph2 = this.curGraph;
				++curLink[curGraph2];
				final int[] edgeCount = this.edgeCount;
				final int curGraph3 = this.curGraph;
				++edgeCount[curGraph3];
			}
			if (b) {
				this.arcStart = i;
			} else {
				if (this.nc > 1) {
					final int n5 = 1 - this.curGraph;
					this.curNodes[this.nc++] = i;
					int j;
					for (j = 0; j < this.curLink[n5]
							&& (this.link[n5][j][0] != this.curNodes[0] || this.link[n5][j][1] != this.curNodes[this.nc - 1])
							&& (this.link[n5][j][1] != this.curNodes[0] || this.link[n5][j][0] != this.curNodes[this.nc - 1]); ++j) {
					}
					if (j < this.curLink[n5]) {
						final int[] curLink2 = this.curLink;
						final int n6 = n5;
						--curLink2[n6];
					}
					while (j < this.curLink[n5]) {
						this.link[n5][j][0] = this.link[n5][j + 1][0];
						this.link[n5][j][1] = this.link[n5][j + 1][1];
						++j;
					}
					--this.nc;
					final int n7 = this.nodeX[n5][this.curNodes[0]];
					final int n8 = this.nodeX[n5][this.curNodes[this.nc]];
					final int n9 = this.nodeY[n5][this.curNodes[0]];
					final int n10 = this.nodeY[n5][this.curNodes[this.nc]];
					final int n11 = n8 - n7;
					final int n12 = n10 - n9;
					for (int k = 0; k < this.nc; ++k) {
						if (k != 0) {
							this.nodeX[n5][this.curNode[n5]] = n7 + k * n11 / this.nc;
							this.nodeY[n5][this.curNode[n5]] = n9 + k * n12 / this.nc;
							final int[] curNode2 = this.curNode;
							final int n13 = n5;
							++curNode2[n13];
						}
						this.link[n5][this.curLink[n5]][0] = this.curNodes[k];
						this.link[n5][this.curLink[n5]][1] = this.curNodes[k + 1];
						final int[] curLink3 = this.curLink;
						final int n14 = n5;
						++curLink3[n14];
						final int[] edgeCount2 = this.edgeCount;
						final int n15 = n5;
						++edgeCount2[n15];
					}
				}
				this.arcStart = -1;
				this.drawing = false;
			}
			final Graphics graphics2 = this.bi.getGraphics();
			this.redrawGraph(graphics2);
			graphics.drawImage(this.bi, 10, 10, this);
			graphics2.dispose();
		} else {
			if (i == this.curNode[this.curGraph]) {
				return;
			}
			this.arcStart = i;
			this.nc = 0;
			this.curNodes[this.nc++] = i;
			this.drawing = true;
		}
	}

	void blink() {
		if (!this.drawing) {
			return;
		}
		final Graphics graphics = this.getGraphics();
		final int curGraph = this.curGraph;
		final int arcStart = this.arcStart;
		this.blinkColor = 130 - this.blinkColor;
		this.drawNode(graphics, this.nodeX[curGraph][arcStart], this.nodeY[curGraph][arcStart], this.nodeColor[curGraph][arcStart],
				this.nodeID[curGraph][arcStart], this.blinkColor);
		graphics.dispose();
	}

	public void actionPerformed(final ActionEvent actionEvent) {
		final Button button = (Button) actionEvent.getSource();
		if (button == this.animate) {
			this.changeMode();
		} else if (button == this.playForward) {
			this.animate(true);
		} else if (button == this.playBackward) {
			this.animate(false);
		} else if (button == this.start) {
			this.curFrame = 0;
			this.frames.setValue(0);
			this.framesText.setText("0");
		} else if (button == this.finish) {
			this.curFrame = this.totFrames.getValue();
			this.frames.setValue(this.curFrame);
			this.framesText.setText("" + this.curFrame);
		} else if (button == this.pause) {
			this.doAnimate = !this.doAnimate;
		} else if (button == this.reset) {
			this.curNode[0] = (this.curNode[1] = 0);
			this.curLink[0] = (this.curLink[1] = 0);
			this.nodeCount[0] = (this.nodeCount[1] = 0);
			this.edgeCount[0] = (this.edgeCount[1] = 0);
			this.extraNodeCount = 0;
			final Graphics graphics = this.getGraphics();
			final Graphics graphics2 = this.bi.getGraphics();
			this.redrawGraph(graphics2);
			graphics.drawImage(this.bi, 10, 10, this);
			graphics2.dispose();
			graphics.dispose();
		} else if (button == this.findPolygon) {
			int n = 1000;
			this.extraNodeCount = 0;
			this.addBoundary();
			this.correctEc = 0;
			this.correctNc = this.curNode[0];
			for (int i = 0; i < this.curNode[1]; ++i) {
				if (this.nodeX[1][i] < n) {
					n = this.nodeX[1][i];
				}
			}
			this.preFindAllPoly(this.nodeX[1], this.nodeY[1], this.curNode[1], this.link[1], this.curLink[1]);
		} else if (button == this.save) {
			this.saveFile();
		} else if (button == this.open) {
			this.loadFile();
		}
	}

	void addBoundary() {
		final int n = this.curNode[0];
		int n3;
		int n2 = n3 = this.nodeX[0][0];
		int n5;
		int n4 = n5 = this.nodeX[1][0];
		int n7;
		int n6 = n7 = this.nodeY[0][0];
		int n9;
		int n8 = n9 = this.nodeY[1][0];
		this.calcFramesRigid();
		this.rtheta = this.rtheta * 180.0 / Math.PI;
		if (this.rtheta < 0.0) {
			this.rtheta += 360.0;
		}
		for (int i = 1; i < this.curNode[0]; ++i) {
			if (this.nodeX[0][i] < n3) {
				n3 = this.nodeX[0][i];
			}
			if (this.nodeX[0][i] > n2) {
				n2 = this.nodeX[0][i];
			}
			if (this.nodeY[0][i] < n7) {
				n7 = this.nodeY[0][i];
			}
			if (this.nodeY[0][i] > n6) {
				n6 = this.nodeY[0][i];
			}
			if (this.nodeX[1][i] < n5) {
				n5 = this.nodeX[1][i];
			}
			if (this.nodeX[1][i] > n4) {
				n4 = this.nodeX[1][i];
			}
			if (this.nodeY[1][i] < n9) {
				n9 = this.nodeY[1][i];
			}
			if (this.nodeY[1][i] > n8) {
				n8 = this.nodeY[1][i];
			}
		}
		final int[] array = new int[4];
		final int[] array2 = new int[4];
		n3 -= 20;
		n2 += 20;
		n7 -= 20;
		n6 += 20;
		array[0] = n3;
		array2[0] = n7;
		array[1] = n2;
		array2[1] = n7;
		array[2] = n2;
		array2[2] = n6;
		array[3] = n3;
		array2[3] = n6;
		for (int j = 0; j < 4; ++j) {
			this.nodeX[0][this.curNode[0]] = array[j];
			this.nodeY[0][this.curNode[0]] = array2[j];
			this.link[0][this.curLink[0]][0] = n + j;
			this.link[0][this.curLink[0]][1] = n + (j + 1) % 4;
			final int[] curNode = this.curNode;
			final int n10 = 0;
			++curNode[n10];
			final int[] curLink = this.curLink;
			final int n11 = 0;
			++curLink[n11];
		}
		final int[] array3 = new int[4];
		final int[] array4 = new int[4];
		n5 -= 20;
		n4 += 20;
		n9 -= 20;
		n8 += 20;
		array3[0] = n5;
		array4[0] = n9;
		array3[1] = n4;
		array4[1] = n9;
		array3[2] = n4;
		array4[2] = n8;
		array3[3] = n5;
		array4[3] = n8;
		for (int k = 0; k < 4; ++k) {
			this.nodeX[1][this.curNode[1]] = array3[k];
			this.nodeY[1][this.curNode[1]] = array4[k];
			this.link[1][this.curLink[1]][0] = n + k;
			this.link[1][this.curLink[1]][1] = n + (k + 1) % 4;
			this.borderNodes[k] = this.curNode[1];
			final int[] curNode2 = this.curNode;
			final int n12 = 1;
			++curNode2[n12];
			final int[] curLink2 = this.curLink;
			final int n13 = 1;
			++curLink2[n13];
		}
		this.bc = 4;
		this.bound.select(this.bc - 3);
		final double[][] array5 = new double[4][n];
		final double[][] array6 = new double[4][n];
		int l = 0;
		int m = 0;
		for (int n14 = 0; n14 < 4; ++n14) {
			for (l = 0; l < n; ++l) {
				if (!this.edgeCross(array[n14], array2[n14], this.nodeX[0][l], this.nodeY[0][l], 0, l)) {
					array5[n14][l] = Math.sqrt((this.nodeX[0][l] - array[n14]) * (this.nodeX[0][l] - array[n14])
							+ (this.nodeY[0][l] - array2[n14]) * (this.nodeY[0][l] - array2[n14]));
					for (m = 0; m < 4; ++m) {
						for (int n15 = 0; n15 < n; ++n15) {
							if (!this.edgeCross(array3[m], array4[m], this.nodeX[1][n15], this.nodeY[1][n15], 1, n15)) {
								array6[m][n15] = Math.sqrt((this.nodeX[1][n15] - array3[m]) * (this.nodeX[1][n15] - array3[m])
										+ (this.nodeY[1][n15] - array4[m]) * (this.nodeY[1][n15] - array4[m]));
							} else {
								array6[m][n15] = 10000.0;
							}
						}
					}
				} else {
					array5[n14][l] = 10000.0;
				}
			}
			if (l < n) {
				break;
			}
		}
		int i2 = -1;
		double n16 = 20000.0;
		final int n17 = (int) (this.rtheta * 4.0 / 360.0);
		for (int n18 = 0; n18 < 4; ++n18) {
			for (int n19 = 0; n19 < n; ++n19) {
				if (array5[n18][n19] <= 9000.0) {
					if (array6[n17][n19] <= 9000.0) {
						if (array5[n18][n19] + array6[n17][n19] < n16) {
							n16 = array5[n18][n19] + array6[n17][n19];
							i2 = n18;
							m = n17;
							l = n19;
						}
					}
				}
			}
		}
		System.out.println("" + i2 + "  " + m + "  " + l);
		if (i2 == -1) {
			System.out.println("Cannot find a visible node in both graphs");
			return;
		}
		final int[] array7 = new int[4];
		final int[] array8 = new int[4];
		for (int n20 = 0; n20 < 4; ++n20) {
			array7[n20] = this.nodeX[1][n20 + n];
			array8[n20] = this.nodeY[1][n20 + n];
		}
		for (int n21 = 0; n21 < 4; ++n21) {
			this.nodeX[1][(n21 + i2) % 4 + n] = array7[(n21 + m) % 4];
			this.nodeY[1][(n21 + i2) % 4 + n] = array8[(n21 + m) % 4];
		}
		this.link[0][this.curLink[0]][0] = n + i2;
		this.link[0][this.curLink[0]][1] = l;
		final int[] curLink3 = this.curLink;
		final int n22 = 0;
		++curLink3[n22];
		this.link[1][this.curLink[1]][0] = n + i2;
		this.link[1][this.curLink[1]][1] = l;
		final int[] curLink4 = this.curLink;
		final int n23 = 1;
		++curLink4[n23];
	}

	void addBoundary1() {
		final int n = this.curNode[0];
		int n3;
		int n2 = n3 = this.nodeX[0][0];
		int n5;
		int n4 = n5 = this.nodeX[1][0];
		int n7;
		int n6 = n7 = this.nodeY[0][0];
		int n9;
		int n8 = n9 = this.nodeY[1][0];
		for (int i = 1; i < this.curNode[0]; ++i) {
			if (this.nodeX[0][i] < n3) {
				n3 = this.nodeX[0][i];
			}
			if (this.nodeX[0][i] > n2) {
				n2 = this.nodeX[0][i];
			}
			if (this.nodeY[0][i] < n7) {
				n7 = this.nodeY[0][i];
			}
			if (this.nodeY[0][i] > n6) {
				n6 = this.nodeY[0][i];
			}
			if (this.nodeX[1][i] < n5) {
				n5 = this.nodeX[1][i];
			}
			if (this.nodeX[1][i] > n4) {
				n4 = this.nodeX[1][i];
			}
			if (this.nodeY[1][i] < n9) {
				n9 = this.nodeY[1][i];
			}
			if (this.nodeY[1][i] > n8) {
				n8 = this.nodeY[1][i];
			}
		}
		final int[] array = new int[4];
		final int[] array2 = new int[4];
		n3 -= 20;
		n2 += 20;
		n7 -= 20;
		n6 += 20;
		array[0] = n3;
		array2[0] = n7;
		array[1] = n2;
		array2[1] = n7;
		array[2] = n2;
		array2[2] = n6;
		array[3] = n3;
		array2[3] = n6;
		for (int j = 0; j < 4; ++j) {
			this.nodeX[0][this.curNode[0]] = array[j];
			this.nodeY[0][this.curNode[0]] = array2[j];
			this.link[0][this.curLink[0]][0] = n + j;
			this.link[0][this.curLink[0]][1] = n + (j + 1) % 4;
			final int[] curNode = this.curNode;
			final int n10 = 0;
			++curNode[n10];
			final int[] curLink = this.curLink;
			final int n11 = 0;
			++curLink[n11];
		}
		final int[] array3 = new int[4];
		final int[] array4 = new int[4];
		n5 -= 20;
		n4 += 20;
		n9 -= 20;
		n8 += 20;
		array3[0] = n5;
		array4[0] = n9;
		array3[1] = n4;
		array4[1] = n9;
		array3[2] = n4;
		array4[2] = n8;
		array3[3] = n5;
		array4[3] = n8;
		for (int k = 0; k < 4; ++k) {
			this.nodeX[1][this.curNode[1]] = array3[k];
			this.nodeY[1][this.curNode[1]] = array4[k];
			this.link[1][this.curLink[1]][0] = n + k;
			this.link[1][this.curLink[1]][1] = n + (k + 1) % 4;
			this.borderNodes[k] = this.curNode[1];
			final int[] curNode2 = this.curNode;
			final int n12 = 1;
			++curNode2[n12];
			final int[] curLink2 = this.curLink;
			final int n13 = 1;
			++curLink2[n13];
		}
		this.bc = 4;
		this.bound.select(this.bc - 3);
		int l = 0;
		int n14 = 0;
		int n15;
		for (n15 = 0; n15 < 4; ++n15) {
			for (l = 0; l < n; ++l) {
				if (!this.edgeCross(array[n15], array2[n15], this.nodeX[0][l], this.nodeY[0][l], 0, l)) {
					for (n14 = 0; n14 < 4 && this.edgeCross(array3[n14], array4[n14], this.nodeX[1][l], this.nodeY[1][l], 1, l); ++n14) {
					}
					if (n14 < 4) {
						break;
					}
				}
			}
			if (l < n) {
				break;
			}
		}
		if (n15 == 4) {
			System.out.println("Cannot find a visible node in both graphs");
			return;
		}
		final int[] array5 = new int[4];
		final int[] array6 = new int[4];
		for (int n16 = 0; n16 < 4; ++n16) {
			array5[n16] = this.nodeX[1][n16 + n];
			array6[n16] = this.nodeY[1][n16 + n];
		}
		for (int n17 = 0; n17 < 4; ++n17) {
			this.nodeX[1][(n17 + n15) % 4 + n] = array5[(n17 + n14) % 4];
			this.nodeY[1][(n17 + n15) % 4 + n] = array6[(n17 + n14) % 4];
		}
		this.link[0][this.curLink[0]][0] = n + n15;
		this.link[0][this.curLink[0]][1] = l;
		final int[] curLink3 = this.curLink;
		final int n18 = 0;
		++curLink3[n18];
		this.link[1][this.curLink[1]][0] = n + n15;
		this.link[1][this.curLink[1]][1] = l;
		final int[] curLink4 = this.curLink;
		final int n19 = 1;
		++curLink4[n19];
	}

	boolean edgeCross(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
		for (int i = 0; i < this.curLink[n5]; ++i) {
			final int n7 = this.nodeX[n5][this.link[n5][i][0]];
			final int n8 = this.nodeX[n5][this.link[n5][i][1]];
			final int n9 = this.nodeY[n5][this.link[n5][i][0]];
			final int n10 = this.nodeY[n5][this.link[n5][i][1]];
			final int n11 = n8 - n7;
			final int n12 = n10 - n9;
			final int n13 = n3 - n;
			final int n14 = n4 - n2;
			final int n15 = n - n7;
			final int n16 = n2 - n9;
			final double n17 = n13 * n12 - n14 * n11;
			if (n17 != 0.0) {
				final double n18 = (n16 * n11 - n12 * n15) / n17;
				final double n19 = (n16 * n13 - n14 * n15) / n17;
				if (n18 > 0.0 && n18 < 1.0 && n19 > 0.0 && n19 < 1.0) {
					return true;
				}
			}
		}
		return false;
	}

	void preFindBorder(final int[] array, final int[] array2, final int n, final int[][] array3, final int n2, final int n3) {
		final int[][] array4 = new int[40000][200];
		final int[] array5 = new int[40000];
		for (int i = 0; i < n; ++i) {
			array5[i] = 0;
			for (int j = 0; j < n2; ++j) {
				if (array3[j][0] == i) {
					array4[i][array5[i]++] = array3[j][1];
				}
				if (array3[j][1] == i) {
					array4[i][array5[i]++] = array3[j][0];
				}
			}
		}
		final int[] array6 = new int[200];
		if (n3 < n) {
			double n4 = -1000.0;
			int n5 = -1;
			for (int k = 0; k < array5[n3]; ++k) {
				double atan2;
				for (atan2 = Math.atan2(array[array4[n3][k]] - array[n3],
						array2[array4[n3][k]] - array2[n3]); atan2 < 0.0; atan2 += (Math.PI * 2)) {
				}
				if (atan2 > n4) {
					n4 = atan2;
					n5 = k;
				}
			}
			if (array5[n3] > 0) {
				int bc = 0;
				final int[] array7 = array5;
				--array7[n3];
				array6[bc++] = array4[n3][n5];
				for (int l = n5; l < array5[n3]; ++l) {
					array4[n3][l] = array4[n3][l + 1];
				}
				int n6 = array6[bc - 1];
				int n7 = n3;
				while (n6 != n3) {
					double n8 = -1000.0;
					int n9 = -1;
					final double atan3 = Math.atan2(array[n7] - array[n6], array2[n7] - array2[n6]);
					for (int n10 = 0; n10 < array5[n6]; ++n10) {
						double n11;
						for (n11 = Math.atan2(array[array4[n6][n10]] - array[n6], array2[array4[n6][n10]] - array2[n6])
								- atan3; n11 < 0.0; n11 += (Math.PI * 2)) {
						}
						if (n11 > n8) {
							n8 = n11;
							n9 = n10;
						}
					}
					final int[] array8 = array5;
					final int n12 = n6;
					--array8[n12];
					array6[bc++] = array4[n6][n9];
					for (int n13 = n9; n13 < array5[n6]; ++n13) {
						array4[n6][n13] = array4[n6][n13 + 1];
					}
					n7 = n6;
					n6 = array6[bc - 1];
				}
				final Graphics graphics = this.getGraphics();
				final Graphics graphics2 = this.bi.getGraphics();
				this.redrawGraph(graphics2);
				graphics2.setColor(Color.red);
				for (int n14 = 0; n14 < bc; ++n14) {
					graphics2.drawLine(array[array6[n14]] + 10, array2[array6[n14]] + 10, array[array6[(n14 + 1) % bc]] + 10,
							array2[array6[(n14 + 1) % bc]] + 10);
					graphics.drawImage(this.bi, 10, 10, this);
				}
				graphics2.dispose();
				graphics.dispose();
				for (int n15 = 0; n15 < bc; ++n15) {
					this.borderNodes[n15] = array6[n15];
				}
				this.bc = bc;
				this.bound.select(this.bc - 3);
			}
		}
	}

	void preFindAllPoly(final int[] array, final int[] array2, final int n, final int[][] array3, final int n2) {
		final int[][] array4 = new int[200][200];
		final int[] array5 = new int[200];
		for (int i = 0; i < n; ++i) {
			array5[i] = 0;
			for (int j = 0; j < n2; ++j) {
				if (array3[j][0] == i) {
					array4[i][array5[i]++] = array3[j][1];
				}
				if (array3[j][1] == i) {
					array4[i][array5[i]++] = array3[j][0];
				}
			}
		}
		final int[] array6 = new int[200];
		while (true) {
			int n3 = -1;
			int n4 = 10000;
			for (int k = 0; k < n; ++k) {
				if (array[k] < n4 && array5[k] > 0) {
					n4 = array[k];
					n3 = k;
				}
			}
			if (n3 == -1) {
				break;
			}
			final int n5 = n3;
			while (array5[n5] > 0) {
				int nc = 0;
				final int[] array7 = array5;
				final int n6 = n5;
				--array7[n6];
				array6[nc++] = array4[n5][array5[n5]];
				final int[] array8 = array5;
				final int n7 = n5;
				++array8[n7];
				int n8 = array6[nc - 1];
				int n9 = n5;
				do {
					double n10 = 1000.0;
					int n11 = -1;
					final double atan2 = Math.atan2(array2[n8] - array2[n9], array[n8] - array[n9]);
					for (int l = 0; l < array5[n8]; ++l) {
						double n12;
						for (n12 = Math.atan2(array2[n8] - array2[array4[n8][l]], array[n8] - array[array4[n8][l]])
								- atan2; n12 < 0.0; n12 += (Math.PI * 2)) {
						}
						if (n12 < n10) {
							if (nc > 1) {
								if (array4[n8][l] == array6[nc - 2] && array5[n8] > 1) {
									continue;
								}
							} else if (array4[n8][l] == n5 && array5[n8] > 1) {
								continue;
							}
							n10 = n12;
							n11 = l;
						}
					}
					final int[] array9 = array5;
					final int n13 = n8;
					--array9[n13];
					array6[nc++] = array4[n8][n11];
					for (int n14 = n11; n14 < array5[n8]; ++n14) {
						array4[n8][n14] = array4[n8][n14 + 1];
					}
					n9 = n8;
					n8 = array6[nc - 1];
				} while (nc <= 2 || n8 != array6[0] || array6[nc - 2] != n3);
				--nc;
				boolean b = true;
				if (this.bc == nc) {
					int n15;
					for (n15 = 0; n15 < nc && array6[n15] != this.borderNodes[0]; ++n15) {
					}
					int n16;
					for (n16 = 0; n16 < nc && array6[(n15 + n16) % nc] == this.borderNodes[n16]; ++n16) {
					}
					if (n16 == nc) {
						b = false;
					}
					int n17;
					for (n17 = 0; n17 < nc && array6[(n15 - n17 + nc) % nc] == this.borderNodes[n17]; ++n17) {
					}
					if (n17 == nc) {
						b = false;
					}
				}
				if (!b) {
					continue;
				}
				for (int n18 = 0; n18 < nc; ++n18) {
					System.out.print(" " + array6[n18]);
				}
				System.out.println("");
				for (int n19 = 0; n19 < nc; ++n19) {
					for (int n20 = n19 + 1; n20 < nc; ++n20) {
						if (array6[n20] == array6[n19]) {
							this.nodeX[0][this.curNode[0]] = this.nodeX[0][array6[n19]];
							this.nodeY[0][this.curNode[0]] = this.nodeY[0][array6[n19]];
							this.nodeX[1][this.curNode[1]] = this.nodeX[1][array6[n19]];
							this.nodeY[1][this.curNode[1]] = this.nodeY[1][array6[n19]];
							this.extraNodes[this.extraNodeCount][0] = this.curNode[0];
							this.extraNodes[this.extraNodeCount][1] = array6[n19];
							++this.extraNodeCount;
							final int[] curNode = this.curNode;
							final int n21 = 0;
							++curNode[n21];
							final int[] curNode2 = this.curNode;
							final int n22 = 1;
							++curNode2[n22];
							for (int n23 = 0; n23 < this.curLink[0]; ++n23) {
								if (this.link[0][n23][0] == array6[n19] && this.link[0][n23][1] == array6[(n19 - 1 + nc) % nc]) {
									this.link[0][n23][0] = this.curNode[0] - 1;
									break;
								}
								if (this.link[0][n23][1] == array6[n19] && this.link[0][n23][0] == array6[(n19 - 1 + nc) % nc]) {
									this.link[0][n23][1] = this.curNode[0] - 1;
									break;
								}
							}
							for (int n24 = 0; n24 < this.curLink[1]; ++n24) {
								if (this.link[1][n24][0] == array6[n19] && this.link[1][n24][1] == array6[(n19 - 1 + nc) % nc]) {
									this.link[1][n24][0] = this.curNode[1] - 1;
									break;
								}
								if (this.link[1][n24][1] == array6[n19] && this.link[1][n24][0] == array6[(n19 - 1 + nc) % nc]) {
									this.link[1][n24][1] = this.curNode[1] - 1;
									break;
								}
							}
							int n25;
							for (n25 = n19 + 1; n25 < nc && (array6[n25] != array6[n19] || array6[(n25 + 1) % nc] != array6[(n19 + 1) % nc])
									&& (array6[n25] != array6[(n19 + 1) % nc] || array6[(n25 + 1) % nc] != array6[n19]); ++n25) {
							}
							if (n25 == nc) {
								for (int n26 = 0; n26 < this.curLink[0]; ++n26) {
									if (this.link[0][n26][0] == array6[n19] && this.link[0][n26][1] == array6[(n19 + 1) % nc]) {
										this.link[0][n26][0] = this.curNode[0] - 1;
										break;
									}
									if (this.link[0][n26][1] == array6[n19] && this.link[0][n26][0] == array6[(n19 + 1) % nc]) {
										this.link[0][n26][1] = this.curNode[0] - 1;
										break;
									}
								}
								for (int n27 = 0; n27 < this.curLink[1]; ++n27) {
									if (this.link[1][n27][0] == array6[n19] && this.link[1][n27][1] == array6[(n19 + 1) % nc]) {
										this.link[1][n27][0] = this.curNode[1] - 1;
										break;
									}
									if (this.link[1][n27][1] == array6[n19] && this.link[1][n27][0] == array6[(n19 + 1) % nc]) {
										this.link[1][n27][1] = this.curNode[1] - 1;
										break;
									}
								}
							} else {
								this.link[0][this.curLink[0]][0] = this.curNode[0] - 1;
								this.link[0][this.curLink[0]][1] = array6[(n19 + 1) % nc];
								this.link[1][this.curLink[1]][0] = this.curNode[1] - 1;
								this.link[1][this.curLink[1]][1] = array6[(n19 + 1) % nc];
								final int[] curLink = this.curLink;
								final int n28 = 0;
								++curLink[n28];
								final int[] curLink2 = this.curLink;
								final int n29 = 1;
								++curLink2[n29];
							}
							array6[n19] = this.curNode[1] - 1;
						}
					}
				}
				for (int n30 = 0; n30 < nc; ++n30) {
					this.curNodes[n30] = array6[n30];
				}
				this.nc = nc;
				this.doTriangulate();
				this.correctEdges();
				this.removeRepeated();
			}
		}
	}

	void removeRepeated() {
		for (int i = 0; i < this.curLink[0]; ++i) {
			for (int j = i + 1; j < this.curLink[0]; ++j) {
				if ((this.link[0][i][0] == this.link[0][j][0] || this.link[0][i][0] == this.link[0][j][1])
						&& (this.link[0][i][1] == this.link[0][j][0] || this.link[0][i][1] == this.link[0][j][1])) {
					final int[] curLink = this.curLink;
					final int n = 0;
					--curLink[n];
					for (int k = j; k < this.curLink[0]; ++k) {
						this.link[0][k][0] = this.link[0][k + 1][0];
						this.link[0][k][1] = this.link[0][k + 1][1];
					}
					--j;
				}
			}
		}
		for (int l = 0; l < this.curLink[1]; ++l) {
			for (int n2 = l + 1; n2 < this.curLink[1]; ++n2) {
				if ((this.link[1][l][0] == this.link[1][n2][0] || this.link[1][l][0] == this.link[1][n2][1])
						&& (this.link[1][l][1] == this.link[1][n2][0] || this.link[1][l][1] == this.link[1][n2][1])) {
					final int[] curLink2 = this.curLink;
					final int n3 = 1;
					--curLink2[n3];
					for (int n4 = n2; n4 < this.curLink[1]; ++n4) {
						this.link[1][n4][0] = this.link[1][n4 + 1][0];
						this.link[1][n4][1] = this.link[1][n4 + 1][1];
					}
					--n2;
				}
			}
		}
	}

	void loadFile() {
		final FileDialog fileDialog = new FileDialog(this.ref, "Load file...", 0);
		fileDialog.setDirectory(".");
		fileDialog.show();
		try {
			final String string = fileDialog.getDirectory() + fileDialog.getFile();
			if (fileDialog.getFile() == null) {
				return;
			}
			this.extraNodeCount = 0;
			final BufferedReader bufferedReader = new BufferedReader(new FileReader(string));
			String line;
			while ((line = bufferedReader.readLine()) != null) {
				this.tokenize(line);
			}
			bufferedReader.close();
			this.repaint();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void tokenize(final String s) {
		int n2 = s.indexOf(32, 0); // find index first space
		String s2 = s.substring(0, n2);
		if (s2.equals("node")) {
			String[] data = s.substring(n2 + 1).split(" ");
			final int n3 = n2 + 1; // index of first
			final int index = s.indexOf(32, n3);
			final int int1 = Integer.parseInt(data[0]);
			final int int2 = Integer.parseInt(data[1]);
			final int int3 = Integer.parseInt(data[2]);
			final int r = Integer.parseInt(data[3]);
			final int g = Integer.parseInt(data[4]);
			final int b = Integer.parseInt(data[5]);
			final int int7 = Integer.parseInt(data[6]);
			this.nodeX[int7][int1] = int2;
			this.nodeY[int7][int1] = int3;
			this.nodeColor[int7][int1] = new Color(r, g, b);
			this.nodeID[int7][int1] = int1;
			this.curNode[int7] = int1 + 1;
			if (r != 255 || g != 255 || b != 255) {
				this.nodeCount[int7] = int1 + 1;
			}
		}
		if (s2.equals("edge")) {
			final int n10 = n2 + 1;
			final int index7 = s.indexOf(32, n10);
			final int int8 = Integer.parseInt(s.substring(n10, index7));
			final int n11 = index7 + 1;
			final int index8 = s.indexOf(32, n11);
			final int int9 = Integer.parseInt(s.substring(n11, index8));
			final int n12 = index8 + 1;
			final int index9 = s.indexOf(32, n12);
			final int int10 = Integer.parseInt(s.substring(n12, index9));
			final int n13 = index9 + 1;
			final int int11 = Integer.parseInt(s.substring(n13, s.indexOf(32, n13)));
			this.link[int8][int11][0] = int9;
			this.link[int8][int11][1] = int10;
			this.curLink[int8] = (this.edgeCount[int8] = int11 + 1);
		}
	}

	void saveFile() {
		final FileDialog fileDialog = new FileDialog(this.ref, "Save file...", 1);
		fileDialog.setDirectory(".");
		fileDialog.show();
		try {
			if (fileDialog.getFile() == null) {
				return;
			}
			final FileWriter fileWriter = new FileWriter(fileDialog.getDirectory() + fileDialog.getFile());
			for (int i = 0; i < 2; ++i) {
				for (int j = 0; j < this.curNode[i]; ++j) {
					fileWriter.write("node " + j + " " + this.nodeX[i][j] + " " + this.nodeY[i][j] + " " + this.nodeColor[i][j].getRed()
							+ " " + this.nodeColor[i][j].getGreen() + " " + this.nodeColor[i][j].getBlue() + " " + i + " \r\n");
				}
			}
			for (int k = 0; k < 2; ++k) {
				for (int l = 0; l < this.edgeCount[k]; ++l) {
					fileWriter.write("edge " + k + " " + this.link[k][l][0] + " " + this.link[k][l][1] + " " + l + " \r\n");
				}
			}
			fileWriter.close();
			this.repaint();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	void findAllPoly(final int[] array, final int[] array2, final int n, final int[][] array3, final int n2) {
		final int[][] array4 = new int[40000][200];
		final int[] array5 = new int[40000];
		for (int i = 0; i < n; ++i) {
			array5[i] = 0;
			for (int j = 0; j < n2; ++j) {
				if (array3[j][0] == i) {
					array4[i][array5[i]++] = array3[j][1];
				}
				if (array3[j][1] == i) {
					array4[i][array5[i]++] = array3[j][0];
				}
			}
		}
		final int[] array6 = new int[200];
		while (true) {
			int n3 = -1;
			int n4 = 10000;
			for (int k = 0; k < n; ++k) {
				if (array[k] < n4 && array5[k] > 0) {
					n4 = array[k];
					n3 = k;
				}
			}
			if (n3 == -1) {
				break;
			}
			final int n5 = n3;
			while (array5[n5] > 0) {
				int nc = 0;
				final int[] array7 = array5;
				final int n6 = n5;
				--array7[n6];
				array6[nc++] = array4[n5][array5[n5]];
				int l = array6[nc - 1];
				int n7 = n5;
				while (l != n5) {
					double n8 = -1000.0;
					int n9 = -1;
					final double atan2 = Math.atan2(array2[l] - array2[n7], array[l] - array[n7]);
					for (int n10 = 0; n10 < array5[l]; ++n10) {
						double n11;
						for (n11 = Math.atan2(array2[array4[l][n10]] - array2[l], array[array4[l][n10]] - array[l])
								- atan2; n11 <= 0.0; n11 += (Math.PI * 2)) {
						}
						if (n11 > n8) {
							if (nc > 1) {
								if (array4[l][n10] == array6[nc - 2] && array5[l] > 1) {
									continue;
								}
							} else if (array4[l][n10] == n5 && array5[l] > 1) {
								continue;
							}
							n8 = n11;
							n9 = n10;
						}
					}
					final int[] array8 = array5;
					final int n12 = l;
					--array8[n12];
					array6[nc++] = array4[l][n9];
					for (int n13 = n9; n13 < array5[l]; ++n13) {
						array4[l][n13] = array4[l][n13 + 1];
					}
					n7 = l;
					l = array6[nc - 1];
				}
				boolean b = true;
				if (this.bc == nc) {
					int n14;
					for (n14 = 0; n14 < nc && array6[n14] != this.borderNodes[0]; ++n14) {
					}
					int n15;
					for (n15 = 0; n15 < nc && array6[(n14 + n15) % nc] == this.borderNodes[n15]; ++n15) {
					}
					if (n15 == nc) {
						b = false;
					}
					int n16;
					for (n16 = 0; n16 < nc && array6[(n14 - n16 + nc) % nc] == this.borderNodes[n16]; ++n16) {
					}
					if (n16 == nc) {
						b = false;
					}
				}
				if (!b) {
					continue;
				}
				final Graphics graphics = this.getGraphics();
				final Graphics graphics2 = this.bi.getGraphics();
				this.redrawGraph(graphics2);
				graphics2.setColor(Color.green);
				for (int n17 = 0; n17 < nc; ++n17) {
					System.out.print(" " + array6[n17]);
					graphics2.drawLine(array[array6[n17]] + 10, array2[array6[n17]] + 10, array[array6[(n17 + 1) % nc]] + 10,
							array2[array6[(n17 + 1) % nc]] + 10);
					graphics.drawImage(this.bi, 10, 10, this);
					try {
						Thread.sleep(500L);
					} catch (Exception ex) {
					}
				}
				graphics2.dispose();
				graphics.dispose();
				for (int n18 = 0; n18 < nc; ++n18) {
					this.curNodes[n18] = array6[n18];
				}
				this.nc = nc;
				System.out.println("");
				this.doTriangulate();
			}
		}
	}

	void doTriangulate() {
		final int[] array = new int[200];
		final int[] array2 = new int[200];
		final int[][] array3 = new int[800][2];
		final int[] array4 = new int[200];
		final int[] array5 = new int[200];
		final int[] array6 = new int[200];
		final int[] array7 = new int[200];
		final int[][] array8 = new int[800][2];
		for (int i = 0; i < this.nc; ++i) {
			array8[i][0] = i;
			array8[i][1] = (i + 1) % this.nc;
			array[i] = this.curNodes[i];
			array6[i] = this.nodeX[this.curGraph][array[i]];
			array7[i] = this.nodeY[this.curGraph][array[i]];
			this.curNodes[i] = i;
		}
		final int triangulate = this.triangulate(this.curNodes, this.nc, array8, this.nc, array6, array7);
		final int n = 1 - this.curGraph;
		for (int j = 0; j < this.nc; ++j) {
			array3[j][0] = j;
			array3[j][1] = (j + 1) % this.nc;
			array4[j] = this.nodeX[n][array[j]];
			array5[j] = this.nodeY[n][array[j]];
			array2[j] = j;
		}
		final int triangulate2 = this.triangulate(array2, this.nc, array3, this.nc, array4, array5);
		final int[] array9 = new int[3];
		this.findSteiner1(this.nc, array6, array7, this.nc, array4, array5, this.nc, array8, triangulate, array3, triangulate2, array9);
		int n2 = this.curNode[this.curGraph];
		for (int k = this.nc; k < array9[0]; ++k) {
			array[k] = n2 + k - this.nc;
		}
		for (int l = this.nc; l < array9[0]; ++l) {
			this.nodeX[this.curGraph][n2] = array6[l];
			this.nodeY[this.curGraph][n2] = array7[l];
			this.nodeX[n][n2] = array4[l];
			this.nodeY[n][n2] = array5[l];
			++n2;
		}
		this.curNode[this.curGraph] = n2;
		this.curNode[n] = n2;
		for (int nc = this.nc; nc < array9[1]; ++nc) {
			this.link[this.curGraph][this.curLink[this.curGraph]][0] = array[array8[nc][0]];
			this.link[this.curGraph][this.curLink[this.curGraph]][1] = array[array8[nc][1]];
			final int[] curLink = this.curLink;
			final int curGraph = this.curGraph;
			++curLink[curGraph];
			this.link[n][this.curLink[n]][0] = array[array3[nc][0]];
			this.link[n][this.curLink[n]][1] = array[array3[nc][1]];
			final int[] curLink2 = this.curLink;
			final int n3 = n;
			++curLink2[n3];
		}
		final Graphics graphics = this.getGraphics();
		final Graphics graphics2 = this.bi.getGraphics();
		this.redrawGraph(graphics2);
		graphics.drawImage(this.bi, 10, 10, this);
		graphics2.dispose();
		graphics.dispose();
		this.nc = 0;
	}

	void correctEdges() {
		final int n = this.curNode[0];
		final int n2 = this.correctNc - 1;
		final int correctEc = this.curLink[0];
		for (int i = 0; i < correctEc; ++i) {
			if (this.link[0][i][0] > n2) {
				final int inExtra = this.isInExtra(this.link[0][i][0]);
				if (inExtra == -1) {
					final int[] array = this.link[0][i];
					final int n3 = 0;
					array[n3] -= this.extraNodeCount;
				} else {
					this.link[0][i][0] = inExtra;
				}
			}
			if (this.link[0][i][1] > n2) {
				final int inExtra2 = this.isInExtra(this.link[0][i][1]);
				if (inExtra2 == -1) {
					final int[] array2 = this.link[0][i];
					final int n4 = 1;
					array2[n4] -= this.extraNodeCount;
				} else {
					this.link[0][i][1] = inExtra2;
				}
			}
			if (this.link[1][i][0] > n2) {
				final int inExtra3 = this.isInExtra(this.link[1][i][0]);
				if (inExtra3 == -1) {
					final int[] array3 = this.link[1][i];
					final int n5 = 0;
					array3[n5] -= this.extraNodeCount;
				} else {
					this.link[1][i][0] = inExtra3;
				}
			}
			if (this.link[1][i][1] > n2) {
				final int inExtra4 = this.isInExtra(this.link[1][i][1]);
				if (inExtra4 == -1) {
					final int[] array4 = this.link[1][i];
					final int n6 = 1;
					array4[n6] -= this.extraNodeCount;
				} else {
					this.link[1][i][1] = inExtra4;
				}
			}
		}
		for (int j = this.correctNc; j < n - this.extraNodeCount; ++j) {
			this.nodeX[0][j] = this.nodeX[0][j + this.extraNodeCount];
			this.nodeY[0][j] = this.nodeY[0][j + this.extraNodeCount];
			this.nodeX[1][j] = this.nodeX[1][j + this.extraNodeCount];
			this.nodeY[1][j] = this.nodeY[1][j + this.extraNodeCount];
		}
		final int[] curNode = this.curNode;
		final int n7 = 0;
		curNode[n7] -= this.extraNodeCount;
		final int[] curNode2 = this.curNode;
		final int n8 = 1;
		curNode2[n8] -= this.extraNodeCount;
		this.extraNodeCount = 0;
		this.correctEc = correctEc;
		this.correctNc = this.curNode[0];
	}

	int isInExtra(final int n) {
		for (int i = 0; i < this.extraNodeCount; ++i) {
			if (this.extraNodes[i][0] == n) {
				return this.extraNodes[i][1];
			}
		}
		return -1;
	}

	void findBorder(final int[] array, final int[] array2, final int n, final int[][] array3, final int n2, final int n3) {
		final int[][] array4 = new int[40000][200];
		final int[] array5 = new int[40000];
		for (int i = 0; i < n; ++i) {
			array5[i] = 0;
			for (int j = 0; j < n2; ++j) {
				if (array3[j][0] == i) {
					array4[i][array5[i]++] = array3[j][1];
				}
				if (array3[j][1] == i) {
					array4[i][array5[i]++] = array3[j][0];
				}
			}
		}
		final int[] array6 = new int[200];
		if (n3 < n) {
			double n4 = -1000.0;
			int n5 = -1;
			for (int k = 0; k < array5[n3]; ++k) {
				double atan2;
				for (atan2 = Math.atan2(array[array4[n3][k]] - array[n3],
						array2[array4[n3][k]] - array2[n3]); atan2 < 0.0; atan2 += (Math.PI * 2)) {
				}
				if (atan2 > n4) {
					n4 = atan2;
					n5 = k;
				}
			}
			if (array5[n3] > 0) {
				int bc = 0;
				final int[] array7 = array5;
				--array7[n3];
				array6[bc++] = array4[n3][n5];
				for (int l = n5; l < array5[n3]; ++l) {
					array4[n3][l] = array4[n3][l + 1];
				}
				int n6 = array6[bc - 1];
				int n7 = n3;
				while (n6 != n3) {
					double n8 = -1000.0;
					int n9 = -1;
					final double atan3 = Math.atan2(array[n7] - array[n6], array2[n7] - array2[n6]);
					for (int n10 = 0; n10 < array5[n6]; ++n10) {
						double n11;
						for (n11 = Math.atan2(array[array4[n6][n10]] - array[n6], array2[array4[n6][n10]] - array2[n6])
								- atan3; n11 < 0.0; n11 += (Math.PI * 2)) {
						}
						if (n11 > n8) {
							if (bc > 1) {
								if (array4[n6][n10] == array6[bc - 2] && array5[n6] > 1) {
									continue;
								}
							} else if (array4[n6][n10] == n3 && array5[n6] > 1) {
								continue;
							}
							n8 = n11;
							n9 = n10;
						}
					}
					final int[] array8 = array5;
					final int n12 = n6;
					--array8[n12];
					array6[bc++] = array4[n6][n9];
					for (int n13 = n9; n13 < array5[n6]; ++n13) {
						array4[n6][n13] = array4[n6][n13 + 1];
					}
					n7 = n6;
					n6 = array6[bc - 1];
				}
				final Graphics graphics = this.getGraphics();
				final Graphics graphics2 = this.bi.getGraphics();
				this.redrawGraph(graphics2);
				graphics2.setColor(Color.red);
				for (int n14 = 0; n14 < bc; ++n14) {
					graphics2.drawLine(array[array6[n14]] + 10, array2[array6[n14]] + 10, array[array6[(n14 + 1) % bc]] + 10,
							array2[array6[(n14 + 1) % bc]] + 10);
					System.out.print(" " + array6[n14]);
					graphics.drawImage(this.bi, 10, 10, this);
					try {
						Thread.sleep(500L);
					} catch (Exception ex) {
					}
				}
				graphics2.dispose();
				graphics.dispose();
				for (int n15 = 0; n15 < bc; ++n15) {
					this.borderNodes[n15] = array6[n15];
				}
				this.bc = bc;
				this.bound.select(this.bc - 3);
			}
		}
		System.out.println("");
	}

	boolean uniquePoly(final int[] array, final int n, final int[][] array2, final int n2, final int[] array3) {
		for (int i = 0; i < n2; ++i) {
			if (array3[i] == n) {
				int n3;
				for (n3 = 0; n3 < n && array2[i][0] != array[n3]; ++n3) {
				}
				int n4;
				for (n4 = 0; n4 < n && array2[i][n4] == array[(n4 + n3) % n]; ++n4) {
				}
				if (n4 == n) {
					return false;
				}
				int n5;
				for (n5 = 0; n5 < n && array2[i][n5] == array[(n3 - n5 + n) % n]; ++n5) {
				}
				if (n5 == n) {
					return false;
				}
			}
		}
		return true;
	}

	void changeMode() {
		final Graphics graphics = this.getGraphics();
		if (this.animateMode) {
			this.ch.setEnabled(true);
			this.drawNode(graphics, 650, 120, this.curColor, 0, 100);
			graphics.setColor(Color.black);
			graphics.fillRect(650, 170, 20, 2);
			final Graphics graphics2 = this.bi.getGraphics();
			this.redrawGraph(graphics2);
			graphics.drawImage(this.bi, 10, 10, this);
			graphics2.dispose();
			this.animate.setLabel("Animate");
		} else {
			this.ch.setEnabled(false);
			graphics.setColor(this.background);
			graphics.fillRect(635, 115, 100, 75);
			final boolean b = false;
			this.selectArc = b;
			this.selectNode = b;
			this.animate.setLabel("Change Graph");
			switch (this.ch.getSelectedIndex()) {
				case 1 : {
					this.calcFramesRigid();
					break;
				}
				case 2 : {
					this.calcFramesConvex();
					break;
				}
				case 3 : {
					this.calcFramesRigid();
					final int n = this.totFrames.getValue() / 4;
					final Graphics graphics3 = this.bi.getGraphics();
					final int[] array = new int[this.curNode[0]];
					final int[] array2 = new int[this.curNode[0]];
					for (int i = 0; i < this.curNode[0]; ++i) {
						array[i] = this.nodeX[0][i];
						array2[i] = this.nodeY[0][i];
					}
					this.rigid1(graphics3, n * 3, n * 3, true);
					this.calcFramesConvex();
					for (int j = 0; j < this.curNode[0]; ++j) {
						this.nodeX[0][j] = array[j];
						this.nodeY[0][j] = array2[j];
					}
					graphics3.dispose();
					break;
				}
			}
		}
		this.animate.validate();
		this.animateMode = !this.animateMode;
		this.start.setEnabled(this.animateMode);
		this.playBackward.setEnabled(this.animateMode);
		this.playForward.setEnabled(this.animateMode);
		this.finish.setEnabled(this.animateMode);
		this.frames.setEnabled(this.animateMode);
		this.initialGraph.setEnabled(!this.animateMode);
		this.finalGraph.setEnabled(!this.animateMode);
		this.open.setEnabled(!this.animateMode);
		this.save.setEnabled(!this.animateMode);
		this.stopAnimate();
		graphics.dispose();
	}

	void animate(final boolean b) {
		if (b) {
			this.dir = 1;
		} else {
			this.dir = -1;
		}
		this.totFrames.setEnabled(false);
		this.frameRate.setEnabled(false);
		this.pause.setEnabled(true);
		this.doAnimate = true;
	}

	void stopAnimate() {
		this.doAnimate = false;
		this.totFrames.setEnabled(true);
		this.frameRate.setEnabled(true);
		this.pause.setEnabled(false);
		this.curFrame -= this.dir;
	}

	void tick() {
		boolean b = false;
		if (this.curFrame > this.totFrames.getValue()) {
			this.curFrame = this.totFrames.getValue();
			b = true;
		}
		if (this.curFrame < 0) {
			this.curFrame = 0;
			b = true;
		}
		final Graphics graphics = this.getGraphics();
		final Graphics graphics2 = this.bi.getGraphics();
		switch (this.ch.getSelectedIndex()) {
			case 0 : {
				this.linearMotion(graphics2);
				break;
			}
			case 1 : {
				this.rigidMotion(graphics2, false);
				break;
			}
			case 2 : {
				this.ConvexMotion(graphics2, this.curFrame, this.totFrames.getValue());
				break;
			}
			case 3 : {
				this.rigidMotion(graphics2, true);
				break;
			}
		}
		graphics.drawImage(this.bi, 10, 10, null);
		graphics2.dispose();
		graphics.dispose();
		this.frames.setValue(this.curFrame);
		this.framesText.setText("" + this.curFrame);
		if (b) {
			this.stopAnimate();
		}
	}

	void captureFrame() {
		this.curFrame = this.frames.getValue();
		this.tick();
	}

	void redrawDimGraph(final Graphics graphics) {
		final Graphics2D graphics2D = (Graphics2D) graphics;
		graphics2D.setColor(Color.white);
		graphics2D.fillRect(0, 0, 600, 550);
		final int n = 10;
		final int n2 = 8;
		final int value = this.opacity.getValue();
		graphics2D.setColor(new Color(255 - 255 * value / 100, 255 - 255 * value / 100, 255 - 255 * value / 100));
		graphics2D.setStroke(new BasicStroke(2.0f));
		for (int i = 0; i < this.edgeCount[0]; ++i) {
			graphics2D.drawLine(this.nodeX[0][this.link[0][i][0]] + n, this.nodeY[0][this.link[0][i][0]] + n,
					this.nodeX[0][this.link[0][i][1]] + n, this.nodeY[0][this.link[0][i][1]] + n);
		}
		if (this.showSteiner.getState()) {
			graphics2D.setStroke(new BasicStroke(1.0f));
			graphics2D.setColor(new Color(255 - 255 * value / 100, 255 - 255 * value / 100, 255));
			for (int j = this.edgeCount[0]; j < this.curLink[0]; ++j) {
				graphics2D.drawLine(this.nodeX[0][this.link[0][j][0]] + n, this.nodeY[0][this.link[0][j][0]] + n,
						this.nodeX[0][this.link[0][j][1]] + n, this.nodeY[0][this.link[0][j][1]] + n);
			}
			graphics2D.setColor(new Color(255 - 255 * value / 100, 200 + 55 * (100 - value) / 100, 255 - 255 * value / 100));
			for (int k = this.nodeCount[0]; k < this.nodeCount[0] + 5; ++k) {
				graphics2D.fillArc(this.nodeX[0][k] + n - n2 / 2, this.nodeY[0][k] + n - n2 / 2, n2, n2, 0, 360);
			}
		}
		graphics2D.setStroke(new BasicStroke(1.0f));
		for (int l = 0; l < this.nodeCount[0]; ++l) {
			this.drawNode(graphics2D, this.nodeX[0][l], this.nodeY[0][l], this.nodeColor[0][l], this.nodeID[0][l], value);
		}
		graphics2D.setStroke(new BasicStroke(2.0f));
		graphics2D.setColor(new Color(255 - 255 * value / 100, 255 - 255 * value / 100, 255 - 255 * value / 100));
		for (int n3 = 0; n3 < this.edgeCount[1]; ++n3) {
			graphics2D.drawLine(this.nodeX[1][this.link[1][n3][0]] + n, this.nodeY[1][this.link[1][n3][0]] + n,
					this.nodeX[1][this.link[1][n3][1]] + n, this.nodeY[1][this.link[1][n3][1]] + n);
		}
		if (this.showSteiner.getState()) {
			graphics2D.setStroke(new BasicStroke(1.0f));
			graphics2D.setColor(new Color(255 - 255 * value / 100, 255 - 255 * value / 100, 255));
			for (int n4 = this.edgeCount[1]; n4 < this.curLink[1]; ++n4) {
				graphics2D.drawLine(this.nodeX[1][this.link[1][n4][0]] + n, this.nodeY[1][this.link[1][n4][0]] + n,
						this.nodeX[1][this.link[1][n4][1]] + n, this.nodeY[1][this.link[1][n4][1]] + n);
			}
			graphics2D.setColor(new Color(255 - 255 * value / 100, 200 + 55 * (100 - value) / 100, 255 - 255 * value / 100));
			for (int n5 = this.nodeCount[1]; n5 < this.nodeCount[1] + 5; ++n5) {
				graphics2D.fillArc(this.nodeX[1][n5] + n - n2 / 2, this.nodeY[1][n5] + n - n2 / 2, n2, n2, 0, 360);
			}
		}
		graphics2D.setStroke(new BasicStroke(1.0f));
		for (int n6 = 0; n6 < this.nodeCount[1]; ++n6) {
			this.drawNode(graphics2D, this.nodeX[1][n6], this.nodeY[1][n6], this.nodeColor[1][n6], this.nodeID[1][n6], value);
		}
	}

	void translate(final int n, final int n2) {
		for (int i = 0; i < this.curNode[0]; ++i) {
			final int[] array = this.nodeX[0];
			final int n3 = i;
			array[n3] += n;
			final int[] array2 = this.nodeX[1];
			final int n4 = i;
			array2[n4] += n;
			final int[] array3 = this.nodeY[0];
			final int n5 = i;
			array3[n5] += n2;
			final int[] array4 = this.nodeY[1];
			final int n6 = i;
			array4[n6] += n2;
		}
	}

	void calcFramesRigid() {
		double n = 0.0;
		double n2 = 0.0;
		double n3 = 0.0;
		double n4 = 0.0;
		double n5 = 0.0;
		double n6 = 0.0;
		double n7 = 0.0;
		double n8 = 0.0;
		double n9 = 0.0;
		double n10 = 0.0;
		double n11 = 0.0;
		final double[] array = new double[2];
		final double[] array2 = new double[2];
		final double n12 = 0.0;
		this.cy = n12;
		this.cx = n12;
		for (int i = 0; i < this.curNode[0]; ++i) {
			this.cx += this.nodeX[0][i];
			this.cy += this.nodeY[0][i];
		}
		this.cx /= this.curNode[0];
		this.cy /= this.curNode[0];
		for (int j = 0; j < this.curNode[0]; ++j) {
			array[0] = (this.nodeX[1][j] - this.cx) / 100.0;
			array[1] = (this.nodeX[0][j] - this.cx) / 100.0;
			array2[0] = (this.nodeY[1][j] - this.cy) / 100.0;
			array2[1] = (this.nodeY[0][j] - this.cy) / 100.0;
			n += array[1] * array[1];
			n2 += array2[1] * array2[1];
			n3 += array[1];
			n4 += array2[1];
			n5 += array[1] * array2[1];
			n6 += array[0] * array2[1];
			n7 += array[0];
			n8 += array[1] * array[0];
			n9 += array2[0] * array2[1];
			n10 += array2[0];
			n11 += array[1] * array2[0];
		}
		final double n13 = n5 * n5;
		final double n14 = n4 * n4;
		final double n15 = this.curNode[0];
		final double n16 = n * (n15 * n2 - n14) - n15 * n13 + n3 * (2.0 * n4 * n5 - n3 * n2);
		this.a11 = -(n5 * (n15 * n6 - n7 * n4) + n8 * (n14 - n15 * n2) + n3 * (n7 * n2 - n4 * n6));
		this.a11 /= n16;
		this.a12 = n * (n15 * n6 - n7 * n4) + n5 * (n7 * n3 - n15 * n8) + n3 * (n4 * n8 - n3 * n6);
		this.a12 /= n16;
		this.a13 = n * (n7 * n2 - n4 * n6) - n7 * n13 + n5 * (n4 * n8 + n3 * n6) - n3 * n2 * n8;
		this.a13 = this.a13 / n16 * 100.0;
		this.a21 = -(n5 * (n15 * n9 - n10 * n4) + n11 * (n14 - n15 * n2) + n3 * (n10 * n2 - n4 * n9));
		this.a21 /= n16;
		this.a22 = n * (n15 * n9 - n10 * n4) + n5 * (n10 * n3 - n15 * n11) + n3 * (n4 * n11 - n3 * n9);
		this.a22 /= n16;
		this.a23 = n * (n10 * n2 - n4 * n9) - n10 * n13 + n5 * (n4 * n11 + n3 * n9) - n3 * n2 * n11;
		this.a23 = this.a23 / n16 * 100.0;
		final double n17 = (this.a11 * this.a22 - this.a12 * this.a21 < 0.0) ? -1.0 : 1.0;
		this.q11 = this.a11 + n17 * this.a22;
		this.q12 = this.a12 - n17 * this.a21;
		this.q21 = this.a21 - n17 * this.a12;
		this.q22 = this.a22 + n17 * this.a11;
		final double sqrt = Math.sqrt(this.q11 * this.q11 + this.q12 * this.q12);
		this.q11 /= sqrt;
		this.q12 /= sqrt;
		this.q21 /= sqrt;
		this.q22 /= sqrt;
		this.rtheta = Math.atan2(this.a21, this.a11);
		final double n18 = this.q11 * this.q22 - this.q12 * this.q21;
		final double n19 = this.q22 / n18;
		final double n20 = -this.q12 / n18;
		final double n21 = -this.q21 / n18;
		final double n22 = this.q11 / n18;
		this.s11 = n19 * this.a11 + n20 * this.a21;
		this.s12 = n19 * this.a12 + n20 * this.a22;
		this.s21 = n21 * this.a11 + n22 * this.a21;
		this.s22 = n21 * this.a12 + n22 * this.a22;
	}

	void linearMotion(final Graphics graphics) {
		final int[] array = new int[this.curNode[0]];
		final int[] array2 = new int[this.curNode[0]];
		final int value = this.totFrames.getValue();
		final Color[] array3 = new Color[this.curNode[0]];
		for (int i = 0; i < this.curNode[0]; ++i) {
			final int n = this.nodeX[0][i];
			final int n2 = this.nodeY[0][i];
			final int n3 = this.nodeX[1][i];
			final int n4 = this.nodeY[1][i];
			final Color color = this.nodeColor[0][i];
			final int red = color.getRed();
			final int green = color.getGreen();
			final int blue = color.getBlue();
			final Color color2 = this.nodeColor[1][i];
			array3[i] = new Color(red + (color2.getRed() - red) * this.curFrame / value,
					green + (color2.getGreen() - green) * this.curFrame / value, blue + (color2.getBlue() - blue) * this.curFrame / value);
			array[i] = n + (n3 - n) * this.curFrame / value;
			array2[i] = n2 + (n4 - n2) * this.curFrame / value;
		}
		this.drawAnimatedGraph(graphics, array, array2, array3);
	}

	void rigidMotion(final Graphics graphics, final boolean b) {
		final int value = this.totFrames.getValue();
		final int n = value / 4;
		if (this.curFrame <= n * 2) {
			this.rigid1(graphics, this.curFrame, n * 2, false);
		} else if (!b) {
			this.rigid2(graphics, this.curFrame - n * 2, value - n * 2);
		} else {
			this.ConvexMotion(graphics, this.curFrame - n * 2, value - n * 2);
		}
	}

	void rigid1(final Graphics graphics, final int n, final int n2, final boolean b) {
		final int[] array = new int[this.curNode[0]];
		final int[] array2 = new int[this.curNode[0]];
		final Color[] array3 = new Color[this.curNode[0]];
		final double n3 = n / (double) n2;
		final double n4 = this.rtheta * n3;
		this.q11 = Math.cos(n4);
		this.q12 = -Math.sin(n4);
		this.q21 = Math.sin(n4);
		this.q22 = Math.cos(n4);
		final double n5 = 1.0 - n3 + n3 * this.s11;
		final double n6 = n3 * this.s12;
		final double n7 = n3 * this.s21;
		final double n8 = 1.0 - n3 + n3 * this.s22;
		for (int i = 0; i < this.curNode[0]; ++i) {
			final double n9 = this.nodeX[0][i] - this.cx;
			final double n10 = this.nodeY[0][i] - this.cy;
			final Color color = this.nodeColor[0][i];
			final int red = color.getRed();
			final int green = color.getGreen();
			final int blue = color.getBlue();
			final Color color2 = this.nodeColor[1][i];
			array3[i] = new Color(red + (color2.getRed() - red) * n / n2, green + (color2.getGreen() - green) * n / n2,
					blue + (color2.getBlue() - blue) * n / n2);
			final double n11 = n5 * n9 + n6 * n10;
			final double n12 = n7 * n9 + n8 * n10;
			array[i] = (int) (this.q11 * n11 + this.q12 * n12 + this.a13 * n3 + this.cx);
			array2[i] = (int) (this.q21 * n11 + this.q22 * n12 + this.a23 * n3 + this.cy);
			if (b) {
				this.nodeX[0][i] = array[i];
				this.nodeY[0][i] = array2[i];
			}
		}
		this.drawAnimatedGraph(graphics, array, array2, array3);
	}

	void rigid2(final Graphics graphics, final int n, final int n2) {
		final int[] array = new int[this.curNode[0]];
		final int[] array2 = new int[this.curNode[0]];
		final Color[] array3 = new Color[this.curNode[0]];
		final double n3 = n / (double) n2;
		this.q11 = Math.cos(this.rtheta);
		this.q12 = -Math.sin(this.rtheta);
		this.q21 = Math.sin(this.rtheta);
		this.q22 = Math.cos(this.rtheta);
		for (int i = 0; i < this.curNode[0]; ++i) {
			final double n4 = this.nodeX[0][i] - this.cx;
			final double n5 = this.nodeY[0][i] - this.cy;
			final double n6 = this.nodeX[1][i];
			final double n7 = this.nodeY[1][i];
			final Color color = this.nodeColor[0][i];
			final int red = color.getRed();
			final int green = color.getGreen();
			final int blue = color.getBlue();
			final Color color2 = this.nodeColor[1][i];
			array3[i] = new Color(red + (color2.getRed() - red) * n / n2, green + (color2.getGreen() - green) * n / n2,
					blue + (color2.getBlue() - blue) * n / n2);
			final double n8 = this.s11 * n4 + this.s12 * n5;
			final double n9 = this.s21 * n4 + this.s22 * n5;
			final double n10 = (int) (this.q11 * n8 + this.q12 * n9 + this.a13 + this.cx);
			final double n11 = (int) (this.q21 * n8 + this.q22 * n9 + this.a23 + this.cy);
			array[i] = (int) (n10 + (n6 - n10) * n3);
			array2[i] = (int) (n11 + (n7 - n11) * n3);
		}
		this.drawAnimatedGraph(graphics, array, array2, array3);
	}

	double getAngle(final int n, final int n2, final int n3, final int n4) {
		return Math.acos((n * (double) n3 + n2 * n4) / Math.sqrt(n * (double) n + n2 * n2) / Math.sqrt(n3 * (double) n3 + n4 * n4));
	}

	void gauss(final double[][] array, final int n, final int n2, final int[] array2, final int n3) {
		for (int i = 0; i < n - 1; ++i) {
			for (int j = i + 1; j < n; ++j) {
				this.rowManipulate(array[j], array[i], array[j][i] / array[i][i], n2);
			}
		}
		for (int k = n - 1; k >= 0; --k) {
			double n4 = array[k][n];
			for (int l = k + 1; l < n; ++l) {
				n4 -= array[k][l] * array2[l];
			}
			array2[k] = (int) (n4 / array[k][k]);
		}
	}

	void rowManipulate(final double[] array, final double[] array2, final double n, final int n2) {
		for (int i = 0; i < n2; ++i) {
			final int n3 = i;
			array[n3] -= array2[i] * n;
		}
	}

	void sort(final double[] array, final int n) {
		final int[] array2 = new int[n];
		for (int i = 0; i < n; ++i) {
			array2[i] = i;
		}
		for (int j = 0; j < n - 1; ++j) {
			for (int k = j + 1; k < n; ++k) {
				if (array[k] < array[j]) {
					final double n2 = array[j];
					array[j] = array[k];
					array[k] = n2;
					final int n3 = array2[j];
					array2[j] = array2[k];
					array2[k] = n3;
				}
			}
		}
		for (int l = 0; l < n; ++l) {
			array[l] = array2[l];
		}
	}

	void calcFramesConvex() {
		final int n = this.curNode[0];
		final int n2 = this.bound.getSelectedIndex() + 3;
		final double[] array = new double[200];
		final double[] array2 = new double[200];
		final double[] array3 = new double[200];
		for (int i = 0; i < n; ++i) {
			int n3;
			for (n3 = 0; n3 < n2 && this.borderNodes[n3] != i; ++n3) {
			}
			if (n3 == n2) {
				final int n4 = this.nodeX[0][i];
				final int n5 = this.nodeY[0][i];
				double n7;
				double n6 = n7 = 0.0;
				int n8 = 0;
				for (int j = 0; j < n; ++j) {
					if (this.adjMat(i, j)) {
						array3[j] = Math.atan2(this.nodeX[0][j] - this.nodeX[0][i], this.nodeY[0][j] - this.nodeY[0][i]) + Math.PI;
						++n8;
					} else {
						array3[j] = 1000.0;
					}
				}
				this.sort(array3, n);
				for (int n9 = n8, k = 0; k < n9; ++k) {
					final int n10 = (int) array3[k];
					final int n11 = (int) array3[(k - 1 + n9) % n9];
					final int n12 = (int) array3[(k + 1) % n9];
					final int n13 = this.nodeX[0][n10] - this.nodeX[0][i];
					final int n14 = this.nodeY[0][n10] - this.nodeY[0][i];
					array2[n10] = (Math.tan(
							this.getAngle(n13, n14, this.nodeX[0][n11] - this.nodeX[0][i], this.nodeY[0][n11] - this.nodeY[0][i]) / 2.0)
							+ Math.tan(this.getAngle(n13, n14, this.nodeX[0][n12] - this.nodeX[0][i], this.nodeY[0][n12] - this.nodeY[0][i])
									/ 2.0))
							/ Math.sqrt(n13 * n13 + n14 * n14);
					n7 += array2[n10];
				}
				int n15 = 0;
				for (int l = 0; l < n; ++l) {
					if (this.adjMat(i, l)) {
						array3[l] = Math.atan2(this.nodeX[1][l] - this.nodeX[1][i], this.nodeY[1][l] - this.nodeY[1][i]) + Math.PI;
						++n15;
					} else {
						array3[l] = 1000.0;
					}
				}
				this.sort(array3, n);
				for (int n16 = n15, n17 = 0; n17 < n16; ++n17) {
					final int n18 = (int) array3[n17];
					final int n19 = (int) array3[(n17 - 1 + n16) % n16];
					final int n20 = (int) array3[(n17 + 1) % n16];
					final int n21 = this.nodeX[1][n18] - this.nodeX[1][i];
					final int n22 = this.nodeY[1][n18] - this.nodeY[1][i];
					array[n18] = (Math.tan(
							this.getAngle(n21, n22, this.nodeX[1][n19] - this.nodeX[1][i], this.nodeY[1][n19] - this.nodeY[1][i]) / 2.0)
							+ Math.tan(this.getAngle(n21, n22, this.nodeX[1][n20] - this.nodeX[1][i], this.nodeY[1][n20] - this.nodeY[1][i])
									/ 2.0))
							/ Math.sqrt(n21 * n21 + n22 * n22);
					n6 += array[n18];
				}
				for (int n23 = 0; n23 < n; ++n23) {
					if (this.adjMat(i, n23)) {
						this.lamda0[i][n23] = array2[n23] / n7;
						this.lamda1[i][n23] = array[n23] / n6;
					} else {
						this.lamda0[i][n23] = (this.lamda1[i][n23] = 0.0);
					}
				}
			}
		}
		this.calcFramesConvex1();
	}

	void calcFramesConvex1() {
		final double n = 0.0;
		this.uY1 = n;
		this.uX1 = n;
		this.uY0 = n;
		this.uX0 = n;
		final int n2 = this.bound.getSelectedIndex() + 3;
		final int n3 = this.curNode[0];
		final int[] array = new int[n3];
		int n4 = 0;
		for (int i = 0; i < n3; ++i) {
			int n5;
			for (n5 = 0; n5 < n2 && this.borderNodes[n5] != i; ++n5) {
			}
			if (n5 != n2) {
				this.uX0 += this.nodeX[0][i];
				this.uX1 += this.nodeX[1][i];
				this.uY0 += this.nodeY[0][i];
				this.uY1 += this.nodeY[1][i];
			}
		}
		this.uX0 /= n2;
		this.uY0 /= n2;
		this.uX1 /= n2;
		this.uY1 /= n2;
		for (int j = 0; j < n3; ++j) {
			int n6;
			for (n6 = 0; n6 < n2 && this.borderNodes[n6] != j; ++n6) {
			}
			if (n6 != n2) {
				final double x = this.nodeX[0][j] - this.uX0;
				final double y = this.nodeY[0][j] - this.uY0;
				final double x2 = this.nodeX[1][j] - this.uX1;
				final double y2 = this.nodeY[1][j] - this.uY1;
				this.rad[0][j] = Math.sqrt(x * x + y * y);
				this.rad[1][j] = Math.sqrt(x2 * x2 + y2 * y2);
				this.theta[0][j] = Math.atan2(y, x);
				this.theta[1][j] = Math.atan2(y2, x2);
				if (n4 == 0) {
					if (this.theta[0][j] < 0.0) {
						this.theta[0][j] += (Math.PI * 2);
					}
				} else {
					while (this.theta[0][j] < this.theta[0][array[n4 - 1]]) {
						this.theta[0][j] += (Math.PI * 2);
					}
				}
				if (n4 == 0) {
					if (this.theta[1][j] < 0.0) {
						this.theta[1][j] += (Math.PI * 2);
					}
				} else {
					while (this.theta[1][j] < this.theta[1][array[n4 - 1]]) {
						this.theta[1][j] += (Math.PI * 2);
					}
				}
				array[n4++] = j;
			}
		}
	}

	boolean adjMat(final int n, final int n2) {
		for (int i = 0; i < this.curLink[0]; ++i) {
			if ((this.link[0][i][0] == n && this.link[0][i][1] == n2) || (this.link[0][i][0] == n2 && this.link[0][i][1] == n)) {
				return true;
			}
		}
		return false;
	}

	void ConvexMotion(final Graphics graphics, final int n, final int n2) {
		final int n3 = this.curNode[0];
		final Color[] array = new Color[this.curNode[0]];
		final int n4 = this.bound.getSelectedIndex() + 3;
		final double[][] array2 = new double[this.curNode[0]][this.curNode[0]];
		final double[] array3 = new double[n3];
		final double[] array4 = new double[n3];
		final int[] array5 = new int[n3];
		final int[] array6 = new int[n3];
		final int[] array7 = new int[n3];
		this.ConvexMotion1(graphics, array3, array4, n, n2);
		final double n5 = n / (double) n2;
		final int[] array8 = new int[n3 - n4];
		int n6 = 0;
		int n7 = 0;
		final int[] array9 = new int[n4];
		for (int i = 0; i < n3; ++i) {
			int n8;
			for (n8 = 0; n8 < n4 && this.borderNodes[n8] != i; ++n8) {
			}
			if (n8 != n4) {
				array9[n7++] = i;
			} else {
				array8[n6++] = i;
			}
		}
		for (int j = 0; j < n3 - n4; ++j) {
			for (int k = 0; k < n3 - n4; ++k) {
				array2[j][k] = (1.0 - n5) * this.lamda0[array8[j]][array8[k]] + n5 * this.lamda1[array8[j]][array8[k]];
				if (j == k) {
					--array2[j][k];
				}
			}
			double n9 = 0.0;
			for (int l = 0; l < n4; ++l) {
				n9 += ((1.0 - n5) * this.lamda0[array8[j]][array9[l]] + n5 * this.lamda1[array8[j]][array9[l]]) * array3[array9[l]];
			}
			array2[j][n3 - n4] = -n9;
		}
		this.gauss(array2, n3 - n4, n3 - n4 + 1, array7, n4);
		for (int n10 = 0; n10 < n3 - n4; ++n10) {
			array5[array8[n10]] = array7[n10];
		}
		for (int n11 = 0; n11 < n3 - n4; ++n11) {
			for (int n12 = 0; n12 < n3 - n4; ++n12) {
				array2[n11][n12] = (1.0 - n5) * this.lamda0[array8[n11]][array8[n12]] + n5 * this.lamda1[array8[n11]][array8[n12]];
				if (n11 == n12) {
					--array2[n11][n12];
				}
			}
			double n13 = 0.0;
			for (int n14 = 0; n14 < n4; ++n14) {
				n13 += ((1.0 - n5) * this.lamda0[array8[n11]][array9[n14]] + n5 * this.lamda1[array8[n11]][array9[n14]])
						* array4[array9[n14]];
			}
			array2[n11][n3 - n4] = -n13;
		}
		this.gauss(array2, n3 - n4, n3 - n4 + 1, array7, n4);
		for (int n15 = 0; n15 < n3 - n4; ++n15) {
			array6[array8[n15]] = array7[n15];
		}
		for (int n16 = 0; n16 < n3; ++n16) {
			int n17;
			for (n17 = 0; n17 < n4 && this.borderNodes[n17] != n16; ++n17) {
			}
			if (n17 != n4) {
				array5[n16] = (int) array3[n16];
				array6[n16] = (int) array4[n16];
			}
		}
		for (int n18 = 0; n18 < n3; ++n18) {
			final Color color = this.nodeColor[0][n18];
			final int red = color.getRed();
			final int green = color.getGreen();
			final int blue = color.getBlue();
			final Color color2 = this.nodeColor[1][n18];
			array[n18] = new Color(red + (color2.getRed() - red) * n / n2, green + (color2.getGreen() - green) * n / n2,
					blue + (color2.getBlue() - blue) * n / n2);
		}
		this.drawAnimatedGraph(graphics, array5, array6, array);
	}

	void ConvexMotion1(final Graphics graphics, final double[] array, final double[] array2, final int n, final int n2) {
		final int n3 = this.bound.getSelectedIndex() + 3;
		final int n4 = this.curNode[0];
		final Color[] array3 = new Color[this.curNode[0]];
		for (int i = 0; i < n4; ++i) {
			int n5;
			for (n5 = 0; n5 < n3 && this.borderNodes[n5] != i; ++n5) {
			}
			if (n5 != n3) {
				final Color color = this.nodeColor[0][i];
				final int red = color.getRed();
				final int green = color.getGreen();
				final int blue = color.getBlue();
				final Color color2 = this.nodeColor[1][i];
				array3[i] = new Color(red + (color2.getRed() - red) * n / n2, green + (color2.getGreen() - green) * n / n2,
						blue + (color2.getBlue() - blue) * n / n2);
				final double n6 = n / (double) n2;
				final double n7 = (1.0 - n6) * this.uX0 + n6 * this.uX1;
				final double n8 = (1.0 - n6) * this.uY0 + n6 * this.uY1;
				final double n9 = (1.0 - n6) * this.rad[0][i] + n6 * this.rad[1][i];
				final double n10 = (1.0 - n6) * this.theta[0][i] + n6 * this.theta[1][i];
				array[i] = n7 + n9 * Math.cos(n10);
				array2[i] = n8 + n9 * Math.sin(n10);
			}
		}
	}

	void drawAnimatedGraph(final Graphics graphics, final int[] array, final int[] array2, final Color[] array3) {
		final Graphics2D graphics2D = (Graphics2D) graphics;
		final int n = 10;
		this.redrawDimGraph(graphics2D);
		graphics2D.setColor(Color.black);
		graphics2D.setStroke(new BasicStroke(2.0f));
		for (int i = 0; i < this.edgeCount[0]; ++i) {
			graphics2D.drawLine(array[this.link[0][i][0]] + n, array2[this.link[0][i][0]] + n, array[this.link[0][i][1]] + n,
					array2[this.link[0][i][1]] + n);
		}
		if (this.showSteiner.getState()) {
			graphics2D.setStroke(new BasicStroke(1.0f));
			graphics2D.setColor(Color.blue);
			for (int j = this.edgeCount[0]; j < this.curLink[0]; ++j) {
				graphics2D.drawLine(array[this.link[0][j][0]] + n, array2[this.link[0][j][0]] + n, array[this.link[0][j][1]] + n,
						array2[this.link[0][j][1]] + n);
			}
		}
		graphics2D.setStroke(new BasicStroke(1.0f));
		for (int k = 0; k < this.nodeCount[0]; ++k) {
			this.drawNode(graphics2D, array[k], array2[k], array3[k], this.nodeID[0][k], 100);
		}
	}

	int findPoly(final int[] array, final int[] array2, final int n, final int[][] array3, final int n2, final int n3, final int[][] array4,
			int n4, final int[][] array5, int n5) {
		final int[][] array6 = new int[200][200];
		final int[] array7 = new int[200];
		for (int i = 0; i < n; ++i) {
			array7[i] = 0;
			for (int j = 0; j < n2; ++j) {
				if (array3[j][0] == i) {
					array6[i][array7[i]++] = array3[j][1];
				}
				if (array3[j][1] == i) {
					array6[i][array7[i]++] = array3[j][0];
				}
			}
		}
		final int[] array8 = new int[200];
		final double[] array9 = new double[200];
		for (int k = n3; k < n; ++k) {
			for (int l = 0; l < array7[k]; ++l) {
				double atan2;
				int n6;
				for (atan2 = Math.atan2(array2[array6[k][l]] - array2[k], array[array6[k][l]] - array[k]), n6 = l - 1; n6 >= 0
						&& atan2 <= array9[n6]; --n6) {
				}
				++n6;
				for (int n7 = l; n7 > n6; --n7) {
					array8[n7] = array8[n7 - 1];
					array9[n7] = array9[n7 - 1];
				}
				array8[n6] = array6[k][l];
				array9[n6] = atan2;
			}
			for (int n8 = 0; n8 < array7[k]; ++n8) {
				int n9;
				for (n9 = 0; n9 < array7[array8[n8]] && array6[array8[n8]][n9] != array8[(n8 + 1) % array7[k]]; ++n9) {
				}
				if (n9 == array7[array8[n8]]) {
					array4[n4][0] = array8[n8];
					array4[n4][1] = array8[(n8 + 1) % array7[k]];
					++n4;
					array5[n5][0] = array8[n8];
					array5[n5][1] = array8[(n8 + 1) % array7[k]];
					++n5;
					array6[array8[n8]][array7[array8[n8]]++] = array8[(n8 + 1) % array7[k]];
					array6[array8[(n8 + 1) % array7[k]]][array7[array8[(n8 + 1) % array7[k]]]++] = array8[n8];
				}
			}
		}
		return n5;
	}

	int findPoly1(final int[] array, final int[] array2, final int n, final int[][] array3, final int n2, final int n3,
			final int[][] array4, int n4, final int[][] array5, int n5) {
		final int[][] array6 = new int[40000][200];
		final int[] array7 = new int[40000];
		for (int i = 0; i < n; ++i) {
			array7[i] = 0;
			for (int j = 0; j < n2; ++j) {
				if (array3[j][0] == i) {
					array6[i][array7[i]++] = array3[j][1];
				}
				if (array3[j][1] == i) {
					array6[i][array7[i]++] = array3[j][0];
				}
			}
		}
		final int[] array8 = new int[200];
		for (int k = n3; k < n; ++k) {
			while (array7[k] > 0) {
				int n6 = 0;
				final int[] array9 = array7;
				final int n7 = k;
				--array9[n7];
				array8[n6++] = array6[k][array7[k]];
				int l = array8[n6 - 1];
				int n8 = k;
				while (l != k) {
					double n9 = -1000.0;
					int n10 = -1;
					final double atan2 = Math.atan2(array[n8] - array[l], array2[n8] - array2[l]);
					for (int n11 = 0; n11 < array7[l]; ++n11) {
						double n12;
						for (n12 = Math.atan2(array[array6[l][n11]] - array[l], array2[array6[l][n11]] - array2[l])
								- atan2; n12 < 0.0; n12 += (Math.PI * 2)) {
						}
						if (n12 > n9) {
							n9 = n12;
							n10 = n11;
						}
					}
					final int[] array10 = array7;
					final int n13 = l;
					--array10[n13];
					array8[n6++] = array6[l][n10];
					for (int n14 = n10; n14 < array7[l]; ++n14) {
						array6[l][n14] = array6[l][n14 + 1];
					}
					n8 = l;
					l = array8[n6 - 1];
				}
				for (int n15 = 2; n15 < n6 - 1; ++n15) {
					array4[n4][0] = array8[0];
					array4[n4][1] = array8[n15];
					++n4;
					array5[n5][0] = array8[0];
					array5[n5][1] = array8[n15];
					++n5;
				}
			}
		}
		return n5;
	}

	int triangulate(final int[] array, int i, final int[][] array2, int n, final int[] array3, final int[] array4) {
		int n2 = 0;
		int left = 0;
		int inside = 0;
		while (i > 3) {
			left = this.getLeft(array, i, array3, array4);
			n2 = array[left];
			final int n3 = array[(left + 1) % i];
			final int n4 = array[(left - 1 + i) % i];
			inside = this.inside(n3, n2, n4, array, i, array3, array4);
			if (inside != -1) {
				break;
			}
			--i;
			for (int j = left; j < i; ++j) {
				array[j] = array[j + 1];
			}
			array2[n][0] = n3;
			array2[n][1] = n4;
			++n;
		}
		if (i <= 3) {
			return n;
		}
		array2[n][0] = n2;
		array2[n][1] = array[inside];
		++n;
		final int abs = Math.abs(left - inside);
		final int n5 = i - abs;
		final int[] array5 = new int[(abs > n5) ? (abs + 1) : (n5 + 1)];
		final int n6 = (left > inside) ? inside : left;
		for (int k = 0; k < abs + 1; ++k) {
			array5[k] = array[k + n6];
		}
		n = this.triangulate(array5, abs + 1, array2, n, array3, array4);
		final int n7 = (left > inside) ? left : inside;
		for (int l = 0; l < n5 + 1; ++l) {
			array5[l] = array[(l + n7) % i];
		}
		n = this.triangulate(array5, n5 + 1, array2, n, array3, array4);
		return n;
	}

	int inside(final int n, final int n2, final int n3, final int[] array, final int n4, final int[] array2, final int[] array3) {
		int n5 = 10000;
		int n6 = -1;
		for (int i = 0; i < n4; ++i) {
			if (this.getAngle1(n, n2, n3, array[i], array2, array3) == (Math.PI * 2) && n5 > array2[i]) {
				n5 = array2[i];
				n6 = i;
			}
		}
		return n6;
	}

	double getAngle1(final int n, final int n2, final int n3, final int n4, final int[] array, final int[] array2) {
		final int n5 = array[n2] - array[n4];
		final int n6 = array2[n2] - array2[n4];
		final int n7 = array[n3] - array[n4];
		final int n8 = array2[n3] - array2[n4];
		final double acos = Math.acos((n5 * n7 + n6 * n8) / (Math.sqrt(n5 * n5 + n6 * n6) * Math.sqrt(n7 * n7 + n8 * n8)));
		final int n9 = array[n3] - array[n4];
		final int n10 = array2[n3] - array2[n4];
		final int n11 = array[n] - array[n4];
		final int n12 = array2[n] - array2[n4];
		final double acos2 = Math.acos((n9 * n11 + n10 * n12) / (Math.sqrt(n9 * n9 + n10 * n10) * Math.sqrt(n11 * n11 + n12 * n12)));
		final int n13 = array[n] - array[n4];
		final int n14 = array2[n] - array2[n4];
		final int n15 = array[n2] - array[n4];
		final int n16 = array2[n2] - array2[n4];
		return acos + acos2 + Math.acos((n13 * n15 + n14 * n16) / (Math.sqrt(n13 * n13 + n14 * n14) * Math.sqrt(n15 * n15 + n16 * n16)));
	}

	int getLeft(final int[] array, final int n, final int[] array2, final int[] array3) {
		int n2 = 100000;
		int n3 = 0;
		for (int i = 0; i < n; ++i) {
			final int n4 = array[i];
			if (array2[n4] < n2) {
				n2 = array2[n4];
				n3 = i;
			}
		}
		return n3;
	}

	void findSteiner1(final int n, final int[] array, final int[] array2, int n2, final int[] array3, final int[] array4, int n3,
			final int[][] array5, int n4, final int[][] array6, int n5, final int[] array7) {
		final int[][] array8 = new int[n * n][2];
		final int[] array9 = new int[n * n];
		int n6 = n;
		final int[] array10 = new int[n * n];
		int n7 = n;
		for (int i = 0; i < n; ++i) {
			array10[i] = (int) (200.0 * Math.cos(i * Math.PI * 2.0 / n) + 250.0);
			array9[i] = (int) (200.0 * Math.sin(i * Math.PI * 2.0 / n) + 250.0);
			array8[i][0] = i;
			array8[i][1] = (i + 1) % n;
		}
		final double[][] array11 = new double[n][n];
		final double[][] array12 = new double[n][n];
		final int[][] array13 = new int[n][n];
		final int[] array14 = new int[n];
		final int[][] array15 = new int[n][n];
		final int[] array16 = new int[n];
		for (int j = 0; j < n; ++j) {
			array16[j] = 2;
			array12[j][0] = 0.0;
			array15[j][0] = array6[j + n][0];
			array12[j][1] = 1.0;
			array15[j][1] = array6[j + n][1];
		}
		for (int k = n; k < n4; ++k) {
			final int n8 = array10[array5[k][0]];
			final int n9 = array10[array5[k][1]];
			final int n10 = array9[array5[k][0]];
			final int n11 = array9[array5[k][1]];
			final int n12 = n9 - n8;
			final int n13 = n11 - n10;
			array14[k - n] = 1;
			array11[k - n][0] = 0.0;
			array13[k - n][0] = array5[k][0];
			for (int l = n; l < n5; ++l) {
				final int n14 = array10[array6[l][0]];
				final int n15 = array10[array6[l][1]];
				final int n16 = array9[array6[l][0]];
				final int n17 = array9[array6[l][1]];
				final int n18 = n15 - n14;
				final int n19 = n17 - n16;
				final int n20 = n14 - n8;
				final int n21 = n16 - n10;
				final double n22 = n18 * n13 - n19 * n12;
				if (n22 != 0.0) {
					final double n23 = (n21 * n12 - n13 * n20) / n22;
					final double n24 = (n21 * n18 - n19 * n20) / n22;
					if (n23 > 0.0 && n23 < 1.0 && n24 > 0.0 && n24 < 1.0) {
						int n25;
						for (n25 = array16[l - n] - 1; n25 > 0 && n23 < array12[l - n][n25]; --n25) {
							array12[l - n][n25 + 1] = array12[l - n][n25];
							array15[l - n][n25 + 1] = array15[l - n][n25];
						}
						final int[] array17 = array16;
						final int n26 = l - n;
						++array17[n26];
						array12[l - n][n25 + 1] = n23;
						array15[l - n][n25 + 1] = n7;
						final int n27 = (int) (n14 + n23 * (n15 - n14));
						final int n28 = (int) (n16 + n23 * (n17 - n16));
						array10[n7] = n27;
						array9[n7] = n28;
						++n7;
						final int n29 = array3[array6[l][0]];
						final int n30 = array3[array6[l][1]];
						final int n31 = array4[array6[l][0]];
						final int n32 = array4[array6[l][1]];
						final int n33 = (int) (n29 + n23 * (n30 - n29));
						final int n34 = (int) (n31 + n23 * (n32 - n31));
						array3[n3] = n33;
						array4[n3] = n34;
						++n3;
						final double n35 = (n20 * n19 - n18 * n21) / (double) (n19 * n12 - n18 * n13);
						final double n36 = (n21 * n18 - n19 * n20) / (double) (n18 * n13 - n19 * n12);
						final int n37 = array[array5[k][0]];
						final int n38 = array[array5[k][1]];
						final int n39 = array2[array5[k][0]];
						final int n40 = array2[array5[k][1]];
						final int n41 = (int) (n37 + n36 * (n38 - n37));
						final int n42 = (int) (n39 + n36 * (n40 - n39));
						array[n2] = n41;
						array2[n2] = n42;
						++n2;
						int n43;
						for (n43 = array14[k - n] - 1; n43 > 0 && n36 < array11[k - n][n43]; --n43) {
							array11[k - n][n43 + 1] = array11[k - n][n43];
							array13[k - n][n43 + 1] = array13[k - n][n43];
						}
						final int[] array18 = array14;
						final int n44 = k - n;
						++array18[n44];
						array11[k - n][n43 + 1] = n36;
						array13[k - n][n43 + 1] = n7 - 1;
					}
				}
			}
			array11[k - n][array14[k - n]] = 1.0;
			array13[k - n][array14[k - n]] = array5[k][1];
			final int[] array19 = array14;
			final int n45 = k - n;
			++array19[n45];
		}
		final int n46 = n4 - n;
		final int n47 = n5 - n;
		n4 = n;
		n5 = n;
		for (int n48 = 0; n48 < n46; ++n48) {
			for (int n49 = 0; n49 < array14[n48] - 1; ++n49) {
				array8[n6][0] = array13[n48][n49];
				array8[n6][1] = array13[n48][n49 + 1];
				++n6;
				array5[n4][0] = array13[n48][n49];
				array5[n4][1] = array13[n48][n49 + 1];
				++n4;
				array6[n5][0] = array13[n48][n49];
				array6[n5][1] = array13[n48][n49 + 1];
				++n5;
			}
		}
		for (int n50 = 0; n50 < n47; ++n50) {
			for (int n51 = 0; n51 < array16[n50] - 1; ++n51) {
				array8[n6][0] = array15[n50][n51];
				array8[n6][1] = array15[n50][n51 + 1];
				++n6;
				array5[n4][0] = array15[n50][n51];
				array5[n4][1] = array15[n50][n51 + 1];
				++n4;
				array6[n5][0] = array15[n50][n51];
				array6[n5][1] = array15[n50][n51 + 1];
				++n5;
			}
		}
		array7[0] = n2;
		array7[1] = n4;
	}
}
