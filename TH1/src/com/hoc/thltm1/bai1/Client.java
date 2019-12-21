package com.hoc.thltm1.bai1;

import java.io.*;
import java.net.Socket;

public class Client {
  public static void main(String[] args) throws IOException {
    final Socket socket = new Socket("localhost", 5000);

    try (
      final DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
      final DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
      final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in))
    ) {
      System.out.print("Enter string: ");
      final String s = bufferedReader.readLine();
      dataOutputStream.writeUTF(s);

      System.out.println("Uppercased: " + dataInputStream.readUTF());
      System.out.println("Lowercased: " + dataInputStream.readUTF());
      System.out.println("Number of words: " + dataInputStream.readUTF());
    }
  }
}
