//#8
import java.util.*;
import java.io.*;
public class Bank  {
    
  private int size=210;
  private int numaccts;
//  private Account[] account;
   private static double totalSavings;
    private static double totalCheckings;
    private static double totalCD;
    private static double AllAcctstotal;
    private RandomAccessFile bfile;
    
   public Bank()throws IOException{
//        this.account = new ArrayList<>();
        numaccts=0;
        totalSavings=0;
        totalCheckings=0;
        totalCD=0;
        AllAcctstotal= totalCD+totalCheckings+totalSavings;
//        account = new Account[50];
        bfile = new RandomAccessFile("BankAccounts.dat", "rw");
        bfile.setLength(0);
        
    }
   
         
   public int getnumaccts() {
       return numaccts;
   }
    public Account getaccount(int x) throws Exception{         //"%-10s%-10s%-10s%-6d%-10s%-8.2f%-10s
        bfile.seek(size*x);
        char[] lev = new char[105];
        for (int i = 0; i < 105; i++) {
            
            lev[i]= bfile.readChar();
        }
        String acs = String.valueOf(lev);
        String l = acs.substring(0,15).trim();
        String f = acs.substring(15,30).trim();
        String ssn = acs.substring(30,45).trim();
        int accnum = Integer.parseInt(acs.substring(45,60).trim());
        String type = acs.substring(60,75).trim();
        double b = Double.parseDouble(acs.substring(75,90).trim());
        String md = acs.substring(90,105).trim();
        
        Depositor d = new Depositor (new Name(f,l),ssn);
        Account ac;
        if(type.equals("CD")){
             //copy constructor
    CDAccount c = new CDAccount(d,accnum,type,b,md);
    return c;
        }
      else  if(type.equalsIgnoreCase("Checking")){
             //copy constructor
    Checking ca = new Checking(d,accnum,type,b);
    return ca;
        }
      else{
        
                  Savings sa = new Savings(d,accnum,type,b);
                  return sa;
      }
        
    } 
    public TranReceipt[] getTransactionHistory(int accnum) throws Exception {
    TranReceipt[] receipts = new TranReceipt[100]; // You can resize later if needed
    int index = findacct(accnum);
    
    if (index == -1) {
        return null; // Account not found
    }

    Account account = getaccount(index);
    RandomAccessFile tfile = account.getfile();

    
            String record = account.getTRA();
           int numofrecords = record.length()/95;
        for (int x = 0; x <4; x++) {
            
        
    int i = 0;
    int r= 95 * x; // 95 chars * 2 bytes each

    //"%-10s%-15s%-15.2f%-10s%-15.2f%-30s",
      
        
//for (int x = 0; x < numRecords; x++) {
//     record = records.substring(x * 95, (x + 1) * 95);
//    // parse record into TranReceipt...
//TranTicket tt,boolean indicator, String reason,String acctype,double balance,String f, String l
        
        // Parse fields
        String date   = record.substring(r+0, r+10).trim();
        String type   = record.substring(r+10, r+25).trim();
        double amount = Double.parseDouble(record.substring(r+25, r+40).trim());
        String status = record.substring(r+40, r+50).trim();
        double balance = Double.parseDouble(record.substring(r+50, r+65).trim());
        String reason = record.substring(r+65, r+95).trim();

        // Construct TranTicket and TranReceipt
        TranTicket ticket = new TranTicket(accnum, type, amount, null, 0); // Assumes null date, term 0

        boolean success = !status.equalsIgnoreCase("Failed");
        TranReceipt receipt = new TranReceipt(ticket, success, reason, null, balance, null, null);
        receipts[x] = receipt;
    
        }
    return receipts;
}

//    public TranReceipt[] getTransactionHistory(int accnum) throws Exception {
//        //String as = String.format("%10s%15s%8.2f%10s%8.2f%30s",
//              //  TranTicket.toString(today),
//                //getTT().getTypeOfTransaction(),
//          //      getTT().getAmountOfTransaction(), 
//            //    status, 
//              //  getnewbalance(), 
//                //getreason());
//
//        TranReceipt[] r = new TranReceipt[100];
//    int index = findacct(accnum);
//    if (index == -1) {
//        return null; // Account not found
//    }
//       String trs = getaccount(index).getTRA();
//
//    
//    RandomAccessFile tfile = getaccount(index).getfile();
//        for (int i = 0; i < getaccount(index).numberofreceipts(); i++) {
//            
//        
//
//    int s = 95*i;
//   String date = trs.substring(0+s, s+10).trim();
//   String type = trs.substring(s+10,s+35).trim();
//   double amount = Double.parseDouble(trs.substring(s+35,s+50).trim());
//   String status = trs.substring(s+50, s+60);
//   double b = Double.parseDouble(trs.substring(s+60,s+75));
//   String reason = trs.substring(s+75);
//   //int accnum, String typeOfTransaction, double amountOfTransaction, Calendar date, int cdterm
//   TranTicket tt = new TranTicket(accnum,type,amount,null,0); 
//   //TranTicket tt,boolean indicator, String reason,String acctype,double balance,String f, String l
//   boolean u;
//   if(status.equalsIgnoreCase("Failed")){
//       u=false;
//   }
//   else{
//u=true;
//   }
//   TranReceipt tr = new TranReceipt(tt,u,reason,null,b,null,null);
//        r[i]=tr;
//        
//        }
//   return r;
//}
     private int findacct(int accnum) throws IOException
    {
        // Perfrom a linear search throughout the accounts  to find an account witht he same account number as the requested
        
        char[] t = new char[7];
        for (int index = 0; index < getnumaccts(); index++){
              bfile.seek((size*index)+90);           
         for(int i =0; i<7; i++){
             t[i]= bfile.readChar();
         }
            if (Integer.parseInt(String.valueOf(t).trim())== accnum) {   // Account found
                return index;                                           // Return the index     
            }
//            return -1
        }
        return -1;                                                  // Case where the account wasnt found, return -1
    }
 
