package com.hoc.thltm2.bai1;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class Server {
  public static void main(String[] args) throws IOException {
    final DatagramSocket socket = new DatagramSocket(5000);

    while (true) {
      final byte[] buffer = new byte[1024];
      final DatagramPacket incoming = new DatagramPacket(buffer, buffer.length);
      socket.receive(incoming);
      final String s = new String(incoming.getData(), 0, incoming.getLength());

      final InetAddress address = incoming.getAddress();
      final int port = incoming.getPort();

      send(socket, s.toUpperCase(), address, port);
      send(socket, s.toLowerCase(), address, port);
      send(socket, String.valueOf(s.split("\\W+").length), address, port);
    }
  }

  private static void send(DatagramSocket socket, String s, InetAddress address, int port) throws IOException {
    final DatagramPacket outSending = new DatagramPacket(
      s.getBytes(),
      0,
      s.length(),
      address,
      port
    );
    socket.send(outSending);
  }
}
