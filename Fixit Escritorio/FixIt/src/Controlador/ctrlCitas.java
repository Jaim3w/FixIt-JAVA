
package Controlador;

import Modelo.Clientes;
import Modelo.Empleados;
import Modelo.mdlCitas;
import Vistas.frmCitas;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;

/**
 *
 * @author rdlfp
 */
public class ctrlCitas implements MouseListener,KeyListener{
    private mdlCitas modelito;
    private frmCitas vista;
    private Clientes modeloCliente;
    private Empleados modeloEmpleado;
    
    public ctrlCitas(mdlCitas modelo,frmCitas vistas,Clientes Modelo,Empleados ModeloEm){
    this.modelito=modelo;
    this.vista=vistas;
    this.modeloCliente=Modelo;
    this.modeloEmpleado=ModeloEm;
    
    vistas.btnAddCita.addMouseListener(this);
    vistas.btnActualizar.addMouseListener(this);
    vistas.btnEliminar.addMouseListener(this);
    Modelo.CargarCombo(vistas.cmbCliente);
    ModeloEm.Cargarcombo(vistas.cmbEmpleado);
    
    //Se manda a llamar los combos
    vistas.cmbCliente.addActionListener(e ->{
        if (e.getSource() == vistas.cmbCliente) {
            System.out.println("Combobox seleccionado");
            Clientes selectedItem=(Clientes) vistas.cmbCliente.getSelectedItem();
            if (selectedItem != null) {
                String UUID=selectedItem.getDui_cliente();
                Modelo.setDui_cliente(UUID);
                System.out.println("Cliente seleccionado UUID: " + UUID);
            }
            else{
                System.err.println("No se seleccionó ningún Cliente");
            }
        }
    });
    
    vistas.cmbEmpleado.addActionListener(e ->{
        if (e.getSource() == vistas.cmbEmpleado) {
            System.out.println("Combobox seleccionado");
            Empleados selectedItem=(Empleados) vistas.cmbEmpleado.getSelectedItem();
            if (selectedItem != null) {
                String UUID=selectedItem.getDui_empleado();
                ModeloEm.setDui_empleado(UUID);
                System.out.println("Empleado seleccionado: "+ UUID);
            }
            else{
                System.out.println("No se selecciono ningun empleado");
            }
        }
    });
    } 

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.btnAddCita) {
            try {
                System.out.println("Cita guardad");
                modelito.setDui_empleado(modeloEmpleado.getDui_empleado());
                modelito.setDui_cliente(modeloCliente.getDui_cliente());
                modelito.setHora_cita(vista.txtHora.getText());
                modelito.setDescripcion(vista.txtDEsc.getText());
                modelito.setFecha_cita(vista.txtFecha.getText());
                
            modelito.InsertarCitas();
            modelito.Mostrar(vista.tbCitas);
                            JOptionPane.showMessageDialog(null, "Cita registrada con éxito", "Cita registrada", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al registrar cita: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
  
        }
        
        if (e.getSource() == vista.btnEliminar) {
            modelito.Eliminarcita(vista.tbCitas);
            modelito.Mostrar(vista.tbCitas);
        }
        
        if (e.getSource() == vista.btnActualizar) {
            try {
                modelito.setHora_cita(vista.txtHora.getText());
                modelito.setDescripcion(vista.txtDEsc.getText());
                modelito.setFecha_cita(vista.txtFecha.getText());
                
                modelito.ActualizarCitas(vista.tbCitas);
                modelito.Mostrar(vista.tbCitas);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(vista, "No se pudo actualizar la cita", "Error", JOptionPane.WARNING_MESSAGE);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    }
}
