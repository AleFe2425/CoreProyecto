package com.example.BackendCoreMuebles.Servicio;

import com.example.BackendCoreMuebles.Modelos.Cliente;
import com.example.BackendCoreMuebles.Repositorio.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {
    @Autowired
    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    //Metodo para Obtener todos los clientes
    public List<Cliente> getAllClientes() {
        return clienteRepository.findAll();
    }

    //Metodo para Obtener Cliente por ID
    public Cliente findClienteById(int id) throws Exception {
        Cliente cliente = clienteRepository.findById(id);
        if (cliente == null) {
            throw new Exception("No se encontró cliente con el ID: " + id);
        }
        return cliente;
    }

    //Metodo para Crear Cliente
    public Cliente saveCliente(Cliente cliente, String encodedPassword) throws Exception {
        Cliente clienteEncontrado = clienteRepository.findByEmail(cliente.getEmail());
        if (clienteEncontrado != null) {
            throw new Exception("El Cliente con email " + cliente.getEmail() + " ya existe");
        }
        cliente.setPassword(encodedPassword);
        return clienteRepository.save(cliente);
    }

    //Metodo para Actualizar Cliente
    public Cliente updateCliente(int idCliente, Cliente clienteActualizado) throws Exception{
        Cliente clienteEncontrado = clienteRepository.findById(idCliente);
        if (clienteEncontrado != null){
            if (!clienteActualizado.getNombre().equals(clienteEncontrado.getNombre())){
                clienteEncontrado.setNombre(clienteActualizado.getNombre());
            }
            if (!clienteActualizado.getEmail().equals(clienteEncontrado.getEmail())){
                clienteEncontrado.setEmail(clienteActualizado.getEmail());
            }
            if (clienteActualizado.getTelefono().equals(clienteEncontrado.getTelefono())){
                clienteEncontrado.setTelefono(clienteActualizado.getTelefono());
            }
            return clienteRepository.save(clienteEncontrado);
        } else {
            throw new Exception("No existe un cliente con el ID: "+idCliente);
        }
    }

    //Metodo para Eliminar Cliente
    public boolean deleteCliente(String email) throws Exception {
        Cliente clienteEncontrado = clienteRepository.findByEmail(email);
        if (clienteEncontrado != null) {
            clienteRepository.deleteById(clienteEncontrado.getIdCliente());
            return true;
        } else {
            throw new Exception("El cliente con email: " + email + " no existe");
        }
    }
}