package xplatform.util.xml;

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
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import xplatform.platform.devices.DeviceDescriptor;


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
		String type = ((Element)node).getAttribute("type");
		String jarName = ((Element)node).getAttribute("jar");
		
		return new DeviceDescriptor(attValue, name, classpath, jarpath, type, jarName);
	}
	
	public ArrayList<DeviceDescriptor> getOperatorDescriptors(String nodeName, ArrayList<String> childsNodeValues){
		
		NodeList list = doc.getElementsByTagName(nodeName);
		if(list==null){
			return null;
		}
		
		ArrayList<Node> controllerCandidates = new ArrayList<Node>();
		ArrayList<Node> controllers = new ArrayList<Node>();
		
		for(int i = 0 ; i < list.getLength() ; i++)
		{
			if(!((Element)list.item(i)).getAttribute("id").equals(""))
				controllerCandidates.add(list.item(i));
		}
		
		for(Node node: controllerCandidates){
			ArrayList<String> nodeValues = this.getChildNodeValue(node);
			boolean flg = true;
			if(nodeValues.size()==childsNodeValues.size()){
				for(int i = 0 ; i < childsNodeValues.size() ; i++){
					if(!nodeValues.contains(childsNodeValues.get(i))){
						flg = false;
						break;
					}
				}
				
				if(flg) controllers.add(node);
			}
		}
		
		ArrayList<DeviceDescriptor> descriptors = this.getDescriptors(controllers);
		return descriptors;
	}
	
	
	private ArrayList<String> getChildNodeValue(Node node){
		ArrayList<String> childs = new ArrayList<String>();
		
		NodeList list = node.getChildNodes();
		for(int i = 0 ; i<list.getLength() ; i++){
			Node child = list.item(i);
			
			String value = child.getTextContent();
			if(value!=null &&!value.trim().equals("")){
				childs.add(value); 
			}
		}
		
		return childs;
	}
	
	
	public ArrayList<DeviceDescriptor> getOperatorControllerDescriptors(){
		ArrayList<Node> nodes = this.getNodesByAttribute("Device", "jar");
		return getDescriptors(nodes);
	}
	
	private ArrayList<DeviceDescriptor> getDescriptors(ArrayList<Node> nodes) {
		ArrayList<DeviceDescriptor> descriptors = new ArrayList<DeviceDescriptor>();
		
		for(Node node: nodes){
			String id = ((Element)node).getAttribute("id");
			String name = ((Element)node).getAttribute("name");
			String classpath = ((Element)node).getAttribute("classpath");
			String jarpath = ((Element)node).getAttribute("jarpath");
			String type = ((Element)node).getAttribute("type");
			String jarName = ((Element)node).getAttribute("jar");
			
			descriptors.add(new DeviceDescriptor(id, name, classpath, jarpath, type, jarName));
		}
		
		
		return descriptors;
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
	
	private ArrayList<Node> getNodesByAttribute(String nodeName, String attName){
		NodeList list = doc.getElementsByTagName(nodeName);
		ArrayList<Node> controller = new ArrayList<Node>();
		
		for(int i = 0 ; i < list.getLength() ; i++)
		{
			if(!((Element)list.item(i)).getAttribute(attName).equals(""))
				controller.add(list.item(i));
		}
		
		return controller;
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