package com.spring.data.jpa.controllers;
import com.spring.data.jpa.Paginator.PageRender;
import com.spring.data.jpa.models.dao.IUsuarioDao;
import com.spring.data.jpa.models.entity.Usuario;
import com.spring.data.jpa.models.entity.Venta;
import com.spring.data.jpa.models.entity.cteEnviarA;
import com.spring.data.jpa.models.entity.vw_b2barticulos_row;
import com.spring.data.jpa.service.IAddressService;
import com.spring.data.jpa.service.IVentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Null;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Controller
public class UserController {


    @Autowired
    private IUsuarioDao usuarioDao;
    @Autowired
    private IVentaService ventaService;
    @Autowired
    private IAddressService addressService;



    @RequestMapping(value="/history",method = RequestMethod.GET)
    public String listar(@RequestParam Map<String,String> requestParams, Model model){





        int page=Integer.parseInt(requestParams.get("page"));



        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();






        if (principal instanceof UserDetails) {
            String username = ((UserDetails)principal).getUsername();
            Usuario usuario = usuarioDao.findByCliente(username);
            Pageable pageRequest= PageRequest.of(page,10,Sort.by("fechaEmision").descending());
            Page<Venta> ventas = ventaService.findAllByClienteAndEstatus(usuario.getCliente(),requestParams.get("estatus"),pageRequest);



            PageRender<Venta> pageRender = new PageRender<>("/history?estatus="+requestParams.get("estatus"),ventas);

            model.addAttribute("Ventas", ventas);
            model.addAttribute("page", pageRender);
            model.addAttribute("Usuario", usuario);
            return "history";

        } else {
            String username = principal.toString();
        }
            return"redirect:/login";

    }


