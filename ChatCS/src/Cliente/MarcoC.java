
package Cliente;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.net.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

public class MarcoC extends JFrame implements ActionListener{
        private JTextField texto,nickT,ipT;
        private JTextArea charla;
	private JButton enviarB;
        private JLabel nickL,ipL;
        private JScrollPane scroll;
        private JPanel paneld;
        private PanelUno panelu;
        private Socket destino,recibe;
        private ServerSocket servidor;
        private ObjectOutputStream salida;
        private ObjectInputStream entrada;
        private Usuario usuario;
    public MarcoC(){
        setLayout(new BorderLayout(10,10));
        paneld=new JPanel(new FlowLayout());
        nickT=new JTextField(10);
        nickL=new JLabel("Nick: ");
        ipT=new JTextField(10);
        ipL=new JLabel("IP: ");
        paneld.add(nickL);
        paneld.add(nickT);
        paneld.add(ipL);
        paneld.add(ipT);
        add(paneld,BorderLayout.NORTH);
        charla=new JTextArea();
        charla.setEditable(false);
        scroll=new JScrollPane(charla,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        add(scroll,BorderLayout.CENTER);

        panelu=new PanelUno();
        
        texto=new JTextField(20);
        panelu.add(texto);		
        enviarB=new JButton("Enviar");
        enviarB.addActionListener(this);
        panelu.add(enviarB);
        add(panelu,BorderLayout.PAGE_END);
        setVisible(true);
        
        }
    //actionPerformed para enviar mensajes
     @Override
    public void actionPerformed(ActionEvent event){
        try{
        usuario=new Usuario();
        usuario.setIp(ipT.getText());
        usuario.setNick(nickT.getText());
        usuario.setTexto(texto.getText());
        charla.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        charla.append(texto.getText()+"\n");
        texto.requestFocus(true);
        texto.setText("");
        
        destino=new Socket("192.168.1.65",9999);
        salida=new ObjectOutputStream(destino.getOutputStream());
        salida.writeObject(usuario);
        salida.close();
        }catch(Exception e){
            System.out.println("Error "+e);
        }
    }
    //Panel que implementa lectura de mensajes
    private class PanelUno extends JPanel implements Runnable{
        private JLabel et;
        private Thread hilo;
        private Usuario reUsuario;
        public PanelUno(){
            setLayout(new FlowLayout());
            et=new JLabel("Chat");
            add(et);
            try{
            servidor=new ServerSocket(9090);
            }catch(Exception e){}
            hilo=new Thread(this);
            hilo.start();
        }
    //Recibe Mensajes
        @Override
        public void run() {
            try{
             recibe=servidor.accept();
             entrada=new ObjectInputStream(recibe.getInputStream());
             reUsuario=(Usuario)entrada.readObject();
             entrada.close();
             charla.append(reUsuario.getNick()+": "+reUsuario.getTexto()+"\n");
             this.run();
            }catch(Exception e){}
        }
    }   
}
