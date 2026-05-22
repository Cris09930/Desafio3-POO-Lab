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
     *
     * Validaciones aplicadas:
     * - Verificar que no haya campos vacíos
     * - Verificar que el año sea válido (1900-2030)
     * - Verificar que el dato específico sea positivo
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String accion = req.getParameter("accion");

        // Obtener parámetros del formulario
        String idTipoStr = req.getParameter("idTipo");
        String marca = req.getParameter("marca");
        String modelo = req.getParameter("modelo");
        String anioStr = req.getParameter("anio");
        String datoEspecificoStr = req.getParameter("datoEspecifico");
        String estadoMantenimiento = req.getParameter("estadoMantenimiento");
        

        // Validación 1: Tipo de vehículo seleccionado
        if (idTipoStr == null || idTipoStr.trim().isEmpty()) {
            req.getSession().setAttribute("mensajeError", "Debe seleccionar un tipo de vehículo");
            resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
            return;
        }

        int idTipo;
        try {
            idTipo = Integer.parseInt(idTipoStr);
            if (idTipo <= 0) {
                req.getSession().setAttribute("mensajeError", "Tipo de vehículo no válido");
                resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
                return;
            }
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("mensajeError", "Tipo de vehículo no válido");
            resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
            return;
        }

        // Validación 2: Marca no vacía
        if (marca == null || marca.trim().isEmpty()) {
            req.getSession().setAttribute("mensajeError", "La marca del vehículo es obligatoria");
            resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
            return;
        }

        // Validación 3: Modelo no vacío
        if (modelo == null || modelo.trim().isEmpty()) {
            req.getSession().setAttribute("mensajeError", "El modelo del vehículo es obligatorio");
            resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
            return;
        }

        // Validación 4: Año válido
        if (anioStr == null || anioStr.trim().isEmpty()) {
            req.getSession().setAttribute("mensajeError", "El año del vehículo es obligatorio");
            resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
            return;
        }

        int anio;
        try {
            anio = Integer.parseInt(anioStr);
            if (anio < 1900) {
                req.getSession().setAttribute("mensajeError", "El año no puede ser menor a 1900");
                resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
                return;
            }
            if (anio > 2030) {
                req.getSession().setAttribute("mensajeError", "El año no puede ser mayor a 2030");
                resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
                return;
            }
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("mensajeError", "Debe ingresar un año válido");
            resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
            return;
        }

        // Validación 5: Dato específico (cilindraje o capacidad) positivo
        if (datoEspecificoStr == null || datoEspecificoStr.trim().isEmpty()) {
            req.getSession().setAttribute("mensajeError", "El dato específico (cilindraje o capacidad) es obligatorio");
            resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
            return;
        }

        double datoEspecifico;
        try {
            datoEspecifico = Double.parseDouble(datoEspecificoStr);
            if (datoEspecifico <= 0) {
                req.getSession().setAttribute("mensajeError", "El dato específico debe ser mayor a 0");
                resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
                return;
            }
            // Validación adicional para cilindraje (valores razonables)
            if (datoEspecifico > 3000) {
                req.getSession().setAttribute("mensajeError", "El valor ingresado es demasiado alto (máx 3000 CC o 50 ton)");
                resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
                return;
            }
        } catch (NumberFormatException e) {
            req.getSession().setAttribute("mensajeError", "Debe ingresar un número válido para el dato específico");
            resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
            return;
        }



        // Crear objeto Vehiculo y llenarlo con los datos del formulario
        Vehiculo v = new Vehiculo();
        v.setIdTipo(idTipo);
        v.setMarca(marca.trim());
        v.setModelo(modelo.trim());
        v.setAnio(anio);
        v.setDatoEspecifico(datoEspecifico);
        v.setEstadoMantenimiento(estadoMantenimiento);

        boolean ok; // Resultado de la operación

        // Ejecutar la operación correspondiente
        if ("actualizar".equals(accion)) {
            // Modo actualización: se necesita el ID
            String idVehiculoStr = req.getParameter("idVehiculo");
            if (idVehiculoStr == null || idVehiculoStr.trim().isEmpty()) {
                req.getSession().setAttribute("mensajeError", "ID de vehículo no válido");
                resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
                return;
            }
            try {
                v.setIdVehiculo(Integer.parseInt(idVehiculoStr));
            } catch (NumberFormatException e) {
                req.getSession().setAttribute("mensajeError", "ID de vehículo no válido");
                resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
                return;
            }
            ok = dao.actualizar(v);
        } else {
            // Modo inserción
            ok = dao.insertar(v);
        }

        // Establecer mensaje de éxito o error en sesión
        if (ok) {
            req.getSession().setAttribute("mensaje", "Operación exitosa sobre el vehículo");
        } else {
            req.getSession().setAttribute("mensajeError", "Error en la operación (posible duplicado o datos inválidos)");
        }

        // Redirigir al listado de vehículos
        resp.sendRedirect(req.getContextPath() + "/VehiculoServlet?accion=listar");
    }
}
