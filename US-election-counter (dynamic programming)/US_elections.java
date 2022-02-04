import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;

public class US_elections {

	public static int solution(int num_states, int[] delegates, int[] votes_Biden, int[] votes_Trump, int[] votes_Undecided){
		int delegates_needed = 0;
		int[] votesNeeded = new int[num_states];
		
		for (int d : delegates) {delegates_needed += d;}
		delegates_needed = delegates_needed/2 + 1;
		
		for (int s = 0; s < num_states; s++){
			votesNeeded[s] = votesNeeded(votes_Biden[s], votes_Trump[s], votes_Undecided[s]);
		}
		
		int[][] M = new int[num_states+1][delegates_needed+1];
		for (int c = 0; c <= delegates_needed; c++) {M[0][c] = -1;}
		
		int limit = 0;
		for (int s = 1; s <= num_states; s++) {
			limit += delegates[s-1];
				for (int d = 1; d <= delegates_needed; d++) {
					if (d > limit) { //only matters before "enough" states reached
						M[s][d] = -1;
						continue;
					} 
					
					int above = M[s-1][d];
					if (d <= delegates[s-1]) {
						if (M[s-1][d] < votesNeeded[s-1] && M[s-1][d] >= 0 && votesNeeded[s-1] >= 0) {
							M[s][d] = above;
						} else if (votesNeeded[s-1] < 0) {
							M[s][d] = above;
						} else {
							M[s][d] = votesNeeded[s-1];
						}				
					} else {
						int to_win = M[s-1][d-delegates[s-1]];
						if (to_win == -1 || votesNeeded[s-1] == -1) {
							M[s][d] = above;
						} else {
							int opt1 = votesNeeded[s-1] + to_win;
							int opt2 = above;
							if (opt1 > opt2 && opt2 >= 0) {
								M[s][d] = opt2;
							} else {
								M[s][d] = opt1;
							}
						}
					}

				}
		}
		
		return M[num_states][delegates_needed];
	}

	// returns -1 if Biden cannot beat Trump in this state, otherwise returns number of voters needed 
	private static int votesNeeded(int biden, int trump, int undecided) {
		int diff = trump - biden;
		if (diff >= undecided) {
			return -1;
		} else if (diff < 0) {
			if (diff * -1 > undecided) {return 0;}
			return (undecided + diff)/2 + 1;
		} else {
			return diff + (undecided - diff)/2 + 1;
		}
	}

	public static void main(String[] args) {
		try {
			//String path = args[0];
			String path = "/Users/danielkorsunsky/Desktop/McGill/Fall 2020/COMP 251/Final Project/open_case_6.txt";
			File myFile = new File(path);
			Scanner sc = new Scanner(myFile);
			int num_states = sc.nextInt();
			int[] delegates = new int[num_states];
			int[] votes_Biden = new int[num_states];
			int[] votes_Trump = new int[num_states];
			int[] votes_Undecided = new int[num_states];	
			for (int state = 0; state<num_states; state++){
				delegates[state] =sc.nextInt();
				votes_Biden[state] = sc.nextInt();
				votes_Trump[state] = sc.nextInt();
				votes_Undecided[state] = sc.nextInt();
			}
			sc.close();
			int answer = solution(num_states, delegates, votes_Biden, votes_Trump, votes_Undecided);
			System.out.println(answer);
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
	}

}