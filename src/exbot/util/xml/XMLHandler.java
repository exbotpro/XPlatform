package exbot.util.xml;

import java.io.File;
import java.util.ArrayList;
import java.util.Hashtable;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import exbot.platform.devices.DeviceDescriptor;


public class XMLHandler {
	private Document doc = null;
	private String docPath;
	public XMLHandler(String docPath){
		this.docPath = docPath;
		this.parse(new File(docPath));
	}
	public void parse(File fXmlFile){
		
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			doc = docBuilder.parse(fXmlFile);
			doc.getDocumentElement().normalize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public String getAttributeOfNode(String nodeName, String attName){
		String attValue = "";
		NodeList nodeList = doc.getElementsByTagName(nodeName);
		
		return "";
	}

	public ArrayList<String> getAttributesOfNode(String nodeName, String attName){
		Hashtable<String, ArrayList<String>> attValues = new Hashtable<String, ArrayList<String>>();
		
		NodeList nodeList = doc.getElementsByTagName(nodeName);
		return null;
	}
	
	public ArrayList<String> getAttributeOfNodes(String nodeName, String attName){
		ArrayList<String> attValues = new ArrayList<String>();
		NodeList nodeList = doc.getElementsByTagName(nodeName);
		
		for(int i =0 ; i < nodeList.getLength() ; i++){
			Element e = (Element)nodeList.item(i);
			attValues.add(e.getAttribute(attName));
		}
		
		return attValues;
	}
	
	public void addAttributeOfNode(String nodeName, String attName, String attValue){
		
	}

	public void addAttributeOfNode(String nodeName, ArrayList<String> attName, ArrayList<String> attValue){
		
	}
	
	public ArrayList<String> getAttributesOf(String Node){
		ArrayList<String> attributes = new ArrayList<String>();
		
		return attributes; 
	}

	
	public void addNode(String parentNodeName, String nodeName, Hashtable<String, String> attributes)
	{
		
			NodeList nList = doc.getElementsByTagName(parentNodeName);
			Element cp = (Element)nList.item(0);
			Element entry = doc.createElement(nodeName);
			
			for(String key: attributes.keySet())
			{
				entry.setAttribute(key, attributes.get(key));
			}
			cp.appendChild(entry);	
			
			try {
				save();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		

	}


	public DeviceDescriptor getOperatorDescriptorBy(String nodeName, String attName, String attValue){
		
		Node node = this.getNodeByAttribute(nodeName, "id", attValue);
		if(node==null){
			return null;
		}
		
		String name = ((Element)node).getAttribute("name");
		String classpath = ((Element)node).getAttribute("classpath");
		String jarpath = ((Element)node).getAttribute("jarpath");
		
		return new DeviceDescriptor(attValue, name, classpath, jarpath);
	}
	
	
	private Node getNodeByAttribute(String nodeName, String attName, String attValue){
		NodeList list = doc.getElementsByTagName(nodeName);
		for(int i = 0 ; i < list.getLength() ; i++)
		{
			if(((Element)list.item(i)).getAttribute(attName).equals(attValue))
				return list.item(i);
		}
		
		return null;
	}
	
	public void removeNodeByAttribute(String nodeName, String attName, String attValue) {
		Node node = this.getNodeByAttribute(nodeName, attName, attValue);
		if(node!=null) node.getParentNode().removeChild(node);
	}
	
	public void save() throws Exception{
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File(docPath));
        transformer.transform(source, result);
	}
	
}