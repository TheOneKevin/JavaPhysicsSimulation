package oop.simulation.objects;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import oop.simulation.Camera;
import oop.simulation.GameObject;
import oop.simulation.components.Polygon;
import oop.simulation.components.PolygonRenderer;
import oop.simulation.components.Transform;
import oop.simulation.math.MatN;
import oop.simulation.math.Vec2;
import oop.simulation.math.VecN;

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

        // Render only if GameObject has PolygonRenderer AND Transform
        ArrayList<GameObject> gameObjects = getScene().getGameObjects((v) ->
            v.getComponent(PolygonRenderer.class) != null && v.getComponent(Transform.class) != null);

        for(var obj : gameObjects)
        {
            PolygonRenderer pg = (PolygonRenderer)obj.getComponent(PolygonRenderer.class);
            Polygon p = pg.getPolygon();
            ArrayList<Vec2> vec2s = new ArrayList<>();

            // Do model space -> world space transformations
            MatN modelMatrix = ((Transform) obj.getComponent(Transform.class)).getScaleMatrix();
            for(var v : p.getVertices())
                vec2s.add(new Vec2(VecN.getCartFromHomo(VecN.matrixTransform(VecN.getHomoCoords(v), modelMatrix))));

            int[] xs = new int[vec2s.size()];
            int[] ys = new int[vec2s.size()];

            for(int i = 0; i < vec2s.size(); i++)
            {
                xs[i] = (int) Math.round(vec2s.get(i).x.get());
                ys[i] = (int) Math.round(vec2s.get(i).y.get());
            }

            g.setColor(Color.GREEN);
            g.drawPolygon(xs, ys, vec2s.size());
        }
    }
}
