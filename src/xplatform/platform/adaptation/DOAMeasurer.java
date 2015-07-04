package xplatform.platform.adaptation;

public class DOAMeasurer {

	public static double getAverage(int k, double x_bar_prev, double x_cur)
	{
		return ((double)(k-1)/(double)k)*x_bar_prev + (1/(double)k)*(double)x_cur;
	}
	
	public static double getMovingAverage(int n, double x_bar_prev, double x_cur, double x_init)
	{
		return x_bar_prev + ((x_cur - x_init)/(double)n);
	}
}