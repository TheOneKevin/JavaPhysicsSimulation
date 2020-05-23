package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;

public class BasicComponent implements IComponent
{
    protected GameObject owner;

    @Override
    public boolean isUnique()
    {
        return false;
    }

    @Override
    public void update()
    {

    }

    @Override
    public void setOwner(GameObject g)
    {
        if(g != this.owner && this.owner != null)
        {
            this.owner = g;
            // this.owner.removeComponent(this);
        }

        if(this.owner == null)
            this.owner = g;
    }

    @Override
    public GameObject getOwner()
    {
        return this.owner;
    }
}
