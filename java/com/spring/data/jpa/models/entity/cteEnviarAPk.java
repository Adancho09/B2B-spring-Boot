package com.spring.data.jpa.models.entity;

import java.io.Serializable;
import java.util.Objects;

public class cteEnviarAPk implements Serializable {
    private int ID;
    private String cliente;

    public cteEnviarAPk (int ID, String cliente) {
        this.ID = ID;
        this.cliente = cliente;
    }

    public cteEnviarAPk () {
    }

    //Getters and setters are omitted for brevity

    @Override
    public boolean equals(Object o) {
        if ( this == o ) {
            return true;
        }
        if ( o == null || getClass() != o.getClass() ) {
            return false;
        }
        cteEnviarAPk  pk = (cteEnviarAPk ) o;
        return Objects.equals( ID, pk.ID ) &&
                Objects.equals( cliente, pk.cliente );
    }

    @Override
    public int hashCode() {
        return Objects.hash( ID, cliente );
    }
}
