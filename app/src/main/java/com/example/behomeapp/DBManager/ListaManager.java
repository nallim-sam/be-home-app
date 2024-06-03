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

public class ListaManager {


    private static final String OBTENER_LISTAS_QUERY = "SELECT * " +
            "FROM listacompra " +
            "WHERE id_piso = ?";

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

    public static List<ProductoModelo> obtenerProductosLista(int idLista) {
        final List<ProductoModelo> productoModeloList = new ArrayList<>();

        return productoModeloList;
    }

    public static void insertarLista() {

    }

    public static void insertarProducto() {

    }


}
