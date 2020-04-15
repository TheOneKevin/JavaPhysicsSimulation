import oop.simulation.Scene;
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
        DummyCamera cam = new DummyCamera("camera1");
        this.addGameObject(cam);
        this.setActiveCamera("camera1");
    }
}