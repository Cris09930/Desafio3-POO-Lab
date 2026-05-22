package sv.edu.udb.transportes.modelo;

import java.io.Serializable;

/**
 * Clase POJO (Plain Old Java Object) que representa un conductor en el sistema.
 * Implementa Serializable para permitir su almacenamiento en sesión HTTP.
 *
 * Esta clase mapea directamente la tabla 'conductor' de la base de datos.
 * Cada instancia corresponde a un registro de conductor.
 */
public class Conductor implements Serializable {

    // Atributos privados (encapsulamiento)
    private String dui;              // Documento Único de Identidad (clave primaria)
    private String nombreCompleto;   // Nombre completo del conductor
    private int edad;                // Edad del conductor (debe ser >= 18)
    private String sexo;             // Sexo del conductor ('M' o 'F')
    private boolean licenciaVigente; // Estado de la licencia (true = vigente, false = vencida)

    /**
     * Constructor vacío (requerido por JSP/EL y frameworks).
     * Necesario para que JSTL pueda instanciar objetos de esta clase.
     */
    public Conductor() {}

    /**
     * Constructor completo para crear un conductor con todos sus datos.
     *
     * @param dui              Documento Único de Identidad (formato 00000000-0)
     * @param nombreCompleto   Nombre completo del conductor
     * @param edad             Edad del conductor (debe ser >= 18)
     * @param sexo             Sexo ('M' para masculino, 'F' para femenino)
     * @param licenciaVigente  true si la licencia está vigente, false en caso contrario
     */
    public Conductor(String dui, String nombreCompleto, int edad, String sexo, boolean licenciaVigente) {
        this.dui = dui;
        this.nombreCompleto = nombreCompleto;
        this.edad = edad;
        this.sexo = sexo;
        this.licenciaVigente = licenciaVigente;
    }

    // GETTERS Y SETTERS

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public boolean isLicenciaVigente() { return licenciaVigente; }
    public void setLicenciaVigente(boolean licenciaVigente) { this.licenciaVigente = licenciaVigente; }

    /**
     * Método auxiliar para mostrar el estado de la licencia en formato texto.
     * Utilizado en las vistas JSP para mostrar "Sí" o "No" en lugar de true/false.
     *
     * @return "Sí" si la licencia está vigente, "No" en caso contrario
     */
    public String getLicenciaTexto() {
        return licenciaVigente ? "Sí" : "No";
    }
}
