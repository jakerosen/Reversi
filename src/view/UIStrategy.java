package view;

import java.io.PrintStream;
import java.util.LinkedList;

import model.Board;
import model.Model;
import model.Position;
import model.Tile;

/**
 * Defines a strategy for displaying output to the user.  An output strategy must be able to display the board and also
 * be able to display PrintStream messages to the user.
 */
public interface UIStrategy {
  /**
   * Displays the board.
   *
   * @param board The board.
   */
  public abstract void initBoard(Board board);

  /**
   * Resets the board display
   *
   * @param board The board.
   */
  public void newGame(Board board);
  
  /**
   * Updates the board according to the piece played. This method does not change the state of the board, it merely
   * displays changes that were made.
   * 
   * @param board The board
   * @param placedPiece The position of the piece that was just played
   * @param flippedPieces The pieces that were flipped during this turn.
   */
  public abstract void updateBoard(Board board, Position placedPiece, LinkedList<Tile> flippedPieces);
  
  /**
   * Display the legal moves for the turn player.
   *
   * @param game The game state
   */
  public abstract void displayLegalMoves(Model game);

  /**
   * @return The PrintStream used to send messages to.
   */
  public abstract PrintStream getPrintStream();
}
