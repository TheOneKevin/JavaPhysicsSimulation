package oop.simulation.math;

import oop.simulation.beans.Property;

public class Vec2 extends VecN
{
    public final Property<Double> x = Property.get(() -> this.data[0]).set(v -> this.data[0] = v);
    public final Property<Double> y = Property.get(() -> this.data[1]).set(v -> this.data[1] = v);

    public Vec2(double x, double y)
    {
        super(x, y);
    }
}
