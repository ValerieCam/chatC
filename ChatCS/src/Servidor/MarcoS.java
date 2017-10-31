
package Servidor;

import java.awt.BorderLayout;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import Cliente.Usuario;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class MarcoS extends JFrame implements Runnable{
	private JPanel milamina;
        private	JTextArea areatexto;
        private ServerSocket servidor;
        private Socket datos,destino;
        private ObjectInputStream entrada;
        private ObjectOutputStream salida;
        private String texto;
        private Thread hilo;
        private Usuario usuario;
    public MarcoS(){
        milamina= new JPanel();
        milamina.setLayout(new BorderLayout());
        
        areatexto=new JTextArea();
        
        milamina.add(areatexto,BorderLayout.CENTER);
        add(milamina);
        setVisible(true);
        try{
        servidor=new ServerSocket(9999);
        }catch(Exception e){}
        hilo=new Thread(this);
        hilo.start();
  }

    @Override
    public void run() {
        try{
          while(true){
            datos=servidor.accept();
            entrada=new ObjectInputStream(datos.getInputStream());
            usuario=(Usuario)entrada.readObject();
            areatexto.append(usuario.getNick()+": "+usuario.getTexto()+"\n");
            destino=new Socket(usuario.getIp(),9090);
            salida=new ObjectOutputStream(destino.getOutputStream());
            salida.writeObject(usuario);
            destino.close();
            salida.close();
            entrada.close();
        }
        }catch(Exception e){
            System.out.println("Error"+e);
        }
    }
    
}
