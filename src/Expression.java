import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;

public class Expression extends ExpressionTree {



	public String fullyParenthesized() { // a tree calls this function
		BNode<String> tempRoot = (BNode<String>) this.root; // get the root of this tree

		return fullyParenthesized(tempRoot);// return a string in an in-order traversal of the tree.

	}

	private String fullyParenthesized(BNode<String> tempRoot) {
		if (tempRoot == null) { // return when reach to a null
			return "";
		}
		if (tempRoot.getLeft() == null && tempRoot.getRight() == null) {
			// return the data in the leaf when reach to a leaf, a leaf does not have left
			// and right children.
			return tempRoot.data;
		}
		String result = ""; //
		result = result + "(" + fullyParenthesized(tempRoot.getLeft());
		// add the first outer pair parenthesis and calls on the left of the root, and
		// add this left piece data to the string.

		result = result + tempRoot.data;
		// once took care of left children, add the middle, or the internal node to the
		// string.
		result = result + fullyParenthesized(tempRoot.getRight()) + ")";
		// and last, takes care the right children of the leaf.
		return result;
	}

	public Expression(String s) {
		super();

		String regExOfSpace = "\\s+"; // the input string are expected to have space separated in between each operand
		// and operators.
		String regExOfOperations = "\\p{Punct}"; // decide the whether the string is a operator or not.
		s = s.replace("(", " ( ");
		s = s.replace(")", " ) ");
		s = s.replace("+", " + ");
		s = s.replace("-", " - ");
		s = s.replace("*", " * ");
		s = s.replace("/", " / ");
		String[] sNoSpace = s.split(regExOfSpace); // split the each time a space happen is met

		ArrayDeque<String> numbers = new ArrayDeque<String>(); // arraydeque will hold numbers.
		ArrayList<String> operations = new ArrayList<String>(); // dynamic size array to hold operators.

		for (int i = 0; i < sNoSpace.length; i++) {
			// add into correspond arraydeque or arraylist based on what they are.
			if (sNoSpace[i].matches(regExOfOperations)) {
				operations.add(sNoSpace[i]);
			} else {
				numbers.addFirst(sNoSpace[i]);
			}
		}
		
		if (operations.contains("(")) {

			String[] tempOperationArray = operations.toArray(new String[0]);
			// convert the arraylist to array for traversal
//			if (tempOperationArray[0].equals("(") && tempOperationArray[tempOperationArray.length - 1].equals(")")) {
//				tempOperationArray = Arrays.copyOfRange(tempOperationArray, 1, tempOperationArray.length - 1);
//			}

			int indexOfCurrent = checkPrecedenceWithParenthesis(tempOperationArray);
			// a method to locate where to start the first the root of the tree.
			root = new BNode<String>(tempOperationArray[indexOfCurrent], null, null, null);
			// instantiate the root with this element in operator array.
			buildTreeWithParenthesis((BNode<String>) root, tempOperationArray, numbers, indexOfCurrent,
					tempOperationArray.length);
			// call a helper method

		} else {

			String[] tempOperationArray = operations.toArray(new String[0]);
			// convert the arraylist to array for traversal
			int indexOfCurrent = checkPrecedenceWithoutParenthesis(tempOperationArray);
			// a method to locate where to tart/ the first/ the
			// root of the tree.
			root = new BNode<String>(tempOperationArray[indexOfCurrent], null, null, null);
			// instantiate the root with this
			// element in operator array.
			buildTree((BNode<String>) root, tempOperationArray, numbers, indexOfCurrent, tempOperationArray.length);
			// call a helper method
		}

	}

