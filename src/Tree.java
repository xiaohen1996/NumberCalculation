import java.util.ArrayList;
import java.util.Iterator;

class Tree<T> {
   protected Node<T> root;
   public int size;

   public Tree() {
      root = null;
      size = 0;
   }

   public Node<T> root() {
      return root;
   }

   public Node<T> parent(Node<T> v) {
      return v.getParent();
   }

   public Iterator<? extends Node<T>> children(Node<T> v) {
      return v.children();
   }

   public boolean isRoot(Node<T> v) {
      return v == root;
   }

   public boolean isInternal(Node<T> v) {
      return children(v).hasNext();
   }

   public boolean isLeaf(Node<T> v) {
      return !isInternal(v);
   }

   public int size() {
      return size;
   }

   public boolean empty() {
      return size == 0;
   }

   public void replace(Node<T> v, T t) {
      v.setData(t);
   }

   public int depth(Node<T> v) {
      if (isRoot(v))
         return 0;
      return 1 + depth(parent(v));
   }

   public int height(Node<T> v) {
      if (isLeaf(v))
         return 0;
      int maxChild = 0;
      Iterator<? extends Node<T>> c = children(v);
      while (c.hasNext()) {
         int hc = height((Node<T>) c.next());
         if (hc > maxChild)
            maxChild = hc;
      }
      return maxChild + 1;
   }

   public int height() {
      if (root == null)
         return -1;
      return height(root);
   }

   public ArrayList<Node<T>> preOrder() {
      ArrayList<Node<T>> answer = new ArrayList<>();
      preOrder(root(), answer);
      return answer;
   }

   public void preOrder(Node<T> n, ArrayList<Node<T>> v) {
      if (n == null)
         return;
      v.add(n);
      Iterator<? extends Node<T>> x = children(n);
      while (x.hasNext()) {
         Node<T> m = x.next();
         preOrder(m, v);
      }
   }

   public ArrayList<Node<T>> postOrder() {
      ArrayList<Node<T>> answer = new ArrayList<Node<T>>();
      postOrder(root(), answer);
      return answer;
   }

   public void postOrder(Node<T> n, ArrayList<Node<T>> v) {
      if (n == null)
         return;
      Iterator<? extends Node<T>> x = children(n);
      while (x.hasNext()) {
         Node<T> m = x.next();
         postOrder(m, v);
      }
      v.add(n);
   }

   public ArrayList<Node<T>> levelOrder() {
      ArrayList<Node<T>> waiting = new ArrayList<>();
      waiting.add(null);
      if (root() == null)
         return waiting;
      waiting.add(root());
      int done = 0;
      while (done < waiting.size()) {
         Node<T> oldNode = waiting.get(done++);
         if (oldNode == null) {
            if (done < waiting.size())
               waiting.add(null);
            continue;
         }
         Iterator<? extends Node<T>> c = children(oldNode);
         while (c.hasNext())
            waiting.add(c.next());
      }
      return waiting;
   }

   public ArrayList<? extends Node<T>> flatOrder() {
      return preOrder();
   }

   public String toString() {
      return treePrint(null);
   }

   public String treePrint(Node<T> cursor) {
      String answer = "  ";
      Iterator<Node<T>> lev = levelOrder().iterator();
      Iterator<? extends Node<T>> flat = flatOrder().iterator();
      lev.next(); // skip first new line
      while (lev.hasNext()) {
         Node<T> o = lev.next();
         if (o == null) {
            answer += "\n  ";
            flat = flatOrder().iterator();
         } else
            while (true) {
               boolean atCursor = false;
               Node<T> n = flat.next();
               if (n == cursor)
                  atCursor = true;
               String s = n.getData().toString();
               if (atCursor)
                  s = "* " + s + " *";
               if (n == o) {
                  answer += s + " ";
                  break;
               } else {
                  for (int i = 0; i < s.length(); i++)
                     answer += ' ';
                  answer += ' ';
               }
            }
      }
      return answer;
   }
}