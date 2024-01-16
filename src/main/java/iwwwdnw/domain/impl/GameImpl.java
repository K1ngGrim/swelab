package iwwwdnw.domain.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import iwwwdnw.domain.port.Field;
import iwwwdnw.domain.port.Game;
import iwwwdnw.domain.port.Player;

public class GameImpl implements Game {

	private List<PlayerImpl> sortedPlayers;

	private List<FieldImpl> allFields;

	private int currentPlayerIndex;

	public GameImpl(PlayerImpl[] players, List<FieldImpl> fields) {
		this.sortedPlayers = Arrays.asList(players);
		this.allFields = fields;
		this.currentPlayerIndex = 0;
	}
	@Override
	public Player getCurrentPlayer() {return this.sortedPlayers.get(currentPlayerIndex);}

	@Override
	public void nextPlayer() {this.currentPlayerIndex = (this.currentPlayerIndex + 1) % 4;}

	@Override
	public List<Player> allPlayers() {
		return new ArrayList<>(this.sortedPlayers);
	}

	@Override
	public ArrayList<Field> getBoard() {
		return new ArrayList<>(this.allFields);
	}


}
