package exbot.platform.common.app.state;

import exbot.platform.common.app.state.exception.InitException;

public class ReadyState extends State{
	private boolean findAll = false;
	public ReadyState(String id) {
		super(id);
	}
	
	@Override
	public Operator initApp() throws InitException {
		return null;
	}

	@Override
	public boolean waitPRDApp() {
		System.out.println("Ready State");
		if(super.publisherList.size() ==0) findAll = true;
		
		while(!findAll){
			for(String publisherId: super.publisherList){
				if(super.getDevice(publisherId)==null){
					break;
				}
				super.getOp().requestSubscription(super.getDevice(publisherId));
				findAll = true;
			}
		}
		
		return findAll;
	}

	@Override
	public void runApp(Operator operator) {
		
	}

	@Override
	public void terminateApp() {
		
	}


}
