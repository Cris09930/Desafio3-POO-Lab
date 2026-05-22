package sv.edu.udb.transportes.util;

// Importaciones JNDI para acceso al pool de conexiones
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

// Importación JDBC para manejo de conexiones
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Clase utilitaria para obtener conexiones desde el pool JNDI.
 * La configuración del pool se define en META-INF/context.xml
 * con el nombre de recurso "jdbc/flotadb".
 *
 * Esta clase implementa un patrón Singleton implícito mediante
 * un bloque estático que inicializa el DataSource una sola vez.
 */
public class DatabaseConnection {

    // DataSource que mantiene el pool de conexiones a la base de datos
    private static DataSource dataSource;

    // Bloque estático: se ejecuta una sola vez al cargar la clase
    static {
        try {
            // Obtener el contexto inicial JNDI
            Context initContext = new InitialContext();
            // Buscar el contexto de entorno (java:/comp/env)
            Context envContext = (Context) initContext.lookup("java:/comp/env");
            // Buscar el recurso DataSource definido en context.xml
            dataSource = (DataSource) envContext.lookup("jdbc/flotadb");
        } catch (NamingException e) {
            e.printStackTrace();
            // Si falla la configuración, lanzar excepción para detener la aplicación
            throw new RuntimeException("Error al configurar el DataSource: " + e.getMessage());
        }
    }

    /**
     * Obtiene una conexión activa desde el pool de conexiones.
     * No es necesario cerrar el DataSource, solo la conexión devuelta.
     *
     * @return Connection activa desde el pool
     * @throws SQLException si no se puede obtener una conexión
     */
    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}
