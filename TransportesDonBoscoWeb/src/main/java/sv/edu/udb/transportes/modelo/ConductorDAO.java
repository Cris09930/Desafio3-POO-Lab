package sv.edu.udb.transportes.modelo;

// Importación de la clase utilitaria para conexión a base de datos
import sv.edu.udb.transportes.util.DatabaseConnection;

// Clases JDBC para trabajar con SQL
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase DAO (Data Access Object) encargada de gestionar todas las operaciones
 * relacionadas con los conductores en la base de datos.
 *
 * Proporciona métodos para listar, buscar, insertar, actualizar y eliminar conductores.
 * Todas las operaciones utilizan PreparedStatement para prevenir inyección SQL.
 */
public class ConductorDAO {

    // Consultas SQL preparadas (constantes)
    private static final String SQL_SELECT_ALL = "SELECT dui, nombre_completo, edad, sexo, licencia_vigente FROM conductor ORDER BY nombre_completo";
    private static final String SQL_SELECT_BY_ID = "SELECT dui, nombre_completo, edad, sexo, licencia_vigente FROM conductor WHERE dui = ?";
    private static final String SQL_INSERT = "INSERT INTO conductor (dui, nombre_completo, edad, sexo, licencia_vigente) VALUES (?, ?, ?, ?, ?)";
    private static final String SQL_UPDATE = "UPDATE conductor SET nombre_completo = ?, edad = ?, sexo = ?, licencia_vigente = ? WHERE dui = ?";
    private static final String SQL_DELETE = "DELETE FROM conductor WHERE dui = ?";

    /**
     * Obtiene una lista con todos los conductores registrados en la base de datos.
     *
     * @return Lista de objetos Conductor, vacía si no hay registros
     */
    public List<Conductor> listar() {
        List<Conductor> lista = new ArrayList<>();

        // Uso de try-with-resources para asegurar cierre automático de recursos
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_ALL);
             ResultSet rs = ps.executeQuery()) {

            // Recorrer cada fila del resultado
            while (rs.next()) {
                Conductor c = new Conductor();
                // Mapear columnas SQL -> atributos Java
                c.setDui(rs.getString("dui"));
                c.setNombreCompleto(rs.getString("nombre_completo"));
                c.setEdad(rs.getInt("edad"));
                c.setSexo(rs.getString("sexo"));
                c.setLicenciaVigente(rs.getBoolean("licencia_vigente"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Busca un conductor por su DUI (clave primaria).
     *
     * @param dui DUI del conductor a buscar (formato 00000000-0)
     * @return Objeto Conductor si existe, null si no se encuentra
     */
    public Conductor obtenerPorDui(String dui) {
        Conductor c = null;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_SELECT_BY_ID)) {

            // Asignar el parámetro DUI a la consulta
            ps.setString(1, dui);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    c = new Conductor();
                    c.setDui(rs.getString("dui"));
                    c.setNombreCompleto(rs.getString("nombre_completo"));
                    c.setEdad(rs.getInt("edad"));
                    c.setSexo(rs.getString("sexo"));
                    c.setLicenciaVigente(rs.getBoolean("licencia_vigente"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return c;
    }

    /**
     * Inserta un nuevo conductor en la base de datos.
     *
     * @param c Objeto Conductor con los datos a insertar
     * @return true si la inserción fue exitosa, false en caso contrario (ej. DUI duplicado)
     */
    public boolean insertar(Conductor c) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_INSERT)) {

            // Asignar cada parámetro en orden (índice comienza en 1)
            ps.setString(1, c.getDui());
            ps.setString(2, c.getNombreCompleto());
            ps.setInt(3, c.getEdad());
            ps.setString(4, c.getSexo());
            ps.setBoolean(5, c.isLicenciaVigente());

            // executeUpdate devuelve número de filas afectadas
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Actualiza los datos de un conductor existente.
     *
     * @param c Objeto Conductor con los datos actualizados (el DUI no se modifica)
     * @return true si la actualización fue exitosa, false en caso contrario
     */
    public boolean actualizar(Conductor c) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_UPDATE)) {

            // Asignar parámetros (nombre, edad, sexo, licencia, y DUI para el WHERE)
            ps.setString(1, c.getNombreCompleto());
            ps.setInt(2, c.getEdad());
            ps.setString(3, c.getSexo());
            ps.setBoolean(4, c.isLicenciaVigente());
            ps.setString(5, c.getDui());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Elimina un conductor de la base de datos por su DUI.
     *
     * @param dui DUI del conductor a eliminar
     * @return true si la eliminación fue exitosa, false en caso contrario
     */
    public boolean eliminar(String dui) {
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL_DELETE)) {

            ps.setString(1, dui);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            return false;
        }
    }
}
