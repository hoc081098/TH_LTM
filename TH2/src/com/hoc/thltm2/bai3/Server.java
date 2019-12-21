package com.hoc.thltm2.bai3;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

public class Server {
  private static final class InetAddressAndPort {
    final InetAddress address;
    final int port;

    InetAddressAndPort(InetAddress address, int port) {
      this.address = address;
      this.port = port;
    }

    @Override
    public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      InetAddressAndPort that = (InetAddressAndPort) o;
      return port == that.port &&
        Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
      return Objects.hash(address, port);
    }

    @Override
    public String toString() {
      return "InetAddressAndPort{" +
        "address=" + address +
        ", port=" + port +
        '}';
    }
  }

  private static final Set<InetAddressAndPort> inetAddressAndPorts = Collections.synchronizedSet(new LinkedHashSet<>());

  public static void main(String[] args) throws SocketException {
    final DatagramSocket socket = new DatagramSocket(5000);
    while (true) {
      try {
        handle(socket);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  private static void handle(DatagramSocket socket) throws Exception {
    final byte[] buffer = new byte[1024];
    final DatagramPacket packet = new DatagramPacket(
      buffer,
      0,
      buffer.length
    );
    socket.receive(packet);
    final String message = new String(packet.getData(), 0, packet.getLength());

    final InetAddress currentAddr = packet.getAddress();
    final int currentPort = packet.getPort();
    final InetAddressAndPort elem = new InetAddressAndPort(currentAddr, currentPort);
    System.out.println("Receive message=" + message + " from " + elem);

    if (message.equalsIgnoreCase("/join")) {
      inetAddressAndPorts.add(elem);


      inetAddressAndPorts.forEach(e -> {
        try {
          final byte[] bytes;

          if (e.equals(elem)) {
            bytes = ">>> You joined chat room".getBytes();
          } else {
            bytes = String.format(">>> %s:%s joined chat room", currentAddr.getHostName(), currentPort).getBytes();
          }

          socket.send(new DatagramPacket(bytes, 0, bytes.length, e.address, e.port));
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      });
    } else if (message.equalsIgnoreCase("/quit")) {
      inetAddressAndPorts.forEach(e -> {
        try {
          final byte[] bytes;

          if (e.equals(elem)) {
            bytes = "<<< You left chat room".getBytes();
          } else {
            bytes = String.format("<<< %s:%s left chat room", currentAddr.getHostName(), currentPort).getBytes();
          }

          socket.send(new DatagramPacket(bytes, 0, bytes.length, e.address, e.port));
        } catch (IOException ex) {
          ex.printStackTrace();
        }
      });
      inetAddressAndPorts.remove(elem);
    } else {
      inetAddressAndPorts.forEach(e -> {
        try {
          final byte[] bytes;

          if (e.equals(elem)) {
            bytes = String.format("[MESSAGE] You: %s", message).getBytes();
          } else {
            bytes = String.format("[MESSAGE] %s:%s: %s", currentAddr.getHostName(), currentPort, message).getBytes();
          }

          socket.send(new DatagramPacket(bytes, 0, bytes.length, e.address, e.port));
        } catch (IOException ex) {
          ex.printStackTrace();
        }

      });
    }
  }
}
