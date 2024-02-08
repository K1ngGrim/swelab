package iwwwdnw.turn.impl;

import iwwwdnw.domain.DomainFactory;
import iwwwdnw.domain.impl.FigureImpl;
import iwwwdnw.domain.port.*;
import iwwwdnw.statemachine.StateMachineFactory;
import iwwwdnw.statemachine.port.State;
import iwwwdnw.statemachine.port.StateMachine;
import org.eclipse.jetty.util.Fields;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TurnImpl {
    public int remainingDiceSum = 0;
    public int tries = 2;

    public Set<Field> visited = new HashSet<Field>();
    public StateMachine stateMachine;

    public Domain domain;

    public TurnImpl() {
        this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
        this.domain = DomainFactory.FACTORY.domain();
    }

    public void sysop() {
        stateMachine.setState(State.S.ROLL_DICE);
    }

    public void duel(Player player, Field field) {
        Figure opponent = null;
        for (Figure figure : field.allFigures()) {
            if (!figure.getColor().equals(player.getColor())) {
                opponent = figure;
                break;
            }
        }
        //Oppenent always looses
        if (opponent != null) {
            opponent.setField(null);
            field.removeFigureFromField((FigureImpl) opponent);
        }
        stateMachine.setState(State.S.ROLL_DICE);
        this.getGame().nextPlayer();

    }

    public void rollDice(Player player) {
        if (this.tries == 0) {
            this.tries = 2;
            domain.getGame().nextPlayer();
            stateMachine.setState(State.S.START_TURN);
            return;
        }
        int dice1 = (int) (Math.random() * 6) + 1;
        int dice2 = (int) (Math.random() * 6) + 1;
        this.remainingDiceSum = dice1 + dice2;

        int countFiguresHome = getAmountFiguresAtHome(player);

        if (remainingDiceSum != 7) {
            if (countFiguresHome == 5) {
                tries--;
                stateMachine.setState(State.S.ROLL_DICE);

            } else {
                stateMachine.setState(State.S.CHOOSE_FIGURE_TO_MOVE);
            }
        } else {
            stateMachine.setState(State.S.CHOOSE_STARTFIELD);
        }


    }

    private int getAmountFiguresAtHome(Player player) {
        int countFiguresHome = 0;

        for (Figure figure : player.allFigures()) {
            if (figure.getField() == null) ++countFiguresHome;
        }
        return countFiguresHome;
    }

    public ArrayList<Field> getFiguresOnBoard(Player player) {
        ArrayList<Field> fieldsWithFiguresOnThem = new ArrayList<Field>();

        for (Figure figure : player.allFigures()) {
            if (figure.getField() != null) {
                fieldsWithFiguresOnThem.add(figure.getField());
            }
            ;
        }
        return fieldsWithFiguresOnThem;
    }


    public void chooseStartField(Player currentPlayer, int fieldId) {
        Field[] fields = currentPlayer.startFields();
        for (Field field : fields) {
            if (field.getId() == fieldId) {
                for (Figure figure : currentPlayer.allFigures()) {
                    if (figure.getField() == null) {
                        figure.setField(field);
                        field.addFigureToField((FigureImpl) figure);
                        break;
                    }
                }
            }
            if (!field.allFigures().isEmpty()) {
                stateMachine.setState(State.S.DUEL);
            } else {
                stateMachine.setState(State.S.ROLL_DICE);

            }
        }

        this.stateMachine.setState(State.S.ROLL_DICE);
        domain.getGame().nextPlayer();
    }

    public void moveFigure(Player currentPlayer, Field from, Field to) {
        if (this.getRemainingDiceSum() == 0) {
            visited.clear();
            this.stateMachine.setState(State.S.ROLL_DICE);
            this.domain.getGame().nextPlayer();
        }
        for (Figure figure : from.allFigures()) {
            if (currentPlayer.getColor().equals(figure.getColor())) {
                from.removeFigureFromField((FigureImpl) figure);
                to.addFigureToField((FigureImpl) figure);
                figure.setField(to);
                for (Figure possibleEnemy : to.allFigures()) {
                    if (!possibleEnemy.getColor().equals(currentPlayer.getColor())) {
                        this.stateMachine.setState(State.S.DUEL);
                        visited.clear();
                        return;
                    }
                    ;
                }
                visited.add(from);
                this.remainingDiceSum--;
                stateMachine.setState(State.S.MOVE_FIGURE);
                break;

            }
        }
    }

    public void chooseFigureToMove(Player currentPlayer) {
        ArrayList<Field> figuresOnBoard = this.getFiguresOnBoard(currentPlayer);
    }

    public int getRemainingDiceSum() {
        return remainingDiceSum;
    }

    public Set<Field> getVisited() {
        return visited;
    }

    public Game getGame() {
        return domain.getGame();
    }

    public void nextPlayer() {
        this.domain.getGame().nextPlayer();
    }
}