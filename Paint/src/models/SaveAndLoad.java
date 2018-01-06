package models;

import java.awt.Color;
import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import eg.edu.alexu.csd.oop.draw.cs11.json.JSONArray;
import eg.edu.alexu.csd.oop.draw.cs11.json.JSONObject;
import eg.edu.alexu.csd.oop.draw.cs11.json.parser.JSONParser;
import eg.edu.alexu.csd.oop.draw.cs11.json.parser.ParseException;
import model.interfaces.Shape;
import models.shapesImpl.MyShape;

public class SaveAndLoad {

    public void save(String path, ArrayList<Shape> myshapes) {
	ArrayList<Shape> Shapes = new ArrayList<Shape>();
	Shapes = myshapes;
	JSONObject allShapes = new JSONObject();
	JSONArray shapesArray = new JSONArray();
	for (int i = 0; i < Shapes.size(); i++) {
	    JSONObject singleShape = new JSONObject();
	    String name = null;
	    try {
		name = ((MyShape) Shapes.get(i)).getClass().getSimpleName();
	    } catch (Exception e) {
		// TODO
	    }
	    singleShape.put("Type", name);

	    JSONArray properties = new JSONArray();
	    try {
		if (name != null) {
		    properties.add(
			    String.valueOf(((MyShape)Shapes.get(i)).Counter));
		    properties.add(
			    String.valueOf(Shapes.get(i).getPosition().getX()));
		    properties.add(
			    String.valueOf(Shapes.get(i).getPosition().getY()));

		    properties.add(
			    String.valueOf(Shapes.get(i).getColor().getRed()));
		    properties.add(String
			    .valueOf(Shapes.get(i).getColor().getGreen()));
		    properties.add(
			    String.valueOf(Shapes.get(i).getColor().getBlue()));

		    if (Shapes.get(i).getFillColor() == null) {
			properties.add(null);
			properties.add(null);
			properties.add(null);
		    } else {
			properties.add(String.valueOf(
				Shapes.get(i).getFillColor().getRed()));
			properties.add(String.valueOf(
				Shapes.get(i).getFillColor().getGreen()));
			properties.add(String.valueOf(
				Shapes.get(i).getFillColor().getBlue()));
		    }
		    for (final Map.Entry<String, Double> s : Shapes.get(i)
			    .getProperties().entrySet()) {
			properties.add(s.getValue().toString());
		    }

		}

		singleShape.put("Properties", properties);
		shapesArray.add(singleShape);
	    } catch (Exception e) {
		// TODO
	    }
	}
	try {
	    allShapes.put("ShapesArray", shapesArray);
	} catch (Exception e) {
	    // TODO
	}

	// try-with-resources statement based on post comment below
	try (FileWriter file = new FileWriter(path)) {
	    file.write(allShapes.toJSONString());
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}

    }

