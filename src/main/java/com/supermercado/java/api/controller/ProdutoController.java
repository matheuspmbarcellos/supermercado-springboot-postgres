package com.supermercado.java.api.controller;

import com.supermercado.java.api.dto.ProdutoDTO;
import com.supermercado.java.exceptions.RegraNegocioException;
import com.supermercado.java.model.entity.Produto;
import com.supermercado.java.service.ProdutoService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/atualizar/{id}")
    public ResponseEntity atualizar(@PathVariable("id") Long id, @RequestBody ProdutoDTO dto) {
        return service.consultaPorId(id).map(entity -> {
            try {
                Produto produto = converter(dto);
                produto.setId(entity.getId());
                service.atualizar(produto);
                return ResponseEntity.ok(produto);
            } catch (RegraNegocioException e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }
        }).orElseGet(() -> ResponseEntity.badRequest().body("O id do produto informado não foi encontrado na base de dados"));
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity deletar(@PathVariable("id") Long id) {
        return service.consultaPorId(id).map(entity -> {
            service.deletar(entity);
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }).orElseGet(() -> ResponseEntity.badRequest().body("O id do produto informado não foi encontrado na base de dados"));
    }

    @GetMapping("/buscar")
    public ResponseEntity buscar(
            @RequestParam(value = "nome", required = false) String nome,
            @RequestParam(value = "quantidade", required = false) Integer quantidade,
            @RequestParam(value = "valor", required = false) Long valor
    ) {
        Produto produtoFiltro = new Produto();
        produtoFiltro.setNome(nome);
        produtoFiltro.setQuantidade(quantidade);
        produtoFiltro.setValor(valor);

        List<Produto> produtos = service.buscar(produtoFiltro);
        return ResponseEntity.ok(produtos);
    }
}
