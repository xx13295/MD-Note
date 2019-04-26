package plus.ojbk.test;

public class WordsTree {
	private static Node head;

	public WordsTree() {
		head = new Node(' ');// 头结点
		insert("\u00AD");
		insert("\u00A0");
		insert("\u3000");
		insert("\u200C");
		insert("\u200D");
		insert("\uFEFF");
		insert("\u200B");
		insert("\u2800");
		insert("\u2029");
		insert("\u2028");
		//insert("\\u000D");
		insert("\u000C");
		insert("\u000B");
		//insert("\\u000A");
		insert("\u0009");
		insert("\u0008");
	}

	public void insert(String word) {
		if (search(word).equals(word)) {
			return;// 检测树中是否存在此词
		}
		Node node = head;
		for (int i = 0; i < word.length(); i++) {
			Node child = node.subNode(word.charAt(i));
			if (child != null) {
				node = child;
			} else {
				node.addChild(new Node(word.charAt(i)));
				node = node.subNode(word.charAt(i));
			}
		}
		node.setLeaf(true);
	}

	public static String search(String word) {
		Node node = head;
		String str = "";
		for (int i = 0; i < word.length(); i++) {
			if (node.isLeaf()) {
				return str;
			}
			// 在这添加防止内容中的敏感词汇被空格（可以加其他字符）隔开，无法识别
			if (word.charAt(i) == ' ') {
				str += word.charAt(i);
				continue;
			}
			if (node.subNode(word.charAt(i)) == null) {
				return "";
			}
			node = node.subNode(word.charAt(i));
			str += word.charAt(i);
		}
		if (node.isLeaf() == true) {
			return str;
		} else {
			return "";
		}
	}
	
	/**
	 * 过滤内容中的相应字符为空字符串
	 * @param content
	 * @return
	 */
	public static String filterContent(String content) {
		return filterContent(content, "");
	}
	
	/**
	 * 过滤内容中的相应字符为预设好的值 
	 * @param content 带过滤内容
	 * @param value 预设值
	 * @return
	 */
	public static String filterContent(String content, String value) {
		String str = "";
		String newContent = content;
		for (int i = 0; i < content.length(); i++) {
			str = search(content.substring(i));
			if (!str.equals("")) {
				newContent = newContent.replaceAll(str, value); //替换为预设值
			}
			i += str.length();
		}

		return newContent;
	}
}
