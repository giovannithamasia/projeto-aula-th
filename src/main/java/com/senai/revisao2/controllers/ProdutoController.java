package com.senai.revisao2.controllers;

import com.senai.revisao2.dtos.ProdutoDto;
import com.senai.revisao2.dtos.UsuarioDto;
import com.senai.revisao2.services.ProdutoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/produto")
    public String inserirProduto(@Valid @ModelAttribute("produto") ProdutoDto produtoDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "produtos/produtocadastrar";
        }

        service.inserirProduto(produtoDto);
        redirectAttributes.addFlashAttribute("mensagemProduto",
                "Produto salvo com sucesso!");


        return "redirect:/produtolista";
    }
}
