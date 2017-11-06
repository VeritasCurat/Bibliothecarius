import java.io.DataInputStream;
import java.io.IOException;

public class Eingabe {
	static DataInputStream in = new DataInputStream(System.in);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}
	
	@SuppressWarnings("deprecation")
	public static String leseLine() throws IOException{
		return in.readLine();
	}

}
