package com.spring.data.jpa.models.dao;


import com.spring.data.jpa.models.entity.VentaOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;


public interface IVentaODao extends CrudRepository<VentaOrder, Integer>{


    VentaOrder save(VentaOrder o);

    VentaOrder findByMovidAndID(String movID,int id);

    @Query(value = "select TOP 1 movid from venta where mov='Pedido B2B' order By id DESC ",nativeQuery = true)
    String findTheLastOne();

    @Query(value = "select TOP 1 id from venta  order By id DESC ",nativeQuery = true)
    int findTheLAstOneID();

}
