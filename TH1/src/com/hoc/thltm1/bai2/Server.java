package com.hoc.thltm1.bai2;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
  public static void main(String[] args) throws IOException {
    final ServerSocket serverSocket = new ServerSocket(5000);

    while (true) {
      final Socket socket = serverSocket.accept();

      new Thread(() -> {
        try (
          final DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
          final DataOutputStream dos = new DataOutputStream(socket.getOutputStream())
        ) {
          final String s = dataInputStream.readUTF();

          try {
            final double result = Eval.execute(s);
            dos.writeUTF(String.valueOf(result));
          } catch (Exception e) {
            dos.writeUTF(e.toString());
          }
        } catch (IOException e) {
          e.printStackTrace();
        }
      }).start();
    }
  }
}
