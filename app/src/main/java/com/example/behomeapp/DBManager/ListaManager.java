package com.example.behomeapp.DBManager;

import com.example.behomeapp.model.ListaCompraModelo;
import com.example.behomeapp.model.ProductoModelo;
import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ListaManager {

    private static final Logger log = Logger.getLogger(ListaManager.class.getName());
    private static final String OBTENER_LISTAS_QUERY = "SELECT * " +
            "FROM lista " +
            "WHERE id_piso = ?";

    private static final String OBTENER_ID_QUERY = "SELECT * " +
            "FROM lista " +
            "WHERE id_piso = ? " +
            "AND nombre = ?";

    private static final String OBTENER_PRODUCTOS_QUERY = "SELECT p.id, p.nombre " +
            "FROM Producto p " +
            "JOIN lista_producto lcp " +
            "ON p.id = lcp.id_producto " +
            "WHERE lcp.id_lista = ?";
    private static final String INSERTAR_LISTA_QUERY = "INSERT INTO lista (nombre, id_piso) " +
            "VALUES (?, ?)";
    private static final String INSERTAR_PRODUCTO_QUERY = "INSERT INTO Producto (nombre) " +
            "VALUES (?)";
    private static final String INSERTAR_LISTA_PRODUCTO_QUERY = "INSERT INTO Lista_Producto (id_lista, id_producto) " +
            "VALUES (?, ?)";
    private static final String DELETE_PRODUCTO_QUERY = "DELETE FROM Producto WHERE id = ?";
    private static final String DELETE_RELACION_QUERY = "DELETE FROM Lista_Producto WHERE id_producto = ?";




    public static List<ListaCompraModelo> obtenerListasCompras(String pisoId) {

        final List<ListaCompraModelo> listaComprasList = new ArrayList<>();

        try (final Connection connection = ConnectionService.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(OBTENER_LISTAS_QUERY)) {
            preparedStatement.setString(1, pisoId);
            try (final ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ListaCompraModelo listaCompraModelo = new ListaCompraModelo();
                    listaCompraModelo.setNombre(rs.getString("nombre"));
                    listaCompraModelo.setId(rs.getInt("id"));
                    listaComprasList.add(listaCompraModelo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return listaComprasList;
    }

    public static int obtenerIdLista(String idPiso, String nombreLista) {

        try (final Connection connection = ConnectionService.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(OBTENER_ID_QUERY)) {

            preparedStatement.setString(1, idPiso);
            preparedStatement.setString(2, nombreLista);

            try (final ResultSet rs = preparedStatement.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("id");
                }
                log.info(String.format("No se encontró ninguna lista de la compra '%s'en el piso: %s.", nombreLista, idPiso));
                return 0;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error al ejecutar la consulta SQL.", e);
        }
    }

    public static List<ProductoModelo> obtenerProductosLista(int listaId) {

        final List<ProductoModelo> productoModeloList = new ArrayList<>();


        try (final Connection connection = ConnectionService.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(OBTENER_PRODUCTOS_QUERY)) {
            preparedStatement.setInt(1, listaId);
            try (final ResultSet rs = preparedStatement.executeQuery()) {
                while (rs.next()) {
                    ProductoModelo productoModelo = new ProductoModelo();
                    productoModelo.setId(rs.getInt("id"));
                    productoModelo.setNombre(rs.getString("nombre"));

                    productoModeloList.add(productoModelo);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return productoModeloList;
    }

    public static void insertarLista(String nombre, String idPiso) {
        try (final Connection connection = ConnectionService.getConnection();
             final PreparedStatement preparedStatement = connection.prepareStatement(INSERTAR_LISTA_QUERY)) {

            preparedStatement.setString(1, nombre);
            preparedStatement.setString(2, idPiso);

            int lineasInsertadas = preparedStatement.executeUpdate();

            if (lineasInsertadas > 0) {
                log.info("Se ha insertado la lista de la compra en la BBDD correctamente");
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static ProductoModelo insertarProducto(String nombre, int idLista) {
        try (final Connection connection = ConnectionService.getConnection();
             final PreparedStatement preparedStatementProducto = connection.prepareStatement(INSERTAR_PRODUCTO_QUERY);
             final PreparedStatement preparedStatementRelacion = connection.prepareStatement(INSERTAR_LISTA_PRODUCTO_QUERY)) {

            preparedStatementProducto.setString(1, nombre);
            int filasInsertadas = preparedStatementProducto.executeUpdate();

            if (filasInsertadas > 0) {
                // Obtener el ID del producto insertado
                try (ResultSet generatedKeys = preparedStatementProducto.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        int idProducto = generatedKeys.getInt(1);

                        preparedStatementRelacion.setInt(1, idLista);
                        preparedStatementRelacion.setInt(2, idProducto);

                        int rowsAffectedRelacion = preparedStatementRelacion.executeUpdate();

                        if (rowsAffectedRelacion > 0) {
                            final ProductoModelo productoModelo = new ProductoModelo();
                            productoModelo.setId(idProducto);
                            productoModelo.setNombre(nombre);
                            System.out.println("Producto agregado y asociado a la lista de compra exitosamente.");
                            return productoModelo;
                        } else {
                            System.out.println("Error al asociar el producto a la lista de compra.");
                        }
                    } else {
                        System.out.println("No se pudo obtener el ID del producto insertado.");
                    }
                }
            } else {
                System.out.println("Error al agregar el producto.");
            }
        } catch (SQLException e) {
            System.out.println("Error al insertar el producto: " + e.getMessage());
            throw new RuntimeException(e);
        }
        return null;
    }


    public static void eliminarProducto(int productoId) {

        try (Connection connection = ConnectionService.getConnection();
             PreparedStatement preparedStatementProducto = connection.prepareStatement(DELETE_PRODUCTO_QUERY);
             PreparedStatement preparedStatementRelacion = connection.prepareStatement(DELETE_RELACION_QUERY)) {

            preparedStatementRelacion.setInt(1, productoId);
            int rowsAffectedRelacion = preparedStatementRelacion.executeUpdate();

            preparedStatementProducto.setInt(1, productoId);
            int rowsAffectedProducto = preparedStatementProducto.executeUpdate();



            if (rowsAffectedProducto > 0) {
                log.info("Producto eliminado exitosamente.");
            } else {
                log.warning("No se encontró ningún producto con el ID proporcionado.");
            }

            if (rowsAffectedRelacion > 0) {
                log.info("Relación de lista-compra-producto eliminada exitosamente.");
            }

        } catch (SQLException e) {
            log.severe("Error al eliminar el producto: " + e.getMessage());
            throw new RuntimeException("Error al eliminar el producto.", e);
        }

    }

}
