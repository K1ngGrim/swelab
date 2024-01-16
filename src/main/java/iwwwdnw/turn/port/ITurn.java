package iwwwdnw.turn.port;

import iwwwdnw.domain.port.*;

public interface ITurn {
	
	void sysop();

	public int getRemainingDiceSum();

	public Game getGame();

	void duel(Player currentplayer, Player opponent, int fieldId);

	public void rollDice(Player currentPlayer);

	public void chooseStartField(Player currentPlayer, int fieldId);

	public void moveFigure(Player currentPlayer, Figure figure, int fieldId);

}
