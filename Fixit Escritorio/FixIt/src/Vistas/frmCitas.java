package Vistas;

import Controlador.CitaDialog;
import Modelo.Conexion;
import Vistas.elemetos.RoundedWhitePanel;
import calendar.model.ModelDate;
import calendar.utils.CalendarSelectedListener;
import java.awt.CardLayout;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.swing.BoxLayout;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class frmCitas extends RoundedWhitePanel {
    
     private List<String> clientes = new ArrayList<>(); 
    private List<String> empleados = new ArrayList<>();
    private CardLayout cardLayout;


    public frmCitas() {
          super(20, 20);
        initComponents();
        loadData();
        cardLayout = new CardLayout();
        jpContenedor.setLayout(cardLayout);
        
        calendar1.addCalendarSelectedListener(new CalendarSelectedListener() {
            @Override
            public void selected(MouseEvent evt, ModelDate date) {
                showAddCitaDialog(date.toDate());
            }
        });
    }
    
    private void loadData() {
        // Cargar clientes
    try (Connection conn = Conexion.getConexion();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT Nombre || ' ' || Apellido AS Cliente FROM Cliente")) {

        while (rs.next()) {
            clientes.add(rs.getString("Cliente"));
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Manejar excepciones de forma adecuada
    }

    // Cargar empleados
    try (Connection conn = Conexion.getConexion();
         Statement stmt = conn.createStatement();
         ResultSet rs = stmt.executeQuery("SELECT Nombre || ' ' || Apellido AS Empleado FROM Empleado")) {

        while (rs.next()) {
            empleados.add(rs.getString("Empleado"));
        }
    } catch (SQLException e) {
        e.printStackTrace(); // Manejar excepciones de forma adecuada
    }
    }
    
    
    private void showAddCitaDialog(java.util.Date selectedDate) {
        CitaDialog dialog = new CitaDialog((Frame) SwingUtilities.getWindowAncestor(this), true, clientes, empleados);
        dialog.setVisible(true);

        dialog.getBtnGuardar().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cliente = (String) dialog.getComboCliente().getSelectedItem();
                String empleado = (String) dialog.getComboEmpleado().getSelectedItem();
                String hora = dialog.getSpinnerHora().getValue().toString();
                String descripcion = dialog.getTextAreaDescripcion().getText();

                // Guardar en la base de datos
                saveCita(cliente, empleado, selectedDate, hora, descripcion);

                dialog.dispose();
            }
        });
    }
    
    private String getDuiFromName(String fullName, boolean isClient) {
    String[] nameParts = fullName.split(" ", 2); 
    String nombre = nameParts[0];
    String apellido = nameParts[1];

    String dui = null;
    String sql = isClient
        ? "SELECT Dui_cliente FROM Cliente WHERE Nombre = ? AND Apellido = ?"
        : "SELECT Dui_empleado FROM Empleado WHERE Nombre = ? AND Apellido = ?";

    try (Connection conn = Conexion.getConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, nombre);
        pstmt.setString(2, apellido);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                dui = isClient ? rs.getString("Dui_cliente") : rs.getString("Dui_empleado");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return dui;
}

    private void saveCita(String cliente, String empleado, java.util.Date date, String hora, String descripcion) {
    String duiCliente = getDuiFromName(cliente, true);
    String duiEmpleado = getDuiFromName(empleado, false);

    if (duiCliente == null || duiEmpleado == null) {
        JOptionPane.showMessageDialog(this, "Cliente o empleado no encontrado.", "Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String uuidCita = UUID.randomUUID().toString();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String fechaCita = sdf.format(date);

    String sql = "INSERT INTO Cita (UUID_cita, Dui_cliente, Dui_empleado, Fecha_cita, Hora_cita, Descripcion) VALUES (?, ?, ?, ?, ?, ?)";

    try (Connection conn = Conexion.getConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, uuidCita);
        pstmt.setString(2, duiCliente);
        pstmt.setString(3, duiEmpleado);
        pstmt.setString(4, fechaCita);
        pstmt.setString(5, hora);
        pstmt.setString(6, descripcion);

        int rowsAffected = pstmt.executeUpdate();
        if (rowsAffected > 0) {
            JOptionPane.showMessageDialog(this, "Cita guardada con éxito.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
            addCitaCard(uuidCita, cliente, empleado, fechaCita, hora, descripcion);
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo guardar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
        }

    } catch (SQLException e) {
        e.printStackTrace(); 
        JOptionPane.showMessageDialog(this, "Error al guardar la cita.", "Error", JOptionPane.ERROR_MESSAGE);
    }
     
    System.out.println("DUI Cliente: " + duiCliente);
System.out.println("DUI Empleado: " + duiEmpleado);
System.out.println("Fecha Cita: " + fechaCita);
System.out.println("Hora Cita: " + hora);
System.out.println("Descripción: " + descripcion);
}

private void addCitaCard(String uuidCita, String cliente, String empleado, String fechaCita, String hora, String descripcion) {
    JPanel cardPanel = new JPanel();
    cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
    
    // Añadir contenido a la tarjeta
    cardPanel.add(new javax.swing.JLabel("Cliente: " + cliente));
    cardPanel.add(new javax.swing.JLabel("Empleado: " + empleado));
    cardPanel.add(new javax.swing.JLabel("Fecha: " + fechaCita));
    cardPanel.add(new javax.swing.JLabel("Hora: " + hora));
    cardPanel.add(new javax.swing.JLabel("Descripción: " + descripcion));

    // Añadir el panel de tarjeta al jpContenedor
    jpContenedor.add(cardPanel, uuidCita);
    cardLayout.show(jpContenedor, uuidCita);
}

private String getDuiFromName(String fullName) {

    String[] nameParts = fullName.split(" ", 2); 
    String nombre = nameParts[0];
    String apellido = nameParts[1];

    String dui = null;

    String sql = "SELECT Dui_cliente FROM Cliente WHERE Nombre = ? AND Apellido = ?";

    try (Connection conn = Conexion.getConexion();
         PreparedStatement pstmt = conn.prepareStatement(sql)) {

        pstmt.setString(1, nombre);
        pstmt.setString(2, apellido);

        try (ResultSet rs = pstmt.executeQuery()) {
            if (rs.next()) {
                dui = rs.getString("Dui_cliente");
            }
        }

    } catch (SQLException e) {
        e.printStackTrace(); 
    }

    return dui;
}

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jpContenedor = new javax.swing.JPanel();
        calendar1 = new calendar.Calendar();

        setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setText("Citas");

        javax.swing.GroupLayout jpContenedorLayout = new javax.swing.GroupLayout(jpContenedor);
        jpContenedor.setLayout(jpContenedorLayout);
        jpContenedorLayout.setHorizontalGroup(
            jpContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 359, Short.MAX_VALUE)
        );
        jpContenedorLayout.setVerticalGroup(
            jpContenedorLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 626, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(179, 179, 179)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jpContenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jpContenedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(calendar1, javax.swing.GroupLayout.PREFERRED_SIZE, 514, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(calendar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    public calendar.Calendar calendar1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    public javax.swing.JPanel jpContenedor;
    // End of variables declaration//GEN-END:variables
}
