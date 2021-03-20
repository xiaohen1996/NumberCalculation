import java.util.Iterator;

abstract class Node<T> {
   protected Node<T> parent;
   protected T data;

   public abstract Iterator<? extends Node<T>> children();

   public void setParent(Node<T> n) {
      parent = n;
   }

   public void setData(T t) {
      data = t;
   }

   public Node<T> getParent() {
      return parent;
   }

   public T getData() {
      return data;
   }
}