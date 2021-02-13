/**
 * 
 * @author Darwin
 *		Exception for Duplicated Key
 */
public class DuplicatedKeyException extends RuntimeException{
	public DuplicatedKeyException(String msg) {
		super("Duplicated Key" + msg);
	}

}
