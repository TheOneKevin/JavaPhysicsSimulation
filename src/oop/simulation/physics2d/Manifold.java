package oop.simulation.physics2d;

import greenfoot.GreenfootSound;
import oop.simulation.math.Vec2;
import oop.simulation.math.VecN;

public class Manifold
{
    protected Rigidbody2d A;
    protected Rigidbody2d B;

    protected Vec2[] contacts = new Vec2[] { new Vec2(0, 0), new Vec2(0, 0) };
    protected Vec2 normal;
    protected double penetration;

    private double e, sf, df;

    public Manifold(Rigidbody2d A, Rigidbody2d B)
    {
        this.A = A;
        this.B = B;
    }

    public boolean solve()
    {
        return MprCollision.collide(B.collider, A.collider, this);
    }

    public void initialize(double dt)
    {
        e = StrictMath.min(A.restitution, B.restitution);
        sf = StrictMath.sqrt(A.staticFriction * A.staticFriction + B.staticFriction * B.staticFriction);
        df = StrictMath.sqrt(A.dynamicFriction * A.dynamicFriction + B.dynamicFriction * B.dynamicFriction);

        for (Vec2 contact : contacts)
        {
            // Omg ahahaha look at that mess
            Vec2 ra = Vec2.subtract(contact, A.getOwner().TransformComponent.get().Position.get());
            Vec2 rb = Vec2.subtract(contact, B.getOwner().TransformComponent.get().Position.get());
            // Why?! This is so ugly I'm going to cry
            Vec2 rv = Vec2.add(B.linearVelocity, Vec2.cross(B.angularVelocity, rb).subtract(A.linearVelocity).subtract(Vec2.cross(A.angularVelocity, ra)));
            if (PhysicsWorld.equal(rv.lengthSq(), Vec2.scalarMultiply(PhysicsWorld.GRAVITY, dt).lengthSq()))
                e = 0;
        }
    }

    public void applyImpulse()
    {
        if(PhysicsWorld.equal(A.invMass + B.invMass, 0))
        {
            A.linearVelocity.set(0, 0);
            B.linearVelocity.set(0, 0);
            return;
        }

        for (Vec2 contact : contacts)
        {
            Vec2 ra = Vec2.subtract(contact, A.getOwner().TransformComponent.get().Position.get());
            Vec2 rb = Vec2.subtract(contact, B.getOwner().TransformComponent.get().Position.get());
            Vec2 rv = Vec2.add(B.linearVelocity, Vec2.cross(B.angularVelocity, rb).subtract(A.linearVelocity).subtract(Vec2.cross(A.angularVelocity, ra)));
            double cv = rv.dot(normal);
            if (cv > 0) return;

            double raCrossN = ra.cross(normal);
            double rbCrossN = rb.cross(normal);
            double invMass = A.invMass + B.invMass + (raCrossN * raCrossN) * A.invInertia + (rbCrossN * rbCrossN) * B.invInertia;

            double j = -(1.0f + e) * cv;
            j /= invMass;
            j /= contacts.length;

            // Calculate impulse
            Vec2 impulse = Vec2.scalarMultiply(normal, j);
            A.applyImpulse(impulse.clone().negate(), ra.clone());
            B.applyImpulse(impulse.clone(), rb.clone());

            // Calculate tangent impulse
            Vec2 t = Vec2.subtract(rv, Vec2.scalarMultiply(normal, rv.dot(normal)));
            t.normalize();

            double jt = -rv.dot(t);
            jt /= invMass;
            jt /= contacts.length;

            if (PhysicsWorld.equal(jt, 0.0))
                return;

            Vec2 tgtImpulse;
            if (StrictMath.abs(jt) < j * sf)
                tgtImpulse = Vec2.scalarMultiply(t, jt);
            else
                tgtImpulse = Vec2.scalarMultiply(t, -j * df);

            A.applyImpulse(tgtImpulse.clone().negate(), ra.clone());
            B.applyImpulse(tgtImpulse.clone(), rb.clone());
        }
    }

    public void correctPositions()
    {
        double correction = (StrictMath.max(penetration - PhysicsWorld.PENETRATION_ALLOWANCE, 0) / (A.invMass + B.invMass)) * PhysicsWorld.PENETRATION_CORRECTION;
        A.getOwner().TransformComponent.get().Position.get().add(Vec2.scalarMultiply(normal, -A.invMass * correction));
        B.getOwner().TransformComponent.get().Position.get().add(Vec2.scalarMultiply(normal, B.invMass * correction));
    }
}
