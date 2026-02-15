public class Heap {

//the actual storage structure for your heap
private int[] arr;
private int size;

//constructor for your heap
public Heap() {

arr = new int[100];
size = 0;

}

 

 

//create this function to add elements to your heap

//all heap properties must be preserved

// 5 points

public void add(int toAdd) {
    arr[size] = toAdd;
    siftUp(size);
    size++;
    }
 



 

//remove the largest element of the heap (the root) and re-heapafy

//5 points

public void removeMax() {
    if (size == 0) return;
    arr[0] = arr[size - 1];
    size --;
    siftDown(0);
}

 

//this should check and alter the tree after an item is inserted

//3 points

private void siftUp(int index) {
    if (index == 0) return;

    int parentIndex = (index - 1)/2;
    if (arr[index] > arr[parentIndex]){
        int temp = arr[index];
        arr[index] = arr[parentIndex];
        arr[parentIndex] = temp;
        siftUp(parentIndex);
    }
}

 

//this should check and alter the tree after an item is deleted.

//3 points

private void siftDown(int index) {
    int left = 2 * index + 1;
    int right = 2 * index + 2;
    int biggest = index;

    if (left < size && arr[left] > arr[biggest]){
        biggest = left;
    }
    if (right < size && arr[right] > arr[biggest]){
        biggest = right;
    }

    if (biggest != index){
        int temp = arr[index];
        arr[index] = arr[biggest];
        arr[biggest] = temp;
        siftDown(biggest);
    }
}

 
}
//4 points for syntax conventions.