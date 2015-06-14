package exbot.platform.common.app.state.exception;

public class RunningException  extends Exception{

	private static final long serialVersionUID = -7966088336274343902L;
	private String exceptionType;
	
	public RunningException(String exceptionType){
		this.exceptionType = exceptionType;
	}

	public final String getExceptionType() {
		return exceptionType;
	}	
}
