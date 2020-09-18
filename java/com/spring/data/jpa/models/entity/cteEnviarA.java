package com.spring.data.jpa.models.entity;
import javax.persistence.*;
import java.io.Serializable;
@Entity
@Table(name="cteenviara")
@IdClass(cteEnviarAPk.class)
@SequenceGenerator(name="seq", initialValue=1, allocationSize=10000)
public class cteEnviarA implements Serializable{

    @Id
    private int ID;
    @Id
    private String cliente;
    private String pais;
    private String estado;

    private String entrecalles;
    private String nombre;
    private String direccion;
    private String codigopostal;
    private String Poblacion;

    private String colonia;
    private String direccionnumero;
    private String direccionnumeroint;
    private boolean estatus;

    public boolean isEstatus() {
        return estatus;
    }

    public void setEstatus(boolean estatus) {
        this.estatus = estatus;
    }

    private static final long serialVersionUID=1L;


    public String getPoblacion() {
        return Poblacion;
    }

    public void setPoblacion(String poblacion) {
        Poblacion = poblacion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }


    public String getEstado() {
        return this.estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }



    public String getDireccionNumeroInt() {
        return direccionnumeroint;
    }

    public void setDireccionNumeroInt(String direccionNumeroInt) {
        this.direccionnumeroint = direccionNumeroInt;
    }

    public String getDireccionNumero() {
        return direccionnumero;
    }

    public void setDireccionNumero(String direccionNumero) {
        this.direccionnumero = direccionNumero;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getColonia() {
        return colonia;
    }

    public void setColonia(String colonia) {
        this.colonia = colonia;
    }

    public String getCodigoPostal() {
        return codigopostal;
    }

    public void setCodigoPostal(String codigoPostal) {
        this.codigopostal = codigoPostal;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getEntreCalles () {
        return entrecalles ;
    }

    public void setEntreCalles(String alias) {
        this.entrecalles= entrecalles;
    }

    public cteEnviarA() {
    }
}
