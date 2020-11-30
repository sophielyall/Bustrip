import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Trips {
	static ArrayList<String> taps;
	static BufferedWriter writer;
	static Double charge;
	static String currentDirectory;
	
	public static void main(String[] args) throws ParseException, IOException
	{
		currentDirectory = System.getProperty("user.dir");
		readTaps();
        
		writer = new BufferedWriter(new FileWriter(currentDirectory.concat("\\trips.csv")));
		
		
		for(int i = 0; i<taps.size(); i++)
		{
			
			
			String[] result = taps.get(i).split(",");
			if(result[2].equals("ON"))
			{
				//check if another tap
				if(i+1 <taps.size())
				{
					String[] nextLine = taps.get(i+1).split(",");
					
					//check if passenger tapped off.
					if(nextLine[2].equals("OFF"))
					{
						//check if tapped off at a different bus stop.
						if(!result[3].equals(nextLine[3]))
						{
							
							//write completed trip to file.
							String newData = result[1] + ", " + nextLine[1] + ", " + getDiff(result[1], nextLine[1]) + ", " + result[3] + "," + nextLine[3] + "," + getChargeAmount(result[3], nextLine[3]) + "," + result[4] + "," + result[5] + "," + result[6] + ", COMPLETED";
							writeTrips(newData);
							
						}
						else
						{
							
							//write cancelled trip to file
							String newData = result[1] + ", " + nextLine[1] + ", " + getDiff(result[1], nextLine[1]) + ", " + result[3] + "," + nextLine[3] + "," + getChargeAmount(result[3], nextLine[3]) + "," + result[4] + "," + result[5] + "," + result[6] + ", CANCELLED";
							writeTrips(newData);
						}
					}
					else
					{
						
						//write incomplete trip to file.
						String newData = result[1] + ", " + 0 + ", " + 0 + ", " + result[3] + "," + 0 + "," + getChargeAmount(result[3], "0") + "," + result[4] + "," + result[5] + "," + result[6] + ", INCOMPLETE";
						writeTrips(newData);
					}
					
				{
					//write incomplete trip to file.
					String newData = result[1] + ", " + 0 + ", " + 0 + ", " + result[3] + "," + 0 + "," + getChargeAmount(result[3], "0") + "," + result[4] + "," + result[5] + "," + result[6] + ", INCOMPLETE";
					writeTrips(newData);
				}
				
			}
		}	
		}
		writer.close();
	
	}
		
		
		
	
	
	public static void readTaps() throws FileNotFoundException
	{
		
		taps = new ArrayList<String>();
		
		Scanner sc = new Scanner(new File(currentDirectory.concat("\\taps.csv")));
		while(sc.hasNextLine())
		{
			
			taps.add(sc.nextLine());
		}
		
		sc.close();
	}
	
	public static long getDiff(String startDate, String stopDate) throws ParseException
	{
		
		
		SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		
		Date start =  format.parse(startDate);
		Date stop = format.parse(stopDate);
		
		

		//difference in seconds
		long diff = (stop.getTime() - start.getTime())/1000;
		

		
		return diff;
	}
	
	public static Double getChargeAmount(String fromStop, String toStop)
	{
		
		if(fromStop.equals(toStop))
		{
			//cancelled trip charge
			charge = 0.0;
		}
		else if(fromStop.equals("Stop1") || toStop.equals("Stop1"))
		{
			if(fromStop.equals("Stop2") || toStop.equals("Stop2"))
			{
				//completed trip from stop1 and stop2
				charge = 3.25;
			}
			else if(fromStop.equals("Stop3") || toStop.equals("Stop3") || toStop.equals("0"))
			{
				//completed trip from stop1 and stop3 or incomplete trip from stop1.
				charge = 7.30;
			}
			
		}
		else
		{
			charge = 5.50;
		}
		
		return charge;
	}
	
	public static void writeTrips(String trip) throws IOException
	{
		//write trip details to file.
		writer.write(trip);
		writer.newLine();
	}

}
