import java.io.*;
import java.util.*;
public class BankAccounts8 {
    public static void main(String[] args) throws Exception {
        System.out.println();
       //variable declarations
                Bank bank = new Bank();	
                System.out.println(bank.hashCode());
	    char choice;								
	    boolean notDone = true;						
           
            // open input test cases file
            
	    File outf = new File("C:\\Users\\selsa\\OneDrive\\Documents\\NetBeansProjects\\BankAccounts8\\src\\main\\java\\output.txt");
            File testdata = new File ("C:\\Users\\selsa\\OneDrive\\Documents\\NetBeansProjects\\BankAccounts8\\src\\main\\java\\test.txt");
                             RandomAccessFile bfile = new RandomAccessFile("BankAccounts.dat", "rw");

	    //create Scanner object
            
	    Scanner kybd = new Scanner(testdata);
	    //Scanner kybd = new Scanner(System.in);
            
	    // open the output file
	    PrintWriter outFile = new PrintWriter(outf);
	    //PrintWriter outFile = new PrintWriter(System.out);

	   
	    /* fill and print initial database */
	     readaccts(bank,bfile);
             printAccts(bank, outFile);

        do
        {
            menu();                                     // Print the menu
            choice = kybd.next().charAt(0);             // Read In Choice
try{
            switch(choice)
            {
                // Quit and Print
                case 'q':
                case'Q':
                    notDone = false;
                    printAccts(bank, outFile);
                    break;
                    
                // Withdrawal
                case 'w':
                    case'W':
                    withdrawal(bank, outFile,kybd);
                    break;

                // Deposit
                case 'd':
                case 'D':
                    deposit(bank, outFile, kybd,bfile);
                    break;
                
                // Clear Check
                case 'c':
                case 'C':    
                    ClearCheck(bank, outFile, kybd);
                    break;
                
                // New Account
                case 'n':
                 case'N':
                           //hsr
                    newAcct(bank, outFile, kybd,bfile);
                    break;
                
                 case 'S':
                 case 's':
                    close(bank,outFile,kybd);
                    break;
                    
                 case 'h':
                 case 'H':
                     //TH: transaction history
                     AccountInfoTH(bank, outFile, kybd);
                     break;
                    
                 case 'R':
                 case 'r':
                    open(bank,outFile,kybd);
                    break;
                    
                case 'b':
                    case'B':
                    balance(bank, outFile, kybd);
                    break;
                
                // Account Info
                case 'i':
                case 'I':
                    AccountInfo(bank, outFile, kybd);
                    break;
                
                // Delete Account
                case 'x':
                case 'X':
                    deleteAcct(bank, outFile, kybd);
                    break;
                      default:
                          outFile.println();
	                outFile.println();
	                outFile.flush();
                          throw new InvalidMenuSelectionException("Error: " + choice + " is an invalid selection -  try again");
                     
	                 

            }
}
             catch(InvalidMenuSelectionException e){
    outFile.println();
    outFile.println(e.getMessage());
	                outFile.println();
	                outFile.flush();
}
        } while (notDone);
        
        outFile.close();
        kybd.close();
        System.out.println("The Program Is Terminating");
    }

       
       public static void menu()
	{
	    System.out.println();
	    System.out.println("Select one of the following transactions:");
	    System.out.println("\t****************************");
	    System.out.println("\t    List of Choices         ");
	    System.out.println("\t****************************");
	    System.out.println("\t     W -- Withdrawal");
	    System.out.println("\t     D -- Deposit");
            System.out.println("\t     C -- Clear Check");
	    System.out.println("\t     N -- New Account");
            System.out.println("\t     S -- Close Account");
            System.out.println("\t     R -- Reopen Account"); 
            System.out.println("\t     H -- Account Info + Transactions");             
	    System.out.println("\t     B -- Balance Inquiry");
            System.out.println("\t     I -- Account Info");
	    System.out.println("\t     X -- Delete Account");
	    System.out.println("\t     Q -- Quit");
	    System.out.println();
	    System.out.print("\tEnter your selection: ");
	}
        public static void readaccts(Bank bank,RandomAccessFile bfile)throws Exception {
          Account account = new Account();
            Depositor depositor1;
            Name name1;
           
           
             int numaccts=0;          
        String tempstr;
        File inp = new File("C:\\Users\\selsa\\OneDrive\\Documents\\NetBeansProjects\\BankAccounts7\\src\\main\\java\\input.txt");

         //create a Scanner to read the input file
         Scanner inf = new Scanner(inp);
            System.out.println(inf.hashCode());
         while (inf.hasNext()){

      numaccts = bank.getnumaccts();
      String line = inf.nextLine();

      String [] word = line.split(" ");
      
      
      
      name1 = new Name(word[0], word[1]);
      depositor1 = new Depositor(name1,word[2]) ;
     
     
      
     if(word.length==7){
         
         
        account = new CDAccount(depositor1,Integer.parseInt(word[3]), word[4], Double.parseDouble(word[5]),word[6]);
         
     }
     else if (word[4].equalsIgnoreCase("Checking")){
                   account =  new Checking(depositor1,Integer.parseInt(word[3]), word[4], Double.parseDouble(word[5]));
                  

     }
     else if (word[4].equals("Savings")) {
         account = new Savings(depositor1,Integer.parseInt(word[3]), word[4], Double.parseDouble(word[5]));
     }

             TranReceipt tr = bank.newacc(account);
         if(tr.getindicator()==true){
             numaccts++;

                                 bfile.writeChars(account.dor());
//                                                                account.add(tr);

         }
             
}

       
       }
      
