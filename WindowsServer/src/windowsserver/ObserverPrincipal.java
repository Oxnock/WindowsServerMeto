/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package windowsserver;

import UI.Gestor;
import UI.Principal;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

public class ObserverPrincipal {

    public static void main(String[] args) {
        Tiempo tiempo = new Tiempo(01, 40, 10);
        Gestor frame = new Gestor();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.pack();
        tiempo.start();
        ObservadorDeConsola oConsola = new ObservadorDeConsola(tiempo);
        Principal oGrafico = new Principal();
        oGrafico.setVisible(true);

    }
}
