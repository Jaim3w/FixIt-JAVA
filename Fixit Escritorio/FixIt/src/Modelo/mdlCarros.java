package Modelo;

import Vistas.frmCarros;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

public class mdlCarros {
    private String placa;
    private String cliente;
    private String modelo;
    private String color;
    private String anoCarro;
    private String registroFecha;
    private String descripcion;
   
    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAnoCarro() {
        return anoCarro;
    }

    public void setAnoCarro(String anoCarro) {
        this.anoCarro = anoCarro;
    }

    public String getRegistroFecha() {
        return registroFecha;
    }

    public void setRegistroFecha(String registroFecha) {
        this.registroFecha = registroFecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public void Buscar(JTable tabla, JTextField miTextField) {
        Connection conexion = Conexion.getConexion();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Placas", "Color", "Año", "Imagen", "Fecha de Registro", "Descripcion", "Modelo", "Cliente"});
        try {
            String sql = "SELECT " +
                       "c.Placa_carro, " +
                       "c.Color, " +
                       "c.Ano, " +
                       "c.ImagenCarro, " +
                       "c.FechaRegistro, " +
                       "c.Descripcion, " +
                       "m.Nombre AS NombreModelo, " +
                       "cl.Nombre AS NombreCliente " +
                       "FROM Carro c " +
                       "INNER JOIN Modelo m ON c.UUID_modelo = m.UUID_modelo " +
                       "INNER JOIN Cliente cl ON c.Dui_cliente = cl.Dui_cliente WHERE Placa_carro LIKE ? || '%'";
            PreparedStatement buscarCarro = conexion.prepareStatement(sql);
            buscarCarro.setString(1, miTextField.getText());
            ResultSet rs = buscarCarro.executeQuery();

            while (rs.next()) {
                modelo.addRow(new Object[]{
                    rs.getString("Placa_carro"), 
                    rs.getString("Color"), 
                    rs.getString("Ano"), 
                    rs.getString("ImagenCarro"), 
                    rs.getString("FechaRegistro"), 
                    rs.getString("Descripcion"), 
                    rs.getString("NombreModelo"), 
                    rs.getString("NombreCliente")
                });
            }

            tabla.setModel(modelo);
            //tabla.getColumnModel().getColumn(0).setMinWidth(0);
            //tabla.getColumnModel().getColumn(0).setMaxWidth(0);
            //tabla.getColumnModel().getColumn(0).setWidth(0);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo de buscar " + e);
        }
    }
   
    public void Mostrar(JTable tabla) {
    Connection conexion = Conexion.getConexion();
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new Object[]{"Placas", "Color", "Año", "Imagen", "Fecha de Registro", "Descripcion", "Modelo", "Cliente"});
    
    try {
        // Consulta a ejecutar
        String query = "SELECT " +
                       "c.Placa_carro, " +
                       "c.Color, " +
                       "c.Ano, " +
                       "c.ImagenCarro, " +
                       "c.FechaRegistro, " +
                       "c.Descripcion, " +
                       "m.Nombre AS NombreModelo, " +
                       "cl.Nombre AS NombreCliente " +
                       "FROM Carro c " +
                       "INNER JOIN Modelo m ON c.UUID_modelo = m.UUID_modelo " +
                       "INNER JOIN Cliente cl ON c.Dui_cliente = cl.Dui_cliente";
        
        Statement statement = conexion.createStatement();
        ResultSet rs = statement.executeQuery(query);
        
        while (rs.next()) {
            modelo.addRow(new Object[]{
                rs.getString("Placa_carro"), 
                rs.getString("Color"), 
                rs.getString("Ano"), 
                rs.getString("ImagenCarro"), 
                rs.getString("FechaRegistro"), 
                rs.getString("Descripcion"), 
                rs.getString("NombreModelo"), 
                rs.getString("NombreCliente")
            });
        }
        tabla.setModel(modelo);
        
    } catch (Exception e) {
        System.out.println("Este es el error en el modelo, método mostrar: " + e);
    }
}

    
    public void cargarDatosTabla (frmCarros vista) {
        int filaSeleccionada = vista.tbListaCarros.getSelectedRow();
        
        if (filaSeleccionada != -1) {
            String PlacaTb = vista.tbListaCarros.getValueAt(filaSeleccionada, 0).toString();
            String ClienteTb = vista.tbListaCarros.getValueAt(filaSeleccionada, 1).toString();
            String ModeloTb = vista.tbListaCarros.getValueAt(filaSeleccionada, 2).toString();
            String ColorTb = vista.tbListaCarros.getValueAt(filaSeleccionada, 3).toString();
            String AnoTb = vista.tbListaCarros.getValueAt(filaSeleccionada, 4).toString();
            String IngresoTb = vista.tbListaCarros.getValueAt(filaSeleccionada, 5).toString();
            String DescripcionTb = vista.tbListaCarros.getValueAt(filaSeleccionada, 6).toString();
            
            vista.txtPlacaCarro.setText(PlacaTb);
            vista.cmbClienteCarro.setSelectedItem(ClienteTb);
            vista.cmbModeloCarro.setSelectedItem(ModeloTb);
            vista.txtColorCarro.setText(ColorTb);
            vista.txtAnoCarro.setText(AnoTb);
            vista.txtIngresoCarro.setText(IngresoTb);
            vista.txtDescripcionCarro.setText(DescripcionTb);
        }
    }
}
