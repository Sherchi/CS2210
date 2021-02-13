/**
 * 
 * @author Darwin
 * Evaluation Class. Evaluates the state of the board.
 */
public class Evaluate {
	
	private int rows;
	private int cols;
	private int tiles;
	private char[][] gameBoard;
	private Dictionary newDictionary;

	/*
	 * Initilization
	 */
	public Evaluate(int boardRows, int boardColumns, int tilesNeeded, int maxLevels) {
		rows = boardRows;
		cols = boardColumns;
		tiles = tilesNeeded;
		gameBoard = new char[rows][cols];
		for (int row = 0; row <= rows - 1; row++) {
			for (int col = 0; col <= cols - 1; col++){
				gameBoard[row][col] = 'g';
			}
		}
		
	}
	/*
	 * creates a dictionary for the board.
	 */
	public Dictionary createDictionary() {
		newDictionary = new Dictionary(9887);
		
		return newDictionary;
	}
	/*
	 *  makes a String for the board config and checks if it is already in the dictionary.
	 *  If it is, returns the data at that config, if it isn't returns null.
	 */
	public Data repeatedConfig(Dictionary dict) {
		String boardString = "";
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				boardString = boardString + gameBoard[x][y];
			}
		}
		
		if(newDictionary.get(boardString) != null) {
			return newDictionary.get(boardString);
		}else {
			return null;
		}

	}
	/*
	 * Stores a new config into the dictionary
	 */
	public void insertConfig(Dictionary dict, int score, int level) {
		String boardString = "";
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				boardString = boardString + gameBoard[x][y];
				
			}
		}
		Data config = new Data(boardString,score,level);
		newDictionary.put(config);
		
	}
	
	/*
	 * stores the symbol into the index
	 */
	public void storePlay(int row, int col, char symbol) {
		gameBoard[row][col] = symbol;
		
	}
	/*
	 * checks if the tile is empty/is not a tile with a piece.
	 */
	public boolean squareIsEmpty(int row,int col) {
		if (gameBoard[row][col] == 'g') {
			return true;
			
		}else {
			return false;
		}
	}
	
	/*
	 * Checks if the tile is a computer piece 
	 */
	public boolean tileOfComputer(int row, int col) {
		
		if (gameBoard[row][col] == 'o') {
			return true;
			
		}else {
			return false;
		}	
	}
	/*
	 * checks if the tile is a human piece
	 */
	public boolean tileOfHuman(int row, int col) {
		if ( gameBoard[row][col] == 'b') {
			return true;
			
		}else {
			return false;
		}
	}
	/*
	 * Checks if there is a victory with the symbol given.
	 */
	public boolean wins(char symbol) {
		
		/*
		 * Variables for victory types. One for Horizontal, one for verticle, two for each diagonal.
		 */
		boolean isWonV,isWonH,isWonD1,isWonD2;
		isWonV = false;
		isWonH = false;
		isWonD1 = false;
		isWonD2 = false;
		
		/*
		 * goes through each value of the board matrix starting from top left
		 */
		for (int row = 0; row < gameBoard.length; row++) {
			for (int col = 0; col < gameBoard[row].length; col++) {
				if(symbol == gameBoard[row][col]) {
	
					/*
					 * Checks if starting tile has enough space for a victory, then
					 * Checks for Vertical Victory going from top to bottom starting at the tile. If there isn't a line, breaks out of checking the rest.
					 */
	
					if (row <= gameBoard.length - tiles) {
						for (int i = 1; i < tiles; i++) {
							if (symbol == gameBoard[row + i][col]) {
								isWonV = true;
							}else {
								isWonV = false;
								break;
	
							}
						}
					}
					/*
					 * Checks if starting tile has enough space for a victory, then
					 * Checks for Horizontal Victory going from left to right starting at the tile. If there isn't a line, breaks out of checking the rest.
					 */
					if (col <= gameBoard[row].length - tiles) {
						for (int i = 1; i < tiles; i++) {
							if (symbol == gameBoard[row][col + i]) {
								isWonH = true;
							}else {
								isWonH = false;
								break;
	
							}
						}
					}
					/*
					 * Checks if starting tile has enough space for a victory, then
					 * Checks for Diagonal Victory from Top Left -> Bottom Right starting at the tile. If there isn't a line, breaks out of checking the rest.
					 */
					if	(row <= gameBoard.length - tiles && col <= gameBoard[row].length - tiles) {
						for (int i = 1; i < tiles; i++) {
							if (symbol == gameBoard[row + i][col + i]) {
								isWonD1 = true;
							}else {
								isWonD1 = false;
								break;
	
							}
						}
					}
					/*
					 * Checks if starting tile has enough space for a victory, then
					 * Checks for Diagonal Victory from Top Right -> Bottom Left starting at the tile. If there isn't a line, breaks out of checking the rest.
					 */
					if (row <= gameBoard.length - tiles && col >= tiles - 1) {
						for (int i = 0; i < tiles; i++) {
							if (symbol == gameBoard[row + i][col - i]) {
								isWonD2 = true;
							}else {
								isWonD2 = false;
								break;
	
							}
						}
					}
					
					if(isWonV || isWonH || isWonD1 || isWonD2) {
						return true;
					}
				}
			}
		}
		return false;
	}
	/*
	 * If the board is full then its a draw
	 * 
	 */
	public boolean isDraw() {
		for (int x = 0; x < rows; x++) {
			for (int y = 0; y < cols; y++) {
				if (gameBoard[x][y] == 'g') {
					return false;
				}
			}
		}
		return true;
	}
	
	/*
	 * Returns a value so that the AI can calculate how imporant it's next move should be.
	 */
	public int evalBoard() {
		/*
		 * if there is a victory, check who won.
		 */
		if(wins('o')) {
			return 3;
		}
		if(wins('b')) {
			return 0;
		}
		/*
		 * If its a draw
		 */
		if(isDraw()) {
			return 2;
		}
		/*
		 * if undecided.
		 */
		return 1;
	}
	
}
