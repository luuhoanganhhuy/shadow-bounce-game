/**the whole project contributed to sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 * @author Eleanor McMurtry
 * */
import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;
import bagel.util.Vector2;
import bagel.util.Side;

/**create the abstract class Sprite*/
public abstract class Sprite {

    /**get the image*/
    private Image image;

    /**create a rectangle class*/
    private Rectangle rect;

    public Sprite(Point point, String imageSrc) {
        image = new Image(imageSrc);
        rect = image.getBoundingBoxAt(point);
    }

    /**get rectangle
     * @return a rectangle*/
    public Rectangle getRect() {
        return rect;
    }

    /**get centre of the rectangle
     * @return a Point*/
    public Point getCentre(){ return rect.centre();}

    /**check if the rect intersect with other rect
     * @return a boolean*/
    public boolean intersects(Sprite other) {
        return rect.intersects(other.rect);
    }

    /**find the side of intersection
     * @return a side*/
    public Side intersectedAt(Sprite other,Vector2 velocity){
        if (rect.intersects(other.rect.bottomLeft())){
            return rect.intersectedAt(other.rect.bottomLeft(),velocity);
        }
        if (rect.intersects(other.rect.bottomRight())){
            return rect.intersectedAt(other.rect.bottomRight(),velocity);
        }
        if (rect.intersects(other.rect.topLeft())){
            return rect.intersectedAt(other.rect.topLeft(),velocity);
        }
        if (rect.intersects(other.rect.topRight())){
            return rect.intersectedAt(other.rect.topRight(),velocity);
        }
        else{return Side.NONE;}

    }

    /**move to a destination
     * @param dx the destination */
    public void move(Vector2 dx) {
        rect.moveTo(rect.topLeft().asVector().add(dx).asPoint());
    }

    /**draw */
    public void draw() {
        image.draw(rect.centre().x, rect.centre().y);
    }

    public abstract void update();
}
