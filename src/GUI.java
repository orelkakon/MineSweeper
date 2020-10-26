import java.util.*;

public class GUI {
	public static void main (String[]args) {
		Scanner input = new Scanner(System.in);
		System.out.println("Wellcome To Mine Sweeper !");
		System.out.println();
		System.out.println("Please Enter Your Name: ");
		String playerName = input.next();
		while(true) {
			System.out.println();
			System.out.println("Please Select Level for Game: \n"
					+ "[1] - Easy \n"
					+ "[2] - Medium \n"
					+ "[3] - Hard ");
			String gameLevel = input.next();
			BoardGame board;
			try {
				board = new BoardGame(Integer.parseInt(gameLevel));
			}catch(Exception e) {
				System.out.println("TRY AGAIN");
				continue;
			}
			board.RandomizeBoard();
			board.PrintBoard();
			while(board.NotWin()) {
				System.out.println("Please Enter Index In Width To Select [between 1 - "+ board.GetSize() +"]");
				String width = input.next(); 
				System.out.println("Please Enter Index In Height To Select [between 1 - "+ board.GetSize() +"]");
				String height = input.next(); 
				try {
					boolean notGameOver = board.MakeTurn(Integer.parseInt(width) - 1, Integer.parseInt(height) - 1);
					if(notGameOver == false) {
						System.out.println(playerName+" You Push On Bomb - GAME OVER !");
						board.PrintBoard();
						input.close();
						System.exit(0);
					}
				}catch(Exception e) {
					if(width.contains("AF") && width.contains("AF")) {
						if(board.GetSize() == 8)
							board.AddFlag(Integer.parseInt(height.charAt(0)+"") - 1, Integer.parseInt(width.charAt(0)+"") - 1);
						else {
							String first = height.charAt(0)+""; 
							String second = width.charAt(0)+"";
							if(height.charAt(1) >= '0' && height.charAt(1) <= '9') {
								first = first + height.charAt(1);
							}
							if(width.charAt(1) >= '0' && width.charAt(1) <= '9') {
								second = second + width.charAt(1);
							}
							board.AddFlag(Integer.parseInt(first+"") - 1, Integer.parseInt(second+"") - 1);
						}
					}
					else if(width.contains("DF") && width.contains("DF")) {
						if(board.GetSize() == 8)
							board.DeleteFlag(Integer.parseInt(height.charAt(0)+"") - 1, Integer.parseInt(width.charAt(0)+"") - 1);
						else {
							String first = height.charAt(0)+""; 
							String second = width.charAt(0)+"";
							if(height.charAt(1) >= '0' && height.charAt(1) <= '9') {
								first = first + height.charAt(1);
							}
							if(width.charAt(1) >= '0' && width.charAt(1) <= '9') {
								second = second + width.charAt(1);
							}
							board.DeleteFlag(Integer.parseInt(first+"") - 1, Integer.parseInt(second+"") - 1);
						}
					}
					else {
					System.out.println("TRY - AGAIN");
					continue;
					}
				}
				board.PrintBoard();
			}
			System.out.println(playerName + " You Won Game ,Congratulations !!! \n\n");
			System.out.println("Please Select : \n"
					+ "[1] - Exit Game \n"
					+ "[2] - Play Again");
			String choice = input.next();
			try {
				if(Integer.parseInt(choice) == 1) {
					System.out.println("Bye Bye " + playerName );
					input.close();
					System.exit(0);
				}
			}catch(Exception e) {
				System.out.println("Bye Bye " + playerName );
				System.exit(0);
			}
		}
	}
}