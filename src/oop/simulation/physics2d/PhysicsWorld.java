package oop.simulation.physics2d;

import oop.simulation.math.Vec2;

public class PhysicsWorld
{
    public static Vec2 GRAVITY = new Vec2(0.0, -98.1);
    public static final double EPSILON = 0.0001d;
    public static final double PENETRATION_ALLOWANCE = 0.05d;
    public static final double PENETRATION_CORRECTION = 0.4d;

    public static boolean equal(double a, double b)
    {
        return StrictMath.abs(a - b) <= EPSILON;
    }
}
