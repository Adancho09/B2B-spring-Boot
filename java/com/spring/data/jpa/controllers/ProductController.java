package com.spring.data.jpa.controllers;


import com.spring.data.jpa.Paginator.PageRender;
import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.CardD;
import com.spring.data.jpa.models.entity.Usuario;
import com.spring.data.jpa.models.entity.vw_b2barticulos_row;
import com.spring.data.jpa.service.ICardService;
import com.spring.data.jpa.service.ICartService;
import com.spring.data.jpa.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Map;



@Controller
public class ProductController {


    @Autowired
    private IProductService productoService;

    @Autowired
    private IUsuarioDao usuarioDao;
    @Autowired
    private ICardService cardService;
    @Autowired
    private ICartService cartService;


    @RequestMapping(value="/listar",method = RequestMethod.GET)
    public String listar(@RequestParam Map<String,String> requestParams,Model model){

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);

       String ID = cartService.findByRecentDate(usuario.getCliente());
       if(ID!=null)
       {
           List<CardD>cardd=cardService.findAllByID(Integer.parseInt(ID));
           if(cardd.size()>0)
           {
               model.addAttribute("carCount",cardd.size());
           }
       }


        String category=requestParams.get("category");
        String subcategory=requestParams.get("subcategory");
        String fam=requestParams.get("fam");
        String subfam=requestParams.get("subfam");
        System.out.println(subfam);
        if(category==null){
            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,6);
            String company=requestParams.get("company");
            Page<vw_b2barticulos_row> products = productoService.findByFabricanteAndLista(company ,usuario.getListaPreciosEsp(),pageRequest);
            List<String> categories=productoService.findByCategoriaL(usuario.getListaPreciosEsp(),category);

            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?company="+company, products);

            model.addAttribute("categories",categories);
            model.addAttribute("category",category);
            model.addAttribute("Products", products);
            model.addAttribute("page", pageRender);
            return "listar";
        }

        if(category!=null && subcategory==null){
            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,6);


            Page<vw_b2barticulos_row> products=productoService.findByCategoriaAndLista(category,usuario.getListaPreciosEsp(),pageRequest);
            List<String> subCat=productoService.findByGrupo(category,usuario.getListaPreciosEsp());
            System.out.println(subCat);
            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?category="+category, products);
            model.addAttribute("subcat",subCat);
            model.addAttribute("Products", products);
            model.addAttribute("category",category);
            model.addAttribute("page", pageRender);
            return "listar";
        }


        if(subcategory!=null && fam==null){
            System.out.println("entre a grupo");
            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,6);


            Page<vw_b2barticulos_row> products=productoService.findByCategoriaAndGrupoAndLista(category,subcategory,usuario.getListaPreciosEsp(),pageRequest);
            List<String> familia1 =productoService.findByCategoriaAndGrupo(category,subcategory,usuario.getListaPreciosEsp());
            System.out.println(familia1);
            System.out.println(category+subcategory);
            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?category="+category+"&subcategory="+subcategory, products);
            model.addAttribute("familiaList",familia1);
            model.addAttribute("subcat",subcategory);
            model.addAttribute("Products", products);
            model.addAttribute("category",category);
            model.addAttribute("page", pageRender);
            return "listar";
        }
        if(fam!=null && subfam==null){
            System.out.println("entre a Familia ");
            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,6);


            Page<vw_b2barticulos_row> products=productoService.findByCategoriaAndGrupoAndFamiliaAndLista(category,subcategory,fam,usuario.getListaPreciosEsp(),pageRequest);
            List<String> familia =productoService.findByCategoriaAndGrupoAndFamilia(category,subcategory,fam,usuario.getListaPreciosEsp());
            System.out.println(familia);
            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?category="+category+"&subcategory="+subcategory+"&fam="+fam, products);
            model.addAttribute("familiaList",familia);
            model.addAttribute("subcat",subcategory);
            model.addAttribute("famParam",fam);
            model.addAttribute("Products", products);
            model.addAttribute("category",category);
            model.addAttribute("page", pageRender);

            return "listar";
        }
        if(subfam!=null){
            System.out.println("entre a subFamilia ");
            int page=Integer.parseInt(requestParams.get("page"));

            Pageable pageRequest= PageRequest.of(page,6);


            Page<vw_b2barticulos_row> products=productoService.findByCategoriaAndGrupoAndFamiliaAndCanalAndLista(category,subcategory,fam,subfam,usuario.getListaPreciosEsp(),pageRequest);
            List<String> subfamilia =productoService.findByCategoriaAndGrupoAndFamilia(category,subcategory,fam,usuario.getListaPreciosEsp());
            System.out.println(subfamilia);
            PageRender<vw_b2barticulos_row> pageRender = new PageRender<>("/listar?category="+category, products);
            model.addAttribute("subfamilia",subfamilia);
            model.addAttribute("subcat",subcategory);
            model.addAttribute("fam",fam);
            model.addAttribute("Products", products);
            model.addAttribute("category",category);
            model.addAttribute("page", pageRender);
            return "listar";
        }


        return "listar";

    }


    @RequestMapping(value="/articulo",method = RequestMethod.GET)
    public String articulo(@RequestParam Map<String,String> requestParams,Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);
        vw_b2barticulos_row articulo = productoService.findByArticuloAndLista(requestParams.get("SKU"),usuario.getListaPreciosEsp());
        if(articulo==null){
            String error = "No se encontraron resultados para tu busqueda";
            model.addAttribute("Error", error);
            return "errorArt";
    }
        model.addAttribute("Product", articulo);
        return "articulo";
    }
}
