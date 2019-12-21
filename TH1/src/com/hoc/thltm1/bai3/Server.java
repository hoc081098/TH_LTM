package com.hoc.thltm1.bai3;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
  private static final Set<PrintWriter> writers = Collections.synchronizedSet(new LinkedHashSet<>());

  public static void main(String[] args) throws IOException {
    final ServerSocket serverSocket = new ServerSocket(5000);
    final ExecutorService threadPool = Executors.newFixedThreadPool(100);
    System.out.println(serverSocket);

    while (true) {
      final Socket socket = serverSocket.accept();
      threadPool.submit(new Handler(socket));
    }
  }

  private static class Handler implements Runnable {
    private final Socket socket;

    public Handler(Socket socket) {
      this.socket = socket;
    }

    @Override
    public void run() {
      System.out.println("" + socket.getInetAddress().getHostName() + " connected");

      Scanner scanner = null;
      PrintWriter writer = null;

      try {
        scanner = new Scanner(socket.getInputStream());
        writer = new PrintWriter(socket.getOutputStream(), true);
        final PrintWriter finalWriter = writer;

        writers.add(writer);
        writers.forEach(e -> {
          if (e == finalWriter) {
            e.println(">>> You joined chat room. Type /quit to quit!");
          } else {
            e.println(">>> " + socket.getInetAddress().getHostAddress() + " joined chat room");
          }
        });

        while (true) {
          final String s = scanner.nextLine();
          System.out.println(s);

          if (s.equalsIgnoreCase("/quit")) {
            return;
          }

          writers.forEach(e -> {
            if (e == finalWriter) {
              e.println("[MESSAGE] You: " + s);
            } else {
              e.println("[MESSAGE] " + socket.getInetAddress().getHostAddress() + ": " + s);
            }
          });

        }

      } catch (IOException e) {
        e.printStackTrace();
      } finally {
        writers.remove(writer);
        writers.forEach(e -> e.println("<<< " + socket.getInetAddress().getHostAddress() + " left chat room!"));

        if (scanner != null) {
          scanner.close();
        }
        if (writer != null) {
          writer.close();
        }
        try {
          socket.close();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
  }
}
