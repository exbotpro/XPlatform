package exbot.platform.common.app.state;

import exbot.platform.common.app.state.exception.InitException;

public class ReadyState extends AppState{
	private boolean findAll = false;
	public ReadyState(String id, String type) {
		super(id, type);
	}
	
	@Override
	public Operator initApp() throws InitException {
		return null;
	}

	@Override
	public boolean waitPRDApp() {
		System.out.println("Ready State: " + type);
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

	@Override
	public void unpluggedApp() {
		
	}

	@Override
	public void suspendApp() {
		
	}

}
