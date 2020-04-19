package oop.simulation.physics2d.collision;

import oop.simulation.math.Vec2;

public interface IShape // Formerly ISupportMappable2D but that's too long to type lol
{
    Vec2 getSupport(Vec2 in);
    Vec2 getCentroid();
    Vec2 getSupportWorld(Vec2 in);
    Vec2 getCentroidWorld();
}
