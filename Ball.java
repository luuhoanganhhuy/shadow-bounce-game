

import bagel.Window;
import bagel.util.Point;
import bagel.util.Vector2;
/**the whole project contributed to sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 * @author Eleanor McMurtry
 * */

/**create the ball*/
public class Ball extends Sprite {

    /**the velocity of the ball*/
    private Vector2 velocity;

    /**the gravity*/
    private static final double GRAVITY = 0.15;

    /**the speed of the ball*/
    private static final double SPEED = 10;

    /**the state of the ball*/
    private boolean isFire = false;

    public Ball(Point point, Vector2 direction,String type) {
        super(point, "res/"+type+".png");
        velocity = direction.mul(SPEED);
        if (type.equals("fireball")){
            isFire = true;
        }
    }

    /**get the state of the ball
     * @return a boolean of state's ball*/
    public boolean getFire(){return isFire;}

    /**check if the ball of screen
     * @return a boolean*/
    public boolean outOfScreen() {
        return super.getRect().top() > Window.getHeight();
    }

    /**return the velocity of the ball
     * @return a Vector2 of velocity*/
    public Vector2 getVelocity(){return velocity;}

    /**make the ball bouce vertically*/
    public void bounceVertically(){
        velocity = new Vector2(velocity.x,-velocity.y);
    }

    /**make the ball bounce horizontally*/
    public void bounceHorizontally(){
        velocity= new Vector2(-velocity.x,velocity.y);
    }

    /**get the position of the ball
     * @return a Point of ball's position*/
    public Point getPosition(){return super.getRect().centre();}

    @Override
    public void update() {
        velocity = velocity.add(Vector2.down.mul(GRAVITY));
        super.move(velocity);

        //change direction if hit the side
        if (super.getRect().left() < 0 || super.getRect().right() > Window.getWidth()) {
            velocity = new Vector2(-velocity.x, velocity.y);
        }

        super.draw();
    }
}
