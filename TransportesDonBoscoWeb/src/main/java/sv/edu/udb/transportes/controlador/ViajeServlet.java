package sv.edu.udb.transportes.controlador;

// Importación de las clases del modelo
import sv.edu.udb.transportes.modelo.*;

// Importaciones de Jakarta Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

/**
 * Controlador (Servlet) encargado de gestionar las operaciones relacionadas con los viajes.
 * Responde a las peticiones HTTP GET y POST para mostrar el formulario de registro,
 * listar los viajes existentes y guardar nuevos viajes en la base de datos.
 *
 * Las restricciones de negocio aplicadas son:
 * - Solo conductores con licencia vigente pueden realizar viajes
 * - Solo vehículos con estado "Al día" pueden ser utilizados
 * - El costo se calcula según el tipo de vehículo (Moto: $0.05/km, Camión: $0.20/km)
 */
@WebServlet("/ViajeServlet")
public class ViajeServlet extends HttpServlet {

    // Instancias de los DAO para acceder a los datos necesarios
    private ConductorDAO conductorDAO = new ConductorDAO(); // Para validar conductores
    private VehiculoDAO vehiculoDAO = new VehiculoDAO();   // Para obtener tipo y calcular tarifa
    private ViajeDAO viajeDAO = new ViajeDAO();            // Para guardar el viaje

    /**
     * Maneja las peticiones GET.
     * Acciones soportadas:
     * - nuevo: muestra el formulario de registro con conductores y vehículos disponibles
     * - listar: muestra el historial de todos los viajes registrados
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");
        if (accion == null) accion = "nuevo"; // Por defecto muestra el formulario

        if ("nuevo".equals(accion)) {
            // Filtrar conductores que tengan licencia vigente
            List<Conductor> todos = conductorDAO.listar();
            List<Conductor> disponibles = todos.stream()
                    .filter(Conductor::isLicenciaVigente)  // Solo licencia vigente
                    .toList();
            req.setAttribute("conductores", disponibles);

            // Filtrar vehículos que estén en estado "Al día"
            List<Vehiculo> todosVehiculos = vehiculoDAO.listar();
            List<Vehiculo> vehiculosDisp = todosVehiculos.stream()
                    .filter(v -> "Al día".equals(v.getEstadoMantenimiento())) // Solo operativos
                    .toList();
            req.setAttribute("vehiculos", vehiculosDisp);

            // Mostrar el formulario de registro
            req.getRequestDispatcher("/registrarViaje.jsp").forward(req, resp);

        } else if ("listar".equals(accion)) {
            // Obtener todos los viajes y enviarlos a la vista
            req.setAttribute("viajes", viajeDAO.listar());
            req.getRequestDispatcher("/listaViajes.jsp").forward(req, resp);
        }
    }

    /**
     * Maneja las peticiones POST (registro de un nuevo viaje).
     * Recibe los datos del formulario, calcula el costo según el tipo de vehículo,
     * guarda el viaje en la base de datos y redirige al listado de viajes.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener parámetros del formulario
        String dui = req.getParameter("duiConductor");
        int idVehiculo = Integer.parseInt(req.getParameter("idVehiculo"));
        double distancia = Double.parseDouble(req.getParameter("distanciaKm"));

        // Obtener el vehículo para conocer su tipo y calcular la tarifa correspondiente
        Vehiculo v = vehiculoDAO.obtenerPorId(idVehiculo);
        double tarifa = ("Moto".equals(v.getNombreTipo())) ? 0.05 : 0.20; // Moto: $0.05, Camión: $0.20
        double costo = distancia * tarifa; // Calcular costo total del viaje

        // Crear objeto Viaje y llenarlo con los datos
        Viaje viaje = new Viaje();
        viaje.setDuiConductor(dui);
        viaje.setIdVehiculo(idVehiculo);
        viaje.setDistanciaKm(distancia);
        viaje.setCosto(costo);
        viaje.setFechaViaje(LocalDate.now().toString()); // Fecha actual

        // Intentar guardar el viaje en la base de datos
        boolean ok = viajeDAO.insertar(viaje);

        // Establecer mensaje de éxito o error en sesión
        if (ok) {
            req.getSession().setAttribute("mensaje", "Viaje registrado exitosamente. Costo: $" + String.format("%.2f", costo));
        } else {
            req.getSession().setAttribute("mensajeError", "Error al registrar el viaje");
        }

        // Redirigir al listado de viajes
        resp.sendRedirect(req.getContextPath() + "/ViajeServlet?accion=listar");
    }
}
