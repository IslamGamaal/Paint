package view;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import model.interfaces.Shape;
import models.DrawingEngineSingleton;
import view.Paint.Painter;


public class EditPane extends Canvas{

    private JFrame frame;
    private JTextField positionFieldX;
    private JTextField textField_3;
    Shape chosenShape;
    Painter canvas;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
	EventQueue.invokeLater(new Runnable() {
	    @Override
	    public void run() {
		try {
		} catch (Exception e) {
		    e.printStackTrace();
		}
	    }
	});
    }

    /**
     * Create the application.
     */
    Shape newChosenShape;
    private JTextField positionFieldY;
    public EditPane(Shape chosenShape, Painter canvas) {
	this.chosenShape = chosenShape;
	this.canvas = canvas;
	try {
	    newChosenShape = (Shape) this.chosenShape.clone();
	} catch (CloneNotSupportedException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	initialize();
    }
    private void prepareSmallCanvas(Canvas smallCanvas) {
	for(int i = 0; i < smallCanvasShapes.size(); i++) {
	    Map<String, Double> prop = new HashMap<String, Double>();
	    for(Map.Entry<String, Double> entry : ((Shape) smallCanvasShapes.get(i)).getProperties().entrySet()) {
		if(entry.getValue() > 160) {
		    prop.put(entry.getKey(), entry.getValue()/2);
		}
		else {
		    prop.put(entry.getKey(), entry.getValue());
		}
	    }
	    ((Shape)smallCanvasShapes.get(i)).setProperties(prop);
	    ((Shape)smallCanvasShapes.get(i)).setPosition(new Point(85, 85));
	    ((Shape) smallCanvasShapes.get(i)).draw(smallCanvas.getGraphics());
	}
    }

    /**
     * Initialize the contents of the frame.
     */
    ArrayList smallCanvasShapes = new ArrayList();
    private void initialize() {
	frame = new JFrame();
	frame.getContentPane().setBackground(new Color(189, 183, 107));
	frame.getContentPane().setFont(new Font("Tahoma", Font.PLAIN, 25));
	frame.setBounds(100, 100, 450, 628);
	frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	frame.getContentPane().setLayout(null);
	frame.setVisible(true);
	
	Canvas canv = new Canvas();
	canv.setBackground(Color.WHITE);
	canv.setBounds(10, 12, 414, 169);
	frame.getContentPane().add(canv);
	canv.setVisible(false);
	
	try {
	    smallCanvasShapes.add(newChosenShape.clone());
	    prepareSmallCanvas(canv);
	} catch (CloneNotSupportedException e1) {
	    // TODO Auto-generated catch block
	    e1.printStackTrace();
	}

	positionFieldY = new JTextField();
	positionFieldY.setText("0.0, 0.0");
	positionFieldY.setColumns(10);
	positionFieldY.setBounds(317, 309, 86, 38);
	frame.getContentPane().add(positionFieldY);
	positionFieldY.setText(String.valueOf(chosenShape.getPosition().getY()));
	
	JButton outLineColor = new JButton("");
	outLineColor.setBounds(353, 215, 47, 38);
	frame.getContentPane().add(outLineColor);
	outLineColor.setBackground(chosenShape.getColor());
	
	JButton fillColor = new JButton("");
	fillColor.setBounds(353, 264, 47, 38);
	frame.getContentPane().add(fillColor);
	fillColor.setBackground(chosenShape.getFillColor());
	
	positionFieldX = new JTextField();
	positionFieldX.setBounds(221, 309, 86, 38);
	frame.getContentPane().add(positionFieldX);
	positionFieldX.setColumns(10);
	positionFieldX.setText(String.valueOf(chosenShape.getPosition().getX()));

	JLabel lblNewLabel = new JLabel("Outline Color");
	lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblNewLabel.setBounds(32, 215, 166, 38);
	frame.getContentPane().add(lblNewLabel);

	JLabel lblColor = new JLabel("   Fill Color");
	lblColor.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblColor.setBounds(32, 264, 166, 38);
	frame.getContentPane().add(lblColor);

	JLabel lblPosXy = new JLabel("   POS: X,Y");
	lblPosXy.setFont(new Font("Tahoma", Font.PLAIN, 25));
	lblPosXy.setBounds(32, 309, 166, 38);
	frame.getContentPane().add(lblPosXy);

	JButton outlineColorButton = new JButton("Change Color");
	outlineColorButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent arg0) {
		JColorChooser jcc = new JColorChooser();
		Color color = jcc.showDialog(null,
			"Please Choose the Outline Color",
			chosenShape.getColor());
		newChosenShape.setColor(color);
		outLineColor.setBackground(newChosenShape.getColor());
		    prepareSmallCanvas(canv);

	    }
	});
	outlineColorButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
	outlineColorButton.setBounds(221, 215, 122, 38);
	frame.getContentPane().add(outlineColorButton);

	JButton fillColorButton = new JButton("Change Color");
	fillColorButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		JColorChooser jcc = new JColorChooser();
		Color color = jcc.showDialog(null,
			"Please Choose the Outline Color",
			chosenShape.getFillColor());
		newChosenShape.setFillColor(color);
		fillColor.setBackground(newChosenShape.getFillColor());
		    prepareSmallCanvas(canv);
	    }
	});
	fillColorButton.setFont(new Font("Tahoma", Font.PLAIN, 15));
	fillColorButton.setBounds(221, 264, 122, 38);
	frame.getContentPane().add(fillColorButton);
	Map<String, Double> properties = new HashMap<String, Double>();
	properties = chosenShape.getProperties();
	ArrayList<JLabel> labels = new ArrayList<JLabel>();
	ArrayList<JTextField> textFields = new ArrayList<JTextField>();
	int counter = 0;
	for (Map.Entry<String, Double> map : properties.entrySet()) {
	    JLabel label = new JLabel(map.getKey());
	    label.setFont(new Font("Tahoma", Font.PLAIN, 25));
	    label.setBounds(57, 358 + (38 + 10) * counter, 180, 38);
	    frame.getContentPane().add(label);
	    labels.add(label);

	    JTextField textField = new JTextField(
		    String.valueOf(map.getValue()));
	    textField.setColumns(10);
	    textField.setBounds(221, 358 + (38 + 10) * counter, 180, 38);
	    frame.getContentPane().add(textField);
	    textFields.add(textField);
	    counter++;
	}
	frame.repaint();

	JButton btnNewButton = new JButton("DONE");
	btnNewButton.addActionListener(new ActionListener() {
	    @Override
	    public void actionPerformed(ActionEvent e) {
		Map<String, Double> prop = new HashMap<String, Double>();
		for (int i = 0; i < labels.size(); i++) {
		    prop.put(labels.get(i).getText(),
			    Double.valueOf(textFields.get(i).getText()));
		}
		newChosenShape.setProperties(prop);
		newChosenShape.setPosition(new Point(Double.valueOf(positionFieldX.getText()).intValue(), Double.valueOf(positionFieldY.getText()).intValue()));
		DrawingEngineSingleton.drawingEngine()
			.updateShape(chosenShape, newChosenShape);
		canvas.repaint();
		frame.dispatchEvent(
			new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
		canvas.repaint();
	    }
	});
	btnNewButton.setBackground(Color.GREEN);
	btnNewButton.setForeground(Color.WHITE);
	btnNewButton.setBounds(335, 548, 89, 31);
	frame.getContentPane().add(btnNewButton);
	    prepareSmallCanvas(canv);
	    
	    JButton refreshButton = new JButton("preview model of shape");
	    refreshButton.addActionListener(new ActionListener() {
	    	@Override
		public void actionPerformed(ActionEvent arg0) {
	    	    canv.setVisible(true);
		    prepareSmallCanvas(canv);
		    
	    	}
	    });
	    refreshButton.setBounds(10, 187, 414, 23);
	    frame.getContentPane().add(refreshButton);
	    
	    JLabel lblNewLabel_1 = new JLabel("");
	    lblNewLabel_1.setIcon(new ImageIcon("F:\\Downloads\\Webp.net-resizeimage (6).png"));
	    lblNewLabel_1.setBounds(0, 0, 434, 590);
	    frame.getContentPane().add(lblNewLabel_1);
	    
	    refreshButton.doClick();
	
    }
}
