package view;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.interfaces.Shape;
import models.DrawingEngineSingleton;
import models.MyDrawingEngine;
import models.ShadingShapeSingleton;
import models.shapesImpl.Circle;
import models.shapesImpl.Elipse;
import models.shapesImpl.Line;
import models.shapesImpl.MyShape;
import models.shapesImpl.Rectangle;
import models.shapesImpl.Square;
import models.shapesImpl.Triangle;

class Paint {

    static MyDrawingEngine DrawingEngine;
    int index = 0;
    private JFrame frame;
    JLabel currentShapeLabel;
    JComboBox comboBox;
    Painter canvas = new Painter();

    public class Painter extends JPanel {

	public Painter() {
	    addMouseListener(new MouseAdapter() {
		@Override
		public void mousePressed(MouseEvent e) {
		    if (currentShapeLabel.getText().equals("Copy")) {
			x = e.getX();
			y = e.getY();
			try {
			    MyShape copied = (MyShape) DrawingEngine.shapes
				    .get(comboBox.getSelectedIndex()).clone();
			    copied.setPosition(new Point(x, y));
			    for (int j = DrawingEngine.shapes.size()
				    - 1; j >= 0; j--) {
				if (copied.getClass().getSimpleName()
					.equals(DrawingEngine.shapes.get(j)
						.getClass().getSimpleName())) {
				    copied.Counter = ((MyShape) DrawingEngine.shapes
					    .get(j)).Counter + 1;
				    break;
				}
			    }
			    DrawingEngine.addShape(copied);
			    repaint();
			} catch (CloneNotSupportedException ef) {
			    // TODO Auto-generated catch block
			    ef.printStackTrace();
			}
		    }
		    if (currentShapeLabel.getText().equals("triangle")) {
			x = e.getX();
			y = e.getY();
			trianglePoints.add(x);
			trianglePoints.add(y);
			Circle c = new Circle();
			Map<String, java.lang.Double> propertiess = new HashMap<String, java.lang.Double>();
			propertiess.put("radius", 8.0);
			c.setProperties(propertiess);
			c.setColor(Color.BLACK);
			c.setFillColor(null);
			Point position1 = new Point(x, y);
			c.setPosition(position1);
			c.draw(canvas.getGraphics());
			if (trianglePoints.size() == 6) {
			    Triangle t = new Triangle();
			    t.setColor(color); // setting Fill color
			    t.setFillColor(fillC); // setting position
			    Point position = new Point(trianglePoints.get(0),
				    trianglePoints.get(1));
			    t.setPosition(position); // setting properties
			    Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			    properties.put("secondX",
				    (double) trianglePoints.get(2));
			    properties.put("secondY",
				    (double) trianglePoints.get(3));
			    properties.put("thirdX",
				    (double) trianglePoints.get(4));
			    properties.put("thirdY",
				    (double) trianglePoints.get(5));
			    t.setProperties(properties);
			    c.draw(canvas.getGraphics());
			    t.Counter = counterT++;
			    DrawingEngine.addShape(t);
			    canvas.repaint();
			    prepareComboBox(comboBox, DrawingEngineSingleton.drawingEngine());
			    trianglePoints.clear();
			}
		    }
		    for (int i = 0; i < DrawingEngine.getSupportedShapes()
			    .size(); i++) {
			if (currentShapeLabel.getText().equals(DrawingEngine
				.getSupportedShapes().get(i).getSimpleName())) {
			    x = e.getX();
			    y = e.getY();
			    Constructor constructor = null;
			    try {
				constructor = DrawingEngine.getSupportedShapes()
					.get(i).getConstructor();
			    } catch (NoSuchMethodException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			    } catch (SecurityException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			    }
			    MyShape shapeObject = null;
			    try {
				shapeObject = (MyShape) constructor
					.newInstance();
			    } catch (InstantiationException
				    | IllegalAccessException
				    | IllegalArgumentException
				    | InvocationTargetException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			    }
			    int dummii;
			    if (currentShapeLabel.getText().equals("Square")) {
				dummii = 1;
			    }
			    repaint();
			    setProperties(shapeObject);
			    Map<String, java.lang.Double> properties = shapeObject
				    .getProperties();
			    String name = DrawingEngine.getSupportedShapes()
				    .get(i).getSimpleName();
			    int counter2 = 0;
			    if (!(textFields.get(counter2).getText())
				    .equals("")) {
				for (Map.Entry<String, Double> entry : properties
					.entrySet()) {

				    properties
					    .put(labels.get(counter2).getText(),
						    Double.valueOf(textFields
							    .get(counter2)
							    .getText()));
				    counter2++;

				    /*
				     * catch (Exception ek) {
				     * System.out.println(ek.getMessage()); }
				     */
				}
				shapeObject.setProperties(properties);
				for (int j = DrawingEngine.shapes.size()
					- 1; j >= 0; j--) {
				    if (DrawingEngine.getSupportedShapes()
					    .get(i).getSimpleName()
					    .equals(DrawingEngine.shapes.get(j)
						    .getClass()
						    .getSimpleName())) {
					shapeObject.Counter = ((MyShape) DrawingEngine.shapes
						.get(j)).Counter + 1;
					break;
				    }
				}
				DrawingEngine.addShape(shapeObject);
				canvas.repaint();
				prepareComboBox(comboBox, DrawingEngineSingleton.drawingEngine());
				currentShapeLabel.setText("None");
			    }
			}
		    }

		}

		@Override
		public void mouseReleased(MouseEvent e) {
		    // TODO Auto-generated method stub
		    if (currentShapeLabel.getText().equals("Circle")
			    && flag == 1) {
			flag = 0;
			Circle c = new Circle();
			c.setColor(color);
			c.setFillColor(fillC);
			c.setPosition(new Point(x1, y1));
			Map<String, java.lang.Double> prop = new HashMap<String, java.lang.Double>();
			prop.put("radius", r);
			c.setProperties(prop);
			for (int j = DrawingEngine.shapes.size()
				- 1; j >= 0; j--) {
			    if ("Circle".equals(DrawingEngine.shapes.get(j)
				    .getClass().getSimpleName())) {
				c.Counter = ((MyShape) DrawingEngine.shapes
					.get(j)).Counter + 1;
				break;
			    }
			}
			DrawingEngine.addShape(c);
			prepareComboBox(comboBox, DrawingEngine);
			repaint();
		    }
		    if (currentShapeLabel.getText().equals("Square")
			    && flag == 1) {
			flag = 0;
			Square sq = new Square();
			sq.setColor(color);
			sq.setFillColor(fillC);
			sq.setPosition(new Point(x1, y1));
			Map<String, java.lang.Double> prop = new HashMap<String, java.lang.Double>();
			prop.put("length", dragLength);
			sq.setProperties(prop);

			for (int j = DrawingEngine.shapes.size()
				- 1; j >= 0; j--) {
			    if ("Square".equals(DrawingEngine.shapes.get(j)
				    .getClass().getSimpleName())) {
				sq.Counter = ((MyShape) DrawingEngine.shapes
					.get(j)).Counter + 1;
				break;
			    }
			}
			DrawingEngine.addShape(sq);
			prepareComboBox(comboBox, DrawingEngineSingleton.drawingEngine());
			repaint();
		    }
		    if (currentShapeLabel.getText().equals("Rectangle")
			    && flag == 1) {
			flag = 0;
			Rectangle rec = new Rectangle();
			rec.setColor(color);
			rec.setFillColor(fillC);
			rec.setPosition(new Point(x1, y1));
			Map<String, java.lang.Double> prop = new HashMap<String, java.lang.Double>();
			prop.put("length", dragLength);
			prop.put("width", dragWidth);
			rec.setProperties(prop);

			for (int j = DrawingEngine.shapes.size()
				- 1; j >= 0; j--) {
			    if ("Rectangle".equals(DrawingEngine.shapes.get(j)
				    .getClass().getSimpleName())) {
				rec.Counter = ((MyShape) DrawingEngine.shapes
					.get(j)).Counter + 1;
				break;
			    }
			}
			DrawingEngine.addShape(rec);
			prepareComboBox(comboBox, DrawingEngine);
			repaint();
		    }
		    if (currentShapeLabel.getText().equals("Elipse")
			    && flag == 1) {
			flag = 0;
			Elipse elps = new Elipse();
			elps.setColor(color);
			elps.setFillColor(fillC);
			elps.setPosition(new Point(x1, y1));
			Map<String, java.lang.Double> prop = new HashMap<String, java.lang.Double>();
			prop.put("a", dragLength);
			prop.put("b", dragWidth);
			elps.setProperties(prop);

			for (int j = DrawingEngine.shapes.size()
				- 1; j >= 0; j--) {
			    if ("Elipse".equals(DrawingEngine.shapes.get(j)
				    .getClass().getSimpleName())) {
				elps.Counter = ((MyShape) DrawingEngine.shapes
					.get(j)).Counter + 1;
				break;
			    }
			}
			DrawingEngine.addShape(elps);
			prepareComboBox(comboBox, DrawingEngineSingleton.drawingEngine());
			repaint();
		    }
		    if (currentShapeLabel.getText().equals("Line")
			    && flag == 1) {
			flag = 0;
			Line l = new Line();
			l.setColor(color);
			l.setFillColor(fillC);
			l.setPosition(new Point(x1, y1));
			Map<String, java.lang.Double> prop = new HashMap<String, java.lang.Double>();
			prop.put("x2", (double) X2);
			prop.put("y2", (double) Y2);
			l.setProperties(prop);

			for (int j = DrawingEngine.shapes.size()
				- 1; j >= 0; j--) {
			    if ("Line".equals(DrawingEngine.shapes.get(j)
				    .getClass().getSimpleName())) {
				l.Counter = ((MyShape) DrawingEngine.shapes
					.get(j)).Counter + 1;
				break;
			    }
			}
			DrawingEngine.addShape(l);
			prepareComboBox(comboBox, DrawingEngineSingleton.drawingEngine());
			repaint();
		    }
		}
	    });
	    addMouseMotionListener(new MouseMotionAdapter() {
		@Override
		public void mouseDragged(MouseEvent arg0) {
		    if (flag == 0) {
			x1 = Math.abs(arg0.getX());
			y1 = Math.abs(arg0.getY());
			flag = 1;

		    }
		    int x2 = Math.abs(arg0.getX());
		    int y2 = Math.abs(arg0.getY());
		    if (currentShapeLabel.getText().equals("Circle")) {
			Circle c = new Circle();
			c.setPosition(new Point(x1, y1));
			c.setColor(color);
			c.setFillColor(fillC);
			r = Math.hypot(x2 - x1, y2 - y1);
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			r = r * 2;
			properties.put("radius", r);
			c.setProperties(properties);
			draggedShape = c;
			canvas.repaint();
			X2 = x2;
			Y2 = y2;
		    } else if (currentShapeLabel.getText().equals("Square")) {
			Square sq = new Square();
			sq.setPosition(new Point(x1, y1));
			sq.setColor(color);
			sq.setFillColor(fillC);
			dragLength = Math.hypot(x2 - x1, y2 - y1);
			dragLength = dragLength / 1.41421356237309;
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("length", dragLength);
			sq.setProperties(properties);
			draggedShape = sq;
			canvas.repaint();

		    } else if (currentShapeLabel.getText()
			    .equals("Rectangle")) {
			Rectangle rec = new Rectangle();
			rec.setPosition(new Point(x1, y1));
			rec.setColor(color);
			rec.setFillColor(fillC);
			dragLength = (x2 - x1);
			dragWidth = (y2 - y1);
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("length", dragLength);
			properties.put("width", dragWidth);
			rec.setProperties(properties);
			draggedShape = rec;
			canvas.repaint();
		    } else if (currentShapeLabel.getText().equals("Elipse")) {
			Elipse elps = new Elipse();
			elps.setPosition(new Point(x1, y1));
			elps.setColor(color);
			elps.setFillColor(fillC);
			dragLength = (x2 - x1)*2;
			dragWidth = (y2 - y1)*2;
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("a", dragLength);
			properties.put("b", dragWidth);
			elps.setProperties(properties);
			draggedShape = elps;
			canvas.repaint();
		    } else if (currentShapeLabel.getText().equals("Line")) {
			Line l = new Line();
			l.setPosition(new Point(x1, y1));
			l.setColor(color);
			l.setFillColor(fillC);
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("x2", (double) x2);
			properties.put("y2", (double) y2);
			l.setProperties(properties);
			draggedShape = l;
			canvas.repaint();
			X2 = x2;
			Y2 = y2;
		    } else if (currentShapeLabel.getText().equals("brush")) {
			Circle c = new Circle();
			c.setPosition(new Point(x2, y2));
			c.setColor(color);
			c.setFillColor(color);
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("radius", 10.0);
			c.setProperties(properties);
			c.draw(canvas.getGraphics());
			//DrawingEngineSingleton.drawingEngine().addShape(c);
		    }
		}

	    });
	}

