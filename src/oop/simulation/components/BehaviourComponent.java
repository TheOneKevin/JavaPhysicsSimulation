package oop.simulation.components;

import oop.simulation.IBehaviour;
import oop.simulation.beans.Property;

/**
 * Allows attaching behaviours to GameObjects
 *
 * @author Kevin Dai
 */
public class BehaviourComponent extends BasicComponent
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
    public void update()
    {
        privateScript.act(owner);
    }
}
