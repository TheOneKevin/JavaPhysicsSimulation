package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;
import oop.simulation.beans.Property;

import java.util.function.Consumer;

public class BehaviourComponent implements IComponent
{
    private Consumer<GameObject> privateScript;

    public final Property<Consumer<GameObject>> Script = Property.get(() -> privateScript).set(v -> privateScript = v);

    /**
     * Constructor for attaching behaviours to GameObjects.
     *
     * @param script The method to call every act()
     */
    public BehaviourComponent(Consumer<GameObject> script)
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
    public void act(GameObject g)
    {
        privateScript.accept(g);
    }
}
