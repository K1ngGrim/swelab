package iwwwdnw.domain.port;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public interface Game {

	Player getCurrentPlayer();

	void nextPlayer();



	List<Player> allPlayers();
	
	ArrayList<Field> getBoard();
	

}
