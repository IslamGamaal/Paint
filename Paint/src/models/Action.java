package models;

import model.interfaces.Shape;

public class Action {
    private final String operation;
    private final Shape oldShape;
    private final Shape newShape;
    private final int indexOfDeletedShape;

    public String getOperation() {
	return operation;
    }

    public Shape getOldShape() {
	return oldShape;
    }

    public Shape getNewShape() {
	return newShape;
    }

    public int getIndex() {
	return this.indexOfDeletedShape;
    }

    public Action(String operation, Shape oldShape, Shape newShape,
	    int indexOfDeletedShape) {
	this.operation = operation;
	this.oldShape = oldShape;
	this.newShape = newShape;
	this.indexOfDeletedShape = indexOfDeletedShape;
    }

}