	private int checkPrecedenceWithParenthesis(String[] array) { // check where is the next precedence in this array.
		int pre = 5; // precedence meter,
		int indexOfCurrent = array.length - 1; // location of target.
		int leftParenthesisCounter = 0;
		int rightParenthesisCounter = 0;
		int previousPrecedence = array.length - 1;
		;
		int i;
		String[] subArray;
		if (array.length == 1 && (array[0].equals("(") || array[0].equals(")"))) {
			return -1;
		}
		for (int j = 0; j < array.length; j++) {
			if (array[j].equals("(")) {
				leftParenthesisCounter++;
			}
			if (array[j].equals(")")) {
				rightParenthesisCounter++;
			}

		}
		for (i = array.length - 1; i >= 0; i--) {
			if (array[i].equals(")")) {
				if (leftParenthesisCounter - rightParenthesisCounter == 0 && i != array.length - 1) {
					return previousPrecedence;
					
				}
				rightParenthesisCounter--;
				indexOfCurrent = i;

				pre = 4;
				
			}
			if (array[i].equals("(")) {

				leftParenthesisCounter--;
				previousPrecedence = indexOfCurrent;
				pre = 4;
				indexOfCurrent = i;
				if (leftParenthesisCounter - rightParenthesisCounter == 0) {
					
						indexOfCurrent = i - 1;
					break;

					
				}
			}

			if (array[i].equals("-")&& pre > 1) { // if encounter - or + sign, exit the loop and indexOfCurrent will be
				// return.
				if (leftParenthesisCounter - rightParenthesisCounter == 0) {

					previousPrecedence = indexOfCurrent;
					pre = 1;
					indexOfCurrent = i;
					break;
				} 
				else {

					previousPrecedence = indexOfCurrent;
					pre = 1;
					indexOfCurrent = i;

				}
			}
			if (array[i].equals("+") && pre > 1) {

				if (leftParenthesisCounter - rightParenthesisCounter == 0) {

					previousPrecedence = indexOfCurrent;
					pre = 1;
					indexOfCurrent = i;
					break;
				} else {

					previousPrecedence = indexOfCurrent;
					pre = 1;
					indexOfCurrent = i;

				}

			}
			if (array[i].equals("/") && pre > 3) {
				
					previousPrecedence = indexOfCurrent;
					pre = 3;
					indexOfCurrent = i;
				

			}
			if (array[i].equals("*") && pre > 3) {

				previousPrecedence = indexOfCurrent;
				pre = 3;
				indexOfCurrent = i;
			}
		}
		if (indexOfCurrent == -1) {
			indexOfCurrent = previousPrecedence;
		}
		return indexOfCurrent;
	}

	private void buildTreeWithParenthesis(BNode<String> root, String[] tempOperationArray, ArrayDeque<String> numbers,
			int currentIndex, int rightIndex) {
		
		// takes in the root, the array of operators, numbers arraydeque and a
		// currentIndex, which return by checkPrecedence method. and a right
		// index, or originally size of operator array - 1
		int currentIndexOfLeft;
		int currentIndexOfRight;// two index indicator for left and right sub arrays.
		
		BNode<String> leftNode = new BNode<String>(null, root, null, null);
		BNode<String> rightNode = new BNode<String>(null, root, null, null);
		// instantiate the left and right node and set their parents to root.

		root.setLeft(leftNode); // let root's left and right points to left and right nodes.
		root.setRight(rightNode);
		
		if (currentIndex < 0) {
			return;
		}

		if (rightIndex == 3 && tempOperationArray[0].equals("(") && tempOperationArray[rightIndex - 1].equals(")")) {
			// a unique case to take care of tree of height 1.
			rightNode.setData(numbers.poll());
			leftNode.setData(numbers.poll());
			return;
		}
		if (currentIndex == rightIndex || rightIndex <= 0) {    

			rightNode.setData(numbers.poll());
			leftNode.setData(numbers.poll());
			return;

		} else { // when height of the tree is 2 or more,
			
			if (currentIndex == 0 && rightIndex != 1) {  
                //if the found sign located at the most left index
				String[] rightSubArray = Arrays.copyOfRange(tempOperationArray, currentIndex + 1, rightIndex);
                // get the sub array from current index to the end of original array.
				if (rightSubArray.length == 3 && rightSubArray[0].equals("(")
						&& rightSubArray[rightSubArray.length - 1].equals(")")) {
					// a unique case to take care (x+y) situation
					leftNode.setData(numbers.pollLast());
					currentIndexOfLeft = checkPrecedenceWithParenthesis(rightSubArray);
					rightNode.setData(rightSubArray[currentIndexOfLeft]);
					buildTreeWithParenthesis(rightNode, rightSubArray, numbers, 0, 0);
				}
				else {
					
					
					currentIndexOfRight = checkPrecedenceWithParenthesis(rightSubArray);
					if (currentIndexOfRight == -1) {	
						//if right sub array is empty, just add left and right node to current root.
						rightNode.setData(numbers.poll());
						leftNode.setData(numbers.poll());
						
					}
					else {
					leftNode.setData(numbers.pollLast());
					// check the precedence of right sub array
					rightNode.setData(rightSubArray[currentIndexOfRight]);
					//set right node to that sign in the right sub array
					buildTreeWithParenthesis(rightNode, rightSubArray, numbers, currentIndexOfRight,
							rightSubArray.length);
					// recursive call the function of the right node and the right sub array
					}
				}
			}
			else if (currentIndex == rightIndex - 1) {
				//if the found sign located at the most right index
				String[] leftSubArray = Arrays.copyOfRange(tempOperationArray, 0, currentIndex);
				// get the sub array from current index to the end of original array.
				
				if (leftSubArray.length == 3 && leftSubArray[0].equals("(")
						&& leftSubArray[leftSubArray.length - 1].equals(")")) {
					// a unique case to take care (x+y) situation
					rightNode.setData(numbers.poll());
					currentIndexOfLeft = checkPrecedenceWithParenthesis(leftSubArray);
					leftNode.setData(leftSubArray[currentIndexOfLeft]);
					buildTreeWithParenthesis(leftNode, leftSubArray, numbers, 0, 0);
				} 
				else {
					
					currentIndexOfLeft = checkPrecedenceWithParenthesis(leftSubArray);
					// check the precedence of left sub array
		
					if (currentIndexOfLeft == -1) {	
						//if left sub array is empty, just add left and right node to current root.
						rightNode.setData(numbers.poll());
						leftNode.setData(numbers.poll());
						return;

					}
					rightNode.setData(numbers.poll());
					leftNode.setData(leftSubArray[currentIndexOfLeft]);
					buildTreeWithParenthesis(leftNode, leftSubArray, numbers, currentIndexOfLeft,
								leftSubArray.length);
					// add the left node to be operator and right node to be the number and keep
					// call to the left.
				}
			} 
			else {
				// if the found precedence is at the middle of the expression
				String[] rightSubArray = Arrays.copyOfRange(tempOperationArray, currentIndex + 1, rightIndex);
				// do the right sub array first
				
					currentIndexOfRight = checkPrecedenceWithParenthesis(rightSubArray);
					// get the precedence in the right sub array
					if (currentIndexOfRight == -1) {
						//if right sub array is empty, set it to be a number
						rightNode.setData(numbers.poll());
					}
					else {
						// recursive call on right node on the rest of right sub node
						rightNode.setData(rightSubArray[currentIndexOfRight]);
						buildTreeWithParenthesis(rightNode, rightSubArray, numbers, currentIndexOfRight, rightSubArray.length);
					}
				
				String[] leftSubArray = Arrays.copyOfRange(tempOperationArray, 0, currentIndex);
				
					currentIndexOfLeft = checkPrecedenceWithParenthesis(leftSubArray);
					// get the precedence in the left sub array
					if(currentIndexOfLeft == -1) {
						//if left sub array is empty, set it to be a number
						leftNode.setData(numbers.poll());
						return;
					} 
					else {
						// recursive call on left node on the rest of left sub node
						leftNode.setData(leftSubArray[currentIndexOfLeft]);
						buildTreeWithParenthesis(leftNode, leftSubArray, numbers, currentIndexOfLeft,
								leftSubArray.length);
					}
				
			}
		}
	}

