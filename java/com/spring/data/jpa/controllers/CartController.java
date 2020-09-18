package com.spring.data.jpa.controllers;

import com.spring.data.jpa.Paginator.PageRender;
import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.*;
import com.spring.data.jpa.service.IAddressService;
import com.spring.data.jpa.service.ICardService;
import com.spring.data.jpa.service.ICartService;
import com.spring.data.jpa.service.IProductService;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.internal.build.AllowPrintStacktrace;
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
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class CartController {



    @Autowired
    private IUsuarioDao usuarioDao;
    @Autowired
    private IProductService productoService;
    @Autowired
    private ICardService cardService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IAddressService addressService;








    @RequestMapping(value="/cart",method = RequestMethod.GET)
    public String cart(Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);
        String ID = cartService.findByRecentDate(usuario.getCliente());


        if(ID != null)
        {
            List<CardD> ArticulosCard= cardService.findAllByID(Integer.parseInt(ID));
            model.addAttribute("cartProducts",ArticulosCard);
        }


        model.addAttribute("RecentCart",ID);

        return "cart";

    }

    @RequestMapping(value="/searchArt",method = RequestMethod.GET)
    public String articulo(@RequestParam Map<String,String> requestParams, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);
        String ID = cartService.findByRecentDate(usuario.getCliente());



        vw_b2barticulos_row articulo = productoService.findByArticuloAndLista(requestParams.get("SKU"),usuario.getListaPreciosEsp());
        if(articulo==null){
            String error = "No se encontraron resultados para tu busqueda";
            model.addAttribute("Error", error);
            return "cart";
        }

        System.out.println(ID+"siandoenSearch");
        if(ID != null)
        {
            List<CardD> ArticulosCard= cardService.findAllByID(Integer.parseInt(ID));
            model.addAttribute("cartProducts",ArticulosCard);
        }
        model.addAttribute("RecentCart",ID);
        model.addAttribute("Product", articulo);
        return "cart";
    }

    @RequestMapping(value="/addToCart",method = RequestMethod.GET)
    public String addToCart(@RequestParam Map<String,String> requestParams, Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);
        vw_b2barticulos_row articulo = productoService.findByArticuloAndLista(requestParams.get("SKU"),usuario.getListaPreciosEsp());


        String ID = cartService.findByRecentDate(usuario.getCliente());
        System.out.println(ID);
        if(ID ==null)
        {
            Date ahora = new Date();
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//HH:mm:ss
            String fechaAlta = formateador.format(ahora);
            System.out.println(fechaAlta);

            Card cart = new Card();
            cart.setCliente(usuario.getCliente());
            cart.setAgente(usuario.getAgente());
            cart.setFechaalta(fechaAlta);
            cart.setIsactive('1');
            cart.setEnviara(0);
            try {
                cartService.save(cart);
            }catch (Exception e)
            {
                System.out.println(e);
            }



        }
        ID = cartService.findByRecentDate(usuario.getCliente());
