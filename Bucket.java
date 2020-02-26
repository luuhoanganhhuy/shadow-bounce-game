/**the whole project contributed to sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 * @author Eleanor McMurtry
 * */
import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;

/**create the bucket*/
public class Bucket extends Sprite{

    /**the velocity of the bucket*/
    private Vector2 velocity = new Vector2(-4,0);

    public Bucket(Point point){
        super(point, "res/bucket.png");
    }

    @Override
    public void update(){

        //change direction if hit the side
        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }
        super.move(velocity);
        super.draw();
    }
}
