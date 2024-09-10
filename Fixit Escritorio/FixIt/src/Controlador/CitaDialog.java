package Controlador;

import javax.swing.*;
import java.awt.*;

public class CitaDialog extends JDialog {
    private JComboBox<String> comboCliente;
    private JComboBox<String> comboEmpleado;
    private JSpinner spinnerHora;
    private JTextArea textAreaDescripcion;
    private JButton btnGuardar;

    public CitaDialog(Frame parent, boolean modal, java.util.List<String> clientes, java.util.List<String> empleados) {
        super(parent, "Agregar Cita", modal);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10)); 

        panel.add(new JLabel("Cliente:"));
        comboCliente = new JComboBox<>(clientes.toArray(new String[0]));
        panel.add(comboCliente);

        panel.add(new JLabel("Empleado:"));
        comboEmpleado = new JComboBox<>(empleados.toArray(new String[0]));
        panel.add(comboEmpleado);

        panel.add(new JLabel("Hora:"));
        spinnerHora = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor editor = new JSpinner.DateEditor(spinnerHora, "HH:mm");
        spinnerHora.setEditor(editor);
        panel.add(spinnerHora);

        panel.add(new JLabel("Descripción:"));
        textAreaDescripcion = new JTextArea(3, 20);
        JScrollPane scrollPane = new JScrollPane(textAreaDescripcion);
        panel.add(scrollPane);

        btnGuardar = new JButton("Guardar");
        panel.add(btnGuardar);

        add(panel, BorderLayout.CENTER);
        setSize(400, 300); // Tamaño ajustado
        setLocationRelativeTo(parent);
    }

    public JButton getBtnGuardar() {
        return btnGuardar;
    }

    public JComboBox<String> getComboCliente() {
        return comboCliente;
    }

    public JComboBox<String> getComboEmpleado() {
        return comboEmpleado;
    }

    public JSpinner getSpinnerHora() {
        return spinnerHora;
    }

    public JTextArea getTextAreaDescripcion() {
        return textAreaDescripcion;
    }
}

