package chattingApplicaiton;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.io.*;

//to create an event needs to implement ActionListener
public class Client implements ActionListener {

    JTextField text;    //defining globally to access it from diff constructor
    static JPanel a1;          // needs to access in ActionListener Constructor
    static Box vertical = Box.createVerticalBox();   //set the message vertically, one by one

    static JFrame f = new JFrame();

    static DataOutputStream dout;
    Client(){
        f.setLayout(null);   //if layout set as null only then setBounds will work
        //to add more thing or work, use JPanel
        JPanel p1 = new JPanel();
        p1.setBackground(new Color(19, 110, 103));  //customized background color
        p1.setBounds(0,0, 450,70);     //customize bounds
        p1.setLayout(null);
        f.add(p1);

        //Process to set an image on the frame(for arrow)
        ImageIcon i1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));  //giving the source of the original image(Arrow)
        Image i2 = i1.getImage().getScaledInstance(25,25, Image.SCALE_DEFAULT);    //scaled image
        ImageIcon i3 = new ImageIcon(i2);   //image to image icon
        JLabel back = new JLabel(i3);
        back.setBounds(5,20,25,25);
        p1.add(back);

        //Giving that  arrow icon an action to perform
        back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent ae) {
                //setVisible(false);
                //close the frame
                System.exit(0);
            }
        });

        //Process to set an image on the frame(for profile)
        ImageIcon i4 = new ImageIcon(ClassLoader.getSystemResource("icons/profile.png"));  //giving the source of the original image(profile pic)
        Image i5 = i4.getImage().getScaledInstance(50,50, Image.SCALE_DEFAULT);    //scaled image
        ImageIcon i6 = new ImageIcon(i5);   //image to image icon
        JLabel profile = new JLabel(i6);
        profile.setBounds(40,10,50,50);  //same as i5
        p1.add(profile);

        //Process to set an image on the frame(for video)
        ImageIcon i7 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));  //giving the source of the original image(profile pic)
        Image i8 = i7.getImage().getScaledInstance(30,30, Image.SCALE_DEFAULT);    //scaled image
        ImageIcon i9 = new ImageIcon(i8);   //image to image icon
        JLabel video = new JLabel(i9);
        video.setBounds(300,20,30,30);  //same as i5
        p1.add(video);

        //Process to set an image on the frame(for phone)
        ImageIcon i10 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));  //giving the source of the original image(profile pic)
        Image i11 = i10.getImage().getScaledInstance(35,30, Image.SCALE_DEFAULT);    //scaled image
        ImageIcon i12 = new ImageIcon(i11);   //image to image icon
        JLabel phone = new JLabel(i12);
        phone.setBounds(360,20,35,30);  //same as i5
        p1.add(phone);

        //Process to set an image on the frame(for 3dots)
        ImageIcon i13 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));  //giving the source of the original image(profile pic)
        Image i14 = i13.getImage().getScaledInstance(10,25, Image.SCALE_DEFAULT);    //scaled image
        ImageIcon i15 = new ImageIcon(i14);   //image to image icon
        JLabel morevert = new JLabel(i15);
        morevert.setBounds(410,20,10,25);  //same as i5
        p1.add(morevert);

        //Placing the text in the frame
        JLabel name = new JLabel("Arpita");
        name.setBounds(110,20,100,18);
        name.setForeground(Color.WHITE);
        name.setFont(new Font("SAN SERIF",Font.BOLD,18));
        p1.add(name);

        //Placing the text in the frame
        JLabel status = new JLabel("Active Now");
        status.setBounds(110,40,100,18);
        status.setForeground(Color.WHITE);
        status.setFont(new Font("SAN SERIF",Font.BOLD,12));
        p1.add(status);

        //panel for the text  // declared globally
        a1 = new JPanel();
        a1.setBounds(5,75,440,570);
        f.add(a1);

        //Field to type text
        text = new JTextField();
        text.setBounds(5,655,310,40);
        text.setFont(new Font("SAN SERIF",Font.PLAIN,16));
        f.add(text);

        JButton send = new JButton("Send");
        send.setBounds(320,655,123,40);
        send.setBackground(new Color(18, 108, 98));
        send.setForeground(Color.WHITE);
        send.addActionListener(this);
        send.setFont(new Font("SAN SERIF",Font.PLAIN,16));
        f.add(send);



        f.setSize(450,700);  //give size to the frame
        f.setLocation(800,100);     //set location for the frame pop up
        f.setUndecorated(true);
        f.getContentPane().setBackground(Color.WHITE);   //set background as WHITE

        f.setVisible(true);  //should be at last, it makes the frame visible
    }

    public void actionPerformed(ActionEvent ae){
        try {
            String out = text.getText();
            //JLabel output = new JLabel(out);  //does not need after formatLabel method

            //JPanel p2 = new JPanel();
            JPanel p2 = formatLabel(out);    //formatLabel also returns JPanel

            //p2.add(output);               ////does not need after formatLabel method

            //BorderLayout() --> places elements in either right.
            a1.setLayout(new BorderLayout());

            //aligning the messages at the right
            JPanel right = new JPanel(new BorderLayout());    //showing message at the line end
            //right.add(out, BorderLayout.LINE_END);   // out is a String, this method do not take String
            right.add(p2, BorderLayout.LINE_END);   //so change it to a label

            //aligning messages one by one
            vertical.add(right);
            vertical.add(Box.createVerticalStrut(15));   //creating 15 spaces between two vertical structure

            //adding all details on the a1 panel
            a1.add(vertical, BorderLayout.PAGE_START);
            //for sending the msg to server
            dout.writeUTF(out);

            text.setText("");

            f.repaint();    //needs to repaint to make the box text visible
            f.invalidate();
            f.validate();
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public static JPanel formatLabel(String out){
        JPanel panel =new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel output = new JLabel("<html><p style=\"width: 150px\">" + out + "</p></html>");
        output.setFont(new Font("Tahoma",Font.PLAIN,16));
        output.setBackground(new Color(37,211,102));  //giving color to the box
        output.setOpaque(true);         //"true" make the color visible
        output.setBorder(new EmptyBorder(15,15,15,50));     //gives the box padding or extra spaces

        //printing time of send
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        JLabel time = new JLabel();
        time.setText(sdf.format(cal.getTime()));
        panel.add(time);

        panel.add(output);

        return panel;
    }

    public static void main(String[] args) {
        new Client();
        try{
            //sockets can be multiple. but seversocket can be only one, connecting with the server
            Socket s = new Socket("127.0.0.1",6001);  //address of local host, port number of the server
            //for receiving the msg, read
            DataInputStream din = new DataInputStream(s.getInputStream());
            // for sending the msg, write
            dout =new DataOutputStream(s.getOutputStream());

            while(true){
                a1.setLayout(new BorderLayout());
                //reding the received msg
                String msg = din.readUTF();

                //Displaying the msg in frame
                JPanel panel = formatLabel(msg);

                //adding the left side of the panel
                JPanel left = new JPanel(new BorderLayout());
                left.add(panel,BorderLayout.LINE_START);            //displaying the received msg befor the line start
                // msg shown one by one
                vertical.add(left);

                vertical.add(Box.createVerticalStrut(15));
                a1.add(vertical,BorderLayout.PAGE_START);
                f.validate();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
