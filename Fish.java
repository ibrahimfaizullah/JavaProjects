import java.util.Random;

/** A Fish class to model fish in a video game.
  *
  * COMP 1006/1406 - Summer 2016 - Assignment 2
  */

public class Fish{
  /* constants for fish bowl and fish parameters (DO NO CHANGE THESE) */
  /* -----------------------------------------------------------------
  /* a fish must have position that is within the fish bowl         
   * so 0 <= x <= WIDTH
	*    0 <= y <= HEIGHT
	* the speed of a fish must always satisfy 
	*    -MAX_SPEED <= dx,dy <= MAX_SPEED
   * the size of a fish must always satisfy 
	*    1 <= size <= MAX_SIZE
	* the health of a fish must be non-negative 
   -------------------------------------------------------------------	*/
  public static final int WIDTH = 600;
  public static final int HEIGHT = 400;
  public static final int MAX_SPEED = 5;
  public static final int MAX_SIZE = 30;
  
  
  /* attributes of a fish object */
  private int id;     // unique id for each fish
  private int size;   // size of fish (1 <= size <= MAX_SIZE)
  private int health; // health of fish (0 means the fish is not alive)
  
  private double x, y;      // x,y coordinates of the fish
  private double dx, dy;    // x,y speed values for the fish
  private static int ID_CTR = 0;
  
  /* constructors */
  public Fish(int size, int health){
    /* creates a fish with specified size > 0 and health > 0   */
    /* with a unique id and random position and speed          */
	  Random rand = new Random();
	  this.id = ID_CTR++;
	  this.size = size;
	  this.health = health;
	  this.x = rand.nextInt(WIDTH+1);
	  this.y = rand.nextInt(HEIGHT+1);
	  this.dx = rand.nextInt(MAX_SPEED+1);
	  this.dy = rand.nextInt(MAX_SPEED+1);
    }
  
  public Fish(int size, int health, double x, double y){
    /* creates a fish with specified size >0, health > 0 and position  */
    /* with a unique id and random speed   
     *                             */
	  
	  Random rand = new Random();
	  this.id = ID_CTR++;
	  this.size = size;
	  this.health = health;
	  this.x = x;
	  this.y = y;
	  this.dx = rand.nextInt(MAX_SPEED+1);
	  this.dy = rand.nextInt(MAX_SPEED+1);
    }
  
  public Fish(int size, int health, 
              double x, double y, 
              double dx, double dy){
	 /* creates a fish with specified size > 0, health > 0, position and  */
    /* speed with a unique id                                           */
	  this.id = ID_CTR++;
	  this.size = size;
	  this.health = health;
	  this.x = x;
	  this.y = y;
	  this.dx = dx;
	  this.dy = dy;
    }
  
  
  /* provided getters */
  public int getID(){ return id; }
  public int getSize(){ return size; }
  public int getHealth(){ return health; }
  public double getX(){ return x; }
  public double getY(){ return y; }
  public double getDX(){ return dx; }
  public double getDY(){ return dy; }
  
  /* these setters are for testing only!!      */
  /* only use them to test your other methods  */
  public void setSize(int s){ this.size = s; }
  public void setHealth(int h){ this.health = h; }
  public void setX(double x){ this.x = x; }
  public void setY(double y){ this.y = y; }
  public void setDX(double dx){ this.dx = dx; }
  public void setDY(double dy){ this.dy = dy; }
  
  
  /* provided helper methods */
  
  public String toString(){
    /* returns a string representation of a fish object           */
    /* format method of String class allows us to format numbers  */
    /* with specified number of digits, or decimal places         */
    String out = "Fish "; 
    out += String.format("%03d", this.getID()) + " : ";
    out += String.format("%02d", this.getHealth()) + " health : ";
    out += String.format("%02d", this.getSize()) + " size : ";
    out += "at (" + this.getX() + "," + this.getY() + ")";
    return out;
  }
  
  public boolean hasMated(Fish mater){
    /* since all attributes are private, the mate method
     cannot change the health of both fish. This
     helper method, called from mate, allows change the health 
     of the "this" fish from mate method.   
     */
    if( this.closeEnough(mater) && mater.getSize() == getSize() & this.getHealth() > 0){
      health -= 1;
      return true;
    }
    return false;
  }
  
  
  public boolean beenEaten(Fish eater){
    /* since all attributes are private, the eat method
     cannot change the health of the eaten fish. This
     helper method, called from eat, allows us to do this.  
     */
    if( this.closeEnough(eater) && eater.getSize() > getSize() ){
      health = 0;
      return true;
    }
    return false;
  }
  




  
  
  /* ------------------------------------------------------------- */
  /* methods                                                       */
  /* ------------------------------------------------------------- */
  
