import java.io.*;
public class Savings extends Account {
    
    
    public Savings(Depositor d, int accnum, String acctype, double balance)throws IOException{
        
        super(d, accnum, acctype, balance);
        
        
    }
    public Savings (Savings s){
        super(s);
    }
    
        @Override
     public TranReceipt deposit(TranTicket tt, String f, String l)throws Exception{
       TranReceipt tr = new TranReceipt();
       // IF account is a CD and maturity Date is not passed
        double oldBalance = balance;
         if(getstatus()==false){
              tr = new TranReceipt(tt,false,"Error, Account closed unable to perform transaction on a closed account. ", getacctype(),balance,f,l);

                add(tr);
               throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. ");
          }

       if(tt.getAmountOfTransaction()<0){
              tr = new TranReceipt(tt,false,"Error in input, Cannot deposit a negative amount \n Attempted Deposit Amount "
                     +tt.getAmountOfTransaction(), getacctype(),balance,f,l);
 
                add(tr);
                   throw new InvalidAmountException("Error in input, Cannot deposit a negative amount\n Attempted Deposit Amount ");
                
       }
       else
      
                Bank.addtotalsavings(tt.getAmountOfTransaction());
            
        // Make the Deposit
        balance += tt.getAmountOfTransaction();

        // Return a successfull receipt
        tr = new TranReceipt(tt, true,acctype,oldBalance,balance,null,null,f,l);
        
                add(tr);
        
            return new TranReceipt(tr);
       
       
       
 }
     @Override
      public TranReceipt withdraw(TranTicket tt, String f, String l)throws Exception{
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
          else
          Bank.addtotalsavings(-tt.getAmountOfTransaction());
          balance-=tt.getAmountOfTransaction();
                // Return a successfull receipt
         tr = new TranReceipt(tt, true,acctype,oldbalance,balance,null,null,f,l);

                add(tr);
        
                return new TranReceipt(tr);
}
      
      public String dor(){
    Name name = getDepositor().getname();
        String maturityDate = " ";
        
    String st = String.format("%-15s%-15s%-15s%-15d%-15s%-15.2f%-15s",
            name.getlast(),
            name.getfirst(),
            getDepositor().getSSN(),
            accnum,  
            acctype,         
            balance,
            maturityDate);
    return st;
    
}
}

