package com.spring.data.jpa.service;


import com.spring.data.jpa.models.dao.IVentaODao;
import com.spring.data.jpa.models.entity.VentaOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class IVentaOServiceImpl  implements IVentaOService{

    @Autowired

    private IVentaODao ventaODao;


    @Override
    @Transactional
    public VentaOrder save(VentaOrder o) {
        return ventaODao.save(o);
    }

   @Override
    public VentaOrder findByMovidAndID(String movId,int id) {
        return ventaODao.findByMovidAndID(movId,id);
    }

    @Override
    @Transactional(readOnly = true)
    public String findTheLastOne() {
        return ventaODao.findTheLastOne();
    }

    @Override
    @Transactional(readOnly = true)
    public int findTheLAstOneID() {
        return ventaODao.findTheLAstOneID();
    }
}
