
public class Name {
   
 private String l;
    private String f;
    // copy constructor
    public Name(Name name){
        l = name.l;
        f = name.f;
    }
    public Name(){
        
    }
    public Name(String first, String last){
        l=last;
        f=first;
    }
    public String getfirst(){
       // String first = f;
        return f;
    }
    public String getlast(){
       // String last = l;
        return l;
    }
    public boolean equals(Name n){
        if(this.f.equals(n.f)&& this.l.equals(n.l))
            return true;
        return false;
    }
    
    public String toString(Name n){
        return n.f + " " + n.l;
    }    
}
