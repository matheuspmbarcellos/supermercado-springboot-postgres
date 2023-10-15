package com.supermercado.java.api.controller;

import com.supermercado.java.api.dto.ProdutoDTO;
import com.supermercado.java.exceptions.RegraNegocioException;
import com.supermercado.java.model.entity.Produto;
import com.supermercado.java.service.ProdutoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/produtos")
public class ProdutoController {

    private ProdutoService service;

    public Produto converter(ProdutoDTO dto) {
        Produto produto = new Produto();

        produto.setNome(dto.getNome());
        produto.setQuantidade(dto.getQuantidade());
        produto.setValor(dto.getValor());

        if(dto.getId() != null) {
            produto.setId(dto.getId());
        }
        return produto;
    }
    @PostMapping("/salvar")
    public ResponseEntity salvar(@RequestBody ProdutoDTO dto) {
        try {
            Produto entidadeProduto = service.salvar(converter(dto));
            return ResponseEntity.ok(entidadeProduto);
        } catch (RegraNegocioException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
