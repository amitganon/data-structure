import static java.lang.Integer.parseInt;

public class BacktrackingArray implements Array<Integer>, Backtrack {
    private Stack stack;
    private int[] arr;
    private int arrayTopIndex; //Added to keep track on last index of the actual(non-trash) array

    // Do not change the constructor's signature
    public BacktrackingArray(Stack stack, int size) {
        this.stack = stack;
        arr = new int[size];
        arrayTopIndex = 0;
    }

    @Override
    public Integer get(int index) { //returns the value in the index location
        if (index >= arrayTopIndex | index < 0)
            throw new IllegalArgumentException("index is out of array size's bounds");
        return arr[index];
    }

    @Override
    public Integer search(int k) { //returns the index of the value
        for (int i = 0; i < arrayTopIndex; i++)
            if (arr[i] == k)
                return i;
        return -1;
    }

    @Override
    public void insert(Integer x) { //Inserts the value to the next free index
        if (arrayTopIndex < arr.length) { //if there is space left
            stack.push("1-" + arr[arrayTopIndex]); //pushing to the stack for backtracking, 1=insert
            arr[arrayTopIndex] = x;
            arrayTopIndex++;
        } else
            throw new RuntimeException("data structure overflow");
    }

    @Override
    public void delete(Integer index) { //Deletes the value in the index
        if (index >= arr.length | index < 0)
            throw new IllegalArgumentException("index is out of array size's bounds");
        if (index >= arrayTopIndex)
            throw new IllegalArgumentException("A value hasn't been inserted to this index");
        stack.push("0-" + index + "-" + arr[index]); //pushing to the stack for backtracking, 0=delete
        for (int i = index; i < arrayTopIndex - 1; i++)
            arr[i] = arr[i + 1];
        arrayTopIndex--; //making the last index's set value irrelvant
    }

    @Override
    public Integer minimum() { //Gets the minimum value
        if (arrayTopIndex == 0)
            throw new IllegalArgumentException("Array is empty");
        int min = 0;
        for (int i = 1; i < arrayTopIndex; i++)
            if (arr[i] < arr[min])
                min = i;
        return min;
    }

    @Override
    public Integer maximum() { //Gets the maximum value
        if (arrayTopIndex == 0)
            throw new IllegalArgumentException("Array is empty");
        int max = 0;
        for (int i = 1; i < arrayTopIndex; i++)
            if (arr[i] > arr[max])
                max = i;
        return max;
    }

    @Override
    public Integer successor(Integer index) { //Gets the successor of the given value
        if (index >= arrayTopIndex | index < 0)
            throw new IllegalArgumentException("index is out of array size's bounds");
        int i = 0;
        while (i < arrayTopIndex && arr[i] <= arr[index]) //finding the first bigger value
            i++;
        int currMin = i;
        for (int j = i; j < arrayTopIndex; j++) //finding the smallest successor
            if (arr[j] < arr[currMin] & arr[index] < arr[j])
                currMin = j;
        if (currMin == index | currMin >= arrayTopIndex)
            throw new IllegalArgumentException("There isn't a successor to this index");
        return currMin;
    }

    @Override
    public Integer predecessor(Integer index) {//Gets the predecessor of the given value
        if (index >= arrayTopIndex | index < 0)
            throw new IllegalArgumentException("index is out of array size's bounds");
        int i = 0;
        while (i < arrayTopIndex && arr[i] >= arr[index]) //finding the first bigger value
            i++;
        int currMax = i;
        for (int j = i; j < arrayTopIndex; j++) //finding the smallest successor
            if (arr[j] > arr[currMax] & arr[index] > arr[j])
                currMax = j;
        if (currMax == index | currMax >= arrayTopIndex)
            throw new IllegalArgumentException("There isn't a predecessor to this index");
        return currMax;
    }

    @Override
    public void backtrack() { //Undo the last insert/delete action
        if (stack.isEmpty())
            throw new IllegalArgumentException("No actions preformed");
        String action = (String) stack.pop();
        if (action.charAt(0) == '1') { //Backtrack Insert action
            String value = action.split("-")[1]; //gets the inserted value
            if (arrayTopIndex != arr.length)
                arr[arrayTopIndex] = parseInt(value);
            arrayTopIndex--;
        } else { //Backtrack Delete action
            int index = parseInt(action.split("-")[1]); //gets the index of the deleted value
            int value = parseInt(action.split("-")[2]); //gets the deleted value
            arrayTopIndex++;
            for (int i = arrayTopIndex; i > index; i--) //returning the set to its prior state.
                arr[i] = arr[i - 1];
            arr[index] = value;
        }
    }

    @Override
    public void retrack() {
        /////////////////////////////////////
        // Do not implement anything here! //
        /////////////////////////////////////
    }

    @Override
    public void print() {
        if (arrayTopIndex == 0) //set is empty
            System.out.println("");
        else {
            String toPrint = "";
            for (int i = 0; i < arrayTopIndex - 1; i++)
                toPrint += arr[i] + " ";
            toPrint += arr[arrayTopIndex - 1]; //The last value shouldn't be followed by " ".
            System.out.println(toPrint);
        }
    }
}
