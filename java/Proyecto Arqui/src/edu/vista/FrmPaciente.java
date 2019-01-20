/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.vista;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import com.reportes.Reportes;
import edu.dao.DaoConsulta;
import edu.dao.DaoPaciente;
import edu.modelo.Consulta;
import edu.modelo.Paciente;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import jssc.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import net.sf.jasperreports.engine.JRException;
import proyecto.arqui.rxSimple;

/**
 *
 * @author DTO
 */
public class FrmPaciente extends javax.swing.JFrame {

    static PanamaHitek_Arduino ino = new PanamaHitek_Arduino();
    public static String a;

    public FrmPaciente() {

        initComponents();
        tablaPaciente();
        tablaConsulta();

    }

    DefaultTableModel tabla;
    DefaultTableModel tabla2;
    Paciente pa = new Paciente();
    Consulta con = new Consulta();
    DaoPaciente daoP = new DaoPaciente();
    DaoConsulta daoC = new DaoConsulta();
    static SerialPortEventListener listener = new SerialPortEventListener() {
        @Override
        //Si se recibe algun dato en el puerto serie, se ejecuta el siguiente metodo
        public void serialEvent(SerialPortEvent serialPortEvent) {
            try {
                /*
                Los datos en el puerto serie se envian caracter por caracter. Si se
                desea esperar a terminar de recibir el mensaje antes de imprimirlo, 
                el metodo isMessageAvailable() devolvera TRUE cuando se haya terminado
                de recibir el mensaje, el cual podra ser impreso a traves del metodo
                printMessage()
                 */
                if (ino.isMessageAvailable()) {
                    //Se le asigna el mensaje recibido a la variable msg
                    String msg = ino.printMessage();
                    //Se imprime la variable msg
                    a = msg;
                    FrmPaciente pa = new FrmPaciente();
                    pa.txtBuscado.setText(a);

                }
            } catch (SerialPortException ex) {
                Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ArduinoException ex) {
                Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    };
    

    public void tablaPaciente() {
        String[] columnas = {"ID", "Codigo", "Nombre", "Apellido", "Edad", "Genero"};
        Object[] obj = new Object[6];
        tabla = new DefaultTableModel(null, columnas);
        List ls;

        try {
            ls = daoP.mostrarPaciente();
            for (int i = 0; i < ls.size(); i++) {
                pa = (Paciente) ls.get(i);
                obj[0] = pa.getIdPaciente();
                obj[1] = pa.getCodigoPaciente();
                obj[2] = pa.getNombre();
                obj[3] = pa.getApellido();
                obj[4] = pa.getEdad();
                obj[5] = pa.getGenero();
                tabla.addRow(obj);
            }
            ls = daoP.mostrarPaciente();
            this.JtbPaciente.setModel(tabla);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar datos" + e.toString());
        }
    }
    
     public void tablaConsulta() {
        String[] columnas = {"ID", "Codigo_Pac", "Fecha", "Hora", "Descripcion"};
        Object[] obj = new Object[5];
        tabla2 = new DefaultTableModel(null, columnas);
        List ls;

        try {
            ls = daoC.mostrarConsulta();
            for (int i = 0; i < ls.size(); i++) {
                con = (Consulta) ls.get(i);
                obj[0] = con.getIdConsulta();
                obj[1] = con.getIdPaciente();
                obj[2] = con.getFecha();
                obj[3] = con.getHora();
                obj[4] = con.getDescripcion();
                tabla2.addRow(obj);
            }
            ls = daoC.mostrarConsulta();
            this.JtbConsulta.setModel(tabla2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar datos" + e.toString());
        }
    }

    public void insertar() throws Exception {
        pa.setIdPaciente(ICONIFIED);
        pa.setCodigoPaciente(this.txtCodigoPaciente.getText());
        pa.setNombre(this.txtNombrePaciente.getText());
        pa.setApellido(this.txtApellidoPaciente.getText());
        pa.setEdad(Integer.parseInt(this.SpEdad.getValue().toString()));
         if(this.RbMasculino.isSelected())
        {
            pa.setGenero("Masculino");
        }
        else
        {
            pa.setGenero("Femenino");
        }
        
        daoP.insertarPaciente(pa);
        JOptionPane.showMessageDialog(null, "Datos insertados con exito");
        tablaPaciente();
        limpiar();
    }

    public void limpiar() {
        this.txtIdPaciente.setText("");
        this.txtCodigoPaciente.setText("");
        this.txtNombrePaciente.setText("");
        this.txtApellidoPaciente.setText("");
        this.SpEdad.setValue(18);
        this.buttonGroup1.clearSelection();
        this.txtBuscado.setText("");
    }

    public void llenarTabla() {
        int fila = this.JtbPaciente.getSelectedRow();
        this.txtIdPaciente.setText(String.valueOf(this.JtbPaciente.getValueAt(fila, 0)));
        this.txtCodigoPaciente.setText(String.valueOf(this.JtbPaciente.getValueAt(fila, 1)));
        this.txtNombrePaciente.setText(String.valueOf(this.JtbPaciente.getValueAt(fila, 2)));
        this.txtApellidoPaciente.setText(String.valueOf(this.JtbPaciente.getValueAt(fila, 3)));
        int edad = Integer.parseInt(this.JtbPaciente.getValueAt(fila, 4).toString());
        SpEdad.setValue(edad);
        String genero = String.valueOf(this.JtbPaciente.getValueAt(fila, 5));
        if (genero.toUpperCase().equals("MASCULINO")) {
                RbMasculino.setSelected(true);
            } else {
                RbFemenino.setSelected(true);
            }
    }

    public void modificar() {
        try {
            pa.setIdPaciente(Integer.parseInt(this.txtIdPaciente.getText()));
            pa.setCodigoPaciente(this.txtCodigoPaciente.getText());
            pa.setNombre(this.txtNombrePaciente.getText());
            pa.setApellido(this.txtApellidoPaciente.getText());
            pa.setEdad(Integer.parseInt(this.SpEdad.getValue().toString()));
             if(this.RbMasculino.isSelected())
             {
                 pa.setGenero("Masculino");
             }
             else
             {
                pa.setGenero("Femenino");
             }
            int SiONo = JOptionPane.showConfirmDialog(this, "Desea modificar el Paciente? ", "Modificar Paciente", JOptionPane.YES_NO_OPTION);

            if (SiONo == 0) {
                daoP.modificarPaciente(pa);
                JOptionPane.showMessageDialog(rootPane, "Paciente modificado con exito", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
                tablaPaciente();
                limpiar();
            } else {
                limpiar();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public void eliminar() {
        try {
            pa.setIdPaciente(Integer.parseInt(this.txtIdPaciente.getText()));
            int SiONo = JOptionPane.showConfirmDialog(this, "Desea eliminar el Paciente?", "Eliminar Perfil", JOptionPane.YES_NO_OPTION);

            if (SiONo == 0) {
                daoP.eliminarPaciente(pa);
                JOptionPane.showMessageDialog(rootPane, "Paciente eliminado con exito", "Confirmacion", JOptionPane.INFORMATION_MESSAGE);
                tablaPaciente();
                limpiar();
            } else {
                limpiar();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(rootPane, ex.toString(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }
    
    public void tablaConsultaBusqueda()
    {
        String[] columnas = {"ID", "Codigo_Pac", "Fecha", "Hora", "Descripcion"};
        Object[] obj = new Object[5];
        tabla2 = new DefaultTableModel(null, columnas);
        List ls;

        try {
            ls = daoC.buscarConsulta(this.txtBuscado.getText());
            for (int i = 0; i < ls.size(); i++) {
                con = (Consulta) ls.get(i);
                obj[0] = con.getIdConsulta();
                obj[1] = con.getIdPaciente();
                obj[2] = con.getFecha();
                obj[3] = con.getHora();
                obj[4] = con.getDescripcion();
                tabla2.addRow(obj);
            }
            ls = daoC.mostrarConsulta();
            this.JtbConsulta.setModel(tabla2);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al mostrar datos" + e.toString());
        }
    
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtIdPaciente = new javax.swing.JTextField();
        txtCodigoPaciente = new javax.swing.JTextField();
        txtNombrePaciente = new javax.swing.JTextField();
        txtApellidoPaciente = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnEliminar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        JtbPaciente = new javax.swing.JTable();
        txtBuscado = new javax.swing.JTextField();
        btnActualizar = new javax.swing.JButton();
        RbMasculino = new javax.swing.JRadioButton();
        RbFemenino = new javax.swing.JRadioButton();
        SpEdad = new javax.swing.JSpinner();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        JtbConsulta = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        btnImprimir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel1.setText("DATOS DE PACIENTE");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setText("ID");

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel3.setText("CODIGO");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel4.setText("NOMBRE");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setText("APELLIDO");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel6.setText("EDAD");

        txtIdPaciente.setEnabled(false);

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel7.setText("GENERO");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        btnGuardar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/iconos/GUARDAR1.png"))); // NOI18N
        btnGuardar.setText("GUARDAR");
        btnGuardar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnGuardarMouseClicked(evt);
            }
        });

        btnEditar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/iconos/editar1.png"))); // NOI18N
        btnEditar.setText("EDITAR");
        btnEditar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEditarMouseClicked(evt);
            }
        });

        btnEliminar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnEliminar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/iconos/eliminar2.png"))); // NOI18N
        btnEliminar.setText("ELIMINAR");
        btnEliminar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnEliminarMouseClicked(evt);
            }
        });

        btnLimpiar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnLimpiar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/iconos/limpiar1.png"))); // NOI18N
        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnLimpiarMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnGuardar)
                    .addComponent(btnEliminar))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnEditar)
                    .addComponent(btnLimpiar))
                .addContainerGap(39, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnEditar))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEliminar)
                    .addComponent(btnLimpiar))
                .addContainerGap(42, Short.MAX_VALUE))
        );

        JtbPaciente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "CODIGO PACIENTE", "NOMBRE", "APELLIDO", "EDAD", "GENERO"
            }
        ));
        JtbPaciente.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JtbPacienteMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(JtbPaciente);

        txtBuscado.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscadoKeyReleased(evt);
            }
        });

        btnActualizar.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        btnActualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/iconos/actualizar1.png"))); // NOI18N
        btnActualizar.setText("ACTUALIZAR");
        btnActualizar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnActualizarMouseClicked(evt);
            }
        });

        buttonGroup1.add(RbMasculino);
        RbMasculino.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        RbMasculino.setText("MASCULINO");

        buttonGroup1.add(RbFemenino);
        RbFemenino.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        RbFemenino.setText("FEMENINO");

        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0), 2));

        JtbConsulta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "CODIGO_PA", "FECHA", "HORA", "DESCRIPCION"
            }
        ));
        jScrollPane2.setViewportView(JtbConsulta);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 602, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(30, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(71, 71, 71))
        );

        jLabel8.setFont(new java.awt.Font("Arial", 1, 18)); // NOI18N
        jLabel8.setText("HISTORIAL DE CONSULTAS");

        jLabel9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/iconos/buscar1.png"))); // NOI18N

        btnImprimir.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/com/iconos/imprimir.png"))); // NOI18N
        btnImprimir.setText("IMPRIMIR");
        btnImprimir.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                btnImprimirMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(246, 246, 246)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(207, 207, 207))
            .addGroup(layout.createSequentialGroup()
                .addGap(51, 51, 51)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 523, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(10, 10, 10)))
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(txtApellidoPaciente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNombrePaciente, javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCodigoPaciente, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(RbMasculino)
                            .addComponent(SpEdad, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(34, 34, 34)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addComponent(RbFemenino))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtBuscado, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnActualizar)
                                .addGap(33, 33, 33))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(400, 400, 400)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(33, 33, 33)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel1))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(32, 32, 32)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(7, 7, 7)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel2)
                                        .addGap(120, 120, 120)
                                        .addComponent(jLabel5)
                                        .addGap(33, 33, 33)
                                        .addComponent(jLabel6)
                                        .addGap(28, 28, 28)
                                        .addComponent(jLabel7))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(txtIdPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(24, 24, 24)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(txtCodigoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel3))
                                                .addGap(26, 26, 26)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(txtNombrePaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jLabel4))
                                                .addGap(24, 24, 24)
                                                .addComponent(txtApellidoPaciente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(27, 27, 27)
                                                .addComponent(SpEdad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(166, 166, 166)
                                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                        .addGap(21, 21, 21)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(RbMasculino)
                                            .addComponent(RbFemenino)))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(btnActualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtBuscado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(25, 25, 25)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addComponent(btnImprimir)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLimpiarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnLimpiarMouseClicked
        limpiar();
    }//GEN-LAST:event_btnLimpiarMouseClicked

    private void btnGuardarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnGuardarMouseClicked
        try {
            insertar();
        } catch (Exception ex) {
            Logger.getLogger(FrmPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnGuardarMouseClicked

    private void JtbPacienteMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_JtbPacienteMouseClicked
        llenarTabla();
    }//GEN-LAST:event_JtbPacienteMouseClicked

    private void btnEditarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEditarMouseClicked
        modificar();
    }//GEN-LAST:event_btnEditarMouseClicked

    private void btnEliminarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnEliminarMouseClicked
        eliminar();
    }//GEN-LAST:event_btnEliminarMouseClicked

    private void btnActualizarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnActualizarMouseClicked
        this.txtBuscado.setText(a);

    }//GEN-LAST:event_btnActualizarMouseClicked

    private void txtBuscadoKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscadoKeyReleased
        if(this.txtBuscado.getText().isEmpty())
        {
            String[] columnas = {"ID", "Codigo", "Nombre", "Apellido", "Edad", "Genero"};
            Object[] obj = new Object[6];
            tabla = new DefaultTableModel(null, columnas);
            List ls;

            try {
                ls = daoP.mostrarPaciente();
                for (int i = 0; i < ls.size(); i++) {
                    pa = (Paciente) ls.get(i);
                    obj[0] = pa.getIdPaciente();
                    obj[1] = pa.getCodigoPaciente();
                    obj[2] = pa.getNombre();
                    obj[3] = pa.getApellido();
                    obj[4] = pa.getEdad();
                    obj[5] = pa.getGenero();
                    tabla.addRow(obj);
                }
                ls = daoP.mostrarPaciente();
                this.JtbPaciente.setModel(tabla);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al mostrar datos" + e.toString());
            }
            
            tablaConsulta();
        
        }
        else
        {
            String[] columnas = {"ID", "Codigo", "Nombre", "Apellido", "Edad", "Genero"};
            Object[] obj = new Object[6];
            tabla = new DefaultTableModel(null, columnas);
            List ls;

            try 
            {
                ls = daoP.buscarPaciente(this.txtBuscado.getText());
                for (int i = 0; i < ls.size(); i++) 
                {
                    pa = (Paciente) ls.get(i);
                    obj[0] = pa.getIdPaciente();
                    obj[1] = pa.getCodigoPaciente();
                    obj[2] = pa.getNombre();
                    obj[3] = pa.getApellido();
                    obj[4] = pa.getEdad();
                    obj[5] = pa.getGenero();
                    tabla.addRow(obj);
                }
                ls = daoP.mostrarPaciente();
                this.JtbPaciente.setModel(tabla);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Error al mostrar datos" + e.toString());
            }
            
            tablaConsultaBusqueda();
        
        }
                
    }//GEN-LAST:event_txtBuscadoKeyReleased

    private void btnImprimirMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnImprimirMouseClicked
        try {
            Reportes re = new Reportes();
            re.reportePaciente();
        } catch (JRException ex) {
            Logger.getLogger(FrmPaciente.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (SQLException ex) {
            Logger.getLogger(FrmPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnImprimirMouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPaciente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPaciente().setVisible(true);

            }

        });

        try {
            //Se inicializa la conexion con el Arduino en el puerto COM5
            ino.arduinoRX("COM6", 9600, listener);

        } catch (ArduinoException ex) {
            Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(rxSimple.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable JtbConsulta;
    private javax.swing.JTable JtbPaciente;
    private javax.swing.JRadioButton RbFemenino;
    private javax.swing.JRadioButton RbMasculino;
    private javax.swing.JSpinner SpEdad;
    private javax.swing.JButton btnActualizar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnEliminar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField txtApellidoPaciente;
    private javax.swing.JTextField txtBuscado;
    private javax.swing.JTextField txtCodigoPaciente;
    private javax.swing.JTextField txtIdPaciente;
    private javax.swing.JTextField txtNombrePaciente;
    // End of variables declaration//GEN-END:variables
}
