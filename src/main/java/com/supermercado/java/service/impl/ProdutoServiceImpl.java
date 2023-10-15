package com.supermercado.java.service.impl;

import com.supermercado.java.model.entity.Produto;
import com.supermercado.java.model.repository.ProdutoRepository;
import com.supermercado.java.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ProdutoServiceImpl implements ProdutoService {

    // Instanciar objeto repository para ter acesso aos métodos de persistência
    private ProdutoRepository repository;

    @Override
    @Transactional
    public Produto salvar(Produto produtoParam) {
        return repository.save(produtoParam);
    }

    @Override
    @Transactional
    public Produto atualizar(Produto produtoParam) {
        Objects.requireNonNull(produtoParam.getId());
        return repository.save(produtoParam);
    }

    @Override
    public void deletar(Produto produtoParam) {
        Objects.requireNonNull(produtoParam.getId());
        repository.delete(produtoParam);
    }

    @Override
    public List<Produto> buscar(Produto produtoParamFiltro) {
        Example<Produto> example = Example.of(produtoParamFiltro);
        return repository.findAll(example);
    }

    @Override
    @Transactional
    public Optional<Produto> consultaPorId(Long id) {
        return repository.findById(id);
    }
}
