import java.io.*;
import java.util.*;
public class Account implements Serializable{
    public Account(){
        
    }
    protected int size = 190;
  protected Depositor depositor;
      protected int accnum;
     protected String acctype;
     protected double balance;
//     protected TranReceipt[] tra;
     protected boolean status;
     protected RandomAccessFile bfile;
     protected int num;

     public Account(Depositor depositor, int accnum,String acctype,double balance)throws IOException{
         this.depositor = depositor;
         this.accnum = accnum;
         this.acctype = acctype;
         this.balance=balance;
         bfile = new RandomAccessFile(depositor.getname().getfirst()+accnum+".dat","rw");
        status=true;
        num=num;
//        bfile.setLength(0);
//     tra = new TranReceipt[50];
     
     
 }

     //copy constructor
    public Account(Account account){
        //use depositor copy constructor for an aggregate class
        depositor = new Depositor(account.depositor);
        
        //copies for all field objects
        this.balance = account.balance;
        acctype = account.acctype;
        accnum = account.accnum;
        status = account.status;
        bfile=account.bfile;
      num=account.num;
        status=true;

        
    }
// add method adds the the transction receipt to the corresponding account's binary file.
    //tra is the array of transaction reciepts

public TranReceipt balance(TranTicket ticket,String f, String l)throws Exception{
         
         TranReceipt tr = new TranReceipt();
         
          if(getstatus()==false){

//              tr = new TranReceipt(ticket,false,"Error, Account closed unable to perform transaction on a closed account. ", getacctype(),balance,f,l);
//                                           add(tr); 
              throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. ");
           
          }

    
          else{
        tr = new TranReceipt(ticket, true, getacctype(), getbalance(),getbalance(),null, null,f,l);

//                add(tr);
                             

          }
                          add(tr);
                 return new TranReceipt(tr);

}    
//
//
   public TranReceipt deposit(TranTicket tt, String f, String l)throws Exception{

       // IF account is a CD and maturity Date is not passed
        double oldBalance = balance;
        TranReceipt tr = new TranReceipt();
          if(getstatus()==false){
//                             add(tr);
               throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. ");

          }

       if(tt.getAmountOfTransaction()<0){
                          
//                               add(tr);
                         System.out.println("boom");   
                         //throw exception
                   throw new InvalidAmountException("Error in input, Cannot deposit a negative amount Attempted Deposit Amount ");
//            return new TranReceipt(tr);
       }
       
            else{
                Bank.addtotalsavings(tt.getAmountOfTransaction());
            
        // Make the Deposit
        balance += tt.getAmountOfTransaction();

        // Return a successfull receipt
        
         tr = new TranReceipt(tt, true,acctype,oldBalance,balance,null,null,f,l);
       
                
       }
                                      add(tr);

            return new TranReceipt(tr);
       
       
       
 }
// 
    public TranReceipt withdraw(TranTicket tt, String f, String l)throws Exception{
       // IF account is a CD and maturity Date is not passed
        double oldbalance = balance;
        TranReceipt tr = new TranReceipt();
         
          if(getstatus()==false){
//                             add(tr);
               throw new AccountCloseException("Error, Account closed unable to perform transaction on a closed account. ");
//             tra[num] = tr;
//             num++;
          }

         if(tt.getAmountOfTransaction()<0){
                         
//                               add(tr);
                         System.out.println("boom");   
                         //throw exception
                   throw new InvalidAmountException("Error in input, Cannot withdraw a negative amount Attempted withdraw Amount ");
//            return new TranReceipt(tr);
       }
         
          if(tt.getAmountOfTransaction()>balance){
            
//                add(tr);
                throw new InsufficientFundsException("Error in input, insufficient funds, Cannot withdraw more than "+ balance);
                                 

             
         }
          else{
          Bank.addtotalsavings(-tt.getAmountOfTransaction());
          balance-=tt.getAmountOfTransaction();
                // Return a successfull receipt
                
         tr = new TranReceipt(tt, true,acctype,oldbalance,balance,null,null,f,l);
          }          

//                add(tr);
        
                return new TranReceipt(tr);
         
    }
//    
 public TranReceipt open(TranTicket tt, String f, String l)throws Exception{
TranReceipt tr = new TranReceipt();

         if(getstatus()==true){
//   tr = new TranReceipt(tt,false,"Error, Account open: unable to open an already open account.\nAccount owner: "+ getfirst()+ " "+ getLast(),
//                                   getacctype(),balance,f,l);


//                add(tr);
                throw new AccountAlreadyOpenException("Error, Account open: unable to open an already open account.\nAccount owner: "+ getfirst()+ " "+ getLast());
          }

          else{
             
             status = true;
               tr = new TranReceipt(tt, true,acctype,balance,balance, null,f,l,"opened",1);
//               add(tr);
         }

                add(tr);
                return tr;
          

         
     }
     public TranReceipt close(TranTicket tt, String f, String l)throws Exception{
         TranReceipt tr = new TranReceipt();
         
         if(getstatus()==false){
//               tr = new TranReceipt(tt,false,"Error, Account closed: unable to close an already closed account.\nAccount owner: "+ getfirst()+ " "+ getLast(), getacctype(),balance,f,l);
                throw new AccountAlreadyClosedException("Error, Account closed: unable to close an already closed account.\nAccount owner: "+ getfirst()+ " "+ getLast());
          }
         else{
             status = false; //true,acctype,oldbalance,balance,null,f,l,"Account Number: "+ accnum+"\n"
              tr = new TranReceipt(tt,true,acctype,balance,balance,null,f,l,"closed",2);
         
         }
         
                add(tr);
              return tr;
         

     }   
//         
         
        public Depositor getDepositor(){
               
         return new Depositor(depositor);
     }
      public int  getaccnum(){
          int accnumc = accnum;
         return  accnum;
     }
      

    public boolean getstatus() {
    	return status;
    }

     public String  getacctype(){
         return  acctype;
     }
     
     public  double getbalance(){
         return balance;
         
     }
     
     public RandomAccessFile getfile()throws Exception{
         return bfile;
     }
     
     public String getTRA() throws Exception {
//    bfile.seek(95*u); // start at the beginning of the file
    StringBuilder sb = new StringBuilder();

        
         
         for (int i = 0; i < 95*4; i++) {
             
         
// 95 characters per receipt
            char c = bfile.readChar(); // readChar because you used writeChars()
            sb.append(c);
         }
         
    

    return sb.toString(); // return full file content as a string
}

     public int numberofreceipts(){
         return num;
     }
       public void addTran(TranReceipt receipt) {
           
//        tra.add(receipt);
    }
        public String getfirst() {
            
        return depositor.getname().getfirst();
        }
        
       
        
        
     public void add(TranReceipt tr)throws Exception{
         bfile.seek(bfile.length());
         bfile.writeChars(tr.dor());
         num++;
         
        
     }   
     
          
     
    public String getLast() {
    return depositor.getname().getlast();
    }
    public String getSSN() {
        return depositor.getSSN();
    }
    
   
    
    // toString method 
//    @Override
    public String toString() {
        Name name = getDepositor().getname();
        String maturityDate = "";
//        if (getacctype().equals("CD")) {
//             maturityDate = getdate();
//        }
        return String.format("%-20s%-22s%-28s%-28d%-22s$%15.2f%15s",
            name.getlast(),
            name.getfirst(),
            getDepositor().getSSN(),
            accnum,  
            acctype,         
            balance,
            maturityDate);
    }
    
   // .equals() method
    
    public boolean equals(Account account){
        if (this.accnum == account.accnum) 
            return true;
        return false;
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
            "   ");
    return st;
    
}
    
    
}

