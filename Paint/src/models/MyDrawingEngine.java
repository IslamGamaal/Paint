package models;

import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;

import model.interfaces.DrawingEngine;
import model.interfaces.Shape;
import models.shapesImpl.Circle;
import models.shapesImpl.Elipse;
import models.shapesImpl.Line;
import models.shapesImpl.MyShape;
import models.shapesImpl.Rectangle;
import models.shapesImpl.Square;
import models.shapesImpl.Triangle;

public class MyDrawingEngine implements DrawingEngine {
    public ArrayList<Shape> shapes = new ArrayList<Shape>();
    int undoCounter = 0;

    Deque<Action> undo = new LinkedList<Action>();
    Deque<Action> redo = new LinkedList<Action>();
    
    LinkedList<Class<? extends Shape>> supportedShapes = new LinkedList<Class<? extends Shape>>();;

    @Override
    public void refresh(Graphics canvas) {
	// TODO Auto-generated method stub
	canvas.setColor(Color.WHITE);
	canvas.fillRect(0, 0, 1010, 557);
	int len = shapes.size();
	for (int i = 0; i < len; i++) {
	    shapes.get(i).draw(canvas);
	}
    }

    @Override
    public void addShape(Shape shape) {
	// TODO Auto-generated method stub
	shapes.add(shape);
	Action action = new Action("Add", shape, null, shapes.size() - 1);
	if (undo.size() >= 20) {
	    undo.removeLast();
	}
	redo.clear();
	undo.push(action);
    }

    @Override
    public void removeShape(Shape shape) {
	// TODO Auto-generated method stub
	int index = shapes.indexOf(shape);
	shapes.remove(shape);
	Action action = new Action("Delete", shape, null, index);
	if (undo.size() >= 20) {
	    undo.removeLast();
	}
	redo.clear();
	undo.push(action);
    }

    @Override
    public void updateShape(Shape oldShape, Shape newShape) {
	// TODO Auto-generated method stub
	int place = 0;
	if (shapes.contains(oldShape)) {
	    place = shapes.indexOf(oldShape);
	    shapes.remove(oldShape);
	    shapes.add(place, newShape);
	    Action action = new Action("Update", oldShape, newShape, -10);
	    if (undo.size() >= 20) {
		undo.removeLast();
	    }
	    redo.clear();
	    ((MyShape)newShape).Counter = ((MyShape)oldShape).Counter;
	    undo.push(action);
	}

    }

    @Override
    public Shape[] getShapes() {
	// TODO Auto-generated method stub
	int length = shapes.size();
	Shape[] retArr = new Shape[length];
	retArr = shapes.toArray(retArr);
	return retArr;
    }

    @Override
    public List<Class<? extends Shape>> getSupportedShapes() {
	if(supportedShapes.isEmpty()) {
	supportedShapes.add(Circle.class);
	supportedShapes.add(Rectangle.class);
	supportedShapes.add(Elipse.class);
	supportedShapes.add(Triangle.class);
	supportedShapes.add(Square.class);
	supportedShapes.add(Line.class);
	}
	return supportedShapes;
    }

    @Override
    public void undo() {
	if (undo.isEmpty()) {
	    return;
	}

	final Action lastOperation = this.undo.pop();
	if (lastOperation.getOperation() == "Add") {
	    int index = lastOperation.getIndex();
	    this.shapes.remove(lastOperation.getOldShape());
	    redo.push(new Action("Delete", lastOperation.getOldShape(), null,
		    index));
	} else if (lastOperation.getOperation() == "Delete") {
	    this.shapes.add(lastOperation.getIndex(),
		    lastOperation.getOldShape());
	    redo.push(new Action("Add", lastOperation.getOldShape(), null,
		    lastOperation.getIndex()));
	} else if (lastOperation.getOperation() == "Update") {
	    int indexOfOldShape = this.shapes
		    .indexOf(lastOperation.getNewShape());
	    this.shapes.remove(lastOperation.getNewShape());
	    this.shapes.add(indexOfOldShape, lastOperation.getOldShape());
	    redo.push(new Action("Update", lastOperation.getNewShape(),
		    lastOperation.getOldShape(), -1));
	}

    }

