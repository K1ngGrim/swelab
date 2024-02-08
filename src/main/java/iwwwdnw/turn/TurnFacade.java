package iwwwdnw.turn;

import iwwwdnw.domain.port.Field;
import iwwwdnw.domain.port.Game;
import iwwwdnw.domain.port.Player;
import iwwwdnw.statemachine.StateMachineFactory;
import iwwwdnw.statemachine.port.State.S;
import iwwwdnw.statemachine.port.StateMachine;
import iwwwdnw.turn.impl.TurnImpl;
import iwwwdnw.turn.port.ITurn;

import java.util.ArrayList;
import java.util.Set;

public class TurnFacade implements TurnFactory, ITurn {

    private TurnImpl turn;
    private StateMachine stateMachine;

    @Override
    public ITurn turn() {
        if (this.turn == null) {
            this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
            this.turn = new TurnImpl();
        }
        return this;
    }

    @Override
    public synchronized void sysop() {
        if (this.stateMachine.getState().isSubStateOf(S.MAKE_A_TURN /* choose right state*/))
            this.turn.sysop();
    }

    @Override
    public synchronized void duel(Player currentplayer, Field field) {
        if (this.stateMachine.getState().isSubStateOf(S.DUEL))
            this.turn.duel(currentplayer, field);
    }

    @Override
    public synchronized void rollDice(Player currentPlayer) {
        if (this.stateMachine.getState().isSubStateOf(S.ROLL_DICE))
            this.turn.rollDice(currentPlayer);
    }

    @Override
    public synchronized void chooseStartField(Player currentPlayer, int fieldId) {
        if (this.stateMachine.getState().isSubStateOf(S.CHOOSE_STARTFIELD))
            this.turn.chooseStartField(currentPlayer, fieldId);
    }

    @Override
    public synchronized void moveFigure(Player currentPlayer, Field from, Field to) {
        if (this.stateMachine.getState().isSubStateOf(S.MOVE_FIGURE))
            this.turn.moveFigure(currentPlayer, from, to);
    }

    @Override
    public synchronized ArrayList<Field> getFieldsWithFiguresOnThem(Player currentPlayer) {
        return this.turn.getFiguresOnBoard(currentPlayer);
    }

    @Override
    public synchronized int getRemainingDiceSum() {
        return this.turn.getRemainingDiceSum();
    }

    @Override
    public synchronized Set<Field> getVisited() {
        return this.turn.getVisited();
    }

    @Override
    public synchronized Game getGame() {
        return this.turn.getGame();
    }

    @Override
    public synchronized void nextPlayer() {
        this.turn.nextPlayer();
    }
}