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
     * @param g GameObject currently being run
     */
    void update(GameObject g);
}
