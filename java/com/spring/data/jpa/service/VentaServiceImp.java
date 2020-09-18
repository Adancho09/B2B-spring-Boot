package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IVentaDao;
import com.spring.data.jpa.models.entity.Venta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VentaServiceImp implements IVentaService {


    @Autowired
    private IVentaDao ventaDao;


    @Override
    @Transactional(readOnly = true)
    public Page<Venta> findAllByClienteAndEstatus(String cliente,String estatus,Pageable pageRequest) {
        return  ventaDao.findAllByClienteAndEstatus(cliente,estatus,pageRequest);
    }



}
