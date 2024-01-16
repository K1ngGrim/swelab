package iwwwdnw.turn.impl;

import iwwwdnw.domain.DomainFactory;
import iwwwdnw.domain.port.*;
import iwwwdnw.statemachine.StateMachineFactory;
import iwwwdnw.statemachine.port.State;
import iwwwdnw.statemachine.port.StateMachine;

public class TurnImpl {
	public int remainingDiceSum = 0;
	public int tries = 3;
	public StateMachine stateMachine;

	public Domain domain;
	public TurnImpl() {
		this.stateMachine = StateMachineFactory.FACTORY.stateMachine();
		this.domain = DomainFactory.FACTORY.domain();
	}

	public void sysop() {
		stateMachine.setState(State.S.ROLL_DICE);
	}

	public void rollDice(Player player) {
		int dice1 = (int) (Math.random() * 6) + 1;
		int dice2 = (int) (Math.random() * 6) + 1;
		this.remainingDiceSum = dice1 + dice2;


		int countFiguresHome = 0;

		for (Figure figure : player.allFigures()){
			if(figure.getField() == null) ++countFiguresHome;
		}

		if (remainingDiceSum != 7){
			if(countFiguresHome == 5){
				//TODO: Wie Anzahl Tries behandeln?
			}else{
				stateMachine.setState(State.S.MOVE_FIGURE);
			}
		}else{
				stateMachine.setState(State.S.CHOOSE_STARTFIELD);
		}


	}

	public void chooseStartField(Player currentPlayer, int fieldId) {

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