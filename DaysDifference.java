
/**
 *  Find the difference between 2 dates in days.
 *  Specific to Hobart, reading in a dates.csv file as it's input
 * 
 * @return Returns to stdout the difference in days between 2 dates in the Australia/Hobart Locale 
 * @version 0.1
 * @author Ben Cameron
*/
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.TimeZone;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Iterator;

public class DaysDifference {
	public static void main(String[] args) {

		final String COMMA_DELIMITER = ",";
		final String SPACE_DELIMITER = " ";

		List<List<String>> dates = new ArrayList<>();
		List<List<Integer>> yearsEarliest = new ArrayList<>();
		List<List<Integer>> yearsLatest = new ArrayList<>();

		System.out.println( "BETWEEEN TWO DATES" );		
		System.out.println( "Reading dates.csv to get days inbetween 2 dates that are between 1900 and 2010, specific to Australia/Hobart " );
		System.out.println( " " );	

		/**
		 *****************************************************
		 *  Read the csv file, split into valid and not valid entries.
		 *  If entry is valid, give to a list for processing.
		 *****************************************************
		*/

		try (BufferedReader br = new BufferedReader(new FileReader("dates.csv"))) {
			String line;

			System.out.println( "Invalid dates not falling within 1900 and 2010" );
			System.out.println( "**********************************************" );

			try {
				// Read each line, saving the whole date and each of earlier and latest years into seperate lists
				while ((line = br.readLine()) != null) {
					String[] values = line.split(COMMA_DELIMITER);
					String earliestDate = values[0];
					String latestDate = values[1];

					String[] datePiecesEarliest = earliestDate.split(SPACE_DELIMITER);
					String[] datePiecesLatest = latestDate.split(SPACE_DELIMITER);

					int yearEarliest = Integer.parseInt(datePiecesEarliest[2]);
					int yearLatest = Integer.parseInt(datePiecesLatest[2]);

					// Validation of years to exclude them from further calculation
					if ( yearEarliest >= 1900 && yearLatest <= 2010  ) {
						dates.add(Arrays.asList(values));
						yearsEarliest.add(Arrays.asList( yearEarliest ));
						yearsLatest.add(Arrays.asList( yearLatest ));
					} else {
						System.out.println( earliestDate + ", " + latestDate );
					}
				}
			} catch (Exception e) {
				System.out.println("ERROR: There was an error reading from the file containing dates.");
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println("ERROR: There was an error getting the dates file.");
			e.printStackTrace();
		}

		/**
		 *****************************************************
		 *  Parse and display the valid dates with days between
		 *****************************************************
		*/

		// Get a date parsing pattern created and create new instance of class
        String datePattern = "DD MM yyyy";
		DateFormat simpleDateFormat = new SimpleDateFormat(datePattern);
		
		// Adjust to Hobart not GMT
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Australia/Hobart"));

		Date date1 = null;
		Date date2 = null;

		System.out.println( " " );
		System.out.println( "Valid dates, displayed as 'earliest date, latest date, days between'" );
		System.out.println( "*********************************************************************" );

		try {
			Iterator<List<String>> iterator = dates.iterator();
			while(iterator.hasNext()) {
				// Object next = iterator.next();
				List<String> next = iterator.next();
				String earliestDate = next.get(0);
				String latestDate = next.get(1);

				date1 = simpleDateFormat.parse(earliestDate);
				date2 = simpleDateFormat.parse(latestDate);
	
				// difference in Milliseconds between dates since 00:00:00 UTC
				long getDiff = date2.getTime() - date1.getTime();
	
				// Convert Milliseconds to days
				long daysDiff = getDiff / (24 * 60 * 60 * 1000);
	
				// Display the result
				System.out.println( earliestDate + ", " + latestDate + ", " + daysDiff );
			} 
		} catch (Exception e) {
			System.out.println("ERROR: There was an error finding and displaying the days between the dates.");
			e.printStackTrace();
		}
	}

}