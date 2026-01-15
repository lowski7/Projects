 import java.io.*;
import java.util.*;
public class Checking extends Account {
   
     public Checking(Depositor d, int accnum, String acctype, double balance)throws IOException{
       
        super(d, accnum, acctype, balance);
        
        
     }
     
     //copy constructor
     public Checking(Checking c){
         super(c);
     }
     
     
     @Override
      public TranReceipt deposit(TranTicket tt, String f, String l) throws Exception{

       // IF account is a CD and maturity Date is not passed
        double oldBalance = balance;
        TranReceipt tr = new TranReceipt();
         
          if(getstatus()==false){
                             add(tr);
               tr = new TranReceipt(tt,false,"Error, Account closed unable to perform transaction on a closed account. ", getacctype(),balance,f,l);
               throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. ");
//             tra[num] = tr;
//             num++;
          }

       if(tt.getAmountOfTransaction()<0){
              tr = new TranReceipt(tt,false,"Error in input, Cannot deposit a negative amount \n Attempted Deposit Amount "
                     +tt.getAmountOfTransaction(), getacctype(),balance,f,l);
                         
                               add(tr);
                         System.out.println("boom");   
                         //throw exception
                   throw new InvalidAmountException("Error in input, Cannot deposit a negative amount \n Attempted Deposit Amount "
                     +tt.getAmountOfTransaction());
//            return new TranReceipt(tr);
       }
       
            
        // Make the Deposit
        balance += tt.getAmountOfTransaction();

        // Return a successfull receipt
        
         tr = new TranReceipt(tt, true,acctype,oldBalance,balance,null,null,f,l);
       
 
                add(tr);
        
            return new TranReceipt(tr);
       
       
       
 }
//     
     @Override
     public TranReceipt withdraw(TranTicket tt,String f, String l)throws Exception {
             // IF account is a CD and maturity Date is not passed
        double oldbalance = balance;
        TranReceipt tr = new TranReceipt();
         
          if(getstatus()==false){
                             add(tr);
               tr = new TranReceipt(tt,false,"Error, Account closed unable to perform transaction on a closed account. ", getacctype(),balance,f,l);
                           
               throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. ");
            
          }

         if(tt.getAmountOfTransaction()<0){
              tr = new TranReceipt(tt,false,"Error in input, Cannot withdraw a negative amount \n Attempted withdraw Amount "
                     +tt.getAmountOfTransaction(), getacctype(),balance,f,l);
                         
                               add(tr);
                         System.out.println("boom");   
                         //throw exception
                   throw new InvalidAmountException("Error in input, Cannot withdraw a negative amount \n Attempted withdraw Amount "
                     +tt.getAmountOfTransaction());
//            return new TranReceipt(tr);
       }
         
          if(tt.getAmountOfTransaction()>balance){
            tr = new TranReceipt(tt,false,"Error in input, insufficient funds "
                   , getacctype(),balance,f,l);

                add(tr);
                throw new InsufficientFundsException("Error in input, insufficient funds ");
                                 

             
         }
          else
          Bank.addtotalchecking(-tt.getAmountOfTransaction());
          balance-=tt.getAmountOfTransaction();
                // Return a successfull receipt
         tr = new TranReceipt(tt, true,acctype,oldbalance,balance,null,null,f,l);
 
                add(tr);
        
                return new TranReceipt(tr);
         
    }
     
    /**
     *
     * @param check
     * @param f
     * @param l
     * @return
     */
//   @Override
      public TranReceipt clearcheck(Check check,String f, String l)throws Exception{
          TranReceipt tr = new TranReceipt();
          System.out.println("yes god");
          Double oldbalance=balance;
          if(getstatus()==false){
               tr = new TranReceipt(null,false,"Error, Account closed unable to perform transaction on a closed account. \nAccount number:  "+accnum, getacctype(),balance,f,l);
             add(tr);
             throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. \nAccount number:  "+accnum);
          }
                   if(check.getcheckamount()<0==true){
                          tr = new TranReceipt(null,false,"Error in input, Check Amount can not be negative \n Account number: "+accnum,getacctype(),balance,f,l);
//                         tra.add(tr);
                         throw new InvalidAmountException("Error in input, Check Amount can not be negative \n Account number: "+accnum);
                     }
                   
                   
                     Calendar today = Calendar.getInstance();
                     if(check.getcheckdate().after(today)){
                          tr =  new TranReceipt(null,false,"Error in input: Post Dated check. \n Unable to clear check due to it being a future date\nAccount number: "+accnum,
                         
                                 null, balance,f,l);
//                         tra.add(tr);
                         throw new PostDatedCheckException("Error in input: Post Dated check. \n Unable to clear check due to it being a future date\nAccount number: "+accnum);
                         
                         
                     }
                     today.add(Calendar.MONTH, -6);
                     if(check.getcheckdate().before(today)){
                     tr = new TranReceipt(null,false," Check too old \n Unable to clear check due to it being past 6 months\nAccount number "+accnum,null,balance,f,l);
//                    tra.add(tr);    
                         throw new CheckTooOldException(" Check too old \n Unable to clear check due to it being past 6 months\nAccount number "+accnum);
                     }

                 if(check.getcheckamount()>balance){
                                                          balance-=2.50;
                                                          double r = oldbalance-2.50;
                    tr = new TranReceipt(null,false,"Error in input: Insufficient funds,\n cannot make a transaction more than "+oldbalance+" 2.50 penalty reducted from your account due to bouncing check \nAccount number: "+accnum
                           + "\n old balance: "+oldbalance+"\n new balance:  "+r,null,balance,f,l);
//                   tra.add(tr);
                   throw new InsufficientFundsException("Error in input: Insufficient funds,\n cannot make a transaction more than "+oldbalance+" 2.50 penalty reducted from your account due to bouncing check \nAccount number: "+accnum
                           + "\n old balance: "+oldbalance+"\n new balance:  "+r);

                     
    }     
                 //-1.50 if balance less than 2500
                 if(balance<2500){
              balance-=1.50;
          }
                 
                     Bank.addtotalchecking(check.getcheckamount()*-1);
                     oldbalance =balance;
                 balance-=check.getcheckamount();
                 System.out.println("boom"+balance);
                  tr = new TranReceipt(true,acctype,oldbalance,balance,null,f,l,"Account Number: "+ accnum+"\n");
//               add(tr);
                         return new TranReceipt (tr,"CD");
         
     }
      public String dor(){
    Name name = getDepositor().getname();
        String maturityDate = " ";
        
    String st = String.format("%-15s%-15s%-15s%-15d%-15s%-15.2f%-15s",name.getlast(),
            name.getfirst(),
            getDepositor().getSSN(),
            accnum,  
            acctype,         
            balance,
            maturityDate);
    return st;
    
}
     }
    

