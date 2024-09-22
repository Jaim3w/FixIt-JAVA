package Controlador;

import Modelo.mdlProveedor;
import Vistas.frmProveedor;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;


public class ctrlProveedor implements MouseListener, KeyListener{
    private mdlProveedor Modelo;
    private frmProveedor Vista;
    
    public ctrlProveedor(mdlProveedor Modelo, frmProveedor Vista){
        
        this.Modelo = Modelo;
        this.Vista = Vista;
     

        
        Vista.btnGuardarProveedor.addMouseListener(this);
        Vista.btnEliminarProveedor.addMouseListener(this);
        Vista.btnActualizarProveedor.addMouseListener(this);
        Vista.btnLimpiarCamposProveedor.addMouseListener(this);
        Vista.txtBuscarProveedor.addKeyListener(this);
        Vista.tbProveedores.addMouseListener(this);
        Modelo.Mostrar(Vista.tbProveedores);
          
    }
        

    @Override
    public void mouseClicked(MouseEvent e) {
        
        if (e.getSource() == Vista.btnGuardarProveedor) {
            if (Vista.txtDui.getText().isEmpty() || Vista.txtNombre.getText().isEmpty() || Vista.txtApellido.getText().isEmpty()
                     || Vista.txtTelefono.getText().isEmpty() || Vista.txtCorreo.getText().isEmpty() 
                    || Vista.txtDireccion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Debes llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    //Asignar lo de la vista al modelo
                    Modelo.setDui(Vista.txtDui.getText());
                    Modelo.setNombre(Vista.txtNombre.getText());
                    Modelo.setApellido(Vista.txtApellido.getText());
                    Modelo.setTelefono(Vista.txtTelefono.getText());
                    Modelo.setCorreo(Vista.txtCorreo.getText());
                    Modelo.setDireccion(Vista.txtDireccion.getText());

                    //Ejecutar el metodo 
                    Modelo.Guardar();
                    Modelo.Mostrar(Vista.tbProveedores);
                    //Modelo.limpiar(Vista);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Vista, "La edad debe ser un número", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        
        
        if (e.getSource() == Vista.btnEliminarProveedor) {
            if (Vista.txtDui.getText().isEmpty() || Vista.txtNombre.getText().isEmpty() || Vista.txtApellido.getText().isEmpty()
                     || Vista.txtTelefono.getText().isEmpty() || Vista.txtCorreo.getText().isEmpty() 
                    || Vista.txtDireccion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Debes seleccionar un registro para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Modelo.Eliminar(Vista.tbProveedores);
                Modelo.Mostrar(Vista.tbProveedores);
                Modelo.limpiar(Vista);
            }
        }
        
        if (e.getSource() == Vista.btnActualizarProveedor) {
            if (Vista.txtDui.getText().isEmpty() || Vista.txtNombre.getText().isEmpty() || Vista.txtApellido.getText().isEmpty()
                     || Vista.txtTelefono.getText().isEmpty() || Vista.txtCorreo.getText().isEmpty() 
                    || Vista.txtDireccion.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Debes seleccionar un registro para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    //Asignar lo de la vista al modelo al momento de darle clic a actualizar
                    Modelo.setDui(Vista.txtDui.getText());
                    Modelo.setNombre(Vista.txtNombre.getText());
                    Modelo.setApellido(Vista.txtApellido.getText());
                    Modelo.setTelefono(Vista.txtTelefono.getText());
                    Modelo.setCorreo(Vista.txtCorreo.getText());
                    Modelo.setDireccion(Vista.txtDireccion.getText());


                    //Ejecutar el método    
                    Modelo.Actualizar(Vista.tbProveedores);
                    Modelo.Mostrar(Vista.tbProveedores);
                    Modelo.limpiar(Vista);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Vista, "La edad debe ser un número", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        
         if (e.getSource() == Vista.btnLimpiarCamposProveedor) {
            Modelo.limpiar(Vista);
        }

        if (e.getSource() == Vista.tbProveedores) {
            Modelo.cargarDatosTabla(Vista);
        }
        
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource() == Vista.txtBuscarProveedor) {
            Modelo.Buscar(Vista.tbProveedores, Vista.txtBuscarProveedor);
        }
    }
    
    
}