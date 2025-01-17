package controller;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import model.Model;
import view.Message;
import view.View;

/**
 * Controller class for coordinating Model and View to play a game of Reversi.
 */
public class Controller {
  private Model model;
  private View view;
  private BlockingQueue<Message> messageQueue;
  private List<Valve> valves;

  /**
   * Contructs a Controller for this Model and View, using this queue for messages
   *
   * @param model The model
   * @param view The view
   * @param messageQueue The queue to read messages from
   */
  public Controller(Model model, View view, BlockingQueue<Message> messageQueue) {
    this.model = model;
    this.view = view;
    this.messageQueue = messageQueue;

    // create list of valves
    valves = new LinkedList<Valve>();
    valves.add(new NewGameValve(model, view));
    valves.add(new PlacePieceValve(model, view));
  }

  /**
   * Plays Reversi
   */
  public void playGame() {
    view.initBoard(model.getBoard());

    // needed for first turn
    // find legal moves of turn player (game start -- Black)
    // tell View to display legal moves
    model.getLegalMoves();
    view.displayLegalMoves(model);

    // forever loop (until game finished)
    // wait for input (i.e. wait for message to arrive in queue)
    ValveResponse response = ValveResponse.MISS;
    //while (response != ValveResponse.ENDGAME) {
    //int turnNumber = 1;
    while (true) {
      try {
        Message msg = messageQueue.take();
        for (Valve valve : valves) {
          response = valve.execute(msg);
          // if valve was executed or endgame was reached, don't continue
          if (response != ValveResponse.MISS) {
            break;
          }
        }
      } catch (InterruptedException ex) {
        System.err.println("error: thread interrupted");
        ex.printStackTrace();
      }
    }
    //messageQueue.clear();
    }
  }
