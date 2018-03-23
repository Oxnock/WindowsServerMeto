/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import java.awt.*;
import java.sql.*;
import java.util.*;
import javax.swing.*;
import javax.swing.table.*;

public class Gestor extends JFrame {

    private JButton boton;

    public Gestor() {
        this.setResizable(false);

        setBounds(250, 20, 900, 700);//x, y, ancho, largo

        boton = new JButton("Volver");
        boton.setBounds(792, 610, 100, 50);// x,y,ancho,largo
        boton.setBackground(Color.GREEN);

        boton.addActionListener((e) -> {
            Actualizar();
            Principal oGrafico = new Principal();
            oGrafico.setVisible(true);
            this.dispose();
        });
        getContentPane().add(boton, BorderLayout.SOUTH);

        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();

        //  Connect to an MySQL Database, run query, get result set
        String url = "jdbc:mysql://127.0.0.1/servicew";
        String userid = "root";
        String password = "12";
        String sql = "SELECT idNotificaciones,Titulo,Descripcion FROM notificaciones where notificaciones.nuevo=0 ";

        // Java SE 7 has try-with-resources
        // This will ensure that the sql objects are closed when the program 
        // is finished with them
        try (Connection connection = DriverManager.getConnection(url, userid, password);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            //  Get column names
            for (int i = 1; i <= columns; i++) {
                columnNames.add(md.getColumnName(i));
            }

            //  Get row data
            while (rs.next()) {
                ArrayList row = new ArrayList(columns);

                for (int i = 1; i <= columns; i++) {
                    row.add(rs.getObject(i));
                }

                data.add(row);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        // Create Vectors and copy over elements from ArrayLists to them
        // Vector is deprecated but I am using them in this example to keep 
        // things simple - the best practice would be to create a custom defined
        // class which inherits from the AbstractTableModel class
        Vector columnNamesVector = new Vector();
        Vector dataVector = new Vector();

        for (int i = 0; i < data.size(); i++) {
            ArrayList subArray = (ArrayList) data.get(i);
            Vector subVector = new Vector();
            for (int j = 0; j < subArray.size(); j++) {
                subVector.add(subArray.get(j));
            }
            dataVector.add(subVector);
        }

        for (int i = 0; i < columnNames.size(); i++) {
            columnNamesVector.add(columnNames.get(i));
        }

        //  Create table with database data    
        JTable table = new JTable(dataVector, columnNamesVector) {
            public Class getColumnClass(int column) {
                for (int row = 0; row < getRowCount(); row++) {
                    Object o = getValueAt(row, column);

                    if (o != null) {
                        return o.getClass();

                    }
                }

                return Object.class;
            }
        };

        JScrollPane scrollPane = new JScrollPane(table);

        getContentPane().add(scrollPane);

        JPanel buttonPanel = new JPanel();
        getContentPane().add(buttonPanel, BorderLayout.PAGE_END);

    }

    public void Actualizar() {
        ArrayList columnNames = new ArrayList();
        ArrayList data = new ArrayList();
        String url = "jdbc:mysql://127.0.0.1/servicew";
        String userid = "root";
        String password = "12";
        String sqlU = "UPDATE  notificaciones set notificaciones.nuevo=1 where notificaciones.nuevo=0";

        try {
            Connection connection = DriverManager.getConnection(url, userid, password);
            Statement stmt = connection.createStatement();
            PreparedStatement pst = connection.prepareStatement(sqlU);
            pst.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
}
