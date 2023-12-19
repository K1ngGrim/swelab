package iwwwdnw.turn.port;

import iwwwdnw.domain.port.Figure;
import iwwwdnw.domain.port.Player;
import iwwwdnw.domain.port.Position;

public interface ITurn {
	
	void sysop();

	public void rollDice();

	public void checkDiceSum();

	public void chooseStartField(Position position);

	public void chooseFigureToMove(Figure figure);

	public void moveFigure(Position position);

	public void duel(Player opponent);

	public void checkRemainingDicesum();

}