	private int checkPrecedenceWithoutParenthesis(String[] array) { // check where is the next precedence in this array.
		int pre = 5; // precedence meter,
		int indexOfCurrent = 0; // location of target.
		int i;

		for (i = array.length - 1; i >= 0; i--) {

			if (array[i].equals("-")) { // if encounter - or + sign, exit the loop and indexOfCurrent will be return.
				pre = 1;
				indexOfCurrent = i; // index of current is updated everytime it sees a pre with lower value.
				break;
			} else if (array[i].equals("+") && pre > 1) {
				pre = 1;
				indexOfCurrent = i;
				break;
			} else if (array[i].equals("/") && pre > 3) {
				pre = 3;
				indexOfCurrent = i;
			} else if (array[i].equals("*") && pre > 3) {
				pre = 3;
				indexOfCurrent = i;
			}

		}
		return indexOfCurrent;
	}

	private void buildTree(BNode<String> root, String[] tempOperationArray, ArrayDeque<String> numbers,
			int currentIndex, int rightIndex) {

		// takes in the root, the array of operators, numbers arraydeque and a
		// currentIndex, which return by checkPrecedence method. and a right
		// index, or originally size of operator array - 1

		BNode<String> leftNode = new BNode<String>(null, root, null, null);
		BNode<String> rightNode = new BNode<String>(null, root, null, null);
		// instantiate the left and right node and set their parents to root.

		root.setLeft(leftNode); // let root's left and right points to left and right nodes.
		root.setRight(rightNode);

		if (currentIndex > rightIndex || rightIndex <= 0 || currentIndex < 0) {
			return;
		}

		root.setData(tempOperationArray[currentIndex]);
		if (rightIndex == 1) {
			// a unique case to take care of tree of height 1.
			rightNode.setData(numbers.poll());
			leftNode.setData(numbers.poll());
		} else { // when height of the tree is 2 or more,
			if (currentIndex == 0 && rightIndex != 1) {
				// this is the case of the first precedence is the first operator, which is the
				// best case. add all other node to the right of the root //

				leftNode.setData(numbers.pollLast());
				rightNode.setData(tempOperationArray[rightIndex - 1]);
				// let the left node be the number, and right node be the operator.

				buildTreeToRight(rightNode, tempOperationArray, numbers, rightIndex - 2, currentIndex + 1);
				// calls on a function that adds from the right most operator to currentindex +
				// 1(index 1)
				// call the function from the second most left index and all the way to the
				// middle. because the right subarray is all signs with same precedence.

			} else if (currentIndex == rightIndex - 1) {

				String[] leftSubArray = Arrays.copyOfRange(tempOperationArray, 0, currentIndex);
				currentIndex = checkPrecedenceWithoutParenthesis(leftSubArray);
				leftNode.setData(tempOperationArray[currentIndex]);
				rightNode.setData(numbers.poll());
				buildTree(leftNode, leftSubArray, numbers, currentIndex, leftSubArray.length);

				// add the left node to be operator and right node to be the number and keep
				// call to the right.
			} else { // most complicated case, havent figured out yet. requires check the next
				// precedence operator in the left of sub array
				// example will be 1 + 2 * 3 * 4 + 2 * 2 in this case, the most left + should be
				// the left node of the root.
				String[] leftSubArray = Arrays.copyOfRange(tempOperationArray, 0, currentIndex);
				String[] rightSubArray = Arrays.copyOfRange(tempOperationArray, currentIndex + 1, rightIndex - 1);

				leftNode.setData(tempOperationArray[currentIndex - 1]);
				rightNode.setData(tempOperationArray[rightIndex - 1]);
				buildTreeToRight(rightNode, rightSubArray, numbers, rightSubArray.length - 1, 0);

				currentIndex = checkPrecedenceWithoutParenthesis(leftSubArray);
				buildTree(leftNode, leftSubArray, numbers, currentIndex, leftSubArray.length);

			}
		}
	}

