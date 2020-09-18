package com.spring.data.jpa.models.entity;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Product {

    private String articulo;
    private String fabricante;
    private String categoria;
    private String grupo;
    private String canal;
    private String familia;
    private String Descripcion;
    private int real_qty;
    private int real_qty3;
    private BigDecimal Precio;
    private String lista;

    public String getLista() {
        return lista;
    }

    public void setLista(String lista) {
        this.lista = lista;
    }

    public String getCanal() {
        return canal;
    }

    public void setCanal(String subFamilia) {
        this.canal = subFamilia;
    }

    public String getGrupo() {
        return grupo;
    }

    public void setGrupo(String subCategoria) {
        this.grupo = subCategoria;
    }

    public BigDecimal getPrecio() {
        BigDecimal formatNumber = new BigDecimal(String.valueOf(Precio));
        Precio = formatNumber.setScale(2, RoundingMode.DOWN);
        return Precio;
    }

    public void setPrecio(BigDecimal precio) {
        Precio = precio;
    }


    public String getFamilia() {
        return familia;
    }

    public void setFamilia(String familia) {
        this.familia=familia;}

    public int getReal_qty() {
        return real_qty;
    }

    public void setReal_qty(int disponible) {
        this.real_qty = disponible;
    }


    public int getReal_qty3() {
        return real_qty3;
    }

    public void setReal_qty3(int disponible) {
        this.real_qty3 = disponible;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.Descripcion = descripcion;
    }

    public String getCategoria() {
        return categoria;
    }


    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricator) {
        this.fabricante = fabricator;
    }

    public String getArticulo() {
        return articulo;
    }

    public void setArticulo(String Articulo) {
        this.articulo = articulo;
    }

    public Product() {

    }
}
