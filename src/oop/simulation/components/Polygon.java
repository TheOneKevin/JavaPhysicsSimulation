package oop.simulation.components;

import oop.simulation.math.Vec2;

import java.util.ArrayList;
import java.util.Collections;

public class Polygon
{
    private ArrayList<Vec2> vertices = new ArrayList<>();

    public Polygon(ArrayList<Vec2> vertices)
    {
        this.vertices = vertices;
    }

    public Polygon(Vec2[] vertices)
    {
        Collections.addAll(this.vertices, vertices);
    }

    public ArrayList<Vec2> getVertices() {
        return vertices;
    }
}
