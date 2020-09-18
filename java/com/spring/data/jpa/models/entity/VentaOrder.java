package com.spring.data.jpa.models.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name="Venta")
public class VentaOrder  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ID;
    private String empresa;
    private String cliente;
    private String fechaemision;
    private String fecharequerida;
    private String agente;
    private String ordencompra;
    private String condicion;
    private BigDecimal importe;
    private BigDecimal impuestos;
    private String mov;
    private String moneda;
    private int enviara;
    private int almacen;
    private String movid;
    private String estatus;
    private String referencia;
    private String usuario;
    private int tipocambio;
    private String observaciones;
    private String origentipo;
    private String ejercicio;
    private String periodo;
    private String listapreciosesp;

    private static final long serialVersionUID = 1L;

    public VentaOrder() {
    }



    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getListapreciosesp() {
        return listapreciosesp;
    }

    public void setListapreciosesp(String listapreciosesp) {
        this.listapreciosesp = listapreciosesp;
    }

    public String getEjercicio() {
        return ejercicio;
    }

    public void setEjercicio(String ejercicio) {
        this.ejercicio = ejercicio;
    }

    public String getOrigentipo() {
        return origentipo;
    }

    public void setOrigentipo(String origentipo) {
        this.origentipo = origentipo;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public int getTipocambio() {
        return tipocambio;
    }

    public void setTipocambio(int tipocambio) {
        this.tipocambio = tipocambio;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getReferencia() {
        return referencia;
    }

    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
    }

    public String getMovid() {
        return movid;
    }

    public void setMovid(String movid) {
        this.movid = movid;
    }

    public int getAlmacen() {
        return almacen;
    }

    public void setAlmacen(int almacen) {
        this.almacen = almacen;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    public String getMov() {
        return mov;
    }

    public void setMov(String mov) {
        this.mov = mov;
    }



    public String getOrdencompra() {
        return ordencompra;
    }

    public void setOrdencompra(String ordencompra) {
        this.ordencompra = ordencompra;
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


    public String getFecharequerida() {
        return fecharequerida;
    }

    public void setFecharequerida(String fecharequeridacita) {
        this.fecharequerida= fecharequeridacita;
    }

    public String getFechaemision() {
        return fechaemision;
    }

    public void setFechaemision(String fechaemision) {
        this.fechaemision = fechaemision;
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

    public String getEmpresa() {
        return empresa;
    }

    public void setEmpresa(String empresa) {
        this.empresa = empresa;
    }

    public String getCondicion() {
        return condicion;
    }

    public void setCondicion(String condicion) {
        this.condicion = condicion;
    }

    public String getCliente() {

        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getAgente() {
        return agente;
    }

    public void setAgente(String agente) {
        this.agente = agente;
    }
}
