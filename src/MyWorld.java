import greenfoot.Color;
import greenfoot.Greenfoot;
import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.components.*;
import oop.simulation.math.Polygon;
import oop.simulation.math.Vec2;
import oop.simulation.objects.Camera2d;
import oop.simulation.physics2d.collision.Manifold;
import oop.simulation.physics2d.collision.MprCollision;

/**
 * Example main world
 */
public class MyWorld extends Scene
{
    public MyWorld()
    {
        super(600, 400);

        // Create a camera now
        Camera2d cam = new Camera2d("camera1");
        cam.addComponent(new Transform(10, 10, 1, 1));
        this.addGameObject(cam);
        this.setActiveCamera("camera1");

        UiOverlayComponent ui = new UiOverlayComponent(this.getWidth(), this.getHeight());
        cam.addComponent(ui);

        Polygon t1 = new Polygon(
            new Vec2(0, 0),
            new Vec2(100, 0),
            new Vec2(100, 100),
            new Vec2(0, 100)
        );

        Polygon t2 = new Polygon(
            new Vec2(10, 30), new Vec2(125,140), new Vec2(0,140)
        );

        GameObject g1 = new GameObject("g1");
        g1.addComponent(new PolygonRenderer(t1));
        g1.addComponent(new PolygonCollider(t1));
        g1.addComponent(new Transform(0, 0, 1, 1));

        GameObject g2 = new GameObject("g2");
        g2.addComponent(new PolygonRenderer(t2));
        g2.addComponent(new PolygonCollider(t2));
        g2.addComponent(new Transform(0, 0, 1, 1));

        g1.addComponent(new BehaviourComponent(g -> {
            // Ah, here we go...
            Manifold m = new Manifold();
            boolean collide = MprCollision.collide(g1.getComponent(PolygonCollider.class), g2.getComponent(PolygonCollider.class), m);
            System.out.println(collide);

            // Just testing
            var v = new Vec2(0, 0);
            if(Greenfoot.isKeyDown("w"))
                v.y.set(v.y.get() + 1);
            if(Greenfoot.isKeyDown("s"))
                v.y.set(v.y.get() - 1);
            if(Greenfoot.isKeyDown("a"))
                v.x.set(v.x.get() - 1);
            if(Greenfoot.isKeyDown("d"))
                v.x.set(v.x.get() + 1);
            v.scalarMultiply(100 * deltaTime); // multiply by time, convert ns to s

            if(collide)
            {
                ui.getTexture().setColor(Color.RED);
                ui.getTexture().drawOval(
                    (int) Math.round(m.Point1.x.get()),
                    (int) Math.round(m.Point1.y.get()),
                    3, 3);
                ui.getTexture().drawOval(
                    (int) Math.round(m.Point2.x.get()),
                    (int) Math.round(m.Point2.y.get()),
                    3, 3);
                m.Penetration.normalize();
                v.add(m.Penetration);
            }

            // Get Transform
            var T = g.getComponent(Transform.class);
            T.Position.get().add(v); // move the damn thing

            // Rotate the damn thing
            if(Greenfoot.isKeyDown("left"))
                T.Rotation.set(T.Rotation.get() - 1 * deltaTime);
            if(Greenfoot.isKeyDown("right"))
                T.Rotation.set(T.Rotation.get() + 1 * deltaTime);
        }));

        this.addGameObject(g1);
        this.addGameObject(g2);
    }
}