package com.senai.revisao2.controllers;

import com.senai.revisao2.dtos.UsuarioDto;
import com.senai.revisao2.services.UsuarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class PageController {

    private final UsuarioService service;

    public PageController(UsuarioService service) {
        this.service = service;
    }

    @GetMapping("/")
    public String getIndex(){
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }

    @GetMapping("/home")
    public String getHome(){
        return "home";
    }

    @GetMapping("/usuariolista")
    public String getUsuarioLista(Model model){
        List<UsuarioDto> usuarioDtoLista =  service.obterListaUsuarios();

        model.addAttribute("usuarios",usuarioDtoLista);

        return "usuariolista";
    }

    @GetMapping("/usuarioinserir")
    public String getUsuarioInserir(Model model){
        UsuarioDto dto = new UsuarioDto();
        model.addAttribute("usuario",dto);
        return "usuarioinserir";
    }

    @GetMapping("/usuarioatualizar/{id}")
    public String getAtualizarUsuario(@PathVariable("id") Long id,
                                      Model model){
        UsuarioDto dto = service.obterUsuarioPorId(id);
        model.addAttribute("usuario",dto);
        return "usuarioatualizar";
    }
}
