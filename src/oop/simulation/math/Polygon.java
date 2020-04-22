package oop.simulation.math;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Simple polygon class. Does nothing much other than store some vertices.
 *
 * @author Kevin Dai
 */
public class Polygon
{
    private ArrayList<Vec2> vertices = new ArrayList<>();

    /**
     * Constructor for polygon.
     * @param vertices ArrayList of vertices
     */
    public Polygon(ArrayList<Vec2> vertices)
    {
        this.vertices.addAll(vertices);
        calculateCentroid();
    }

    /**
     * Constructor for polygon.
     * @param vertices Array of vertices
     */
    public Polygon(Vec2 ... vertices)
    {
        Collections.addAll(this.vertices, vertices);
        calculateCentroid();
    }

    /**
     * Obtains the ArrayList of vertices
     * @return Vertices
     */
    public ArrayList<Vec2> getVertices()
    {
        return vertices;
    }

    public double getSignedArea()
    {
        double area = 0;
        for(int i = 0; i < vertices.size(); i++)
        {
            Vec2 a = vertices.get(i % vertices.size());
            Vec2 b = vertices.get((i+1) % vertices.size());
            area += a.cross(b);
        }
        return area / 2d;
    }

    public double getMomentOfInertia()
    {
        double p = 0, d = 0;
        for(int i = 0; i < vertices.size(); i++)
        {
            Vec2 a = vertices.get(i % vertices.size());
            Vec2 b = vertices.get((i+1) % vertices.size());
            p += Math.abs(a.cross(b)) * (a.dot(a) + a.dot(b) + b.dot(b));
            d += Math.abs(a.cross(b));
        }
        return p / (6*d);
    }

    private void calculateCentroid()
    {
        double cx = 0, cy = 0;
        for(int i = 0; i < vertices.size(); i++)
        {
            Vec2 a = vertices.get(i % vertices.size());
            Vec2 b = vertices.get((i+1) % vertices.size());
            cx += (a.x.get() + b.x.get()) * a.cross(b);
            cy += (a.y.get() + b.y.get()) * a.cross(b);
        }
        cx /= 6d * getSignedArea();
        cy /= 6d * getSignedArea();

        Vec2 centroid = new Vec2(cx, cy);
        for(Vec2 v : vertices)
            v.subtract(centroid);
    }
}
