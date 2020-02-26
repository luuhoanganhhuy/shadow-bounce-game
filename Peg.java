/**the whole project contributed to sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 * @author Eleanor McMurtry
 * */
import bagel.util.Point;

/**create the peg*/
public class Peg extends Sprite {

    /**the type of peg*/
    private String pegType;

    /**the position of the peg*/
    private Point pegPosition;


    public Peg(Point point, String type) {
        super(point,type);
        pegType = type;
        pegPosition = point;
    }

    /**get the type of peg
     * @return a String of peg's type */
    public String getType(){return pegType;}

    /**get the position of peg
     * @return a Point of the peg's position*/
    public Point getPosition(){return pegPosition;}

    @Override
    public void update() {
        super.draw();
    }
}
