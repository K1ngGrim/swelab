package iwwwdnw.turn;

import iwwwdnw.domain.DomainFactory;
import iwwwdnw.domain.port.Field;
import iwwwdnw.domain.port.Figure;
import iwwwdnw.domain.port.Player;
import iwwwdnw.domain.port.Position;
import iwwwdnw.statemachine.StateMachineFactory;
import iwwwdnw.statemachine.port.State.S;
import iwwwdnw.statemachine.port.StateMachine;
import iwwwdnw.turn.impl.TurnImpl;
import iwwwdnw.turn.port.ITurn;

public class TurnFacade implements TurnFactory, ITurn {

	private TurnImpl turn;
	private StateMachine stateMachine;
	private Player currentplayer = null;
	private Position position = null;
	private Player opponent = null;

	@Override
	public ITurn turn() {
		if (this.turn == null) {
			this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
			this.turn = new TurnImpl(stateMachine, DomainFactory.FACTORY.domain());
		}
		return this;
	}

	@Override
	public synchronized void sysop() {
		if (this.stateMachine.getState().isSubStateOf( S.MAKE_A_TURN /* choose right state*/ ))
			this.turn.sysop();
	}

	@Override
	public synchronized void rollDice(Player player) {
		if (this.stateMachine.getState().isSubStateOf( S.ROLL_DICE  ))
			this.turn.rollDice(currentplayer);
	}

	@Override
	public synchronized void chooseStartField(Figure figure, int fieldId) {
		if (this.stateMachine.getState().isSubStateOf( S.CHOOSE_STARTFIELD  ))
			this.turn.chooseStartField(figure, fieldId);
	}

	@Override
	public synchronized void moveFigure(Figure figure, int fieldId) {
		if (this.stateMachine.getState().isSubStateOf( S.MOVE_FIGURE  ))
			this.turn.moveFigure(figure, fieldId);
	}


}