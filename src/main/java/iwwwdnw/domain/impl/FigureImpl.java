package iwwwdnw.domain.impl;

import iwwwdnw.domain.DomainFactory;
import iwwwdnw.domain.port.Domain;
import iwwwdnw.domain.port.Field;
import iwwwdnw.domain.port.Figure;

public class FigureImpl implements Figure {

    private final Domain domain;
    private Field field;
    private PlayerImpl player;

    public FigureImpl(PlayerImpl player) {
        this.player = player;
        this.domain = DomainFactory.FACTORY.domain();
    }

    @Override
    public Field getField() {
        return this.field;
    }

    @Override
    public void setField(Field field) {
        this.field = field;
    }

    @Override
    public String getColor() {
        return this.player.getColor();
    }

}
