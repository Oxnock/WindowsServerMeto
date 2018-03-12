/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import Connexion.Connections;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author erick
 */
public class Consulta {
    
    private final Connection conexion = Connections.conexion();
    
    public int Notificaciones(){
        int n = 0;
        try {
            Statement st = conexion.createStatement();
            ResultSet rs = st.executeQuery("select count(idNotificaciones) from notificaciones where Nuevo = 0");
            while(rs.next()){
                n = rs.getInt(1);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Error Notificaciones\n" + ex);
        }
        return n;
    }
    
}
