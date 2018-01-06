package models;

public class DrawingEngineSingleton {
    static MyDrawingEngine drawingEngine = new MyDrawingEngine();
    
    public static MyDrawingEngine drawingEngine() {
	return drawingEngine;
    }
}
