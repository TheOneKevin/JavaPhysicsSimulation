package oop.simulation.objects;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import oop.simulation.Camera;
import oop.simulation.GameObject;
import oop.simulation.components.*;
import oop.simulation.math.*;

import java.util.ArrayList;

/**
 * Simple 2D Camera Implementation
 * @author Kevin Dai
 * @author Mustafa M.
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
        // Clear buffer
        g.setColor(Color.BLACK);
        g.fill();

        // Render only if GameObject has IRenderer (is render-able)
        ArrayList<GameObject> gameObjects = getScene().getGameObjects((v) ->
            (v.getComponent(PolygonRenderer.class) != null || v.getComponent(CircleRenderer.class) != null)
                && v.getComponent(Transform.class) != null
        );

        // Prepare for camera transforms
        MatN viewMatrix = Transform.computeModelWorldMatrix(this);

        for(var obj : gameObjects)
        {
            // Do model space -> view space transformations by computing (view * modelWorld) * vertex
            MatN modelWorldViewMatrix = MatN.matrixMultiply(viewMatrix, Transform.computeModelWorldMatrix(obj));

            // Polygon Renderer
            PolygonRenderer pg = obj.getComponent(PolygonRenderer.class);
            if(pg != null)
            {
                Polygon p = pg.getPolygon();
                ArrayList<Vec2> vec2s = new ArrayList<>();

                for (var v : p.getVertices())
                    vec2s.add(Vec2.wTransform(modelWorldViewMatrix, v));

                // Put it in a form that GreenFoot can understand
                int[] xs = new int[vec2s.size()];
                int[] ys = new int[vec2s.size()];

                for (int i = 0; i < vec2s.size(); i++)
                {
                    xs[i] = (int) Math.round(vec2s.get(i).x.get());
                    ys[i] = getScene().getHeight() - (int) Math.round(vec2s.get(i).y.get());
                }

                g.setColor(Color.GREEN);
                g.drawPolygon(xs, ys, vec2s.size());

                // Display centroid for now...
                var c = obj.getComponent(PolygonCollider.class).getCentroidWorld();
                g.drawOval((int) Math.round(c.x.get()), getScene().getHeight() - (int) Math.round(c.y.get()), 4, 4);
            }

            // Circle renderer
            CircleRenderer cr = obj.getComponent(CircleRenderer.class);
            if(cr != null)
            {
                Circle cl = cr.getCircle();
                Vec2 pos = Vec2.wTransform(modelWorldViewMatrix, new Vec2(0, 0));
                int r = (int) Math.round(cl.getRadius());
                g.setColor(Color.GREEN);
                g.drawOval(
                    (int) Math.round(pos.x.get()) - r,
                    getScene().getHeight() - (int) Math.round(pos.y.get()) - r,
                    2 * r,
                    2 * r
                );

                // Display centroid for now...
                var c = obj.getComponent(CircleCollider.class).getCentroidWorld();
                g.drawOval((int) Math.round(c.x.get()+7), getScene().getHeight() - (int) Math.round(c.y.get()+9), 4, 4);
            }

            // End scope
        }

        for(var ui : this.getComponents(UiOverlayComponent.class))
        {
            g.drawImage(ui.getTexture(), 0, 0);
            ui.clear();
        }
    }
}
