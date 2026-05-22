package sv.edu.udb.transportes.modelo;

import java.io.Serializable;

/**
 * Clase POJO (Plain Old Java Object) que representa un tipo de vehículo en el sistema.
 * Implementa Serializable para permitir su almacenamiento en sesión HTTP.
 *
 * Esta clase mapea directamente la tabla 'tipo_vehiculo' de la base de datos.
 * Los tipos disponibles son: "Moto" y "Camion".
 */
public class TipoVehiculo implements Serializable {

    private int idTipo;        // Identificador único del tipo de vehículo (1: Moto, 2: Camion)
    private String nombreTipo; // Nombre descriptivo del tipo ("Moto" o "Camion")

    /**
     * Constructor vacío (requerido por JSP/EL y frameworks).
     * Necesario para que JSTL pueda instanciar objetos de esta clase.
     */
    public TipoVehiculo() {}

    /**
     * Constructor completo para crear un tipo de vehículo con sus datos.
     *
     * @param idTipo     Identificador del tipo (1 o 2)
     * @param nombreTipo Nombre del tipo ("Moto" o "Camion")
     */
    public TipoVehiculo(int idTipo, String nombreTipo) {
        this.idTipo = idTipo;
        this.nombreTipo = nombreTipo;
    }

    // GETTERS Y SETTERS
    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getNombreTipo() { return nombreTipo; }
    public void setNombreTipo(String nombreTipo) { this.nombreTipo = nombreTipo; }
}
