package oop.simulation.objects;

import greenfoot.Color;
import greenfoot.GreenfootImage;
import oop.simulation.Camera;
import oop.simulation.GameObject;
import oop.simulation.components.PolygonCollider;
import oop.simulation.components.PolygonRenderer;
import oop.simulation.components.Transform;
import oop.simulation.components.UiOverlayComponent;
import oop.simulation.math.MatN;
import oop.simulation.math.Polygon;
import oop.simulation.math.Vec2;
import oop.simulation.math.VecN;

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
            v.getComponent(PolygonRenderer.class) != null && v.getComponent(Transform.class) != null);

        // Prepare for camera transforms
        MatN viewMatrix = Transform.computeModelWorldMatrix(this);

        for(var obj : gameObjects)
        {
            PolygonRenderer pg = obj.getComponent(PolygonRenderer.class);
            Polygon p = pg.getPolygon();
            ArrayList<Vec2> vec2s = new ArrayList<>();

            // Do model space -> view space transformations by computing (view * modelWorld) * vertex
            MatN modelWorldViewMatrix = MatN.matrixMultiply(viewMatrix, Transform.computeModelWorldMatrix(obj));
            for(var v : p.getVertices())
                vec2s.add(Vec2.wTransform(modelWorldViewMatrix, v));

            // Put it in a form that GreenFoot can understand
            int[] xs = new int[vec2s.size()];
            int[] ys = new int[vec2s.size()];

            for(int i = 0; i < vec2s.size(); i++)
            {
                xs[i] = (int) Math.round(vec2s.get(i).x.get());
                ys[i] = getScene().getHeight() - (int) Math.round(vec2s.get(i).y.get());
            }

            g.setColor(Color.GREEN);
            g.drawPolygon(xs, ys, vec2s.size());

            // Display centroid for now...
            var c = obj.getComponent(PolygonCollider.class).getCentroidWorld();
            g.drawOval((int) Math.round(c.x.get()+2), getScene().getHeight() - (int) Math.round(c.y.get()+8), 4, 4);
        }

        for(var ui : this.getComponents(UiOverlayComponent.class))
        {
            g.drawImage(ui.getTexture(), 0, 0);
            ui.clear();
        }
    }
}
