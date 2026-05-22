package sv.edu.udb.transportes.modelo;

import java.io.Serializable;

/**
 * Clase POJO (Plain Old Java Object) que representa un viaje realizado en el sistema.
 * Implementa Serializable para permitir su almacenamiento en sesión HTTP.
 *
 * Esta clase mapea directamente la tabla 'viaje' de la base de datos.
 * Cada instancia corresponde a un viaje registrado, asociando un conductor y un vehículo.
 */
public class Viaje implements Serializable {

    // Atributos persistentes (se guardan en la base de datos)
    private int idViaje;           // Identificador único del viaje (clave primaria)
    private String duiConductor;   // DUI del conductor que realizó el viaje (clave foránea)
    private int idVehiculo;        // ID del vehículo utilizado en el viaje (clave foránea)
    private double distanciaKm;    // Distancia recorrida en kilómetros
    private double costo;          // Costo total del viaje (distancia * tarifa)
    private String fechaViaje;     // Fecha del viaje (formato yyyy-mm-dd)

    // Campos auxiliares (no se guardan en la base de datos)
    // Se utilizan para mostrar información combinada en las vistas JSP
    private String nombreConductor;        // Nombre completo del conductor (desde JOIN)
    private String descripcionVehiculo;    // "Marca Modelo (Tipo)" (desde JOIN)
    private String tipoVehiculo;           // "Moto" o "Camion" (desde JOIN)

    /**
     * Constructor vacío (requerido por JSP/EL y frameworks).
     * Necesario para que JSTL pueda instanciar objetos de esta clase.
     */
    public Viaje() {}

    /**
     * Constructor completo para crear un viaje con sus datos persistentes.
     *
     * @param idViaje       Identificador único del viaje
     * @param duiConductor  DUI del conductor
     * @param idVehiculo    ID del vehículo utilizado
     * @param distanciaKm   Distancia recorrida en kilómetros
     * @param costo         Costo total del viaje
     * @param fechaViaje    Fecha del viaje (yyyy-mm-dd)
     */
    public Viaje(int idViaje, String duiConductor, int idVehiculo, double distanciaKm,
                 double costo, String fechaViaje) {
        this.idViaje = idViaje;
        this.duiConductor = duiConductor;
        this.idVehiculo = idVehiculo;
        this.distanciaKm = distanciaKm;
        this.costo = costo;
        this.fechaViaje = fechaViaje;
    }

    // GETTERS Y SETTERS

    // Atributos persistentes
    public int getIdViaje() { return idViaje; }
    public void setIdViaje(int idViaje) { this.idViaje = idViaje; }

    public String getDuiConductor() { return duiConductor; }
    public void setDuiConductor(String duiConductor) { this.duiConductor = duiConductor; }

    public int getIdVehiculo() { return idVehiculo; }
    public void setIdVehiculo(int idVehiculo) { this.idVehiculo = idVehiculo; }

    public double getDistanciaKm() { return distanciaKm; }
    public void setDistanciaKm(double distanciaKm) { this.distanciaKm = distanciaKm; }

    public double getCosto() { return costo; }
    public void setCosto(double costo) { this.costo = costo; }

    public String getFechaViaje() { return fechaViaje; }
    public void setFechaViaje(String fechaViaje) { this.fechaViaje = fechaViaje; }

    // Campos auxiliares (para vistas)
    public String getNombreConductor() { return nombreConductor; }
    public void setNombreConductor(String nombreConductor) { this.nombreConductor = nombreConductor; }

    public String getDescripcionVehiculo() { return descripcionVehiculo; }
    public void setDescripcionVehiculo(String descripcionVehiculo) { this.descripcionVehiculo = descripcionVehiculo; }

    public String getTipoVehiculo() { return tipoVehiculo; }
    public void setTipoVehiculo(String tipoVehiculo) { this.tipoVehiculo = tipoVehiculo; }
}
