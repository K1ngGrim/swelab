package iwwwdnw.gui;

import iwwwdnw.domain.port.Field;
import iwwwdnw.domain.port.Player;
import iwwwdnw.domain.port.Figure;
import iwwwdnw.statemachine.StateMachineFactory;
import iwwwdnw.statemachine.port.Observer;
import iwwwdnw.statemachine.port.State;
import iwwwdnw.statemachine.port.StateMachine;
import iwwwdnw.statemachine.port.Subject;
import iwwwdnw.turn.port.ITurn;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Set;

import static iwwwdnw.statemachine.port.State.S;
import static java.util.regex.Pattern.matches;

public class Controller implements Observer {

    private View myView;
    private Subject subject;
    private ITurn myModel;
    private StateMachine stateMachine;
    private Player currentPlayer;
    private Field chosenFieldWithFigureOnIt;

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
                this.stateMachine.setState(S.CHOOSE_FIGURE_TO_MOVE);
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

        } else if (this.stateMachine.getState().equals(S.DUEL)) {
            myModel.duel(currentPlayer, this.chosenFieldWithFigureOnIt);
        } else if (this.stateMachine.getState().equals(S.CHOOSE_FIGURE_TO_MOVE)) {
            this.myView.show("Choose Figure to move!");
            ArrayList<Field> possibleFigures = myModel.getFieldsWithFiguresOnThem(currentPlayer);
            for (Field field : possibleFigures) {
                this.myView.show("Figure at field: " + field.getId());
            }
            String input = scanner.nextLine();
            int parsedInput = -1;
            try {
                parsedInput = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                this.myView.show("Invalid - Choose a correct figure");
                stateMachine.setState(S.MOVE_FIGURE);
            }
            boolean containsFieldWithId = false;
            Field temp = null;
            for (Field field : possibleFigures) {
                if (parsedInput > 0 && field.getId() == parsedInput) {
                    containsFieldWithId = true;
                    temp = field;
                    break;
                }
            }
            if (containsFieldWithId) {
                this.chosenFieldWithFigureOnIt = temp;
                this.stateMachine.setState(S.MOVE_FIGURE);
            } else {
                this.myView.show("Invalid - A correct field");
                stateMachine.setState(S.CHOOSE_FIGURE_TO_MOVE);
            }
        } else if (this.stateMachine.getState().equals(S.MOVE_FIGURE)) {
            Field neighbour = chosenFieldWithFigureOnIt.neighbor();
            Field succ = chosenFieldWithFigureOnIt.succ();
            Field pred = chosenFieldWithFigureOnIt.pred();


            if (this.myModel.getRemainingDiceSum() > 0) {
                this.myView.show("Choose where to move your figure, remaining moves : " + this.myModel.getRemainingDiceSum());
                this.myView.show("Options: \n" + (!this.myModel.getVisited().contains(pred) ? pred.getId() : "") + "\n" + (!this.myModel.getVisited().contains(neighbour) ? (neighbour != null ? String.valueOf(neighbour.getId()) : "") : "") + "\n" + (!this.myModel.getVisited().contains(succ) ? succ.getId() : ""));
                String input = scanner.nextLine();
                int parsedInput = -1;
                try {
                    parsedInput = Integer.parseInt(input);
                } catch (NumberFormatException e) {
                    this.myView.show("Invalid - Choose a correct field");
                    stateMachine.setState(S.MOVE_FIGURE);
                }
                if (parsedInput == pred.getId()) {
                    this.myModel.moveFigure(currentPlayer, chosenFieldWithFigureOnIt, pred);
                    chosenFieldWithFigureOnIt = pred;
                } else if (parsedInput == succ.getId()) {
                    this.myModel.moveFigure(currentPlayer, chosenFieldWithFigureOnIt, succ);
                    chosenFieldWithFigureOnIt = succ;
                } else if (neighbour != null && parsedInput == neighbour.getId()) {
                    this.myModel.moveFigure(currentPlayer, chosenFieldWithFigureOnIt, neighbour);
                    chosenFieldWithFigureOnIt = neighbour;
                } else {
                    this.myView.show("Invalid - Choose a correct field");
                    stateMachine.setState(S.MOVE_FIGURE);
                }
            } else {
                stateMachine.setState(S.ROLL_DICE);
                this.myModel.nextPlayer();
            }


        } else {
            this.myView.show("ERROR");
        }


    }
}
