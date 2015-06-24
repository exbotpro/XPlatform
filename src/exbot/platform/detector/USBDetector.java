package exbot.platform.detector;

import java.util.Hashtable;

import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbServices;
import javax.usb.event.UsbServicesEvent;
import javax.usb.event.UsbServicesListener;

import exbot.platform.common.app.AppContext;
import exbot.platform.devices.DeviceDescriptor;
import exbot.platform.devices.DeviceLookupTable;
import exbot.platform.devices.OperatorPool;

public class USBDetector extends Detector{
	Hashtable<String, String> busIdList = new Hashtable<String, String>();
	
	public USBDetector(){
		busIdList.put("8087:07dc", "8087:07dc");
		busIdList.put("17ef:1010", "17ef:1010");
		busIdList.put("8087:8008", "8087:8008");
		busIdList.put("04f2:b39a", "04f2:b39a");
		busIdList.put("138a:0017", "138a:0017");
		busIdList.put("046d:c52b", "046d:c52b");
		busIdList.put("17e9:03e0", "17e9:03e0");
		busIdList.put("17ef:100f", "17ef:100f");
		busIdList.put("0557:8021", "0557:8021");
		busIdList.put("045e:00db", "045e:00db");
		busIdList.put("8087:8000", "8087:8000");
		busIdList.put("0557:8021", "0557:8021");
		busIdList.put("045e:00db", "045e:00db");
	}
	
	
	public void watch() {
		 try {
				UsbServices services = UsbHostManager.getUsbServices();
				services.addUsbServicesListener(new UsbServicesListener(){
					public void usbDeviceAttached(UsbServicesEvent arg0) {
						//create appContext and run it!
						String id = getID(arg0.getUsbDevice().toString());
						if(isDevice(id)){
							DeviceDescriptor desc = DeviceLookupTable.getLookupTable().getDeviceDescriptor(id);
							System.out.println(id + " Device has been detected.");
							AppContext context = AppContext.getContext(id, desc.getType());
							(new Thread(context)).start();
						}
					}
					
					public void usbDeviceDetached(UsbServicesEvent arg0) {
						String id = getID(arg0.getUsbDevice().toString());
						DeviceDescriptor desc = DeviceLookupTable.getLookupTable().getDeviceDescriptor(id);
						System.out.println("unplugged device: "+id);
						OperatorPool.getLookupTable().getOperator(id).stop();
						
						AppContext context = AppContext.getContext(id, desc.getType());
						context.changeToUnpluggedState();
					}
					
		        });
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (UsbException e) {
			}
    }
	
	private String getID(String device){
		String[] tok = device.split(" ");
		return tok[tok.length-1];
	}
	
	private boolean isDevice(String id){
		return !busIdList.containsKey(id);
		
	}
}
