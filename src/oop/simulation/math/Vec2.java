package oop.simulation.math;

import oop.simulation.beans.Property;

import java.util.Arrays;

/**
 * "Simple" wrapper around VecN. Some items are reimplemented
 * for speed and extensibility (interop b/w VecN and Vec2).
 *
 * TODO: Fully integrate with VecN at some point in the future.
 *
 * @author Kevin Dai
 */
public class Vec2 extends VecN {
    public final Property<Double> x = Property.get(() -> this.data[0]).set(v -> this.data[0] = v);
    public final Property<Double> y = Property.get(() -> this.data[1]).set(v -> this.data[1] = v);

    public Vec2(double x, double y) {
        super(x, y);
    }

    public Vec2(VecN v) {
        super(Arrays.copyOf(v.getArrayData(), 2));
    }

    public Vec2 add(Vec2 b)
    {
        this.data[0] += b.data[0];
        this.data[1] += b.data[1];
        return this;
    }

    public Vec2 subtract(Vec2 b)
    {
        this.data[0] -= b.data[0];
        this.data[1] -= b.data[1];
        return this;
    }

    public static Vec2 add(Vec2 a, Vec2 b)
    {
        return a.clone().add(b);
    }

    public static Vec2 subtract(Vec2 a, Vec2 b)
    {
        return a.clone().subtract(b);
    }

    public static Vec2 negate(Vec2 d)
    {
        return new Vec2(-d.data[0], -d.data[1]);
    }

    public Vec2 negate()
    {
        this.data[0] = -data[0];
        this.data[1] = -data[1];
        return this;
    }

    public double cross(Vec2 b)
    {
        return this.data[0] * b.data[1] - this.data[1] * b.data[0];
    }

    public double dot(Vec2 b)
    {
        return this.data[0] * b.data[0] + this.data[1] * b.data[1];
    }

    public Vec2 rotateLeft90()
    {
        double t = data[1];
        data[1] = data[0];
        data[0] = -t;
        return this;
    }

    public Vec2 rotateRight90()
    {
        double t = data[1];
        data[1] = -data[0];
        data[0] = t;
        return this;
    }

    public static Vec2 wTransform(MatN T, Vec2 v)
    {
        return new Vec2(VecN.wDivide(VecN.matrixMultiply(VecN.getHomoCoords(v), T)));
    }

    public Vec2 clone()
    {
        return new Vec2(this.data[0], this.data[1]);
    }
}
