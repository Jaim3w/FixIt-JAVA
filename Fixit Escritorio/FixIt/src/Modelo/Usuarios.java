/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Vistas.frmRegistrarse;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author rdlfp
 */
public class Usuarios {

    private String UUID_usuario;

    //getters y setters
    public String getUUID_usuario() {
        return UUID_usuario;
    }

    public void setUUID_usuario(String UUID_usuario) {
        this.UUID_usuario = UUID_usuario;
    }

    public String getCorreoElectronico() {
        return CorreoElectronico;
    }

    public void setCorreoElectronico(String CorreoElectronico) {
        this.CorreoElectronico = CorreoElectronico;
    }

    public String getContrasena() {
        return Contrasena;
    }

    public void setContrasena(String Contrasena) {
        this.Contrasena = Encriptqacion.encriptar(Contrasena);
    }
    private String CorreoElectronico;
    private String Contrasena;
    private String UUID_rol;

    public String getUUID_rol() {
        return UUID_rol;
    }

    public void setUUID_rol(String UUID_rol) {
        this.UUID_rol = UUID_rol;
    }

   
    //Aqui se insertan los usuarios la base
    public void InsertarUser() {

        Connection conexion = Conexion.getConexion();
        try {

            PreparedStatement addUser = conexion.prepareStatement("Insert into Usuario(UUID_usuario,UUID_rol,CorreoElectronico,Contrasena) values(?,?,?,?)");
            addUser.setString(1, UUID.randomUUID().toString());
            addUser.setString(2, getUUID_rol());
            addUser.setString(3, getCorreoElectronico());
            addUser.setString(4, getContrasena());
            addUser.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Error en el modelo" + e);
        }
    }

    public boolean Verificar() {

        boolean usuarios = false;

        Connection con = Conexion.getConexion();
        try {

            PreparedStatement Verificar = con.prepareStatement("select * from Usuario");
            ResultSet rs = Verificar.executeQuery();

            usuarios = rs.next();
            rs.close();

        } catch (Exception e) {
            e.printStackTrace();
        }

        return usuarios;
    }

    //Aqui se hace el loguin del programa
    public boolean IniciarLogin() {

        Connection conexion = Conexion.getConexion();
        boolean resultado = false;

        try {
            String sql = "Select * from Usuario where CorreoElectronico = ? and Contrasena = ?";
            PreparedStatement statement = conexion.prepareStatement(sql);
            statement.setString(1, getCorreoElectronico());
            statement.setString(2, getContrasena());

            ResultSet resulset = statement.executeQuery();
            if (resulset.next()) {
                resultado = true;
            }

        } catch (SQLException ex) {
            System.out.println("Error en el modelo");
        }

        return resultado;
    }

    //funcion para actualizar la contraseña
    public void ActualizarContrasena(Usuarios usuario) {
        Connection conexion = Conexion.getConexion();
        try {
            PreparedStatement smpt = conexion.prepareStatement("Update Usuario set Contrasena = ? where CorreoElectronico = ?");
            smpt.setString(1, usuario.getContrasena());
            smpt.setString(2, usuario.getCorreoElectronico());
            smpt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Errro al actualizar la contraseña" + e.getMessage());
        }
    }

}
