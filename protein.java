import java.util.ArrayList;

public class protein {
	private ArrayList<String> proteinNames;
	private ArrayList<String> stateSequences;
	private int[] LBR;
	private double[] LMR;
	
	public protein(ArrayList<String> proteinNames2, ArrayList<String> stateSequences){
		this.proteinNames = proteinNames2;
		this.stateSequences = stateSequences;
		this.LBR = new int[stateSequences.size()];
		this.LMR = new double[stateSequences.size()];
	}
	
	private int findLBR(String s){
		
		int max = 0;
		int ctr = 0;
		for(int i=0; i<s.length(); i++){
			if(s.charAt(i) == 'B'){
				ctr++;
			}
			else{
				if(max<ctr)
					max = ctr; 
				ctr = 0;
			}
		}
		return max;
	}
	
	private void constructLBRseq(){		
		for(int i=0; i<stateSequences.size(); i++){
			LBR[i] = findLBR(stateSequences.get(i));
		}
	}
	
	//there is only 1 such protein
	private void findProteinOfLBR(){
		
		int max=0;
		String target = "";
		for(int i=0; i<LBR.length; i++){
			if(max < LBR[i]){ 
				max = LBR[i];
				target = proteinNames.get(i);
			}
		}
		System.out.println("The protein that contains the longest hydrophobic region is:" + target);
	}
	
	public void evaluateLBR() {
		try {
			constructLBRseq();
			findProteinOfLBR();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private double findAA(String s){
		double ctr=0.0;
		for(int i=0; i<s.length();i++){
			if(s.charAt(i) == 'M'){
				ctr++;
			}
		}
		return ctr;
	}
	
	private void constructLMR(){
		for(int i=0; i<stateSequences.size(); i++){
			LMR[i] = findAA(stateSequences.get(i))/stateSequences.get(i).length();
		}
	}
	
	//there are multiple such proteins
	private void findProteinOfLMR(){
		double max=0.0;
		for(int i=0; i<LMR.length; i++){
			if(max<LMR[i])
				max=LMR[i];			
		}
		for(int i=0; i<LMR.length; i++){
			if(max==LMR[i])
				System.out.println(i);//proteinNames.get(i));
		}
		
	}
	
	public void evaluateLMR() {
		try {
			constructLMR();
			findProteinOfLMR();
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
