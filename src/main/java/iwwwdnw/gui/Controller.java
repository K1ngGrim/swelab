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

public class Controller implements Observer {

	private View myView;
	private Subject subject;
	private ITurn myModel;
	private Game game;
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
		}
		if (this.stateMachine.getState().equals(S.ROLL_DICE)) {
			this.myView.show(this.currentPlayer.getName() + ": Press y and Enter to roll");

			if (scanner.nextLine().equalsIgnoreCase("y")) {
				myModel.rollDice(this.currentPlayer);
				this.myView.show("Sum: " + this.myModel.getRemainingDiceSum());
			}


			/*
			this.myView.show("need input!");
			int x = 1; // read input
			switch (x) {
				case 1:
					this.myModel.sysop();
					break;
				case -1:
					this.myView.stop();
					break;
				default:
					;

			}
		*/
			// TODO Auto-generated method stub

		}
	}
}
