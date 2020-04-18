package oop.simulation.components;

import oop.simulation.GameObject;
import oop.simulation.IComponent;
import oop.simulation.beans.Property;
import oop.simulation.math.MatN;
import oop.simulation.math.Vec2;

/**
 * Transform component describes object space to world space transformations.
 * @author Kevin Dai
 */
public class Transform implements IComponent
{
    private Vec2 translation;
    private double rotation = 0;
    private Vec2 scaling;

    public final Property<Vec2> Position = Property.get(() -> translation).set((v) -> translation = new Vec2(v));
    public final Property<Double> Rotation = Property.get(() -> rotation).set((r) -> rotation = r);
    public final Property<Vec2> Scale = Property.get(() -> scaling).set((s) -> scaling = new Vec2(s));

    public Transform(double x, double y, double sx, double sy)
    {
        this.translation = new Vec2(x, y);
        this.scaling = new Vec2(sx, sy);
    }

    public MatN getScaleMatrix()
    {
        var T = new double[][] {
            new double[] { scaling.x.get(), 0, 0 },
            new double[] { 0, scaling.y.get(), 0 },
            new double[] { 0, 0, 1 }
        };
        return new MatN(T);
    }

    public MatN getTranslationMatrix()
    {
        var T = new double[][] {
            new double[] { 1, 0, translation.x.get() },
            new double[] { 0, 1, translation.y.get() },
            new double[] { 0, 0, 1 }
        };
        return new MatN(T);
    }

    public MatN getRotationMatrix()
    {
        var T = new double[][] {
            new double[] { Math.cos(rotation), -Math.sin(rotation), 0 },
            new double[] { Math.sin(rotation),  Math.cos(rotation), 0 },
            new double[] { 0, 0, 1 }
        };
        return new MatN(T);
    }

    public static MatN computeModelViewMatrix(GameObject g)
    {
        MatN r = MatN.identityN(3);
        for(var t : g.getComponents(Transform.class))
            r.matrixMultiply(MatN.matrixMultiply(t.getTranslationMatrix(), MatN.matrixMultiply(t.getRotationMatrix(), t.getScaleMatrix())));
        return r;
    }

    @Override
    public boolean isUnique()
    {
        return false;
    }

}
