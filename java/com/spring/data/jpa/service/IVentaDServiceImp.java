package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IVentaDDao;
import com.spring.data.jpa.models.entity.VentaD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IVentaDServiceImp implements IVentaDService {
 @Autowired
 private IVentaDDao ventaDDao;
    @Override
    public VentaD save(VentaD ventaD) {
        return ventaDDao.save(ventaD);
    }

    @Override
    public List<VentaD> findByID(int ID) {
        return ventaDDao.findByID(ID);
    }
}
