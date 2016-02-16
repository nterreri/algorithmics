package uk.ac.ucl.myuclaccountname.BinaryTreeLUT;

class KeyNotFoundInTableException extends Exception {
}

/**
 * Implementation of LUT using a binary tree
 */
public class BinaryTreeLUT {

	/**
	 * The member class Key is used for the indexing keys of the LUT. It
	 * is a String with basic comparative methods added.
	 */
	protected class Key {

		public Key(String s) {
			kString = s;
		}

		public boolean equals(Key k) {
			return kString.equals(k.toString());
		}

		public boolean lessThan(Key k) {
			return kString.compareTo(k.toString()) < 0;
		}

		public boolean greaterThan(Key k) {
			return kString.compareTo(k.toString()) > 0;
		}

		public String toString() {
			return kString;
		}

		private String kString;
	}

	/**
	 * Implementation of an entry.
	 *
	 * The member class Entry encapsulates an entry of the LUT and
	 * contains a {key, value} pair.
	 */
	protected class Entry {

		public Entry(Key k, Object v) {
			key = k;
			value = v;
		}

		protected Key key;
		protected Object value;
	}

	/**
	 * Implementation of a node in a tree that represents a subtree.
	 *
	 * The member class BSTreeNode encapsulates node of the binary
	 * search tree, which contains a LUT entry and links to left and
	 * right subtrees.
	 */
	protected class BSTreeNode {

		public BSTreeNode(Entry e) {
			kvPair = e;
			left = null;
			right = null;
		}

		public BSTreeNode(Entry e, BSTreeNode l, BSTreeNode r) {
			kvPair = e;
			left = l;
			right = r;
		}

		protected Entry kvPair;
		protected BSTreeNode left;
		protected BSTreeNode right;
	}

	// Single protected data member - the LUT is stored in a sequence.
	protected BSTreeNode root;

	/**
	 * Default constructor - no need to specify capacity of LUT.
	 */
	public BinaryTreeLUT() {
		root = null;
	}


	/**
	 * Inserts a new key-value pair into the look-up table.
	 */
	public void insert(String key, Object value) {
		BSTreeNode newNode =
				new BSTreeNode(new Entry(new Key(key), value));
		addToTree(newNode, root);
	}

	/**
	 * Removes the key-value pair with the specified key from the
	 * look-up table.
	 */
	public void remove(String key)
			throws KeyNotFoundInTableException {

		Key searchKey = new Key(key);
		removeFromTree(searchKey, root);
	}

	/**
	 * Retrieves the key-value pair with the specified key from the
	 * look-up table.
	 */
	public Object retrieve(String key)
			throws KeyNotFoundInTableException {
		Key searchKey = new Key(key);
		BSTreeNode treeNode = getFromTree(searchKey, root);
		return treeNode.kvPair.value;
	}

	/**
	 * Updates the key-value pair with the specified key with the new
	 * specified value.
	 */
	public void update(String key, Object value)
			throws KeyNotFoundInTableException {
		Key searchKey = new Key(key);
		BSTreeNode treeNode = getFromTree(searchKey, root);
		treeNode.kvPair.value = value;
	}

	/**
	 * Returns a string listing all the key-entry pairs in the LUT
	 */
	public String toString() {
		return treeString(root);
	}

	// Protected methods implementing recursive operations on the tree.

	/**
	 * Adds newNode to the tree rooted at curNode recursively.
	 */
	protected void addToTree(BSTreeNode newNode,
			BSTreeNode curNode) {
		if (curNode == null) {
			// Special case for empty tree.
			root = newNode;
		} else if (curNode.kvPair.key
				.lessThan(newNode.kvPair.key)) {
			// General case: recurse left or right depending on comparison.
			if (curNode.left == null) {
				curNode.left = newNode;
			} else {
				addToTree(newNode, curNode.left);
			}
		} else {
			if (curNode.right == null) {
				curNode.right = newNode;
			} else {
				addToTree(newNode, curNode.right);
			}
		}
	}

