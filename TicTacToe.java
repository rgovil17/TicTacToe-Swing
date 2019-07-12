package GameDevelopment;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class TicTacToe extends JFrame implements ActionListener {
	
	public static int BOARD_SIZE = 3;
	public static enum GameStatus {
		Incomplete, XWins, OWins, Tie
	}
	private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
	boolean crossTurn = true;
	
	public TicTacToe() {
		super.setTitle("Tic-Tac-Toe");
		super.setSize(400, 400);
		GridLayout grid = new GridLayout(BOARD_SIZE, BOARD_SIZE);
		super.setLayout(grid);
		Font font = new Font("Comic Sans", 1, 75);
		for (int row = 0; row < BOARD_SIZE; row++) {
			for (int col = 0; col < BOARD_SIZE; col++) {
				JButton button = new JButton("");
				buttons[row][col] = button;
				button.setFont(font);
				button.addActionListener(this);
				super.add(button);
			}
		}
		super.setResizable(false);
		super.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton clickedButton = (JButton) e.getSource();
		makeMove(clickedButton);
		GameStatus gs = getGameStatus();
		if (gs == GameStatus.Incomplete) {
			return;
		}
		declareWinner(gs);
		int choice = JOptionPane.showConfirmDialog(this, "Do you want to play again?");
		if (choice == JOptionPane.YES_OPTION) {
			for (int row = 0; row < BOARD_SIZE; row++) {
				for (int col = 0; col < BOARD_SIZE; col++) {
					buttons[row][col].setText("");
				}
			}
			crossTurn = true;
		} else {
			super.dispose();
		}
	}

	private void makeMove(JButton clickedButton) {
		String buttonText = clickedButton.getText();
		if (buttonText.equals("")) {
			if (crossTurn) {
				clickedButton.setText("X");
			} else {
				clickedButton.setText("O");
			}
			crossTurn = !crossTurn;
		} else {
			JOptionPane.showMessageDialog(this, "Invalid Move");
		}
	}
	
	private GameStatus getGameStatus() {
		String text1 = "", text2 = "";
		int row = 0, col = 0;
		
		// text inside rows
		row = 0;
		while (row < BOARD_SIZE) {
			col = 0;
			while (col < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row][col + 1].getText();
				if (!text1.equals(text2) || text1.length() == 0)
					break;
				col++;
			}
			if (col == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.XWins;
				} else {
					return GameStatus.OWins;
				}
			}
			row++;
		}
		
		// text inside cols
		col = 0;
		while (col < BOARD_SIZE) {
			row = 0;
			while (row < BOARD_SIZE - 1) {
				text1 = buttons[row][col].getText();
				text2 = buttons[row + 1][col].getText();
				if (!text1.equals(text2) || text1.length() == 0)
					break;
				row++;
			}
			if (row == BOARD_SIZE - 1) {
				if (text1.equals("X")) {
					return GameStatus.XWins;
				} else {
					return GameStatus.OWins;
				}
			}
			col++;
		}
		
		// Diagonal 1
		row = 0;
		col = 0;
		while (row < BOARD_SIZE - 1) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row + 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0)
				break;
			row++;
			col++;
		}
		if (row == BOARD_SIZE - 1) {
			if (text1.equals("X")) {
				return GameStatus.XWins;
			} else {
				return GameStatus.OWins;
			}
		}
		
		// Diagonal 2
		row = BOARD_SIZE - 1;
		col = 0;
		while (row > 0) {
			text1 = buttons[row][col].getText();
			text2 = buttons[row - 1][col + 1].getText();
			if (!text1.equals(text2) || text1.length() == 0)
				break;
			row--;
			col++;
		}
		if (row == 0) {
			if (text1.equals("X")) {
				return GameStatus.XWins;
			} else {
				return GameStatus.OWins;
			}
		}
		
		String text = "";
		for (row = 0; row < BOARD_SIZE; row++) {
			for (col = 0; col < BOARD_SIZE; col++) {
				text = buttons[row][col].getText();
				if (text.length() == 0)
					return GameStatus.Incomplete;
			}
		}
		
		return GameStatus.Tie;
	}

	private void declareWinner(GameStatus gs) {
		if (gs == GameStatus.XWins) {
			JOptionPane.showMessageDialog(this, "X has won!");
		} else if (gs == GameStatus.OWins) {
			JOptionPane.showMessageDialog(this, "O has won!");
		} else {
			JOptionPane.showMessageDialog(this, "It is a tie!");
		}
		
	}
}
