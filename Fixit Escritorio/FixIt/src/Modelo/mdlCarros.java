package Modelo;

import Vistas.frmCarros;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.json.JSONObject;

public class mdlCarros {
    private String placa;
    private String cliente;
    private String modelo;
    private String color;
    private String anoCarro;
    private String registroFecha;
    private String descripcion;
    private File imagenSeleccionada;

    public String getImagenUrl() {
        return imagenUrl;
    }

    public void setImagenUrl(String imagenUrl) {
        this.imagenUrl = imagenUrl;
    }
    
    private String imagenUrl;

    public String getImgCarroUrl() {
        return imgCarroUrl;
    }

    public void setImgCarroUrl(String imgCarroUrl) {
        this.imgCarroUrl = imgCarroUrl;
    }
    private String imgCarroUrl;

    public void setImagenSeleccionada(File imagen) {
        this.imagenSeleccionada = imagen;
    }

    public File getImagenSeleccionada() {
        return this.imagenSeleccionada;
    }
    
    private String subirImagenImgur(File imageFile) throws IOException {
    // Cargar la imagen y convertirla en Base64
    byte[] fileContent = Files.readAllBytes(imageFile.toPath());
    String encodedImage = Base64.getEncoder().encodeToString(fileContent);

    // URL de la API de Imgur
    String uploadUrl = "https://api.imgur.com/3/image";

    // Crear un cliente HTTP
    CloseableHttpClient httpClient = HttpClients.createDefault();
    HttpPost uploadFile = new HttpPost(uploadUrl);

    // Configurar las cabeceras para autenticar la API de Imgur
    uploadFile.addHeader("Authorization", "Client-ID 8fade595e9f4606");

    // Crear el JSON para el body de la petición
    JSONObject json = new JSONObject();
    json.put("image", encodedImage);

    // Establecer el JSON como entidad de la petición
    StringEntity entity = new StringEntity(json.toString());
    uploadFile.setEntity(entity);
    uploadFile.addHeader("Content-Type", "application/json");

    // Declarar la respuesta
    CloseableHttpResponse response = null;

    try {
        // Ejecutar la solicitud de subida
        response = httpClient.execute(uploadFile);
        
        // Convertir la entidad de la respuesta a una cadena JSON
        String jsonResponse = EntityUtils.toString(response.getEntity());

        // Analizar la respuesta JSON para obtener la URL de la imagen
        JSONObject responseObject = new JSONObject(jsonResponse);
        String uploadedUrl = responseObject.getJSONObject("data").getString("link");

        return uploadedUrl;
    } catch (ParseException e) {
        e.printStackTrace();
        throw new IOException("Error al parsear la respuesta de la imagen: " + e.getMessage());
    } catch (IOException e) {
        e.printStackTrace();
        throw new IOException("Error de entrada/salida: " + e.getMessage());
    } finally {
        if (response != null) {
            response.close();
        }
        httpClient.close();
        }
    }
   
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
    
