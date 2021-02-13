/**
 * 
 * @author Darwin
 *	Inexistent Key Exception
 */
public class InexistentKeyException extends RuntimeException{
	public InexistentKeyException(String msg) {
		super("Inexistent Key" + msg);
	}
}
