package com.spring.data.jpa.service;


import com.spring.data.jpa.models.dao.IProductDao;
import com.spring.data.jpa.models.entity.vw_b2barticulos_row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    @Qualifier("productBean")
    private IProductDao productDao;


    @Override
    @Transactional(readOnly = true)
    public List<vw_b2barticulos_row> findAllByLista(String lista) {
        return (List<vw_b2barticulos_row>) productDao.findAllByLista(lista);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<vw_b2barticulos_row> findAllByLista(String lista,Pageable pageable)
    {
        return productDao.findAllByLista(lista,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<vw_b2barticulos_row> findByFabricanteAndLista(String fabricante,String lista, Pageable pageable) {
        return  productDao.findByFabricanteAndLista(fabricante,lista,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public vw_b2barticulos_row findByArticuloAndLista(String articulo,String lista) {
     return productDao.findByArticuloAndLista(articulo,lista);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<vw_b2barticulos_row> findByCategoriaAndLista(String categoria,String lista, Pageable pageable) {
        return productDao.findByCategoriaAndLista(categoria,lista,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<vw_b2barticulos_row> findByCategoriaAndGrupoAndLista(String categoria, String subCategoria,String lista, Pageable pageable) {
        return productDao.findByCategoriaAndGrupoAndLista(categoria,  subCategoria,lista, pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<vw_b2barticulos_row> findByCategoriaAndGrupoAndFamiliaAndLista(String categoria, String subCategoria,String familia,String lista, Pageable pageable) {
        return productDao.findByCategoriaAndGrupoAndFamiliaAndLista(categoria,subCategoria,familia,lista,pageable);
    }
    @Override
    @Transactional(readOnly = true)
    public Page<vw_b2barticulos_row> findByCategoriaAndGrupoAndFamiliaAndCanalAndLista(String categoria, String subCategoria,String familia,String canal,String lista, Pageable pageable) {
        return productDao.findByCategoriaAndGrupoAndFamiliaAndCanalAndLista(categoria,  subCategoria,familia ,canal,lista, pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> findByCategoriaL(String lista,String categoria) {
        return productDao.findByCategoriaL(lista,categoria);
    }
    @Override
    @Transactional(readOnly = true)
    public List<String> findByGrupo(String grupo,String lista) {
        return productDao.findByGrupo(grupo,lista);
    }



    @Override
    @Transactional(readOnly = true)
    public List<String> findByCategoriaAndGrupo(String categoria,String grupo,String lista){
        return productDao.findByCategoriaAndGrupo(categoria,grupo,lista);
    }
    @Override
    @Transactional(readOnly = true)
    public List<String> findByCategoriaAndGrupoAndFamilia(String categoria,String grupo,String familia,String lista) {
        return productDao.findByCategoriaAndGrupoAndFamilia(categoria,grupo,familia,lista);
    }

}
