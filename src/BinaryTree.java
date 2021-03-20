import java.util.ArrayList;
class BinaryTree<T> extends Tree<T> {
   public BinaryTree() {
      super();
   }

   public void addRoot(T x) throws Exception {
      if (root != null) throw new Exception("The tree is not empty");
      root = new BNode<T>(x, null, null, null);
      size++;
   }

   public void addLeft(BNode<T> n, T x) throws Exception {
      if (n.getLeft() != null) throw new Exception("Already used");
      n.setLeft(new BNode<T>(x, n, null, null));
      size++;
   }

   public void addRight(BNode<T> n, T x) throws Exception {
      if (n.getRight() != null) throw new Exception("Already used");
      n.setRight(new BNode<T>(x, n, null, null));
      size++;
   }

   // returns the parent of the removed node
   public BNode<T> removeNode(BNode<T> n) {
      if (isLeaf(n)) {  // base case
         BNode<T> p = (BNode<T>) parent(n);
         if (p == null) root = null;
         else p.removeChild(n);
         size--;
         return p;
      }
      if (n.getLeft() != null) {
         BNode<T> m = rightmostLeftDescendant(n);
         n.setData(m.getData());
         return removeNode(m);   // recursively remove rightmost left descendant
      }
      // otherwise n does have a right child
      BNode<T> m = leftmostRightDescendant(n);
      n.setData(m.getData());
      return removeNode(m);
   }
   
   public BNode<T> leftmostRightDescendant(BNode<T> n) {
      BNode<T> m = n.getRight();
      while (m.getLeft() != null) m = m.getLeft();
      return m;
   }

   public BNode<T> rightmostLeftDescendant(BNode<T> n) {
      BNode<T> m = n.getLeft();
      while (m.getRight() != null) m = m.getRight();
      return m;
   }

   public ArrayList<BNode<T>> inOrder() {
      ArrayList<BNode<T>> answer = new ArrayList<BNode<T>>();
      inOrder((BNode<T>) root(), answer);
      return answer;
   }

   public void inOrder(BNode<T> n, ArrayList<BNode<T>> v) {
      if (n == null) return;
      inOrder(n.getLeft(), v);
      v.add(n);
      inOrder(n.getRight(), v);
   }
   
   public ArrayList<BNode<T>> flatOrder() {
      return inOrder();
   }
}