    public TranReceipt newacc(Account account)throws Exception{
     
        // Construct a Transaction ticket 
        TranReceipt tr = new TranReceipt();
        TranTicket ticket = new TranTicket(account.getaccnum(), "New account");
try{
        int index = findacct(account.getaccnum());         // Seach if account is already in our database
        // Account already exists
        if (index != -1) {
            
//            tr = new TranReceipt(ticket, false, "Error in input, Account number "+account.getaccnum()+" already Exists. ",null,0,null,null);
            throw new AccountAlreadyExistsException("Error in input, Account number "+account.getaccnum()+" already Exists. ");
        }
        // Account doesnt exist, add that account to our database
        else {
//        


         if(account.getacctype().equals("Checking")){
            addtotalchecking(account.getbalance());
         }
         if(account.getacctype().equals("Savings")){
             addtotalsavings(account.getbalance());
         }
         if(account.getacctype().equals("CD")){
             addCD(account.getbalance());
         }
         
         // public TranReceipt(TranTicket tt, boolean indicator, String acctype,
          //  double balance, Calendar newmaturitydate, String date,String f, String l
            
            
             String f =account.getDepositor().getname().getfirst();
             String l = account.getDepositor().getname().getlast();
             tr =  new TranReceipt(ticket, true,account.getacctype(),account.getbalance(),0,null,f,l,"Account opened",getnumaccts());
            //REMEMBER 
//            account.addTran(tr);//REMEMBER
    RandomAccessFile o = account.getfile();
o.setLength(0);        // clear file
o.seek(0);             // reset pointer to beginning

                                      numaccts++;

        }
}
catch(AccountAlreadyExistsException e){
    tr = new TranReceipt(ticket, false,e.getMessage(),null,0,null,null);
}
               account.add(tr);
                    return new TranReceipt(tr);

    }
//    
    public TranReceipt Balance(TranTicket tt)throws Exception{
        TranReceipt tr = new TranReceipt();
        String f="";
        String l="";       
        Account ac = new Account();
        int index = findacct(tt.getAcctNum());
        try{
     
        if(index == -1){
             tr =new TranReceipt(tt, false, "Error in input, Account number "+ tt.getAcctNum()+" Does not exist",null,0,null,null);
             //Exception handling
            throw new InvalidAccountException("Error in input, Account number "+ tt.getAcctNum()+" Does not exist");
//                        return new TranReceipt(tt, false, "Error in input, Account number "+ tt.getAcctNum()+" Does not exist",null,0,null,null);

        }            
        
        else{
            // Account was found
         ac = getaccount(index);
             f = ac.getfirst();
             l = ac.getLast();
            
           tr = ac.balance(tt,f,l);  
//           getaccount(index);
//                       return new TranReceipt(tr);

    
        }
        }
        
              catch(InvalidAccountException | AccountCloseException e){
              
              tr = new TranReceipt(tt,false,e.getMessage(), ac.getacctype(),ac.balance,f,l);
                    
              }
//                       ac.add(tr);

           
                               return new TranReceipt(tr);

//        
    }
     public TranReceipt deposit(TranTicket tt)throws Exception{
        TranReceipt tr = new TranReceipt();
         String f="";
        String l="";       
        Account ac = new Account();
        try{
        int index = findacct(tt.getAcctNum());
        
        if (index == -1){
            
            tr = new TranReceipt(tt,false,"Error in input, Account number "+ tt.getAcctNum()+" Does not exist",null,0,null,null);
            throw new InvalidAccountException("Error in input, Account number "+ tt.getAcctNum()+" Does not exist");
        }
        
        else{
             ac = getaccount(index);
             f = getaccount(index).getfirst();
             l = getaccount(index).getLast();
            tr = ac.deposit(tt,f,l);
            
            if(tr.getindicator()==true){
              replaceacc(ac,tr,index);
            }
//          return new TranReceipt(tr);

        }
        }
        catch(InvalidAccountException | AccountCloseException | InvalidAmountException |MaturityDateException e){
                           tr = new TranReceipt(tt,false,e.getMessage(), ac.getacctype(),ac.balance,f,l);

          }
//                               ac.add(tr);

                               return new TranReceipt(tr);

    }
//     
     public TranReceipt withdraw(TranTicket tt)throws Exception{
        TranReceipt tr;
        String f="";
        String l="";       
        Account ac = new Account();
        try{
        int index = findacct(tt.getAcctNum());
        
        if(index ==-1){
                        tr= new TranReceipt(tt,false,"Error in input, Account number "+ tt.getAcctNum()+" Does not exist",null,0,null,null);
                       throw new InvalidAccountException("Error in input, Account number "+ tt.getAcctNum()+" Does not exist");

            
        }
        else{
            //because im returning a copy of the account, all data member are copies, so i can use direct methods and avoid security hole
               ac = getaccount(index);//return copy of account
             f = ac.getfirst();
               l = ac.getLast();
              tr = ac.withdraw(tt,f,l);
               if(tr.getindicator()==true){
                            replaceacc(ac,tr,index);

            }
//              getaccount(index);
//                          return new TranReceipt(tr);
                          

        }
        } 
                catch(InvalidAccountException |AccountAlreadyOpenException| AccountCloseException | InvalidAmountException | InsufficientFundsException|MaturityDateException e){

                                   tr = new TranReceipt(tt,false,e.getMessage(), ac.getacctype(),ac.balance,f,l);

          }
//                               ac.add(tr);

                               return new TranReceipt(tr);

    }
//     
//     
      public TranReceipt Check(Check check)throws Exception{
           TranReceipt tr = new TranReceipt();
           String f="";
        String l="";       
        Account ac = new Account();
        try{
           int index = findacct(check.getaccnum());

        // Account not found
        if (index == -1) {
            tr= new TranReceipt(null, false, " account number: "+ check.getaccnum()+ " does not exist",null,0,null,null);
                                   throw new InvalidAccountException(" account number: "+ check.getaccnum()+ " does not exist");

        }
                             ac = getaccount(index);
         f= ac.getfirst();
          l   = ac.getLast();
         if (getaccount(index).acctype.equalsIgnoreCase("checking")==false){
            tr =  new TranReceipt (null, false, "Error in input: Your Account Type isnt a checking, unable to clear check\nAccount number: "+getaccount(index).accnum, null,getaccount(index).balance,f,l);
                    throw new AccountNotCheckingException("Error in input: Your Account Type isnt a checking, unable to clear check\nAccount number: "+getaccount(index).accnum);
         }
           
            
                     ac = getaccount(index);
            Checking ca = (Checking)ac;
            
        tr = ca.clearcheck(check,f,l);
         if(tr.getindicator()==true){
                                         replaceacc(ac,tr,index);

            }
        }
         catch(InvalidAccountException | AccountNotCheckingException| AccountCloseException |PostDatedCheckException| CheckTooOldException| InvalidAmountException | InsufficientFundsException e){
                                                tr = new TranReceipt(null,false,e.getMessage(), ac.getacctype(),ac.balance,f,l);

         }
        
         
            
  // return copy of the receipt so every data field is a copy as long as aggregate classes also copy  
//                         ac.add(tr);

            return new TranReceipt(tr);
    }
      
