package com.example.demo.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import com.example.demo.model.Pedido;

import org.springframework.stereotype.Component;

//Indica para o Spring que deve construir e gerenciar o objeto
@Component
public class PedidoRepository {
    
    private List<Pedido> pedidos;
    private int nextCod;

    //Psiu, depois que você construir, faça isto aqui pra mim
    @PostConstruct
    public void init() {
        Pedido p1 = new Pedido();
        p1.setCodigo(1);
        p1.setCliente("Vitor");
        p1.setDataPedido("25/09/2020");
        p1.setDescricao("Portuguesa");
        p1.setValor(39.9);

        Pedido p2 = new Pedido();
        p2.setCodigo(2);
        p2.setCliente("Mileide");
        p2.setDataPedido("26/09/2020");
        p2.setDescricao("Franbacon");
        p2.setValor(35.9);

        Pedido p3 = new Pedido();
        p3.setCodigo(3);
        p3.setCliente("Natan");
        p3.setDataPedido("24/09/2020");
        p3.setDescricao("Boliviana");
        p3.setValor(43.9);

        //Usar ArrayList quando não tiver uma quantidade fixa
        pedidos = new ArrayList<Pedido>();
        pedidos.add(p1);
        pedidos.add(p2);
        pedidos.add(p3);
        nextCod = 4;
    }

    public Pedido save(Pedido pedido) {
        pedido.setCodigo(nextCod++);
        pedidos.add(pedido);

        return pedido;
	}  

    public List<Pedido> getAllPedidos() {
        return pedidos;
    }

    public Pedido getPedidoByCod(int codigo) {
        for (Pedido aux : pedidos) {
            if (codigo == aux.getCodigo()) {
                return aux;
            }
        }
        return null;
    }

	public void remove(Pedido pedido) {
        pedidos.remove(pedido);
	}

	public Pedido modify(Pedido pedido) {
        Pedido aux = getPedidoByCod(pedido.getCodigo());

        if (aux != null) {
            aux.setCliente(pedido.getCliente());
            aux.setDescricao(pedido.getDescricao());
            aux.setValor(pedido.getValor());
        } 

        return aux;
	}

}
