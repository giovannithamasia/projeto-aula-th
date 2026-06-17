package com.senai.revisao2.repositories;

import com.senai.revisao2.entities.ProdutoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProdutoRepository extends JpaRepository<ProdutoEntity,Long> {
}
