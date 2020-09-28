package com.example.demo.controller;

import java.net.URI;
import java.util.List;

import com.example.demo.model.Pedido;
import com.example.demo.repository.PedidoRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//Indica ao Spring que é um Controlador
@RestController
@RequestMapping("/pedidos")
public class PedidoController {   

    //Psiu, coloca o objeto aqui pra mim
    @Autowired
    private PedidoRepository repository;    //cria um atributo que armazenará um objeto do tipo PedidoRepository

    //Método responsável por fazer o cadastro um pedido
    @PostMapping("/")
    public ResponseEntity<Void> save(@RequestBody Pedido pedido) {    //Void pq é um post não precisa retornar nada, além dos status http
        Pedido ped = repository.save(pedido);

        URI uri = URI.create("http://localhost:8080/clientes/" + ped.getCodigo());

        return ResponseEntity.created(uri).build(); //201 http status
    }

    //Método responsável por mostrar todos os pedidos
    @GetMapping("/")
    public List<Pedido> getPedidos() {
        return repository.getAllPedidos();
    }

    //Método responsável por mostrar determinada pedido
    @GetMapping("/{codigo}")
    public ResponseEntity<Pedido> getPedidoByCodigo(@PathVariable int codigo) {
        Pedido ped = repository.getPedidoByCod(codigo);

        //Existe um pedido com o id passado?
        if(ped != null) {
            return ResponseEntity.ok(ped);  //200 http status
        } else {
            return ResponseEntity.notFound().build();   //404 http status
        }
    }

    //Método responsável por deletar um pedido
    @DeleteMapping("/{codigo}")
    public ResponseEntity<Void> removePedido(@PathVariable int codigo) {
        Pedido ped = repository.getPedidoByCod(codigo);

        //Existe um pedido com o id passado?
        if(ped != null) {
            repository.remove(ped);
            return ResponseEntity.noContent().build();   //204 http status
        } else {
            return ResponseEntity.notFound().build();   //404 http status
        }
    }

    //Método responsável por alterar um pedido
    @PutMapping("/{codigo}")
    public ResponseEntity<Pedido> modifyPedido(@PathVariable int codigo, @RequestBody Pedido pedido) {
        //Existe um pedido com o id passado?
        if (repository.getPedidoByCod(codigo) != null) {
            pedido.setCodigo(codigo);
            pedido = repository.modify(pedido);
            return ResponseEntity.ok(pedido);   //200 http status
        } else {    
            return ResponseEntity.notFound().build();   //404 http status
        }
    }

}
