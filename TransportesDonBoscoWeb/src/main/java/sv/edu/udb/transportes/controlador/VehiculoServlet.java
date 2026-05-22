package sv.edu.udb.transportes.controlador;

// Importación de las clases del modelo
import sv.edu.udb.transportes.modelo.Vehiculo;
import sv.edu.udb.transportes.modelo.VehiculoDAO;

// Importaciones de Jakarta Servlet
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controlador (Servlet) encargado de gestionar todas las operaciones relacionadas con los vehículos.
 * Responde a las peticiones HTTP GET y POST para listar, insertar, actualizar y eliminar vehículos.
 * Las acciones se diferencian mediante el parámetro "accion" enviado desde las vistas JSP.
 */
@WebServlet("/VehiculoServlet")
public class VehiculoServlet extends HttpServlet {

    // Instancia del DAO para interactuar con la base de datos
    private VehiculoDAO dao = new VehiculoDAO();

    /**
     * Maneja las peticiones GET (obtener datos).
     * Acciones soportadas:
     * - listar: muestra todos los vehículos
     * - nuevo: muestra el formulario vacío con los tipos de vehículo
     * - editar: carga los datos de un vehículo existente en el formulario
     * - eliminar: borra un vehículo de la base de datos
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener la acción (por defecto "listar")
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        switch (accion) {
            case "nuevo":
                // Cargar la lista de tipos de vehículo para el combo del formulario
                req.setAttribute("tipos", dao.listarTipos());
                // Mostrar formulario vacío
                req.getRequestDispatcher("/formularioVehiculo.jsp").forward(req, resp);
                break;

            case "editar":
                // Obtener el ID del vehículo a editar
                int id = Integer.parseInt(req.getParameter("id"));
                // Buscar el vehículo en la base de datos
                Vehiculo v = dao.obtenerPorId(id);
                // Enviar el vehículo, los tipos y el modo edición a la vista
                req.setAttribute("vehiculo", v);
                req.setAttribute("tipos", dao.listarTipos());
                req.setAttribute("modoEdicion", true);
                req.getRequestDispatcher("/formularioVehiculo.jsp").forward(req, resp);
                break;

            case "eliminar":
                id = Integer.parseInt(req.getParameter("id"));
                // Intentar eliminar el vehículo
                if (dao.eliminar(id)) {
                    req.getSession().setAttribute("mensaje", "Vehículo eliminado correctamente");
                } else {
                    req.getSession().setAttribute("mensajeError", "Error al eliminar el vehículo");
                }
                // Redirigir al listado
                resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
                break;

            default: // "listar"
                // Obtener todos los vehículos y enviarlos a la vista
                req.setAttribute("vehiculos", dao.listar());
                req.getRequestDispatcher("/listaVehiculos.jsp").forward(req, resp);
                break;
        }
    }

    /**
     * Maneja las peticiones POST (envío de formularios).
     * Acciones soportadas:
     * - insertar: crea un nuevo vehículo
     * - actualizar: modifica un vehículo existente
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        // Crear objeto Vehiculo y llenarlo con los datos del formulario
        Vehiculo v = new Vehiculo();
        v.setIdTipo(Integer.parseInt(req.getParameter("idTipo")));
        v.setMarca(req.getParameter("marca"));
        v.setModelo(req.getParameter("modelo"));
        v.setAnio(Integer.parseInt(req.getParameter("anio")));
        v.setDatoEspecifico(Double.parseDouble(req.getParameter("datoEspecifico")));
        v.setEstadoMantenimiento(req.getParameter("estadoMantenimiento"));

        boolean ok; // Resultado de la operación

        // Ejecutar la operación correspondiente
        if ("actualizar".equals(accion)) {
            // Modo actualización: se necesita el ID
            v.setIdVehiculo(Integer.parseInt(req.getParameter("idVehiculo")));
            ok = dao.actualizar(v);
        } else {
            // Modo inserción
            ok = dao.insertar(v);
        }

        // Establecer mensaje de éxito o error en sesión
        if (ok) {
            req.getSession().setAttribute("mensaje", "Operación exitosa sobre el vehículo");
        } else {
            req.getSession().setAttribute("mensajeError", "Error en la operación");
        }

        // Redirigir al listado de vehículos
        resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
    }
}
