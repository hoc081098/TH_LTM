package com.hoc.thltm2.bai2;

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

      try {
        final String result = String.valueOf(Eval.execute(s));

        final DatagramPacket outSending = new DatagramPacket(
          result.getBytes(),
          0,
          result.length(),
          address,
          port
        );
        socket.send(outSending);
      } catch (Exception e) {
        final String error = e.toString();
        final DatagramPacket outSending = new DatagramPacket(
          error.getBytes(),
          0,
          error.length(),
          address,
          port
        );
        socket.send(outSending);
      }
    }
  }

}
