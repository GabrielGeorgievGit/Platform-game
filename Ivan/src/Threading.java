
public class Threading extends Thread{
	 
	public void run(int a) {
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.print("Ivan");
	}
}
