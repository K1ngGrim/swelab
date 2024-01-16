package iwwwdnw.gui;

import iwwwdnw.domain.port.Game;
import iwwwdnw.domain.port.Player;
import iwwwdnw.statemachine.StateMachineFactory;
import iwwwdnw.statemachine.port.Observer;
import iwwwdnw.statemachine.port.State;
import iwwwdnw.statemachine.port.StateMachine;
import iwwwdnw.statemachine.port.Subject;
import iwwwdnw.turn.port.ITurn;

import java.util.Scanner;

import static iwwwdnw.statemachine.port.State.S;
import static java.util.regex.Pattern.matches;

public class Controller implements Observer {

    private View myView;
    private Subject subject;
    private ITurn myModel;
    private StateMachine stateMachine;
    private Player currentPlayer;

    public Controller(View view, ITurn model, Subject subject) {
        this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
        this.myView = view;
        this.myModel = model;
        this.subject = subject;
        this.subject.attach(this);
        this.currentPlayer = model.getGame().getCurrentPlayer();
    }

    @Override
    public void update(State currentState) {
        /* maybe there is something to do */
    }

    public void doit() {
        this.currentPlayer = this.myModel.getGame().getCurrentPlayer();
        Scanner scanner = new Scanner(System.in);
        if (this.stateMachine.getState().equals(S.START_TURN)) {
            this.myModel.sysop();
        } else if (this.stateMachine.getState().equals(S.ROLL_DICE)) {
            this.myView.show(this.currentPlayer.getName() + ": Press y and Enter to roll");

            if (scanner.nextLine().equalsIgnoreCase("y")) {
                myModel.rollDice(this.currentPlayer);
                this.myView.show("Sum: " + this.myModel.getRemainingDiceSum());
            }
        } else if (this.stateMachine.getState().equals(S.CHOOSE_STARTFIELD)) {
            int[] freeStartFieldIds = this.currentPlayer.getFreeStartFields();
            int occupiedStartFields = 2;
            for (int fieldId : freeStartFieldIds) {
                if (fieldId != -1) {
                    this.myView.show("Free field: " + fieldId);
                    occupiedStartFields--;
                }
            }
            if (occupiedStartFields == 2) {
                this.myView.show("Both start fields are occupied - Move with other figures");
                this.stateMachine.setState(S.MOVE_FIGURE);
            } else {
                this.myView.show("Choose the field you want to go to:");
                String input = scanner.nextLine();
                if (matches(freeStartFieldIds[0] + "|" + freeStartFieldIds[1], input)) {
                    myModel.chooseStartField(this.currentPlayer, Integer.parseInt(input));

                } else {
                    this.myView.show("Invalid - Choose one of the free fields.");
                    stateMachine.setState(S.CHOOSE_STARTFIELD);
                }
            }

        } else if(this.stateMachine.getState().equals(S.DUEL)){
            //myModel.duel(currentPlayer,);
        }else {
            this.myView.show("ERROR");
        }


    }
}
