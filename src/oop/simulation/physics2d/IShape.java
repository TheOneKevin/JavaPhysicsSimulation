package oop.simulation.physics2d;

import oop.simulation.IComponent;
import oop.simulation.math.Vec2;

public interface IShape extends IComponent // Formerly ISupportMappable2D but that's too long to type lol
{
    Vec2 getSupport(Vec2 in);
    Vec2 getSupportWorld(Vec2 in);
    Vec2 getCentroidWorld();
    double getMomentOfInertia();
}
