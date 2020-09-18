package com.spring.data.jpa.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "VentaD")
@IdClass(CardDApk.class)
public class VentaD implements Serializable {

    @Id
    @Column(name = "ID")
    private int ID;
    @Id
    private int renglon;
    private int renglonid;
    private String articulo;
    private int cantidad;
    private int enviara;
    private int almacen;
    private BigDecimal precio;
    private BigDecimal preciosugerido;
    private int impuesto1;
    private char renglontipo;
    private String preciomoneda;
    private int preciotipocambio;
    @Column(nullable=true)
    private Integer cantidadreservada;
    private int cantidadinventario;
    private String unidad;

    private static final long serialVersionUID=1L;

    public VentaD() {
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }

    public int getCantidadinventario() {
        return cantidadinventario;
    }

    public void setCantidadinventario(int cantidadinventario) {
        this.cantidadinventario = cantidadinventario;
    }

    public int getCantidadreservada() {
        return cantidadreservada;
    }

    public void setCantidadreservada(int cantidadreservada) {
        this.cantidadreservada = cantidadreservada;
    }

    public char getRenglontipo() {
        return renglontipo;
    }

    public void setRenglontipo(char renglontipo) {
        this.renglontipo = renglontipo;
    }

    public int getRenglonid() {
        return renglonid;
    }

    public void setRenglonid(int renglonid) {
        this.renglonid = renglonid;
    }

    public int getRenglon() {
        return renglon;
    }

    public void setRenglon(int renglon) {
        this.renglon = renglon;
    }

    public int getPreciotipocambio() {
        return preciotipocambio;
    }

    public void setPreciotipocambio(int preciotipocambio) {
        this.preciotipocambio = preciotipocambio;
    }

    public BigDecimal getPreciosugerido() {
        return preciosugerido;
    }

    public void setPreciosugerido(BigDecimal preciosugerido) {
        this.preciosugerido = preciosugerido;
    }

    public String getPreciomoneda() {
        return preciomoneda;
    }

    public void setPreciomoneda(String preciomoneda) {
        this.preciomoneda = preciomoneda;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public void setCantidadreservada(Integer cantidadreservada) {
        this.cantidadreservada = cantidadreservada;
    }

    public int getImpuesto1() {
        return impuesto1;
    }

    public void setImpuesto1(int impuesto1) {
        this.impuesto1 = impuesto1;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public int getEnviara() {
        return enviara;
    }

    public void setEnviara(int enviara) {
        this.enviara = enviara;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String articulo) {
        this.articulo = articulo;
    }

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }
}
