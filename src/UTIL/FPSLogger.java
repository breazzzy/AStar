package UTIL;

public class FPSLogger{
	long start;
	int n;
	
	public FPSLogger(){
		start = System.nanoTime();
	}
	public void log(){
		n++;
		if(System.nanoTime() - start> 1000000000) /* One second;*/{
			System.out.printf("Curent Fps: %d\n" , n);
			start = System.nanoTime();
			n = 0;
		}
	}
}