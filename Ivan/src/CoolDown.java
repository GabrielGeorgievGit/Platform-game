//only dashing
public class CoolDown implements Runnable {
	public int time;
	public static GameObject obj;
	public static boolean isMoving[];
	
	public void dashing(GameObject o) {
			
			o.dashReady = false;
			
			if(o.sidefaced) o.setSpeedX(o.dashSpeed);
			else o.setSpeedX(-o.dashSpeed);
			Ivan.timer(o.dashTime);
			//o.setSpeedX(0);
			//making player keep speed after dash
			if(isMoving[3])
				o.setSpeedX(o.speedInUse[1]);
			else if(isMoving[1]) 
				o.setSpeedX(o.speedInUse[0]);
			else o.setSpeedX(0);
			
			System.out.println("TIMER START");
			time = o.dashCD;
			
		}
		public void run() {
			dashing(obj);
			Ivan.timer(time);

			System.out.println("TIMER END");
			obj.dashReady = true;
		}
		

}
