package com.senai.revisao2.controllers;

import com.senai.revisao2.dtos.*;
import com.senai.revisao2.services.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping("/login")
    public String realizarLogin(String email, String senha,
                                Model model, RedirectAttributes redirectAttributes){

        System.out.println("email=" + email + " senha=" + senha);

        UsuarioDto usuarioDto = new UsuarioDto();
        usuarioDto.setEmail(email);
        usuarioDto.setSenha(senha);

        UsuarioDto usuarioDtoRetorno = usuarioService.realizarLogin(usuarioDto);

        if (usuarioDtoRetorno.getNome() !=null) {

            redirectAttributes.addFlashAttribute("usuario", " Bem-vindo, " + usuarioDtoRetorno.getNome());

            return "redirect:/home";
        }

        model.addAttribute("erro","E-mail ou senha invalidos.");

        return "login";

    }

    @PostMapping("/usuarioinserir")
    public String inserirUsuario(@Valid @ModelAttribute("usuario") UsuarioDto usuarioDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "usuarioinserir";
        }

        usuarioService.inserirUsuario(usuarioDto);
        redirectAttributes.addFlashAttribute("mensagem", "Usuario cadastrado com sucesso");

        return "redirect:/usuariolista";
    }

    @PostMapping("/usuarioatualizar")
    public String atualizarUsuario(Model model,
                                   @Valid @ModelAttribute("usuario") UsuarioDto dto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "usuarioatualizar";
        }

        usuarioService.atualizarUsuario(dto);
        redirectAttributes.addFlashAttribute("mensagemAtualizacao", "Usuario atualizado com sucesso");

        return "redirect:/usuariolista";
    }

}
