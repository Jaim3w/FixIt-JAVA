package Modelo;

import java.sql.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JComboBox;

public class Clientes {
    private String UUID_modelo;
    private String NombreModelo;
    private String Dui_cliente;
    private String Nombre;

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
    
    public String getDui_cliente() {
        return Dui_cliente;
    }

    public void setDui_cliente(String Dui_cliente) {
        this.Dui_cliente = Dui_cliente;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }
    
    public Clientes(){
    
    }
    
    public Clientes(String dui,String nombre){
      this.Dui_cliente=dui;
      this.Nombre=nombre;
    }
    
    @Override
    public String toString()
    {
      return Nombre;
    }
    
    public void CargarComboModelos(JComboBox comboBox){
        Connection conexion=Conexion.getConexion();
        comboBox.removeAllItems();
        try{
            Statement statement=conexion.createStatement();
            ResultSet rs=statement.executeQuery("select UUID_modelo, Nombre from Modelo");
            while(rs.next()){
                String uuid=rs.getString("UUID_modelo");
                String nombre=rs.getString("Nombre");
                comboBox.addItem(new Clientes(uuid, nombre));
          }
      
      }catch(SQLException e){
        e.printStackTrace();
      
      }
    }
    
    public void CargarComboClientes(JComboBox comboBox){
        Connection conexion=Conexion.getConexion();
        comboBox.removeAllItems();
      try{
          Statement statement=conexion.createStatement();
          ResultSet rs=statement.executeQuery("select Dui_cliente, Nombre  from Cliente");
          while(rs.next()){
            String dui=rs.getString("Dui_cliente");
            String nombre=rs.getString("Nombre");
            comboBox.addItem(new Clientes(dui, nombre));
          }
      
      }catch(SQLException e){
        e.printStackTrace();
      
      }
    
    }
}
