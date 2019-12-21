package com.hoc.thltm1.bai2;

import java.io.*;
import java.net.Socket;

public class Client {
  public static void main(String[] args) throws IOException {
    final Socket socket = new Socket("localhost", 5000);

    try (
      final DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
      final DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
      final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))
    ) {
      System.out.print("Enter expression: ");
      final String expression = bufferedReader.readLine();
      dos.writeUTF(expression);

      System.out.println("Result: " + dataInputStream.readUTF());
    }
  }
}