	@Override
	public void paintComponent(Graphics g) {
	    //if(selectionFlag == 0) {
	    super.paintComponent(g);
	    System.out.println("Auto Refresh Done");
	    DrawingEngineSingleton.drawingEngine().refresh(g);
	    if(draggedShape!=null) {
	    draggedShape.draw(g);
	    draggedShape = null;
	    }
	    if(ShadingShapeSingleton.shadingShape() != null) {
		    ShadingShapeSingleton.shadingShape().draw(g);
	    }
	}
    }

    Shape draggedShape = null;
    int selectionFlag = 0;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		    Paint window = new Paint();
		    window.frame.setVisible(true);
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    public Paint() {
	initialize();
    }

    /*
     * @Override public void paintComponent(Graphics g) {
     * 
     * }
     */

    int counterE = 1;
    int counterS = 1;
    int counterC = 1;
    int counterR = 1;
    int counterT = 1;
    int counterL = 1;

    Double r;
    int x1 = 0;
    int y1 = 0;

    private void prepareComboBox(JComboBox comboBox,
	    MyDrawingEngine drawingEngine) {
	// TODO Auto-generated method stub
	comboBox.removeAllItems();
	for (int i = 0; i < drawingEngine.shapes.size(); i++) {
	    comboBox.addItem(((MyShape) drawingEngine.shapes.get(i)).getClass()
		    .getSimpleName() + " "
		    + String.valueOf(
			    ((MyShape) drawingEngine.shapes.get(i)).Counter));
	}
	comboBox.setSelectedIndex(drawingEngine.shapes.size() - 1);
    }

