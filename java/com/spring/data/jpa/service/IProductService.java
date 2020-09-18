package com.spring.data.jpa.service;

import com.spring.data.jpa.models.entity.vw_b2barticulos_row;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface IProductService  {
    public List<vw_b2barticulos_row> findAllByLista(String lista );
    public Page<vw_b2barticulos_row> findAllByLista(String lista,Pageable pegeable);
    Page<vw_b2barticulos_row> findByFabricanteAndLista(String fabricante,String lista, Pageable pageable);
    Page<vw_b2barticulos_row> findByCategoriaAndLista(String categoria, String lista,Pageable pageable);
    vw_b2barticulos_row findByArticuloAndLista(String articulo,String lista);
    Page<vw_b2barticulos_row> findByCategoriaAndGrupoAndLista(String categoria,String grupo,String lista,Pageable pageable);
    Page<vw_b2barticulos_row> findByCategoriaAndGrupoAndFamiliaAndLista(String categoria,String grupo,String familia,String lista,Pageable pageable);
    Page<vw_b2barticulos_row> findByCategoriaAndGrupoAndFamiliaAndCanalAndLista(String categoria,String grupo,String familia,String canal,String lista,Pageable pageable);
    @Query("select categoria from vw_b2barticulos_row where lista =?1  GROUP BY categoria ")
    List<String> findByCategoriaL(String lista,String categoria);
    @Query("select grupo from vw_b2barticulos_row where categoria =?1 and lista =?2 GROUP BY grupo")
    List<String> findByGrupo(String categoria,String lista);
    @Query("select familia from vw_b2barticulos_row  where categoria =?1 and grupo =?2 and lista =?3  GROUP BY familia")
    List<String> findByCategoriaAndGrupo(String categoria,String grupo,String lista);
    @Query("select canal from vw_b2barticulos_row  where categoria =?1 and grupo =?2 and familia =?3 and lista =?4 GROUP BY canal")
    List<String> findByCategoriaAndGrupoAndFamilia(String categoria,String grupo,String familia,String lista);



}
