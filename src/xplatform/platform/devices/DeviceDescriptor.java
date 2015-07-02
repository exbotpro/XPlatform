package xplatform.platform.devices;

public class DeviceDescriptor {

	private String id;
	private String name;
	private String classpath;
	private String jarpath;
	private String type;
	private String jarName;
	
	public DeviceDescriptor(String id, String name, String classpath, String jarpath, String type, String jarName) {
		this.id = id;
		this.name = name;
		this.classpath = classpath;
		this.jarpath = jarpath;
		this.type = type;
		this.jarName = jarName;
	}
	
	
	public final String getJarName() {
		return jarName;
	}


	public final void setJarName(String jarName) {
		this.jarName = jarName;
	}


	public final String getType() {
		return type;
	}


	public final void setType(String type) {
		this.type = type;
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