 public static void printAccts(Bank bank, PrintWriter outf)throws Exception{
  
            Depositor depositor1;
            Name name1;
            
          //print table title
		outf.println("\t\tBank Accounts in the Database");
		outf.println();
		//print table column headings
		outf.printf("%-20s%-20s%20s%25s%25s%24s%18s",
				"Last Name","First Name ","Social Security # "," Account Number ","Account Type","Balance","Maturity Date");
		outf.println();
                for (int i = 0; i < 160; i++) {
               outf.print("_");
               
           }
                outf.println();
               
		for (int count = 0; count < bank.getnumaccts(); count++){
                       Account account = bank.getaccount(count);
                       //to string usage
                       
                       if(account.getacctype().equals("CD")){
                           
                           CDAccount cd = (CDAccount)account;
                           outf.print(cd.toString());
                           
                       }
                       else
                         outf.print(account.toString());
                        outf.println();
                       
                }
               
        

                outf.println();

                 outf.println("Total Balance in Checking Accounts: " +bank.getTotalCheckings());
                outf.println("Total Balance in Savings Accounts: " +bank.getTotalSavings());
                outf.println("Total Balance in CD Accounts: " +bank.getTotalCD());
                outf.println("Total Balance in All Accounts: " +bank.getTotalBalance());
                    
                outf.println();
                outf.println();
		outf.flush();		//flush the output file buffer

}


 
        /*
        input: reads in data from test cases file, creates ticket and receipt objects 
        
        
        process: calls makebalance() in the account class. checks if account exists or not,
        calls makebalance() in Account class, checks seperates cd and regular accounts
        
        output: reads in indicator in receipt, if false, error message+ reason, if not,prints success receipt. 
        
        */
  
        //since all the exceptions are a subclass of the main EXCEPTION class, throwing Exception throws all the other exceptions
 //works the same way instead of throwing each individual exception seperately
        public static void balance(Bank bank, PrintWriter outf,Scanner kybd)throws Exception{
            int reqnum;
                 TranReceipt tr = new TranReceipt();
                 TranTicket tt;
	    System.out.println();
	    System.out.print("Enter the account number: ");			//prompt for the account number
	    reqnum = kybd.nextInt();						//read-in the account number
	 
           tt = new TranTicket(reqnum,"balance inquiry");
          
           tr = bank.Balance(tt);
            outf.println();
             outf.print(tr.toString(tr,0.0));
		    outf.println();
           
           
           tr = bank.Balance(tt);
          
          if(tr.getindicator()==false){
              outf.println();
              
              outf.println(tr.toString(tr,"",0));
              outf.println();      
                      
              }
          
          else{
              
               outf.println();
             outf.print(tr.toString(tr,0.0));
                       outf.println();
              
        
          outf.println();
         outf.flush();
}
        }
        
         /*
        input: reads in data from test cases file, creates ticket and receipt objects, if the amount is negative,
        prints an error message and ends the transaction 
        
        
        process: calls makedeposit() in the account class. checks if account exists or not,
        calls makedeposit() in Account class, checks seperates cd and regular accounts and checks for a valid maturity date
        
        output: reads in indicator in receipt, if false, error message + reason, if not,prints success receipt. 
        
        */
        
