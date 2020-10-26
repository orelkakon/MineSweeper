import java.util.LinkedList;
import java.util.Random;

public class BoardGame {
	private int size;
	private int numMines;
	private char[][] board;
	private LinkedList<Pair> visitedBlocks = new LinkedList<Pair>();
	private LinkedList<Pair> flagBlocks = new LinkedList<Pair>();

	public BoardGame(int chosenBoard) {
		if(chosenBoard == 1) {
			size = 8;
			numMines = 12;
		}
		else if(chosenBoard == 2){
			size = 16;
			numMines = 50;
		}
		else {  //chosenBoard == 3
			size = 25;
			numMines = 125;
		}
	}
	
	public void RandomizeBoard() {
		board = new char[size][size];
		ResetBoard();
		Random rand = new Random();
		int i = numMines;
		while (i != 0) {
			int num = rand.nextInt(size*size);
			if(board[num / size][num % size] != '*') {
				board[num / size][num % size] = '*';
				i--;	
			}
		}
	}

	public void ResetBoard() {
		try {
			for (int i = 0; i < size; i++) {
				for (int j = 0; j < size; j++) {
					board[i][j] = '-';
				}
			}
		}
		catch(Exception e) {
			// not need to reset
		}
	}

	public void PrintBoard() {
		System.out.println();
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(board[i][j] == '*' || board[i][j] == '-')
					if(ContainFlag(i, j))
						System.out.print("$"+" ");
					else
						System.out.print("#"+" ");
				else
					System.out.print(board[i][j]+" ");
			}
			System.out.println();
		}
		System.out.println();
	}

	private boolean ContainFlag(int x , int y) {
		for(int i = 0; i < flagBlocks.size(); i++) {
			if(flagBlocks.get(i).first == y && flagBlocks.get(i).second == x)
				return true;
		}
		return false;
	}
	
	public boolean MakeTurn(int x, int y) {
		if(IsBadGuess(x, y)) {
			board[y][x] = 'X';
			return false; //lose game
		}
		else {
			int num = NeighborsNum(x, y);
			if(num > 0) {
				board[y][x] = (char) (num + 48);
				return true;
			}
			else { //pop all 0 neighbors
				visitedBlocks.add(new Pair(y,x));
				if(num == 0) {
					board[y][x] = '0';
				}
				PopAllNeighbors(x, y);
				visitedBlocks = new LinkedList<Pair>(); // reset
				return true;
			}
		}
	}
	
	private void PopAllNeighbors(int i , int j) {
		for(int x = i - 1; x < i + 2; x++) {
			for (int y = j - 1; y < j + 2; y++) {
				int num = NeighborsNum(x, y);
				if(num > 0) {
					try {
						if(!IsBadGuess(x, y))
							board[y][x] = (char) (num + 48);
					}
					catch(Exception e) {
						
					}
				}
				else { //pop all 0 neighbors
					if(isLegal(x,y) && !WasVisit(x,y)) {
						if(num == 0) {
							board[y][x] = '0';
						}
						visitedBlocks.add(new Pair(y,x));
						PopAllNeighbors(x, y);
					}
				}
			}
		}
	}

	private boolean WasVisit(int x, int y) {
		for(int i = 0; i < visitedBlocks.size(); i++) {
			if(visitedBlocks.get(i).first == y && visitedBlocks.get(i).second == x)
				return true;
		}
		return false;
	}

	private boolean isLegal(int x, int y) {
		if(x<0 || x>=size || y<0 || y>=size)
			return false;
		return true;
	}
	
	private boolean IsBadGuess(int x , int y) {
		if(board[y][x] == '*')
			return true;
		return false;
	}
	
	private int NeighborsNum(int j , int i) { //first width , second height
		int num = 0;
		for(int x = i - 1; x < i + 2; x++) {
			for (int y = j - 1; y < j + 2; y++) {
				try {
					if(board[x][y] == '*') {
						if(x!=i || y!=j)
							num++;
					}
				}
				catch(Exception e) {
					
				}
			}
		}
		return num;
	}

	
	public int GetSize() {
		return size;
	}

	public boolean NotWin() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				if(board[i][j] == '-')
					return true;
			}
		}
		return false;
	}
	
	public void AddFlag(int x , int y) {
		if(!ContainFlag(x, y))
			flagBlocks.add(new Pair(y, x));
	}
	
	public void DeleteFlag(int x , int y) {
		for(int i = 0; i < flagBlocks.size(); i++) {
			if(flagBlocks.get(i).first == y && flagBlocks.get(i).second == x)
				flagBlocks.remove(i);
		}
	}
}


