package sv.edu.udb.transportes.modelo;

import java.io.Serializable;

/**
 * Clase POJO (Plain Old Java Object) que representa un vehículo en el sistema.
 * Implementa Serializable para permitir su almacenamiento en sesión HTTP.
 *
 * Esta clase mapea directamente la tabla 'vehiculo' de la base de datos.
 * Admite dos tipos de vehículos: Moto (con cilindraje) y Camión (con capacidad de carga).
 */
public class Vehiculo implements Serializable {

    // Atributos privados (encapsulamiento)
    private int idVehiculo;              // Identificador único del vehículo (clave primaria)
    private int idTipo;                  // Referencia al tipo de vehículo (1: Moto, 2: Camion)
    private String marca;               // Marca del vehículo
    private String modelo;              // Modelo del vehículo
    private int anio;                   // Año de fabricación
    private double datoEspecifico;      // CC para moto, toneladas para camión
    private String estadoMantenimiento; // "Al día" o "Requiere revisión"
    private String nombreTipo;          // Auxiliar para vista (no se guarda en BD)

    /**
     * Constructor vacío (requerido por JSP/EL y frameworks).
     * Necesario para que JSTL pueda instanciar objetos de esta clase.
     */
    public Vehiculo() {}

    /**
     * Constructor completo para crear un vehículo con todos sus datos.
     *
     * @param idVehiculo          Identificador único del vehículo
     * @param idTipo              Tipo de vehículo (1: Moto, 2: Camion)
     * @param marca               Marca del vehículo
     * @param modelo              Modelo del vehículo
     * @param anio                Año de fabricación
     * @param datoEspecifico      Cilindraje (CC) o capacidad de carga (toneladas)
     * @param estadoMantenimiento Estado del mantenimiento del vehículo
     */
    public Vehiculo(int idVehiculo, int idTipo, String marca, String modelo, int anio,
                    double datoEspecifico, String estadoMantenimiento) {
        this.idVehiculo = idVehiculo;
        this.idTipo = idTipo;
        this.marca = marca;
        this.modelo = modelo;
        this.anio = anio;
        this.datoEspecifico = datoEspecifico;
        this.estadoMantenimiento = estadoMantenimiento;
    }

    // GETTERS Y SETTERS
    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public int getIdTipo() { return idTipo; }
    public void setIdTipo(int idTipo) { this.idTipo = idTipo; }

    public String getMarca() { return marca; }
    public void setMarca(String marca) { this.marca = marca; }

    public String getModelo() { return modelo; }
    public void setModelo(String modelo) { this.modelo = modelo; }

    public int getAnio() { return anio; }
    public void setAnio(int anio) { this.anio = anio; }

    public double getDatoEspecifico() { return datoEspecifico; }
    public void setDatoEspecifico(double datoEspecifico) { this.datoEspecifico = datoEspecifico; }

    public String getEstadoMantenimiento() { return estadoMantenimiento; }
    public void setEstadoMantenimiento(String estadoMantenimiento) { this.estadoMantenimiento = estadoMantenimiento; }

    public String getNombreTipo() { return nombreTipo; }
    public void setNombreTipo(String nombreTipo) { this.nombreTipo = nombreTipo; }

    /**
     * Método auxiliar para mostrar el dato específico formateado según el tipo de vehículo.
     * Para motos: muestra el cilindraje seguido de " CC"
     * Para camiones: muestra la capacidad seguida de " ton" con un decimal
     *
     * @return Cadena formateada del dato específico (ej: "250 CC" o "5.5 ton")
     */
    public String getDatoEspecificoFormateado() {
        if (nombreTipo != null && nombreTipo.equalsIgnoreCase("Moto")) {
            return (int) datoEspecifico + " CC";
        } else {
            return String.format("%.1f ton", datoEspecifico);
        }
    }
}
