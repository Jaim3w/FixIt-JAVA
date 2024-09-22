/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vistas.elemetos;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Jaimew
 */
public class CustomTextField extends JTextField{
    private String hint = "";

    public CustomTextField() {
        setOpaque(true);
        setBorder(new EmptyBorder(10, 10, 10, 10));
        setBackground(Color.BLACK);
        setForeground(Color.WHITE);
    }

    public void setHint(String hint) {
        this.hint = hint;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (getText().isEmpty() && hint != null && !hint.isEmpty()) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            
            g2.setColor(new Color(240, 202, 78));
            FontMetrics fm = g2.getFontMetrics();
            int x = getInsets().left;
            int y = (getHeight() + fm.getAscent()) / 2 - fm.getDescent();
            g2.drawString(hint, x, y);

            g2.dispose();
        }
    }

    @Override
    protected void paintBorder(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        
        g2.setColor(new Color(240, 202, 78));
        g2.setStroke(new BasicStroke(1));
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);

        g2.dispose();
    }

    @Override
    public void updateUI() {
        super.updateUI();
        setBackground(Color.WHITE);
    }
    
}