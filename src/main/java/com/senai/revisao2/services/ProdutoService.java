package com.senai.revisao2.services;

import com.senai.revisao2.dtos.ProdutoDto;
import com.senai.revisao2.entities.ProdutoEntity;
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

    public void cadastrarProduto(ProdutoDto produtoDto){
        repository.save(produtoDto.toEntity());
    }

    public ProdutoDto obterProdutoPorId(Long idProduto){
        ProdutoEntity produtoEntity = repository.findById(idProduto)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        return ProdutoDto.toDto(produtoEntity);
    }

    public void atualizarProduto(Long id,ProdutoDto produtoDto){
        ProdutoEntity produtoEntity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Produto não encontrado"));

        produtoEntity.setNome(produtoDto.nome());
        produtoEntity.setDescricao(produtoDto.descricao());
        produtoEntity.setPreco(produtoDto.preco());
        produtoEntity.setQuantidadeEstoque(produtoDto.quantidadeEstoque());

        repository.save(produtoEntity);
    }

    public void removerProduto(Long id){
        repository.deleteById(id);
    }
}
