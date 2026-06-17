package com.senai.revisao2.controllers;

import com.senai.revisao2.dtos.ProdutoDto;
import com.senai.revisao2.services.ProdutoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ProdutoPageController {

    private final ProdutoService service;

    public ProdutoPageController(ProdutoService service) {
        this.service = service;
    }

    @GetMapping("/produtolista")
    public String getProdutoLista(Model model){
        List<ProdutoDto> listaProdutos = service.listarProdutos();

        model.addAttribute("listaProdutos",listaProdutos);

        return "produtos/produtolista";
    }
}
