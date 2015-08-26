
public class SubClass extends SuperClass {
	public int x = 5;
	//Overriding the default method in super class
	@Override
	public void display() {
		System.out.println("Hi I am the subclass.");
		System.out.println("x = " + x);
	}
}
