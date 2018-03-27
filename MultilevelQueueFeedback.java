package schedulingAlgorithm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.StringTokenizer;

class ReaderMQ {
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


public class MultilevelQueueFeedback {
	static int n; 
	
	static double[] arrivalTime;
	static double[] burstTime;
	static double[] processedId;
	static double[] priority;
	
	static int n1; static int n2;
	
	double averageTotalWaitingTime; double averageTotalTurnAroundTime;
	static double averageWaitingTime1; static double averageTotalTurnAroundTime1;
	static double averageWaitingTime2; static double averageTotalTurnAroundTime2;
	static double[] finalTurnAroundTime1 = new double[n1]; 
	static double[] finalTurnAroundTime2 = new double[n2];
	static double[] processId1 = new double[n1];
	static double[] processId2 = new double[n2];
	
	public static void roundRobin(double[] burstTime, double[] arrivalTime,int n,double quantum) {
		
		double wt = 0 ; double tat = 0; 
		
		double[] waitingTime = new double[n];
		double[] turnAroundTime = new double[n];
		processId2 = new double[n];
		
		for(int i=0;i<n;i++) {
			processId2[i] = i;
		}
			
		double rem_bt[] = new double[n];
		   for (int i = 0 ; i < n ; i++) {
			   //rem_bt[i] =  burstTime[i];
			   //System.out.println(rem_bt.length +" "+ burstTime.length);
		   }
	 
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
			 finalTurnAroundTime2[i] = turnAroundTime[i];
		 }
		 
		 for(int i=0;i<n;i++) {
				tat += turnAroundTime[i];
				wt +=waitingTime[i];
		 }
		 
		 averageTotalTurnAroundTime2 = tat/n;
		 averageWaitingTime2 = wt/n;		
		
	}
	public static void FCFS(double[] burstTime, double[] arrivalTime,int n) {
		
		double[] waitingTime = new double[n];
		double[] turnAroundTime = new double[n];
	
		double[] completionTime = new double[n];
		double[] processId = new double[n];
	
		for(int i=0;i<n;i++) {
			processId[i] = i;
		}
		
		double tat = 0; double wt=0; 
	
		completionTime[0] = burstTime[0];
	
		for(int i=1;i<n;i++) {
			completionTime[i] = completionTime[i-1] + burstTime[i];
			//System.out.println(completionTime[i]);
		}
	
		for(int i=0;i<n;i++) {
			turnAroundTime[i] = completionTime[i] - arrivalTime[i];
			finalTurnAroundTime1[i] = turnAroundTime[i];
			waitingTime[i] = turnAroundTime[i] - burstTime[i];
			//System.out.println(turnAroundTime[i]);
			//System.out.println(waitingTime[i]);
		}
		//System.out.println(finalTurnAroundTime1.length +" " + turnAroundTime.length);
	
		for(int i=0;i<n;i++) {
			tat += turnAroundTime[i];
			wt +=waitingTime[i];
		}
	
		averageWaitingTime1 = wt/n; averageTotalTurnAroundTime1 = tat/n;

	}
 
	public static void display(double wt, double tat, double[] turnAroundTime, double[] processId){
	 
		System.out.println("Average waiting time: " + wt + " ms");
		
		for(int i=0;i<n1;i++) {
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


	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		ReaderMQ.init(System.in);
		
		System.out.println("Enter time: ");
		
		int time = ReaderMQ.nextInt();
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
		double averageBurstTime = 0;
		
		 double[] burstTime = new double[burstTimeAL.size()];
		 for (int i = 0; i < burstTime.length; i++) {
		    burstTime[i] = burstTimeAL.get(i).doubleValue();  // java 1.4 style
		    averageBurstTime += burstTime[i];
		 }
		 
		 double[] arrivalTime = new double[arrivalTimeAL.size()];
		 for (int i = 0; i < arrivalTime.length; i++) {
		    arrivalTime[i] = arrivalTimeAL.get(i).doubleValue();  // java 1.4 style
		 }
		 averageBurstTime = averageBurstTime/n;
		
		final double quantum = 2;
		
		ArrayList<Double> arrivalTimeAL1 = new ArrayList<Double>();
		ArrayList<Double> burstTimeAL1 = new ArrayList<Double>();
		
		ArrayList<Double> arrivalTimeAL2 = new ArrayList<Double>();
		ArrayList<Double> burstTimeAL2 = new ArrayList<Double>();
		
		
		for(int i=0;i<n;i++) {
			if(burstTime[i]>=averageBurstTime) {
				arrivalTimeAL1.add(arrivalTime[i]);
				burstTimeAL1.add(burstTime[i]);
			}
			else if(burstTime[i]<averageBurstTime){
				arrivalTimeAL2.add(arrivalTime[i]);
				burstTimeAL2.add(burstTime[i]);
			}
		}
		
		n1 = burstTimeAL1.size();
		n2 = burstTimeAL2.size();
		finalTurnAroundTime1 = new double[n1]; 
		finalTurnAroundTime2 = new double[n2];
		
		double[] arrivalTime1 = new double[arrivalTimeAL1.size()];
		double[] burstTime1 = new double[burstTimeAL1.size()];
		
		double[] arrivalTime2 = new double[arrivalTimeAL2.size()];
		double[] burstTime2 = new double[burstTimeAL2.size()];
		
		for(int i=0;i<n1;i++) {
			arrivalTime1[i] = arrivalTimeAL1.get(i).doubleValue();
			burstTime1[i] = burstTimeAL1.get(i).doubleValue();
		}
		
		for(int i=0;i<n2;i++) {
			arrivalTime2[i] = arrivalTimeAL2.get(i).doubleValue();
			burstTime2[i] = burstTimeAL2.get(i).doubleValue();
		}
		
		FCFS(burstTime1, arrivalTime1, n1);
		roundRobin(burstTime2, arrivalTime2, n2, quantum);
		
		double[] processId = new double[burstTimeAL.size()];
		
		//double finalAverageWaitingTime = (averageWaitingTime1 + averageWaitingTime2)/2; 
		//double finalTurnAroundTime = (averageTotalTurnAroundTime1 + averageTotalTurnAroundTime2)/2;
		
		processId1 = new double[arrivalTime1.length];
		processId2 = new double[arrivalTime2.length];
		
		display(averageWaitingTime1, averageTotalTurnAroundTime1, finalTurnAroundTime1,processId1);
		display(averageWaitingTime2, averageTotalTurnAroundTime1,finalTurnAroundTime1, processId2);
		
		
	}

}
