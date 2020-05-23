package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;

/**
 * This is the super class that all the other classes in the components inherit from
 * @author Kevin Dai
 * @version April 2020
 */
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

    /**
     * Determines the set owner of a game object
     * @param g             GameObject that is parent
     */
    @Override
    public void setOwner(GameObject g)
    {
        if(g != this.owner && this.owner != null)
        {
            this.owner = g;
            this.owner.removeComponent(this);
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