  public boolean eat(Fish other){
    // If this fish is bigger than the other fish and
    // this fish is close enough to the other fish, it 
    // eats it.
    // When a fish eats another fish, it takes all of the 
    // health of the other fish for itself and it also 
    // increases in size by one third of the size of the eaten fish.
	 // Use integer division for all computations.
    // Example: if this fish has 11 health and size 6 and the fish 
    //          it eats has health 7 and size 5, then this fish 
    //          increases its health by 7 and increases its 
    //          size by 5/3 = 1.
    
	 /* ------------------------------------------------------------
	    purpose: as above
		 preconditions: other is not null
		 postconditions: returns true if this fish eats the other fish,
		                 based on the conditions above
		                 returns false otherwise 
	    side effects: when the method returns true, the health of 
		               the other fish is reduced to zero and the health 
							of this fish is increased by previous health of 
							the other. Also, the size of this fish is increased
							by 1/3 (integer division rounded) the size of 
							the other fish.
 	    ------------------------------------------------------------ */
		if(this.size > other.size && this.closeEnough(other)){
			this.health += other.health;
			other.health = 0;
			this.size += other.size / 3;
			return true;
		}
		return false;
    
  }
  
  public Fish mate(Fish other){
    /* If the two fish are close enough and have the same size
     *  (and each have health at least 1) then the fish mate
     *  and create a new baby fish.
     *  The baby fish has
     *     -size that is the average of size of parents
     *     -health that is the average of size of parents
     *     -position that is the average of size of parents
     *     -speed that is random
	   *  Use integer division for integers.
     *  The health of both parents are reduced by 1 when they mate.
     *  Use the hasMated helper method to change the health of the
     *  "other" parent
     * 
     * Otherwise, the method returns null.
	   * ------------------------------------------------------------
	    purpose: as above
	 	  preconditions: other is not null
 		  postcondition: if the conditions above are met returns 
		                 a new fish object as described above
		      					 otherwise, returns null
		  side effects:  if a new fish is created, the health of this
	  	               and other are reduced by 1 when method ends
	    -------------------------------------------------------------
     */
    
	  if(this.closeEnough(other) && this.size == other.size && this.health >= 1 && other.health >= 1){
		  //Mate
		  Fish baby = new Fish(this.size/2, (this.health + other.health) / 2, (this.x + other.x) / 2, (this.y + other.y) / 2);
		  this.health -= 1;
		  other.health -= 1;
		  return baby;
	  }
	
    return null;
  }
  
  
  public void swim(){
    /*
     Update the position of the fish using its speed.
	  
     The fishes position is update by adding the speed
	  to the position (for each coordinate)
	   x += dx
      y += dy
     
	  If the fish does not hit or go through a wall 
	  (of the fish bowl) in this update then do this update
	  and end the method.
	  
	  If the new value of x or y is outside of the walls
	  (less then zero or greater then the width/height)
	  then do not make this simple update. Instead, 
	  you the fish must bounce off of the wall it hits.
     
	  If the fish hits a vertical wall (left or right 
     side of fish bowl) then its x speed is negated and
	  the x coordinate is not changed.
	  
     If the fish hits a horizontal wall (top or bottom)
     then its y speed is negated and the y position is not
	  changed.
     
	  If the fish only hits one wall then the other coordinate
	  will update in the normal way (from above). 
	   
	  Note that this will result in a slightly odd behaviour 
	  when a fish bounces off of a wall. If you want to make the
	  behaviour more smooth you are free to do so.
	   
     The fish must always stay within the fish bowl. 
     Its position must always satisfy 
     0 <= x <= WIDTH and 0 <= y <= HEIGHT
	  
     -------------------------------------------------------------
	  purpose: as above
	  preconditions: fish is alive (health is > 0)
	  postconditions: outputs nothing
	  side effects: as above
	  --------------------------------------------------------------
     */
	  
	if(this.health <= 0){
		return;
	}
    if (x + dx >= Fish.WIDTH || x + dx <= 0){
    	dx *= -1;
    }
    if (y + dy >= Fish.HEIGHT || y + dy <= 0){
    	dy *= -1;
    }
    
    x += dx;
    y += dy;
  }
  
  public boolean closeEnough(Fish other){
    /* returns true if the two fish are "close enough",      */
    /* where close enough is defined in the assignment specs */
	//  System.out.println(Math.sqrt(Math.pow(this.x - other.x,2) + Math.pow(this.y - other.y, 2)));;
    return (Math.sqrt(Math.pow(this.x - other.x,2) + Math.pow(this.y - other.y, 2)) <= this.size + other.size);
  }
  
  
  
  /* simple tests */
  public static void main(String[] args){
    Fish red = new Fish(5, 10, 30,40, 2, -2);
    Fish blue = new Fish(5, 10, 31,39, -3, 0.1);
    Fish b2 = new Fish(16, 3, 300,100, -3, 0.1);
    System.out.println( red.closeEnough(blue) );
    System.out.println( b2.closeEnough(blue) );
    System.out.println(red);
    
    b2.swim();
    System.out.println(b2);
    System.out.println(blue);
    
    red.eat(blue);
    System.out.println(red);
    System.out.println(blue);
    
    Fish b3 = red.mate(blue);
    Fish b4 = red.mate(b2);
   
    System.out.println(red);
    System.out.println(blue);
    System.out.println(b3);
    System.out.println(b4);
    
    
  }
}