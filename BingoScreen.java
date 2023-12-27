import java.awt.*; 
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener; 

public class BingoScreen implements ActionListener{
	
	// fields
	JPanel panel;
	JFrame frame; 
	JLabel instructLabel; 
	JLabel bLabel; 
	JLabel ilabel; 
	JLabel nlabel;
	JLabel glabel; 
	JLabel olabel; 
	JButton nextNumButton; 
	JTextArea bingoBoard; 
	BingoCard bingoCard; 
	
	// constructor
	public BingoScreen() { 

		frame = new JFrame("Bingo Application");
		frame.setSize(1800, 1080); 
		frame.setBounds(0,0,1800,1080);

		panel = new JPanel(); 
		panel.setSize(1800,1080);
		panel.setBounds(0,0,1800,1080);
		panel.setBackground(Color.BLUE);
		panel.setLayout(null);
		frame.add(panel); 

		instructLabel = new JLabel("Click button to get next number:"); 
		instructLabel.setBounds(150,50,600,60);
		instructLabel.setFont(new Font("Sans Serif", Font.BOLD, 30)); 
		instructLabel.setForeground(Color.WHITE);
		panel.add(instructLabel);

		bLabel = new JLabel("B"); 
		bLabel.setBounds(150,250,600,60);
		bLabel.setFont(new Font("Sans Serif", Font.BOLD, 30)); 
		bLabel.setForeground(Color.BLACK);
		panel.add(bLabel);

		ilabel = new JLabel("I"); 
		ilabel.setBounds(440,250,600,60);
		ilabel.setFont(new Font("Sans Serif", Font.BOLD, 30)); 
		ilabel.setForeground(Color.BLACK);
		panel.add(ilabel);

		nlabel = new JLabel("N"); 
		nlabel.setBounds(720,250,600,60);
		nlabel.setFont(new Font("Sans Serif", Font.BOLD, 30)); 
		nlabel.setForeground(Color.BLACK);
		panel.add(nlabel);

		glabel = new JLabel("G"); 
		glabel.setBounds(1010,250,600,60);
		glabel.setFont(new Font("Sans Serif", Font.BOLD, 30)); 
		glabel.setForeground(Color.BLACK);
		panel.add(glabel);

		olabel = new JLabel("O"); 
		olabel.setBounds(1300,250,600,60);
		olabel.setFont(new Font("Sans Serif", Font.BOLD, 30)); 
		olabel.setForeground(Color.BLACK);
		panel.add(olabel);

		nextNumButton = new JButton("Generate Next Number"); 
		nextNumButton.setBounds(800,40,300,100);
		nextNumButton.addActionListener(this);
		nextNumButton.setFocusable(false);
		nextNumButton.setBackground(Color.RED);
		nextNumButton.setFont(new Font("Sans Serif", Font.BOLD, 20)); 
		panel.add(nextNumButton);

		bingoCard = new BingoCard(); 

		bingoBoard = new JTextArea(); 
		bingoBoard.setEditable(false);
		bingoBoard.setBounds(150, 300, 800, 1000);
		bingoBoard.setSize(1180, 350);
		updateBingoBoard();
		panel.add(bingoBoard); 

		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setVisible(true);	
	}

	// getter and setter methods
	public JLabel getInstructLabel() {
		return instructLabel;
	}

	public void setInstructLabel(JLabel instructLabel) {
		this.instructLabel = instructLabel;
	}

	public JButton getNextNumButton() {
		return nextNumButton;
	}

	public void setNextNumButton(JButton nextNumButton) {
		this.nextNumButton = nextNumButton;
	}

	private void updateBingoBoard() {
		int[][] cardGrid = bingoCard.getGrid();
		boolean[][] markedCells = bingoCard.getMarkedCells();
		StringBuilder boardText = new StringBuilder();
	
		for (int row = 0; row < cardGrid.length; row++) {
			for (int col = 0; col < cardGrid[row].length; col++) {
				if (markedCells[row][col]) {
					boardText.append("X\t\t\t");
				} else {
					boardText.append(cardGrid[row][col]).append("\t\t\t");
				}
			}
			boardText.append("\n\n\n\n\n");
		}
	
		bingoBoard.setText(boardText.toString());

		// Check for bingo and inform the user
		if (bingoCard.checkForBingo()) {
			instructLabel.setText("BINGO! You've hit bingo!");
		}
	}
	

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == nextNumButton) {
			int nextNumber = bingoCard.generateNextNumber();
	
			if (nextNumber == -1) {
				instructLabel.setText("All numbers have been called!");
			} else {
				instructLabel.setText("Next Number: " + nextNumber);
				bingoCard.markNumber(nextNumber); // Mark the number on the card
				updateBingoBoard(); // Update the bingo board after generating the next number
	
				// Check for bingo and inform the user
				if (bingoCard.checkForBingo()) {
					JOptionPane.showMessageDialog(frame, "BINGO! You've hit bingo!", "Bingo Notification", JOptionPane.WARNING_MESSAGE);
					int option = JOptionPane.showConfirmDialog(frame, "Do you want to start a new game?", "Game Over", JOptionPane.YES_NO_OPTION);
                
                	if (option == JOptionPane.YES_OPTION) {
						JOptionPane.showMessageDialog(frame, "Starting new game", "New Game", JOptionPane.INFORMATION_MESSAGE);
                    	// Reset the game
						bingoCard = new BingoCard();
						updateBingoBoard();
						instructLabel.setText("Click button to get next number:");
						nextNumButton.setText("Next Number");
                	} else {
						// Close existing game
						JOptionPane.showMessageDialog(frame, "Thank you for playing!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
						frame.dispose();
						System.exit(option);
                	}
				}
			}
		}
	}
}
