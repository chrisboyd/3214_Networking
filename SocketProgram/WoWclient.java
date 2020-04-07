import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class WoWclient {
	
	private int port;
	private DatagramSocket socket;
	private InetAddress hostAddr;
	
	
	public WoWclient(String hostAddr, int port) throws IOException{
		this.hostAddr = InetAddress.getByName(hostAddr);
		this.port = port;
		socket = new DatagramSocket();		
	}

	public static void main(String[] args) {
		
		if (args.length < 2) {
            System.out.println("Syntax: WoWclient <hostname> <port>");
            return;
        }
		
		try {
			WoWclient sender = new WoWclient(args[0], Integer.parseInt(args[1]));
			
			sender.service();	
			
			
		} catch (IOException e) {
			System.out.println("IOException: " + e.getMessage());
		}
		
	}
	
	private void service() throws IOException{
		byte[] buffer = new byte[512];
		byte[] send = new byte[1];
		send[0] = 'Q';
		String response;
		
		do {
			DatagramPacket WoWsend = new DatagramPacket(send, 1, this.hostAddr, this.port);
			DatagramPacket WoWreceive = new DatagramPacket(buffer, buffer.length);
			
		
			this.socket.send(WoWsend);
			this.socket.receive(WoWreceive);
			response = new String(WoWreceive.getData(), 0, WoWreceive.getLength());
			System.out.println(response + "\n");
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				System.out.println("Sleep Exception: " + e.getMessage());
			}
		}while(!response.isEmpty());
				
	}

}
