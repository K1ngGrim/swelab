package iwwwdnw.turn.port;

import iwwwdnw.domain.port.Field;
import iwwwdnw.domain.port.Figure;
import iwwwdnw.domain.port.Player;
import iwwwdnw.domain.port.Position;

public interface ITurn {
	
	void sysop();

	public void rollDice(Player player);

	public void chooseStartField(Figure figure, int fieldId);

	public void moveFigure(Figure figure, int fieldId);

}
