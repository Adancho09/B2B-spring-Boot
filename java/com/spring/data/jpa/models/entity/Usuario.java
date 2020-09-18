package com.spring.data.jpa.models.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="Cte")
public class Usuario implements Serializable {
    @Id
    @Column(unique=true)
    private String cliente;
    @Column(unique=true)
    private String contrasena;
    private String Nombre;
    private String listapreciosesp;
    private String agente;


    private static final long serialVersionUID=1L;

    public Usuario() {
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }

    public String getListapreciosesp() {
        return listapreciosesp;
    }

    public void setListapreciosesp(String listapreciosesp) {
        this.listapreciosesp = listapreciosesp;
    }

    public String getListaPreciosEsp() {
        return listapreciosesp;
    }

    public void setListaPreciosEsp(String listapreciosesp) {
        this.listapreciosesp = listapreciosesp;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        cliente = cliente;
    }

    public String getContrasena() {
        return contrasena;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public void setContrasena(String contrasena) {
        contrasena = contrasena;
    }
}
