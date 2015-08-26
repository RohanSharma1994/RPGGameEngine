
public class SuperClass {
	public int x = 3;
	public void display() {
		System.out.println("Hi, I am the super class.");
		System.out.println("x = " + x);
	}
	public void display2(SuperClass a) {
		a.display();
	}
}
