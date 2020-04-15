package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;
import oop.simulation.math.Vec2;

import java.util.ArrayList;

public class PolygonRenderer implements IComponent
{
    ArrayList<Vec2> verticies;

    public PolygonRenderer(ArrayList<Vec2> verts)
    {
        this.verticies = verts;
    }

    @Override
    public GameObject parent()
    {
        return null;
    }

    @Override
    public void addedToParent(GameObject o)
    {

    }

    @Override
    public boolean isUnique()
    {
        return false;
    }
}
