package com.hoc.thltm2.bai3;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Client {
  private static class MainPanel extends JPanel {

    private JTextArea textArea;
    private JTextField textField;

    private final DatagramSocket socket;
    private final ExecutorService executor = Executors.newSingleThreadExecutor();

    MainPanel() throws IOException {
      socket = new DatagramSocket();
      setupUI();
      listen();
    }

    private void setupUI() {
      setPreferredSize(new Dimension(500, 400));
      setBackground(Color.WHITE);

      setLayout(null);
      textArea = new JTextArea();
      final JScrollPane scrollPane = new JScrollPane(textArea);
      scrollPane.setBounds(0, 0, 500, 350);
      this.add(scrollPane);

      textField = new JTextField("", 50);
      textField.setBounds(0, 350, 500, 50);
      this.add(textField);

      textField.addActionListener(e -> {
        executor.submit(() -> {
          try {
            final byte[] bytes = textField.getText().getBytes();
            final DatagramPacket p = new DatagramPacket(bytes, 0, bytes.length, InetAddress.getByName("localhost"), 5000);
            socket.send(p);
            SwingUtilities.invokeAndWait(() -> textField.setText(""));
          } catch (Exception ex) {
            ex.printStackTrace();
          }
        });
      });
    }

    private void listen() {
      new Thread(() -> {
        while (true) {
          try {
            final byte[] buffer = new byte[1024];
            final DatagramPacket p = new DatagramPacket(buffer, 0, buffer.length);
            socket.receive(p);
            final String text = new String(p.getData(), 0, p.getLength());

            System.out.println(text);
            SwingUtilities.invokeAndWait(() -> textArea.append(text + "\n"));
          } catch (Exception e) {
            e.printStackTrace();
          }
        }
      }).start();
    }

  }

  public static void main(String[] args) throws IOException {
    SwingUtilities.invokeLater(() -> {
      final JFrame frame = new JFrame("UDP Chat room");
      try {
        frame.add(new MainPanel());
      } catch (IOException e) {
        e.printStackTrace();
        System.exit(1);
      }
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      frame.setVisible(true);
    });
  }
}
