package models.shapesImpl;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import model.interfaces.Shape;

public class Triangle extends MyShape {

    public int counterT = 1;

    public Triangle() {
	final Map<String, Double> prop = new HashMap<String, Double>();
	prop.put("secondX", 0.0);
	prop.put("secondY", 0.0);
	prop.put("thirdX", 0.0);
	prop.put("thirdY", 0.0);
	setProperties(prop);
    }

    @Override
    public void draw(Graphics canvas) {
	int x2 = getProperties().get("secondX").intValue();
	int y2 = getProperties().get("secondY").intValue();
	int x3 = getProperties().get("thirdX").intValue();
	int y3 = getProperties().get("thirdY").intValue();
	Graphics2D g = (Graphics2D) canvas;
	g.setStroke(new BasicStroke(2));
	int[] xPoints = {(int) super.getPosition().getX(), x2, x3 };
	int[] yPoints = {(int) super.getPosition().getY(), y2, y3 };
	g.setColor(getColor());
	g.drawPolygon(xPoints, yPoints, 3);
	if (getFillColor() == null) {
	    g.setColor(new Color(0, 0, 0, 1));
	    g.fillPolygon(xPoints, yPoints, 3);
	} else {
	    g.setColor(getFillColor());
	    g.fillPolygon(xPoints, yPoints, 3);
	}
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
	final Shape triangle = new Triangle();
	triangle.setColor(getColor());
	triangle.setFillColor(getFillColor());
	triangle.setPosition(getPosition());
	final Map<String, Double> newprop = new HashMap<>();
	for (final Map.Entry<String, Double> s : getProperties().entrySet()) {
	    newprop.put(s.getKey().toString(), s.getValue());
	}
	triangle.setProperties(newprop);
	return triangle;
    }

    @Override
    public String getName() {
	return "Triangle ";
    }

    @Override
    public int counter() {
	return counterT;
    }
}