    @Override
    public void redo() {
	if (redo.isEmpty()) {
	    return;
	}
	final Action lastOperation = this.redo.pop();
	if (lastOperation.getOperation() == "Add") {
	    final int index = lastOperation.getIndex();
	    this.shapes.remove(lastOperation.getOldShape());
	    undo.push(new Action("Delete", lastOperation.getOldShape(), null,
		    index));
	} else if (lastOperation.getOperation() == "Delete") {
	    this.shapes.add(lastOperation.getIndex(),
		    lastOperation.getOldShape());
	    undo.push(new Action("Add", lastOperation.getOldShape(), null,
		    lastOperation.getIndex()));
	} else if (lastOperation.getOperation() == "Update") {
	    final int indexOfOldShape = this.shapes
		    .indexOf(lastOperation.getNewShape());
	    this.shapes.remove(lastOperation.getNewShape());
	    this.shapes.add(indexOfOldShape, lastOperation.getOldShape());
	    undo.push(new Action("Update", lastOperation.getNewShape(),
		    lastOperation.getOldShape(), -1));
	}

    }

    @Override
    public void save(String path) {
	// TODO Auto-generated method stub
	SaveAndLoad save = new SaveAndLoad();
	save.save(path, this.shapes);
    }

    @Override
    public void load(String path) {
	// TODO Auto-generated method stub
	SaveAndLoad load = new SaveAndLoad();
	ArrayList loadedShapes = new ArrayList();
	loadedShapes = load.load(path);
	if (loadedShapes == null) {
	    ;
	} else {
	    this.shapes = loadedShapes;
	}
	redo.clear();
	undo.clear();
    }
    
    public void reflect(String path) {
	final String cN = path.substring(path.lastIndexOf('\\') + 1, path.length());
	path = path.substring(0, path.lastIndexOf('\\'));
	File operatorFile = new File(path);

    ClassLoader operatorsLoader = null;
	try {
		operatorsLoader = new URLClassLoader(new URL[] { operatorFile.toURI().toURL() });
	} catch (MalformedURLException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}

	File[] files = operatorFile.listFiles(new FilenameFilter() {
        @Override public boolean accept(File dir, String name) {
        	//System.out.println(cN);
            return name.endsWith(".class") && name.equals(cN);
        }
    });
	//System.out.println(files.length);
	if (files.length == 0) {
		throw new RuntimeException("File is not found in the destination or not supported format.");
	}
    ArrayList<Class> operators = new ArrayList<>();
    for (File file : files) {
        String className = file.getName().substring(0, file.getName().length() - 6);
        System.out.println(className);
        try {
			Class<? extends Shape> newClass = (Class<? extends Shape>) operatorsLoader.loadClass(className);
			this.supportedShapes.add(newClass);
	        System.out.println(newClass.getName());

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }

}

public void reflectJarFile(String path) {
	ArrayList<Class> classes = new ArrayList();
	String jarName = new String(path);
	try {
		JarInputStream jarFile = new JarInputStream(new FileInputStream(jarName));
		File myJar = new File(jarName);
		URL url = myJar.toURI().toURL();
	
		Class[] parameters = new Class[]{URL.class};
		URLClassLoader sysLoader =(URLClassLoader)ClassLoader.getSystemClassLoader();
		Class sysClass = URLClassLoader.class;
		Method method = sysClass.getDeclaredMethod("addURL", parameters);
		method.setAccessible(true);
		method.invoke(sysLoader, new Object[]{url});
		
		JarEntry jarEntry;
		while (true) {
			jarEntry = jarFile.getNextJarEntry();
			if (jarEntry == null) {
				break;
			}
			if (jarEntry.getName().endsWith(".class")) {
				System.out.println(jarEntry.getName().replaceAll("/", "\\."));
				String name = jarEntry.getName().replaceAll("/", "\\.").replace(".class", "");
				Constructor cs = ClassLoader.getSystemClassLoader().loadClass(name).getConstructor();
				Object instance = cs.newInstance();
				Method test = ClassLoader.getSystemClassLoader().loadClass(name).getMethod("test");
				this.supportedShapes.add((Class<? extends Shape>) ClassLoader.getSystemClassLoader()
						.loadClass(name));
				test.invoke(instance);
			}
	}
	
	} catch (Exception e) {
		System.out.println(e.getMessage());
	}


}

}