       public TranReceipt close(TranTicket tt)throws Exception{
        TranReceipt tr = new TranReceipt();
         String f="";
        String l="";       
        Account ac = new Account();
        try{
        int index = findacct(tt.getAcctNum());
        
        if(index==-1){
           tr =  new TranReceipt(tt, false, " account number: "+ tt.getAcctNum()+ " does not exist",null,0,null,null);
                                  throw new InvalidAccountException(" account number: "+ tt.getAcctNum()+ " does not exist");

        }
                    ac = getaccount(index);
        f = ac.getfirst();
        l = ac.getLast();
            tr = ac.close(tt,f,l);
       }
        catch (InvalidAccountException|AccountAlreadyClosedException e){
                                                                tr = new TranReceipt(tt,false,e.getMessage(), ac.getacctype(),ac.balance,f,l);

                }
            return new TranReceipt(tr);
    }
       
        public TranReceipt open(TranTicket tt)throws Exception{
        TranReceipt tr = new TranReceipt();
         String f="";
        String l="";       
        Account ac = new Account();
        try{
        int index = findacct(tt.getAcctNum());
        
        if(index==-1){
           tr= new TranReceipt(tt, false, " account number: "+ tt.getAcctNum()+ " does not exist",null,0,null,null);
                       throw new InvalidAccountException(" account number: "+ tt.getAcctNum()+ " does not exist");

        }
        else{
            ac = getaccount(index);
         f = ac.getfirst();
         l = ac.getLast();
            tr = ac.open(tt,f,l);
//            return new TranReceipt(tr);
        }
        }
         catch (InvalidAccountException|AccountAlreadyClosedException|AccountAlreadyOpenException  e){
                                                                tr = new TranReceipt(tt,false,e.getMessage(), ac.getacctype(),ac.balance,f,l);

                }
        ac.add(tr);
                               return new TranReceipt(tr);

    }
        
