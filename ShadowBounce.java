/**the whole project contributed to sample solution for SWEN20003 Object Oriented Software Development
 * Project 1, Semester 2, 2019
 * @author Eleanor McMurtry
 * */

import bagel.*;
import bagel.util.*;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/** create the ShadowBouce game*/
public class ShadowBounce extends AbstractGame {

    /**create a array list of peg*/
    private List<Peg> pegs ;

    /**create a lise of 3 ball*/
    private Ball[] ball = new Ball[TOTAL_BALL];

    /**create the bucket at its initial position */
    private Bucket bucket = new Bucket(BUCKET_POSITION);

    /**create the power up*/
    private PowerUp powerUp;

    /**create the number of turn left*/
    private int turn = INI_TURN;

    /**create the current level*/
    private int level = INI_LEVEL;

    /**create the state whether ready for new turn*/
    private boolean newTurn = false;

    /**create the state whether ready for new board*/
    private boolean newBoard = true;

    /**create the the number of red pegs left*/
    private int redPeg;

    /**there are 3 balls in total */
    private static final int TOTAL_BALL = 3;

    /**player has 20 turns to complete the game*/
    private static final int INI_TURN = 20;

    /**the speed of ball*/
    private static final int SPEED = 10;

    /**the initial position of the ball*/
    private static final Point BALL_POSITION = new Point(512, 32);

    /**the initial position of the bucket*/
    private static final Point BUCKET_POSITION = new Point(512,744);

    /**bound of power up's strength*/
    private static final int POWER_BOUND = 70;

    /**the final level*/
    private static final int FINAL_LEVEL = 4;

    /**the ball created by the clicked*/
    private static final int ORIGINAL_BALL =0;

    /**a radom number using for 1/10 percentange*/
    private static final int RANDOM_NUM =6;

    /**the initial level of the game*/
    private static final int INI_LEVEL =0;

    /**number of green peg*/
    private static final int NUM_GREEN_PEG =1;

    /**ball to the left which is created when intersect with green ball*/
    private static final int LEFT_BALL = 1;

    /**ball to the right which is created when intersect with green ball*/
    private static final int RIGHT_BALL = 2;

    public ShadowBounce() {

        generateBoard(level);
        generateGreen();
        generatePowerUp();

    }

