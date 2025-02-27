package echoService.client;

import util.ConsoleInput;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        // SET-UP:
        // OUR address information - we listen for messages here
        int myPort = 4444;

        boolean isRunning = true;
        String shortestMessage = null;
        String longestMessage = null;
        int messagesSentCount = 0;

        while (isRunning) {
            // Create a socket on which to listen for messages to that port
            try (DatagramSocket mySocket = new DatagramSocket(myPort)) {
                // Set timeout on the socket so it doesn't wait for a message for longer than 5000 milliseconds
                mySocket.setSoTimeout(5000);

                // Destination address information - IP and port
                InetAddress destinationIP = InetAddress.getByName("localhost");
                int destinationPort = 7777;

                // LOGIC:
                // Message to be sent
                if (!ConsoleInput.getYesNo("Do you want to send a message?")) {
                    isRunning = false;
                    continue;
                }
                String message = "ECHO%%" + ConsoleInput.getString("Message: ");

                // TRANSMISSION:
                // Condition the message for transmission
                byte[] payload = message.getBytes();
                // Build the packet to be sent
                DatagramPacket packet = new DatagramPacket(payload, payload.length, destinationIP, destinationPort);
                // Send message to server
                mySocket.send(packet);
                messagesSentCount++;

                // RECEIVE RESPONSE:
                // Create a byte array to hold the payload data
                byte[] receivedMessage = new byte[50];
                // Create a packet to hold the received message
                DatagramPacket incomingMessage = new DatagramPacket(receivedMessage, receivedMessage.length);
                // Receive the message from the network
                // This will BLOCK until it receives a message, i.e. the code will not progress beyond this line!
                mySocket.receive(incomingMessage);
                // Get the data out of the packet
                receivedMessage = incomingMessage.getData();

                // LOGIC STAGE
                // Display the data from the packet
                System.out.println("Response received: " + new String(receivedMessage));
            } catch (UnknownHostException e) {
                System.out.println("IP address is not recognised");
                System.out.println(e.getMessage());
            } catch (SocketException e) {
                System.out.println("Problem occurred on the socket");
                System.out.println(e.getMessage());
            } catch (IOException e) {
                System.out.println("Problem occurred when working with the socket");
                System.out.println(e.getMessage());
            }
        }

        System.out.println("Program shutting down...");
//        System.out.println("Longest message sent: " + longestMessage);
//        System.out.println("Shortest message sent: " + shortestMessage);
        System.out.println("Client sent " + messagesSentCount + " message(s).");
    }
}
