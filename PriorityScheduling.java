package schedulingAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;

class ReaderPriorityScheduling {
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


public class PriorityScheduling {
	
static int n; 
	
	static double[] arrivalTime;
	static double[] burstTime;
	static double[] processedId;
	static double[] priority;
	
	public static void Priority(double[] burstTime, double[] arrivalTime, double[] priority, int n) {
		
		double[] processId = new double[n];
		
		for(int i=0;i<n;i++) {
			processId[i] = i+1;
		}
		
		for(int i=0;i<n;i++){
	        int pos=i;
	        for(int j=i+1;j<n;j++){
	            if(priority[j]<priority[pos])
	                pos=j;
	        }
	 
	        double temp = burstTime[i];
	        burstTime[i] = burstTime[pos];
	        burstTime[pos]=temp;
	 
	        temp = arrivalTime[i];
	        arrivalTime[i] = arrivalTime[pos];
	        arrivalTime[pos] = temp;
	        
	        temp = processId[i];
	        processId[i] = processId[pos];
	        processId[pos] = temp;
	        
	        temp = priority[i];
	        priority[i] = priority[pos];
	        priority[pos] = temp;
	       
	    }
		
		FCFS(burstTime, arrivalTime, processId, n);
		
	}
	
	public static void FCFS(double[] burstTime, double[] arrivalTime, double[] processId, int n) {
		
		double[] waitingTime = new double[n];
		double[] turnAroundTime = new double[n];
		
		double[] completionTime = new double[n];
		
		double tat = 0; double wt=0; 
		
		completionTime[0] = burstTime[0];
		
		for(int i=1;i<n;i++) {
			completionTime[i] = completionTime[i-1] + burstTime[i];
			//System.out.println(completionTime[i]);
		}
		
		for(int i=0;i<n;i++) {
			turnAroundTime[i] = completionTime[i] - arrivalTime[i];
			waitingTime[i] = turnAroundTime[i] - burstTime[i];
			//System.out.println(turnAroundTime[i]);
			//System.out.println(waitingTime[i]);
		}
		
		for(int i=0;i<n;i++) {
			tat += turnAroundTime[i];
			wt +=waitingTime[i];
		}
		
		wt = wt/n; tat = tat/n;
		
		display(wt, tat, turnAroundTime, processId);
	
	}

	public static void display(double wt, double tat, double[] turnAroundTime, double[] processId){
		
		System.out.println("Average waiting time: " + wt + " ms");

		for(int i=0;i<n;i++) {
			System.out.println("Turn around time for " + (int)processId[i] + " process: " + turnAroundTime[i]);
		}
	
		System.out.println("Average turn around time: " + tat + " ms");	
	
	}
	
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
    
	static Random rn = new Random();

	public static double randomPriority() {
		
		return 1+(11-1)*rn.nextDouble();
	}

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		ReaderPriorityScheduling.init(System.in);
		System.out.println("Enter time: ");
		
		int time = ReaderPriorityScheduling.nextInt();
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
		 
		 n = arrivalTime.length;
		 
		 double[] priority = new double[n];
		 for(int i=0;i<n;i++) {
			 priority[i] =(int) randomPriority();
			 System.out.println(priority[i]);
		 }
		
	
		Priority(burstTime, arrivalTime, priority, n);

	}

}
