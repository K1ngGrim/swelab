package iwwwdnw.turn.impl;

import iwwwdnw.domain.DomainFactory;
import iwwwdnw.domain.impl.FigureImpl;
import iwwwdnw.domain.port.*;
import iwwwdnw.statemachine.StateMachineFactory;
import iwwwdnw.statemachine.port.State;
import iwwwdnw.statemachine.port.StateMachine;

public class TurnImpl {
    public int remainingDiceSum = 0;
    public int tries = 2;
    public StateMachine stateMachine;

    public Domain domain;

    public TurnImpl() {
        this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
        this.domain = DomainFactory.FACTORY.domain();
    }

    public void sysop() {
        stateMachine.setState(State.S.ROLL_DICE);
    }

    public void duel(Player player, Player opponent, int fieldId) {
        for (Figure opponentFigure : opponent.allFigures()) {
            if (opponentFigure.getField().getId() == fieldId) {
                opponentFigure.setField(null);
                for (Field field : domain.getGame().getBoard()) {
                    if (field.getId() == fieldId) {
                        field.removeFigureFromField((FigureImpl) opponentFigure);
                        break;
                    }
                }
            }

        }
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
        this.remainingDiceSum = dice2 + dice1;

        int countFiguresHome = getAmountFiguresAtHome(player);

        if (remainingDiceSum != 7) {
            if (countFiguresHome == 5) {
                tries--;
                stateMachine.setState(State.S.ROLL_DICE);

            } else {
                stateMachine.setState(State.S.MOVE_FIGURE);
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
        //TODO enemy on field
        this.stateMachine.setState(State.S.ROLL_DICE);
        domain.getGame().

                nextPlayer();
    }

    public void moveFigure(Player currentPlayer, Figure figure, int fieldId) {

    }

    public int getRemainingDiceSum() {
        return remainingDiceSum;
    }

    public Game getGame() {
        return domain.getGame();
    }
}