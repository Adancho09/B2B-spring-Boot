package com.spring.data.jpa.models.entity;

import java.io.Serializable;
import java.util.Objects;

public class CardDApk implements Serializable {
    private int ID;

    private int renglon;

    public CardDApk (int ID, int renglon) {
        this.ID = ID;
        this.renglon = renglon;
    }

    public CardDApk () {

    }

    //Getters and setters are omitted for brevity


    public int getRenglon() {
        return renglon;
    }

    public void setRenglon(int renglon) {
        this.renglon = renglon;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        CardDApk  pk = (CardDApk ) o;
        return Objects.equals( ID, pk.ID ) &&
                Objects.equals( renglon, pk.renglon);
    }

    @Override
    public int hashCode() {
        return Objects.hash( ID,renglon );
    }
}
