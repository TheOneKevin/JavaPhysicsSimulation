package oop.simulation.math;

import oop.simulation.beans.Property;

public class Vec3 extends VecN
{
    public final Property<Double> x = Property.get(() -> this.data[0]).set(v -> this.data[0] = v);
    public final Property<Double> y = Property.get(() -> this.data[1]).set(v -> this.data[1] = v);
    public final Property<Double> z = Property.get(() -> this.data[2]).set(v -> this.data[2] = v);

    public Vec3(double x, double y, double z)
    {
        super(x, y, z);
    }
}
