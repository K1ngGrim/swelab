package iwwwdnw.turn.port;

import iwwwdnw.domain.port.*;

public interface ITurn {
	
	void sysop();

	public int getRemainingDiceSum();

	public Game getGame();

	public void rollDice(Player currentPlayer);

	public void chooseStartField(Player currentPlayer, int fieldId);

	public void moveFigure(Player currentPlayer, Figure figure, int fieldId);

}
