package sv.edu.udb.transportes.controlador;

// Importación de las clases necesarias del modelo
import sv.edu.udb.transportes.modelo.Conductor;
import sv.edu.udb.transportes.modelo.ConductorDAO;

// Importaciones de Jakarta Servlet para manejo de peticiones HTTP
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * Controlador (Servlet) encargado de gestionar todas las operaciones relacionadas con los conductores.
 * Responde a las peticiones HTTP GET y POST para listar, insertar, actualizar y eliminar conductores.
 * Las acciones se diferencian mediante el parámetro "accion" enviado desde las vistas JSP.
 *
 * Este servlet actúa como el punto de entrada para la gestión de conductores,
 * siguiendo el patrón MVC (Modelo-Vista-Controlador).
 *
 */
@WebServlet("/ConductorServlet") // Mapeo del Servlet: se accede a través de /ConductorServlet
public class ConductorServlet extends HttpServlet {

    // Instancia del DAO (Data Access Object) para interactuar con la base de datos
    private ConductorDAO dao = new ConductorDAO();

    /**
     * Maneja las peticiones GET (obtener datos).
     * Las acciones soportadas son:
     * - listar: muestra todos los conductores
     * - nuevo: muestra el formulario vacío para crear un nuevo conductor
     * - editar: carga los datos de un conductor existente en el formulario para modificarlo
     * - eliminar: borra un conductor de la base de datos
     *
     * @param req  objeto HttpServletRequest que contiene los parámetros de la petición
     * @param resp objeto HttpServletResponse para enviar la respuesta al cliente
     * @throws ServletException si ocurre un error en el procesamiento del Servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener el parámetro "accion" (si no viene, por defecto "listar")
        String accion = req.getParameter("accion");
        if (accion == null) accion = "listar";

        // Estructura switch para ejecutar la acción solicitada
        switch (accion) {
            case "nuevo":
                // Mostrar formulario vacío para registrar un nuevo conductor
                req.getRequestDispatcher("/formularioConductor.jsp").forward(req, resp);
                break;

            case "editar":
                // Obtener el DUI del conductor a editar
                String dui = req.getParameter("dui");
                // Buscar el conductor en la base de datos mediante el DAO
                Conductor c = dao.obtenerPorDui(dui);
                // Enviar el objeto conductor y un flag de modo edición a la vista
                req.setAttribute("conductor", c);
                req.setAttribute("modoEdicion", true);
                // Redirigir al formulario (que usará los datos para precargar los campos)
                req.getRequestDispatcher("/formularioConductor.jsp").forward(req, resp);
                break;

            case "eliminar":
                dui = req.getParameter("dui");
                // Intentar eliminar el conductor
                if (dao.eliminar(dui)) {
                    // Éxito: guardar mensaje en sesión
                    req.getSession().setAttribute("mensaje", "Conductor eliminado correctamente");
                } else {
                    // Error: guardar mensaje de error
                    req.getSession().setAttribute("mensajeError", "Error al eliminar el conductor");
                }
                // Redirigir al listado de conductores
                resp.sendRedirect(req.getContextPath() + "/ConductorServlet?accion=listar");
                break;

            default: // accion = "listar" o cualquier otro valor no reconocido
                // Obtener la lista de todos los conductores desde el DAO
                req.setAttribute("conductores", dao.listar());
                // Enviar la lista a la vista de listado
                req.getRequestDispatcher("/listaConductores.jsp").forward(req, resp);
                break;
        }
    }

    /**
     * Maneja las peticiones POST (envío de formularios).
     * Las acciones soportadas son:
     * - insertar: crea un nuevo conductor
     * - actualizar: modifica un conductor existente
     *
     * @param req  objeto HttpServletRequest que contiene los datos del formulario
     * @param resp objeto HttpServletResponse para enviar la respuesta al cliente
     * @throws ServletException si ocurre un error en el procesamiento del Servlet
     * @throws IOException      si ocurre un error de entrada/salida
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        // Obtener la acción (insertar o actualizar)
        String accion = req.getParameter("accion");

        // Crear un objeto Conductor y llenarlo con los datos recibidos del formulario
        Conductor c = new Conductor();
        c.setDui(req.getParameter("dui"));
        c.setNombreCompleto(req.getParameter("nombreCompleto"));
        c.setEdad(Integer.parseInt(req.getParameter("edad")));
        c.setSexo(req.getParameter("sexo"));
        // Convertir el parámetro "licenciaVigente" (true/false) a booleano
        c.setLicenciaVigente("true".equals(req.getParameter("licenciaVigente")));

        boolean ok; // Variable para almacenar el resultado de la operación

        // Ejecutar la operación correspondiente según la acción
        if ("actualizar".equals(accion)) {
            // Modo actualización: actualizar el conductor existente
            ok = dao.actualizar(c);
        } else {
            // Por defecto: insertar un nuevo conductor
            ok = dao.insertar(c);
        }

        // Establecer mensaje de éxito o error en la sesión
        if (ok) {
            req.getSession().setAttribute("mensaje", "Operación exitosa sobre el conductor");
        } else {
            req.getSession().setAttribute("mensajeError", "Error en la operación (posible DUI duplicado o datos inválidos)");
        }

        // Redirigir siempre al listado de conductores después de la operación
        resp.sendRedirect(req.getContextPath() + "/ConductorServlet?accion=listar");
    }
}
