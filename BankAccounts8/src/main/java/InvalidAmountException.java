
public class InvalidAmountException extends Exception {
    //if(transactionAmount is <0)
    public InvalidAmountException(String s){
        super(s);
    }
}
