import java.io.*;
import java.net.*;
import java.util.Scanner;

class InputStream implements Runnable {
	
	Socket s;

	public InputStream(Socket s) {
		this.s = s;
	}

	@Override
	public void run() {
		while(true) {
			try {
				DataInputStream din = new DataInputStream(s.getInputStream());
				System.out.println(din.readUTF());
			} catch(Exception e) {
				System.out.println(e);
			}
		}
	}
}

class App {
	public static void main(String args[]) {
		Scanner in = new Scanner(System.in);
		try {
			// Socket s = new Socket("localhost", 5003);
			Socket s = new Socket("139.59.69.131", 5003);
			DataOutputStream dout = new DataOutputStream(s.getOutputStream());
			Thread is = new Thread(new InputStream(s));
			is.start();
			while(true) {
				String text = in.next();
				if(text.equals("end")) break;
				dout.writeUTF(text);
				dout.flush();
			}			
			dout.close();
			is.stop();
			s.close();
		} catch(Exception e) {
			System.out.println(e);
		}
	}
}