/**
 * Data Item Class. Stores the key and the information
 * @author Darwin
 *
 */
public class DataItem {
	
	//Init Variables
	Key theKey;
	String content;
	
	
	//Constructor
	public DataItem(Key k, String data) {
		this.theKey = k;
		this.content = data;
		
	}
	
	//Returns the Key
	public Key getKey() {
		return theKey;
		
	}
	
	//Returns the Content
	public String getContent() {
		return content;
		
	}

}
