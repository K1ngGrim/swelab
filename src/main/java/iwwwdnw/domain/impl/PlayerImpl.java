package iwwwdnw.domain.impl;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import iwwwdnw.domain.port.Field;
import iwwwdnw.domain.port.Figure;
import iwwwdnw.domain.port.Player;

public class PlayerImpl implements Player {

    private String name;
    private int yearOfBirth;
    private String color;
    private Set<FigureImpl> myFigures = new HashSet<>();
    private FieldImpl[] startField;

    public PlayerImpl(String name, int year, String color) {
        this.name = name;
        this.yearOfBirth = year;
        this.color = color;
        for (int i = 0; i < 5; i++)
            this.myFigures.add(new FigureImpl(this));
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public int getYearOfBirth() {
        return this.yearOfBirth;
    }

    @Override
    public String getColor() {
        return this.color;
    }

    @Override
    public HashSet<Figure> allFigures() {
        return new HashSet<>(this.myFigures);
    }

    @Override
    public FieldImpl[] startFields() {
        return new FieldImpl[]{this.startField[0], this.startField[1]};
    }

    @Override
    public int[] getFreeStartFields() {
        int[] freeStartFieldIds = {-1, -1};
        for (int i = 0; i < this.startFields().length; i++) {
            Set<Figure> figuresOnStartField = this.startFields()[i].allFigures();//returns all figures on this field
            if (figuresOnStartField.isEmpty()) {
                freeStartFieldIds[i] = this.startFields()[i].getId();
            } else {
                for (Figure figureOnStartField : figuresOnStartField) {
                    if (!figureOnStartField.getColor().equals(this.color)) {
                        freeStartFieldIds[i] = (this.startFields()[i].getId());
                    }
                }
            }
        }
        return freeStartFieldIds;
    }

    public void setStartField(FieldImpl outerField, FieldImpl innerField) {
        this.startField = new FieldImpl[]{outerField, innerField};
    }

}
