package models.shapesImpl;

import java.awt.Color;
import java.awt.Point;
import java.util.Map;

import model.interfaces.Shape;

public abstract class MyShape implements Shape {
    public int Counter = 1;
    protected Point position = new Point(0, 0);
    protected Color c = new Color(0);
    protected Color fillC = null;
    protected Map<String, java.lang.Double> prop;

    @Override
    public void setPosition(java.awt.Point position) {
	this.position = position;
    }

    @Override
    public java.awt.Point getPosition() {
	return position;

    }

    /* update shape specific properties (e.g., radius) */
    @Override
    public void setProperties(java.util.Map<String, Double> properties) {
	this.prop = properties;

    }

    @Override
    public java.awt.Color getFillColor() {
	return fillC;

    }

    @Override
    public void setColor(java.awt.Color color) {
	this.c = color;
    }

    @Override
    public java.awt.Color getColor() {
	return c;

    }

    @Override
    public java.util.Map<String, Double> getProperties() {
	return prop;

    }

    @Override
    public void setFillColor(java.awt.Color color) {
	this.fillC = color;

    }

    /* redraw the shape on the canvas */
    @Override
    public void draw(java.awt.Graphics canvas) {

    }

    /* create a deep clone of the shape */
    @Override
    public Object clone() throws CloneNotSupportedException {
	return null;

    }

    public String getName() {
	return null;
    }

    public int counter() {
	return 0;
    }

}
