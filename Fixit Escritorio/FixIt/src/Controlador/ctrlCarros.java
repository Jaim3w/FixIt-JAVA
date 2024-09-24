package Controlador;

import Modelo.Clientes;
import Modelo.ModeloCarro;
import Modelo.mdlCarros;
import Vistas.frmCarros;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.text.html.ImageView;

public class ctrlCarros implements MouseListener, KeyListener {
    private mdlCarros Modelo;
    private frmCarros Vista;
    private Clientes mClientes;
    private ModeloCarro mModelo;
    
    public ctrlCarros(mdlCarros Modelo, frmCarros Vista, Clientes mClientes, ModeloCarro mModelo) {
        this.Modelo = Modelo;
        this.Vista = Vista;
        this.mClientes = mClientes;
        this.mModelo = mModelo;
        
        Vista.BtnLimpiarcamposCarro.addMouseListener(this);
        Vista.btnActualizarCarro.addMouseListener(this);
        Vista.btnEliminarCarro.addMouseListener(this);
        Vista.btnGuardarCarro.addMouseListener(this);
        Vista.btnSubirImagen.addMouseListener(this);
        Vista.txtBuscarCarro.addKeyListener(this);
        Vista.tbListaCarros.addMouseListener(this);
        
        this.mClientes.CargarComboClientes(Vista.cmbClienteCarro);
        this.mModelo.CargarComboModelos(Vista.cmbModeloCarro);
        
        Vista.cmbClienteCarro.addActionListener(e -> {
            if (e.getSource() == Vista.cmbClienteCarro) {
                System.out.println("ComboBox seleccionado");
                Clientes selectedItem = (Clientes) Vista.cmbClienteCarro.getSelectedItem();
                if (selectedItem != null) {
                    String dui = selectedItem.getDui_cliente();
                    mClientes.setDui_cliente(dui);
                    System.out.println("dui de cliente seleccionado: " + dui);
                } else {
                    System.out.println("No se seleccionó ningún cliente");
                }
            }
        });
        
        Vista.cmbModeloCarro.addActionListener(e -> {
            if (e.getSource() == Vista.cmbModeloCarro) {
                System.out.println("ComboBox seleccionado");
                ModeloCarro selectedItem = (ModeloCarro) Vista.cmbModeloCarro.getSelectedItem();
                if (selectedItem != null) {
                    String uuidModelo = selectedItem.getUUID_modelo();
                    mModelo.setUUID_modelo(uuidModelo);
                    System.out.println("uuid de modelo seleccionado: " + uuidModelo);
                } else {
                    System.out.println("No se seleccionó ningún modelo");
                }
            }
        });
        
        Modelo.Mostrar(Vista.tbListaCarros);
    }
    
    
    private void guardarCarro() {
        System.out.println("Guardar carro");
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() == Vista.btnGuardarCarro) {
            if (Vista.txtPlacaCarro.getText().isEmpty() || Vista.cmbClienteCarro.getSelectedItem() == null || Vista.cmbModeloCarro.getSelectedItem() == null
                     || Vista.txtColorCarro.getText().isEmpty() || Vista.txtAnoCarro.getText().isEmpty() 
                    || Vista.txtIngresoCarro.getText().isEmpty() || Vista.txtDescripcionCarro.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Debes llenar todos los campos", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                System.out.println("Guardando proveedor...");

                    Modelo.setPlaca(Vista.txtPlacaCarro.getText());
                    
                    Clientes clienteSeleccionado = (Clientes) Vista.cmbClienteCarro.getSelectedItem();
                    ModeloCarro modeloSeleccionado = (ModeloCarro) Vista.cmbModeloCarro.getSelectedItem();

                    Modelo.setCliente(clienteSeleccionado.getDui_cliente());
                    Modelo.setModelo(modeloSeleccionado.getUUID_modelo());
                    Modelo.setColor(Vista.txtColorCarro.getText());
                    Modelo.setAnoCarro(Vista.txtAnoCarro.getText());
                    Modelo.setRegistroFecha(Vista.txtIngresoCarro.getText());
                    Modelo.setDescripcion(Vista.txtDescripcionCarro.getText());

                    Modelo.Guardar();
                    Modelo.Mostrar(Vista.tbListaCarros);
                    
                    
                System.out.println("Carro guardado correctamente.");

                    
                    
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Vista, "La edad debe ser un número", "Error", JOptionPane.WARNING_MESSAGE);
                    System.out.println("Error al guardar el vehiculo: " + ex.getMessage());

                }
                
            }
        }
        
        if (e.getSource() == Vista.btnSubirImagen) {
    // Crear el JFileChooser para seleccionar un archivo
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setDialogTitle("Seleccionar Imagen");

    // Abrir el diálogo para seleccionar una imagen
    int result = fileChooser.showOpenDialog(Vista);

    if (result == JFileChooser.APPROVE_OPTION) {
        // Obtener el archivo seleccionado
        File imagenSeleccionada = fileChooser.getSelectedFile();

        // Establecer la imagen seleccionada en el modelo
        Modelo.setImagenSeleccionada(imagenSeleccionada);

        // Mostrar la imagen en el JLabel lblImagenCarro
        mostrarImagenEnLabel(imagenSeleccionada);

        // Mostrar el nombre de la imagen seleccionada
        JOptionPane.showMessageDialog(Vista, "Imagen seleccionada: " + imagenSeleccionada.getName());
    } else {
        JOptionPane.showMessageDialog(Vista, "No se seleccionó ninguna imagen.");
    }
}
        
        if (e.getSource() == Vista.btnActualizarCarro) {
            if (Vista.txtPlacaCarro.getText().isEmpty() || Vista.cmbClienteCarro.getSelectedItem() == null || Vista.cmbModeloCarro.getSelectedItem() == null
                     || Vista.txtColorCarro.getText().isEmpty() || Vista.txtAnoCarro.getText().isEmpty() 
                    || Vista.txtIngresoCarro.getText().isEmpty() || Vista.txtDescripcionCarro.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Debes seleccionar un registro para actualizar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Modelo.setPlaca(Vista.txtPlacaCarro.getText());
                    
                    Clientes clienteSeleccionado = (Clientes) Vista.cmbClienteCarro.getSelectedItem();
                    ModeloCarro modeloSeleccionado = (ModeloCarro) Vista.cmbModeloCarro.getSelectedItem();

                    Modelo.setCliente(clienteSeleccionado.getDui_cliente());
                    Modelo.setModelo(modeloSeleccionado.getUUID_modelo());
                    Modelo.setColor(Vista.txtColorCarro.getText());
                    Modelo.setAnoCarro(Vista.txtAnoCarro.getText());
                    Modelo.setRegistroFecha(Vista.txtIngresoCarro.getText());
                    Modelo.setDescripcion(Vista.txtDescripcionCarro.getText());
                    
                    Modelo.Actualizar(Vista.tbListaCarros);
                    Modelo.Mostrar(Vista.tbListaCarros);
                    Modelo.limpiar(Vista);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(Vista, "La edad debe ser un número", "Error", JOptionPane.WARNING_MESSAGE);
                }
            }
        }
        
        if (e.getSource() == Vista.btnEliminarCarro) {
            if (Vista.txtPlacaCarro.getText().isEmpty() || Vista.cmbClienteCarro.getSelectedItem() == null || Vista.cmbModeloCarro.getSelectedItem() == null
                     || Vista.txtColorCarro.getText().isEmpty() || Vista.txtAnoCarro.getText().isEmpty() 
                    || Vista.txtIngresoCarro.getText().isEmpty() || Vista.txtDescripcionCarro.getText().isEmpty()) {
                JOptionPane.showMessageDialog(Vista, "Debes seleccionar un registro para eliminar", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Modelo.Eliminar(Vista.tbListaCarros);
                Modelo.Mostrar(Vista.tbListaCarros);
                Modelo.limpiar(Vista);
            }
       }
        
        if (e.getSource() == Vista.BtnLimpiarcamposCarro) {
            Modelo.limpiar(Vista);
        }

        if (e.getSource() == Vista.tbListaCarros) {
            Modelo.cargarDatosTabla(Vista);
        }
    }
    
    private void mostrarImagenEnLabel(File imagen) {
        try {
            // Leer la imagen desde el archivo
            BufferedImage bufferedImage = ImageIO.read(imagen);

            // Crear el ImageIcon a partir de la imagen
            ImageIcon icon = new ImageIcon(bufferedImage);

            // Redimensionar la imagen para que se ajuste al JLabel
            Image imageScaled = icon.getImage().getScaledInstance(Vista.lblImagenCarro.getWidth(),
            Vista.lblImagenCarro.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(imageScaled);

            // Establecer la imagen en el JLabel
            Vista.lblImagenCarro.setIcon(scaledIcon);

        } catch (IOException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(Vista, "Error al cargar la imagen.");
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
        if (e.getSource() == Vista.txtBuscarCarro) {
            Modelo.Buscar(Vista.tbListaCarros, Vista.txtBuscarCarro);
        }
    }
}
