package iwwwdnw.turn.port;

import iwwwdnw.domain.port.*;

import java.util.ArrayList;
import java.util.Set;

public interface ITurn {

    void sysop();

    public int getRemainingDiceSum();
    public Set<Field> getVisited();

    public Game getGame();

    void duel(Player currentplayer, Field field);

    public void rollDice(Player currentPlayer);

    public void chooseStartField(Player currentPlayer, int fieldId);

    public ArrayList<Field> getFieldsWithFiguresOnThem(Player player);

    public void moveFigure(Player currentPlayer,Field from, Field to);

    public void nextPlayer();

}
