package q3b;

import java.util.Arrays;

public class Viterbi {

	private String[] states;
	private String[] output;
	private char[] sequence;
	private float[][] space;
	private int[][] spaceT;
	private float[] iniP;
	private float[][] tranP;
	private float[][] emiP;
	
	public Viterbi(String observation, String[] states, float[] iniP, float[][] tranP, float[][] emiP) {
		this.states = states;
		this.output = new String[observation.length()];
		this.sequence = observation.toCharArray();
		this.space = new float[states.length][sequence.length];
		this.spaceT = new int[states.length][sequence.length];
		this.iniP = iniP;
		this.tranP = tranP;
		this.emiP = emiP;
	}
	
	private boolean hydrophobic(char c) {
		return (c=='A' || c=='V' || c=='I' || c=='L' || c=='M' || c=='F' || c=='Y' || c=='W');
	}
	
	private void initializeSpace() {
		for(int i = 0; i<states.length; i++){
			if(hydrophobic(sequence[0]))
				space[i][0] = iniP[i] * emiP[i][0];
			else
				space[i][0] = iniP[i] * emiP[i][1];
			
			spaceT[i][0] = 0;
		}
	}
	
	
	private void fillSpace() {
		
		//m = new int[sequence.length]; // store multiplying factors
		//Arrays.fill(m, 0);
		
		for(int i = 1; i < sequence.length; i++) {
			for(int j = 0; j < states.length; j++) {
				float max = 0.0f;	
				int pos = 0;
				for(int k = 0; k < states.length; k++){
					if(max<space[k][i-1] * tranP[k][j]){
						max = space[k][i-1] * tranP[k][j];
						pos = k;
					}
				}
					//max = Math.max(max, space[k][i-1] * tranP[k][j]);	
				if(hydrophobic(sequence[i]))
					space[j][i] = max * emiP[j][0];
				else
					space[j][i] = max * emiP[j][1];
			spaceT[j][i]=pos;
			}
			
			float max = 0.0f;
			for(int m=0; m<states.length; m++){
				max = Math.max(max, space[m][i]);
			}
			while(max<0.001){
				for(int k = 0; k < states.length; k++)
					space[k][i]= space[k][i]*10;
				    max *=10;
			}
			
		}
//			System.out.println();
//			System.out.println("m array is: " + Arrays.toString(m));
	}
	
	private int getLastState() {
		int last = 0;
		for(int i = 1; i < states.length; i++)
			if(space[last][sequence.length-1] < space[i][sequence.length-1])
				last = i;
		return last;
	}
	
	private void constructOutput() {
		int l = getLastState();
		output[sequence.length-1] = states[l];		
		//check if spaceT is well compiled
		
		for(int i=sequence.length-2; i>=0;i--){
			output[i] = states[spaceT[l][i+1]];
			l = spaceT[l][i+1];		
		}
		

			
			/*for(int j = 0; j < 3; j++) {
				if(hydrophobic(sequence[i])) {
					if(space[j][i] * tranP[j][l] * emiP[l][0] == space[l][i+1]) {
						l = j;
						break;
					}
				else
					if(space[j][i] * tranP[j][l] * emiP[l][1] == space[l][i+1]) {
						l = j;
						break;
					}
				}
			}
			output[i] = states[l]; */
	}

	
	public float[][] getSpace() {
		return this.space;
	}
	
	public int[][] getSpaceT() {
		return this.spaceT;
	}
	
	public String[] evaluate() {
		try {
			initializeSpace();
			fillSpace();
			constructOutput();
			return output;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return null;
		}
	}

}

