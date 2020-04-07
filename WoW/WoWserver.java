import java.io.*;
import java.net.*;
import java.util.*;
 
/**
 * This program demonstrates how to implement a UDP server program.
 *
 *
 * @author www.codejava.net
 */
public class WoWserver {
    private DatagramSocket socket;
    private List<String> WoWmessages = new ArrayList<String>();
    private Random random;
 
    public WoWserver(int port) throws SocketException {
        socket = new DatagramSocket(port);
        random = new Random();
    }
 
    public static void main(String[] args) {
        if (args.length < 2) {
            System.out.println("Syntax: WoWserver <file> <port>");
            return;
        }
 
        String WoWfile = args[0];
        int port = Integer.parseInt(args[1]);
 
        try {
            WoWserver server = new WoWserver(port);
            server.loadWoWFromFile(WoWfile);
            server.service();
        } catch (SocketException ex) {
            System.out.println("Socket error: " + ex.getMessage());
        } catch (IOException ex) {
            System.out.println("I/O error: " + ex.getMessage());
        }
    }
 
    private void service() throws IOException {
        while (true) {
            DatagramPacket WoWrequest = new DatagramPacket(new byte[1], 1);
            socket.receive(WoWrequest);
            String WoWmessage = getNewWoW();
            byte[] buffer = WoWmessage.getBytes();
 
            InetAddress clientAddress = WoWrequest.getAddress();
            int clientPort = WoWrequest.getPort();
 
            DatagramPacket WoWresponse = new DatagramPacket(buffer, buffer.length, clientAddress, clientPort);
            socket.send(WoWresponse);
        }
    }
 
    private void loadWoWFromFile(String WoWfile) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(WoWfile));
        String WoWcontentLine;
 
        while ((WoWcontentLine = reader.readLine()) != null) {
            WoWmessages.add(WoWcontentLine);
        }
 
        reader.close();
    }
 
    private String getNewWoW() {
        int randomIndex = random.nextInt(WoWmessages.size());
        String randomWoW = WoWmessages.get(randomIndex);
        return randomWoW;
    }
}