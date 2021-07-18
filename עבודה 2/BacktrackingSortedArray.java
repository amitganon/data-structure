import static java.lang.Integer.parseInt;

public class BacktrackingSortedArray implements Array<Integer>, Backtrack {
    private Stack stack;
    public int[] arr; // This field is public for grading purposes. By coding conventions and best practice it should be private.
    private int arrayTopIndex; //Added to keep track on last index of the actual(non-trash) array

    // Do not change the constructor's signature
    public BacktrackingSortedArray(Stack stack, int size) {
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
        int low = 0;
        int high = arrayTopIndex - 1;
        int mid = (high + low) / 2;
        while (low <= high) {
            int tmp = arr[mid];
            if (tmp == k)
                return mid;
            else if (tmp > k)
                high = mid - 1;
            else
                low = mid + 1;
            mid = (high + low) / 2;
        }
        return -1;
    }

    @Override
    public void insert(Integer x) { //Inserts the value to the next free index
        if (arrayTopIndex == arr.length) //Array is full
            throw new RuntimeException("data structure overflow");
        if (arrayTopIndex == 0) { //Array is empty
            stack.push("1-" + arr[0] + "-" + x);
            arr[0] = x;
        } else { //searching for the predecessor
            int low = 0;
            int high = arrayTopIndex - 1;
            int mid = (high + low) / 2; //using average for binary search
            while (low < high) {
                int tmp = arr[mid];
                if (tmp > x)
                    high = mid - 1;
                else
                    low = mid + 1;
                mid = (high + low) / 2;
            } //finding the index which we will insert the value before/after it
            String action = "1-" + arr[arrayTopIndex]; //for backtracking, 1=insert
            if (arr[mid] < x) { //insert after mid
                for (int i = arrayTopIndex; i > mid + 1; i--)
                    arr[i] = arr[i - 1];
                arr[mid + 1] = x;
                action = action + "-" + (mid + 1);
            } else { //insert before mid
                for (int i = arrayTopIndex; i > mid; i--)
                    arr[i] = arr[i - 1];
                arr[mid] = x;
                action = action + "-" + mid;
            }
            stack.push(action); //pushing backtracking info to the stack
        }
        arrayTopIndex++;
    }

    @Override
    public void delete(Integer index) { //Deletes the value in the index
        if (index >= arr.length | index < 0)
            throw new IllegalArgumentException("index is out of array size's bounds");
        if (index >= arrayTopIndex)
            throw new IllegalArgumentException("A value hasn't been inserted to this index");
        stack.push("0-" + arr[index] + "-" + index); //pushing to the stack for backtracking, 0=delete
        for (int i = index; i < arrayTopIndex - 1; i++)
            arr[i] = arr[i + 1];
        arrayTopIndex--; //making the last index's set value irrelvant
    }

    @Override
    public Integer minimum() { //Gets the minimum value
        if (arrayTopIndex == 0)
            throw new RuntimeException("The Array is empty");
        return 0; //Array is sorted, minimum will be in index 0
    }

    @Override
    public Integer maximum() { //Gets the maximum value
        if (arrayTopIndex == 0)
            throw new RuntimeException("The Array is empty");
        return arrayTopIndex - 1; //Array is sorted, maximum will be in the last index
    }

    @Override
    public Integer successor(Integer index) { //Gets the successor of the given value
        if (index < 0 | index >= arrayTopIndex)
            throw new IllegalArgumentException("Index not in the array");
        if (index == arrayTopIndex - 1)
            throw new IllegalArgumentException("There isn't a successor to the maximal variable");
        return index + 1; //Array is sorted, successor will be 1 index after
    }

    @Override
    public Integer predecessor(Integer index) { //Gets the predecessor of the given value
        if (index < 0 | index >= arrayTopIndex)
            throw new IllegalArgumentException("Index not in the array");
        if (index == 0)
            throw new IllegalArgumentException("There isn't a predecessor to the minimal variable");
        return index - 1; //Array is sorted, predecessor will be 1 index before
    }

    @Override
    public void backtrack() { //Undo the last insert/delete action
        if (stack.isEmpty())
            throw new IllegalArgumentException("No actions preformed");
        String action = (String) stack.pop();
        if (action.charAt(0) == '1') { //Backtrack Insert action
            int prevValue = parseInt(action.split("-")[1]); //gets the previous value
            int index = parseInt(action.split("-")[2]); //gets the inserted index
            arrayTopIndex--;
            for (int i = index; i < arrayTopIndex; i++) //returning the set to its prior state.
                arr[i] = arr[i + 1];
            arr[arrayTopIndex] = prevValue;
        } else { //Backtrack Delete action
            int value = parseInt(action.split("-")[1]); //gets the deleted value
            int index = parseInt(action.split("-")[2]); //gets the index of the deleted value
            arrayTopIndex++;
            for (int i = arrayTopIndex - 1; i > index; i--) //returning the set to its prior state.
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
