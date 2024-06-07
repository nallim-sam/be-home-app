package com.example.behomeapp.DBManager;

import com.example.behomeapp.model.ListaCompraModelo;
import com.example.behomeapp.service.ConnectionService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Logger;

public class ListaCompraManager {

    private static final Logger log = Logger.getLogger(ListaCompraManager.class.getName());
    private static final String DELETE_LISTA_PRODUCTO_QUERY = "DELETE FROM ListaCompra_Producto WHERE id_lista = ?";
    private static final String DELETE_PRODUCTOS_QUERY= "DELETE FROM Producto WHERE id IN (SELECT id_producto FROM ListaCompra_Producto WHERE id_lista = ?)";
    private static final String DELETE_LISTA_QUERY = "DELETE FROM ListaCompra WHERE id = ?";


    public static void eliminarLista(ListaCompraModelo listaCompra) {

        try (final Connection connection = ConnectionService.getConnection();
             final PreparedStatement preparedStatementEliminarRelaciones = connection.prepareStatement(DELETE_LISTA_PRODUCTO_QUERY);
             final PreparedStatement preparedStatementEliminarProductos = connection.prepareStatement(DELETE_PRODUCTOS_QUERY);
             final PreparedStatement preparedStatementEliminarLista = connection.prepareStatement(DELETE_LISTA_QUERY)) {

            preparedStatementEliminarRelaciones.setInt(1, listaCompra.getId());
            int rowsAffectedRelaciones = preparedStatementEliminarRelaciones.executeUpdate();

            preparedStatementEliminarProductos.setInt(1, listaCompra.getId());
            int rowsAffectedProductos = preparedStatementEliminarProductos.executeUpdate();

            preparedStatementEliminarLista.setInt(1, listaCompra.getId());
            int rowsAffectedLista = preparedStatementEliminarLista.executeUpdate();

            if (rowsAffectedLista > 0) {
                log.info("Lista de compras eliminada exitosamente.");
            } else {
                log.warning("No se encontró ninguna lista de compras con el ID proporcionado.");
            }

            if (rowsAffectedProductos > 0) {
                log.info("Productos asociados a la lista eliminados exitosamente.");
            }

            if (rowsAffectedRelaciones > 0) {
                log.info("Relaciones de productos asociados a la lista eliminadas exitosamente.");
            }

        } catch (SQLException e) {
            log.severe("Error al eliminar la lista de compras: " + e.getMessage());
            throw new RuntimeException("Error al eliminar la lista de compras.", e);
        }
    }


}


