
//Author: Spencer Gilcrest
//Date: 12/8/25
//This is a AVL tree that you can insert into, search, delete from, and print
class AVL {

    private Node root;

    public AVL()
    {
         root = null;
    }

   
    //pre condition: root has been initialized;
    //post conidiotn: inserts into tree based on AVL rules
    void insert(int key){
        root = insertHelp(root, null, key);
    }

    private Node insertHelp(Node current, Node parent, int key) {
        if (current == null) {
            Node newNode = new Node(key);
            if (parent == null) root = newNode;
            return newNode;
        }

        if (key < current.key) {
            current.left = insertHelp(current.left, current, key);
        } else if (key > current.key) {
            current.right = insertHelp(current.right, current, key);
        } else {
            return current; 
        }

        
        return rebalance(current, parent);
    }


    //pre condition: root has been intialized
    //post condition: returns true if key is in tree and false if not
    Node search(int key){
        Node current = root;

        while (current != null){
            if (key == current.key){
                return current;
            }
            else if (key < current.key){
                current = current.left;
            }
            else{
                current = current.right;
            }
        }

        return null;
    }

    //pre condition: none
    //post condition: removes node from tree and rearranges the tree
    void remove(int key){
      root = removeHelp(root, null, key);
    }

    private Node removeHelp(Node n, Node parent, int key){
        if (n == null) return null;

        if (key < n.key){
            n.left = removeHelp(n.left, n, key);
        }
        else if (key > n.key){
            n.right = removeHelp(n.right, n, key);
        }
        else {
            //leaf
            if (n.left == null && n.right == null){
                return null;
            }
            
            //1 child
            if (n.left == null) return n.right;
            if (n.right ==null) return n.left;

            //2 children
            Node next = n.right;
            while (next.left != null){
                next = next.left;
            }

            n.key = next.key;

            n.right = removeHelp(n.right, n, next.key);
        }

        
        return rebalance(n, parent);
    }

    //precondition: n and parent initialized
    //postcondition: balances the tree such that the balance factor of node is never too large in either direction
    Node rebalance(Node n, Node parent){
        int balanceValue = balance(n);

        
        if (balanceValue < -1){
            if (balance(n.left) > 0){
                rotateLeft(n.left, n); //LR
            }
            n = rotateRight(n, parent); //LL
        }

        
        else if (balanceValue > 1){
            if (balance(n.right) < 0){
                rotateRight(n.right, n); //RL
            }
            n = rotateLeft(n, parent); //RR
        }

        return n;
    }

    
    //precondition: n intialized
    //post condition returns int form of longest path (# of edges) to leaf from that node
    private int height(Node n){
        if (n == null) return -1;
        return 1 + Math.max(height(n.left), height(n.right));
    }

    //precondition: n has been initialized
    //post condition: returns balance of node calculated by subtracting height of right child from height of left
    private int balance(Node n){
        return height(n.right) - height(n.left);
    }

    //precondition: subroot and prev initialized
    //postcondition: makes a rightward rotation around node subroot
    private Node rotateRight(Node subRoot, Node prev){
        Node newRoot = subRoot.left;
        Node temp = newRoot.right;

        newRoot.right = subRoot;
        subRoot.left = temp;

        if (prev == null){
            root = newRoot;
        }
        else if (prev.left == subRoot){
            prev.left = newRoot;
        }
        else{
            prev.right = newRoot;
        }

        return newRoot;
    }


    //precondition: subroot and prev initialized
    //postcondition: makes a leftward rotation around Node subroot 
    private Node rotateLeft(Node subRoot, Node prev){
        Node newRoot = subRoot.right;
        Node temp = newRoot.left;

        newRoot.left = subRoot;
        
        subRoot.right = temp;

        if (prev == null){
            root = newRoot;
        }
        else if(prev.left == subRoot){
            prev.left = newRoot;
        }
        else{
            prev.right = newRoot;
        }

        return newRoot;

    }

    //pre condition: none
    //post condition: prints out tree in string format with levels
    public String toString(){
        String result = "";
        int h  = height(root);

        for (int level = 1; level <= h; level++){
            result += levelString(root, level) + "\n";
        }

        return result;
    }

    //prints individual level of tree
    String levelString(Node n, int level){
        if (n == null) return "";
        if (level == 1){
            return n.key + " ";
        }
        String leftSide = levelString(n.left, level - 1);
        String rightSide = levelString(n.right, level - 1);

        return leftSide + rightSide;
    }


    public boolean isBSTOrNot() {
        return isBSTOrNot(this.root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    private boolean isBSTOrNot(Node root, int minValue, int maxValue) {
        // check for root is not null or not
        if (root == null) {
            return true;
        }
        // check for current node value with left node value and right node value and recursively check for left sub tree and right sub tree
        if(root.key >= minValue && root.key <= maxValue && isBSTOrNot(root.left, minValue, root.key) && isBSTOrNot(root.right, root.key, maxValue)){
            return true;
        }
        return false;
    }

 
   // please use the following pieces of code to display your tree in a more easy to follow style (Note* you'll need to place the Trunk class in it's own file)
    public static void showTrunks(Trunk p)
    {
        if (p == null) {
            return;
        }
 
        showTrunks(p.prev);
        System.out.print(p.str);
    }
 
    public void printTree(){
        printTree(root, null, false);
    }

    private void printTree(Node root, Trunk prev, boolean isLeft)
    {
        if (root == null) {
            return;
        }
 
        String prev_str = "    ";
        Trunk trunk = new Trunk(prev, prev_str);
 
        printTree(root.right, trunk, true);
 
        if (prev == null) {
            trunk.str = "———";
        }
        else if (isLeft) {
            trunk.str = ".———";
            prev_str = "   |";
        }
        else {
            trunk.str = "`———";
            prev.str = prev_str;
        }
 
        showTrunks(trunk);
        System.out.println(" " + root.key);
 
        if (prev != null) {
            prev.str = prev_str;
        }
        trunk.str = "   |";
 
        printTree(root.left, trunk, false);
    }


//tests
    public static void main(String[] args) {
      
        
        // LL rotation
        AVL avl = new AVL();
        avl.insert(30);
        avl.insert(20);
        avl.insert(10);
        avl.printTree();

        // RR rotation
        AVL avl2 = new AVL();
        avl2.insert(10);
        avl2.insert(20);
        avl2.insert(30);
        avl2.printTree();

        // LR rotation
        AVL avl3 = new AVL();
        avl3.insert(30);
        avl3.insert(10);
        avl3.insert(20);
        avl3.printTree();

        // RL rotation
        AVL avl4 = new AVL();
        avl4.insert(10);
        avl4.insert(30);
        avl4.insert(20);
        avl4.printTree();

        System.out.println("Deletions");
        AVL avl5 = new AVL();
        avl5.insert(50);
        avl5.insert(30);
        avl5.insert(70);
        avl5.insert(20);
        avl5.insert(40);
        avl5.insert(60);
        avl5.insert(80);
        System.out.println("Initial tree:");
        avl5.printTree();

     
        avl5.remove(20);
        System.out.println("After removing leaf 20:");
        avl5.printTree();

     
        avl5.remove(30);
        System.out.println("After removing node 30 (one child):");
        avl5.printTree();

        avl5.remove(70);
        System.out.println("After removing node 70 (two children):");
        avl5.printTree();

        }
  
}