System.out.println(ID);

             Card cartID = cartService.findByID(Integer.parseInt(ID));
        if(cartID.isIsactive()=='1'){
            int cantidad1 = Integer.parseInt(requestParams.get("cantidad1"));
            int cantidad3 = Integer.parseInt(requestParams.get("cantidad3"));
            DecimalFormat df2 = new DecimalFormat("#.00");
            if(cantidad1==0 && cantidad3 ==0)
            {
                String error = "Selecciona  una cantidad Disponible ";
                model.addAttribute("Error", error);
                return "cart";
            }else if(cantidad1>0 || cantidad3 >0)
            {
                if(cantidad1>articulo.getReal_qty() || cantidad3>articulo.getReal_qty3()  ){
                    String error = "Selecciona  una cantidad Disponible ";
                    model.addAttribute("Error", error);
                    return "cart";
                }else if(cantidad1<=articulo.getReal_qty() || cantidad3<=articulo.getReal_qty3()){
                    CardD card = new CardD();
                    card.setArticulo(requestParams.get("SKU"));
                    List<CardD>cardd=cardService.findAllByID(Integer.parseInt(ID));
                    int renglon =0;
                    if(cardd.size()>0)
                    {
                        renglon =cardd.size();
                    }
                    if(cantidad1==0){
                        boolean isDuplicated =false;
                        String param=requestParams.get("SKU");

                        for(int i=0;i<cardd.size();i++)
                        {





                            char[] aCaracteres = cardd.get(i).getArticulo().toCharArray();

                            for (int x=0;x<aCaracteres.length;x++);



                            char[] aCaracteres2 = param.toCharArray();
                            boolean isUpdate =true;
                            for (int x=0;x<aCaracteres2.length;x++)
                            {
                                if(aCaracteres[x]!=aCaracteres2[x]){
                                    isUpdate=false;
                                }
                            }



                            if(isUpdate && cardd.get(i).getAlmacen()==3)
                            {
                                System.out.println("si entre al for de cantidad1 0 "+cardd.get(i).getArticulo()+" param "+requestParams.get("SKU"));
                                card.setID(cartID.getID());
                                card.setRenglon(cardd.get(i).getRenglon());
                                card.setAlmacen(3);
                                card.setCantidad(cantidad3+cardd.get(i).getCantidad());
                                double precio = Double.parseDouble(requestParams.get("precio"))*cantidad3;
                                precio = precio+cardd.get(i).getPrecio().doubleValue();
                                BigDecimal formatNumber = new BigDecimal(precio);
                                card.setArticulo(articulo.getArticulo());
                                card.setPrecio( formatNumber.setScale(2, RoundingMode.DOWN));
                                double impuesto =precio*0.16;
                                card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                                double total = precio*1.16;
                                card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                                card.setIsaviable(1);
                                cardService.saveCard(card);
                                isDuplicated=true;

                            }

                        }
                        if(isDuplicated){
                            return "redirect:/listar?company=BALAM RUSH&page=0";

                        }
                        card.setID(cartID.getID());
                        card.setRenglon(renglon+1);
                        card.setAlmacen(3);
                        card.setCantidad(cantidad3);
                        double precio = Double.parseDouble(requestParams.get("precio"))*cantidad3;
                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        double impuesto =precio*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        double total = precio*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setIsaviable(1);
                        cardService.saveCard(card);

                        return "redirect:/listar?company=BALAM RUSH&page=0";
                    }else if(cantidad3==0){
                        boolean isDuplicated =false;
                        String param=requestParams.get("SKU");

                        for(int i=0;i<cardd.size();i++)
                        {





                            char[] aCaracteres = cardd.get(i).getArticulo().toCharArray();

                            for (int x=0;x<aCaracteres.length;x++);



                            char[] aCaracteres2 = param.toCharArray();
                            boolean isUpdate =true;
                            for (int x=0;x<aCaracteres2.length;x++)
                            {
                                if(aCaracteres[x]!=aCaracteres2[x]){
                                    isUpdate=false;
                                }
                            }


                            if(cardd.get(i)==null){

                            }else
                            {
                                if(isUpdate && cardd.get(i).getAlmacen()==1)
                                {
                                    System.out.println("si entre al for de cantidad1 0 "+cardd.get(i).getArticulo()+" param "+requestParams.get("SKU"));
                                    card.setID(cartID.getID());
                                    card.setRenglon(cardd.get(i).getRenglon());
                                    card.setAlmacen(1);
                                    card.setCantidad(cantidad1+cardd.get(i).getCantidad());
                                    double precio = Double.parseDouble(requestParams.get("precio"))*cantidad1;
                                    precio = precio+cardd.get(i).getPrecio().doubleValue();
                                    card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                                    double impuesto =precio*0.16;
                                    card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                                    double total = precio*1.16;
                                    card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                                    card.setIsaviable(1);
                                    cardService.saveCard(card);
                                    isDuplicated=true;

                                }
                            }


                        }
                        if(isDuplicated){
                            return "redirect:/listar?company=BALAM RUSH&page=0";
                        }
                        card.setID(cartID.getID());
                        card.setRenglon(renglon+1);
                        card.setAlmacen(1);
                        card.setCantidad(cantidad1);
                        double precio = Double.parseDouble(requestParams.get("precio"))*cantidad1;
                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        double impuesto =precio*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        double total = precio*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setIsaviable(1);
                        cardService.saveCard(card);

                        return "redirect:/listar?company=BALAM RUSH&page=0";
                    }else{
                        boolean isDuplicated =false;
                        String param=requestParams.get("SKU");

                        for(int i=0;i<cardd.size();i++)
                        {





                            char[] aCaracteres = cardd.get(i).getArticulo().toCharArray();

                            for (int x=0;x<aCaracteres.length;x++);



                            char[] aCaracteres2 = param.toCharArray();
                            boolean isUpdate =true;
                            for (int x=0;x<aCaracteres2.length;x++)
                            {
                                if(aCaracteres[x]!=aCaracteres2[x]){
                                    isUpdate=false;
                                }
                            }



                            if(isUpdate && cardd.get(i).getAlmacen()==1)
                            {
                                System.out.println("si entre al for de cantidad1 0 "+cardd.get(i).getArticulo()+" param "+requestParams.get("SKU"));
                                card.setID(cartID.getID());
                                card.setRenglon(cardd.get(i).getRenglon());
                                card.setAlmacen(1);
                                card.setCantidad(cantidad1+cardd.get(i).getCantidad());
                                double precio = Double.parseDouble(requestParams.get("precio"))*cantidad1;
                                precio = precio+cardd.get(i).getPrecio().doubleValue();
                                card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                                double impuesto =precio*0.16;
                                card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                                double total = precio*1.16;
                                card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                                card.setIsaviable(1);
                                cardService.saveCard(card);
                                isDuplicated=true;
                            }
                            if(isUpdate && cardd.get(i).getAlmacen()==3)
                            {
                                System.out.println("si entre al for de cantidad1 0 "+cardd.get(i).getArticulo()+" param "+requestParams.get("SKU"));
                                card.setID(cartID.getID());
                                card.setRenglon(cardd.get(i).getRenglon());
                                card.setAlmacen(3);
                                card.setCantidad(cantidad3+cardd.get(i).getCantidad());
                                double precio = Double.parseDouble(requestParams.get("precio"))*cantidad3;
                                precio = precio+cardd.get(i).getPrecio().doubleValue();
                                card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                                double impuesto =precio*0.16;
                                card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                                double total = precio*1.16;
                                card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                                card.setIsaviable(1);
                                cardService.saveCard(card);
                                isDuplicated=true;
                            }

                        }
                        if(isDuplicated){
                            return "redirect:/listar?company=BALAM RUSH&page=0";
                        }
                        card.setID(cartID.getID());
                        card.setAlmacen(3);
                        card.setCantidad(cantidad3);
                        card.setRenglon(renglon+1);
                        double precio = Double.parseDouble(requestParams.get("precio"))*cantidad3;

                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        double impuesto =precio*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        double total = precio*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setIsaviable(1);
                        cardService.saveCard(card);
                        card.setID(cartID.getID());
                        card.setAlmacen(1);
                        card.setCantidad(cantidad1);
                        double precio3 = Double.parseDouble(requestParams.get("precio"))*cantidad1;
                        card.setPrecio(BigDecimal.valueOf(precio3).setScale(2,RoundingMode.DOWN));
                        double impuesto3 =precio*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto3).setScale(2,RoundingMode.DOWN));
                        double total3 = precio*1.16;
                        card.setTotal(BigDecimal.valueOf(total3).setScale(2,RoundingMode.DOWN));
                        card.setRenglon(renglon+2);
                        card.setIsaviable(1);
                        cardService.saveCard(card);

                        return "redirect:/listar?company=BALAM RUSH&page=0";
                    }

                }

            }
        }else
        {
            cartID.setID(cartID.getID()+1);
            int cantidad1 = Integer.parseInt(requestParams.get("cantidad1"));
            int cantidad3 = Integer.parseInt(requestParams.get("cantidad3"));
            DecimalFormat df2 = new DecimalFormat("#.00");
            if(cantidad1==0 && cantidad3 ==0)
            {
                String error = "Selecciona  una cantidad Disponible ";
                model.addAttribute("Error", error);
                return "cart";
            }else if(cantidad1>0 || cantidad3 >0)
            {
                if(cantidad1>articulo.getReal_qty() || cantidad3>articulo.getReal_qty3()  ){
                    String error = "Selecciona  una cantidad Disponible ";
                    model.addAttribute("Error", error);
                    return "cart";
                }else if(cantidad1<=articulo.getReal_qty() || cantidad3<=articulo.getReal_qty3()){
                    CardD card = new CardD();
                    card.setArticulo(requestParams.get("SKU"));
                    List<CardD>cardd=cardService.findAllByID(Integer.parseInt(ID));
                    int renglon =0;
                    if(cardd.size()>0)
                    {
                        renglon =cardd.size();
                    }
                    if(cantidad1==0){
                        boolean isDuplicated =false;
                        String param=requestParams.get("SKU");

                        for(int i=0;i<cardd.size();i++)
                        {





                            char[] aCaracteres = cardd.get(i).getArticulo().toCharArray();

                            for (int x=0;x<aCaracteres.length;x++);



                            char[] aCaracteres2 = param.toCharArray();
                            boolean isUpdate =true;
                            for (int x=0;x<aCaracteres2.length;x++)
                            {
                                if(aCaracteres[x]!=aCaracteres2[x]){
                                    isUpdate=false;
                                }
                            }



                            if(isUpdate && cardd.get(i).getAlmacen()==3)
                            {
                                System.out.println("si entre al for de cantidad1 0 "+cardd.get(i).getArticulo()+" param "+requestParams.get("SKU"));
                                card.setID(cartID.getID());
                                card.setRenglon(cardd.get(i).getRenglon());
                                card.setAlmacen(3);
                                card.setCantidad(cantidad3+cardd.get(i).getCantidad());
                                double precio = Double.parseDouble(requestParams.get("precio"))*cantidad3;
                                precio = precio+cardd.get(i).getPrecio().doubleValue();
                                BigDecimal formatNumber = new BigDecimal(precio);
                                card.setArticulo(articulo.getArticulo());
                                card.setPrecio( formatNumber.setScale(2, RoundingMode.DOWN));
                                double impuesto =precio*0.16;
                                card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                                double total = precio*1.16;
                                card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                                card.setIsaviable(1);
                                cardService.saveCard(card);
                                isDuplicated=true;

                            }

                        }
                        if(isDuplicated){
                            return "redirect:/listar?company=BALAM RUSH&page=0";
                        }
                        card.setID(cartID.getID());
                        card.setRenglon(renglon+1);
                        card.setAlmacen(3);
                        card.setCantidad(cantidad3);
                        double precio = Double.parseDouble(requestParams.get("precio"))*cantidad3;
                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        double impuesto =precio*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        double total = precio*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setIsaviable(1);
                        cardService.saveCard(card);

                        return "redirect:/listar?company=BALAM RUSH&page=0";
                    }else if(cantidad3==0){
                        boolean isDuplicated =false;
                        String param=requestParams.get("SKU");

                        for(int i=0;i<cardd.size();i++)
                        {





                            char[] aCaracteres = cardd.get(i).getArticulo().toCharArray();

                            for (int x=0;x<aCaracteres.length;x++);



                            char[] aCaracteres2 = param.toCharArray();
                            boolean isUpdate =true;
                            for (int x=0;x<aCaracteres2.length;x++)
                            {
                                if(aCaracteres[x]!=aCaracteres2[x]){
                                    isUpdate=false;
                                }
                            }


                            if(cardd.get(i)==null){

                            }else
                            {
                                if(isUpdate && cardd.get(i).getAlmacen()==1)
                                {
                                    System.out.println("si entre al for de cantidad1 0 "+cardd.get(i).getArticulo()+" param "+requestParams.get("SKU"));
                                    card.setID(cartID.getID());
                                    card.setRenglon(cardd.get(i).getRenglon());
                                    card.setAlmacen(1);
                                    card.setCantidad(cantidad1+cardd.get(i).getCantidad());
                                    double precio = Double.parseDouble(requestParams.get("precio"))*cantidad1;
                                    precio = precio+cardd.get(i).getPrecio().doubleValue();
                                    card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                                    double impuesto =precio*0.16;
                                    card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                                    double total = precio*1.16;
                                    card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                                    card.setIsaviable(1);
                                    cardService.saveCard(card);
                                    isDuplicated=true;

                                }
                            }


                        }
                        if(isDuplicated){
                            return "redirect:/listar?company=BALAM RUSH&page=0";
                        }
                        card.setID(cartID.getID());
                        card.setRenglon(renglon+1);
                        card.setAlmacen(1);
                        card.setCantidad(cantidad1);
                        double precio = Double.parseDouble(requestParams.get("precio"))*cantidad1;
                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        double impuesto =precio*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        double total = precio*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setIsaviable(1);
                        cardService.saveCard(card);

                        return "redirect:/listar?company=BALAM RUSH&page=0";
                    }else{
                        boolean isDuplicated =false;
                        String param=requestParams.get("SKU");

                        for(int i=0;i<cardd.size();i++)
                        {





                            char[] aCaracteres = cardd.get(i).getArticulo().toCharArray();

                            for (int x=0;x<aCaracteres.length;x++);



                            char[] aCaracteres2 = param.toCharArray();
                            boolean isUpdate =true;
                            for (int x=0;x<aCaracteres2.length;x++)
                            {
                                if(aCaracteres[x]!=aCaracteres2[x]){
                                    isUpdate=false;
                                }
                            }



                            if(isUpdate && cardd.get(i).getAlmacen()==1)
                            {
                                System.out.println("si entre al for de cantidad1 0 "+cardd.get(i).getArticulo()+" param "+requestParams.get("SKU"));
                                card.setID(cartID.getID());
                                card.setRenglon(cardd.get(i).getRenglon());
                                card.setAlmacen(1);
                                card.setCantidad(cantidad1+cardd.get(i).getCantidad());
                                double precio = Double.parseDouble(requestParams.get("precio"))*cantidad1;
                                precio = precio+cardd.get(i).getPrecio().doubleValue();
                                card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                                double impuesto =precio*0.16;
                                card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                                double total = precio*1.16;
                                card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                                card.setIsaviable(1);
                                cardService.saveCard(card);
                                isDuplicated=true;
                            }
                            if(isUpdate && cardd.get(i).getAlmacen()==3)
                            {
                                System.out.println("si entre al for de cantidad1 0 "+cardd.get(i).getArticulo()+" param "+requestParams.get("SKU"));
                                card.setID(cartID.getID());
                                card.setRenglon(cardd.get(i).getRenglon());
                                card.setAlmacen(3);
                                card.setCantidad(cantidad3+cardd.get(i).getCantidad());
                                double precio = Double.parseDouble(requestParams.get("precio"))*cantidad3;
                                precio = precio+cardd.get(i).getPrecio().doubleValue();
                                card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                                double impuesto =precio*0.16;
                                card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                                double total = precio*1.16;
                                card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                                card.setIsaviable(1);
                                cardService.saveCard(card);
                                isDuplicated=true;
                            }

                        }
                        if(isDuplicated){
                            return "redirect:/listar?company=BALAM RUSH&page=0";
                        }
                        card.setID(cartID.getID());
                        card.setAlmacen(3);
                        card.setCantidad(cantidad3);
                        card.setRenglon(renglon+1);
                        double precio = Double.parseDouble(requestParams.get("precio"))*cantidad3;

                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        double impuesto =precio*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        double total = precio*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setIsaviable(1);
                        cardService.saveCard(card);
                        card.setID(cartID.getID());
                        card.setAlmacen(1);
                        card.setCantidad(cantidad1);
                        double precio3 = Double.parseDouble(requestParams.get("precio"))*cantidad1;
                        card.setPrecio(BigDecimal.valueOf(precio3).setScale(2,RoundingMode.DOWN));
                        double impuesto3 =precio*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto3).setScale(2,RoundingMode.DOWN));
                        double total3 = precio*1.16;
                        card.setTotal(BigDecimal.valueOf(total3).setScale(2,RoundingMode.DOWN));
                        card.setRenglon(renglon+2);
                        card.setIsaviable(1);
                        cardService.saveCard(card);

                        return "redirect:/listar?company=BALAM RUSH&page=0";
                    }

                }

            }

        }





        if(articulo==null){
            String error = "No se encontraron resultados para tu busqueda";
            model.addAttribute("Error", error);
            return "cart";
        }
        model.addAttribute("Product", articulo);
        return "cart";
    }




    @RequestMapping(value="/import",method =RequestMethod.POST)
    public String mapReapExcelDatatoDB(@RequestParam("file") MultipartFile archivo,Model model) throws IOException {




        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);
        String ID = cartService.findByRecentDate(usuario.getCliente());

   ;
        if (ID == null) {
            Date ahora = new Date();
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//HH:mm:ss
            String fechaAlta = formateador.format(ahora);
            System.out.println(fechaAlta);
            Card cart = new Card();
            cart.setCliente(usuario.getCliente());
            cart.setAgente(usuario.getAgente());
            cart.setFechaalta(fechaAlta);
            cart.setEnviara(0);
            cart.setIsactive('1');
            cartService.save(cart);
            ID = cartService.findByRecentDate(usuario.getCliente());

        }
        ID = cartService.findByRecentDate(usuario.getCliente());

        XSSFWorkbook workbook = new XSSFWorkbook(archivo.getInputStream());
        XSSFSheet worksheet = workbook.getSheetAt(0);

        for(int i=0;i<worksheet.getPhysicalNumberOfRows()-1 ;i++) {

            CardD card =new CardD();
            XSSFRow row = worksheet.getRow(i);

            vw_b2barticulos_row articulo = productoService.findByArticuloAndLista((row.getCell(0).getStringCellValue()),usuario.getListaPreciosEsp());

            if(articulo!=null){
                int reqQuant=(int) row.getCell(1).getNumericCellValue();

    System.out.println("solicitada: "+reqQuant+" "+"disponible "+articulo.getReal_qty());
                if(reqQuant<=articulo.getReal_qty()){

                    card.setID(Integer.parseInt(ID));
                    card.setCantidad(reqQuant);
                    card.setRenglon(i);
                    BigDecimal formatNumber = new BigDecimal(String.valueOf(articulo.getPrecio()));
                   double precio  = formatNumber.doubleValue() * card.getCantidad();
                   card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                    card.setArticulo(articulo.getArticulo());
                    double impuesto =card.getPrecio().doubleValue()*0.16;
                    card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                    double total = card.getPrecio().doubleValue()*0.16*1.16;
                    card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));

                    card.setAlmacen(1);
                    card.setIsaviable(1);
                    cardService.saveCard(card);

                }else if(reqQuant>articulo.getReal_qty()&&articulo.getReal_qty()!=0)
                {
                    int pending =reqQuant - articulo.getReal_qty();
                    System.out.println("pendiente: "+pending);
                    if(pending > articulo.getReal_qty() && pending <= articulo.getReal_qty3()){
                        card.setID(Integer.parseInt(ID));
                        card.setCantidad(articulo.getReal_qty());
                        card.setRenglon(i);
                        BigDecimal formatNumber = new BigDecimal(String.valueOf(articulo.getPrecio()));
                        double precio  = formatNumber.doubleValue() * card.getCantidad();
                        card.setArticulo(articulo.getArticulo());
                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        double impuesto =card.getPrecio().doubleValue()*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        double total = card.getPrecio().doubleValue()*0.16*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setAlmacen(1);
                        card.setIsaviable(1);
                        cardService.saveCard(card);
                        card.setID(Integer.parseInt(ID));
                        card.setCantidad(pending);
                        card.setRenglon(i+10);
                        formatNumber = new BigDecimal(String.valueOf(articulo.getPrecio()));
                        precio  = formatNumber.doubleValue() * card.getCantidad();
                        card.setArticulo(articulo.getArticulo());
                        card.setImpuesto1(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        impuesto =card.getPrecio().doubleValue()*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        total = card.getPrecio().doubleValue()*0.16*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setAlmacen(3);
                        card.setIsaviable(1);
                        cardService.saveCard(card);
                    }else{

                        System.out.println("Pendiente 1: "+pending+" "+"disponible "+articulo.getReal_qty3());
                        card.setID(Integer.parseInt(ID));
                        card.setArticulo(articulo.getArticulo());
                        card.setCantidad(articulo.getReal_qty());
                        card.setRenglon(i);
                        BigDecimal formatNumber = new BigDecimal(String.valueOf(articulo.getPrecio()));
                        double precio  = formatNumber.doubleValue() * card.getCantidad();
                        card.setArticulo(articulo.getArticulo());
                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        double impuesto =card.getPrecio().doubleValue()*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        double total = card.getPrecio().doubleValue()*0.16*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setAlmacen(1);
                        card.setIsaviable(1);
                        cardService.saveCard(card);
                        card.setRenglon(i+10);
                        card.setID(Integer.parseInt(ID));
                        card.setArticulo(articulo.getArticulo());
                        card.setCantidad(articulo.getReal_qty3());
                        formatNumber = new BigDecimal(String.valueOf(articulo.getPrecio()));
                        precio  = formatNumber.doubleValue() * card.getCantidad();
                        card.setArticulo(articulo.getArticulo());
                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        impuesto =card.getPrecio().doubleValue()*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        total = card.getPrecio().doubleValue()*0.16*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setAlmacen(3);
                        card.setIsaviable(1);
                        cardService.saveCard(card);
                        int notAvailable=pending-articulo.getReal_qty3();
                        card.setID(Integer.parseInt(ID));
                        card.setRenglon(i+20);
                        card.setArticulo(articulo.getArticulo());
                        card.setCantidad(notAvailable);
                        formatNumber = new BigDecimal(String.valueOf(articulo.getPrecio()));
                        precio  = formatNumber.doubleValue() * card.getCantidad();
                        card.setArticulo(articulo.getArticulo());
                        card.setPrecio(BigDecimal.valueOf(precio).setScale(2,RoundingMode.DOWN));
                        impuesto =card.getPrecio().doubleValue()*0.16;
                        card.setImpuesto1(BigDecimal.valueOf(impuesto).setScale(2,RoundingMode.DOWN));
                        total = card.getPrecio().doubleValue()*0.16*1.16;
                        card.setTotal(BigDecimal.valueOf(total).setScale(2,RoundingMode.DOWN));
                        card.setAlmacen(3);
                        card.setIsaviable(0);
                        cardService.saveCard(card);

                    }

                }
            }

        }

        return "redirect:/cart";
    }




    @RequestMapping(value="/delete",method = RequestMethod.GET)
    public String delete(@RequestParam Map<String,String> requestParams,Model model) {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);

        String ID = cartService.findByRecentDate(usuario.getCliente());

        try{
            CardD card =cardService.findOneByIDAndRenglon(Integer.parseInt(ID),Integer.parseInt(requestParams.get("renglon")));
            if(card != null)
            {
                cardService.delete(card);
            }

        }catch(Exception e){
            return "cart";
        }


        return "redirect:/cart";
    }



    @RequestMapping(value="/deleteCart",method = RequestMethod.GET)
    public String deleteCart(@RequestParam Map<String,String> requestParams,Model model) {


        try{
            Card card =cartService.findByID(Integer.parseInt(requestParams.get("ID")));
            if(card != null)
            {
                card.setIsactive('0');
                cartService.save(card);
            }

        }catch(Exception e){

            return "cart";
        }


        return "redirect:/cart";
    }


    @RequestMapping(value={"/PrepareOrder"},method =RequestMethod.GET)
    public String PrepareOrder(@RequestParam Map<String,String> requestParams, Model model) {


        int page;
        if(requestParams.get("page")==null){
            page=0;
        }else{
            page=Integer.parseInt(requestParams.get("page"));
        }


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            Pageable pageRequest= PageRequest.of(page,3);
            String username = ((UserDetails)principal).getUsername();
            Usuario usuario = usuarioDao.findByCliente(username);

            Page<cteEnviarA> address=addressService.findAllByCliente(usuario.getCliente(),pageRequest);

            PageRender<cteEnviarA> pageRender = new PageRender<>("/PrepareOrder",address);
            String ID = cartService.findByRecentDate(usuario.getCliente());
            Card card =cartService.findByID(Integer.parseInt(ID));

String step = requestParams.get("step2");
                      if(step!=null)
                    {
                       if(Integer.parseInt(requestParams.get("step2"))==0)
                        {
                          card.setEnviara(0);
                           cartService.save(card);
                         }
                    }

            int step2=0;
            if(card.getEnviara() > 0 )
            {
               cteEnviarA addressOne =addressService.findByIDAndCliente(card.getEnviara(),usuario.getCliente());
               model.addAttribute("addressOne",addressOne);
               step2=1;
            }
            ID = cartService.findByRecentDate(usuario.getCliente());


            if(ID != null)
            {
                List<CardD> ArticulosCard= cardService.findAllByID(Integer.parseInt(ID));
                model.addAttribute("cartProducts",ArticulosCard);
                Card card2 =cartService.findByID(Integer.parseInt(ID));
                model.addAttribute("cart",card2);
            }
            model.addAttribute("step2",step2);
            model.addAttribute("address", address);
            model.addAttribute("page", pageRender);
            model.addAttribute("Usuario", usuario);
            return "ToNewOrder";
        }
        return"listar";
    }


   @RequestMapping(value={"/PrepareFieldsOrder"},method =RequestMethod.POST)
    public String PrepareFieldsOrder( @RequestParam("miFile")MultipartFile foto,@RequestParam Map<String,String> requestParams, Model model) {


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);

        String ID = cartService.findByRecentDate(usuario.getCliente());


            Card card =cartService.findByID(Integer.parseInt(ID));

       cteEnviarA addressOne =addressService.findByIDAndCliente(Integer.parseInt(requestParams.get("id")),usuario.getCliente());
       System.out.println(requestParams.get("id"));
       int step2=0;

       String oCompra = requestParams.get("oCompra");

       if(card.getEnviara() > 0 )
       {

           model.addAttribute("addressOne",addressOne);
           step2=1;
          ID = cartService.findByRecentDate(usuario.getCliente());


           if(ID != null)
           {
               List<CardD> ArticulosCard= cardService.findAllByID(Integer.parseInt(ID));
               model.addAttribute("cartProducts",ArticulosCard);
           }

       }else
       {
            card.setEnviara(addressOne.getID());
            card.setOrdencompra(requestParams.get("oCompra"));
            card.setCondicion(requestParams.get("mPago"));
            cartService.save(card);

           Card card2 =cartService.findByID(Integer.parseInt(ID));
           model.addAttribute("cart",card2);
           step2=1;
           ID = cartService.findByRecentDate(usuario.getCliente());
           model.addAttribute("addressOne",addressOne);

           if(ID != null)
           {
               List<CardD> ArticulosCard= cardService.findAllByID(Integer.parseInt(ID));
               model.addAttribute("cartProducts",ArticulosCard);
           }
       }
       if(!foto.isEmpty()){
           Path directorio = Paths.get("src//main//resources//static/img/Pagos");
           String rotPath=directorio.toFile().getAbsolutePath();
           try{
               byte[] bytes= foto.getBytes();
               Path rutacompleta= Paths.get(rotPath+"//"+usuario.getCliente()+"-"+card.getID()+"-"+".jpg");
               Files.write(rutacompleta,bytes);

           } catch(IOException e){
               e.printStackTrace();

           }



       }

       model.addAttribute("step2",step2);
       model.addAttribute("Usuario", usuario);
       return "ToNewOrder";


    }





}






