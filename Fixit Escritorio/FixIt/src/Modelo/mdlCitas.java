
package Modelo;
import java.sql.*;
import java.util.UUID;

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
    
    //funcion para eliminar citas
    public void Eliminarcita(){
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
    public void ActualizarCitas(){
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
}