        public TranReceipt delete(TranTicket tt)throws Exception{
            TranReceipt tr = new TranReceipt();
            String f="";
        String l="";    
        try{
        Account ac = new Account();
            int accnum;
            double b;
        int index = findacct(tt.getAcctNum());
        
         if (index == -1) {
             
            tr =  new TranReceipt(tt, false, " account number: "+ tt.getAcctNum()+ " does not exist",null,0,null,null);
            throw new InvalidAccountException(" account number: "+ tt.getAcctNum()+ " does not exist");
        }
         else {
                          ac = new Account(getaccount(index));

            if (ac.getbalance()>0) {
                System.out.println(getaccount(index).getbalance());
                
                 tr =  new TranReceipt(tt, false, "Error in input: Unable to delete account " +tt.getAcctNum()+
                        " due to funds being available.\n please clear your account first.",null,0,null,null);
                 throw new AccountNotEmptyException( "Error in input: Unable to delete account " +tt.getAcctNum()+
                        " due to funds being available.\n please clear your account first.");
            }
            //take the last account in the array, write it in place of the account requested to be deleted
            // truncate the size of the RANDOMACESSFILE 
            else{
            Account tp = getaccount(numaccts-1);
            replaceacc(tp,tr,index);
                     numaccts--;
                     
                     //setting the new length of the RAF (random access file)
            bfile.setLength(size*(numaccts));
             f = ac.getfirst();
             l = ac.getLast();
                
        
       
            
          tr = new TranReceipt(tt,true,getaccount(index).getacctype(),0,0,null,f,l,"account Successfully deleted",numaccts);   
           
            }
         }
        }
         catch(InvalidAccountException | AccountNotEmptyException e){
                                  tr = new TranReceipt(tt,false,e.getMessage(), null,0,f,l);

                 }
         return new TranReceipt(tr);

    
    
        }
    
     //access functions for static data members
    public static double addtotalsavings(double balance){
        totalSavings +=balance;
        AllAcctstotal+=balance;
        double totalSavingsc = totalSavings;
        return totalSavingsc;
        
    }
    public static double addtotalchecking(double balance){
        totalCheckings+=balance;
        AllAcctstotal+=balance;
        double totalCheckingsC = totalCheckings;
        return totalCheckingsC;
    }
     public static double addCD(double balance){
         totalCD += balance;
         AllAcctstotal+=balance;
         double totalCDC = totalCD;
         return totalCDC;
     }
     public static String getTotalBalance(){
         double totalbalanceC = AllAcctstotal;
         return String.format("%.2f",totalbalanceC);
     }
     
     public static String getTotalCheckings(){
         double totalCheckingC = totalCheckings;
         return String.format("%.2f",totalCheckingC);
     }
     public static String getTotalSavings(){
         double totalSavingsC = totalSavings;
         return  String.format("%.2f",totalSavingsC);
     }
   public static String getTotalCD(){
         double totalCDC = totalCD;
         return  String.format("%.2f",totalCDC);
     }
    
  
     public void replaceacc(Account ac, TranReceipt tr, int index)throws Exception{
         
              String s = ac.dor();
           bfile.seek((size*index));
           bfile.writeChars(s); 
     }
}