    private void prepareLabel(JLabel label, JLabel currentShapeLabel) {
	switch (currentShapeLabel.getText()) {
	case "Circle":
	    label.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/circleIcon.png")));
	    break;
	case "Line":
	    label.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/lineIcon.png")));
	    break;
	case "Rectangle":
	    label.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/rectangleIcon.png")));
	    break;
	case "Square":
	    label.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/squareIcon.png")));
	    break;
	case "Elipse":
	    label.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/elipseIcon.png")));
	    break;
	case "triangle":
	    label.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/triangleIcon.png")));
	    break;
	case "None":
	    label.setIcon(null);
	    break;
	default:
	    label.setIcon(null);

	}

    }

    private void setProperties(MyShape obj) {
	// TODO Auto-generated method stub
	// settingColor
	obj.setColor(color);
	// setting Fill color
	obj.setFillColor(fillC);
	// setting position
	Point position = new Point(x, y);
	obj.setPosition(position);

    }

    public void clearLabelsAndTextFields() {

	for (int i = 0; i < labels.size(); i++) {
	    frame.getContentPane().remove(labels.get(i));
	    frame.getContentPane().remove(textFields.get(i));
	}
	labels.clear();
	textFields.clear();
    }

    ArrayList<JLabel> labels = new ArrayList<JLabel>();
    ArrayList<JTextField> textFields = new ArrayList<JTextField>();

