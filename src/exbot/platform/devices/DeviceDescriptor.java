package exbot.platform.devices;

public class DeviceDescriptor {

	private String id;
	private String name;
	private String classpath;
	private String jarpath;
	
	
	public DeviceDescriptor(String id, String name, String classpath, String jarpath) {
		this.id = id;
		this.name = name;
		this.classpath = classpath;
		this.jarpath = jarpath;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getClasspath() {
		return classpath;
	}
	public void setClasspath(String classpath) {
		this.classpath = classpath;
	}
	public String getJarpath() {
		return jarpath;
	}
	public void setJarpath(String jarpath) {
		this.jarpath = jarpath;
	}

	
}
