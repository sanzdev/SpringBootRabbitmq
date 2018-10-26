package hello;

public class StringMesssage {

	private String Text;
	
	// Default constructor is needed to deserialize JSON
    public StringMesssage() {
    }
    
    public StringMesssage(String text) {    	
        this.Text = text;                
    }
    
    @Override
    public String toString() {
        return "Got string: " + Text ;
    }
}
