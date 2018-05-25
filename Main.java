import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

	    //String observations = "MRNGISPIIIDNTNLHAWEMKPYAVMALENNYEVIFREPDTRWKFNVQELARRNIHGVSREKIHRMKERYEHDVTFHSVLHAEKPSRMNRNQDRNNALPSNNARYWNSYTEFPNRRAHGGFTNESSYHRRGGCHHGY";
	    String observations;
		final String[] states = { "B", "L", "M" };
		final float[] iniP = { 1f/3, 1f/3, 1f/3};
		final float[][] tranP = { { 4f/5, 0.2f/5, 0.8f/5 },
				{ 0.3f/8, 7.0f/8, 0.7f/8}, { 0.5f/7, 0.5f/7, 6.0f/7 } };
		final float[][] emiP = { {0.6f/8,0.4f/12}, {0.2f/8,0.8f/12}, {0.5f/8,0.5f/12} };

		
		/*Viterbi v = new Viterbi(observations, states, iniP, tranP, emiP);	
		String[] results = v.evaluate();
		System.out.println(Arrays.toString(results).replaceAll(", ", ""));
		
		float[][] space = v.getSpace();
		for(int i = 0; i< space.length; i++) {
			System.out.println(Arrays.toString(space[i]));
		}
		
		int[][] spaceT = v.getSpaceT();
		for(int i = 0; i< spaceT.length; i++) {
			System.out.println(Arrays.toString(spaceT[i]));
		}*/


		
		//read fa file, analysis each sequence, and write the result into a new txt file
			String filePath = "hw3_proteins.fa";
			String writePath = "hw3_proteinsRegions.txt";
		    try{
		      BufferedReader br = new BufferedReader(new FileReader(filePath));
	          BufferedWriter bw = new BufferedWriter(new FileWriter(writePath));
		      try {
		        StringBuilder sb = new StringBuilder();
		        String line = br.readLine();				
		        bw.write(line);	
		        bw.newLine();
		        line = br.readLine();				        
		        while (line != null) {     
			          if((!line.startsWith(">"))){
				            sb.append(line);				            
				            line = br.readLine();}
			          else{    	    
			        	    observations = sb.toString();				     
			        	    Viterbi v = new Viterbi(observations, states, iniP, tranP, emiP);        		
			        		String[] results = v.evaluate();
			        		bw.write(Arrays.toString(results).replaceAll(", ", ""));
			        		bw.newLine();
			        		
			        		/*
			        		//test case , v value
			        		float[][] space = v.getSpace();
			        		for(int i = (space.length)/3; i< space.length; i++) {
			        			bw.write(Arrays.toString(space[i]));
			        			bw.newLine();
			        		} // test end */
			        		
			        		bw.write(line);
			        	    bw.newLine();
			        		
				            sb = new StringBuilder();
				            line = br.readLine();	            
				            }
				        }			        
				        observations = sb.toString();				     
		        	    Viterbi v = new Viterbi(observations, states, iniP, tranP, emiP);        		
		        		String[] results = v.evaluate();
		        		bw.write(Arrays.toString(results).replaceAll(", ", ""));
		        		bw.newLine();
				        		       
				      } finally {
				        br.close();
				        bw.close();
				      }
				    }catch(Exception e){
				      e.printStackTrace();}	

		/*float[][] space = v.getSpace();
		for(int i = 0; i< space.length; i++) {
			System.out.println(Arrays.toString(space[i]));
		}*/
		
		//read fa file, analysis each sequence, and write the result into a new txt file
	    try{
	      BufferedReader br = new BufferedReader(new FileReader(writePath));
	      ArrayList<String> proteinNames = new ArrayList<String>();
	      ArrayList<String> stateSequences = new ArrayList<String>();
	      
	      try {
	        String line = br.readLine();	
	        proteinNames.add(line);
	        line = br.readLine();	
	        stateSequences.add(line.replaceAll("^.|.$", ""));
	        
	        //line = line.replaceAll("^.|.$", "");
	        //System.out.println(line);
	        
	        while (line != null) {     
		          if((line.startsWith(">"))){
		        	  	proteinNames.add(line);			            
			            line = br.readLine();
			            }
		          else{    	    
		        	  	stateSequences.add(line.replaceAll("^.|.$", ""));
		        	  	line = br.readLine();	
		        		}
			        }			        
			     
	        	    protein p = new protein(proteinNames,stateSequences);        		
	        		p.evaluateLBR();  //c1 
	        		p.evaluateLMR();  //c2
			        		       
			      } finally {
			        br.close();
			      }
			    }catch(Exception e){
			      e.printStackTrace();}	
	}
	


}
