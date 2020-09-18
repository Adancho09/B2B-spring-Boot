package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.cteEnviarA;
import com.spring.data.jpa.models.entity.cteEnviarAPk;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IAddressDao extends PagingAndSortingRepository<cteEnviarA, cteEnviarAPk> {


              Page<cteEnviarA> findAllByCliente(String cliente, Pageable pageable);
              cteEnviarA findByIDAndCliente(int id,String cliente);
              cteEnviarA findByID(int id);
              cteEnviarA save(cteEnviarA cte);
              cteEnviarA findByNombre(String nombre);



}
