package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;
import oop.simulation.beans.Property;
import oop.simulation.math.Vec2;
import oop.simulation.physics2d.collision.IShape;

public class Rigidbody2d implements IComponent
{
    private double angularVelocity;
    private double torque;
    private double invMass;
    private double mass;

    private Vec2 force;
    private Vec2 arm;
    private Vec2 linearVelocity;
    private IShape collider;

    public final Property<Double> AngularVelocity = Property.get(() -> angularVelocity).set(v -> angularVelocity = v);

    public Rigidbody2d(IShape collider, double mass)
    {
        this.force = new Vec2(0, 0);
        this.arm = new Vec2(0, 0);
        this.linearVelocity = new Vec2(0, 0);

        this.collider = collider;
        this.mass = mass;
        this.invMass = 1.0 / mass;
    }

    public Rigidbody2d(GameObject g, IShape collider, double mass)
    {
        this(collider, mass);
        g.addComponent(collider);
    }

    public void applyForce(Vec2 f)
    {
        force.add(f);
    }

    public void applyForce(Vec2 f, Vec2 r)
    {
        arm.add(r);
        force.add(f);
    }

    public void clearForces()
    {
        force.x.set(0d);
        force.y.set(0d);
        arm.x.set(0d);
        arm.y.set(0d);
    }

    @Override
    public boolean isUnique()
    {
        return true;
    }

    @Override
    public void update(GameObject g)
    {
        double dt = g.getScene().DeltaT.get();
        Rigidbody2d rb = g.getComponent(Rigidbody2d.class);
        Transform t = g.getComponent(Transform.class);

        // Calculate force and torque
        rb.torque = arm.cross(rb.force);

        // Semi-implicit Euler
        Vec2 linearAcceleration = Vec2.scalarMultiply(rb.force, rb.invMass);
        rb.linearVelocity.add(Vec2.scalarMultiply(linearAcceleration, dt));
        t.Position.get().add(Vec2.scalarMultiply(rb.linearVelocity, dt));

        double angularAcceleration = (rb.torque * invMass) / rb.collider.getMomentOfInertia();
        rb.angularVelocity += angularAcceleration * dt;
        t.Rotation.set(t.Rotation.get() + rb.angularVelocity * dt);
    }
}
