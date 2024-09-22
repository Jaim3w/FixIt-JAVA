package main;

import Vistas.dashboard;
import com.formdev.flatlaf.FlatLightLaf;


public class main {

    
    public static void main(String[] args) {
        FlatLightLaf.install();

        java.awt.EventQueue.invokeLater(() -> {
            new dashboard().setVisible(true);
        });
    }
}
