package oop.simulation.objects;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import oop.simulation.Camera;
import oop.simulation.GameObject;
import oop.simulation.components.Polygon;
import oop.simulation.components.PolygonRenderer;
import oop.simulation.math.Vec2;

import java.util.ArrayList;

/**
 * Example Camera implementation
 */
public class Camera2d extends Camera
{
    /**
     * Default constructor of GameObject
     *
     * @param name Name of GameObject
     */
    public Camera2d(String name)
    {
        super(name);
    }

    @Override
    public void renderToBuffer(GreenfootImage g)
    {
        g.clear();
        g.setColor(Color.BLACK);
        g.fill();
        ArrayList<GameObject> gameObjects = getScene().getGameObjects((v) -> {
            return v.getComponent(PolygonRenderer.class) != null;
        });
        for(var obj : gameObjects)
        {
            PolygonRenderer pg = (PolygonRenderer)obj.getComponent(PolygonRenderer.class);
            Polygon p = pg.getPolygon();
            ArrayList<Vec2> vec2s = p.getVertices();

            int[] xs = new int[vec2s.size()];
            int[] ys = new int[vec2s.size()];

            for(int i = 0; i < vec2s.size(); i++)
            {
                xs[i] = (int)Math.round(vec2s.get(i).getX());
                ys[i] = (int)Math.round(vec2s.get(i).getY());
            }
            g.setColor(Color.GREEN);
            g.drawPolygon(xs, ys, vec2s.size());
        }
    }
}
