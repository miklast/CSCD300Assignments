import java.util.Scanner;



public class LinkedStack {
	private class Node {

		private Object data;
		private Node next;

      //Node constructor
		public Node(Object e, Node n) {
			this.data = e;
			this.next = n;
		}
	}
	
  protected Node top;
  protected int size;

  //Creates a LinkedStack
  public LinkedStack() {
    this.top = null;
    this.size = 0;
  }
  public int size() { 
   return size; 
  }
  
  public boolean isEmpty() {
    if (top == null) return true;
    return false;
  }
  
  public void push(Object elem) {
    Node v = new Node(elem, top);
    this.top = v;
    this.size++;
  }
  
  public Object top() throws EmptyStackException {
    if (isEmpty()) 
    		throw new EmptyStackException("empty stack.");
    return top.data;
  }
  
  public Object pop() throws EmptyStackException {
    if (isEmpty()) 
    	throw new EmptyStackException("empty stack.");
    
    Object temp = top.data;
    this.top = top.next;
    this.size--;
    return temp;
  }
  
  public static String infixToPostfix(String infixExpression) {
      LinkedStack stack = new LinkedStack();
      String postfixOutput = "";
      infixExpression.trim();
      

      for (int i = 0; i < infixExpression.length(); i++) {
         //current item
         char item = infixExpression.charAt(i);
         
         //if current item is an operand
         //Used isDigit of the Character class to avoid typing out all cases
         if (Character.isDigit(item)) {
            postfixOutput += item;
         }
         
         else if (item == '(') {
            stack.push(item);
         }
         
         else if (item == ')') {
            while(!(stack.top().equals('('))) {

               postfixOutput += stack.pop();
            }
            stack.pop();
         }
         

         else if (item == ' ') {}
         
         //Handle operators here
         else {
            while(stack.size() > 0 && (precedence((Character)stack.top())) >= precedence(item)) {
               System.out.println(postfixOutput);
               postfixOutput += stack.pop();
            }
            stack.push(item);
         }
      }
      
      //After running through the infix expression, append the remaining stack values to the postfix expression
      while (stack.isEmpty() == false) {
         postfixOutput += stack.pop();
      }
      return postfixOutput;
  }
  
  //Used to check precedence of an item on the stack
  public static int precedence(Character item) {
      if (item == '+' || item == '-') {
         return 2;
      }
      if (item == '%' || item == '/' || item == '*') {
         return 4;
      }
      if (item == '^') {
         return 5;
      }
      if (item == '(') {
         return 0;
      }
      return -1;
  }
  
  public static int postfixEvaluation(String postfixExpression) {
      LinkedStack stack = new LinkedStack();
      int result = -1;
      
      for (int i = 0; i < postfixExpression.length(); i++) {
         char item = postfixExpression.charAt(i);
         
         if (Character.isDigit(item)) {
            stack.push(item - '0');
         }
         else {
            //Operands
            int num1 = (int) stack.pop();
            int num2 = (int) stack.pop();
            

            switch (item) {
               case '+':
               stack.push(num2 + num1);
               break;
                
               case '-':
               stack.push(num2 - num1);
               break;
               
               case '/':
               stack.push(num2 / num1);
               break;
               
               case '*':
               stack.push(num2 * num1);
               break;
               
               case '^':
               stack.push((int) Math.pow(num2, num1));
               break;
            }
         }
      }
      if (stack.size() == 1) {
         result = (int) stack.pop();
      }
      else {
         throw new IllegalArgumentException("ERROR: Postfix Expression is invalid!");
      }
      
      return result;
  }

  public static void main(String args[]) {
   Scanner kb = new Scanner(System.in);
   
   System.out.print("Please enter the infix expression to process: ");
   String infixExpression = kb.nextLine();
   
   String postfixExpression = LinkedStack.infixToPostfix(infixExpression);
   System.out.println("The postfix expression for the input infix is: " + postfixExpression);
   
   int valueOfPostfix = LinkedStack.postfixEvaluation(postfixExpression);
   System.out.println("The final result after evaluating the postfix is: " + valueOfPostfix);
}
}