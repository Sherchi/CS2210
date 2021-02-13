/**
 * 
 * @author Darwin
 *		Data storage
 */
public class Data {

	private String key;
	private int score;
	private int level;
	/**
	 * Constructor.
	 * @param key
	 * 		Stores the key for this item
	 * @param score
	 * 		Stores the score of the item
	 * @param level
	 * 		Stores how far down the tree this item is.
	 */

	public Data(String key, int score, int level) {
		this.key = key;
		this.score = score;
		this.level = level;
		
	}
	/*
	 * returns the key
	 */
	public String getKey() {
		return key;
		
	}
	/*
	 * returns the score
	 */
	public int getScore() {
		return score;
		
	}
	/*
	 * returns the level
	 */
	public int getLevel() {
		return level;
		
	}
}
