package schedulingAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class ReaderFCFS {
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


public class FCFS {

	static int n; 	
	static double[] arrivalTime;
	static double[] burstTime;
	static double[] processedId;
	
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
	
	public static void display(double wt, double tat, double[] turnAroundTime, double[] processId){
		 
		for(int i=0;i<n;i++) {
			System.out.println("Turn around time for " + (int)processId[i] + " process: " + turnAroundTime[i]);
		}
		
		//System.out.println("Average turn around time: " + tat + " ms");	
		
		System.out.println("Average waiting time: " + wt + " ms");
		
	}
	
	public static void FCFS(ArrayList<Double> burstTime, ArrayList<Double> arrivalTime,int n) {
		
		double[] waitingTime = new double[n];
		double[] turnAroundTime = new double[n];
		
		double[] completionTime = new double[n];
		double[] processId = new double[n];
		
		for(int i=0;i<n;i++) {
			processId[i] = i;
			//System.out.println(processId[i]);
		}
		
		double tat = 0; double wt=0; 
		
		completionTime[0] = burstTime.get(0);
		
		for(int i=1;i<n;i++) {
			completionTime[i] = completionTime[i-1] + burstTime.get(i);
			//System.out.println(completionTime[i]);
		}
		
		for(int i=0;i<n;i++) {
			turnAroundTime[i] = completionTime[i] - arrivalTime.get(i);
			waitingTime[i] = turnAroundTime[i] - burstTime.get(i);
			//System.out.println(turnAroundTime[i]);
			//System.out.println(waitingTime[i]);
		}
		
		for(int i=0;i<n;i++) {
			tat += turnAroundTime[i];
			wt +=waitingTime[i];
		}
		//System.out.println(wt/1000);
		wt = wt/n; tat = tat/n;
		//System.out.println(wt/1000);
		display(wt, tat, turnAroundTime, processId);
	
	}
	
	public static void main(String[] args) throws IOException {
		
		ReaderFCFS.init(System.in);
	
		System.out.println("Enter time: ");
		
		int time = ReaderFCFS.nextInt();
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
		
		n = burstTime.size();
		//System.out.println(n);

		FCFS(burstTime, arrivalTime, n);
		
	}
	
	
	
}
