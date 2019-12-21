package com.hoc.thltm2.bai2;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Client {
  public static void main(String[] args) throws IOException {
    final DatagramSocket socket = new DatagramSocket();

    while (true) {
      final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
      System.out.print("Enter a expression: ");
      final String s = reader.readLine();

      final byte[] bytes = s.getBytes();
      final DatagramPacket sender = new DatagramPacket(bytes, bytes.length,
        InetAddress.getByName("localhost"), 5000);
      socket.send(sender);

      final byte[] buffer = new byte[1024];
      final DatagramPacket receiver = new DatagramPacket(buffer, buffer.length);
      socket.receive(receiver);
      final String result = new String(receiver.getData(), 0, receiver.getLength());

      System.out.println("Result: " + result);
    }
  }
}
