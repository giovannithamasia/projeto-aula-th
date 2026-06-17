package com.senai.revisao2.services;

import com.senai.revisao2.dtos.ProdutoDto;
import com.senai.revisao2.repositories.ProdutoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public ProdutoService(ProdutoRepository repository) {
        this.repository = repository;
    }

    public List<ProdutoDto> listarProdutos(){
        return repository.findAll()
                .stream()
                .map(ProdutoDto::toDto)
                .toList();
    }

    public void inserirProduto(ProdutoDto produtoDto){
        repository.save(produtoDto.toEntity());
    }


}
