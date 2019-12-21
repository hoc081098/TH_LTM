package com.hoc.thltm1.bai3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
  private static class MainPanel extends JPanel {

    private JTextArea textArea;
    private JTextField textField;

    private final Socket socket;

    MainPanel() throws IOException {
      socket = new Socket("localhost", 5000);
      setupUI(new PrintWriter(socket.getOutputStream(), true));
      listen(new Scanner(socket.getInputStream()));
    }

    private void setupUI(PrintWriter out) {
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

      textField.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          out.println(textField.getText());
          textField.setText("");
        }
      });
    }

    private void listen(Scanner in) {
      new Thread(() -> {
        while (in.hasNextLine()) {
          String text = in.nextLine();
          try {
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
      final JFrame frame = new JFrame("TCP/IP Chat room");
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
