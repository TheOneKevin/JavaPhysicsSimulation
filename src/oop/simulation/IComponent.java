package oop.simulation;

/**
 * IComponent interface (attached to a GameObject).
 * Components should not contain logic either. Like GameObjects,
 * they only serve to store information. One component can be
 * attached to many GameObjects.
 *
 * @author Kevin Dai
 */
public interface IComponent
{
    /**
     * Determines whether multiple components of this type may
     * be attached to a GameObject
     * @return True if the above statement is true, false otherwise.
     */
    boolean isUnique();

    /**
     * Contains very very simple logic run once per act()
     */
    void update();

    /**
     * @param g GameObject that is parent
     */
    void setOwner(GameObject g);

    /**
     * @return The owner
     */
    GameObject getOwner();
}