    public void prepareInputLabels(String shapeName) {
	clearLabelsAndTextFields();
	for (int i = 0; i < DrawingEngine.getSupportedShapes().size(); i++) {
	    if (shapeName.equals(((Class<? extends MyShape>) DrawingEngine
		    .getSupportedShapes().get(i)).getSimpleName())) {
		Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
		try {
		    properties = DrawingEngine.getSupportedShapes().get(i)
			    .getConstructor().newInstance().getProperties();
		} catch (InstantiationException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IllegalAccessException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (IllegalArgumentException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (InvocationTargetException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (NoSuchMethodException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		} catch (SecurityException e) {
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		int counter = 0;
		for (Map.Entry<String, Double> entry : properties.entrySet()) {
		    JLabel label = new JLabel(entry.getKey());
		    label.setFont(new Font("Tahoma", Font.PLAIN, 15));
		    label.setBounds(10, 372 + 60 + (23 + 28 + 5) * counter, 56,
			    23);
		    frame.getContentPane().add(label);
		    labels.add(label);
		    JTextField textFld = new JTextField();
		    textFld.setBounds(10, 396 + 60 + (28 + 23 + 5) * counter,
			    59, 28);
		    frame.getContentPane().add(textFld);
		    textFld.setColumns(10);
		    textFields.add(textFld);
		    counter++;

		}
		frame.repaint();
	    }
	}

    }

    /**
     * Initialize the contents of the frame.
     */
    int flag = 0;
    int x = 0;
    int y = 0;
    int X2, Y2;
    int length = 0;
    MyShape currentShape;
    double dragLength = 0;
    double dragWidth = 0;
    Color color = Color.BLACK, fillC = null;
    ArrayList<Integer> trianglePoints = new ArrayList<Integer>();
    ArrayList<Integer> linePoints = new ArrayList<Integer>();
    private JTextField textField;

    private void initialize() {
	frame = new JFrame();
	frame.getContentPane().setBackground(new Color(211, 211, 211));
	frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 15));
	frame.setBounds(100, 100, 1113, 718);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	frame.setResizable(false);

	DrawingEngine = DrawingEngineSingleton.drawingEngine();
	// Canvas canvas = CanvasSingleton.Canvas();
	// canvas.setBackground(Color.WHITE);
	// canvas.setBounds(91, 62, 1006, 557);
	// frame.getContentPane().add(canvas);

	canvas.setBackground(Color.WHITE);
	canvas.setBounds(91, 62, 1006, 557);
	frame.getContentPane().add(canvas);

	JButton btnNewButton_6 = new JButton("");
	btnNewButton_6.setBackground(null);
	btnNewButton_6.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JColorChooser jcc = new JColorChooser();
		fillC = jcc.showDialog(null, "Please Choose the Fill Color",
			null);
		canvas.repaint();
		if (fillC != null) {
		    btnNewButton_6.setBackground(fillC);
		}
	    }
	});
	btnNewButton_6.setBounds(899, 33, 200, 23);
	btnNewButton_6.setToolTipText("Fill Color");
	frame.getContentPane().add(btnNewButton_6);

