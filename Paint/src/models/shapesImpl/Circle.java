package models.shapesImpl;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.HashMap;
import java.util.Map;

import model.interfaces.Shape;

public class Circle extends MyShape {

    public int counterC = 1;

    public Circle() {
	final Map<String, Double> propo = new HashMap<String, Double>();
	propo.put("radius", 0.0);
	setProperties(propo);
    }

    /* create a deep clone of the shape */
    @Override
    public Object clone() throws CloneNotSupportedException {
	final Shape circle = new Circle();
	circle.setColor(getColor());
	circle.setFillColor(getFillColor());
	circle.setPosition(getPosition());
	final Map<String, Double> newprop = new HashMap<>();
	for (final Map.Entry<String, Double> s : getProperties().entrySet()) {
	    newprop.put(s.getKey().toString(), s.getValue());
	}
	circle.setProperties(newprop);
	return circle;
    }

    @Override
    public void draw(Graphics canvas) {
	// TODO Auto-generated method stub
	double r;
	r = prop.get("radius");
	canvas
	.setColor(c);
	Graphics2D g = (Graphics2D) canvas;
	g.setStroke(new BasicStroke(2));
	Ellipse2D.Double shape = new Ellipse2D.Double(position.x - r / 2,
		position.y - r / 2, r, r);
	g.draw(shape);
	if (fillC != null) {
	    g.setColor(fillC);
	    g.fill(shape);
	}
    }

    @Override
    public String getName() {
	return "Circle ";
    }

    @Override
    public int counter() {
	return counterC;
    }
}
