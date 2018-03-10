/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsserver;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author jose
 */
public class ObservadorDeConsola implements IObserver {

    private IObservable observable;


    public ObservadorDeConsola(IObservable observable) {
        this.observable = observable;
        this.observable.addObserver(this);
    }
//w a s z c=agacharse

    @Override
    public void Update() {
        contar();
        System.out.println("" + observable.toString());
    }

    public static int contar() {
        ArrayList r = new ArrayList();
        ArrayList columnNames = new ArrayList();
        int valor = 0;
        String url = "jdbc:mysql://127.0.0.1/pppp";
        String userid = "root";
        String password = "oxnock2020";
        String sql = "SELECT idNotificaciones,Titulo,Descripcion FROM notificaciones where notificaciones.nuevo=0 ";

        try (Connection connection = DriverManager.getConnection(url, userid, password);
                Statement stmt = connection.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            ResultSetMetaData md = rs.getMetaData();
            int columns = md.getColumnCount();

            for (int i = 1; i <= columns; i++) {
                columnNames.add(md.getColumnName(i));
            }

            while (rs.next()) {
                ArrayList row = new ArrayList(columns);

                for (int i = 1; i <= columns; i++) {
                    row.add(rs.getObject(i));
                }

                r.add(row);

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());

        }
        valor = r.size();
      
        System.out.println(Integer.toString(valor));
     
        return valor;
    }

}
