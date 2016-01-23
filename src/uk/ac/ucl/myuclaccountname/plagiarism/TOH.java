package uk.ac.ucl.myuclaccountname.plagiarism;

import java.util.*;


class TOH {

	static final int STACKSNO = 3;

	static int discNo;
	//static int moves;

	//static int soMany[] = new int[STACKSNO];
	static int soManyA = 0;
	static int soManyB = 0;
	static int soManyC = 0;

	//static private Stack<Integer> stacks[] = new Stack[STACKSNO];
	static private Stack<Integer> A = new Stack<Integer>();
	static private Stack<Integer> B = new Stack<Integer>();
	static private Stack<Integer> C = new Stack<Integer>();
	
	static private int solveCase2 ( Stack<Integer> origin, Stack<Integer> middle, Stack<Integer> target, int noOfProcedureCalls ) {

		//pack array for no reason
		@SuppressWarnings("unchecked")
		Stack<Integer> stacks[] = new Stack[STACKSNO];

		stacks[0] = origin;
		stacks[1] = middle;
		stacks[2] = target;
		
		//1	0
		//2	0
		//2	1
		int i = 1;
		
		stacks[i].addElement ( Integer.valueOf(stacks[i-1].pop()) );
		//moves++;
		noOfProcedureCalls = output(noOfProcedureCalls);
		stacks[++i].addElement ( Integer.valueOf(stacks[i-2].pop()) );
		//moves++;
		noOfProcedureCalls = output(noOfProcedureCalls);
		stacks[i].addElement ( Integer.valueOf(stacks[--i].pop()) );
		//moves++;
		noOfProcedureCalls = output(noOfProcedureCalls);
		
		//noOfProcedureCalls++;
		return noOfProcedureCalls;
	}

	static private int solveCaseGeneral ( int discNo, Stack<Integer> origin, Stack<Integer> middle, int noOfProcedureCalls, Stack<Integer> target ) {

		if ( discNo < 5 ) {
			if ( ( discNo % 2 ) == 0 ) {

				noOfProcedureCalls = solveCase2 ( origin, middle, target, noOfProcedureCalls );
				--discNo;
				if ( discNo == 1 ) {
					//noOfProcedureCalls++;
					return noOfProcedureCalls;
				}

				middle.addElement(Integer.valueOf(origin.pop()) );
				//moves++;
				noOfProcedureCalls = output(noOfProcedureCalls);

				noOfProcedureCalls = solveCase2 ( target, origin, middle, noOfProcedureCalls );
				target.addElement(Integer.valueOf(origin.pop()) );
				//moves++;
				noOfProcedureCalls = output(noOfProcedureCalls);
				noOfProcedureCalls = solveCaseGeneral ( discNo, middle, origin, noOfProcedureCalls, target );
			} else {

				noOfProcedureCalls = solveCase2 ( origin, target, middle, noOfProcedureCalls );
				--discNo;
				target.addElement(Integer.valueOf(origin.pop()) );
				//moves++;
				noOfProcedureCalls = output(noOfProcedureCalls);
				noOfProcedureCalls = solveCase2 ( middle, origin, target, noOfProcedureCalls );
			}
		} else if ( discNo >= 5 ) {
			noOfProcedureCalls = solveCaseGeneral ( discNo - 2, origin, middle,noOfProcedureCalls, target );
			middle.addElement(Integer.valueOf(origin.pop()) );
			//moves++;
			noOfProcedureCalls = output(noOfProcedureCalls);
			noOfProcedureCalls = solveCaseGeneral ( discNo - 2, target, origin,noOfProcedureCalls, middle );
			target.addElement(Integer.valueOf(origin.pop()) );
			//moves++;
			noOfProcedureCalls = output(noOfProcedureCalls);
			noOfProcedureCalls = solveCaseGeneral ( discNo - 1, middle, origin,noOfProcedureCalls, target );
		}
		//noOfProcedureCalls++;
		return noOfProcedureCalls;
	}

	static private int output(int noOfProcedureCalls) {
		if ( soManyA != A.size() ||
				soManyB != B.size() ||
				soManyC != C.size() ) {

			if ( A.size() - soManyA == 1 ) {
				if ( B.size() - soManyB == -1 ) {
					System.out.println ( "Move Disc " + A.peek() + " From B To A" );
				} else {
					System.out.println ( "Move Disc " + A.peek() + " From C To A" );
				}
			} else if ( B.size() - soManyB == 1 ) {
				if ( A.size() - soManyA == -1 ) {
					System.out.println ( "Move Disc " + B.peek() + " From A To B" );
				} else {
					System.out.println ( "Move Disc " + B.peek() + " From C To B" );
				}
			} else {
				if ( A.size() - soManyA == -1 ) {
					System.out.println ( "Move Disc " + C.peek() + " From A To C" );
				} else {
					System.out.println ( "Move Disc " + C.peek() + " From B To C" );
				}
			}
			soManyA = A.size();
			soManyB = B.size();
			soManyC = C.size();
		}

		//pack array for no reason
		@SuppressWarnings("unchecked")
		Stack<Integer> stacks[] = new Stack[STACKSNO];

		stacks[0] = A;
		stacks[1] = B;
		stacks[2] = C;

		for(int i = 0; i < STACKSNO; i++)
			System.out.print ( stacks[i].toString() + " , " );

		noOfProcedureCalls++;
		return noOfProcedureCalls;
	}

	public static void main(String[] args) {

		int noOfProcedureCalls = -1;
		discNo = Integer.parseInt(args[0]);
		//moves = 0;

		if ( discNo <= 1 || discNo >= 10 ) {
			System.out.println ( "Enter between 2 - 9" );
			System.exit(1);
		}

		int i = discNo;
		while(i >= 1)
			A.addElement(Integer.valueOf(i--) );

		soManyA = A.size();
		soManyB = B.size();
		soManyC = C.size();

		noOfProcedureCalls = output(noOfProcedureCalls);
		noOfProcedureCalls = solveCaseGeneral ( discNo, A, B, noOfProcedureCalls, C );
		System.out.println ( "Total moves = " + /*moves + " procedure calls: " + */noOfProcedureCalls);
		

	}
}