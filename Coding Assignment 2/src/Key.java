
/**
 * Class for the key. Contains the name, kind of key, and a comparison function
 * @author Darwin
 *
 */
public class Key {
	
	//Init
	String name;
	String kind;
	
	
	//Class COnstructor
	public Key(String word, String type) {
		
		name = word.toLowerCase();
		kind = type;
		
	}
	
	//Returns the name
	public String getName() {
		return name;	
	}
	
	//Returns the kind of key
	public String getKind() {
		
		return kind;
	}
	
	
	//Compares two keys lexicographically using Java's compareTo function.
	public int compareTo(Key k) {
		
		if (this.name.compareTo(k.name) > 0 || this.name.compareTo(k.name) == 0 && this.kind.compareTo(k.kind) > 0) {
			return 1;
		}
		else if(this.name.compareTo(k.name) == 0 && this.kind.compareTo(k.kind) == 0) {
			return 0;
		}
		else {
			return -1;
		}
	}
	

}
