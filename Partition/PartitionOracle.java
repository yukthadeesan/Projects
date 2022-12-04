import java.util.Arrays;
import java.util.Random;


public class PartitionOracle {

    public static int runPartition(Partitioner p, String[] strs, int low, int high) {
        try {
            return p.partition(strs, low, high);
        } catch (Throwable t) {
            return -1;
        }
    }

	public static void printArray(String[] array) {
    	System.out.print("array: ");
    	for(int ch=0; ch<array.length; ch++) {
    		System.out.print(array[ch]+" ");
    	}
	}

    public static String isValidPartitionResult(String[] before, int low, int high, int pivot, String[] after) {
    	String[] copyOfBefore= Arrays.copyOf(before, before.length);
    	String[] copyOfAfter= Arrays.copyOf(after, after.length);
		int a= 0;
		int b= 0;
		for(int i=0; i<pivot; i++) {
			if(after[i].compareTo(after[pivot])<=0) {
				a+=1;
			}
		}
		for(int j=pivot+1; j<after.length;j++){
			if(after[j].compareTo(after[pivot])>=0){
				b+=1;
			}
		}
		if(before.length!= after.length) { //checking for equal length
			return "array doesnt have equal number of items after partition";
		}
		if(a+b!=(after.length-1)){ //checking if all the elements before pivot are smaller and all the elements after pivot are greater
			return "array is not properly sorted";
		}
		if(low<0==true) { //checking low validity
			return ("low is out of range");
		}
		if(high>before.length==true) { //checking high validity
			return ("high is out of range");
		}
		if(low>high==true) { //checking if low is lesser than high 
			return ("low is greater than high");
		}
		if(pivot<0==true){ //checking pivot validity
			return ("negative pivot");			
		}
		if(pivot<low==true) {
			return ("pivot out of range");
		}
		if(pivot>high==true) {
			return ("pivot out of range");
		}
		if(a< pivot-low==true) { //checking elements before pivot 
			return ("item before pivot too large");
		}
		if(b< high-1-pivot==true) { //checking elements after pivot 
			return ("item before pivot too large");
		}
		if(copyOfBefore.length==copyOfAfter.length) { //checking if the before and after arrays have same number of repeating elements 
			Arrays.sort(copyOfBefore);
			Arrays.sort(copyOfAfter);
			int count=0;
			for(int i=0; i<copyOfBefore.length; i++) {
				if(copyOfBefore[i].equals(copyOfAfter[i])) {
					count+=1;
				}
			}
			if(count!=copyOfBefore.length) {
				return ("does not contain same number of elements");
			}
			else {
				return null;
			}			
		}
		return null;

    }
  
    //generates a string array with one element. 
    public static String[] generateInput(int n) {
    	String[] arr= new String[n];
    	Random random= new Random();
    	for(int i=0; i<n; i++) {
    		int r = 97 + random.nextInt(26); //generates an integer between 97 and 122 for a-z ASCII values
    		arr[i]=Character.toString(r);
    	}
        return arr;
    }

    public static CounterExample findCounterExample(Partitioner p) { 
    	for(int i=0; i<20; i++) { //generating 20 random arrays of length 5. 
	    	String[] before= generateInput(5);
	    	String[] newbefore= Arrays.copyOf(before, before.length);
	    	int pivot= runPartition(p, before, 0, before.length);
	    	String[] after= before;
	    	if(isValidPartitionResult(newbefore, 0, newbefore.length, pivot, after)==null) { 
	    		System.out.println("Partitioner worked");
	    		}
	    	else {
	    		String error= isValidPartitionResult( newbefore, 0, newbefore.length, pivot, after);
	    		CounterExample coex= new CounterExample(newbefore, 0, newbefore.length, pivot, after, error );
	    		System.out.println(coex.toString());
	    		return coex;
	    		}
    	}
    	return null;
    }
}
