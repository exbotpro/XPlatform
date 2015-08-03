package xplatform.platform.detector;

import java.util.Hashtable;

import javax.usb.UsbException;
import javax.usb.UsbHostManager;
import javax.usb.UsbServices;
import javax.usb.event.UsbServicesEvent;
import javax.usb.event.UsbServicesListener;

import xplatform.platform.common.app.AppContext;
import xplatform.platform.detector.controller.ControllAppDetector;
import xplatform.platform.devices.DeviceDescriptor;
import xplatform.platform.devices.DeviceLookupTable;
import xplatform.platform.devices.OperatorPool;
import xplatform.platform.devices.loader.JarLoaderFactory;

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
		busIdList.put("413c:2105", "413c:2105");
		
	}
	
	private void run(String id) {
		DeviceDescriptor desc = DeviceLookupTable.getLookupTable().getDeviceDescriptor(id);
		System.out.println(id + " Device has been detected.");
		AppContext context = AppContext.getContext(id, desc.getType());
		(new Thread(context)).start();
		ControllAppDetector.getControllerDetector().addDevice(id);
	}
	
	public void watch() {
		 try {
				UsbServices services = UsbHostManager.getUsbServices();
				services.addUsbServicesListener(new UsbServicesListener(){
					public void usbDeviceAttached(UsbServicesEvent arg0) {

						System.gc();
						//create appContext and run it!
						String id = getID(arg0.getUsbDevice().toString());
						if(isDevice(id)){
							run(id);
						}
					}
					
					public void usbDeviceDetached(UsbServicesEvent arg0) {
						String id = getID(arg0.getUsbDevice().toString());
						System.out.println("unplugged device: "+id);
						DeviceDescriptor desc = DeviceLookupTable.getLookupTable().getDeviceDescriptor(id);
						
						stop(id, desc);
						
//						ControllAppDetector.getControllerDetector().removeDevice(id);
//						String controllerId = ControllAppDetector.getControllerDetector().getContollAppId();
//						DeviceDescriptor controllerDesc = DeviceLookupTable.getLookupTable().getDeviceDescriptor(controllerId);
//						stop(controllerId, controllerDesc);
						
						System.gc();
					}

					private void stop(String id, DeviceDescriptor desc) {
						OperatorPool.getLookupTable().getOperator(id).stop();
						AppContext context = AppContext.getContext(id, desc.getType());
						context.changeToUnpluggedState();
						JarLoaderFactory.getClassLoaderFactory().renewClassLoader(id);
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
