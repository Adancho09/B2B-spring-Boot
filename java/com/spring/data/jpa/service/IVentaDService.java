package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.VentaD;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IVentaDService {
    VentaD save(VentaD venta);

    List<VentaD> findByID(int ID);
}
