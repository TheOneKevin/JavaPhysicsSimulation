import greenfoot.Greenfoot;
import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.components.BehaviourComponent;
import oop.simulation.components.PolygonCollider;
import oop.simulation.math.Polygon;
import oop.simulation.components.PolygonRenderer;
import oop.simulation.components.Transform;
import oop.simulation.math.Vec2;
import oop.simulation.objects.Camera2d;

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

        Polygon p1 = new Polygon(new Vec2[]{
                new Vec2(10, 10), new Vec2(10,110), new Vec2(110,110)
        });
        GameObject g1 = new GameObject("g1");
        g1.addComponent(new PolygonRenderer(p1));
        g1.addComponent(new PolygonCollider(p1));
        g1.addComponent(new Transform(10, 10, 1, 1));
        g1.addComponent(new BehaviourComponent(g -> {
            var v = new Vec2(0, 0);
            // Just testing
            if(Greenfoot.isKeyDown("w"))
                v.y.set(v.y.get() - 1);
            if(Greenfoot.isKeyDown("s"))
                v.y.set(v.y.get() + 1);
            if(Greenfoot.isKeyDown("a"))
                v.x.set(v.x.get() - 1);
            if(Greenfoot.isKeyDown("d"))
                v.x.set(v.x.get() + 1);
            v.scalarMultiply(100 * this.deltaTime / 1000000000.0); // multiply by time, convert ns to s
            var T = g.getComponent(Transform.class);
            T.Position.get().add(v); // move the damn thing
        }));
        this.addGameObject(g1);
    }
}