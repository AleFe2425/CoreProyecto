package com.example.BackendCoreMuebles.Servicio;

import com.example.BackendCoreMuebles.Modelos.Pedido;
import com.example.BackendCoreMuebles.Modelos.DetallePedido;
import com.example.BackendCoreMuebles.Repositorio.PedidoRepository;
import com.example.BackendCoreMuebles.Repositorio.DetallePedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class PedidoService {
    @Autowired
    private final PedidoRepository pedidoRepository;

    @Autowired
    private final DetallePedidoService detallePedidoService;
    @Autowired
    private final DetallePedidoRepository detallePedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository, DetallePedidoRepository detallePedidoRepository, DetallePedidoService detallePedidoService, DetallePedidoRepository detallePedidoRepository1) {
        this.pedidoRepository = pedidoRepository;
        this.detallePedidoService = detallePedidoService;
        this.detallePedidoRepository = detallePedidoRepository1;
    }

    //Metodo para Obtener todos los pedidos
    public List<Pedido> getAllPedidos() {
        return pedidoRepository.findAll();
    }

    //Metodo para Obtener pedido por ID, incluyendo los detalles
    public Pedido findPedidoById(int id) throws Exception {
        Pedido pedido = pedidoRepository.findById(id);
        if (pedido == null) {
            throw new Exception("No se encontró pedido con el ID: " + id);
        }
        pedido.setDetallesPedido(detallePedidoRepository.findByPedido(pedido)); // Asignar detalles al pedido
        return pedido;
    }

    //Metodo para Crear Pedido
    public Pedido savePedido(Pedido pedido) throws Exception {
        double precioTotal = 0;
        double tiempoTotal = 0;
        for (DetallePedido detalle : pedido.getDetallesPedido()) {
            if (detalle.getCantidad()<=0){
                throw new Exception("La cantidad del mueble es incorrecta");
            }
            detalle.setTiempoEstimado(detalle.getCantidad()*(detalle.getMueble().getTiempoBaseProduccion() * detalle.getMaterial().getFactorTiempo()));
            detalle.setPrecioUnitario(detalle.getMueble().getPrecioMueble() * detalle.getMaterial().getPrecioMaterial());
            if (detalle.getTiempoEstimado()<=0){
                throw new Exception("El tiempo estimado no puede ser menor o igual a 0");
            }
            if (detalle.getPrecioUnitario()<=0){
                throw new Exception("El tiempo estimado no puede ser menor o igual a 0");
            }
            detalle.setPrecioSubtotal(detalle.getPrecioUnitario()*detalle.getCantidad());

            precioTotal += detalle.getPrecioSubtotal();
            tiempoTotal += detalle.getTiempoEstimado();
        }

        pedido.setPrecioTotal(precioTotal);
        pedido.setTiempoTotalEstimado(tiempoTotal);
        pedido.setEstado("Proceso");
        Pedido savedPedido = pedidoRepository.save(pedido);

        List<DetallePedido> pedidos = new ArrayList<>();
        for (DetallePedido detalles: pedido.getDetallesPedido()){
            detalles.setPedido(savedPedido);
            pedidos.add(detalles);
        }

        detallePedidoService.saveDetalles(pedidos);
        return savedPedido;
    }

    //Metodo para Actualizar Pedido
    public Pedido updatePedido(int idPedido, Pedido pedidoActualizado) throws Exception{
        Pedido pedidoEncontrado = pedidoRepository.findById(idPedido);
        if (pedidoEncontrado != null){
            pedidoEncontrado.setTiempoTotalEstimado(pedidoActualizado.getTiempoTotalEstimado());
            pedidoEncontrado.setEstado(pedidoActualizado.getEstado());
            return pedidoRepository.save(pedidoEncontrado);
        }else {
            throw new Exception("El pedido con el ID:"+idPedido);
        }
    }

    //Metodo para Eliminar pedido y sus detalles
    public boolean deletePedido(int idPedido) throws Exception {
        Pedido pedido = findPedidoById(idPedido);
        if (pedido != null) {
            List<DetallePedido> detalles = detallePedidoRepository.findByPedido(pedido);
            detallePedidoRepository.deleteAll(detalles);
            pedidoRepository.deleteById(idPedido);
            return true;
        } else {
            throw new Exception("El pedido con ID: " + idPedido + " no existe");
        }
    }
}