        public static void deposit(Bank bank,PrintWriter outf, Scanner kybd,RandomAccessFile bfile)throws Exception{
////            
            double depositamt;
             int reqnum;
          
	    System.out.println();
            outf.println();
	    System.out.print("Enter the account number: ");			//prompt for the account number
	    String line = kybd.nextLine().trim();
            String[] word = line.split(" ");
            
            reqnum = Integer.parseInt(word[0]);
            depositamt = Double.parseDouble(word[1]);
            
            
	    //call findAcct to search if requestedAccount exists
	  //  index = findacct(account, numaccts, reqnum);
          Calendar today = Calendar.getInstance();
          TranTicket tt ;
          TranReceipt tr = new TranReceipt() ;
          
          if (word.length == 3) {
           tt = new TranTicket(reqnum, "Deposit", depositamt, today ,Integer.parseInt(word[2]));
               

        }
        else{
              tt = new TranTicket(reqnum,"Deposit",depositamt,null,0);
        }
                   tr = bank.deposit(tt);

          if(tr.getindicator()==true){
           outf.println();
             outf.println(tr.toString(tr,0.0));
           
                outf.println();
                outf.println();
               }
          else{
           outf.println();
             outf.println(tr.toString(tr," ",0.0));
           
                outf.println();
                outf.println();
          
            
        }
        }
               
         /*
        input: reads in data from test cases file, creates ticket and receipt objects if test cases reads in a negative value, error message gets
        printed and transactin is over
        
        
        process: calls withdraw() in the account class. checks if account exists or not,
        calls makewithdrawal() in Account class, checks seperates cd and regular accounts, checks maturity date,
        checks if withdraw amount is more than the account balance available, 
        
        
        output: reads in indicator in receipt, if false, error message+ reason, if not,prints success receipt. 
        
        */
        
public static void withdrawal(Bank bank,PrintWriter outf,Scanner kybd)throws Exception{
//    
     double withdrawamt;
             int reqnum;
    
	    System.out.println();
	    System.out.print("Enter the account number: ");			//prompt for the account number
	    String line = kybd.nextLine().trim();
            String[] word = line.split(" ");
            reqnum = Integer.parseInt(word[0]);
            withdrawamt = Double.parseDouble(word[1]);
            
            
	    //call findAcct to search if requestedAccount exists
	  //  index = findacct(account, numaccts, reqnum);
          Calendar today = Calendar.getInstance();
          TranTicket tt;
          TranReceipt tr = new TranReceipt();
       
           if (word.length == 3) {
           tt = new TranTicket(reqnum, "Withdraw", withdrawamt, today ,Integer.parseInt(word[2]));
               
        }
        else{
              tt = new TranTicket(reqnum,"Withdraw",withdrawamt,null,0);
        }
         
          tr = bank.withdraw(tt);

         if(tr.getindicator()==true){
          
                  outf.println();
             outf.println(tr.toString(tr,0.0));
                outf.println();
                outf.println();
               
          }
         else{
             outf.println();
             outf.println(tr.toString(tr, " ",0.0));
                outf.println();
                outf.println();
               
         }
          
            outf.println();
            outf.flush();

            

          
               


}
 /*
        input: reads in data from test cases file, check and receipt objects if check amount is negative, immediate rejection
and transaction ends

        
        
        process: calls clear check() in the account class. checks if account exists or not,
        calls clearcheck() in Account class, checks seperates cd and regular accounts, checks if account type is cd, checks checks date and returns receipt 
        
        output: reads in indicator in receipt, if false, error message+ reason, if not,prints success receipt. 
        
        */
public static void ClearCheck(Bank bank, PrintWriter outf,Scanner kybd)throws Exception{
////      
//
        outf.println();
    int reqnum;
    double checkamt;
    reqnum = kybd.nextInt();
    checkamt = kybd.nextDouble();
    String str = kybd.next();
        
  Check  check= new Check(reqnum,checkamt,str);
    
    TranReceipt tr = new TranReceipt();
                tr = bank.Check(check);

         if(tr.getindicator()==true){
      
        outf.println();
             outf.print(tr.toString(tr,checkamt));
                outf.println();
                                outf.println();
                
        
    }
         else{
     outf.println();
             outf.print(tr.toString(tr,str,checkamt));
                outf.println();
                                outf.println();
         }
                outf.println();
              outf.flush();
    
                  }
    



/*
finds index of first occurence of an account associated ssn
*/

