package Controlador;

import Modelo.Credenciales;
import Modelo.EnviarCorreo;
import Vistas.EnviarcorreoE;
import Vistas.NuevaContra;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class controladorCorreo implements MouseListener {

    static int numeroAle;

    private Credenciales modelo;
    private EnviarcorreoE vista;

    public controladorCorreo(Credenciales modelo, EnviarcorreoE vista) {
        this.modelo = modelo;
        this.vista = vista;

        if (vista.btnenviar == null) {
            System.out.println("btnenviar es null");
        } else {
            vista.btnenviar.addMouseListener(this);
            System.out.println("MouseListener agregado a btnenviar");
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == vista.btnenviar) {
            System.out.println("btnenviar clickeado");
            
            // Generar un código aleatorio de 4 dígitos
            Random random = new Random();
            int numeroAle = 1000 + random.nextInt(9000);

            String recipient = vista.txtCorreo.getText();
            String subject = "Recuperación de contraseña";
            String content = "Este es el código de verificación: " + numeroAle;

            // Enviar correo
            EnviarCorreo.enviarCorreo(recipient, subject, content);
            System.out.println("Correo enviado a: " + recipient);

           Vistas.NuevaContra.initNuevaContras();
            vista.dispose();
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println("mousePressed triggered");
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.println("mouseReleased triggered");
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouseEntered triggered");
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.println("mouseExited triggered");
    }
}