package models.shapesImpl;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.util.HashMap;
import java.util.Map;

import model.interfaces.Shape;

public class Rectangle extends MyShape {
    public int counterR = 1;

    public Rectangle() {
	final Map<String, Double> propo = new HashMap<String, Double>();
	propo.put("length", 0.0);
	propo.put("width", 0.0);
	setProperties(propo);
    }

    /* create a deep clone of the shape */
    @Override
    public Object clone() throws CloneNotSupportedException {
	final Shape rect = new Rectangle();
	rect.setColor(getColor());
	rect.setFillColor(getFillColor());
	rect.setPosition(getPosition());
	final Map<String, Double> newprop = new HashMap<>();
	for (final Map.Entry<String, Double> s : getProperties().entrySet()) {
	    newprop.put(s.getKey().toString(), s.getValue());
	}
	rect.setProperties(newprop);
	return rect;
    }

    @Override
    public void draw(Graphics canvas) {
	// TODO Auto-generated method stub
	double l = prop.get("length");
	double w = prop.get("width");
	canvas.setColor(c);
	Graphics2D g = (Graphics2D) canvas;
	g.setStroke(new BasicStroke(2));
	Rectangle2D.Double shape = new Rectangle2D.Double(position.x,
		position.y, l, w);
	g.draw(shape);
	if (fillC != null) {
	    g.setColor(fillC);
	    g.fill(shape);
	}
    }

    @Override
    public String getName() {
	return "Rectangle ";

    }

    @Override
    public int counter() {
	return counterR;
    }
}
