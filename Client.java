package chat.app;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.border.*;
import java.util.*;
import java.text.*;
import java.net.*;
import java.io.*;

public class Client implements ActionListener {
    JTextField text;
    static JPanel a1;
    static Box vertical = Box.createVerticalBox();
    static DataOutputStream dout;
    static JFrame f = new JFrame();
    //for frame
    Client(){
        
        f.setLayout(null);
        
        JPanel p1 = new JPanel();                     //above frame
        p1.setBackground(new Color(7,94,84));
        p1.setBounds(0, 0, 450, 70);
        p1.setLayout(null);
        f.add(p1);
       
        //setting image on frame
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
        Image i2 = i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT); //image class is in awt
        ImageIcon i3 = new ImageIcon(i2);
        JLabel back = new JLabel(i3);       //use of jlabel?
        back.setBounds(5,20,25,25);
        p1.add(back);
        
        back.addMouseListener(new MouseAdapter(){
        public void mouseClicked(MouseEvent ae){
            System.exit(0);
    }
    });
        
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
        Image i5 = i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT); //image class is in awt
        ImageIcon i6 = new ImageIcon(i5);
        JLabel profile = new JLabel(i6);       //use of jlabel?
        profile.setBounds(40,10,50,50);
        p1.add(profile);
        
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
        Image i8 = i7.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT); //image class is in awt
        ImageIcon i9 = new ImageIcon(i8);
        JLabel video= new JLabel(i9);       //use of jlabel?
        video.setBounds(300,20,30,30);
        p1.add(video);
        
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
        Image i11 = i10.getImage().getScaledInstance(35,30, Image.SCALE_DEFAULT); //image class is in awt
        ImageIcon i12 = new ImageIcon(i11);
        JLabel phone= new JLabel(i12);       //use of jlabel?
        phone.setBounds(360,20,35,30);
        p1.add(phone);

        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
        Image i14 = i13.getImage().getScaledInstance(10,25, Image.SCALE_DEFAULT); //image class is in awt
        ImageIcon i15 = new ImageIcon(i14);
        JLabel morevert= new JLabel(i15);       //use of jlabel?
        morevert.setBounds(420,20,10,25);
        p1.add(morevert);

        JLabel name = new JLabel("Komu");
        name.setBounds(110, 15, 100, 18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF", Font.BOLD, 18));
        p1.add(name);
        
        JLabel activenow = new JLabel("ACTIVE NOW");
        activenow.setBounds(110, 35, 100, 18);
        activenow.setForeground(Color.WHITE);
        activenow.setFont(new Font("SAN SERIF", Font.BOLD, 14));
        p1.add(activenow);
        
        a1 = new JPanel();
        a1.setBounds(5,75,440, 570);
        f.add(a1);
        
        text = new JTextField();
        text.setBounds(5,655,310, 40);
        text.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        f.add(text);
        
        JButton send = new JButton("Send");
        send.setBounds(320, 655, 123, 40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN SERIF", Font.PLAIN, 16));
        f.add(send);
        
        
        
        f.setSize(450, 700);          //length and width of frame
        f.setLocation(800,50);
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);
        f.setVisible(true);
        
    }
    
    public void actionPerformed(ActionEvent ae) {
        try{
        
        String out = text.getText();
        
        
        JPanel p2 = formatLabel(out);

        
        a1.setLayout(new BorderLayout());
        
        JPanel right = new JPanel(new BorderLayout());
        right.add(p2, BorderLayout.LINE_END);
        
        vertical.add(right);
        vertical.add(Box.createVerticalStrut(15));
        a1.add(vertical, BorderLayout.PAGE_START);
        
        dout.writeUTF(out);
        text.setText("");
        
        f.repaint();
        f.invalidate();
        f.validate();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public static JPanel formatLabel(String out){
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JLabel output = new JLabel("<html><p style=\"width: 150px\">"+ out +"</html>");
        output.setFont(new Font("Tahoma", Font.PLAIN, 16));
        output.setBackground(Color.YELLOW);
        output.setOpaque(true);
        output.setBorder(new EmptyBorder(15, 15, 15, 50));
  
        panel.add(output);
        
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        
        panel.add(time);
        return panel;
    }
    
    public static void main(String args[]){
        Client s = new Client();
        
        try{
            Socket t = new Socket("127.0.0.1", 6001);       //local host
            DataInputStream din = new DataInputStream(t.getInputStream());
            dout = new DataOutputStream(t.getOutputStream());
            
            while(true){
                    a1.setLayout(new BorderLayout());
                    String msg = din.readUTF();
                    JPanel panel = formatLabel(msg);
                    
                    JPanel left = new JPanel(new BorderLayout());
                    left.add(panel, BorderLayout.LINE_START);
                    vertical.add(left);
                    
                    vertical.add(Box.createVerticalStrut(15));
                    a1.add(vertical, BorderLayout.PAGE_START);
                    f.validate();
                }

        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
