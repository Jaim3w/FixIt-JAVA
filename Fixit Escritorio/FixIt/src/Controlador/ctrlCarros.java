package Controlador;

import Modelo.mdlCarros;
import Vistas.frmCarros;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class ctrlCarros implements MouseListener, KeyListener {
    private mdlCarros Modelo;
    private frmCarros Vista;
    
    public ctrlCarros(mdlCarros Modelo, frmCarros Vista) {
        this.Modelo = Modelo;
        this.Vista = Vista;
        
        Vista.txtBuscarCarro.addKeyListener(this);
        
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
