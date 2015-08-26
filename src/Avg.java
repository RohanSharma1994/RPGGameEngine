import java.util.*;
public class Avg {
	public static void main(String args[]) {
		Scanner scanner = new Scanner(System.in);
		int n, sum = 0;
		int n_size = 0;
		ArrayList <Integer> num = new ArrayList<Integer>();
		while(scanner.hasNext()) {
			try {
				n = Integer.parseInt(scanner.next());
				if (n < 0) {
					throw  new Exception("N must be positive.");
				}
				num.add(n);
				n_size++;
			}
			catch (InputMismatchException e) {
				System.out.println("Error, enter number again.");
			}
			catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		for(Integer x:num) {
			sum+=x;
		}
		double avg = sum/n_size;
		System.out.println("Average : " + avg);
	}

}
