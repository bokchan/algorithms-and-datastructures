package experiments;

import edu.princeton.cs.stdlib.StdRandom;

public class BinaryTreeTest {

	  public static void main(String[] args) {
	    new BinaryTreeTest().run();
	  }

	  static class Node {
	    Node left;

	    Node right;

	    int value;
	    public Node(int value) {
	      this.value = value;
	    }
	  }

	  public void run() {
	    // build the simple tree from chapter 11.
		  int size = 1000;
		  int[] a =new int[size];
		  for (int i = 0; i < size; i++) {
			   a[i] = StdRandom.uniform(size);
		  }  
		StdRandom.shuffle(a);
		  
	    Node root = new Node(a[0]);
	    System.out.println("Binary Tree Example");
	    System.out.println("Building tree with root value " + root.value);
	    for (int i = 1; i < size; i++) {
	    	insert(root, a[i]);
	    }
	    System.out.println("Traversing tree in order");
	    printInOrder(root);
	    System.out.println("Traversing tree front-to-back from location " + size );
	    printFrontToBack(root, size);
	  }

	  public void insert(Node node, int value) {
	    if (value < node.value) {
	      if (node.left != null) {
	        insert(node.left, value);
	      } else {
	        System.out.println("  Inserted " + value + " to left of "
	            + node.value);
	        node.left = new Node(value);
	      }
	    } else if (value > node.value) {
	      if (node.right != null) {
	        insert(node.right, value);
	      } else {
	        System.out.println("  Inserted " + value + " to right of "
	            + node.value);
	        node.right = new Node(value);
	      }
	    }
	  }

	  public void printInOrder(Node node) {
	    if (node != null) {
	      printInOrder(node.left);
	      //System.out.println("  Traversed " + node.value);
	      printInOrder(node.right);
	    }
	  }

	  /**
	   * uses in-order traversal when the origin is less than the node's value
	   * 
	   * uses reverse-order traversal when the origin is greater than the node's
	   * order
	   */
	  public void printFrontToBack(Node node, int camera) {
	    if (node == null)
	      return;
	    if (node.value > camera) {
	      // print in order
	      printFrontToBack(node.left, camera);
	      System.out.println("  Traversed " + node.value);
	      printFrontToBack(node.right, camera);
	    } else if (node.value < camera) {
	      // print reverse order
	      printFrontToBack(node.right, camera);
	      System.out.println("  Traversed " + node.value);
	      printFrontToBack(node.left, camera);
	    } else {
	      // order doesn't matter
	      printFrontToBack(node.left, camera);
	      printFrontToBack(node.right, camera);
	    }
	  }

	}