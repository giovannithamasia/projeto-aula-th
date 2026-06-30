package com.senai.revisao2.controllers;

import com.senai.revisao2.dtos.*;
import com.senai.revisao2.services.UsuarioService;
import com.senai.revisao2.sessoes.SessaoDto;
import com.senai.revisao2.sessoes.SessaoUtil;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
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
                                Model model, RedirectAttributes redirectAttributes,
                                HttpSession session){

        UsuarioDto usuarioDto = usuarioService.realizarLogin(email,senha);

        if (usuarioDto ==null) {
            model.addAttribute("erro","E-mail ou senha invalidos.");
            return "login";
        }

        SessaoDto sessaoDto = new SessaoDto();
        sessaoDto.setUsuarioId(usuarioDto.getId());
        sessaoDto.setUsuarioNome(usuarioDto.getNome());
        SessaoUtil.RegistrarSessao(session, sessaoDto);

        return "redirect:/home";

    }

    @PostMapping("/usuarioinserir")
    public String inserirUsuario(@Valid @ModelAttribute("usuario") UsuarioDto usuarioDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes,
                                 HttpSession session){

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);
        if (sessaoDto == null){
            return "redirect:/login";
        }

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
                                   RedirectAttributes redirectAttributes,
                                   HttpSession session){

        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);
        if (sessaoDto == null){
            return "redirect:/login";
        }

        if (bindingResult.hasErrors()){
            return "usuarioatualizar";
        }

        usuarioService.atualizarUsuario(dto);
        redirectAttributes.addFlashAttribute("mensagemAtualizacao", "Usuario atualizado com sucesso!");

        return "redirect:/usuariolista";
    }

    @DeleteMapping("/usuarioexcluir/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id, RedirectAttributes redirectAttributes,HttpSession session) {
        SessaoDto sessaoDto = SessaoUtil.ObterSessao(session);
        if (sessaoDto == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Acesso negado: faça login");
        }

        usuarioService.excluir(id);
        return ResponseEntity.ok().body("Excluido");
    }

}
