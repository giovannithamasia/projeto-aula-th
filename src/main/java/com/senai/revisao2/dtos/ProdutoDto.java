package com.senai.revisao2.dtos;

import com.senai.revisao2.entities.ProdutoEntity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public record ProdutoDto (

     Long idProduto,

    @NotBlank
    @Size(min = 2, max = 100, message = "O nome deve ter entre dois e cem caracteres")
    String nome,

    @Size(max = 255, message = "A descrição só pode ter 255 caracteres")
    String descricao,

    @Positive
    @NotNull
     BigDecimal preco,

    @Positive
    @NotNull
    Integer quantidadeEstoque

){

    public ProdutoEntity toEntity(){
        ProdutoEntity produtoEntity = new ProdutoEntity();

        produtoEntity.setIdProduto(this.idProduto);
        produtoEntity.setNome(this.nome);
        produtoEntity.setDescricao(this.descricao);
        produtoEntity.setPreco(this.preco);
        produtoEntity.setQuantidadeEstoque(this.quantidadeEstoque);

        return produtoEntity;
    }

    public static ProdutoDto toDto(ProdutoEntity produtoEntity){
        return new ProdutoDto(produtoEntity.getIdProduto(),produtoEntity.getNome(),
                produtoEntity.getDescricao(),produtoEntity.getPreco(),produtoEntity.getQuantidadeEstoque());
    }
}
