package oop.simulation.math;

import oop.simulation.beans.Property;

/**
 * "Simple" wrapper around VecN. Some items are reimplemented
 * for speed and extensibility (interop b/w VecN and Vec2). It's
 * all horribly complex. I'm probably doing it wrong.
 *
 * @author Kevin Dai
 * @author Kai Qi Hao (nova-terra)
 */
public class Vec3 extends VecN {
    public final Property<Double> x = Property.get(() -> this.data[0]).set(v -> this.data[0] = v);
    public final Property<Double> y = Property.get(() -> this.data[1]).set(v -> this.data[1] = v);
    public final Property<Double> z = Property.get(() -> this.data[2]).set(v -> this.data[2] = v);

    public Vec3(double x, double y, double z) {
        super(x, y, z);
    }

    public static Vec3 getCrossProduct(Vec3 u, Vec3 v) {
        Vec3 w = (Vec3) u.clone();
        w.getCrossProduct(v);
        return w;
    }

    public void getCrossProduct(Vec3 v) {
        Vec3 n = new Vec3(getEntryAt(2) * v.getEntryAt(3) - getEntryAt(3) * v.getEntryAt(2),
                getEntryAt(3) * v.getEntryAt(1) - getEntryAt(1) * v.getEntryAt(3),
                getEntryAt(1) * v.getEntryAt(2) - getEntryAt(2) * v.getEntryAt(1));
        data = n.data;
    }
}
