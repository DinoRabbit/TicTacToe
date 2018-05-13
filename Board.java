import java.util.Random;

public class Board
{
	Random ranGen = new Random();
	int pMove, cMove;
	int [] comMoves = new int[9];
	int [] playerMoves = new int[9];
	int [][] coordinates = new int[9][2];
	char [][] theBoard = new char[3][3];
	
	//Create the Board
	public Board ()
	{
		int x = 0, y = 0;
		pMove = 0;
		cMove = 0;
		for(int i = 0; i < 3; i++)
			for(int j = 0; j < 3; j++)
				theBoard[i][j] = 'N';
		for(int i = 0; i < 9; i++)
		{
			playerMoves[i] = -5;
			comMoves[i] = -5;
			if(y == 3)
			{
				x++;
				y = 0;
			}
			for(int j = 0; j < 2; j++)
			{
				if(j == 0)
					coordinates[i][j] = x;
				else
					coordinates[i][j] = y;
			}
			y++;
		}
	}
	
	//Function to display the current board state
	public void displayBoard()
	{
		int count = 1;
		for(int i = 0; i < 3; i++)
		{
			System.out.print("\t");
			for(int j = 0; j < 3; j++)
			{
				System.out.print("|");
				if(theBoard[i][j] == 'N')  //'N' represents a space that has not been marked with an X or O
					System.out.print(count);
				else
					System.out.print(theBoard[i][j]);
				count++;
			}
			System.out.print("|");
			System.out.println();
			if(i != 2)
				System.out.println("\t-------");
		}
		System.out.println();
		System.out.println();
	}
	
	//Test to see if there is a winner.  The boolean argument tells the function if we're looking at the player or the computer
	public boolean testWin(boolean player)
	{
		int [] testCase = new int[9];
		int start;
		if(player)
		{
			start = playerMoves[pMove - 1];
			testCase = playerMoves;
		}
		else
		{
			start = comMoves[cMove - 1];
			testCase = comMoves;
		}
		
		//Test the various win conditions i.e. vertical, horizontal, and the diaganols
		if(start == 1 || start == 2 || start == 3)
		{
			if(searchMoves(testCase, start + 3, start + 6))
				return true;
			else if(start == 1 && searchMoves(testCase, start + 4, start + 8))
				return true;
			else if(start == 3 && searchMoves(testCase, start + 2, start + 4))
				return true;
			else
				switch(start)
				{
					case 1:
						return searchMoves(testCase, 2, 3);
					case 2:
						return searchMoves(testCase, 1, 3);
					case 3:
						return searchMoves(testCase, 1, 2);
					default:
						return false;
				}
		}
		else if(start == 4 || start == 5 || start == 6)
		{
			if(searchMoves(testCase, start + 3, start - 3))
				return true;
			else if(start == 5 && searchMoves(testCase, start + 4, start - 4))
				return true;
			else if(start == 5 && searchMoves(testCase, start + 2, start - 2))
				return true;
			else
				switch(start)
				{
					case 4:
						return searchMoves(testCase, 5, 6);
					case 5:
						return searchMoves(testCase, 4, 6);
					case 6:
						return searchMoves(testCase, 4, 5);
					default:
						return false;
				}
		}
		else if(start == 7 || start == 8 || start == 9)
		{
			if(searchMoves(testCase, start - 3, start - 6))
				return true;
			else if(start == 9 && searchMoves(testCase, start - 4, start - 8))
				return true;
			else if(start == 7 && searchMoves(testCase, start - 2, start - 4))
				return true;
			else
				switch(start)
				{
					case 7:
						return searchMoves(testCase, 8, 9);
					case 8:
						return searchMoves(testCase, 7, 9);
					case 9:
						return searchMoves(testCase, 7, 8);
					default:
						return false;
				}
		}
		return false;
	}
	
	//Function to execute the player's move
	public boolean playerChoice(int a, boolean first)
	{
		int x, y;
		if(a > 9 || a < 1) //invalid move: not on the board
			return false;
		x = coordinates[a - 1][0];
		y = coordinates[a - 1][1];
		if(theBoard[x][y] == 'N')  //If the space is open
		{
			if(first)
				theBoard[x][y] = 'X';
			else
				theBoard[x][y] = 'O';
			playerMoves[pMove] = a;
			pMove++;
			return true;
		}
		else //Invalid move: choice is taken
			return false;
	}
	
	//Function to handle the computer's choice
	public void comChoice(boolean first)
	{
		int choice = -1, x, y;
		if(cMove == 0 && pMove == 0) //First turn going first, get the middle
			choice = 5;
		else if(cMove == 0 && pMove == 1) //first turn going second, if middle is taken get the upper left corner
		{
			if(playerMoves[pMove - 1] != 1)
				choice = 1;
			else
				choice = 5;
		}
		else 
		{
			while(choice == -1) //Generate a random move until a valid move (an unoccupied space) is generated
			{					//I would very much like to make this less random and a little more logical.
				choice = ranGen.nextInt(9) + 1;
				for(int i = 0; i < 9; i++)
					if(playerMoves[i] == choice || comMoves[i] == choice)
					{	
						choice = -1;
					}
			}
		}
		x = coordinates[choice - 1][0];
		y = coordinates[choice - 1][1];
		if(first)
			theBoard[x][y] = 'X';
		else
			theBoard[x][y] = 'O';
		comMoves[cMove] = choice;
		cMove++;
	}
	
	//Function to search an array for two values
	private boolean searchMoves(int [] moves, int first, int second)
	{
		boolean answer = false;
		for(int i = 0; i < moves.length; i++)
		{
			if(moves[i] == first)
			{
				answer = true;
				break;
			}
		}
		if(answer)
		{
			answer = false;
			for(int i = 0; i < moves.length; i++)
				if(moves[i] == second)
				{
					answer = true;
					break;
				}
		}
		return answer;
	}
}
