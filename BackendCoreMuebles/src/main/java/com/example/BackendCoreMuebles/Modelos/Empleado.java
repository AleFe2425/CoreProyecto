package com.example.BackendCoreMuebles.Modelos;

import jakarta.persistence.*;

@Entity
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idEmpleado;

    @Column(name = "Nombre")
    private String nombre;

    @Column(name = "Pedidos Asignados")
    private int pedidosAsignados;

    @Column(name = "Asistencia Laboral")
    private boolean asistencia;

    public int getIdEmpleado() {
        return idEmpleado;
    }

    public void setIdEmpleado(int idEmpleado) {
        this.idEmpleado = idEmpleado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getPedidosAsignados() {
        return pedidosAsignados;
    }

    public void setPedidosAsignados(int pedidosAsignados) {
        this.pedidosAsignados = pedidosAsignados;
    }

    public boolean isAsistencia() {
        return asistencia;
    }

    public void setAsistencia(boolean asistencia) {
        this.asistencia = asistencia;
    }
}
