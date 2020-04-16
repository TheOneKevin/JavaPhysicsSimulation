package oop.simulation;

/**
 * IComponent interface (attached to a GameObject).
 *
 * @author Kevin Dai
 */
public interface IComponent
{
    /**
     * Gets the GameObject the component is attached to
     * @return GameObject
     */
    GameObject parent();

    /**
     * A way to set the parent of the GameObject
     * @param o Parent
     */
    void addedToParent(GameObject o);

    /**
     * Determines whether multiple components of this type may
     * be attached to a GameObject
     * @return True if the above statement is true, false otherwise.
     */
    boolean isUnique();

    /**
     * Called once per act() in the order the component
     * is added to the GameObject.
     */
    void update();
}
