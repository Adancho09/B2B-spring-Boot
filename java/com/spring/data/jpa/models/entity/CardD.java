package com.spring.data.jpa.models.entity;

import org.springframework.beans.factory.annotation.Qualifier;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;


@Entity
@Table(name="CardD")
@IdClass(CardDApk.class)
public class CardD  implements Serializable {

    @Id
    @Column(name = "ID")
    private int ID;
    @Id
    private int renglon;
    private String articulo;
    private int cantidad;
    private int almacen;
    private BigDecimal precio;
    private BigDecimal impuesto1;
    private BigDecimal total;
    private int isaviable;
    private static final long serialVersionUID=1L;

    public CardD() {

    }

    public int getIsaviable() {
        return isaviable;
    }

    public void setIsaviable(int isaviable) {
        this.isaviable = isaviable;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public int getRenglon() {
        return renglon;
    }

    public void setRenglon(int renglon) {
        this.renglon = renglon;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public BigDecimal getImpuesto1() {
        return impuesto1;
    }

    public void setImpuesto1(BigDecimal impuesto1) {
        this.impuesto1 = impuesto1;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
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
