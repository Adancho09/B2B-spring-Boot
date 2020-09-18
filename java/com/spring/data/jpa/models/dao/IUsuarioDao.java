package com.spring.data.jpa.models.dao;

import com.spring.data.jpa.models.entity.Usuario;
import org.springframework.data.repository.CrudRepository;

public interface IUsuarioDao extends CrudRepository <Usuario , String> {

    public Usuario findByCliente(String cliente);



}
