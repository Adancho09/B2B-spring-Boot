package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.cteEnviarA;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IAddressService {
    Page<cteEnviarA> findAllByCliente(String cliente, Pageable pageable);
    cteEnviarA findByIDAndCliente(int id,String cliente);
    cteEnviarA findByID(int id);
    cteEnviarA save(cteEnviarA cte);
    cteEnviarA findByNombre(String nombre);

}
