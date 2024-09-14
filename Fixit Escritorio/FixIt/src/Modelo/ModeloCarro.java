package Modelo;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;

public class ModeloCarro {

    private String UUID_modelo;
    private String NombreModelo;
    
    public ModeloCarro() {
        
    }
    
    public ModeloCarro(String UUID_modelo, String NombreModelo) {
        this.UUID_modelo = UUID_modelo;
        this.NombreModelo = NombreModelo;
    }

    public String getUUID_modelo() {
        return UUID_modelo;
    }

    public void setUUID_modelo(String UUID_modelo) {
        this.UUID_modelo = UUID_modelo;
    }

    public String getNombreModelo() {
        return NombreModelo;
    }

    public void setNombreModelo(String NombreModelo) {
        this.NombreModelo = NombreModelo;
    }
    
    @Override
    public String toString() {
        return NombreModelo;
    }

    public void CargarComboModelos(JComboBox comboBox){
        Connection conexion = Conexion.getConexion();
        comboBox.removeAllItems();
        try {
            Statement statement = conexion.createStatement();
            ResultSet rs = statement.executeQuery("select UUID_modelo, Nombre from Modelo");
            while(rs.next()){
                String uuid = rs.getString("UUID_modelo");
                String nombre = rs.getString("Nombre");
                comboBox.addItem(new ModeloCarro(uuid, nombre));
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