	private void buildTreeToRight(BNode<String> root, String[] tempOperationArray, ArrayDeque<String> numbers,
			int currentIndex, int endIndex) {
//		System.out.println(root);

		BNode<String> leftNode = new BNode<String>(null, root, null, null);
		BNode<String> rightNode = new BNode<String>(null, root, null, null);
		root.setLeft(leftNode);
		root.setRight(rightNode); // right side of the array will be all signs with same precedence

		if (currentIndex < endIndex) { // once reach the end index, put the leaf data to the node.
			rightNode.setData(numbers.poll());
			leftNode.setData(numbers.poll());
			return;
		} else {
			leftNode.setData(tempOperationArray[currentIndex]);
			rightNode.setData(numbers.poll());
			buildTreeToRight(leftNode, tempOperationArray, numbers, currentIndex - 1, endIndex); // keep call to left.
		}
	}

	public double evaluate() { // adapt the code from lab 14
		String exprString = this.fullyParenthesized();

		if (exprString == null || exprString == "") {
			return 0;
		}

		exprString = exprString.replace("(", "( "); // format all the operations for future split with space
		exprString = exprString.replace(")", " )");
		exprString = exprString.replace("+", " + ");
		exprString = exprString.replace("-", " - ");
		exprString = exprString.replace("*", " * ");
		exprString = exprString.replace("/", " / ");
		String[] resultArray = exprString.split("\\s+");

		double result; // the final result on the top of the arraydeque

		ArrayDeque<Double> values = new ArrayDeque<>(); // two arraydeque for result and value.
		ArrayDeque<String> operas = new ArrayDeque<>();

		for (int i = 0; i < resultArray.length; i++) {
			if (resultArray[i].equals(" "))
				continue; // check each condition.

			else if (resultArray[i].equals("("))
				continue; // perform different steps based on what operator it is.
			// skip left parenthesis
			else if (resultArray[i].equals("+")) {
				operas.addFirst(resultArray[i]);
			} else if (resultArray[i].equals("-")) { // add all the operators on to the arraydeque
				operas.addFirst(resultArray[i]);
			} else if (resultArray[i].equals("*")) {
				operas.addFirst(resultArray[i]);
			} else if (resultArray[i].equals("/")) {
				operas.addFirst(resultArray[i]);
			} else if (resultArray[i].equals(")")) {
				// everytime it is a right parenthesis, we start to solve what we had.

				String oper = operas.poll();
				result = values.poll();
				if (oper.equals("+")) {
					result = values.poll() + result; // based on the operator, we do plus, minus, time, divide on the
					// result.
				} else if (oper.equals("-")) {
					result = values.poll() - result;
				} else if (oper.equals("*")) {
					result = values.poll() * result;
				} else if (oper.equals("/")) {
					result = values.poll() / result;
				}
				values.addFirst(result); // put that result back to the top and ready to use if next ")" appear.

			} else
				values.addFirst(Double.parseDouble(resultArray[i])); // a case that contains no operator, which simply
			// return the value.
		}

		return values.poll(); // once we finish the for loop the result is always on the top of the
		// arraydeque.

	}
}
