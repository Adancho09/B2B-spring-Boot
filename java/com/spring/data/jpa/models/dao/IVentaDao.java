package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.Venta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface IVentaDao extends PagingAndSortingRepository<Venta, Integer> {





    Page<Venta> findAllByClienteAndEstatus(String cliente,String estatus,Pageable pageRequest);


}
