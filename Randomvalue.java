package schedulingAlgorithm;

import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;


class ReaderRandom {
    static BufferedReader reader;
    static StringTokenizer tokenizer;

    /** call this method to initialize reader for InputStream */
    static void init(InputStream input) {
        reader = new BufferedReader(
                     new InputStreamReader(input) );
        tokenizer = new StringTokenizer("");
    }

    /** get next word */
    static String next() throws IOException {
        while ( ! tokenizer.hasMoreTokens() ) {
        	
            tokenizer = new StringTokenizer(
                   reader.readLine() );
        }
        return tokenizer.nextToken();
    }

    static int nextInt() throws IOException {
        return Integer.parseInt( next() );
    }
	
    static double nextDouble() throws IOException {
        return Double.parseDouble( next() );
    }
}

public class Randomvalue {
	
	public static  double poissonRandomInterarrivalDelay(double L) {
	    return (Math.log(1.0-Math.random())/-L);

	}
	public static double nextExponential(double b) {
    	double randx;
		 double result;
		randx = Math.random();
		result = -1*b*Math.log(randx);
		return result;
		
    }
	
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		
		ReaderRandom.init(System.in);
		System.out.println("Enter time: ");
		
		int time = ReaderRandom.nextInt();
		time = time*1000;
		
		ArrayList<Double> arrivalTime = new ArrayList<Double>();
		ArrayList<Double> burstTime = new ArrayList<Double>();
		
		long start = System.currentTimeMillis();
		long end = start+ time ;
	
		while(System.currentTimeMillis()<end) {
			
			double val1 = poissonRandomInterarrivalDelay(time);
			arrivalTime.add(val1);
			double val2 = nextExponential(time);
			burstTime.add(val2);
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			
		}
		
		System.out.println(arrivalTime.size());
		System.out.println(burstTime.size());
		
	}

}
