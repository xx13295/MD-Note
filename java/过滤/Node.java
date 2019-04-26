package plus.ojbk.test;

import java.util.ArrayList;
import java.util.List;

public class Node {
	private char root;
	private List<Node> childList;
	private boolean isLeaf;

	public Node(char root) {
		this.root = root;
		childList = new ArrayList<Node>();
		isLeaf = false;
	}

	public Node subNode(char c) {
		if (childList != null) {
			for (Node eachChild : childList) {
				if (eachChild.root == c) {
					return eachChild;
				}
			}
		}
		return null;
	}

	public boolean isLeaf() {
		return isLeaf;
	}

	public void setLeaf(boolean leaf) {
		isLeaf = leaf;
	}

	public void addChild(Node node) {
		this.childList.add(node);
	}
}
