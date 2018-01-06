package models.shapesImpl;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

import model.interfaces.Shape;

public class Elipse extends MyShape {
    public int counterE = 1;

    public Elipse() {
	final Map<String, Double> propo = new HashMap<String, Double>();
	propo.put("a", 0.0);
	propo.put("b", 0.0);
	setProperties(propo);
    }

    /* create a deep clone of the shape */
    @Override
    public Object clone() throws CloneNotSupportedException {
	final Shape ellipse = new Elipse();
	ellipse.setColor(getColor());
	ellipse.setFillColor(getFillColor());
	ellipse.setPosition(getPosition());
	final Map<String, Double> newprop = new HashMap<>();
	for (final Map.Entry<String, Double> s : getProperties().entrySet()) {
	    newprop.put(s.getKey().toString(), s.getValue());
	}
	ellipse.setProperties(newprop);
	return ellipse;

    }

    @Override
    public void draw(Graphics canvas) {
	// TODO Auto-generated method stub
	double a = prop.get("a");
	double b = prop.get("b");
	canvas.setColor(c);
	Graphics2D g = (Graphics2D) canvas;
	g.setStroke(new BasicStroke(2));
	Ellipse2D.Double shape = new Ellipse2D.Double(position.x - a / 2,
		position.y - b / 2, a, b);
	g.draw(shape);
	if (fillC != null) {
	    g.setColor(fillC);
	    g.fill(shape);
	}
    }

    @Override
    public String getName() {
	return "Ellipse ";

    }

    @Override
    public int counter() {
	return counterE;
    }
}
