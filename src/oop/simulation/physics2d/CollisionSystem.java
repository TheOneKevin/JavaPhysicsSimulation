package oop.simulation.physics2d;

import greenfoot.Greenfoot;
import greenfoot.GreenfootSound;
import oop.simulation.GameObject;
import oop.simulation.Scene;
import oop.simulation.math.Vec2;

import java.util.ArrayList;

public class CollisionSystem
{
    ArrayList<Manifold> contacts = new ArrayList<>();

    public void collide(ArrayList<Rigidbody2d> bodies, double dt)
    {
        contacts.clear();
        // Get every pair of bodies in the scene
        for(int i = 0; i < bodies.size(); i++)
        {
            Rigidbody2d A = bodies.get(i);
            for(int j = i+1; j < bodies.size(); j++)
            {
                Rigidbody2d B = bodies.get(j);
                if(A.invMass == 0 && B.invMass == 0) continue;
                Manifold m = new Manifold(A, B);
                if(m.solve())
                {
                    contacts.add(m);
                    m.initialize(dt);
                }
            }
        }
    }

    public void solve()
    {
        for(var c : contacts)
            c.applyImpulse();
    }

    public void correctPositions()
    {
        for(var c : contacts)
            c.correctPositions();
    }

    public int numberContacts()
    {
        return contacts.size();
    }
}
