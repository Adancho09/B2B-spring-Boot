package com.spring.data.jpa.models.entity;
import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="card")
public class Card  implements Serializable {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int ID;
    private String cliente;
    private String fechaalta;
    private String agente;
    private String ordencompra;
    private String condicion;
    private BigDecimal importe;
    private BigDecimal impuestos;
    private BigDecimal total;
    private int enviara;
    private char isactive;
    private static final long serialVersionUID=1L;

    public Card(int ID,  String cliente, String fechaAlta, String agente, String ordenCompra ) {
        this.ID = ID;

        this.cliente = cliente;
        this.fechaalta = fechaAlta;
        this.agente = agente;
        this.ordencompra = ordenCompra;


    }

    public int getEnviara() {
        return enviara;
    }

    public void setEnviara(int enviara) {
        this.enviara = enviara;
    }

    public char isIsactive() {
        return isactive;
    }

    public void setIsactive(char isactive) {
        this.isactive = isactive;
    }

    public String getOrdencompra() {
        return ordencompra;
    }

    public void setOrdencompra(String ordencompra) {
        this.ordencompra = ordencompra;
    }

    public String getFechaalta() {
        return fechaalta;
    }

    public void setFechaalta(String fechaalta) {
        this.fechaalta = fechaalta;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public Card() {
    }

    public BigDecimal getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(BigDecimal impuestos) {
        this.impuestos = impuestos;
    }

    public BigDecimal getImporte() {

        return importe;
    }

    public void setImporte(BigDecimal importe) {
        this.importe = importe;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }



    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }





    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }


    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }


}

