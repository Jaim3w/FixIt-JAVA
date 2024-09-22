
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Jaimew
 */
public class Botonprueba extends JButton{
    public Botonprueba() {
        setFocusPainted(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setBorder(new EmptyBorder(10, 20, 10, 20));
        setBackground(new Color(240, 202, 78));
        setForeground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 20, 20);

        FontMetrics fm = g2.getFontMetrics();
        int textoX = (getWidth() - fm.stringWidth(getText())) / 2;
        int textoY = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();

        g2.setColor(getForeground());
        g2.drawString(getText(), textoX, textoY);

        g2.dispose();
    }
}
