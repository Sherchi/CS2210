import java.io.*;
import java.util.Scanner;


/**
 * Text interface class. Takes in the lines form the file given then puts them into the ordered dictionary.
 * @author Darwin
 *	Hello Connor. Have Fun reading my code ^.^
 */
public class TextInterface {
	
	/**
	 * Initialization
	 * @param fileName
	 */
	public TextInterface(String fileName){
		
		//Creates the ordered dictionary and variables that will be used.
		/**
		 * 
		 * All lines: takes in all the lines
		 * keyNames: used to store all key names
		 * descNames: used to store all description names
		 * line: the line of the text file
		 * type: used to store the type of file it is
		 * Key: a key to pass through the put fuction of the ordered dictionary
		 * 
		 */
		OrderedDictionary dict = new OrderedDictionary();
		String[] allLines,keyNames = null, descNames = null;
		String line,type;
		Key key;
		
		/**
		 * Multimedia players
		 */
		SoundPlayer soundPlayer = new SoundPlayer();
		PictureViewer pictureViewer = new PictureViewer();
		ShowHTML htmlShower = new ShowHTML();
		RunCommand exeRunner = new RunCommand();
		
		/**
		 * Tries to read in the file. First it counts how many lines are in the file to make initlize the size of the 
		 * 			allLines arr, keyNames arr, and descNames arr.
		 */
		try {
			//Reads the file
			BufferedReader inFile1 = new BufferedReader(new FileReader(fileName));
			DataItem tempData;
			//counter for how many lines
			int counter = 0;
			//Counts the lines
			while ((line = inFile1.readLine()) != null) {
				counter ++;
			}
			//Init Arrays and reset counter
			allLines = new String[counter];
			keyNames = new String[counter/2];
			descNames = new String[counter/2];
			counter = 0;
			//reads in the lines again, this time adds the lines to the allLines arr
			BufferedReader inFile = new BufferedReader(new FileReader(fileName));

			while ((line = inFile.readLine()) != null) {
				allLines[counter] = line;
				counter ++;
				
			}
			
			/**
			 * Categorizes the keys and their corresponding descriptions. then adds makes a dataItem out of the result and adds it to the
			 * 		ordered dictionary.
			 * 
			 */
			counter = 0;
			for(int i = 0; i < allLines.length - 1; i += 2) {
				keyNames[counter] = allLines[i].toLowerCase();
				descNames[counter] = allLines[i+1].toLowerCase();
				counter ++;

				
				if (allLines[i+1].contains(".exe")) {
					type = "program";
				}
				else if(allLines[i+1].contains(".wav") || allLines[i+1].contains(".mid")) {
					type = "sound";
				}
				else if(allLines[i+1].contains(".jpg") || allLines[i+1].contains(".gif")){
					type = "picture";
					
				}else if(allLines[i+1].contains(".html")) {
					type = "url";
				}
				else {
					type = "definition";
				}
				
				key = new Key(allLines[i],type);
				tempData = new DataItem(key,allLines[i+1]);
				
				dict.put(tempData);
				
			}
		}
		//Exception Catching
		catch (IOException e) { 
			System.out.println("Error opening file. " + e.getMessage());
			System.exit(0);
		}
		catch (Exception e) {
			System.out.println("Error in input file: "+e.getMessage());
			System.exit(0);
		}
		
		
		/**
		 * The following is for the commands.
		 * Asks for a line the splits the line at the first space into two separate strings.
		 * 		then take the first string and compares that to the possible commands. If it is equal then it will execute the command.
		 * 
		 * 		The 2nd part of the string is to see what keyName they want.. For commands with 3 inputs, it will seperate the line further
		 * 			and work with those strings
		 */
		Scanner inputScanner = new Scanner(System.in);
		
		String inputLine;
		String[] tempArr;
		String command = null;
		String content = null;
	
		Key contentKey;
		String desc;
		
		do {
			//Asks for input again 
			System.out.println("Enter a command: ");
			inputLine = inputScanner.nextLine();
			tempArr = inputLine.split(" ",2);
			
			//If the input is invalid. then it will ask to reenter the command.
			//if the command is a one word command (First or last) it will not set the content.
			if(tempArr[0].equals("first") || tempArr[0].equals("last")) {
				command = tempArr[0];
			}
			else if(tempArr.length < 2) {
				do {
					System.out.println("Enter a Command");
					inputLine = inputScanner.nextLine();
					tempArr = inputLine.split(" ",2);
				}while(tempArr.length < 2);
				command = tempArr[0];
				content = tempArr[1];
			}else {
				command = tempArr[0];
				content = tempArr[1];
			}
			
			/**
			 * If the command is "get" it will try to execute every possible key with the different kinds and execute them all
			 * 
			 */
			if (command.toLowerCase().equals("get")) {
				int amountCounter = 0;
				contentKey = new Key(tempArr[1],"definition");
				DataItem data = dict.get(contentKey);
				if(data != null) {
					System.out.println(data.getContent());
					amountCounter ++;
				}
				try {
					contentKey = new Key(tempArr[1],"sound");
					data = dict.get(contentKey);
					if(data != null) {
						soundPlayer.play(data.getContent());
						amountCounter ++;
					}	
					
					contentKey = new Key(tempArr[1],"picture");
					data = dict.get(contentKey);
					if(data != null) {
						pictureViewer.show(data.getContent());
						amountCounter ++;
					}
					
					contentKey = new Key(tempArr[1],"url");
					data = dict.get(contentKey);
					if(data != null) {
						htmlShower.show(data.getContent());
						amountCounter ++;
					}
					
					contentKey = new Key(tempArr[1],"program");
					data = dict.get(contentKey);
					if(data != null) {
						exeRunner.run(data.getContent());
						amountCounter ++;
					}
					
				}catch (MultimediaException e) {
				    System.out.println(e.getMessage());
				}
				
				// If none of the types/kinds worked, then it will return that the word is not in the dictionary
				if(amountCounter == 0) {
					System.out.println("The word " + content + " Is not in the dictionary");
					DataItem pred = dict.predecessor(contentKey);
					DataItem succ = dict.successor(contentKey);
					
					System.out.println("The Preceding word is: " + pred);
					System.out.println("The Following word is: " + succ);
				}
	
			/**
			 * 
			 * If the command is "remove" it will remove the given key from the tree.
			 * 	If the key does not exist, it will tell you.
			 */
			}else if (command.toLowerCase().equals("remove")) {
				tempArr = content.split(" ");
				contentKey = new Key(tempArr[0],tempArr[1]);
				if (dict.get(contentKey) != null) {
					dict.remove(contentKey);
					System.out.println("Removed Key(" + tempArr[0] + " , " + tempArr[1] + ").");

				}else {
					System.out.println("No Record in ordered Dictionary for Key(" + tempArr[0] + " , " + tempArr[1] + ").");
				}
			/**
			 * 
			 * If the command is "add" it will add the data item with (keyName, KeyType, Desc) into the tree
			 * If the key is already there, then tells us it is.
			 */
			}else if (command.toLowerCase().equals("add")) {
				tempArr = content.split(" ", 3);
				contentKey = new Key(tempArr[0],tempArr[1]);
				desc = tempArr[2];
				
				DataItem tempData = new DataItem(contentKey,desc);
				
				if(dict.get(contentKey) == null) {
					dict.put(tempData);
					
				}else {
					System.out.println("A record with the Key (" + tempArr[0] + " , " + tempArr[1] + "). Already exists");
				}
				
			/**
			 * If the command is "list" it will list all the words that start with the given letters
			 * 
			 * If there are no words, it will tell you via a temp counter that increases every time a word is listed.
			 */
			}else if (command.toLowerCase().equals("list")) {
				int tempCounter = 0;
				for(int x = 0; x < keyNames.length; x++) {
					if (keyNames[x].startsWith(content)) {
						System.out.println(keyNames[x]);
						tempCounter ++;
					}
				}
				if(tempCounter == 0) {
					System.out.println("No works start with the prefex " + content);
				}
				
			}
			
			/**
			 * Prints out the information of the smallest word in the tree
			 */
			else if (command.toLowerCase().equals("first")) {
				System.out.print(dict.smallest().getKey().getName() + " , ");
				System.out.print(dict.smallest().getKey().getKind() + " , ");
				System.out.print(dict.smallest().getContent() + "\n");

			}
			/**
			 * Prints out the information of the largest word in the tree
			 */
			else if (command.toLowerCase().equals("last")) {
				System.out.print(dict.largest().getKey().getName() + " , ");
				System.out.print(dict.largest().getKey().getKind() + " , ");
				System.out.print(dict.largest().getContent() + "\n");

			}
			/**
			 * Ends the program
			 */
			else if (command.toLowerCase().equals("end")) {
				System.out.println("Goodbye!");
				System.exit(0);
			}

			
		}while (!command.toLowerCase().equals("end"));
	}
	
	/**
	 * takes in the file arguments and inits an instance of the textInterface
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		if (args.length < 1) {
			System.out.println("Usage: java TextInterface input_file");
			System.exit(0);
		}
		
		TextInterface program = new TextInterface(args[0]);

	}

}