	/**
	 * Returns the node containing k from the tree rooted at node.
	 */
	protected BSTreeNode getFromTree(Key k, BSTreeNode node)
			throws KeyNotFoundInTableException {
		if (node == null) {
			throw new KeyNotFoundInTableException();
		} else if (node.kvPair.key.equals(k)) {
			return node;
		} else if (node.kvPair.key.lessThan(k)) {
			return getFromTree(k, node.left);
		} else {
			return getFromTree(k, node.right);
		}
	}

	protected void removeFromTree(Key k, BSTreeNode node)
			throws KeyNotFoundInTableException {
		if (node == null) {
			// Special case for empty tree.
			throw new KeyNotFoundInTableException();
		} else if (root.kvPair.key.equals(k)) {
			// Special case when deleting the root node.
			root = lrMerge(root);
		} else if (node.kvPair.key.lessThan(k)) {
			// If the key at the current node is less than the search key,
			// go to the left subtree.

			if (node.left == null) {
				// If the left subtree is empty, the tree cannot contain the
				// search key.
				throw new KeyNotFoundInTableException();
			}

			if (node.left.kvPair.key.equals(k)) {
				// If this is the parent of the node to be removed, do the
				// removal.
				node.left = lrMerge(node.left);
			} else {
				// Otherwise, recurse down another level.
				removeFromTree(k, node.left);
			}
		} else {
			// Otherwise go to the right subtree.
			if (node.right == null) {
				// If the right subtree is empty, the tree cannot contain the
				// search key.
				throw new KeyNotFoundInTableException();
			}

			if (node.right.kvPair.key.equals(k)) {
				// If this is the parent of the node to be removed, do the
				// removal.
				node.right = lrMerge(node.right);
			} else {
				// Otherwise, recurse down another level.
				removeFromTree(k, node.right);
			}
		}
	}

	/**
	 * Merges the two subtrees of node prior to removal of the node from
	 * the tree.
	 * @throws KeyNotFoundInTableException 
	 */
	protected BSTreeNode lrMerge(BSTreeNode target) throws KeyNotFoundInTableException {
		BSTreeNode mergedTrees = null;
		// First two cases occur when one or both subtrees of the node to
		// be deleted are empty.
		if (target.left == null) {
			mergedTrees = target.right;
		} else if (target.right == null) {
			mergedTrees = target.left;
		} else {
			//Otherwise, find the rightmost node of the left subtree of the 
			//target node, then swap the key/value pair of this rightmost
			//subnode for the ones in the target node, then delete the 
			//rightmost subnode (since it is the rightmost, it has only one
			//left child node/tree)


			BSTreeNode rightmost = findRightmost(target.left);
			Entry temp = rightmost.kvPair;
			removeFromTree(rightmost.kvPair.key, target); 		
			//propagate exception from here, but should not occur: rightmost.kvPair.key
			//is guaranteed to be a valid subnode of the target node to delete
			//this should only really occur from concurrency issues
			target.kvPair = temp; //could have also used update()
			mergedTrees = target;

			//      // Otherwise, merge the left and right subtrees and link the
			//      // merged structure to the current node.
			//      addToTree(node.right, node.left);
			//      mergedTrees = node.left;
		}

		return mergedTrees;
	}

	/**
	 * Uses in order tree traversal to construct a string containing all
	 * the key value pairs in the binary search tree.
	 */
	protected String treeString(BSTreeNode node) {
		if (node == null) {
			return "";
		}
		Entry lutEntry = node.kvPair;
		String thisNode = "";
		thisNode = lutEntry.key.toString() + ":"
				+ lutEntry.value;
		return "(" + treeString(node.left)
		+ thisNode
		+ treeString(node.right) + ")";
	}

	
	/*Returns the rightmost subnode of the parameter node*/
	protected BSTreeNode findRightmost(BSTreeNode subnode) throws NullPointerException {
		if(subnode == null) 
			throw new NullPointerException();

		if(subnode.right == null) 
			return subnode;
		else
			return findRightmost(subnode.right);
	}

}