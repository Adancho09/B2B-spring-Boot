package com.spring.data.jpa.models.entity;

import java.io.Serializable;
import java.util.Objects;

public class CartApk implements Serializable {
    private int ID;

    private String cliente;

    public CartApk (int ID, String cliente) {
        this.ID = ID;
        this.cliente = cliente;
    }

    public CartApk () {

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
        CartApk  pk = (CartApk ) o;
        return Objects.equals( ID, pk.ID ) &&
                Objects.equals( cliente, pk.cliente);
    }

    @Override
    public int hashCode() {
        return Objects.hash( ID,cliente );
    }
}