    public void Guardar() {
        Connection conexion = Conexion.getConexion();

        try {
            if (imagenSeleccionada != null) {
                setImgCarroUrl(subirImagenImgur(imagenSeleccionada)); // Usar la imagen seleccionada
            } else {
                JOptionPane.showMessageDialog(null, "Por favor selecciona una imagen antes de guardar.");
                return; // Salir si no hay imagen seleccionada
            }

            String sql = "INSERT INTO Carro (Placa_carro, Dui_cliente, UUID_modelo, Color, Ano, ImagenCarro, FechaRegistro, Descripcion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conexion.prepareStatement(sql);
            pstmt.setString(1, getPlaca());
            pstmt.setString(2, getCliente());
            pstmt.setString(3, getModelo());
            pstmt.setString(4, getColor());
            pstmt.setString(5, getAnoCarro());
            pstmt.setString(6, getImgCarroUrl());
            pstmt.setString(7, getRegistroFecha());
            pstmt.setString(8, getDescripcion());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error al guardar el carro: " + e.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(mdlCarros.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void Buscar(JTable tabla, JTextField miTextField) {
        Connection conexion = Conexion.getConexion();

        DefaultTableModel modelo = new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"Placas", "Cliente", "Modelo", "Color", "Año", "Imagen", "Fecha de Registro", "Descripcion"});
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
                    rs.getString("NombreCliente"),
                    rs.getString("NombreModelo"),
                    rs.getString("Color"), 
                    rs.getString("Ano"), 
                    rs.getString("FechaRegistro"), 
                    rs.getString("Descripcion"), 
                    rs.getString("ImagenCarro"), 
                });
            }

            tabla.setModel(modelo);
        } catch (Exception e) {
            System.out.println("Este es el error en el modelo, metodo de buscar " + e);
        }
    }
    
    public void Actualizar(JTable tabla) {
        Connection conexion = Conexion.getConexion();
        int filaSeleccionada = tabla.getSelectedRow();
        if (filaSeleccionada != -1) {
            String Placas = tabla.getValueAt(filaSeleccionada, 0).toString();
            try {
                String sql = "update Carro set Color = ?, Ano = ?, FechaRegistro = ?, Descripcion = ?, NombreModelo = ?, NombreCliente = ?  where Placa_carro = ?";
                PreparedStatement updateCarro = conexion.prepareStatement(sql);

               updateCarro.setString(1, getCliente());
               updateCarro.setString(2, getModelo());
               updateCarro.setString(3, getColor());
               updateCarro.setString(4, getAnoCarro());
               updateCarro.setString(5, getRegistroFecha());
               updateCarro.setString(6, getDescripcion());
               updateCarro.setString(7, Placas);
               updateCarro.executeUpdate();
            } catch (Exception e) {
                System.out.println("este es el error en el metodo de actualizar" + e);
            }
        } 
    }
    
    public void Eliminar(JTable tabla) {
        Connection conexion = Conexion.getConexion();

        int filaSeleccionada = tabla.getSelectedRow();

        String miId = tabla.getValueAt(filaSeleccionada, 0).toString();
        try {
            String sql = "delete from Carro where Placa_carro = ?";
            PreparedStatement deleteCarro = conexion.prepareStatement(sql);
            deleteCarro.setString(1, miId);
            deleteCarro.executeUpdate();
        } catch (Exception e) {
            System.out.println("este es el error metodo de eliminar" + e);
        }
    }
    
    public void limpiar(frmCarros vista) {
        vista.txtPlacaCarro.setText("");
        vista.cmbClienteCarro.setSelectedItem("");
        vista.cmbModeloCarro.setSelectedItem("");
        vista.txtColorCarro.setText("");
        vista.txtAnoCarro.setText("");
        vista.txtIngresoCarro.setText("");
        vista.txtDescripcionCarro.setText("");
        vista.lblImagenCarro.setIcon(null);
    }
   
    public void Mostrar(JTable tabla) {
    Connection conexion = Conexion.getConexion();
    DefaultTableModel modelo = new DefaultTableModel();
    modelo.setColumnIdentifiers(new Object[]{"Placas", "Cliente", "Modelo", "Color", "Año", "Fecha de Registro", "Descripcion", "Imagen"});
    
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
                rs.getString("NombreCliente"),
                rs.getString("NombreModelo"),
                rs.getString("Color"), 
                rs.getString("Ano"), 
                rs.getString("FechaRegistro"), 
                rs.getString("Descripcion"), 
                rs.getString("ImagenCarro"), 
                
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
            String imagenUrlTb = vista.tbListaCarros.getValueAt(filaSeleccionada, 7).toString();
            
            vista.txtPlacaCarro.setText(PlacaTb);
            vista.cmbClienteCarro.setSelectedItem(ClienteTb);
            vista.cmbModeloCarro.setSelectedItem(ModeloTb);
            vista.txtColorCarro.setText(ColorTb);
            vista.txtAnoCarro.setText(AnoTb);
            vista.txtIngresoCarro.setText(IngresoTb);
            vista.txtDescripcionCarro.setText(DescripcionTb);
            vista.lblImagenCarro.setText(imagenUrlTb);
        }
    }
}
