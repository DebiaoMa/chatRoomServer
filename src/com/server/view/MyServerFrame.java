package com.server.view;

import com.server.model.MyServer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MyServerFrame extends JFrame implements ActionListener {

    private static final long serialVersionUID = 2876730358141950960L;
    JPanel jPanel;
    JButton startButton, endButton;
    MyServer myServer = null;

    public static void main(String[] args) {

        MyServerFrame myServer = new MyServerFrame();
    }

    public MyServerFrame() {

        jPanel = new JPanel();
        startButton = new JButton("启动");
        startButton.addActionListener(this);
        endButton = new JButton("关闭");
        endButton.addActionListener(this);

        jPanel.add(startButton);
        jPanel.add(endButton);

        this.add(jPanel);
        this.setBounds(1024, 666, 500, 400);
        this.setIconImage((new ImageIcon("image/qq.gif")).getImage());
        this.setAlwaysOnTop(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == startButton) {
            myServer = new  MyServer();
        } else if (e.getSource() == endButton) {
            //myServer.killServer();
        }
    }
}
