package models.shapesImpl;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import model.interfaces.Shape;

public class Line extends MyShape {

    public int counterL = 1;
    public int Stroke = 2;

    public Line() {
	final Map<String, Double> prop = new HashMap<>();
	prop.put("x2", 0.0);
	prop.put("y2", 0.0);
	setProperties(prop);
    }

    @Override
    public void draw(Graphics canvas) {
	int x2 = getProperties().get("x2").intValue();
	int y2 = getProperties().get("y2").intValue();
	Graphics2D g = (Graphics2D) canvas;
	g.setColor(getFillColor());
	g.setStroke(new BasicStroke(Stroke));
	g.setColor(getColor());
	g.drawLine((int) getPosition().getX(), (int) getPosition().getY(), x2,
		y2);
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
	final Shape line = new Line();
	line.setColor(getColor());
	line.setFillColor(getFillColor());
	line.setPosition(getPosition());
	final Map<String, Double> newprop = new HashMap<>();
	for (final Map.Entry<String, Double> s : getProperties().entrySet()) {
	    newprop.put(s.getKey(), s.getValue());
	}
	line.setProperties(newprop);
	return line;
    }

    @Override
    public String getName() {
	return "Line ";
    }

    @Override
    public int counter() {
	return counterL;
    }
    public void setStroke(int value) {
	Stroke = value;
    }
}
