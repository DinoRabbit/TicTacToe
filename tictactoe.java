import java.util.Scanner;
import java.lang.Object;
import java.util.Random;

//Simple and straight forward, a game of tic-tac-toe.  Uses Board.java

public class tictactoe
{
	public static void main (String [] args)
	{
		char play;
		Scanner reader = new Scanner(System.in);
		Random ranGen = new Random();
		System.out.println("Welcome to Tic Tac Toe.");
		System.out.println("Play a game?:(y/n)");
		play = reader.nextLine().charAt(0);
		play = Character.toLowerCase(play);
		while(play == 'y')
		{
			int choice, coin, turn = 0;
			boolean first, pFirst, cFirst, pTurn = false, pWin = false, cWin = false;
			Board theBoard = new Board();
			System.out.println("New Game!");
			
			//Start with a coin toss to determine who goes first.
			coin = ranGen.nextInt(2);
			if(coin == 0) //Tails: Com always chooses to go first
			{
				System.out.println("Coin toss is Tails, Computer chooses to go first.");
				first = false;
				cFirst = true;
				pFirst = false;
			}
			else //Heads: Player chooses whether to go first or second
			{
				System.out.println("Coin toss is Heads.  Player wins, would you like to go first or second:(1/2)");
				choice = reader.nextInt();
				if(choice == 1) //player chooses first
				{
					first = true;
					pFirst = true;
					cFirst = false;
				}
				else //player chooses second
				{
					first = false;
					pFirst = false;
					cFirst = true;
				}
			}
			while(true) //Game logic
			{
				turn++;
				choice = 0;
				pTurn = false;
				theBoard.displayBoard();
				if(first) //if player goes first
				{
					while(pTurn == false)  //prompt for player choice until it is valid
					{	
						System.out.println("Please choose a square:(1-9)");
						choice = reader.nextInt();
						pTurn = theBoard.playerChoice(choice, pFirst);
					}
					pWin = theBoard.testWin(true);  //test if the player has won and break the loop if so
					if(pWin)
						break;
					if(turn == 5)  //Break after 5 turns (no winner possible at that point)
						break;
					else  //get the Computer's choice and see if it won
					{
						theBoard.comChoice(cFirst);
						cWin = theBoard.testWin(false);
						if(cWin)
							break;				
					}
					theBoard.displayBoard();
				}
				if(!first) //if player is second, basically the same, but turns are taken in reverse order
				{
					theBoard.comChoice(cFirst);
					cWin = theBoard.testWin(false);
					if(cWin)
						break;
					theBoard.displayBoard();
					if(turn == 5)
						break;
					else
					{
						while(pTurn == false)
						{	
							System.out.println("Please choose a square:(1-9)");
							choice = reader.nextInt();
							pTurn = theBoard.playerChoice(choice, pFirst);
						}
						pWin = theBoard.testWin(true);
						if(pWin)
							break;
					}
				}
			}
			theBoard.displayBoard();
			
			//Display whether the player has won, lost, or the game was a drawy
			if(pWin)
				System.out.println("You Win!");
			else if(cWin)
				System.out.println("You Lose!");
			else
				System.out.println("Draw.");
			
			//Ask the player if they want to play again
			System.out.println("Would you like to play again?:(y/n)");
			reader.nextLine();
			play = reader.nextLine().charAt(0);
			play = Character.toLowerCase(play);
		}
		System.out.println("Goodbye.");
	}
}