import java.util.Scanner;
class Utility {
   public static void main(String args[]) {
      Scanner scnr = new Scanner(System.in);
      Expression expr;
      boolean eval = true;
      String testInput = "";
      //Grab eval flag
      if(scnr.hasNext()){
         try{
            eval = scnr.nextBoolean();
            scnr.nextLine();
         }
         catch(Exception ex){
            System.out.println("Improper input\n" + ex);
         }
         while(scnr.hasNext()){
            try{
               testInput = scnr.nextLine();
               expr = new Expression(testInput);
               print(expr);
               if(eval)
                  System.out.println("Evaluated: " + expr.evaluate() + "\n");
            }
            catch(Exception ex){
               System.out.println("Improper input\n" + ex);
            }
         }
      }
      else{
         expr = new Expression("1 + 2 - 3 / 4");
         print(expr);
         if(eval)
            System.out.println("Evaluated: " + expr.evaluate() + "\n");
      }
   }
   
   public static String getInput() {
      System.out.println("Enter an expression: ");
      Scanner s = new Scanner(System.in);
      String answer =  s.nextLine();
      s.close();
      return answer;
   }

   public static void print(ExpressionTree y) {
      System.out.println("Prefix: " + y.prefix());
      System.out.println("Postfix: " + y.postfix());
      System.out.println("Fully parenthesized: " + y.fullyParenthesized());
      System.out.println("-----------------");
   }   
}