           public static int findssn(Bank bank, String reqssn)throws Exception{
           // find ssn method for Accountinfo requirements. finds index of first occurence of Social security in sample data
           int index=0;
           for (index = 0; index < bank.getnumaccts(); index++) 
               
               if(bank.getaccount(index).getDepositor().getSSN().equals(reqssn))  
                   
                 return index;//returns index
           
           return -1; //if not, returns -1
       }
                /*
              prints accounts associated with ssn
           */
                  public static void AccountInfo(Bank bank, PrintWriter outf, Scanner kybd)throws Exception{
      boolean b=true;
          
	    System.out.println();
	    System.out.print("Enter your social security number: ");
            String ssn=kybd.next();
            int index = findssn(bank,ssn);

            if(index==-1){
                // if social security does not exist
                //no receipt involved here
                outf.println();
                    outf.println("Transaction requested: Account Info.");
                    outf.println("Social Security number: "+ ssn);
                    outf.println("Error in input, incorrect Social Security # entered.");
                    
            }  
            
            else{
                outf.println();
                    //if social security does exist, print the following
			outf.println();
                        outf.printf("%-20s%-20s%20s%25s%25s%17s",
				"Last Name","First Name ","Social Security # "," Account Number ","Account Type","Balance");
                        outf.println();
                        for(int i =0; i<150; i++){
                outf.print("_");
            }
                        for (int i = index; i < bank.getnumaccts() ; i++) {
                                   if(bank.getaccount(i).getDepositor().getSSN().equals(ssn)) {
                                       
outf.println();
                       

//		//print account info into 
                   //toString in the account class
			outf.print(bank.getaccount(i).toString());
                        outf.flush();
//                                   }
                                   }
                                   }
                        outf.println();
                        outf.println();
//                        
//            
//                        
//            
            
                    }
                  }
                  
                  
                  /*
                  input: reads ssn checks first occurence of ssn in database
                  
                  process: gets the transaction arraylist associated with the found account, and checks if there are transactions made, then prints
                  
                  output: prints all transactions in a neatly formatted table
                  */
                 public static void AccountInfoTH(Bank bank, PrintWriter outf, Scanner kybd)throws Exception{
    outf.println();
    outf.println("Transaction requested: Account info + history");
    
    System.out.print("Enter your social security number: ");
    String ssn = kybd.next();
       //find ssn is in the client program
    int index = findssn(bank, ssn);

    if (index == -1) {
        outf.println("Error: Social Security number " + ssn + " not found.");
        return;
    }

    // Print account details
    outf.println();
    outf.printf("%-20s%-20s%-20s%-20s%-20s%-20s%n",
                "Last Name", "First Name", "Social Security #", "Account Number", "Account Type", "Balance");
    outf.println("_______________________________________________________________________________________");

    for (int i = index; i < bank.getnumaccts(); i++) {
        
        if (bank.getaccount(i).getDepositor().getSSN().equals(ssn)) {
            
        

        Account acc = bank.getaccount(i);
        outf.print(acc.toString());

        // Retrieve transaction history
        TranReceipt[]transactions = bank.getTransactionHistory(acc.getaccnum());

        if (transactions[0] == null ) {
            outf.println();
            outf.println("No Transactions found/made on this account.");
            continue;
        }
            //header
        outf.println("\n*************** Transactions *************");
        outf.printf("%-20s%-20s%-20s%-20s%-20s%-20s",
                    "Date", "Transaction Type","Transaction Amount", "Transaction Status", "Balance", "Reason");
        

                    outf.println();
            outf.println();
            System.out.println(acc.num);
            System.out.println(transactions[0].toString(transactions[0], ssn));
        for(int x=0; x<4;x++){
            
           
    TranReceipt tr = transactions[x];
    if(tr == null) break;
            if (tr.getTT() == null) {
                //error message
        System.out.println("Error: Transaction Ticket is null for transaction");
            }
            else{
            //HERE
            //toString
            outf.println(tr.toString(tr,"Transactions"));
            outf.println();
            outf.println();

            continue;
            }
        }
        }
        }
        outf.println();
        outf.println();
    }

//reads input from file and checks if account is already closed or not then closes account and doesnt allow transactions after
                      public static void close(Bank bank, PrintWriter outf, Scanner kybd)throws Exception{
////                        
                        System.out.println("Enter account number to reopen: ");
        int reqnum = kybd.nextInt();
        TranTicket tt;
        TranReceipt tr = new TranReceipt();
        Calendar today = Calendar.getInstance();
        
         tt = new TranTicket (reqnum,"Close Account",0,null,0);
         tr = bank.close(tt);
         if(tr.getindicator()){
         outf.print(tr.toString(tr,0.0));
            outf.println();
            outf.println();
         }
         else{
             outf.print(tr.toString(tr,"",0.0));
            outf.println();
            outf.println();
         }
        
                      
                  }
                  
