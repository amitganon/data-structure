public class Warmup {

    public static int backtrackingSearch(int[] arr, int x, int forward, int back, Stack myStack) {
        if(forward<=back | back < 0 | arr == null | myStack == null)
            throw new IllegalArgumentException("The input is illegal");
        int i = 0;
         while(i<arr.length){
             for(int j = 0; j < forward; j++){
                 myStack.push(arr[i]);
                 if(arr[i] == x)
                     return i;
                 i++;
             }
             for(int j = 0; j < back; j++) {
                 myStack.pop();
                 i--;
             }
         }
         return -1;
    }

    public static int isConsistent() {
        double res = (Math.random()*100)-75;
        if (res > 0){
            return (int) Math.round(res / 10);
        }
        else {
            return 0;
        }
    }

    public static int consistentBinSearch(int[] arr, int x, Stack myStack) {
        int high = arr.length -1;
        int low = 0;
        while(low <= high){
            int mid = (high + low)/2;
            //int inconsistencies = Consistency.isConsistent(arr);
            int inconsistencies = isConsistent();
            if(arr[mid] == x)
                return mid;
            else{
                for(int i =0; i<inconsistencies*3; i=i+3){
                    high = (int)myStack.pop();
                    mid = (int)myStack.pop();
                    low = (int)myStack.pop();
                }
                myStack.push(low);
                myStack.push(mid);
                myStack.push(high);
                if(arr[mid] < x)
                    low = mid + 1;
                else
                    high = mid - 1;
            }
        }
        return -1;
    }
    public static void main(String[] args){
        int[] arr = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16};
        int x = 7;
        Stack myStack = new Stack();
        System.out.println(consistentBinSearch(arr, x, myStack));
    }
}
