package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.CardDApk;
import com.spring.data.jpa.models.entity.VentaD;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface IVentaDDao extends CrudRepository<VentaD, CardDApk> {

    VentaD save(VentaD venta);

    List<VentaD> findByID(int ID);
}
