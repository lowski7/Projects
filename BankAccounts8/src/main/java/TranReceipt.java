import java.util.*;

public class TranReceipt {
    
      
    
    
    /*
    A TransactionReceipt class with data fields consisting of a TransactionTicket, successIndicatorFlag, reasonForFailure
String, accountType, preTransactionBalance, postTransactionBalance, postTransactionMaturityDate (for CDs).
    */
    private TranTicket tticket;
    private boolean indicator;
    private String reason;
    private String acctype;
    private double oldbalance;
    private double newbalance;
    private Calendar newmaturitydate;
    private String f;
    private String l;
    private String date;
    private double balance;
    private String info;
    
    public TranReceipt(){
    }
    // new TranReceipt(tt, true,acctype,balance,balance,getmaturitydate(), getdate(),f,l);
   
    
    
    
     public TranReceipt(TranTicket tt, boolean indicator, String acctype,
            double oldbalance,double newbalance, String date,String f, String l, String info,int difference){
        this.info = info; 
         this.indicator = indicator;
        this.acctype = acctype;        
        tticket = tt;

        this.oldbalance = oldbalance;
        this.newbalance = newbalance;
        
        // Add to the date based on the term of cd
       this.newmaturitydate = Calendar.getInstance();  
if(newmaturitydate!= null){
//                    this.newmaturitydate = Calendar.getInstance();

                  this.newmaturitydate.add(Calendar.MONTH, tt.getcdterm());
                   
        }       // Returns Date and Time when the object is created
this.date = date;
        this.f = f;
        this.l =l;
    }
    //copy constructor
    public TranReceipt(TranReceipt tr){
        
         this.indicator = tr.indicator;
        this.acctype = tr.acctype;        
        tticket = tr.tticket;

        this.oldbalance = tr.oldbalance;
        this.newbalance = tr.newbalance;
        this.reason = tr.reason;
          this.f = tr.f;
        this.l =tr.l;
        this.info = tr.info;
        // Add to the date based on the term of cd
       
  if(tr.newmaturitydate!= null){
                    this.newmaturitydate = tr.newmaturitydate;
   
        }       // Returns Date and Time when the object is created
      
    }
    
    
    // copy check constructor
    public TranReceipt (TranReceipt tr, String ch){
         indicator = tr.indicator;
        acctype = tr.acctype;        

        oldbalance = tr.oldbalance;
        newbalance = tr.newbalance;
        balance = tr.balance;
        info = tr.info;
        // Add to the date based on the term of cd
      
        this.f = tr.f;
        this.l =tr.l;
    }
    
    //reciept for Failure
    public TranReceipt(TranTicket tt,boolean indicator, String reason,String acctype,double balance,String f, String l){
        this.acctype = acctype;
        this.reason = reason;
        tticket = tt;
        this.indicator = indicator; 
        this.balance = balance;
        this.oldbalance=balance;
        newbalance = balance;
        this.f =f;
        this.l =l;
        
    }
    
    //receipt for success
    public TranReceipt(TranTicket tt, boolean indicator, String acctype,
            double oldbalance,double newbalance, Calendar newmaturitydate, String date,String f, String l){
        
         this.indicator = indicator;
        this.acctype = acctype;        
        tticket = tt;

        this.oldbalance = oldbalance;
        this.newbalance = newbalance;
        
        // Add to the date based on the term of cd
       this.newmaturitydate = Calendar.getInstance();  
if(newmaturitydate!= null){
                    this.newmaturitydate = Calendar.getInstance();

                  this.newmaturitydate.add(Calendar.MONTH, tt.getcdterm());
                   
        }       // Returns Date and Time when the object is created
this.date = date;
        this.f = f;
        this.l =l;
    }
    
    
    
    // new account
    
    public TranReceipt(TranTicket tt, boolean indicator, String acctype,
            double balance, Calendar newmaturitydate, String date,String f, String l){
    
                tticket = tt;

    this.indicator = indicator;
        this.acctype = acctype; 
        this.balance = balance;
        newbalance = balance;
        this.newmaturitydate = newmaturitydate;
        this.date = date;
        this.f = f;
        this.l = l;
        
    }
    public TranReceipt(TranTicket tt, boolean indicator, String reason, String acctype, Calendar maturitydate, String date,double balance,String f, String l){
         newbalance = balance;
    this.indicator = indicator;
        this.acctype = acctype; 
        this.date = date;
        this.reason = reason;
                tticket = tt;

        if(maturitydate!= null){
                    newmaturitydate = Calendar.getInstance();

                  newmaturitydate.add(Calendar.MONTH, tt.getcdterm());
                   this.f=f;
                   this.l=l;
        }
       
    }
    
    //check
     public TranReceipt( boolean successIndicatorFlag, String acctType,
            double preTransactionBalance, double postTransactionBalance, String date, String f,String l,String info)
    {
       
        indicator = successIndicatorFlag;
        acctype = acctType;        

        oldbalance = preTransactionBalance;
        newbalance = postTransactionBalance;
        this.info = info;
        // Add to the date based on the term of cd
        
       
        this.f = f;
        this.l =l;
    }
    
    
        
