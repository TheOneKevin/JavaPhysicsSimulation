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
    private Vec2 centroid;

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

    /**
     * Obtains the precomputed centroid of polygon.
     * @return Centroid
     */
    public Vec2 getCentroid()
    {
        return centroid.clone();
    }

    private void calculateCentroid()
    {
        centroid = new Vec2(0, 0);
        for(Vec2 v : vertices)
            centroid.add(v);
        centroid.scalarMultiply(1.0 / vertices.size());
    }
}
