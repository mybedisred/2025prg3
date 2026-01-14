/*
*Author: Spencer Gilcrest
*Date: 11/20/25
*This program allows you to know if water can flow off a map with varying elevations. tests in main method
 */
public class WaterFlowing{
    //precondition: map has been initialized
    //returns true of water can flow off - Returns false if it cannot
    public static boolean canFlowOff(int[][] map, int row, int col){
        //base case (water on border)
        if (row == 0 || col == 0 || row == map.length - 1 || col ==  map[0].length - 1){
            return true;
        }

        int current = map[row][col];
        
        //if been here
        if (current < 0){
            return false;
        }

        //already been here
        map[row][col] = -current;

        //check up
        if(map[row-1][col] < current && canFlowOff(map, row - 1, col)){
            map[row][col] = current;
            return true;
        }

        //check down
        if(map[row+1][col] < current && canFlowOff(map, row + 1, col)){
            map[row][col] = current;
            return true;
        }

        //check left
        if(map[row][col-1] < current && canFlowOff(map, row, col - 1)){
            map[row][col] = current;
            return true;
        }

        //check right
        if(map[row][col+1] < current && canFlowOff(map, row, col + 1)){
            map[row][col] = current;
            return true;
        }
        
        map[row][col] = current;
        return false;
    }

    public static void main (String[] args){
        int[][] map = {
			{100, 99, 200, 200, 200, 200, 200, 200, 200, 200}, 
			{200, 98, 200, 200, 200, 200, 200, 200, 200, 200},
			{200, 97, 96, 200, 200, 200, 200, 200, 200, 200},
			{200, 200, 95, 200, 200, 200, 85, 84, 83, 200},
			{200, 200, 94, 93, 92, 200, 86, 200, 82, 200},
			{200, 150, 200, 200, 91, 200, 87, 200, 81, 200},
			{200, 200, 200, 200, 90, 89, 88, 200, 80, 200},
			{200, 150, 100, 200, 200, 200, 200, 200, 79, 200},
			{200, 200, 200, 200, 200, 200, 200, 200, 78, 77},
			{200, 98, 200, 200, 200, 200, 200, 200, 200, 76}		
	    };

        System.out.println("should be true: " + canFlowOff(map, 2, 2));
        System.out.println("should be true: " + canFlowOff(map, 3, 0));
        System.out.println("should be true: " + canFlowOff(map, 3, 1));
        System.out.println("should be false: " + canFlowOff(map, 6, 1));

        int[][] anuthaOne = {
            {100, 200, 200, 200, 200, 200, 200, 200, 200, 200}, 
            {200, 200, 200, 200, 200, 200, 200, 200, 200, 200},
            {200, 10, 200, 200, 200, 200, 200, 200, 200, 200},
            {200, 11, 10, 200, 200, 6, 85, 84, 83, 200},
            {200, 200, 14, 15, 59, 200, 86, 200, 82, 200},
            {200, 11, 12, 200, 200, 200, 87, 200, 81, 200},
            {200, 10, 200, 200, 90, 89, 88, 200, 200, 200},
            {200, 9, 8, 200, 200, 200, 200, 200, 200, 200},
            {200, 200, 7, 200, 200, 200, 200, 200, 200, 200},
            {200, 98, 6, 200, 200, 200, 200, 200, 200, 200}
            };
        
        System.out.println("other map also should be true: " + canFlowOff(anuthaOne, 4, 5));


    }
}