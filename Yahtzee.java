/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.util.ArrayList;

import acm.io.*;
import acm.program.*;
import acm.util.*;

public class Yahtzee extends GraphicsProgram implements YahtzeeConstants {
	
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
		for(int round=0; round< N_SCORING_CATEGORIES;round++){
			for(int playerNumber=1;playerNumber<=nPlayers;playerNumber++){
				
				firstRoll(playerNumber);
				nextTwoRoll(playerNumber);
				selectedCategory(playerNumber);
				
			}
		}
		
	}
	private void firstRoll(int playerNumber){
		for(int chooseDiceNumber=0; chooseDiceNumber<N_DICE;chooseDiceNumber++){
			int rolldice = rgen.nextInt(1,6);
			dieRollResult[chooseDiceNumber] = rolldice;
			
		}
		display.printMessage(playerNames[playerNumber-1] + "'s turn! Click the Roll Dicebutton to roll the dice.");
		display.waitForPlayerToClickRoll(playerNumber);
		display.displayDice(dieRollResult);
	}
	
	
	
	private void nextTwoRoll(int playerNumber){
		for(int re_rollDice=0;re_rollDice<2;re_rollDice++){
			display.printMessage("Select the dice you wish to re roll and click Roll Agian");
			display.waitForPlayerToSelectDice();
				for(int selectDice=0;selectDice<N_DICE;selectDice++){
		
					if(display.isDieSelected(selectDice) == true){
						int rollSelectedDice = rgen.nextInt(1,6);
						dieRollResult[selectDice] = rollSelectedDice;
			}
					
		}
				display.displayDice(dieRollResult);
		}
	}
	
	
	
	private void selectedCategory(int PlayerNumber){
		while(true){
			display.printMessage("Slect the category Thanks You");
		category = display.waitForPlayerToSelectCategory();
		if(selectedCategory[PlayerNumber][category] == 0){
			categoryResultCalculating(PlayerNumber);
			break;
		}
	
		
		}	
		
	}
	
	private void categoryResultCalculating(int PlayerNumber){
		int categoryScore;
		//selectedCategory[PlayerNumber][category] = 1;
		
		if(checkCategory(dieRollResult,category) == true){
			setCategoryScore(PlayerNumber, category);
			categoryScore = categoryScores[PlayerNumber][category];
			display.updateScorecard(category, PlayerNumber,categoryScore);
			
		}
		
		else {
			categoryScores[PlayerNumber][category] = 0;
			display.updateScorecard(category, PlayerNumber, 0);

	}
}
	
	private void setCategoryScore(int playerNumber, int category) {
		int score = 0; 
		if(category >= ONES && category <= SIXES) {
			for(int i = 0; i < N_DICE; i++) {
				 if(dieRollResult[i] == category) {
					 score += category;
				 }
			 }
		}
		else if(category == THREE_OF_A_KIND || category == FOUR_OF_A_KIND || category == CHANCE) {
			for(int i = 0; i<N_DICE; i++) {
				score += dieRollResult[i];
			}
		}
		else if(category == FULL_HOUSE) {
			score = 25;
		}
		else if(category == SMALL_STRAIGHT) {
			score = 30;
		}
		else if(category == LARGE_STRAIGHT) {
			score = 40;
		}
		else if(category == YAHTZEE) {
			score = 50;
		}
		categoryScores[playerNumber][category] = score;
	}

	//this function given in assignment document but is write in our own code.
	
	private boolean checkCategory(int[] dice, int category) {
		boolean categoryMatch = false;
		if(category >= ONES && category <= SIXES || category == CHANCE) {
			categoryMatch = true;
		}
		else {
			
			//array list for one to six category store values
			ArrayList <Integer> ones = new ArrayList<Integer>();  
			ArrayList <Integer> twos = new ArrayList<Integer>(); 
			ArrayList <Integer> threes = new ArrayList<Integer>(); 
			ArrayList <Integer> fours = new ArrayList<Integer>(); 
			ArrayList <Integer> fives = new ArrayList<Integer>(); 
			ArrayList <Integer> sixes = new ArrayList<Integer>();
			
			for(int i = 0; i < N_DICE; i++) {
				if(dice[i] == 1) {
					ones.add(1);
				}
				else if(dice[i] == 2) {
					twos.add(1);
				}
				else if(dice[i] == 3) {
					threes.add(1);
				}
				else if(dice[i] == 4) {
					fours.add(1);
				}
				else if(dice[i] == 5) {
					fives.add(1);
				}
				else if(dice[i] == 6) {
					sixes.add(1);
				}
			}
			if(category == THREE_OF_A_KIND) {
				if(ones.size() >= 3 || twos.size() >= 3 || threes.size() >= 3 || fours.size() >= 3 || fives.size() >= 3 || sixes.size() >= 3) {
					categoryMatch = true;
				}
			}	
			else if(category == FOUR_OF_A_KIND) { 
				if(ones.size() >= 4 || twos.size() >= 4 || threes.size() >= 4 || fours.size() >= 4 || fives.size() >= 4 || sixes.size() >= 4) {
					categoryMatch = true;
				}
			}
			else if(category == YAHTZEE) {
				if(ones.size() == 5 || twos.size() == 5 || threes.size() == 5 || fours.size() == 5 || fives.size() == 5 || sixes.size() == 5) {
					categoryMatch = true;
				}
			}
			else if(category == FULL_HOUSE) {
				if(ones.size() == 3 || twos.size() == 3 || threes.size() == 3 || fours.size() == 3 || fives.size() == 3 || sixes.size() == 3) {
					if(ones.size() == 2 || twos.size() == 2 || threes.size() == 2 || fours.size() == 2 || fives.size() == 2 || sixes.size() == 2) {
						categoryMatch = true;
					}
				}
			}	
			else if(category == LARGE_STRAIGHT) { 
				if(ones.size() == 1 && twos.size() == 1 && threes.size() == 1 && fours.size() == 1 && fives.size() == 1){
					categoryMatch = true;
				}
				else if(twos.size() == 1 && threes.size() == 1 && fours.size() == 1 && fives.size() == 1 && sixes.size() == 1) {
					categoryMatch = true;
				}
			}
			else if(category == SMALL_STRAIGHT) { 
				if(ones.size() >= 1 && twos.size() >= 1 && threes.size() >= 1 && fours.size() >= 1) {
					categoryMatch = true;
				}
				else if(twos.size() >= 1 && threes.size() >= 1 && fours.size() >= 1 && fives.size() >= 1) {
					categoryMatch = true;
				}
				else if(threes.size() >= 1 && fours.size() >= 1 && fives.size() >= 1 && sixes.size() >= 1) {
					categoryMatch = true;
				}
			}
		}
		return categoryMatch;
	}
	
/* Private instance variables */
	private int[] dieRollResult = new int[N_DICE];
	private int category;
	private int[][] selectedCategory;
	private int[][] categoryScores;
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private RandomGenerator rgen = new RandomGenerator();

}
