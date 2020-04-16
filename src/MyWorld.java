import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.components.Polygon;
import oop.simulation.components.PolygonRenderer;
import oop.simulation.components.Transform;
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
        cam.addComponent(new Transform(10, 10, 1, 1));
        this.addGameObject(cam);
        this.setActiveCamera("camera1");

        Polygon p1 = new Polygon(new Vec2[]{
                new Vec2(10, 10), new Vec2(100,100), new Vec2(200,100)
        });
        GameObject g1 = new GameObject("g1");
        g1.addComponent(new PolygonRenderer(p1));
        g1.addComponent(new Transform(10, 0, 0.5, 1));
        this.addGameObject(g1);
    }
}