package com.spring.data.jpa.controllers;

import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.dao.IVentaODao;
import com.spring.data.jpa.models.entity.*;
import com.spring.data.jpa.service.IAddressService;
import com.spring.data.jpa.service.ICardService;
import com.spring.data.jpa.service.ICartService;
import com.spring.data.jpa.service.IVentaDService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class OrderController {

    @Autowired
    private ICardService cardService;
    @Autowired
    private ICartService cartService;
    @Autowired
    private IUsuarioDao usuarioDao;
    @Autowired
    private IVentaODao ventaOservice;
    @Autowired
    private IVentaDService ventaDService;
    @Autowired
    private IAddressService addressService;


    @RequestMapping(value="/orderConfirmation",method = RequestMethod.GET)
    public String orderConfirm(Map<String,String> requestParams, Model model) {


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails) principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);
        String ID = cartService.findByRecentDate(usuario.getCliente());
      List<VentaD> renglones = new ArrayList<>();




            Date ahora = new Date();
            SimpleDateFormat formateador = new SimpleDateFormat("yyyy-MM-dd");//HH:mm:ss
            String fechaAlta = formateador.format(ahora);

            Card card =cartService.findByID(Integer.parseInt(ID));
            List<CardD> cards =cardService.findAllByID(Integer.parseInt(ID));
            double impuestos = 0;
            double importe = 0;
            for(int i=0;i<cards.size();i++)
            {
                if(cards.get(i).getIsaviable()==0)
                {

                }else{
                    importe += cards.get(i).getTotal().doubleValue();
                    impuestos += cards.get(i).getImpuesto1().doubleValue();
                }

            }
            card.setImporte(BigDecimal.valueOf(importe));
            card.setImpuestos(BigDecimal.valueOf(impuestos));
            card.setTotal(BigDecimal.valueOf(impuestos+importe));
            try
            {
               cartService.save(card);
            }catch (Exception e)
            {
                System.out.println("Something went wrong.1");
            }
            try
            {
                Calendar fecha = Calendar.getInstance();
                int año = fecha.get(Calendar.YEAR);
                int mes = fecha.get(Calendar.MONTH) + 1;


                VentaOrder newOrder = new VentaOrder();
                newOrder.setAgente(card.getAgente());
                newOrder.setCliente(card.getCliente());
                newOrder.setEmpresa("ACT");
                newOrder.setEnviara(card.getEnviara());
                newOrder.setFecharequerida(card.getFechaalta());
                newOrder.setOrdencompra(card.getOrdencompra());
                newOrder.setMov("Pedido B2B");
                newOrder.setMoneda("PESOS");
                newOrder.setAlmacen(1);
                newOrder.setEstatus("PENDIENTE");
                newOrder.setObservaciones("PEDIDO");
                newOrder.setUsuario("LCAMBEROS");
                newOrder.setReferencia("Pedido desde B2B");
                newOrder.setImporte(card.getImporte());
                newOrder.setImpuestos(card.getImpuestos());
                newOrder.setTipocambio(1);
                newOrder.setCondicion(card.getCondicion());
                newOrder.setOrigentipo("VTAs");
                String MovID = ventaOservice.findTheLastOne();
                System.out.println(MovID+"movID");

                newOrder.setFechaemision(fechaAlta);
                newOrder.setEjercicio(String.valueOf(año));
                newOrder.setPeriodo(String.valueOf(mes));
                newOrder.setListapreciosesp(usuario.getListapreciosesp());
                char[] aCaracteres2 = MovID.toCharArray();

                String numbers=MovID.substring(3,aCaracteres2.length);
                int nmov =Integer.parseInt(numbers);
                nmov=nmov+1;
                String letters =MovID.substring(0,3);
                System.out.println(letters+nmov);
                newOrder.setMovid(letters+nmov);
                model.addAttribute("order",newOrder);


            ventaOservice.save(newOrder);
            }catch (Exception e)
            {
                System.out.println("Something went wrong.2");
            }
            try{
                int id = ventaOservice.findTheLAstOneID();
                System.out.println(id);

                List<CardD> articulosCard= cardService.findAllByID(Integer.parseInt(ID));
                 int renglon = 0;
                 int renglonid =0;
                for(int i =0; i<articulosCard.size();i++)
                {
                    if(articulosCard.get(i).getIsaviable()!=0)
                    {
                        VentaD ventaDetail = new VentaD();
                        ventaDetail.setAlmacen(articulosCard.get(i).getAlmacen());
                        System.out.println(articulosCard.get(i).getAlmacen());
                        ventaDetail.setArticulo(articulosCard.get(i).getArticulo());
                        System.out.println(articulosCard.get(i).getArticulo());
                        ventaDetail.setRenglon(renglon);
                        System.out.println(articulosCard.get(i).getRenglon());
                        ventaDetail.setRenglonid(renglonid);
                        System.out.println(ventaDetail.getRenglonid());
                        ventaDetail.setCantidad(articulosCard.get(i).getCantidad());
                        ventaDetail.setCantidadinventario(articulosCard.get(i).getCantidad());
                        ventaDetail.setCantidadreservada(articulosCard.get(i).getCantidad());
                        ventaDetail.setRenglontipo('L');
                        int imp = 16;
                        ventaDetail.setImpuesto1(imp);
                        ventaDetail.setPrecio(BigDecimal.valueOf(articulosCard.get(i).getPrecio().doubleValue()/Double.parseDouble(String.valueOf(articulosCard.get(i).getCantidad()))));
                        ventaDetail.setPreciosugerido(BigDecimal.valueOf(articulosCard.get(i).getPrecio().doubleValue()/Double.parseDouble(String.valueOf(articulosCard.get(i).getCantidad()))));
                        ventaDetail.setPreciomoneda("PESOS");
                        ventaDetail.setUnidad("PZA");
                        ventaDetail.setPreciotipocambio(1);
                        ventaDetail.setID(id);
                        renglon+=2048;
                        renglonid++;
                        renglones.add(ventaDetail);


                        ventaDService.save(ventaDetail);

                    }
                }
System.out.println(card.getEnviara());
                cteEnviarA adres= addressService.findByIDAndCliente(card.getEnviara(),usuario.getCliente());
                System.out.println(adres.getCliente());
                model.addAttribute("adres",adres);
                model.addAttribute("orderD",renglones);
            }catch (Exception e)
            {
                System.out.println("Something went wrong.3");
            }

card.setIsactive('0');
            cartService.save(card);
      return "redirect:/history?page=0&estatus=PENDIENTE";







    }


@RequestMapping(value = "/orderDetail", method = RequestMethod.GET)
    public String orderDetail (@RequestParam Map<String,String> requestParams, Model model)
{
System.out.println(requestParams.get("movId"));
    VentaOrder ventaO = ventaOservice.findByMovidAndID(requestParams.get("movId"),Integer.parseInt(requestParams.get("id")));
    model.addAttribute("order",ventaO);
    List<VentaD> ventas = ventaDService.findByID(ventaO.getID());

    for (int i=0;i<ventas.size();i++)
    {
        BigDecimal precio =BigDecimal.valueOf(ventas.get(i).getPrecio().doubleValue()*ventas.get(i).getCantidad());
        ventas.get(i).setPrecio(precio.setScale(2, RoundingMode.DOWN));
        ventas.get(i).setImpuesto1(16);
    }
    model.addAttribute("orderD",ventas);
    cteEnviarA adres = addressService.findByIDAndCliente(ventaO.getEnviara(),ventaO.getCliente());
    model.addAttribute("adres",adres);

    return "orderDetail";
}


}
