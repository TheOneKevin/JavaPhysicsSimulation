import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.components.Polygon;
import oop.simulation.components.PolygonRenderer;
import oop.simulation.math.Vec2;
import oop.simulation.objects.Camera2d;
import oop.simulation.objects.DummyCamera;

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
        this.addGameObject(cam);
        this.setActiveCamera("camera1");

        Polygon p1 = new Polygon(new Vec2[]{
                new Vec2(10, 10), new Vec2(100,100), new Vec2(200,100)
        });
        GameObject g1 = new GameObject("g1");
        g1.addComponent(new PolygonRenderer(p1));
        this.addGameObject(g1);

        Polygon p2 = new Polygon(new Vec2[]{
            new Vec2(20, 20), new Vec2(20, 30), new Vec2(30,30), new Vec2(30, 20)
        });
        GameObject g2 = new GameObject("g2");
        g2.addComponent(new PolygonRenderer(p2));
        this.addGameObject(g2);
    }
}