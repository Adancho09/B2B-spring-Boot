package com.spring.data.jpa.models.entity;

import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import javax.persistence.*;

@Entity
@Immutable
@Subselect("SELECT\n" +
        "rs.*\n" +
        "FROM\n" +
        "(\n" +
        "select\n" +
        "   ID,\n" +
        "mov,\n" +
        "Codigo,\n" +
        "Referencia,\n" +
        "Estatus,\n" +
        "Cliente,\n" +
        "venta.Importe,\n" +
        "venta.Impuestos,\n" +
        "FechaEmision,\n" +
        "OrdenCompra,\n" +
        "CASE\n" +
        "WHEN  Mov in ('Pedido B2B') AND Estatus='SINAFECTAR'  THEN 14\n" +
        "WHEN  Observaciones ='PEDIDO A LA ESPERA DE EXISTENCIAS' AND  Mov in ('Pedido B2B') AND Estatus IN ('PENDIENTE') THEN 14\n" +
        "WHEN  Observaciones ='PEDIDO A LA ESPERA DE EXISTENCIAS' AND  Mov in ('Pedido B2B') AND Estatus IN ('CONCLUIDO') THEN 2\n" +
        "WHEN  Mov in ('Pedido B2B')  AND Estatus IN ('PENDIENTE')  THEN 2\n" +
        "WHEN  Mov =  'Factura'       AND Estatus='CONCLUIDO'  AND  (select sum( vd.CantidadPendiente) from venta v join VentaD vd ON (v.id = vd.ID  ) where v.codigo = venta.codigo and Mov = 'Orden Surtido') IS NULL  THEN 5\n" +
        "WHEN  Mov =  'Orden Surtido' AND Estatus='PENDIENTE'  AND  Saldo IS NOT NULL   THEN 9\n" +
        "WHEN  Mov =  'Orden Surtido' AND Estatus='PENDIENTE'  THEN 3\n" +
        "WHEN  Mov in ('Pedido B2B')  AND Estatus='CANCELADO'  THEN 6\n" +
        "ELSE 0\n" +
        "END AS 'PS_estado',\n" +
        "ISNULL( venta.movID,'S/N') AS movID,\n" +
        "ISNULL(convert (varchar (30),CFD.fechatimbrado,103),'S/N') AS fechatimbrado,\n" +
        "Observaciones,\n" +
        "CONCAT( Codigo,'-',venta.movID) as Codweb\n" +
        "\n" +
        "from venta WITH (NOLOCK)\n" +
        "left join cfd WITH (NOLOCK) ON (venta.id = cfd.ModuloID  AND cfd.Modulo='VTAS')\n" +
        "where Referencia= 'Pedido desde B2B' AND venta.mov <> 'BackO Surtido' AND venta.mov != 'Orden surtido' ) AS rs\n" +
        "where  rs.PS_estado != '0' --and rs.Observaciones IN ('PEDIDO', 'PEDIDO A LA ESPERA DE EXISTENCIAS')\n")
public class Venta {
    @Id
    int ID;
    @Column(unique=true,name="movid" )
    String movId;
    @Column(unique=true,name="mov")
    String mov;
    int impuestos;
    int importe;

    @Column(name="fechaemision")
    private String  fechaEmision;
    private String estatus;
    @Column(unique=true)
    private String cliente;
private String ordencompra;

    public String getOrdencompra() {
        return ordencompra;
    }

    public void setOrdencompra(String ordencompra) {
        this.ordencompra = ordencompra;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }




    public String getEstatus() {
        return estatus;
    }

    public String getFechaEmision() {
        return fechaEmision;
    }

    public int getID() {
        return ID;
    }

    public String getMov() {
        return mov;
    }

    public String getMovId() {
        return movId;
    }


    public int getImpuestos() {
        return impuestos;
    }

    public void setImpuestos(int impuestos) {
        this.impuestos = impuestos;
    }

    public int getImporte() {
        return importe;
    }

    public void setImporte(int importe) {
        this.importe = importe;
    }

    public void setMovId(String movId) {
        movId = movId;
    }

    public void setMov(String mov) {
        mov = mov;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public void setFechaEmision(String fechaEmision) {
        fechaEmision = fechaEmision;
    }

    public void setEstatus(String estatus) {
        estatus = estatus;
    }

    public Venta() {
    }
}
