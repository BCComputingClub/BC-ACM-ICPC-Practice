import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Project2 {

	public static void main(String[] args) throws IOException {
		
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String line = "";
		String[] arr = new String[5];
		double d; 
		double h;
		double z;
		double n1;
		double n2;
		while((line = br.readLine()) != null)
		{
			arr = line.split(" ");
			d = Double.parseDouble(arr[0]);
			h = Double.parseDouble(arr[1]);
			z = Double.parseDouble(arr[2]);
			n1 = Double.parseDouble(arr[3]);
			n2 = Double.parseDouble(arr[4]);
			
			
		}
			

	}

}