    /**generate the board
     * @param level a int regards to the current level of this board*/
    public void generateBoard(int level){
        int numBlue=0;
        int numPeg = 0;
        pegs = new ArrayList<>();

        //read the csv file
        try (BufferedReader br = new BufferedReader(new FileReader("res/"+level+".csv"))) {
            String text;
            while ((text = br.readLine()) != null) {
                String[] data = text.split(",");
                Point p = new Point(Double.parseDouble(data[1]), Double.parseDouble(data[2]));

                //assign data to pegs
                if (data[0].equals("blue_peg")) {
                    pegs.add(new Peg(p, "res/peg.png"));
                    numBlue++;
                }
                if (data[0].equals("blue_peg_vertical")) {
                    pegs.add(new Peg(p, "res/vertical-peg.png")) ;
                    numBlue++;
                }
                if (data[0].equals("blue_peg_horizontal")) {
                    pegs.add(new Peg(p, "res/horizontal-peg.png")) ;
                    numBlue++;
                }
                if (data[0].equals("grey_peg")) {
                    pegs.add(new Peg(p, "res/grey-peg.png")) ;
                }
                if (data[0].equals("grey_peg_vertical")) {
                    pegs.add(new Peg(p, "res/grey-vertical-peg.png"));
                }
                if (data[0].equals("grey_peg_horizontal")) {
                    pegs.add(new Peg(p, "res/grey-horizontal-peg.png")) ;
                }

                numPeg++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        //generate random red peg
        int count = 0;
        redPeg = numBlue/5;
        while (count < redPeg){
            Random rand = new Random();
            int ranNum = rand.nextInt(numPeg);
            if (pegs.get(ranNum).getType().equals("res/peg.png")){
                pegs.set(ranNum,new Peg(pegs.get(ranNum).getPosition(),"res/red-peg.png"));
                count++;
            }
            if (pegs.get(ranNum).getType().equals("res/vertical-peg.png")){
                pegs.set(ranNum,new Peg(pegs.get(ranNum).getPosition(),"res/red-vertical-peg.png"));
                count++;
            }
            if (pegs.get(ranNum).getType().equals("res/horizontal-peg.png")){
                pegs.set(ranNum,new Peg(pegs.get(ranNum).getPosition(),"res/red-horizontal-peg.png"));
                count++;
            }
        }

    }

    /**generate a random green ball*/
    public void generateGreen(){
        int count =0;
        while (count < NUM_GREEN_PEG) {
            Random rand = new Random();
            int ranNum = rand.nextInt(pegs.size());
            if (pegs.get(ranNum).getType().equals("res/peg.png")){
                pegs.set(ranNum, new Peg(pegs.get(ranNum).getPosition(), "res/green-peg.png"));
                count++;
            }
            if (pegs.get(ranNum).getType().equals("res/vertical-peg.png")) {
                pegs.set(ranNum, new Peg(pegs.get(ranNum).getPosition(), "res/green-vertical-peg.png"));
                count++;
            }
            if (pegs.get(ranNum).getType().equals("res/horizontal-peg.png")) {
                pegs.set(ranNum, new Peg(pegs.get(ranNum).getPosition(), "res/green-horizontal-peg.png"));
                count++;
            }
        }
    }

    /**delete all pegs with in 70 pixel of explosion
     * @param ball the ball using to analyse the impact of explosion*/
    public void explosion(Ball ball){
        int i =0;
        if (ball.getFire()){
            while (i < pegs.size()){
                if (pegs.get(i) != null) {
                    if (ball.getCentre().asVector().sub(pegs.get(i).getCentre().asVector()).length() <=POWER_BOUND){

                        if (pegs.get(i).getType().equals("res/red-peg.png")
                                || pegs.get(i).getType().equals("res/red-vertical-peg.png")
                                || pegs.get(i).getType().equals("res/red-horizontal-peg.png")) {
                            redPeg--;
                        }
                        if (!pegs.get(i).getType().equals("res/grey-peg.png")
                                && !pegs.get(i).getType().equals("res/grey-vertical-peg.png")
                                && !pegs.get(i).getType().equals("res/grey-horizontal-peg.png")){
                            pegs.remove(i);
                            i--;
                        }
                    }
                }
                i++;

            }
        }
    }

    /**reset the green to blue ball if not hit*/
    public void resetGreen(){
        for (int i =0; i< pegs.size();i++){
            if (pegs.get(i).getType().equals("res/green-vertical-peg.png")){
                pegs.set(i, new Peg(pegs.get(i).getPosition(),"res/vertical-peg.png"));
            }
            if (pegs.get(i).getType().equals("res/green-peg.png") ){
                pegs.set(i, new Peg(pegs.get(i).getPosition(),"res/peg.png"));
            }
            if ( pegs.get(i).getType().equals("res/green-horizontal-peg.png")){
                pegs.set(i, new Peg(pegs.get(i).getPosition(),"res/horizontal-peg.png"));
            }

        }
    }

    /**generate power up with 1/10 chance*/
    public void generatePowerUp(){

        Random rand = new Random();
        int ranNum = rand.nextInt(10);
        //choose a random number between 0 and 10
        if (ranNum == RANDOM_NUM){
            powerUp = new PowerUp();
        }
    }

    @Override
    protected void update(Input input) {

        // Check all non-deleted pegs for intersection with the ball
        int i =0;
        for (int j=0; j < TOTAL_BALL;j++) {
            while ( i < pegs.size()) {
                if (pegs.get(i) != null) {
                    if (ball[j] != null && ball[j].intersects(pegs.get(i))) {

                        //check which side the peg intersects with ball and bounce back
                        if (pegs.get(i).intersectedAt(ball[j],ball[j].getVelocity()).toString().equals("TOP") ||
                                pegs.get(i).intersectedAt(ball[j],ball[j].getVelocity()).toString().equals("BOTTOM")){
                            ball[j].bounceVertically();
                        }
                        if (pegs.get(i).intersectedAt(ball[j],ball[j].getVelocity()).toString().equals("LEFT") ||
                                pegs.get(i).intersectedAt(ball[j],ball[j].getVelocity()).toString().equals("RIGHT")){
                            ball[j].bounceHorizontally();
                        }

                        //create two ball if intersect with green ball
                        if (pegs.get(i).getType().equals("res/green-peg.png")
                                || pegs.get(i).getType().equals("res/green-vertical-peg.png")
                                || pegs.get(i).getType().equals("res/green-horizontal-peg.png")){
                            ball[LEFT_BALL] =
                                    new Ball(pegs.get(i).getPosition(),Vector2.up.add(Vector2.left),"ball");
                            ball[RIGHT_BALL] =
                                    new Ball(pegs.get(i).getPosition(),Vector2.up.add(Vector2.right),"ball");
                        }

                        //decrease the number of red peg if intersect
                        if (pegs.get(i).getType().equals("res/red-peg.png")
                                || pegs.get(i).getType().equals("res/red-vertical-peg.png")
                                || pegs.get(i).getType().equals("res/red-horizontal-peg.png")){
                            redPeg--;
                        }

                        //remove the ball if intersect except grey ball
                        if (!pegs.get(i).getType().equals("res/grey-peg.png")
                                && !pegs.get(i).getType().equals("res/grey-vertical-peg.png")
                                && !pegs.get(i).getType().equals("res/grey-horizontal-peg.png")) {
                            pegs.remove(i);
                            i--;

                        }
                        explosion(ball[j]);

                    } else {
                        pegs.get(i).update();
                    }
                }
                i++;
            }

        }


        // Create the bucket
        bucket.update();


        // create ball when clicked
        if (input.wasPressed(MouseButtons.LEFT) && ball[ORIGINAL_BALL] == null) {
            ball[ORIGINAL_BALL] = new Ball(BALL_POSITION, input.directionToMouse(BALL_POSITION),"ball");
        }

        for (int j=0;j<TOTAL_BALL;j++){
            if (ball[j] != null) {
                newTurn = false;

                //create fire ball if intersect with power up
                if (powerUp != null){
                    if ( ball[j].intersects(powerUp)){
                        ball[j]= new Ball(ball[j].getPosition(),ball[j].getVelocity().div(SPEED),"fireball");
                    }
                }

                ball[j].update();

                //increase the turn if intersect with bucket
                if (ball[j].intersects(bucket)){
                    ball[j] = null;
                    turn++;
                }

                // Delete the ball when it leaves the screen
                else if (ball[j].outOfScreen()) {
                    ball[j] = null;

                    //decrease the turn when the original ball leave the screen
                    if (j==0){
                        turn--;
                    }

                    //begin new turn when all the ball leave the screen
                    if (ball[ORIGINAL_BALL] == null && ball[LEFT_BALL] == null && ball[RIGHT_BALL] == null){
                        powerUp = null;
                        newTurn = true;
                        resetGreen();
                    }
                }
            }

        }


        //begin a new turn
        if (newTurn){

            //create a new random green ball
            generateGreen();

            // create a power up
            generatePowerUp();
            newTurn = false;
            System.out.println("Turn remaining: "+turn);
        }


        if (powerUp != null){
            powerUp.update();
        }

        //load a new board if all red pegs gone
        if ((redPeg==0) && (newBoard)){
            newBoard = false;
            level++;
            for (int j=0;j<TOTAL_BALL;j++){
                ball[j]=null;
            }
            if (level > FINAL_LEVEL){
                System.out.println("You win");
                Window.close();
                System.exit(0);
            }
            generateBoard(level);
            generateGreen();
        }

        if (redPeg == 0) {
            newBoard = true;
        }

        if (turn  == 0){
            Window.close();
            System.out.println("You lose");
        }

    }

    public static void main(String[] args) {
        new ShadowBounce().run();
    }
}