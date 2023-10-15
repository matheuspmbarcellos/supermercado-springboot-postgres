package com.supermercado.java.service;

import com.supermercado.java.model.entity.Produto;

import java.util.List;
import java.util.Optional;

public interface ProdutoService {

    // CRUD

    Produto salvar(Produto produtoParam);
    Produto atualizar(Produto produtoParam);
    void deletar(Produto produtoParam);
    List<Produto> buscar(Produto produto);
    Optional<Produto> consultaPorId(Long id);

}
