import java.util.ArrayList;
abstract class ExpressionTree extends BinaryTree<String> {
   public ExpressionTree() {
      super();
   }
   public abstract String fullyParenthesized();
   
   public final String postfix() {
      String ans = "";
      ArrayList<Node<String>> l = postOrder(); 
      for (Node<String> b:l) ans += b.getData() + " ";
      return ans;
   }

   public final String prefix() {
      String ans = "";
      ArrayList<Node<String>> l = preOrder(); 
      for (Node<String> b:l) ans += b.getData() + " ";
      return ans;
   }
   
   public abstract double evaluate();

}