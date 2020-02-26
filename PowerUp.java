/**the whole project contributed to sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 * @author Eleanor McMurtry
 * */
import bagel.Window;
import bagel.util.*;

/**create the power up*/
public class PowerUp extends Sprite{

    /**the velocity of the power up*/
    private Vector2 velocity;

    /**the speed of the power up*/
    private static final double SPEED = 3;

    /**the random position of initial point*/
    private static final Point iniPoint
            = new Point(Math.random() * Window.getWidth(), Math.random() * Window.getHeight());

    /**the point that power up has to go to*/
    private Point powerPoint;

    /**the destination of the power up*/
    private Vector2 powerDes;

    public PowerUp() {
        super(iniPoint, "res/powerup.png");
        powerPoint = new Point(Math.random() * Window.getWidth(), Math.random() * Window.getHeight());
        powerDes = powerPoint.asVector().sub(iniPoint.asVector()).normalised();
        velocity = powerDes.mul(SPEED);
    }

    @Override
    public void update() {
        super.move(velocity);

        //change the direction if the power up within 5 pixels its destination
        if (super.getCentre().asVector().sub(powerPoint.asVector()).length() <= 5){
            Point prePoint = powerPoint;
            powerPoint = new Point(Math.random() * Window.getWidth(), Math.random() * Window.getHeight());
            powerDes = powerPoint.asVector().sub(prePoint.asVector()).normalised();
            velocity = powerDes.mul(SPEED);
        }

        super.draw();
    }

}