         public TranTicket getTT(){
       if(tticket!=null)
       {
        return new TranTicket(tticket);}
       return null;
    }
    public String getname(){
        return f+" "+l;
    }
    
public boolean getindicator(){
    return indicator;
}
public String getreason(){
    return reason;
}
public double getoldbalance(){
    return oldbalance;
    
}
public double getnewbalance(){
    return newbalance;
}
public Calendar getnewmaturitydate(){
    return newmaturitydate;
}


public String getacctype(){
    return acctype;
}
public String getdate(){
    
        String str;
        str = String.format("%02d/%02d/%4d",
                            newmaturitydate.get(Calendar.MONTH) + 1, //remembered to add 1 since we stored it as -1 earlier
                            getnewmaturitydate().get(Calendar.DAY_OF_MONTH),
                            getnewmaturitydate().get(Calendar.YEAR)
                            );

        return str;
    }


    // DONT TOUCH 
    //SERIOUSLY DONT TOUCH

    //toString for transactions
    public String toString(TranReceipt tr,double checkamt){
        StringBuilder StringB = new StringBuilder();
        //if Ticket is null, that means its a clear check transactions
        if(getTT()==null){
            StringB.append("Transaction Requested: Clear Check\n");
//            StringB.append(info);
             StringB.append("Check Amount: $").append(String.format("%.2f",checkamt )).append("\n");
             StringB.append("old Balance: $").append(String.format("%.2f", oldbalance)).append("\n");
                StringB.append("new Balance: $").append(String.format("%.2f", newbalance)).append("\n");
        }
        //if not
        if(getTT()!=null){
        StringB.append(getTT().toString(getTT()));
        }
        //prints account type and balance anyway
        StringB.append("Account Type: ").append(acctype).append("\n");
         StringB.append("Name of Owner: ").append(getname()).append("\n");
        if(info!=null){      
          StringB.append("Balance: $").append(String.format("%.2f",newbalance)).append("\n");
             StringB.append(info).append("\n");
        }
        else{
          if (indicator==false) {
            StringB.append(reason).append("\n");
        }
        
      if(getTT()!=null)
        if(getTT().getTypeOfTransaction().equals("balance inquiry")){
            StringB.append("Balance: $").append(String.format("%.2f",oldbalance)).append("\n");
        }
       
        else{
               StringB.append("old Balance: $").append(String.format("%.2f", oldbalance)).append("\n");
                StringB.append("new Balance: $").append(String.format("%.2f", newbalance)).append("\n");
        }
           if(acctype!=null)
               if (acctype.equals("CD")) {
            StringB.append("Maturity Date: ").append(TranTicket.toString(newmaturitydate)).append("\n");
        }}
             return StringB.toString();
    }
    
    // overloaded to string for open and create new accounts
    
   // dont touch 
    public String toString(TranReceipt tr, int numaccts){
                StringBuilder StringB = new StringBuilder();
         StringB.append(getTT().toString(getTT()));
         if(indicator==false)
             StringB.append(reason);
         else
             StringB.append(info).append("\n");
                 StringB.append("Name of Owner: ").append(getname()).append("\n");

         StringB.append("Number of Accounts in database: "+numaccts);
         return StringB.toString();
    }
    
    
    //toString for failed transactions, all failed transactions
    public String toString(TranReceipt tr, String f,double d){
         StringBuilder StringB = new StringBuilder();
        if(getTT()==null){
            StringB.append("Transaction Requested: Clear Check\n");
           if(newbalance==oldbalance)
               StringB.append("balance: ").append(String.format("%.2f",oldbalance)).append("\n");
           StringB.append("Check date: "+ f).append("\n");
           StringB.append("Check Amount: $").append(String.format("%.2f",d)).append("\n");
             
        }        
        if(getTT()!=null){
        StringB.append(getTT().toString(getTT()));
        }
        if(acctype !=null && getname()!=null){
        StringB.append("Account Type: ").append(acctype).append("\n");
        StringB.append("Name of Owner: ").append(getname()).append("\n");
        }
        if(info!=null){      
          StringB.append("Balance: $").append(String.format("%.2f",oldbalance)).append("\n");
          StringB.append(info).append("\n");
        }
        StringB.append(reason).append("\n");

        return StringB.toString();
    }
    
    
    
    //toString for transaction history in a neat table
    public String toString(TranReceipt tr, String u){
        Calendar today = Calendar.getInstance();
        String status = tr.getindicator() ? "Successful" : "Failed";
        String str = String.format("%-20s%-20s%-20.2f%-20s%-20.2f%-20s",     
                TranTicket.toString(today), tr.getTT().getTypeOfTransaction(),tr.getTT().getAmountOfTransaction(), status, tr.getnewbalance(), tr.getreason());
    return str;
    }
    
    public String dor(){
        Calendar today = Calendar.getInstance();
                String status = getindicator() ? "Successful" : "Failed";
        //Date Transaction Type Transaction Amount  Transaction Status  Balance  Reason
        String as = String.format("%-10s%-15s%-15.2f%-10s%-15.2f%-30s",
                TranTicket.toString(today),
                getTT().getTypeOfTransaction(),
                getTT().getAmountOfTransaction(), 
                status, 
                getnewbalance(), 
                getreason());
        
        return as;
    }
    
}