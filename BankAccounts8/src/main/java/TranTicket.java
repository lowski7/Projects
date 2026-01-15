
import java.util.*;

public class TranTicket   {
    
     // *A TransactionTicket class with data fields consisting of an account number, a dateOfTransaction, typeOfTransaction
//(deposit, withdrawal, balance inquiry, new account, delete account, etc.), amountOfTransaction (for deposits and
//withdrawals), termOfCD (6, 12, 18, or 24 months 
    
    private int accnum;
    private Calendar dateoftransac;
    private String transactype;
    private double transacamount;
    private int cdterm;
    
    public TranTicket(){
        
    } 
    
    // Constructor for balance inquiries and creating new accounts
    public TranTicket(int accnum, String typeOfTransaction)
    {
        this.accnum = accnum;
        dateoftransac = Calendar.getInstance();
        transactype = typeOfTransaction;

        transacamount = 0.0;
        cdterm= 0;
    }

    // Constructor for withdrawals and deposits
    public TranTicket(int accnum, String typeOfTransaction, double amountOfTransaction, Calendar date, int cdterm)
    {
        this.accnum = accnum;
        dateoftransac = Calendar.getInstance();
        transactype = typeOfTransaction;
        transacamount = amountOfTransaction;
        this.cdterm = cdterm;
        
    }
    // copy consructor
    public TranTicket(TranTicket tt){
        accnum = tt.getAcctNum();
        dateoftransac = tt.dateoftransac;
        transactype = tt.transactype;
        transacamount = tt.transacamount;
        cdterm = tt.cdterm;
    }

    // Access Functions
    public int getAcctNum() {
        return accnum;
    }
    public Calendar getDateOfTransaction() {
        return dateoftransac;
    }
    public int getcdterm(){
        return cdterm;
    }
    public String getTypeOfTransaction() {
        return transactype;
    }
    public double getAmountOfTransaction() {
        return transacamount;
    }
    public static String toString(Calendar tr){
    
        String str;
        str = String.format("%02d/%02d/%4d",
                            tr.get(Calendar.MONTH) + 1, //remembered to add 1 since we stored it as -1 earlier
                            tr.get(Calendar.DAY_OF_MONTH),
                            tr.get(Calendar.YEAR)
                            );

        return str;
        
    }
    //used String Builder that we learned yesterday
    public String toString(TranTicket t){
        
//        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        StringBuilder StringB = new StringBuilder();
        
        StringB.append("Transaction Requested: ").append(transactype).append("\n");
        StringB.append("Account Number: ").append(accnum).append("\n");
        
        StringB.append("Date of transaction: ").append(toString(dateoftransac)).append("\n");
        if(transacamount>0){
            StringB.append("Amount: $").append(String.format("%.2f", transacamount)).append("\n");
        
        }
       if(cdterm>0){
            StringB.append("CD Term: ").append(cdterm).append(" months\n");
       }
        
        return StringB.toString();
    
    
    
}

}