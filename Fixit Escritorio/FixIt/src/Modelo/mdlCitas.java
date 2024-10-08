
package Modelo;
import Vistas.frmCitas;
import com.formdev.flatlaf.ui.FlatListCellBorder;
import java.sql.*;
import java.util.UUID;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author rdlfp
 */
public class mdlCitas {
    private String UUID_cita;

    public String getUUID_cita() {
        return UUID_cita;
    }

    public void setUUID_cita(String UUID_cita) {
        this.UUID_cita = UUID_cita;
    }

    public String getDui_cliente() {
        return Dui_cliente;
    }

    public void setDui_cliente(String Dui_cliente) {
        this.Dui_cliente = Dui_cliente;
    }

    public String getDui_empleado() {
        return Dui_empleado;
    }

    public void setDui_empleado(String Dui_empleado) {
        this.Dui_empleado = Dui_empleado;
    }

    public String getFecha_cita() {
        return fecha_cita;
    }

    public void setFecha_cita(String fecha_cita) {
        this.fecha_cita = fecha_cita;
    }

    public String getHora_cita() {
        return Hora_cita;
    }

    public void setHora_cita(String Hora_cita) {
        this.Hora_cita = Hora_cita;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }
    private  String Dui_cliente;
    private String Dui_empleado;
    private String fecha_cita;
    private String Hora_cita;
    private String Descripcion;
    
    
    //Funcion para agregar citas
    
    public void InsertarCitas(){
    Connection conexion=Conexion.getConexion();
        try {
            PreparedStatement addCita=conexion.prepareStatement("insert into Cita(UUID_cita,Dui_cliente,Dui_empleado,Fecha_cita,Hora_cita,Descripcion) values(?,?,?,?,?,?)");
            addCita.setString(1, UUID.randomUUID().toString());
            addCita.setString(2, getDui_cliente());
            addCita.setString(3, getDui_empleado());
            addCita.setNString(4, getFecha_cita());
            addCita.setString(5, getHora_cita());
            addCita.setString(6, getDescripcion());
            addCita.execute();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    
    //funcion para mostrar datos
    
    public void Mostrar(JTable tabla){
    Connection conexion=Conexion.getConexion();
    
        DefaultTableModel modelo=new DefaultTableModel();
        modelo.setColumnIdentifiers(new Object[]{"UUID_cita","Dui_cliente","Dui_empleado","Fecha_cita","Hora_cita","Descripcion"});
        try {
            String query="Select * from Cita";
            Statement statement=conexion.createStatement();
            ResultSet rs=statement.executeQuery(query);
            while (rs.next()) {
                modelo.addRow(new Object[]{rs.getString(UUID_cita),
                rs.getString(Dui_cliente),
                rs.getString(Dui_empleado),
                rs.getString(fecha_cita),
                rs.getString(Hora_cita),
                rs.getString(Descripcion)});  
            }
            
            tabla.setModel(modelo);
        } catch (Exception e) {
            System.out.println("Error en la funcion mostrar "+ e);
        }
    }
    
    //funcion para eliminar citas
    public void Eliminarcita(JTable tabla){
    Connection conexion=Conexion.getConexion();
        try {
            PreparedStatement eliminarCita=conexion.prepareStatement("Delete  from Cita whwere UUID_cita = ?");
            eliminarCita.setString(1,getUUID_cita() );
            eliminarCita.executeUpdate();
        } catch (SQLException e) {
                e.printStackTrace();
        }
    }
    
    //funcion actualizar
    public void ActualizarCitas(JTable tabla){
    Connection conexion=Conexion.getConexion();
        try {
            PreparedStatement actualizarCita=conexion.prepareStatement("update Cita set Fecha_cita =?,Horra_cita = ?, Descripcion = ?");
            actualizarCita.setString(1, getFecha_cita());
            actualizarCita.setString(2,getHora_cita());
            actualizarCita.setString(3, getDescripcion());
            actualizarCita.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void limpiar(frmCitas vista) {
        vista.txtHora.setText("");   
    }
    
    public void CargarDatosTabla(frmCitas vista){
    int filaselecionada = vista.tbCitas.getSelectedRow();
    
    if(filaselecionada != -1){
      String UUIDTB=vista.tbCitas.getValueAt(filaselecionada, 0).toString();
      String DUICLTB=vista.tbCitas.getValueAt(filaselecionada, 1).toString();
      String DUIEMTB=vista.tbCitas.getValueAt(filaselecionada, 2).toString();
      String HORATB=vista.tbCitas.getValueAt(filaselecionada, 3).toString();
      String DESCTB=vista.tbCitas.getValueAt(filaselecionada, 4).toString();
      String FechaTB=vista.tbCitas.getValueAt(filaselecionada, 5).toString();
      
     vista.txtHora.setText(HORATB);
     vista.txtDEsc.setText(DESCTB);
     vista.txtFecha.setText(FechaTB);
      
    }
    }
}
