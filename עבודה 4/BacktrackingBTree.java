import java.util.ArrayList;
import java.util.Deque;
import java.util.List;

public class BacktrackingBTree<T extends Comparable<T>> extends BTree<T> {

    /**
     * merge a node with his father and brother.
     *
     * @param valueNode - the node that the function merge with.
     * @param withLeft - true if we need to merge with left brother, false otherwise
     * @return the node after merging
     */
    private Node<T> JoinNodes(Node<T> valueNode, boolean withLeft){
        Node<T> parentNode = valueNode.parent;
        T parentValue;
        Node<T> leftBro;
        Node<T> rightBro;
        int childIdx = parentNode.indexOf(valueNode); //mutual parent
        if(withLeft){ //merge with left brother
            leftBro = parentNode.getChild(childIdx-1);
            rightBro = valueNode;
            parentValue = parentNode.getKey(childIdx-1);
        }
        else{ //merge with right brother
            leftBro = valueNode;
            rightBro = parentNode.getChild(childIdx+1);
            parentValue = parentNode.getKey(childIdx);
        }
        //adding all keys to the left node, than adding all children by order, to construct the node before the split
        leftBro.addKey(parentValue);
        for(int i = 0; i < rightBro.numOfKeys; i++)
            leftBro.addKey(rightBro.getKey(i));
        for(int i = 0; i < rightBro.numOfChildren; i++)
            leftBro.addChild(rightBro.getChild(i));
        if(parentNode.getNumberOfKeys() == 1){
            //the splitted value is the only one in his node, so his node's parent is now the original's parent
            leftBro.parent = parentNode.parent;
            if (leftBro.parent != null) {
                int parentLocation = parentNode.parent.indexOf(parentNode);
                parentNode.parent.children[parentLocation] = leftBro;
            }
        }
        else{ //the splitted value wasn't the only one in his node, so his node is the original node's parent
            parentNode.removeKey(parentValue);
            parentNode.removeChild(rightBro);
        }
        return leftBro;
    }

    /**
     * make a backtrack action on the b-tree, backtrack insert action.
     */
    //You are to implement the function Backtrack.
    public void Backtrack() {
        if(!myDeque.isEmpty()) {
            size--;
            T value = myDeque.removeLast(); //value last inserted
            Node<T> valueNode = this.getNode(value);
            valueNode.removeKey(value);
            Node<T> fatherNode = valueNode.parent;
            int childIdx;
            T leftParent;
            T rightParent;
            while (fatherNode != null) { //moving up the tree until the root
                rightParent = myDeque.removeLast();
                leftParent = myDeque.removeLast();
                childIdx = fatherNode.indexOf(valueNode); //location of kid in node
                if(!(compareTo(rightParent,ourGetKey(fatherNode,childIdx))==0 & compareTo(leftParent,ourGetKey(fatherNode,childIdx-1))==0)){
                    //1 of the parents is wrong, therefore there was a split and we need to check if the merged value is now right/left parent
                    if(compareTo(rightParent,fatherNode.getKey(childIdx))==0){//we need to join the node with parent and left brother
                        valueNode = JoinNodes(valueNode, true);
                    }
                    else{//we need to join the node with parent and right brother
                        valueNode = JoinNodes(valueNode, false);
                    }
                }
                if(rightParent == null & leftParent == null) //there was a split in root
                    fatherNode=null;
                if(fatherNode!=null) {  //moving up
                    valueNode = fatherNode;
                    fatherNode = fatherNode.parent;
                }
            }
            root = valueNode;
            if(myDeque.isEmpty()) //last backtrack, tree is empty
                root=null;
        }
    }

    /**
     * Get the key of a value in a node by index but returns null if the index exceeds the limits instead of an exception
     *
     * @param fatherNode - the node to check in.
     * @param index - the index we want the key of.
     * @return the index of the node or null if the index out of bound
     */
    T ourGetKey(Node<T>fatherNode,int index){
        if(index==-1 | index==fatherNode.getNumberOfChildren())//if index out of bound return null and not exception.
            return null;
        return fatherNode.getKey(index);//return the node getKey methode.
    }

    /**
     * compareTo for 2 T values
     * if value of an element is null it's lower than the other value
     *
     * @param value1 - the inserted value
     * @param value2 - the inserted value
     * @return the T compareTo value, or the larger value if one of them is null
     */
    private int compareTo(T value1,T value2){
        if(value1==null & value2==null)//the "override", if both elements are null, they equals.
            return 0;
        else if(value2==null)//if element is null is lower then a not-Null element.
            return 1;
        else if(value1==null)
            return -1;
        return value1.compareTo(value2);//return the T compareTo methode.
    }

    //Change the list returned to a list of integers answering the requirements
    public static List<Integer> BTreeBacktrackingCounterExample(){
        ArrayList<Integer> myList = new ArrayList<>();
        myList.add(20);
        myList.add(30);
        myList.add(40);
        myList.add(50);
        return myList;
    }
}
