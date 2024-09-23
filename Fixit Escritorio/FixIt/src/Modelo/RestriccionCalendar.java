/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import com.toedter.calendar.JCalendar;
import javax.swing.*;
import java.awt.*;
import java.util.Calendar;


public class RestriccionCalendar extends JCalendar{
    public RestriccionCalendar() {
        super();
        setDate(Calendar.getInstance().getTime());
        setMaxSelectableDate(getDate());
        setMinSelectableDate(getDate());
        // Ocultar los botones de mes y año
        hideNavigationButtons();
        setCurrentMonthAndYear();
    }

    private void setCurrentMonthAndYear() {
        // Establecer el calendario para que muestre solo el mes y año actuales
        Calendar calendar = Calendar.getInstance();
        setDate(calendar.getTime());
        
        // Ocultar botones de cambio de mes y año
        JPanel monthPanel = (JPanel) getComponent(1); // Accede al panel del mes
        monthPanel.getComponent(0).setVisible(false); // Botón anterior
        monthPanel.getComponent(1).setVisible(false); // Botón siguiente
        monthPanel.getComponent(2).setVisible(false); // Selector de mes
        monthPanel.getComponent(3).setVisible(false); // Selector de año
    }
    private void hideNavigationButtons() {
        JPanel monthPanel = (JPanel) getComponent(1);
        for (Component component : monthPanel.getComponents()) {
            if (component instanceof JButton) {
                component.setVisible(false);
            }
        }
    }
}
