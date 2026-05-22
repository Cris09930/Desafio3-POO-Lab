package sv.edu.udb.transportes.modelo;

// Importación de la clase utilitaria para conexión a base de datos
import sv.edu.udb.transportes.util.DatabaseConnection;

// Clases JDBC para trabajar con SQL
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) encargada de gestionar todas las operaciones
 * relacionadas con los viajes en la base de datos.
 *
 * Proporciona métodos para insertar un nuevo viaje, listar todos los viajes
 * y obtener el viaje con el costo más alto.
 * Todas las operaciones utilizan PreparedStatement para prevenir inyección SQL.
 */
public class ViajeDAO {

    // Consulta SQL para insertar un nuevo viaje
    private static final String SQL_INSERT =
            "INSERT INTO viaje (dui_conductor, id_vehiculo, distancia_km, costo, fecha_viaje) VALUES (?, ?, ?, ?, ?)";

    // Consulta SQL con JOINs para obtener todos los viajes con datos del conductor y vehículo
    private static final String SQL_SELECT_ALL =
            "SELECT v.id_viaje, v.dui_conductor, c.nombre_completo, " +
                    "v.id_vehiculo, ve.marca, ve.modelo, tv.nombre_tipo, " +
                    "v.distancia_km, v.costo, v.fecha_viaje " +
                    "FROM viaje v " +
                    "JOIN conductor c ON v.dui_conductor = c.dui " +
                    "JOIN vehiculo ve ON v.id_vehiculo = ve.id_vehiculo " +
                    "JOIN tipo_vehiculo tv ON ve.id_tipo = tv.id_tipo " +
                    "ORDER BY v.fecha_viaje DESC";

    /**
     * Inserta un nuevo viaje en la base de datos.
     *
     * @param viaje Objeto Viaje con los datos a insertar (dui_conductor, id_vehiculo,
     *              distancia_km, costo, fecha_viaje)
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public boolean insertar(Viaje viaje) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

            // Asignar cada parámetro en orden (índice comienza en 1)
            ps.setString(1, viaje.getDuiConductor());
            ps.setInt(2, viaje.getIdVehiculo());
            ps.setDouble(3, viaje.getDistanciaKm());
            ps.setDouble(4, viaje.getCosto());
            ps.setString(5, viaje.getFechaViaje());

            // executeUpdate devuelve número de filas afectadas
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Obtiene una lista con todos los viajes registrados en la base de datos.
     * Incluye datos del conductor (nombre) y del vehículo (marca, modelo, tipo)
     * gracias a los JOINs con las tablas conductor, vehiculo y tipo_vehiculo.
     *
     * @return Lista de objetos Viaje con datos completos para mostrar en listados
     */
    public List<Viaje> listar() {
        List<Viaje> lista = new ArrayList<>();

        // Uso de try-with-resources para asegurar cierre automático de recursos
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            // Recorrer cada fila del resultado
            while (rs.next()) {
                Viaje v = new Viaje();
                // Mapear columnas SQL -> atributos Java
                v.setIdViaje(rs.getInt("id_viaje"));
                v.setDuiConductor(rs.getString("dui_conductor"));
                v.setNombreConductor(rs.getString("nombre_completo"));
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                // Construir descripción del vehículo: "Marca Modelo (Tipo)"
                v.setDescripcionVehiculo(rs.getString("marca") + " " + rs.getString("modelo") + " (" + rs.getString("nombre_tipo") + ")");
                v.setTipoVehiculo(rs.getString("nombre_tipo"));
                v.setDistanciaKm(rs.getDouble("distancia_km"));
                v.setCosto(rs.getDouble("costo"));
                v.setFechaViaje(rs.getString("fecha_viaje"));
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Obtiene el viaje con el costo más alto registrado en la base de datos.
     * Utiliza ORDER BY costo DESC LIMIT 1 para obtener solo el registro más costoso.
     * Incluye los mismos JOINs que el método listar().
     *
     * @return Objeto Viaje con el costo más alto, o null si no hay viajes registrados
     */
    public Viaje obtenerViajeMasCaro() {
        String sql = "SELECT v.id_viaje, v.dui_conductor, c.nombre_completo, " +
                "v.id_vehiculo, ve.marca, ve.modelo, tv.nombre_tipo, " +
                "v.distancia_km, v.costo, v.fecha_viaje " +
                "FROM viaje v " +
                "JOIN conductor c ON v.dui_conductor = c.dui " +
                "JOIN vehiculo ve ON v.id_vehiculo = ve.id_vehiculo " +
                "JOIN tipo_vehiculo tv ON ve.id_tipo = tv.id_tipo " +
                "ORDER BY v.costo DESC LIMIT 1";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                Viaje v = new Viaje();
                // Mapear columnas SQL -> atributos Java
                v.setIdViaje(rs.getInt("id_viaje"));
                v.setDuiConductor(rs.getString("dui_conductor"));
                v.setNombreConductor(rs.getString("nombre_completo"));
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                // Construir descripción del vehículo
                v.setDescripcionVehiculo(rs.getString("marca") + " " + rs.getString("modelo") + " (" + rs.getString("nombre_tipo") + ")");
                v.setTipoVehiculo(rs.getString("nombre_tipo"));
                v.setDistanciaKm(rs.getDouble("distancia_km"));
                v.setCosto(rs.getDouble("costo"));
                v.setFechaViaje(rs.getString("fecha_viaje"));
                return v;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
