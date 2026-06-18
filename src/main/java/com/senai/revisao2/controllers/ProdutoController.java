package com.senai.revisao2.controllers;

import com.senai.revisao2.dtos.ProdutoDto;
import com.senai.revisao2.services.ProdutoService;
import jakarta.validation.Valid;
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
public class ProdutoController {

    private final ProdutoService service;

    public ProdutoController(ProdutoService service) {
        this.service = service;
    }

    @PostMapping("/produto")
    public String cadastrarProduto(@Valid @ModelAttribute("produto") ProdutoDto produtoDto,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "produtos/produtocadastrar";
        }

        service.cadastrarProduto(produtoDto);
        redirectAttributes.addFlashAttribute("mensagemProduto",
                "Produto salvo com sucesso!");


        return "redirect:/produtolista";
    }

    @PostMapping("/produto/{id}")
    public String atualizarProduto(@PathVariable("id") Long id, Model model,
                                   @Valid @ModelAttribute("produtoAtualizar") ProdutoDto dto,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "produtos/produtoatualizar";
        }

        try {
            service.atualizarProduto(id,dto);
            redirectAttributes.addFlashAttribute("mensagemAtualizacao", "Produto atualizado com sucesso!");
            return "redirect:/produtolista";
        } catch (RuntimeException ex) {
            model.addAttribute("NotFound",ex.getMessage());
            return "produtos/produtoatualizar";
        }

    }

    @DeleteMapping("/produto/{id}")
    public ResponseEntity<String> excluir(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        service.removerProduto(id);
        return ResponseEntity.ok().body("Excluido");
    }
}
