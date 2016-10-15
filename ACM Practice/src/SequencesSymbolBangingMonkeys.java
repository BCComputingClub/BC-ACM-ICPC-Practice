import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Project {

	public static void main(String args[]) throws IOException
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int[] arr = new int[4];
		String[] arr2 = new String[4];
		String line = "";
		int place = -1;
		double answer;
		boolean stop;
		
		outer:
		while((line = br.readLine()) != null)
		{
			stop = true;
			arr2 = line.split(" ");

			for(int i = 0; i < 4; i++)
			{
				arr[i] = Integer.parseInt(arr2[i]);
				if(arr[i] == -1)
					place = i;
				if(arr[i] != -1)
					stop = false;
			}
			
			if(stop)
				break;
			
			answer = isArithmetic(arr, place);
			if(answer == -1)
				answer = isGeometric(arr, place);
			
			if(errorCheck(answer))
				System.out.println((int)answer);
			else
				System.out.println(-1);



		}
	}
	
	public static double isGeometric(int[] arr, int place)
	{
		double r = -1;
		double ans = -1;
		
		switch(place)
		{
		case 0:
			r = (double) arr[2] / arr[1];
			if(r * arr[2] == arr[3])
				ans = arr[1] / r;
			break;
		case 1:
			r = (double) arr[3] / arr[2];
			if(arr[2] * Math.pow(r, 2) == arr[0])
				ans = arr[2] / r;
			break;
		case 2:
			r = (double) arr[1] / arr[0];
			if(arr[1] * Math.pow(r, 2) == arr[3])
				ans = arr[3] / r;
			break;
		case 3:
			r = (double) arr[1] / arr[0];
			if(arr[1] * r == arr[2])
				ans = arr[2] * r;
			break;
		}
		return ans;
	}

	public static double isArithmetic(int[] arr, int place)
	{
		int dif = -1;
		double ans = -1;
		switch(place)
		{
		case 0:
			dif = arr[2] - arr[1];
			if(dif + arr[2] == arr[3])
				ans = arr[1] - dif;
			break;
		case 1:
			dif = arr[3] - arr[2];
			if(arr[2] - (2 * dif) == arr[0])
				ans = arr[2] - dif;
			break;
		case 2:
			dif = arr[1] - arr[0];
			if(arr[1] + (2*dif) == arr[3])
				ans = arr[3] - dif;
			break;
		case 3:
			dif = arr[1] - arr[0];
			if(arr[1] + dif == arr[2])
				ans = arr[2] + dif;
			break;
		}
		return ans;
	}
	
	public static boolean errorCheck(double num)
	{
		if(num >= 1 && num <= 1000000 && (Math.floor(num) == num))
			return true;
		return false;
	}

}
