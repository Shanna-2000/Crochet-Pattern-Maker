// This code tracks the stiches and rows in a crochet pattern and 
// creates a word file with a working pattern of it
import java.util.*;
import java.io.*;

public class CrochetPatternMaker{
	//* making all the variables public
	public String[] info = new String[4];
	public String[] yarnColor;
	public int totalStiches = 0;
	public int totalRows = 100;
	public String[] rows = new String[100];
	public String startColor;
	Scanner console = new Scanner(System.in);
	
	void main()throws FileNotFoundException{
		//Scanner console = new Scanner(System.in);

		//* finds out name of pattern
		System.out.print("Name of pattern: ");
		String name = console.nextLine();
		info[0] = name.toUpperCase();
		
		//* finds out what size of crochet hook is needed
		System.out.print("Size of hook(in mm): ");
		String hook = console.nextLine();
		info[1] = hook;
		
		//* finds out what weight of yarn is needed
		System.out.print("Weight of yarn(integer): ");
		int yarnWeight = console.nextInt();
		weightTest(yarnWeight);
		
		//* finds out what type of yarn is needed
		System.out.print("Type of yarn: ");
		String yarnType = console.next();
		info[3] = yarnType;
		
		//* finds out how many and what colors are needed
		System.out.print("How many colors(integer)? ");
		int numberOfColors = console.nextInt();
		String[] color = new String[numberOfColors];
		//info[4] = yarnColor;
		if(numberOfColors == 1){
			System.out.print("Color: ");
			color[0] = console.next();
			startColor = color[0];
		}else{
			for(int i = 1; i <= numberOfColors; i++){
				System.out.print("Color " + i + ": ");
				color[i - 1] = console.next();
			}
			System.out.print("Starting color: ");
			startColor = console.next();
		}
		yarnColor = color;
		
		patternStart();
		patternRows();
		
		for(int i = 0; i < totalRows; i++){
			if(rows[i] == null){
				totalRows = i;
			}
		}
		getRows();
		
		//*creating the file
		File pattern = new File("\"" + info[0] + ".txt\"");
		PrintStream out = new PrintStream(info[0]);
		
		out.println(info[0]);
		out.println("MATERIALS");
		out.println("  " + info[3] + " " + info[2]);
		for(int i = 0; i < yarnColor.length; i++){
			out.println("   " + yarnColor[i]);
		}
		out.println("  Crochet hook, " + info[1] + " mm");
		
		out.println(rows[0]);
		out.println("Row 1: " + rows[1] + ". (" + totalStiches + ")");
		for(int i = 2; i < rows.length - 1; i++){
			out.println("Row " + i + ": " + rows[i] + ". (" + count(rows[i]) + ")");
		}
		out.println("Finish off.");
	}
	
	// changes yarn weight into desired format
	public void weightTest(int yarn){
		if(yarn == 1){
			info[2] = "Super Fine Weight Yarn(1)";
		}else if(yarn == 2){
			info[2] = "Fine Weight Yarn(2)";
		}else if(yarn == 3){
			info[2] = "Light Weight Yarn(3)";
		}else if(yarn == 4){
			info[2] = "Medium Weight Yarn(4)";
		}else if(yarn == 5){
			info[2] = "Bulky Weight Yarn(5)";
		}else if(yarn == 6){
			info[2] = "Super Bulky Weight Yarn(6)";
		}
	}
	
	public void patternStart(){
		System.out.println("Starting with a magic ring.");
		rows[0] = "With " + startColor.toLowerCase() + ", ch 3 loosely; being careful not to twist ch, and join with slip st to form a ring.";
		console.nextLine();
		System.out.print("Row 1: ");
		rows[1] = console.nextLine();
		System.out.print("How many stitches in row 1? ");
		totalStiches = console.nextInt();
		console.nextLine();		//makes sure scanner is on next line
	}
	
