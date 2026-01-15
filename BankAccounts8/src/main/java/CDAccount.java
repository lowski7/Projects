import java.util.*;
import java.io.*;
public class CDAccount extends Savings {
    
    private Calendar maturitydate;
    
    public CDAccount(Depositor d, int accnum, String acctype, double balance,String date)throws IOException{
        super (d,accnum,acctype,balance);
        
        maturitydate = Calendar.getInstance();
         if(date !=null){
       String[] dateword = date.split("/");    // Turn the String into tokens

        // Parse each part into integers
        int month = Integer.parseInt(dateword[0]) - 1;        // Months Starts with 0 make sure when you get it back, +1
        int day = Integer.parseInt(dateword[1]);
        int year = Integer.parseInt(dateword[2]);

        // Set the date
        this.maturitydate.set(year, month, day); 
        } 
    }
    //copy constructor
    public CDAccount(CDAccount d){
        super(d);
                maturitydate = d.maturitydate;

    }
    
          public Calendar getmaturitydate(){
             return maturitydate;
     }


    
    public String getdate(){
    TranReceipt tr;
        String str;
        str = String.format("%02d/%02d/%4d",
                            maturitydate.get(Calendar.MONTH)+1, //remembered to add 1 since we stored it as -1 earlier
                            maturitydate.get(Calendar.DAY_OF_MONTH),
                            maturitydate.get(Calendar.YEAR)
                            );

        return str;
    }
    
   
    public String toString() {
        Name name = getDepositor().getname();
        String maturityDate = "";
        if (getacctype().equals("CD")) {
             maturityDate = getdate();
        }
        return String.format("%-20s%-22s%-28s%-28d%-22s$%15.2f%15s",
            name.getlast(),
            name.getfirst(),
            getDepositor().getSSN(),
            accnum,  
            acctype,         
            balance,
            maturityDate);
    }
    
    @Override
    public TranReceipt deposit(TranTicket tt,String f, String l)throws Exception{
        double oldBalance = balance;
         TranReceipt tr = new TranReceipt();
       // IF account is a CD and maturity Date is not passed
         if(getstatus()==false){
              tr = new TranReceipt(tt,false,"Error, Account closed unable to perform transaction on a closed account. ", getacctype(),balance,f,l);

                add(tr);
                throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. ");
          }

       if(tt.getAmountOfTransaction()<0){
              tr = new TranReceipt(tt,false,"Error in input, Cannot deposit a negative amount \n Attempted Deposit Amount "
                     +tt.getAmountOfTransaction(), getacctype(),balance,f,l);
 
                add(tr);
                     throw new InvalidAmountException("Error in input, Cannot deposit a negative amount \n Attempted Deposit Amount ");   
                
       }
      
        if (tt.getDateOfTransaction().before(maturitydate) ) {  // Not past maturity Date
            
            
               tr = new TranReceipt(tt, false,                  
             "Error, maturity date not reached, Transaction made before  "+getdate()+"\n Account type: "
                     + getacctype(), getacctype(),maturitydate,null,balance,f,l);
//              tra.add(tr);
//       addTran(tr);
      add(tr);
      throw new MaturityDateException( "Error, maturity date not reached, Transaction made before  "+getdate()+"\n Account type: "
                     + getacctype());
        }
        
        else{
            
        // If account is a valid CD add to maturity Date 
            maturitydate.add(Calendar.MONTH, tt.getcdterm());
        
               // Make the Deposit
        Bank.addCD(tt.getAmountOfTransaction()); //add to static variable
        balance += tt.getAmountOfTransaction();

        // Return a successfull receipt
         tr = new TranReceipt(tt, true,acctype,oldBalance,balance,getmaturitydate(), getdate(),f,l);
//        tra.add(tr); 
             add(tr);

        
}            return new TranReceipt(tr);

    }
//    
    @Override
    public TranReceipt withdraw(TranTicket tt,String f, String l)throws Exception{
         // IF account is a CD and maturity Date is not passed
        double oldbalance = balance;
        TranReceipt tr = new TranReceipt();
         
          if(getstatus()==false){
                             add(tr);
               tr = new TranReceipt(tt,false,"Error, Account closed unable to perform transaction on a closed account. ", getacctype(),balance,f,l);
               throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. ");
//             tra[num] = tr;
//             num++;
          }

         if(tt.getAmountOfTransaction()<0){
              tr = new TranReceipt(tt,false,"Error in input, Cannot withdraw a negative amount \n Attempted withdraw Amount "
                     +tt.getAmountOfTransaction(), getacctype(),balance,f,l);
                         
                               add(tr);
                         System.out.println("boom");   
                         //throw exception
                   throw new InvalidAmountException("Error in input, Cannot withdraw a negative amount \n Attempted withdraw Amount ");
//            return new TranReceipt(tr);
       }
         
          if(tt.getAmountOfTransaction()>balance){
            tr = new TranReceipt(tt,false,"Error in input, insufficient funds "
                   , getacctype(),balance,f,l);
 
                add(tr);
                throw new InsufficientFundsException("Error in input, insufficient funds, Cannot withdraw more than "+ balance);
                                 

             
         }
         if ( tt.getDateOfTransaction().before(maturitydate) ) {  // Not past maturity Date
              // Concatenated Error String
               tr = new TranReceipt(tt, false,                  
             "Error, maturity date not reached, Transaction made before  "+getdate()+
                     "\n Account type: "+ getacctype(), getacctype(),maturitydate,null,balance,f,l);
              add(tr);
              throw new MaturityDateException("Error, maturity date not reached, Transaction made before  "+getdate()+
                     "\n Account type: "+ getacctype());
       
        }
         
         else{
             
             
                 //subtract money from the total
                 Bank.addCD(tt.getAmountOfTransaction()*-1);
                 maturitydate.add(Calendar.MONTH, tt.getcdterm());
             balance -= tt.getAmountOfTransaction(); 
             // Return a successfull receipt
        tr = new TranReceipt(tt, true,acctype,oldbalance,balance,getmaturitydate(), getdate(),f,l);
         }
        add(tr);
         
                return new TranReceipt(tr);
         
         
     }
    @Override
    public String dor(){
    Name name = getDepositor().getname();

    String st = String.format("%-15s%-15s%-15s%-15d%-15s%-15.2f%-15s",
            name.getlast(),
            name.getfirst(),
            getDepositor().getSSN(),
            accnum,  
            acctype,         
            balance,
            getdate());
    return st;
    
}
    
    }
    
    
    

