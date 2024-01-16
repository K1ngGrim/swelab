package iwwwdnw.domain.impl;

import iwwwdnw.domain.port.Game;

public class DomainImpl {

	private GameImpl game;
	
	public void init() {
		PlayerImpl[] player =
				new PlayerImpl[] {
						new PlayerImpl("Berry Backlog", 1991, "red"),
						new PlayerImpl("Ada Thread", 1994, "green"),
						new PlayerImpl("Gustav Jannis", 2014, "blue"),
						new PlayerImpl("Benson Onsneb", 2000, "yellow")
		};
		this.game = new GameImpl(player, new BoardMaker(player).getBoard() );
	}

	public Game getGame() {
		return this.game;
	}

}
