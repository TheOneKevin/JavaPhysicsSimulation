package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;

public class Transform implements IComponent
{
    private GameObject parentObj;

    public Transform()
    {

    }

    @Override
    public GameObject parent()
    {
        return parentObj;
    }

    @Override
    public void addedToParent(GameObject o)
    {
        this.parentObj = o;
    }

    @Override
    public boolean isUnique()
    {
        return false;
    }

    @Override
    public void update()
    {
        
    }
}
