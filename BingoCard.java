import java.util.*; 

// Class creates & contains the Bingo card with numbers 
public class BingoCard {
	
	// fields - array for bingo card
	private int [][] grid = new int [5][5];
	private Set<Integer> calledNumbers = new HashSet<>();
	private boolean[][] markedCells = new boolean[5][5];

	// constructor
	public BingoCard() {
		createCard(); 
	}

	// getter and setter methods
	public boolean[][] getMarkedCells() {
        return markedCells;
    }
	
	public int[][] getGrid() {
		return grid;
	}

	public void setGrid(int[][] grid) {
		this.grid = grid;
	}

	// toString method
	public String toString() {
		return "BingoCard [grid=" + Arrays.toString(grid) + "]";
	} 
	
	// Creates the template of the card using stacks
	private void createCard() { 
		
		// Goes through the columns 
		for (int col= 0; col < grid.length; col++) { 
			
			// Creates a stack data structure to hold the potential numbers to be called
			Stack<Integer> numList = new Stack<Integer>(); 
			
			// Goes through the 15 spaces in the card
			for (int index = 1; index <= 15; index++) 
				numList.add(index + col * 15); 
			
			Collections.shuffle(numList);
			
			// goes through the rows
			for (int row = 0; row < grid[col].length; row++) { 
				
				grid[row][col] = numList.pop();   // set spot to the number
			}
		}
		
		grid[2][2] = 0;    // create a free space in the middle
	}

	
	// Indicates if a bingo has occurred (5 numbers/spaces have to be covered in a row)
	// Can occur in a vertical, horizontal, or diagonal line
	public boolean checkForBingo() { 
		
		// Check rows
		for (int row = 0; row < grid.length; row++) { 
			boolean rowBingo = true;
			for (int col = 0; col < grid[row].length; col++) {
				if (!markedCells[row][col]) {
					rowBingo = false;
					break;
				}
			}
			if (rowBingo) {
				return true;
			}
		}
	
		// Check columns
		for (int col = 0; col < grid[0].length; col++) { 
			boolean colBingo = true;
			for (int row = 0; row < grid.length; row++) {
				if (!markedCells[row][col]) {
					colBingo = false;
					break;
				}
			}
			if (colBingo) {
				return true;
			}
		}
	
		// Check diagonals
		boolean diagonal1Bingo = true;
		boolean diagonal2Bingo = true;
		for (int index = 0; index < grid.length; index++) {
			if (!markedCells[index][index]) {
				diagonal1Bingo = false;
			}
			if (!markedCells[index][grid.length - 1 - index]) {
				diagonal2Bingo = false;
			}
		}
		if (diagonal1Bingo || diagonal2Bingo) {
			return true;
		}
	
		return false;
	}

	  // Generates the next number that hasn't been called before
	  public int generateNextNumber() {
        List<Integer> availableNumbers = new ArrayList<>();

        // Populate availableNumbers with all possible numbers that haven't been called
        for (int col = 0; col < grid.length; col++) {
            for (int index = 1; index <= 15; index++) {
                int number = index + col * 15;
                if (!calledNumbers.contains(number)) {
                    availableNumbers.add(number);
                }
            }
        }

        if (availableNumbers.isEmpty()) {
            // All numbers have been called
            return -1; // Indicate that no more numbers are available
        }

        // Shuffle numbers
        Collections.shuffle(availableNumbers);

        // Get next number
        int nextNumber = availableNumbers.get(0);

        // Mark number as called
        markNumberCalled(nextNumber);

        return nextNumber;
    }

    // Mark number as called
    private void markNumberCalled(int number) {
        calledNumbers.add(number);
    }

	// Marks off the specified number on the card with an "X"
    public void markNumber(int number) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == number) {
                    markedCells[row][col] = true;
                    return; // Exit the method once the number is marked
                }
            }
        }
    }

}
