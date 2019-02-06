public class Person{
  
  private String firstName;
  private String middleName;
  private String lastName;
  private int age;
  private int height;
  
  public String getName(){
  // purpose: returns the name of the person
  // preconditions: none
  // postconditions: the person's name is returned as a String
  //                 in the following format:
  //               - first (capitalized) name followed by a space, 
  //                 followed by middle initial (capitalized),
  //                 followed by a dot ".",
  //                 followed a space followed by surname (capitalized).
  //               - if the middle name was empty ("") then 
  //                 first name followed by a space followed by surname
  //                 (both capitalized)
  //
  // examples: if the name was (first middle last)
  //  - Cat Eel Dog then it would return "Cat E. Dog"
  //  - Cat Dog (no middle name) then it would return "Cat Dog"
    
    String s = "";
    s += Character.toUpperCase(this.firstName.charAt(0)) + this.firstName.toLowerCase().substring(1) + " ";
    if(!middleName.isEmpty())
    	s+= " " + this.middleName.toUpperCase().charAt(0) + ". ";
    s += Character.toUpperCase(this.lastName.charAt(0)) + this.lastName.toLowerCase().substring(1);
    
    return s;
  }
  
  public void   setFirstName(String first){
   this.firstName = first;
  }

  public void   setMiddleName(String middle){
  this.middleName = middle;
  }
  
  public void   setLastName(String last){
    this.lastName = last;
  }
  

  public int  getHeight(){
   return this.height;
  }
  
  public int[]  getHeightFeetAndInches(){
   // purpose: returns the person's height in feet and inches
   // preconditions: none
   // postconditions: returns an array of length 2
   //                 - first element is the number feet
   //                 - second element is the number of inches.
   // Note: the conversion should be the best possible under the
   //      constraint that feet >= 0, and 0 <= inches < 12
 	 //
   // Note: 1 foot = 12 inches
   //       1 inch = 2.54 cm
   // 
   // examples: if the person's height is 126cm then their height 
   //            is 4 feet and 1.6063 inches.  Thus, the returned 
   //            array will be [4,2] (for 4 feet and 2 inches)
   //           if the person's height is 30cm then their height
	 //            is 0 feet and 11.81102 inches. Thus, the returned 
	 //            array will be [1,0] (for 1 foot)
	 
	int h = this.height;
	int feet;
	int inches;
	
	inches = (int)Math.round((h / (2.54)));
	feet = (int) (inches/12.0);
	
	inches -= 12*feet;
	return new int[] {feet, inches};
  }
  
  public void   setHeight(int cm){
    // sets the current persons height
    // input is given in cm (centimetres)
	  
	  this.height = cm;
  }
  
  public int    getAge(){
    return this.age;
  }
  
  public void   setAge(int years){
    // sets the persons age to the given input (in years)
  }
  
  @Override
  public String toString(){
     // overrides Object's toString() method
     // returns a String in the following format: 
	  //  "SURNAME, Firstname Middlename: age years old, height metres tall."
	  // of if the person has no middle name
     //  "SURNAME, Firstname: age years old, height metres tall."
     // where
	  // - SURNAME is in all uppercase letters
	  // - Firstname and Middlename are capitalized
	  // - age is in years
	  // - height is in metres with exactly 2 decimal places  
     //	  
     // example: the output might look like
     //      "DOG, Cat Eel: 12 years old, 0.23 metres tall."
	 
	  String s = "";
	  
	  s+= this.lastName.toUpperCase() +", " + this.firstName.toUpperCase().charAt(0) + this.firstName.toLowerCase().substring(1) + " ";
	  if(!this.middleName.isEmpty())
		s += this.middleName.toUpperCase().charAt(0) + this.middleName.toLowerCase().substring(1);
	  
	  s += ": " + this.age + " years old, " + (double)this.height/100 + " metres tall.";
	  
	  return s;
  }
  
  public Person(String first, String middle, String surname, int age, int height){
    // constructor that initializes the person's information
    // first - first name
    // middle - middle name
    // surname - surname
    // age - age in years
    // height - height in cm
	  
	  this.firstName = first;
	  this.middleName = middle;
	  this.lastName = surname;
	  this.age = age;
	  this.height = height;
	  
  }

   public Person(String first, String surname, int age, int height){
    // constructor that initializes the person's information 
	// (the person has no middle name)
    // first - first name
    // surname - surname
    // age - age in years
    // height - height in cm
	
	  this.firstName = first;
	  this.middleName = "";
	  this.lastName = surname;
	  this.age = age;
	  this.height = height;
	   
  }

  public Person(){
	// creates the (default) person such that their
	// first name is "Cat", middle name is "Eel"
	// surname is "Dog", age is 99 years and
 	// height is 17 cm
	  
	  this.firstName = "Cat";
	  this.middleName = "Eel";
	  this.lastName = "Dog";
	  this.age = 99;
	  this.height = 17;
  }
  
  public static void main(String[] args){
	  Person p = new Person("CHARLES", "Ernest", "Gooda", 21, 234);
	  System.out.println(p.toString());
	  System.out.println(p.getName());
	  System.out.println(p.getHeightFeetAndInches()[0] + " ," + p.getHeightFeetAndInches()[1]);
  }
}