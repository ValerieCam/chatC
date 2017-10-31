
package Servidor;

import javax.swing.JFrame;

public class Servidor {

    public static void main(String[] args) {
        MarcoS servidor=new MarcoS();
	servidor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        servidor.setSize(300,300);
        servidor.setLocationRelativeTo(null);
    }
}
