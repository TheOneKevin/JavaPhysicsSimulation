package oop.simulation.physics2d;

import oop.simulation.GameObject;
import oop.simulation.IComponent;
import oop.simulation.beans.Property;
import oop.simulation.beans.Readonly;
import oop.simulation.components.Transform;
import oop.simulation.math.Vec2;

/**
 * This is the rigid body that we attach to the game object
 * It provides the physics into the game objects
 *
 * @author Kai Qi
 * @version April 2020
 */
public class Rigidbody2d implements IComponent
{
    private GameObject g;

    protected double angularVelocity;
    protected double torque;
    protected double invMass;
    protected double invInertia;

    protected Vec2 force;
    protected Vec2 arm;
    protected Vec2 linearVelocity;
    protected IShape collider;

    // TODO: Move into another class called "Material"
    protected double restitution;
    protected double staticFriction;
    protected double dynamicFriction;

    public final Property<Double> AngularVelocity = Property.get(() -> angularVelocity).set(v -> angularVelocity = v);

    /**
     * This is a constructor for the rigid body class
     * @param collider          The collider
     * @param mass
     */
    public Rigidbody2d(IShape collider, double mass)
    {
        this.restitution = 3.5;
        this.staticFriction = 0.4;
        this.dynamicFriction = 0.3;

        this.force = new Vec2(0, 0);
        this.arm = new Vec2(0, 0);
        this.linearVelocity = new Vec2(0, 0);

        this.collider = collider;
        // this.mass = mass;
        if(Double.isInfinite(mass))
            this.invMass = 0;
        else
            this.invMass = 1.0 / mass;
        this.invInertia = 1.0 / collider.getMomentOfInertia();
    }

    /**
     * This is a constructor for the rigid body class
     * @param g             This is the game object specified
     * @param collider      This is the collider for the game object
     * @param mass          This is the mass of the object
     */
    public Rigidbody2d(GameObject g, IShape collider, double mass)
    {
        this(collider, mass);
        g.addComponent(collider);
    }

    /**
     * This applies force to the given game object
     * @param f             The specified coordinates
     */
    public void applyForce(Vec2 f)
    {
        force.add(f);
    }

    /**
     * This applies force to the given game object
     * @param f             The specified coordinates
     * @param r             The specified coordinates
     */
    public void applyForce(Vec2 f, Vec2 r)
    {
        arm.add(r);
        force.add(f);
    }

    /**
     * This applies impulse to the game object
     * @param j             The specified coordinates
     * @param r             The specified coordinates
     */
    public void applyImpulse(Vec2 j, Vec2 r)
    {
        linearVelocity.add(j.scalarMultiply(invMass));
        angularVelocity += invInertia * r.cross(j);
    }

    public void clearForces()
    {
        force.x.set(0d);
        force.y.set(0d);
        arm.x.set(0d);
        arm.y.set(0d);
        torque = 0;
    }

    public void integrate()
    {
        applyForce(PhysicsWorld.GRAVITY);

        double dt = g.getScene().DeltaT.get();
        Rigidbody2d rb = g.getComponent(Rigidbody2d.class);

        // Calculate force and torque
        rb.torque = arm.cross(rb.force);

        // Semi-implicit Euler
        Vec2 linearAcceleration = Vec2.scalarMultiply(rb.force, rb.invMass);
        rb.linearVelocity.add(Vec2.scalarMultiply(linearAcceleration, dt));

        double angularAcceleration = (rb.torque * invMass) * invInertia;
        rb.angularVelocity += angularAcceleration * dt;
    }

    @Override
    public boolean isUnique()
    {
        return true;
    }

    @Override
    public void update()
    {
        double dt = g.getScene().DeltaT.get();
        Rigidbody2d rb = g.getComponent(Rigidbody2d.class);
        Transform t = g.TransformComponent.get();
        t.Position.get().add(Vec2.scalarMultiply(rb.linearVelocity, dt));
        t.Rotation.set(t.Rotation.get() + rb.angularVelocity * dt);
    }

    /**
     * Sets the owner of the game object
     * @param g             GameObject that is parent
     */
    @Override
    public void setOwner(GameObject g)
    {
        if(g != this.g && this.g != null)
        {
            this.g = g;
            this.g.removeComponent(this);
        }

        if(this.g == null)
            this.g = g;
    }

    /**
     * Changes the properties of the game object
     * @param m             The specified mass
     * @param e             The specified restitution
     * @param ms            The specified static friction
     * @param mk            The specified dynamic friction
     */
    public void changeProperties(double m, double e, double ms, double mk)
    {
        if(Double.isInfinite(m))
            this.invMass = 0;
        else
            this.invMass = 1.0 / m;
        this.restitution = e;
        this.staticFriction = ms;
        this.dynamicFriction = mk;
    }

    public double getMass()
    {
        return 1.0/invMass;
    }

    @Override
    public GameObject getOwner()
    {
        return this.g;
    }
}
