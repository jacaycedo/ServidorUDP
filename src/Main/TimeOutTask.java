package Main;

import java.util.Timer;
import java.util.TimerTask;

public class TimeOutTask extends TimerTask{

	private ClienteUDP t;
	private Timer timer;
	
	
	TimeOutTask(Timer timer, ClienteUDP t ) 
	{
		this.timer = timer;
		this.t = t;
	}


	@Override
	public void run() 
	{
		if(t != null && t.isAlive())
		{
			t.interrupt();
			t.apagar();
			timer.cancel();
		}
	}
}
