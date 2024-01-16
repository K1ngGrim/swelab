package iwwwdnw.domain.port;

import java.util.List;
import java.util.Set;

public interface Game {

	Player getCurrentPlayer();

	void nextPlayer();

	List<Player> allPlayers();
	
	Set<Field> getBoard();
	

}
