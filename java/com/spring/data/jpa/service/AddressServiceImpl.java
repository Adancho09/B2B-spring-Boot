package com.spring.data.jpa.service;

import com.spring.data.jpa.models.dao.IAddressDao;
import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.cteEnviarA;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AddressServiceImpl implements IAddressService{
    @Autowired
    private IAddressDao addressDao;
    @Override
    @Transactional(readOnly = true)
    public Page<cteEnviarA> findAllByCliente(String cliente, Pageable pageable) {
        return addressDao.findAllByCliente(cliente,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public cteEnviarA findByIDAndCliente(int ID,String cliente) {
        return addressDao.findByIDAndCliente(ID,cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public cteEnviarA findByID(int id) {
        return addressDao.findByID(id);
    }

    @Override
    @Transactional
    public cteEnviarA save(cteEnviarA cte) {
        return addressDao.save(cte);
    }

    @Override
    public cteEnviarA findByNombre(String nombre) {
        return addressDao.findByNombre(nombre);
    }

}