    public ArrayList load(String path) {
	JSONParser parser = new JSONParser();
	try {
	    Object obj = parser.parse(new FileReader(path));
	    JSONObject Shapes = (JSONObject) obj;
	    JSONArray ShapesArray = new JSONArray();
	    MyDrawingEngine MyDE = new MyDrawingEngine();
	    ShapesArray = (JSONArray) Shapes.get("ShapesArray");
	    for (int i = 0; i < ShapesArray.size(); i++) {
		try {
		    JSONObject oneShape = (JSONObject) obj;
		    oneShape = (JSONObject) ShapesArray.get(i);
		    if (oneShape.get("Type") == null) {
			MyDE.addShape(null);
		    } else {
			for (int k = 0; k < MyDE.getSupportedShapes()
				.size(); k++) {
			    if (oneShape.get("Type").equals(MyDE.getSupportedShapes().get(k).getSimpleName())) {
				JSONArray properties = new JSONArray();
				    properties = (JSONArray) oneShape.get("Properties");
				    Constructor constructor = null;
					try {
					    constructor = DrawingEngineSingleton.drawingEngine().getSupportedShapes()
						    .get(k).getConstructor();
					} catch (NoSuchMethodException e1) {
					    // TODO Auto-generated catch block
					    e1.printStackTrace();
					} catch (SecurityException e1) {
					    // TODO Auto-generated catch block
					    e1.printStackTrace();
					}
					MyShape shapeObject = null;
					try {
					    shapeObject = (MyShape) constructor.newInstance();
					} catch (InstantiationException | IllegalAccessException
						| IllegalArgumentException
						| InvocationTargetException e1) {
					    // TODO Auto-generated catch block
					    e1.printStackTrace();
					}
				shapeObject.Counter = Integer
						    .valueOf((String) properties.get(0));
				    final Double x = Double
					    .valueOf((String) properties.get(1));
				    final Double y = Double
					    .valueOf((String) properties.get(2));
				    Point pos = new Point(x.intValue(), y.intValue());
				    shapeObject.setPosition(pos);

				    Double Red = Double
					    .valueOf((String) properties.get(3));
				    Double Green = Double
					    .valueOf((String) properties.get(4));
				    Double Blue = Double
					    .valueOf((String) properties.get(5));
				    Color color = new Color(Red.intValue(),
					    Green.intValue(), Blue.intValue());
				    shapeObject.setColor(color);
				    if (properties.get(6) == null) {
					Color fillC = null;
					shapeObject.setFillColor(fillC);
				    } else {
					Red = Double
						.valueOf((String) properties.get(6));
					Green = Double
						.valueOf((String) properties.get(7));
					Blue = Double
						.valueOf((String) properties.get(8));
					Color fillC = new Color(Red.intValue(),
						Green.intValue(), Blue.intValue());
					shapeObject.setFillColor(fillC);
				    }
				    if (properties.size() == 9) {
					final Map<String, Double> oneShapeProp = new HashMap<>();
					shapeObject.setProperties(oneShapeProp);
				    } else {
					int m = 0;
					Map<String, Double> oneShapeProp = new HashMap<>();
					oneShapeProp = shapeObject.getProperties();
					for (Map.Entry<String, Double> entry : oneShapeProp
						.entrySet()) {
					    oneShapeProp.put(entry.getKey(), Double.parseDouble(
							(String) properties.get(9 + m)));
					    m++;
					}
					shapeObject.setProperties(oneShapeProp);
				    }
				    MyDE.addShape(shapeObject);
			    }
			}
			/*if (oneShape.get("Type").equals("Circle ")) {
			    JSONArray properties = new JSONArray();
			    properties = (JSONArray) oneShape.get("Properties");
			    Constructor constructor = null;
				try {
				    constructor = DrawingEngineSinglton.drawingEngine().getSupportedShapes()
					    .get(i).getConstructor();
				} catch (NoSuchMethodException e1) {
				    // TODO Auto-generated catch block
				    e1.printStackTrace();
				} catch (SecurityException e1) {
				    // TODO Auto-generated catch block
				    e1.printStackTrace();
				}
				MyShape shapeObject = null;
				try {
				    shapeObject = (MyShape) constructor.newInstance();
				} catch (InstantiationException | IllegalAccessException
					| IllegalArgumentException
					| InvocationTargetException e1) {
				    // TODO Auto-generated catch block
				    e1.printStackTrace();
				}

			    final Double x = Double
				    .valueOf((String) properties.get(1));
			    final Double y = Double
				    .valueOf((String) properties.get(2));
			    Point pos = new Point(x.intValue(), y.intValue());
			    shapeObject.setPosition(pos);

			    Double Red = Double
				    .valueOf((String) properties.get(3));
			    Double Green = Double
				    .valueOf((String) properties.get(4));
			    Double Blue = Double
				    .valueOf((String) properties.get(5));
			    Color color = new Color(Red.intValue(),
				    Green.intValue(), Blue.intValue());
			    shapeObject.setColor(color);
			    shapeObject.Counter = Integer
				    .valueOf((String) properties.get(0));
			    if (properties.get(6) == null) {
				Color fillC = null;
				shapeObject.setFillColor(fillC);
			    } else {
				Red = Double
					.valueOf((String) properties.get(6));
				Green = Double
					.valueOf((String) properties.get(7));
				Blue = Double
					.valueOf((String) properties.get(8));
				Color fillC = new Color(Red.intValue(),
					Green.intValue(), Blue.intValue());
				shapeObject.setFillColor(fillC);
			    }
			    if (properties.size() == 9) {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				shapeObject.setProperties(oneShapeProp);
			    } else {
				int m = 0;
				Map<String, Double> oneShapeProp = new HashMap<>();
				oneShapeProp = shapeObject.getProperties();
				for (Map.Entry<String, Double> entry : oneShapeProp
					.entrySet()) {
				    oneShapeProp.put(entry.getKey(), Double.parseDouble(
						(String) properties.get(9 + m)));
				    shapeObject.setProperties(oneShapeProp);
				    m++;
				}
			    }
			    MyDE.addShape(shapeObject);
			}
			if (oneShape.get("Type").equals("Rectangle ")) {
			    JSONArray properties = new JSONArray();
			    properties = (JSONArray) oneShape.get("Properties");
			    Rectangle rec = new Rectangle();

			    final Double x = Double
				    .valueOf((String) properties.get(1));
			    final Double y = Double
				    .valueOf((String) properties.get(2));
			    Point pos = new Point(x.intValue(), y.intValue());
			    rec.setPosition(pos);
			    rec.Counter = Integer
				    .valueOf((String) properties.get(0));
			    Double Red = Double
				    .valueOf((String) properties.get(3));
			    Double Green = Double
				    .valueOf((String) properties.get(4));
			    Double Blue = Double
				    .valueOf((String) properties.get(5));
			    Color color = new Color(Red.intValue(),
				    Green.intValue(), Blue.intValue());
			    rec.setColor(color);

			    if (properties.get(6) == null) {
				Color fillC = null;
				rec.setFillColor(fillC);
			    } else {
				Red = Double
					.valueOf((String) properties.get(6));
				Green = Double
					.valueOf((String) properties.get(7));
				Blue = Double
					.valueOf((String) properties.get(8));
				Color fillC = new Color(Red.intValue(),
					Green.intValue(), Blue.intValue());
				rec.setFillColor(fillC);
			    }

			    if (properties.size() == 9) {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				rec.setProperties(oneShapeProp);
			    } else {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				Double l = Double.parseDouble(
					(String) properties.get(9));
				Double w = Double.parseDouble(
					(String) properties.get(10));
				oneShapeProp.put("length", l);
				oneShapeProp.put("width", w);
				rec.setProperties(oneShapeProp);
			    }
			    MyDE.addShape(rec);

			}
			if (oneShape.get("Type").equals("Square ")) {
			    JSONArray properties = new JSONArray();
			    properties = (JSONArray) oneShape.get("Properties");
			    Square sq = new Square();

			    final Double x = Double
				    .valueOf((String) properties.get(1));
			    final Double y = Double
				    .valueOf((String) properties.get(2));
			    Point pos = new Point(x.intValue(), y.intValue());
			    sq.setPosition(pos);
			    sq.Counter = Integer
				    .valueOf((String) properties.get(0));
			    Double Red = Double
				    .valueOf((String) properties.get(3));
			    Double Green = Double
				    .valueOf((String) properties.get(4));
			    Double Blue = Double
				    .valueOf((String) properties.get(5));
			    Color color = new Color(Red.intValue(),
				    Green.intValue(), Blue.intValue());
			    sq.setColor(color);

			    if (properties.get(6) == null) {
				Color fillC = null;
				sq.setFillColor(fillC);
			    } else {
				Red = Double
					.valueOf((String) properties.get(6));
				Green = Double
					.valueOf((String) properties.get(7));
				Blue = Double
					.valueOf((String) properties.get(8));
				Color fillC = new Color(Red.intValue(),
					Green.intValue(), Blue.intValue());
				sq.setFillColor(fillC);
			    }
			    if (properties.size() == 9) {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				sq.setProperties(oneShapeProp);
			    } else {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				Double l = Double.parseDouble(
					(String) properties.get(9));
				oneShapeProp.put("length", l);
				sq.setProperties(oneShapeProp);
			    }
			    MyDE.addShape(sq);

			}
			if (oneShape.get("Type").equals("Ellipse ")) {
			    JSONArray properties = new JSONArray();
			    properties = (JSONArray) oneShape.get("Properties");
			    Elipse elps = new Elipse();

			    final Double x = Double
				    .valueOf((String) properties.get(1));
			    final Double y = Double
				    .valueOf((String) properties.get(2));
			    Point pos = new Point(x.intValue(), y.intValue());
			    elps.setPosition(pos);

			    Double Red = Double
				    .valueOf((String) properties.get(3));
			    Double Green = Double
				    .valueOf((String) properties.get(4));
			    Double Blue = Double
				    .valueOf((String) properties.get(5));
			    Color color = new Color(Red.intValue(),
				    Green.intValue(), Blue.intValue());
			    elps.setColor(color);
			    elps.Counter = Integer
				    .valueOf((String) properties.get(0));
			    if (properties.get(6) == null) {
				Color fillC = null;
				elps.setFillColor(fillC);
			    } else {
				Red = Double
					.valueOf((String) properties.get(6));
				Green = Double
					.valueOf((String) properties.get(7));
				Blue = Double
					.valueOf((String) properties.get(8));
				Color fillC = new Color(Red.intValue(),
					Green.intValue(), Blue.intValue());
				elps.setFillColor(fillC);
			    }
			    if (properties.size() == 9) {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				elps.setProperties(oneShapeProp);
			    } else {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				Double a = Double.parseDouble(
					(String) properties.get(9));
				Double b = Double.parseDouble(
					(String) properties.get(10));
				oneShapeProp.put("a", a);
				oneShapeProp.put("b", b);
				elps.setProperties(oneShapeProp);
			    }
			    MyDE.addShape(elps);

			}
			if (oneShape.get("Type").equals("Line ")) {
			    JSONArray properties = new JSONArray();
			    properties = (JSONArray) oneShape.get("Properties");
			    Line l = new Line();
			    l.Counter = Integer
				    .valueOf((String) properties.get(0));
			    final Double x = Double
				    .valueOf((String) properties.get(1));
			    final Double y = Double
				    .valueOf((String) properties.get(2));
			    Point pos = new Point(x.intValue(), y.intValue());
			    l.setPosition(pos);

			    Double Red = Double
				    .valueOf((String) properties.get(3));
			    Double Green = Double
				    .valueOf((String) properties.get(4));
			    Double Blue = Double
				    .valueOf((String) properties.get(5));
			    Color color = new Color(Red.intValue(),
				    Green.intValue(), Blue.intValue());
			    l.setColor(color);

			    if (properties.get(6) == null) {
				Color fillC = null;
				l.setFillColor(fillC);
			    } else {
				Red = Double
					.valueOf((String) properties.get(6));
				Green = Double
					.valueOf((String) properties.get(7));
				Blue = Double
					.valueOf((String) properties.get(8));
				Color fillC = new Color(Red.intValue(),
					Green.intValue(), Blue.intValue());
				l.setFillColor(fillC);
			    }
			    if (properties.size() == 9) {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				l.setProperties(oneShapeProp);
			    } else {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				Double x2 = Double.parseDouble(
					(String) properties.get(9));
				Double y2 = Double.parseDouble(
					(String) properties.get(10));

				oneShapeProp.put("y2", x2);
				oneShapeProp.put("x2", y2);
				l.setProperties(oneShapeProp);
			    }
			    MyDE.addShape(l);
			}
			if (oneShape.get("Type").equals("Triangle ")) {
			    JSONArray properties = new JSONArray();
			    properties = (JSONArray) oneShape.get("Properties");
			    Triangle t = new Triangle();
			    t.Counter = Integer
				    .valueOf((String) properties.get(0));
			    final Double x = Double
				    .valueOf((String) properties.get(1));
			    final Double y = Double
				    .valueOf((String) properties.get(2));
			    Point pos = new Point(x.intValue(), y.intValue());
			    t.setPosition(pos);

			    Double Red = Double
				    .valueOf((String) properties.get(3));
			    Double Green = Double
				    .valueOf((String) properties.get(4));
			    Double Blue = Double
				    .valueOf((String) properties.get(5));
			    Color color = new Color(Red.intValue(),
				    Green.intValue(), Blue.intValue());
			    t.setColor(color);

			    if (properties.get(6) == null) {
				Color fillC = null;
				t.setFillColor(fillC);
			    } else {
				Red = Double
					.valueOf((String) properties.get(6));
				Green = Double
					.valueOf((String) properties.get(7));
				Blue = Double
					.valueOf((String) properties.get(8));
				Color fillC = new Color(Red.intValue(),
					Green.intValue(), Blue.intValue());
				t.setFillColor(fillC);
			    }
			    if (properties.size() == 9) {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				t.setProperties(oneShapeProp);
			    } else {
				final Map<String, Double> oneShapeProp = new HashMap<>();
				Double x2 = Double.parseDouble(
					(String) properties.get(9));
				Double y2 = Double.parseDouble(
					(String) properties.get(10));
				Double x3 = Double.parseDouble(
					(String) properties.get(11));
				Double y3 = Double.parseDouble(
					(String) properties.get(12));

				oneShapeProp.put("y3", x2);
				oneShapeProp.put("x3", y2);
				oneShapeProp.put("y2", x3);
				oneShapeProp.put("x2", y3);
				t.setProperties(oneShapeProp);
			    }
			    MyDE.addShape(t);
			}*/
		    }
		} catch (Exception e) {
		    // TODO
		}
	    }
	    return MyDE.shapes;
	} catch (FileNotFoundException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (ParseException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
	return null;
    }
}
