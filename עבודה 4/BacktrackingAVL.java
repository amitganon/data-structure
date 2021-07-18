import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BacktrackingAVL extends AVLTree {
    //You are to implement the function Backtrack.
    /**
     * make a backtrack action on the b-tree, backtrack insert action.
     * 
     * To backtrack we took our the last arr inserted and check for arr[1](that holds the type of rotation)
     * To change back we did the opposite rotation on the nodes' parents, and after we erased the value(arr[0])
     * For example, [Node(x), 12, Node(y), Node(z)], will be a left rotation of Node(z)'s parent and a
     * right rotation on Node(y)'s parent, then erasing Node(x)
     * After that we checked for height updates for every ancestor of Node(x)
     * 
     */
    public void Backtrack() {
        if(!myDeque.isEmpty()) {
            Object[] arr= myDeque.remove();
            if(arr[1]!=null) {
                int lastDigit = (Integer) arr[1] % 10;
                Node parent =((Node) arr[3]).parent.parent;
                if (lastDigit == 1) {
                    if(parent==null) //the (1st) rotation happened on the root
                        root=rightRotate(((Node) arr[3]).parent);
                    else {
                        if(parent.left.value==((Node) arr[3]).parent.value) //checking to which son to assign the coming Node
                            parent.left = rightRotate(((Node) arr[3]).parent);
                        else
                            parent.right = rightRotate(((Node) arr[3]).parent);
                    }
                    if (arr[2] != null) { //if a second rotation committed
                        parent=((Node) arr[2]).parent.parent;
                        if(parent.left!=null && parent.left.value==((Node) arr[2]).parent.value) //checking to which son to assign the coming Node
                            parent.left = leftRotate(((Node) arr[2]).parent);
                        else
                            parent.right = leftRotate(((Node) arr[2]).parent);
                    }
                }
                else {
                    if(parent==null) //the (1st) rotation happened on the root
                        root=leftRotate(((Node) arr[3]).parent);
                    else {
                        parent=((Node) arr[3]).parent.parent;
                        if(parent.left.value==((Node) arr[3]).parent.value) //checking to which son to assign the coming Node
                            parent.left = leftRotate(((Node) arr[3]).parent);
                        else
                            parent.right = leftRotate(((Node) arr[3]).parent);
                    }
                    if (arr[2] != null) { //if a second rotation committed
                        parent=((Node) arr[2]).parent.parent;
                        if(parent.left!=null && parent.left.value==((Node) arr[2]).parent.value) //checking to which son to assign the coming Node
                            parent.left = rightRotate(((Node) arr[2]).parent);
                        else
                            parent.right = rightRotate(((Node) arr[2]).parent);
                    }
                }
            }
            Remove((Node)arr[0]);
        }
    }
    /**
     * Removes The node from his parent and updates all parents heights.
     *
     * @param node - the node we want to remove
     */
    private void Remove(Node node){
        //Erase the node inserted, then update all ancestors' heights.
            if(node.parent==null) { //if node deleted is in the root
                root = null;
            }
            else if (node.parent.left!=null && node.parent.left.value == node.value) { //if node is the left son
                node.parent.left = null;
                if(node.parent.right==null){
                    node.parent.height=node.parent.height -1;
                    UpdateHeight(node.parent);
                }
            }
            else { //if node is the right son
                node.parent.right = null;
                if(node.parent.left==null){
                    node.parent.height=node.parent.height -1;
                    UpdateHeight(node.parent);
                }
            }
        }

    /**
     * Updates height for a node
     *
     * @param node - the node we want to check his current height
     */
    private void UpdateHeight(Node node) {
        Node parent=node.parent;
        if(parent!=null){
            parent.height=Math.max(getNodeHeight(parent.left), getNodeHeight(parent.right)) + 1;
            UpdateHeight(parent);
        }
    }

    //Change the list returned to a list of integers answering the requirements
    public static List<Integer> AVLTreeBacktrackingCounterExample(){
        ArrayList<Integer> myList = new ArrayList<>();
        myList.add(20);
        myList.add(30);
        myList.add(40);
        return myList;
    }
}
