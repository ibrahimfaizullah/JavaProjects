public class CashRegister{
  /* these static attributes keep a count of how many    */
  /* times the updateMoney and allLoonies methods        */
  /* are called (do not use these!)                      */
  public static int updateMoneyCount = 0;
  public static int allLooniesCount  = 0;
    
  private Money registerMoney;
  private static final int[] billValues = {1, 5, 10, 20, 50};
  /* attributes                                */
  /* you need to define your own attributes.   */
  /* they should be private                    */
  
  /* constructors */
  /* different ways of specifying the money amount */
  public CashRegister(){
    /* creates a register with zero money */
	  registerMoney = new Money(0,0,0,0,0);
  }
  
  
  public CashRegister(int[] money){
    /* creates a register with money specified in money array [loonies, 5s, 10s, 20s, 50s] */
	  registerMoney = new Money(money[0], money[1], money[2], money[3], money[4]);
  }
  
  public CashRegister(Money money){
    /* creates a register with the money specified in the money object  */  
	  registerMoney = money;
  }
  
  public CashRegister(int n1, int n5, int n10, int n20, int n50){
   /* creates  aregster withspecified loonies n1, fives n5, tens n10 , twenties n20, fifties n50*/
	  registerMoney = new Money(n1, n5, n10, n20, n50);
  }
  
 
  /* ------------------------------------------------------------- 
   *   getters 
   * ------------------------------------------------------------- */
	  
  /* returns number of loonies in the register */
  public int get1(){return registerMoney.d1;}
  
  /* returns number of five dollar bills in the register */
  public int get5(){return registerMoney.d5;}

  /* returns number of ten dollar bills in the register */
  public int get10(){return registerMoney.d10;}
 
  /* returns number of twenty dollar bills in the register */
 public int get20(){return registerMoney.d20;}
 
   /* returns number of fifty dollar bills in the register */
  public int get50(){return registerMoney.d50;}
  
    /* returns total value of all money in register      */
  public int getTotalValue(){
	  return (registerMoney.d1 + 5*registerMoney.d5 + 10*registerMoney.d10 + 20*registerMoney.d20 + 50*registerMoney.d50);
  }
  
  /* returns all money in register as an array [loonies, 5s, 10s, 20s, 50s]   */
  public int[] getAll(){return new int[]{registerMoney.d1, registerMoney.d5, registerMoney.d10, registerMoney.d20, registerMoney.d50};}
  

  /* returns Money object that corresponds to all money  in the register */
  public Money getMoney(){ return registerMoney; }
  


  
  /* ------------------------------------------------------------- 
   *   methods 
   * ------------------------------------------------------------- */
  
  
  protected Money purchaseItem(Item item, Money payment){
    /*-----------------------------------------------------------
     * process a purchase transaction
     * 
     * preconditions: -item and payment are both non-null
     * 
     * postconditions: -if the payment was not enough for the 
     *                  purchase returns null
     *                 -if payment was enough then returns a money
     *                  object with the change given (might be zero)
     * 
     * side effects: -if payment was enough, the money in the
     *                cash register is updated with the price
     *                of the transaction
     *               -if when making change, the cash register is 
     *                unable to make proper change, it will call
     *                the updateMoney() method to modify its money distribution.
	   *                If it is still unable to make exact change, then 
	   *                the allLoonies() method is called.
     *-------------------------------------------------------------------
     */
    
    int price = item.getPrice();
    int expectedChange = CashRegister.getMoneyValue(payment) - price;
    int bills[] = new int[5];
    int registerBills[];
    int tries = 0;
    int whatweget = 0;
    if(expectedChange < 0){
    	System.out.println("less than 0? " + expectedChange);
    	return null;
    }
    
    this.registerMoney = new Money(this.registerMoney.d1+payment.d1, this.registerMoney.d5+payment.d5, 
    		this.registerMoney.d10+payment.d10, this.registerMoney.d20+payment.d20, this.registerMoney.d50+payment.d50);
    
    while(true){
    	expectedChange = CashRegister.getMoneyValue(payment) - price;
        bills = new int[5];
        registerBills = this.getAll();
        whatweget = 0;
	    for(int i = bills.length-1; i >= 0; i-=1){
	    	while(true){
	    		if(expectedChange > 0 && registerBills[i] > 0 && expectedChange - billValues[i] >= 0){
	    			registerBills[i] -= 1;
	    			expectedChange -= billValues[i];
	    			whatweget += billValues[i];
	    			bills[i] += 1;
	    			continue;
	    		}
	    		break;
	    	}
	    }
	    
	    if(CashRegister.getMoneyValue(bills) != (CashRegister.getMoneyValue(payment) - price)){
	    	if(tries == 0){
	    		//Try 1. Update money 
	    		tries++;
	    		System.out.println("Wut." + CashRegister.getMoneyValue(bills));
	    		System.out.println("Error #1. Old state: " + this);
	    		this.updateMoney();
	    		System.out.println("Error #1. New state: " + this);
	    	}
	    	
	    	else if(tries == 1){
	    		//Try 2. All loonies
	    		System.out.println(whatweget);
	    		tries++;
	    		this.allLoonies();
	    	}
	    	
	    	else{
	    		System.out.println("Error #3. Final state: " + this);
	    		//Try 3. Something has went wrong.
	    		return null;
	    	}
	    }
	    
	    else{
	    	break; //Successful change was made
	    }
	    
    }

	this.registerMoney = new Money(registerBills[0], registerBills[1], registerBills[2], registerBills[3],registerBills[4]);
    Money change = new Money(bills[0], bills[1], bills[2], bills[3], bills[4]);
	
	return change;
  }
  
  
  
  protected Money returnItem(Item item){
    /*-----------------------------------------------------------
     * return an item (giving money back) 
     * 
     * preconditions: -item is non-null
     * 
     * postconditions: -if the register has enough money to give back 
	   *                  for the item then that value is returned as
	   *                  a money object. (The money object corresponds to 
	   *                  the actual number of loonies/bill given back.)
	   *                 -otherwise, returns null.
	   * 
     * side effects: -if the register has enough money but cannot give 
	   *                this amount exactly, it calls the updateMoney() 
	   *                method to try to give the exact value. 
	   *                If this also fails then the allLoonies() method
	   *                is called.
	   *               -the amount of money in the register after the method
	   *                is reduced by the price if the register was able to 
	   *                give this value.
	   *-------------------------------------------------------------------
     */
	  int price = item.getPrice();
	  int bills[] = new int[5];
	  int registerBills[];
	  int tries = 0;
	  if(this.getTotalValue() < price){
	  	return null;
	  }
	  while(true){
		    price = item.getPrice();
	        bills = new int[5];
	        registerBills = this.getAll();
		    for(int i = bills.length-1; i >= 0; i-=1){
		    	while(true){
		    		if(price > 0 && registerBills[i] > 0 && price - billValues[i] >= 0){
		    			registerBills[i] -= 1;
		    			price -= billValues[i];
		    			bills[i] += 1;
		    			continue;
		    		}
		    		break;
		    	}
		    }
		    
		    if(CashRegister.getMoneyValue(bills) != item.getPrice()){
		    	if(tries == 0){
		    		//Try 1. Update money 
		    		tries++;
		    		this.updateMoney();
		    	}
		    	
		    	else if(tries == 1){
		    		//Try 2. All loonies
		    		tries++;
		    		this.allLoonies();
		    	}
		    	
		    	else{
		    		//Try 3. Something has went wrong.
		    		return null;
		    	}
		    }
		    
		    else{
		    	break; //Successful change was made
		    }
		    
	    }

		this.registerMoney = new Money(registerBills[0], registerBills[1], registerBills[2], registerBills[3],registerBills[4]);
	    Money change = new Money(bills[0], bills[1], bills[2], bills[3], bills[4]);
		
		return change;
  }
  
  
  protected CashRegister updateMoney(){ 
    /*----------------------------------------------------
     * Purpose is to change the distribution of loonies/bills
	   * while keeping the total value the same.
	   * For example, 10 loonies might be exchanged for 2 five 
	   * dollar bills.
     * 
	   * preconditions - none
	   * postconditions - returns itself (this)
	   * side effects - the distribution of loonies/bills is possibly 
	   *                changed in some way (it may not change) while
	   *                the total value remains the same
     *-----------------------------------
     */
    
    /* DO NOT CHANGE THIS LINE */
	 updateMoneyCount += 1;
    
    
    /*----------------------------------------------------
     * add your code below this comment block                                  
     *--------------------------------------------------- */
 
	 boolean changedDown = false;
	 int[] bills = this.getAll();
	 //$50 change movement
	 if(bills[4] >= 1){
		 bills[4] = bills[4] - 1;
		 bills[3] = bills[3] + 1; //$20
		 bills[2] = bills[2] + 1; //$10
		 bills[1] = bills[1] + 2; //$5 * 2 = $10
		 bills[0] = bills[0] + 10; //$1 * 10 = $10
		 changedDown = true;
	 }
	 //$20 change movement
	 else if(bills[3] >= 1){
		 bills[3] = bills[3] - 1;
		 bills[2] = bills[2] + 1; //$10
		 bills[1] = bills[1] + 1; //$5 * 2 = $10
		 bills[0] = bills[0] + 5; //$1 * 10 = $10
		 changedDown = true;
	 }

	//$10 change movement
	 else if(bills[2] >= 1){
		 bills[2] = bills[2] - 1;
		 bills[1] = bills[1] + 1; //$5
		 bills[0] = bills[0] + 5; //$1 * 5 = $5
		 changedDown = true;
	 }
	 //$5 change movement
	 else if(bills[1] >= 1){
		 bills[1] = bills[1] - 1;
		 bills[0] = bills[0] + 5; //$1 * 5 = $5
		 changedDown = true;
	 }


	 if(!changedDown){
		 if(bills[0] >= 50){
			 bills[0] = bills[0] - 50;
			 bills[3] = bills[3] + 1; //$20
			 bills[2] = bills[2] + 1; //$10
			 bills[1] = bills[1] + 2; //2*$5 = $10
			 
		 }
		 //$20 change movement
		 else if(bills[0] >= 20){
			 bills[0] = bills[0] - 20;
			 bills[2] = bills[2] + 1; //$10
			 bills[1] = bills[1] + 2; //2*$5 = $10
		 }

		//$10 change movement
		 else if(bills[0] >= 10){
			 bills[0] = bills[0] - 10;
			 bills[1] = bills[1] + 2; //2*$5 = $10
		 }
		 //$5 change movement
		 else if(bills[0] >= 5){
			 bills[0] = bills[0] - 5;
			 bills[1] = bills[1] + 1; //$1 * 5 = $5
		 }
	 }
	 
	this.registerMoney = new Money(bills[0], bills[1], bills[2], bills[3], bills[4]);
	 
    /* DO NOT CHANGE THIS LINE */ 
    /* your method must return this */
    return this;
  }

  //was used for debugging
/*
 public String toString(){
	 return this.registerMoney.toString();
 }

 */ 
  public CashRegister allLoonies(){
    /*--------------------------------------------------------------------
     * Purpose is to change all bills in the register to loonies.
	 *
	 * preconditions - none
	 * postconditions - returns itself (this)
	 * side effects - all money in the register is changed to loonies
	 *                while the total value remains the same
     *------------------------------------------------------------------
     */
    
    /* DO NOT CHANGE THIS LINE */
	 allLooniesCount += 1;
    
    
    /*----------------------------------------------------
     * add your code below this comment block                            
     *--------------------------------------------------- */
 
	 int[] bills = this.getAll();
	 bills[0] += bills[1]*5 + bills[2]*10 + bills[3]*20 + bills[4]*50;
	 bills[1] = 0;
	 bills[2] = 0;
	 bills[3] = 0;
	 bills[4] = 0;
	 this.registerMoney = new Money(bills[0], bills[1], bills[2], bills[3], bills[4]);

    /* DO NOT CHANGE THIS LINE */ 
    /* your method must return this */
    return this;
  }
  
  private static int getMoneyValue(Money m){
	  return m.d1 + m.d5*5 + m.d10*10 + m.d20*20 + m.d50*50; 
  }
  
  private static int getMoneyValue(int[] m){
	  return m[0] + m[1]*5 + m[2]*10 + m[3]*20 + m[4]*50; 
  }
  
  //Uncomment to test the program
  /*
  public static void main(String[] args){
	  Item gum = new Item("Gum", 1);// (something to buy/return, expensive gum!)
	  CashRegister cash = new CashRegister(1,1,1,1,1); //(cash register with no money)
	  System.out.println(cash.get20());
	  System.out.println("Change: " + cash.purchaseItem(gum, new Money(0,0,0,0,4)));
	  System.out.println(cash.getTotalValue());
	  System.out.println(cash.get1());
	  System.out.println(cash.get5());
	  System.out.println(cash.allLoonies().get1());
	  System.out.println(cash.get5());
  }
  */
}
