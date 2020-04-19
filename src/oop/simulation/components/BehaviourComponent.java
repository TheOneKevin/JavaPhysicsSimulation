package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IBehaviour;
import oop.simulation.IComponent;
import oop.simulation.beans.Property;

/**
 * Allows attaching behaviours to GameObjects
 *
 * @author Kevin Dai
 */
public class BehaviourComponent implements IComponent
{
    private IBehaviour privateScript;

    public final Property<IBehaviour> Script = Property.get(() -> privateScript).set(v -> privateScript = v);

    /**
     * Constructor for attaching behaviours to GameObjects.
     *
     * @param script The method to call every act()
     */
    public BehaviourComponent(IBehaviour script)
    {
        this.Script.set(script);
    }

    @Override
    public boolean isUnique()
    {
        return false;
    }

    /**
     * Execute the attached script.
     *
     * @param g GameObject currently being executed.
     */
    @Override
    public void update(GameObject g)
    {
        privateScript.act(g);
    }
}