	// couldn't get this program to work with chain starts. only with magic rings
	/*public void patternStart(){
		
		boolean done = false;	//testing to see if input was valid
		while(!done){
			System.out.print("Start (magic ring OR Ch): ");
			console.nextLine();		//makes sure scanner is on a new line
			String start = console.nextLine();
			start = start.toLowerCase();
			if(start.equals("magic ring")){
				rows[0] = "With " + startColor.toLowerCase() + ", ch 3 loosely; being careful not to twist ch, and join with slip st to form a ring.";
				System.out.print("Row 1: ");
				rows[1] = console.nextLine();
				System.out.print("How many stitches in row 1? ");
				totalStiches = console.nextInt();
				console.nextLine();		//makes sure scanner is on next line
				done = true;
			}else if (start.equals("ch")){
				System.out.print("How many? ");
				int chain = console.nextInt();
				console.nextLine();			//makes sure scanner is on a new line
				System.out.print("What chain do you start in?(From hook and in integers) ");
				totalStiches = chain - console.nextInt() + 1;
				console.nextLine();
				rows[0] = "With " + startColor.toLowerCase() + ", ch " + chain + " loosely.";
				System.out.print("Row 1: ");
				rows[1] = console.nextLine();
				done = true;
			}else{
				System.out.println("Invalid response");
			}
		}
		
	}*/
	
	// this method tracks the rows and what is happening in them
	public void patternRows(){
		boolean done = false;
		do{
			System.out.println("Type COMPLETE when pattern is finished.");
			for(int i = 2; i < rows.length; i++){
				System.out.print("Row " + i + ": ");
				String row = console.nextLine();
				if(row.equals("")){
					System.out.println("Row cannot be blank");
					System.out.print("Row " + i + ": ");
					row = console.nextLine();
				}
				rows[i] = row;
				//System.out.println(rows[i]);
				if(row.toUpperCase().equals("COMPLETE")){
					done = true;
					return;
				}
			}
		}while(!done);
	}
	
	
	
	public int count(String row){
		Scanner scan = new Scanner(row);
		
		//for(int i = 2; i < totalRows; i++){
			int lastRow = totalStiches;	//keeps track of last row of stitches
			int stitch = 0;		//tracks what stitch we are working in
			int num = 0;
			int addStitches = 0;
			boolean done = false;
			do{
				String next = scan.next();
				if(next.equals("sc") || next.equals("hdc") || next.equals("dc") || next.equals("tr") || next.equals("ch") || next.equals("sc,") || next.equals("hdc,") || next.equals("dc,") || next.equals("tr,") || next.equals("ch,")){
					if(num > 0){
						stitch += num;
						num = 0;
					}else{
						stitch += 1;		//working in next stitch
					}
				}else if(next.equals("inc")){
					if(num > 0){
						addStitches += num * 2;
						totalStiches += num * 2;
					}else{
						addStitches += 1;
						totalStiches += 1;
					}
				}else if(next.equals("dec")){	//working in two stitches at same time
					if(num > 0){
						stitch += num * 2 - 1;
						addStitches -= num * 2;
						totalStiches -= num * 2;
						num = 0;
					}else{
						stitch += 1;
						addStitches -= 1;
						totalStiches -= 1;
					}
				}else if(Character.isDigit(next.charAt(0))){
					num = 0;
					if(next.length() == 3){
						num = Character.getNumericValue(next.charAt(0))*100 + Character.getNumericValue(next.charAt(1))*10 + Character.getNumericValue(next.charAt(2));
						
					}else if(next.length() == 2){
						num = Character.getNumericValue(next.charAt(0))*10 + Character.getNumericValue(next.charAt(1));
						
					}else if(next.length() == 1){
						num = Character.getNumericValue(next.charAt(0));
						
					}
				}else if(next.equals("round") || next.equals("across")){
					int loop = stitch;
					while(stitch != lastRow){
						stitch += loop;
						totalStiches += addStitches;
					}
					done = true;
				/*}else if(scan.hasNext()){
					num = 0;*/
				}else{
					done = true;
				}
				/*System.out.println(next);
				System.out.println(num);
				System.out.println(lastRow);
				System.out.println(stitch);
				System.out.println(addStitches);
				System.out.println(totalStiches);*/
				
			}while(!done);
		return totalStiches;
	}
	
	public void getRows(){
		rows = Arrays.copyOf(rows, totalRows);
	}
	

}

