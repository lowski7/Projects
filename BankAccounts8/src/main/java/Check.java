import java.util.*;
public class Check {
    
       
    public Check(){
        
    }
    
    private int accnum;
    private double checkamount;
    private Calendar checkdate;
    
    // Constructor
    public Check(int accnum, double checkAmount, String date){    
        this.accnum = accnum;
        this.checkamount = checkAmount;
        
        // Turn the date of check string into an actual calendar object
       checkdate = Calendar.getInstance();
      checkdate.clear();
        
        String[] dateword = date.split("/");

        // Parse each part into integers
        int month = Integer.parseInt(dateword[0]) - 1;        //minus 1, remember to add it later
        int day = Integer.parseInt(dateword[1]);
        int year = Integer.parseInt(dateword[2]);

        // Set the date
        checkdate.set(year, month, day);
    }

    //copy constructor
    public Check(Check check){
        accnum = check.accnum;
        checkamount = check.checkamount;
        checkdate = check.checkdate;
                }
    // Access Functions
    public int getaccnum() {
        return accnum;
    }
    public double getcheckamount() {
        return checkamount;
    }
    public Calendar getcheckdate() {
        return checkdate;
    }
    
    public String toString() {
        return "Check: Account #" + accnum + 
               ", Amount: $" + String.format("%.2f", checkamount) + 
               ", Date: " + TranTicket.toString(checkdate);
    }
    
    
}

