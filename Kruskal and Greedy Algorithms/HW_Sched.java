import java.util.*;

class Assignment implements Comparator<Assignment>{
	int number;
	int weight;
	int deadline;
	
	
	protected Assignment() {
	}
	
	protected Assignment(int number, int weight, int deadline) {
		this.number = number;
		this.weight = weight;
		this.deadline = deadline;
	}
	
	/**
	 * This method is used to sort to compare assignment objects for sorting. 
	 * Assignments are sorted in increasing deadline order. Within deadlines,
	 * assignments are sorted in increasing weight order.
	 */
	@Override
	public int compare(Assignment a1, Assignment a2) {
		
		if (a1.deadline > a2.deadline) {return 1;}
		if (a1.deadline < a2.deadline) {return -1;}
		
		if (a1.weight > a2.weight) {return 1;}
		if (a1.weight < a2.weight) {return -1;}
		
		return 0; //deadlines and weights are equal
	}
}

public class HW_Sched {
	ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
	int m;
	int lastDeadline = 0;
	
	protected HW_Sched(int[] weights, int[] deadlines, int size) {
		for (int i=0; i<size; i++) {
			Assignment homework = new Assignment(i, weights[i], deadlines[i]);
			this.Assignments.add(homework);
			if (homework.deadline > lastDeadline) {
				lastDeadline = homework.deadline;
			}
		}
		m = size;
	}
	
	
	/**
	 * 
	 * @return Array where output[i] corresponds to the assignment 
	 * that will be done at time i.
	 */
	public int[] SelectAssignments() {		
		//Sort assignments
		//Order will depend on how compare function is implemented
		Collections.sort(Assignments, new Assignment());
		
		// If homeworkPlan[i] has a value -1, it indicates that the 
		// i'th time-slot in the homeworkPlan is empty
		//homeworkPlan contains the homework schedule between now and the last deadline
		
		int[] homeworkPlan = new int[lastDeadline];
		for (int i=0; i < homeworkPlan.length; ++i) {
			homeworkPlan[i] = -1;
		}
	
		//copy of Assignments list from which homework is removed after being selected
		ArrayList<Assignment> homeworks = (ArrayList<Assignment>) Assignments.clone();
		
		for (int i = homeworkPlan.length - 1; i >= 0; --i) {
			int number = -1;
			int heaviest = -1;
			Assignment assnToRemove = null;
			
			//find heaviest compatible assignment
			for (Assignment assn : homeworks) {
				if ((assn.deadline-1 >= i) && (assn.weight > heaviest)) {
					heaviest = assn.weight;
					number = assn.number;
					assnToRemove = assn;
				}
			}	
			
			if (number == -1) {continue;} //if no homework was compatible
			homeworks.remove(assnToRemove);
			homeworkPlan[i] = number;			
		}

		return homeworkPlan;
		
	}
}
	