	JButton btnNewButton_1 = new JButton("");
	btnNewButton_1.setForeground(Color.WHITE);
	btnNewButton_1.setBackground(Color.BLACK);
	btnNewButton_1.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JColorChooser jcc = new JColorChooser();
		color = jcc.showDialog(null, "Please Choose the Outline Color",
			Color.black);
		canvas.repaint();
		if (color != null) {
		    btnNewButton_1.setBackground(color);
		}
	    }
	});
	btnNewButton_1.setBounds(899, 5, 200, 23);
	frame.getContentPane().add(btnNewButton_1);
	btnNewButton_1.setToolTipText("Outline Color");

	currentShapeLabel = new JLabel("None");
	currentShapeLabel.setFont(new Font("Calisto MT", Font.PLAIN, 16));
	currentShapeLabel.setBounds(717, 643, 109, 23);
	frame.getContentPane().add(currentShapeLabel);

	JLabel picLabel = new JLabel("");
	picLabel.setBounds(820, 625, 59, 41);
	frame.getContentPane().add(picLabel);

	comboBox = new JComboBox();
	comboBox.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		Graphics g = canvas.getGraphics();
		if (comboBox.getSelectedIndex() == -1) {
		    ;
		} else {
		    canvas.repaint();
		    selectionFlag = 1;
		    int index = comboBox.getSelectedIndex();
		    Square selecting = new Square();
		    selecting.setColor(new Color(128, 128, 128, 40));
		    selecting.setFillColor(new Color(128, 128, 128, 40));
		    double lengthh = 0;
		    if (((MyShape) DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex())).getName()
				    .equals("Circle ")) {
			lengthh = DrawingEngine.shapes.get(index)
				.getProperties().get("radius");
			double posX = DrawingEngine.shapes.get(index)
				.getPosition().getX() - lengthh / 2;
			double posY = DrawingEngine.shapes.get(index)
				.getPosition().getY() - lengthh / 2;
			selecting
				.setPosition(new Point((int) posX, (int) posY));
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("length", lengthh);

			selecting.setProperties(properties);
			ShadingShapeSingleton.shadingShape = selecting;
		    } else if (((MyShape) DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex())).getName()
				    .equals("Square ")) {
			lengthh = DrawingEngine.shapes.get(index)
				.getProperties().get("length") + 40;
			double posX = DrawingEngine.shapes.get(index)
				.getPosition().getX();
			double posY = DrawingEngine.shapes.get(index)
				.getPosition().getY();
			selecting.setPosition(
				new Point((int) posX - 20, (int) posY - 20));
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("length", lengthh);

			selecting.setProperties(properties);
			ShadingShapeSingleton.shadingShape = selecting;
		    } else if (((MyShape) DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex())).getName()
				    .equals("Rectangle ")) {
			Rectangle rec = new Rectangle();
			rec.setColor(new Color(128, 128, 128, 40));
			rec.setFillColor(new Color(128, 128, 128, 40));
			double lnt = 0;
			double wdt = 0;
			lnt = DrawingEngine.shapes.get(index).getProperties()
				.get("length") + 40;
			wdt = DrawingEngine.shapes.get(index).getProperties()
				.get("width") + 40;
			double posX = DrawingEngine.shapes.get(index)
				.getPosition().getX();
			double posY = DrawingEngine.shapes.get(index)
				.getPosition().getY();
			rec.setPosition(
				new Point((int) posX - 20, (int) posY - 20));
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("length", lnt);
			properties.put("width", wdt);
			rec.setProperties(properties);
			ShadingShapeSingleton.shadingShape = rec;
		    } else if (((MyShape) DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex())).getName()
				    .equals("Line ")) {
			canvas.repaint();
			Line l = new Line();
			l.setProperties(DrawingEngine.shapes
				.get(comboBox.getSelectedIndex())
				.getProperties());
			l.setPosition(DrawingEngine.shapes
				.get(comboBox.getSelectedIndex())
				.getPosition());
			l.setColor(new Color(128, 0, 128));
			l.setStroke(6);
			ShadingShapeSingleton.shadingShape = l;
		    } else if (((MyShape) DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex())).getName()
				    .equals("Ellipse ")) {
			Rectangle rec = new Rectangle();
			rec.setColor(new Color(128, 128, 128, 40));
			rec.setFillColor(new Color(128, 128, 128, 40));
			double lnt = 0;
			double wdt = 0;
			lnt = DrawingEngine.shapes.get(index).getProperties()
				.get("a") + 40;
			wdt = DrawingEngine.shapes.get(index).getProperties()
				.get("b") + 40;
			double posX = DrawingEngine.shapes.get(index)
				.getPosition().getX() - lnt / 3;
			double posY = DrawingEngine.shapes.get(index)
				.getPosition().getY() - wdt / 3;
			rec.setPosition(
				new Point((int) posX - 20, (int) posY - 20));
			Map<String, java.lang.Double> properties = new HashMap<String, java.lang.Double>();
			properties.put("length", lnt);
			properties.put("width", wdt);
			rec.setProperties(properties);
			ShadingShapeSingleton.shadingShape = rec;
		    } else if (((MyShape) DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex())).getName()
				    .equals("Triangle ")) {
			Triangle tr = new Triangle();
			tr.setColor(DrawingEngine.shapes.get(index).getColor());
			tr.setFillColor(new Color(128, 128, 128, 40));
			tr.setProperties(DrawingEngine.shapes.get(index).getProperties());
			tr.setPosition(DrawingEngine.shapes.get(index).getPosition());
			ShadingShapeSingleton.shadingShape = tr;
		    }
		}
	    }
	});

	comboBox.setBounds(222, 5, 128, 34);
	frame.getContentPane().add(comboBox);

	JButton copyButton = new JButton("");
	copyButton.setToolTipText("Copy Selected Shape to be Pasted on click");
	copyButton.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/copyIcon.png")));
	copyButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		currentShapeLabel.setText("Copy");
	    }
	});
	copyButton.setBounds(478, 5, 49, 34);
	frame.getContentPane().add(copyButton);

	JButton Circle = new JButton("");
	Circle.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/circleIcon.png")));
	Circle.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		currentShapeLabel.setText("Circle");
		prepareLabel(picLabel, currentShapeLabel);
		prepareInputLabels("Circle");
	    }
	});
	Circle.setBounds(10, 62, 59, 42);
	frame.getContentPane().add(Circle);

	JButton Triangle = new JButton("");
	Triangle.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		currentShapeLabel.setText("triangle");
		prepareLabel(picLabel, currentShapeLabel);
		prepareInputLabels("triangle");
	    }
	});
	Triangle.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/triangleIcon.png")));
	Triangle.setBounds(10, 223, 59, 42);
	frame.getContentPane().add(Triangle);

	JButton Line = new JButton("");
	Line.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		currentShapeLabel.setText("Line");
		prepareLabel(picLabel, currentShapeLabel);
		prepareInputLabels("Line");
	    }
	});
	Line.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/lineIcon.png")));
	Line.setBounds(10, 117, 59, 42);
	frame.getContentPane().add(Line);

	JButton Rectangle = new JButton("");
	Rectangle.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		currentShapeLabel.setText("Rectangle");
		prepareLabel(picLabel, currentShapeLabel);
		prepareInputLabels("Rectangle");
	    }
	});
	Rectangle.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/rectangleIcon.png")));
	Rectangle.setBounds(10, 276, 59, 42);
	frame.getContentPane().add(Rectangle);

	JButton Elipse = new JButton("");
	Elipse.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		currentShapeLabel.setText("Elipse");
		prepareLabel(picLabel, currentShapeLabel);
		prepareInputLabels("Elipse");
	    }
	});
	Elipse.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/elipseIcon.png")));
	Elipse.setBounds(10, 170, 59, 42);
	frame.getContentPane().add(Elipse);

	JButton Square = new JButton("");
	Square.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		currentShapeLabel.setText("Square");
		prepareLabel(picLabel, currentShapeLabel);
		prepareInputLabels("Square");

	    }
	});
	Square.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/squareIcon.png")));
	Square.setBounds(10, 329, 59, 42);
	frame.getContentPane().add(Square);

	JLabel counterr = new JLabel("");
	counterr.setFont(new Font("Tahoma", Font.PLAIN, 36));
	counterr.setBounds(269, 635, 73, 44);
	frame.getContentPane().add(counterr);

	JButton refreshButton = new JButton("");
	refreshButton.setToolTipText("Refresh");
	refreshButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		canvas.repaint();
		counterr.setText(
			String.valueOf(DrawingEngine.getShapes().length));
		prepareComboBox(comboBox, DrawingEngine);

	    }
	});
	refreshButton.setBounds(537, 5, 49, 34);
	frame.getContentPane().add(refreshButton);
	refreshButton.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/refreshIcon.png")));

	ButtonGroup group = new ButtonGroup();

	JLabel lblCurrentShape = new JLabel("Current Shape:");
	lblCurrentShape.setFont(new Font("Tahoma", Font.PLAIN, 15));
	lblCurrentShape.setBounds(706, 625, 109, 23);
	frame.getContentPane().add(lblCurrentShape);

	JButton btnNewButton_2 = new JButton("");
	btnNewButton_2.setToolTipText("Load");
	btnNewButton_2.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"JSON", "XML");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    String path = chooser.getSelectedFile().getAbsolutePath();
		    System.out.println(path);
		    DrawingEngine.load(path);
		    refreshButton.doClick();
		}
		Graphics cnv = canvas.getGraphics();
		canvas.repaint();
		prepareComboBox(comboBox, DrawingEngine);
	    }
	});
	btnNewButton_2.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/loadIcon.png")));
	btnNewButton_2.setFont(new Font("Tahoma", Font.PLAIN, 13));
	btnNewButton_2.setBounds(10, 5, 43, 34);
	frame.getContentPane().add(btnNewButton_2);

	JButton btnNewButton_3 = new JButton("",
		new ImageIcon(this.getClass().getResource("/Resources/images/saveIcon.png")));
	btnNewButton_3.setToolTipText("Save");
	btnNewButton_3.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter(
			"JSON", "XML");
		chooser.setFileFilter(filter);
		int returnVal = chooser.showOpenDialog(frame);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
		    String path = chooser.getSelectedFile().getAbsolutePath();
		    DrawingEngine.save(path);
		    refreshButton.doClick();
		}
		refreshButton.doClick();

	    }
	});
	btnNewButton_3.setFont(new Font("Tahoma", Font.PLAIN, 13));
	btnNewButton_3.setBounds(63, 5, 43, 34);
	frame.getContentPane().add(btnNewButton_3);

	JButton btnDelete = new JButton("");
	btnDelete.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/deleteIcon.png")));
	btnDelete.addKeyListener(new KeyAdapter() {
	    @Override
	    public void keyPressed(KeyEvent arg0) {
		if (arg0.getKeyCode() == KeyEvent.VK_DELETE) {
		    DrawingEngine.removeShape(DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex()));
		    canvas.repaint();
		    counterr.setText(
			    String.valueOf(DrawingEngine.getShapes().length));
		    prepareComboBox(comboBox, DrawingEngine);
		}
	    }
	});
	btnDelete.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		if (comboBox.getSelectedItem() == null) {
		    ;
		} else {
		    DrawingEngine.removeShape(DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex()));
		    canvas.repaint();
		    counterr.setText(
			    String.valueOf(DrawingEngine.getShapes().length));
		    prepareComboBox(comboBox, DrawingEngine);
		}
	    }
	});
	btnDelete.setForeground(Color.WHITE);
	btnDelete.setToolTipText("Delete Selected Shape");
	btnDelete.setBackground(SystemColor.controlHighlight);
	btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
	btnDelete.setBounds(360, 5, 49, 34);
	frame.getContentPane().add(btnDelete);

	JButton btnNewButton = new JButton("");
	btnNewButton.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/undoIcon.png")));
	btnNewButton.setToolTipText("Undo");
	btnNewButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		DrawingEngine.undo();
		Graphics g = canvas.getGraphics();
		counterr.setText(
			String.valueOf(DrawingEngine.getShapes().length));
		canvas.repaint();
		prepareComboBox(comboBox, DrawingEngine);
		canvas.repaint();
	    }

	});
	btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 13));
	btnNewButton.setBounds(116, 5, 43, 34);
	frame.getContentPane().add(btnNewButton);

	JButton btnNewButton_4 = new JButton("");
	btnNewButton_4.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/redoIcon.png")));
	btnNewButton_4.setToolTipText("Redo");
	btnNewButton_4.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		DrawingEngine.redo();
		Graphics g = canvas.getGraphics();
		counterr.setText(
			String.valueOf(DrawingEngine.getShapes().length));
		canvas.repaint();
		prepareComboBox(comboBox, DrawingEngine);
	    }
	});
	btnNewButton_4.setFont(new Font("Tahoma", Font.PLAIN, 13));
	btnNewButton_4.setBounds(169, 5, 43, 34);
	frame.getContentPane().add(btnNewButton_4);
	JButton editButton = new JButton("");
	editButton.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/editIcon.png")));
	editButton.setToolTipText("Edit Selected Shape");
	editButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		if (comboBox.getSelectedIndex() != -1) {
		    EditPane obj = new EditPane(DrawingEngine.shapes
			    .get(comboBox.getSelectedIndex()), canvas);
		    obj.main(null);
		}
		refreshButton.doClick();
	    }
	});
	editButton.setBounds(419, 5, 49, 34);
	frame.getContentPane().add(editButton);

	JButton btnNewButton_8 = new JButton("");
	btnNewButton_8.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/closeIcon.png")));

	btnNewButton_8.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		clearLabelsAndTextFields();
		currentShapeLabel.setText("None");
		picLabel.setIcon(null);
	    }
	});
	btnNewButton_8.setBounds(899, 625, 49, 41);
	frame.getContentPane().add(btnNewButton_8);
	btnNewButton_8.setToolTipText("Clear Selected Shape to draw");

	JButton btnImport = new JButton("");
	btnImport.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/importIcon.png")));
	btnImport.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		if (DrawingEngine.getSupportedShapes().size() == 6) {
		    JFileChooser chooser = new JFileChooser();
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
			    "class", "java");
		   chooser.setFileFilter(filter);
		    int returnVal = chooser.showOpenDialog(frame);
		    if (returnVal == JFileChooser.APPROVE_OPTION) {
			String path = chooser.getSelectedFile()
				.getAbsolutePath();
			System.out.println(path);
			path = "C:\\Users\\oooooooo\\Desktop\\Circle2.class";
			System.out.println(
				DrawingEngine.getSupportedShapes().size());
			DrawingEngine.reflect(path);
			System.out.println(
				DrawingEngine.getSupportedShapes().size());
			btnImport.doClick();
		    }
		} else if (DrawingEngine.getSupportedShapes().size() == 7) {
		    btnImport.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/hexagonalIcon.png")));
		    currentShapeLabel.setText(DrawingEngine.getSupportedShapes()
			    .get(6).getSimpleName());
		    prepareInputLabels(DrawingEngine.getSupportedShapes().get(6)
			    .getSimpleName());
		    picLabel.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/hexagonalIcon.png")));
		}
	    }
	});
	btnImport.setBounds(10, 382, 59, 42);
	btnImport.setToolTipText("Add Shape Plugin");
	frame.getContentPane().add(btnImport);

	JLabel lblCopyRightsTo = new JLabel(
		"\u00A9 All CopyRights reserved to \"Islam Gamal\".");
	lblCopyRightsTo.setFont(new Font("Tahoma", Font.PLAIN, 11));
	lblCopyRightsTo.setForeground(Color.DARK_GRAY);
	lblCopyRightsTo.setBounds(24, 656, 273, 23);
	frame.getContentPane().add(lblCopyRightsTo);

	JButton outlineButton = new JButton("");
	outlineButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		btnNewButton_1.doClick();
	    }
	});
	outlineButton.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/outlineIcon.png")));
	outlineButton.setBounds(784, 5, 49, 34);
	outlineButton.setToolTipText("Set Outline Color");
	frame.getContentPane().add(outlineButton);

	JButton button = new JButton("");
	button.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		btnNewButton_6.doClick();
	    }
	});
	button.setBounds(843, 5, 49, 34);
	button.setIcon(new ImageIcon(this.getClass().getResource("/Resources/images/fillIcon.png")));
	button.setToolTipText("Set Fill Color");
	frame.getContentPane().add(button);
	
	JLabel lblNewLabel = new JLabel("Hover on button to show its function");
	lblNewLabel.setFont(new Font("SimSun-ExtB", Font.PLAIN, 11));
	lblNewLabel.setForeground(Color.DARK_GRAY);
	lblNewLabel.setBounds(676, 39, 216, 14);
	frame.getContentPane().add(lblNewLabel);
	
	JButton brusher = new JButton("");
	brusher.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
		    currentShapeLabel.setText("brush");
		}
	});
	brusher.setBounds(598, 5, 49, 34);
	frame.getContentPane().add(brusher);

    }
}
