package oop.simulation.math;

import oop.simulation.math.Vec2;

import java.util.ArrayList;
import java.util.Collections;

public class Polygon
{
    private ArrayList<Vec2> vertices = new ArrayList<>();
    private Vec2 centroid;

    public Polygon(ArrayList<Vec2> vertices)
    {
        this.vertices.addAll(vertices);
        calculateCentroid();
    }

    public Polygon(Vec2[] vertices)
    {
        Collections.addAll(this.vertices, vertices);
        calculateCentroid();
    }

    public ArrayList<Vec2> getVertices()
    {
        return vertices;
    }

    public Vec2 getCentroid()
    {
        return centroid.clone();
    }

    private void calculateCentroid()
    {
        double x = 0, y = 0;
        for(Vec2 v : vertices)
        {
            x += v.x.get();
            y += v.y.get();
        }
        x /= vertices.size();
        y /= vertices.size();
        centroid = new Vec2(x, y);
    }
}