    @RequestMapping(value={"/address"},method =RequestMethod.GET)
  public String address(@RequestParam Map<String,String> requestParams, Model model) {


        int page;
 if(requestParams.get("page")==null){
     page=0;
 }else{
      page=Integer.parseInt(requestParams.get("page"));
 }


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof UserDetails) {
            Pageable pageRequest= PageRequest.of(page,6);
            String username = ((UserDetails)principal).getUsername();
            Usuario usuario = usuarioDao.findByCliente(username);

            Page<cteEnviarA> address=addressService.findAllByCliente(usuario.getCliente(),pageRequest);

            PageRender<cteEnviarA> pageRender = new PageRender<>("/address",address);

            model.addAttribute("address", address);
            model.addAttribute("page", pageRender);
            model.addAttribute("Usuario", usuario);
            return "address";
        }
       return"listar";
    }

    @RequestMapping(value="/form/{ID}",method =RequestMethod.GET)
    public String addressEdit(@PathVariable(value="ID") int ID,Model model) {


        System.out.println("si edit");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ID>=0) {
            if (principal instanceof UserDetails) {

                String username = ((UserDetails) principal).getUsername();
                Usuario usuario = usuarioDao.findByCliente(username);
                cteEnviarA addressEdit = addressService.findByIDAndCliente(ID, usuario.getCliente());
                model.addAttribute("addressEdit", addressEdit);
                return "forward:/address";
            }

            return "address";

        }
        return "forward:/address";

    }

    @RequestMapping(value="/change/{ID}",method =RequestMethod.GET)
    public String Change(@PathVariable(value="ID") int ID) {


        System.out.println("si edit");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ID>=0) {
            if (principal instanceof UserDetails) {

                String username = ((UserDetails) principal).getUsername();
                Usuario usuario = usuarioDao.findByCliente(username);
                cteEnviarA addressEdit = addressService.findByIDAndCliente(ID, usuario.getCliente());
               addressEdit.setEstatus(!addressEdit.isEstatus());
                addressService.save(addressEdit);


                return "redirect:/address";
            }

            return "address";

        }
        return "redirect:/address";

    }

    @RequestMapping(value="/form",method =RequestMethod.GET)
    public String addAddress(Model model) {


        System.out.println("si add");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {

                String username = ((UserDetails) principal).getUsername();
                Usuario usuario = usuarioDao.findByCliente(username);
                model.addAttribute("Usuario", usuario);
                return "addAddress";
            }
        return "login";

    }

    @RequestMapping(value="/addAddress",method =RequestMethod.POST)
    public String addressUpdate( @RequestParam("fotoSuc")MultipartFile foto,@RequestParam Map<String,String> requestParams) {


        System.out.println("si add or update");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = ((UserDetails)principal).getUsername();
        Usuario usuario = usuarioDao.findByCliente(username);

    int id=Integer.parseInt(requestParams.get("ID"));

        if(id<=0){

            cteEnviarA sucursal = new cteEnviarA();
            Random r = new Random();
            int ID  = r.nextInt(1000)+1;
            System.out.println(ID);
       boolean existe = false;

            while(existe==false)
            {

                cteEnviarA sucursalFind =addressService.findByIDAndCliente(ID,usuario.getCliente());
                 if(sucursalFind!= null){

                     ID = (int) Math.floor(Math.random()*1000+1);
                     System.out.println(ID+"si habia ");
                 }
                 else
                 {











                     System.out.println(ID+"noHabiaPero se Creo");
                     sucursal.setID(ID);
                     sucursal.setCliente(usuario.getCliente());
                     sucursal.setNombre(requestParams.get("Nombre"));
                     sucursal.setColonia(requestParams.get("Colonia"));
                     sucursal.setDireccion(requestParams.get("direccion"));
                     sucursal.setEntreCalles(requestParams.get("entreCalles"));
                     sucursal.setDireccionNumero(requestParams.get("exterior"));
                     sucursal.setDireccionNumeroInt(requestParams.get("interior"));
                     sucursal.setPais("MX");
                     sucursal.setCodigoPostal(requestParams.get("codigoPostal"));
                     sucursal.setEstado(requestParams.get("Estado"));
                     sucursal.setPoblacion(requestParams.get("Poblacion"));
                     sucursal.setEstatus(true);
                     addressService.save(sucursal);


                     if(!foto.isEmpty()){
                         Path directorio = Paths.get("src//main//resources//static/img/sucursales");
                         String rotPath=directorio.toFile().getAbsolutePath();
                         try{
                             byte[] bytes= foto.getBytes();
                             Path rutacompleta= Paths.get(rotPath+"//"+usuario.getCliente()+"-"+sucursal.getID()+".png");
                             Files.write(rutacompleta,bytes);
                             existe = true;
                             return "redirect:/address";
                         } catch(IOException e){
                             e.printStackTrace();

                         }



                     }


                     existe = true;
                 }
            }




        }else{



            int ID = Integer.parseInt(requestParams.get("ID"));
            cteEnviarA sucursal =addressService.findByIDAndCliente(ID,usuario.getCliente());
            sucursal.setNombre(requestParams.get("Nombre"));
            sucursal.setColonia(requestParams.get("Colonia"));
            sucursal.setDireccion(requestParams.get("direccion"));
            sucursal.setEntreCalles(requestParams.get("entreCalles"));
            sucursal.setDireccionNumero(requestParams.get("exterior"));
            sucursal.setDireccionNumeroInt(requestParams.get("interior"));
            sucursal.setPais("MX");
            addressService.save(sucursal);
            if(!foto.isEmpty()){
                Path directorio = Paths.get("src//main//resources//static/img/sucursales");
                String rotPath=directorio.toFile().getAbsolutePath();
                try{
                    byte[] bytes= foto.getBytes();
                    Path rutacompleta= Paths.get(rotPath+"//"+usuario.getCliente()+"-"+sucursal.getID()+".png");
                    Files.write(rutacompleta,bytes);
                    return "redirect:/address";
                } catch(IOException e){
                    e.printStackTrace();

                }



            }

        }

        return "redirect:/address";

    }

    @RequestMapping(value = "/logout")
    public String logout(){
        SecurityContextHolder.getContext().getAuthentication().setAuthenticated(false);
        return "redirect:/login";
    }

}
