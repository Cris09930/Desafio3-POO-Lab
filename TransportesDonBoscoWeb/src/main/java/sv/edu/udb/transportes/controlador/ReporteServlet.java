package sv.edu.udb.transportes.controlador;

// Importación de las clases del modelo (DAO y POJOs)
import sv.edu.udb.transportes.modelo.*;

// Importaciones de Jakarta Servlet para manejo de peticiones HTTP
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controlador (Servlet) encargado de generar el reporte general del sistema.
 * Recopila información de conductores, vehículos y viajes, incluyendo el viaje más costoso,
 * y envía los datos a la vista reporte.jsp para su visualización.
 *
 * Este servlet responde únicamente a peticiones GET y no modifica el estado de la aplicación.
 *
 */
@WebServlet("/ReporteServlet") // Mapeo del Servlet: se accede a través de /ReporteServlet
public class ReporteServlet extends HttpServlet {

    // Instancias de los DAO necesarios para obtener datos de la base de datos
    private ConductorDAO conductorDAO = new ConductorDAO(); // Acceso a datos de conductores
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();   // Acceso a datos de vehículos
    private ViajeDAO viajeDAO = new ViajeDAO();            // Acceso a datos de viajes

    /**
     * Maneja las peticiones GET (solicitud del reporte).
     * Recupera todos los conductores, vehículos, viajes y el viaje con mayor costo,
     * los almacena como atributos de la petición y redirige a la vista JSP del reporte.
     *
     * @param req  objeto HttpServletRequest que contiene la petición del cliente
     * @param resp objeto HttpServletResponse para enviar la respuesta
     * @throws ServletException si ocurre un error en el procesamiento del Servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener la lista completa de conductores desde el DAO y asignarla como atributo
        req.setAttribute("conductores", conductorDAO.listar());

        // Obtener la lista completa de vehículos desde el DAO
        req.setAttribute("vehiculos", vehiculoDAO.listar());

        // Obtener el historial de todos los viajes realizados
        req.setAttribute("viajes", viajeDAO.listar());

        // Obtener el viaje con el costo más alto (para destacarlo en el reporte)
        req.setAttribute("viajeMasCaro", viajeDAO.obtenerViajeMasCaro());

        // Transferir el control a la página JSP que renderizará el reporte
        req.getRequestDispatcher("/reporte.jsp").forward(req, resp);
    }
}
