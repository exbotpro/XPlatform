package xplatform.platform.common.app.state.exception;

public class InitException  extends Exception{

	private static final long serialVersionUID = -7966088336274343902L;
	private String exceptionType;
	
	public InitException(String exceptionType){
		this.exceptionType = exceptionType;
	}

	public final String getExceptionType() {
		return exceptionType;
	}	
}
