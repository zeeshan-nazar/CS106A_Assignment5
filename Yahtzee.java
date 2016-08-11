/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	private int[] firstRollResult = new int[N_DICE];
	private int category;
	private int[][] selectedCategory;
	public static void main(String[] args) {
		new Yahtzee().start(args);
	}
	
	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		while(nPlayers > MAX_PLAYERS)
		{
			nPlayers = dialog.readInt(" Only 4 Person Play this Game At a time, Please Enter number of players less then 5");
		}
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i);
		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		playGame();
	}

	private void playGame() {
		/* You fill this in */
		for(int turn=0; turn< N_SCORING_CATEGORIES;turn++){
			for(int playerNumber=1;playerNumber<=nPlayers;playerNumber++){
				for(int chooseDiceNumber=0; chooseDiceNumber<N_DICE;chooseDiceNumber++){
					int rolldice = rgen.nextInt(1,6);
					firstRollResult[chooseDiceNumber] = rolldice;
					
				}
				display.printMessage(playerNames[playerNumber-1] + "'s turn! Click the Roll Dicebutton to roll the dice.");
				display.waitForPlayerToClickRoll(playerNumber);
				display.displayDice(firstRollResult);
				
				display.waitForPlayerToSelectDice();
				
				
					display.printMessage("Select the dice you wish to re roll and click Roll Agian");
					
					for(int selectDice=0;selectDice<N_DICE;selectDice++){
						if(display.isDieSelected(selectDice) == true){
							int rollSelectedDice = rgen.nextInt(1,6);
							firstRollResult[selectDice] = rollSelectedDice;
						}
					}
					
					display.displayDice(firstRollResult);
				
				selectedCategory(playerNumber);
				
			}
		}
		
	}
	
	
	private void selectedCategory(int PlayerNumber){
		while(true){
			display.printMessage("Slect the category Thanks You");
		category = display.waitForPlayerToSelectCategory();
		if(selectedCategory[PlayerNumber][category] == 0){
			
			break;
		}
		
		
		
		}	
		
	}
	
/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
