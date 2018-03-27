package schedulingAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class ReaderRoundRobin {
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


public class RoundRobin {
	
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
	
	public static void roundRobin(double[] burstTime, double[] arrivalTime,int n,double quantum) {
		
		double wt = 0 ; double tat = 0; 
		
		double[] waitingTime = new double[n];
		double[] turnAroundTime = new double[n];
		double[] processId = new double[n];
		
		for(int i=0;i<n;i++) {
			processId[i] = i;
		}
			
		double rem_bt[] = new double[n];
		   for (int i = 0 ; i < n ; i++)
			   rem_bt[i] =  burstTime[i];
	 
		   double t = 0; 

		   while(true){
			   boolean done = true;
	 
			   for (int i = 0 ; i < n; i++){
	       
				   if (rem_bt[i] > 0) {
					   done = false; 
	 
					   if (rem_bt[i] > quantum) {
						   t += quantum;
						   rem_bt[i] -= quantum;
					   }
					   else{
						   t = t + rem_bt[i];
						   waitingTime[i] = t - burstTime[i];
						   rem_bt[i] = 0;
					   }
				   }
			   }
			   if (done == true)
				   break;
		   }
		 
		 for(int i=0;i<n;i++) {
			 turnAroundTime[i] = waitingTime[i] + burstTime[i]; 
		 }
		 
		 for(int i=0;i<n;i++) {
				tat += turnAroundTime[i];
				wt +=waitingTime[i];
		 }
		 
		 tat = tat/n;
		 wt = wt/n;
		 
		 display(wt, tat, turnAroundTime, processId);
		
		
	}

	public static void display(double wt, double tat, double[] turnAroundTime, double[] processId){
		
		

		for(int i=0;i<n;i++) {
			System.out.println("Turn around time for " + (int)processId[i] + " process: " + turnAroundTime[i]);
		}
	
		System.out.println("Average turn around time: " + tat + " ms");	
		
		System.out.println("Average waiting time: " + wt + " ms");
	
	}
	
	public static void main(String[] args) throws IOException {
		
		ReaderRoundRobin.init(System.in);
		
		System.out.println("Enter time: ");
		
		int time = ReaderRoundRobin.nextInt();
		time = time*1000;
		
		ArrayList<Double> arrivalTimeAL = new ArrayList<Double>();
		ArrayList<Double> burstTimeAL = new ArrayList<Double>();
		
		long start = System.currentTimeMillis();
		long end = start+ time ;
	
		while(System.currentTimeMillis()<end) {
			
			double val1 = poissonRandomInterarrivalDelay(time);
			arrivalTimeAL.add(val1);
			double val2 = nextExponential(time);
			burstTimeAL.add(val2);
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
		
		n = burstTimeAL.size();
		
		 double[] burstTime = new double[burstTimeAL.size()];
		 for (int i = 0; i < burstTime.length; i++) {
		    burstTime[i] = burstTimeAL.get(i).doubleValue();  // java 1.4 style
		 }
		 
		 double[] arrivalTime = new double[arrivalTimeAL.size()];
		 for (int i = 0; i < arrivalTime.length; i++) {
		    arrivalTime[i] = arrivalTimeAL.get(i).doubleValue();  // java 1.4 style
		 }

		
		final double quantum = 2;
		
		roundRobin(burstTime, arrivalTime, n, quantum);
		
	}


}
