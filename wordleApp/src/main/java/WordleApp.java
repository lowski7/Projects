   //Sajid ELsaeyed 3130  Wordle app, eliminating letters as the guesses come in

// I am using the List<String> in wordfinder to iterate and randomly pick a a 5 letter word from
//a file that contains all the 5 letter english words
// using an aggregate method and a lamba expression  as a bipredicate to lookup 
// if one of the letters is correct but at a wrong spot
import java.util.*;
import java.io.*;
import java.math.*;
import java.nio.file.*;
import java.util.function.*;
import java.util.stream.*;
public class WordleApp {

    public static void main(String[] args) throws IOException {
        
        Set<String> Guesses = new HashSet<>();
        int Nguess = 6;
        int Worldlength =5;
        
         System.out.println("Welcome to Wordle! You have 6 attempts to guess the 5-letter word.");
         System.out.println();
         System.out.println();
         System.out.println(" r indicates a letter at the right place\n" +
                            "wp indicates a letter in the word but at the wrong place\n" +
                            " w indicates a letter not in the word at all");
         
                    
         String Keyword = wordfinder();
//           String Keyword = "thyme";
         
                for(int i =0; i<Nguess; i++){
                    System.out.println("Enter your guess, this is guess # "+ (i+1));
                    
       String Guess = new Scanner(System.in).nextLine();

        if(Guess.length() != 5){
            System.out.println("Guess must have 5 letters\n");
            i--; //doesn't count as an attempt
            continue;
        } 
        
        if(onlyusedonce(Guess,Guesses)==false){
            System.out.println("Guess already used");
            i--;
            continue;
        }

        else {
            
            System.out.println();
            String res = compare(Keyword,Guess);
            System.out.println(res);
                if(res.equals("r r r r r ")){
                    
                    System.out.println("Congratulations! You've guessed the word: " + Keyword);
                    return;
                }
                
                else {
             //function to find if "wp" exists in the result string from the method
             
               BiPredicate<String, String> containschar = 
                      (str, sub) -> IntStream.range(0, str.length() - sub.length() + 1)
                              .anyMatch(j -> str.substring(j, j + sub.length()).equals(sub));
              
              //if theres one of the letters in the wrong position
              if(containschar.test(res, "wp")){
                  System.out.println("Wrong answer! The position in the string marked as ''wp'' is a correct letter but at the wrong position ");
                  continue;//continue, next iteration of the loop
              }
                 else{// if none are right
                        System.out.println("Wrong answer, None of the letters are in the keyword");
                        }
                }
                System.out.println();
            }
                }
                 System.out.println("Sorry! You're out of guesses. The word was: " + Keyword);

}

     public static boolean onlyusedonce(String g,Set<String> retrial){
         
         //the add method returns a boolean,
         //false if the string is already present that means that we add every word guessed in the set
         return retrial.add(g);
     }
     
     
     
     
     
     // file with all the 5 letter words, 
// then randomly select one of them using the data structure: List and the for each loop to erase empty lines
                   public static String wordfinder()throws IOException{
        List<String> words = Files.readAllLines(Path.of("C:\\Users\\selsa\\Downloads\\outf.txt"));
        words.removeIf(String::isEmpty);//incase
        Random random = new Random();
        return words.get(random.nextInt(words.size())).toLowerCase();
    }
                               
                   
                   
    public static String compare(String k , String g){
        StringBuilder sb= new StringBuilder();
    
        int guesess = 6; 
        // r indicates a word at the right place
        // wp indicates a word in the word but at the wrong place
        // w indicates a word not in the word at all
        for (int i = 0; i < k.length(); i++) {
            
            if (g.charAt(i) == k.charAt(i)) {
               sb.append("r ");
//                System.out.print("r ");
            } 
            else if (k.indexOf(g.charAt(i)) != -1) {              
                    sb.append("wp ");
//                System.out.print("wp ");
            }
            else {
               sb.append("w ");
//                System.out.print("w ");
            }
        }
         return sb.toString();

    }     
           }