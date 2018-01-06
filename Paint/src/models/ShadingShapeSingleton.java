package models;

import model.interfaces.Shape;

public class ShadingShapeSingleton {
    	 public static Shape shadingShape= null;
    	//Created this singleton in order not to change the selected shape to be highlighted by autp repainting
    	public static Shape shadingShape() {
    	    if(DrawingEngineSingleton.drawingEngine().shapes.size() == 0) {
    		return null;
    	    }
    	    else {
	    return shadingShape;
    	    }
    	}
}
