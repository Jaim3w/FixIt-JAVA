package Controlador;

import Modelo.Clientes;
import Modelo.mdlCarros;
import Vistas.frmCarros;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ctrlCarros implements MouseListener, KeyListener {
    private mdlCarros Modelo;
    private frmCarros Vista;
    private Clientes mClientes;
    
    public ctrlCarros(mdlCarros Modelo, frmCarros Vista, Clientes mClientes) {
        this.Modelo = Modelo;
        this.Vista = Vista;
        this.mClientes = mClientes;
        
        Vista.txtBuscarCarro.addKeyListener(this);
        this.mClientes.CargarComboClientes(Vista.cmbClienteCarro);
        this.mClientes.CargarComboModelos(Vista.cmbModeloCarro);
        
        Modelo.Mostrar(Vista.tbListaCarros);
    }

    @Override
    public void mouseClicked(MouseEvent e) {
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
        if (e.getSource() == Vista.txtBuscarCarro) {
            Modelo.Buscar(Vista.tbListaCarros, Vista.txtBuscarCarro);
        }
    }
}
