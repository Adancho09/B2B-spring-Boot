package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IVentaService
{


    Page<Venta> findAllByClienteAndEstatus(String cliente,String estatus,Pageable pageRequest);

}