                      //checks if account is closed, opens it and allows transactions
                  public static void open(Bank bank, PrintWriter outf, Scanner kybd)throws Exception{
////                      
                        System.out.println("Enter account number to reopen: ");
        int reqnum = kybd.nextInt();
        TranTicket tt;
        TranReceipt tr = new TranReceipt();
                      tt = new TranTicket (reqnum,"reopen Account",0,null,0);

        Calendar today = Calendar.getInstance();
     
         tr = bank.open(tt);
         if(tr.getindicator()){
         outf.println();
            outf.print(tr.toString(tr,0.0));
          outf.println();
         }
    
         else{
        
       outf.println();
            outf.print(tr.toString(tr,"",0.0));
          outf.println();
        }
                  }
        
                  
/*
input: reads new info from  tests cases file, "user" splits it in line and puts it an "Account" object checks cd and calls newacc in bank 

process: checks if acc already exists, if it does, returns receipt with error message. if acc doesnt exist, account gets added in Account[]
and increments numaccts

output: gets success receipts and prints it



*/
public static void newAcct(Bank bank, PrintWriter outf, Scanner kybd,RandomAccessFile bfile) throws Exception{
     outf.println();
     outf.println();

    System.out.println("Enter Account Information");
// local variables:
         int reqnum;
           
           String maturity=" ";
           String line;
          TranReceipt tr;
            Account account;
            
            //account number
            reqnum = kybd.nextInt();
            
            
             
                //read in prompted info into a line
                String f =   kybd.next();
            String l=     kybd.next();
            Name name = new Name (f,l);
            
            
              String ssn=   kybd.next();
              Depositor depositor = new Depositor(name,ssn);
              
              
         int num= kybd.nextInt();
         String type = kybd.next();
           double b = kybd.nextDouble();
    
      Account ac ;
    
      if(type.equals("CD")){
     maturity = kybd.next();

        ac = new CDAccount(depositor, num, type, b, maturity);   

                 }
      else if (type.equalsIgnoreCase("Checking")) {
          ac = new Checking(depositor, num, type, b); 
          
        }
      else{
          ac = new Savings(depositor,num,type,b);
      }
      
      
     
      tr = bank.newacc(ac);
      if(tr.getindicator()==true){
           outf.print(tr.toString(tr, bank.getnumaccts()));
           bfile.writeChars(ac.dor());
}
            else{
                outf.println();
           outf.print(tr.toString(tr,"", bank.getnumaccts()));

     outf.println();
            }
      
            //  if account already exists
                  


      
              outf.println();
     outf.println();
outf.flush();
} 
/*
input: reads account number from  tests cases file, "user", 

process: checks if acc doesn't exist, if it doesn't, prints error message. if it does, account gets deleted in Account<>.
and increments numaccts

output: gets success receipts and prints it



*/
       
    

public static void deleteAcct(Bank bank, PrintWriter outf, Scanner kybd)throws Exception{
    
//    
    outf.println();
    System.out.println("Enter account number for deletion.");
    
    int reqnum = kybd.nextInt();
    
    TranTicket tt = new TranTicket(reqnum,"Delete Account");
    TranReceipt tr= new TranReceipt() ;
   
    tr= bank.delete(tt);
    
     if(tr.getindicator()==true){
        outf.println();
        outf.print(tr.toString(tr,bank.getnumaccts()));
        outf.println();
     }
       // bank.numacctsminus();
    
     else{
         outf.println();
        outf.print(tr.toString(tr,"",bank.getnumaccts()));
        outf.println();
     }
    
     outf.println();
    outf.flush();

    
    
    
}


        
        
        
        
        public static void pause(Scanner keyboard)
	{
		String tempstr;
		System.out.println();
		System.out.print("press ENTER to continue");
		tempstr = keyboard.nextLine();		//flush previous ENTER
		tempstr = keyboard.nextLine();		//wait for ENTER
	}
        
        
        
        
    }

