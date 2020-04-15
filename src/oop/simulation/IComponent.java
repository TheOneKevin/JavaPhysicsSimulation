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
}
