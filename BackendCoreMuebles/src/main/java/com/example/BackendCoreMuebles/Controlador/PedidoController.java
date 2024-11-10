package com.example.BackendCoreMuebles.Controlador;

import com.example.BackendCoreMuebles.Modelos.Pedido;
import com.example.BackendCoreMuebles.Servicio.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedido")
public class PedidoController {
    @Autowired
    private final PedidoService pedidoService;

    public PedidoController(PedidoService pedidoService) {
        this.pedidoService = pedidoService;
    }

    @GetMapping("/info")
    public String index(){
        return "Conectado a la tabla Pedido";
    }

    @GetMapping()
    public List<Pedido> getAllPedidos() {
        return pedidoService.getAllPedidos();
    }

    @GetMapping("/{idPedido}")
    public ResponseEntity<Pedido> getPedidoById(@PathVariable int idPedido) {
        try {
            return ResponseEntity.ok(pedidoService.findPedidoById(idPedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping
    public ResponseEntity<Pedido> createPedido(@RequestBody Pedido pedido) {
        try {
            System.out.println("Pedido recibido: " + pedido);
            return ResponseEntity.ok(pedidoService.savePedido(pedido));
        } catch (Exception e) {
            e.printStackTrace(); // Imprime el stack trace para más detalles
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/{idPedido}")
    public ResponseEntity<Pedido> updatePedido(@PathVariable int idPedido, @RequestBody Pedido pedidoActualizado) {
        try {
            return ResponseEntity.ok(pedidoService.updatePedido(idPedido, pedidoActualizado));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @DeleteMapping("/{idPedido}")
    public ResponseEntity<Boolean> deletePedido(@PathVariable int idPedido) {
        try {
            return ResponseEntity.ok(pedidoService.deletePedido(idPedido));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

}
