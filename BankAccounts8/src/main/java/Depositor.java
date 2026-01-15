
public class Depositor {
    
 private Name name;
private String SSN;
  

public Depositor(){
    
}
public Depositor(Name name, String SSN){
    this.name = name;
    this.SSN = SSN;
}
//Copy constructor 
 public Depositor(Depositor d){
     //copy of a name object, not actual reference
     name = new Name(d.name);
     SSN = d.SSN;
 }
public Name getname(){
    //return copy
    return new Name(name);
}
    
public String getSSN(){
    return SSN;
}

// .equals() method

public boolean equals(Depositor d){
    if(name.equals(d.name)&& SSN.equals(d.SSN))
        return true;
    return false;
}
// toString
public String toString(Depositor d){
     return name.toString(name) + ", SSN: " + SSN;
}
    
}

