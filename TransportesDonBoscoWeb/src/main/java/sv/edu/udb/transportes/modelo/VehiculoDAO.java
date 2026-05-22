package sv.edu.udb.transportes.modelo;

// Importación de la clase utilitaria para conexión a base de datos
import sv.edu.udb.transportes.util.DatabaseConnection;

// Clases JDBC para trabajar con SQL
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) encargada de gestionar todas las operaciones
 * relacionadas con los vehículos en la base de datos.
 *
 * Proporciona métodos para listar, buscar, insertar, actualizar y eliminar vehículos.
 * También incluye un método para obtener los tipos de vehículo disponibles.
 * Todas las operaciones utilizan PreparedStatement para prevenir inyección SQL.
 */
public class VehiculoDAO {

    // Consultas SQL preparadas (constantes)
    // SELECT con JOIN para obtener también el nombre del tipo de vehículo
    private static final String SQL_SELECT_ALL =
            "SELECT v.id_vehiculo, v.id_tipo, tv.nombre_tipo, v.marca, v.modelo, v.anio, " +
                    "v.dato_especifico, v.estado_mantenimiento FROM vehiculo v " +
                    "INNER JOIN tipo_vehiculo tv ON v.id_tipo = tv.id_tipo ORDER BY v.id_vehiculo";

    // SELECT por ID (también con JOIN)
    private static final String SQL_SELECT_BY_ID =
            "SELECT v.id_vehiculo, v.id_tipo, tv.nombre_tipo, v.marca, v.modelo, v.anio, " +
                    "v.dato_especifico, v.estado_mantenimiento FROM vehiculo v " +
                    "INNER JOIN tipo_vehiculo tv ON v.id_tipo = tv.id_tipo WHERE v.id_vehiculo = ?";

    private static final String SQL_INSERT =
            "INSERT INTO vehiculo (id_tipo, marca, modelo, anio, dato_especifico, estado_mantenimiento) VALUES (?, ?, ?, ?, ?, ?)";

    private static final String SQL_UPDATE =
            "UPDATE vehiculo SET id_tipo = ?, marca = ?, modelo = ?, anio = ?, dato_especifico = ?, estado_mantenimiento = ? WHERE id_vehiculo = ?";

    private static final String SQL_DELETE = "DELETE FROM vehiculo WHERE id_vehiculo = ?";

    /**
     * Obtiene una lista con todos los vehículos registrados en la base de datos.
     * Incluye el nombre del tipo de vehículo gracias al JOIN con la tabla tipo_vehiculo.
     *
     * @return Lista de objetos Vehiculo, vacía si no hay registros
     */
    public List<Vehiculo> listar() {
        List<Vehiculo> lista = new ArrayList<>();

        // Uso de try-with-resources para asegurar cierre automático de recursos
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            // Recorrer cada fila del resultado
            while (rs.next()) {
                Vehiculo v = new Vehiculo();
                // Mapear columnas SQL -> atributos Java
                v.setIdVehiculo(rs.getInt("id_vehiculo"));
                v.setIdTipo(rs.getInt("id_tipo"));
                v.setNombreTipo(rs.getString("nombre_tipo"));
                v.setMarca(rs.getString("marca"));
                v.setModelo(rs.getString("modelo"));
                v.setAnio(rs.getInt("anio"));
                v.setDatoEspecifico(rs.getDouble("dato_especifico"));
                v.setEstadoMantenimiento(rs.getString("estado_mantenimiento"));
                lista.add(v);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca un vehículo por su ID (clave primaria).
     * Incluye el nombre del tipo de vehículo gracias al JOIN.
     *
     * @param id Identificador del vehículo a buscar
     * @return Objeto Vehiculo si existe, null si no se encuentra
     */
    public Vehiculo obtenerPorId(int id) {
        Vehiculo v = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            // Asignar el parámetro ID a la consulta
            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    v = new Vehiculo();
                    v.setIdVehiculo(rs.getInt("id_vehiculo"));
                    v.setIdTipo(rs.getInt("id_tipo"));
                    v.setNombreTipo(rs.getString("nombre_tipo"));
                    v.setMarca(rs.getString("marca"));
                    v.setModelo(rs.getString("modelo"));
                    v.setAnio(rs.getInt("anio"));
                    v.setDatoEspecifico(rs.getDouble("dato_especifico"));
                    v.setEstadoMantenimiento(rs.getString("estado_mantenimiento"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return v;
    }

    /**
     * Inserta un nuevo vehículo en la base de datos.
     * Utiliza RETURN_GENERATED_KEYS para obtener el ID autogenerado.
     *
     * @param v Objeto Vehiculo con los datos a insertar
     * @return true si la inserción fue exitosa, false en caso contrario
     */
    public boolean insertar(Vehiculo v) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT, Statement.RETURN_GENERATED_KEYS)) {

            // Asignar cada parámetro en orden (índice comienza en 1)
            ps.setInt(1, v.getIdTipo());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setInt(4, v.getAnio());
            ps.setDouble(5, v.getDatoEspecifico());
            ps.setString(6, v.getEstadoMantenimiento());

            // executeUpdate devuelve número de filas afectadas
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Actualiza los datos de un vehículo existente.
     *
     * @param v Objeto Vehiculo con los datos actualizados
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizar(Vehiculo v) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            // Asignar parámetros (tipo, marca, modelo, año, dato, estado, y ID para el WHERE)
            ps.setInt(1, v.getIdTipo());
            ps.setString(2, v.getMarca());
            ps.setString(3, v.getModelo());
            ps.setInt(4, v.getAnio());
            ps.setDouble(5, v.getDatoEspecifico());
            ps.setString(6, v.getEstadoMantenimiento());
            ps.setInt(7, v.getIdVehiculo());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Elimina un vehículo de la base de datos por su ID.
     *
     * @param id Identificador del vehículo a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(int id) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Obtiene la lista de tipos de vehículo disponibles en la base de datos.
     * Utilizado para llenar los combos (select) en los formularios.
     *
     * @return Lista de objetos TipoVehiculo (Moto y Camion)
     */
    public List<TipoVehiculo> listarTipos() {
        List<TipoVehiculo> tipos = new ArrayList<>();
        String sql = "SELECT id_tipo, nombre_tipo FROM tipo_vehiculo";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                tipos.add(new TipoVehiculo(rs.getInt("id_tipo"), rs.getString("nombre_tipo")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tipos;
    }
}
