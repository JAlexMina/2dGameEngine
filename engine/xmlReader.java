package engine;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import org.xml.sax.*;
import org.w3c.dom.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class xmlReader {

    public ArrayList<String> xPos = new ArrayList<String>();
    public ArrayList<String> yPos = new ArrayList<String>();
    public ArrayList<String> m_objects = new ArrayList<String>();

    public ArrayList<String> xPos_out = new ArrayList<String>();
    public ArrayList<String> yPos_out = new ArrayList<String>();
    public ArrayList<String> m_objects_out = new ArrayList<String>();


    private ArrayList<String> rolev;




    public boolean readXML(String xml) {
        rolev = new ArrayList<String>();
        Document dom;
        // Make an  instance of the DocumentBuilderFactory
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            // use the factory to take an instance of the document builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            // parse using the builder to get the DOM mapping of the
            // XML file
            dom = db.parse(xml);

            Element doc = dom.getDocumentElement();

            if (doc.hasChildNodes())
            {
                NodeList nodeList = doc.getChildNodes();
                p(nodeList);
            }

            return true;

        }


        catch (ParserConfigurationException pce) {
            java.lang.System.out.println(pce.getMessage());
        } catch (SAXException se) {
            java.lang.System.out.println(se.getMessage());
        } catch (IOException ioe) {
            java.lang.System.err.println(ioe.getMessage());
        }

        return false;
    }


    void p(NodeList nodeList)
    {
        for (int count = 0; count < nodeList.getLength(); count++) {

            Node tempNode = nodeList.item(count);

            // make sure it's element node.
            if (tempNode.getNodeType() == Node.ELEMENT_NODE) {

                if(tempNode.getNodeName() == "xPos")
                {
                    xPos.add(tempNode.getTextContent());
                }
                else if(tempNode.getNodeName() == "yPos")
                {
                    yPos.add(tempNode.getTextContent());
                }


                if (tempNode.hasAttributes())
                {
                    // get attributes names and values
                    NamedNodeMap nodeMap = tempNode.getAttributes();
                    for (int i = 0; i < nodeMap.getLength(); i++)
                    {
                        Node node = nodeMap.item(i);
                        m_objects.add(node.getNodeValue());
                    }
                }



                if (tempNode.hasChildNodes()) {
                    // loop again if has child nodes
                    p(tempNode.getChildNodes());
                }

            }

        }
    }


    public void saveToXML(String xml) {


        DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Document document = documentBuilder.newDocument();



            // objs element
            Element objs = document.createElement("obj");

            document.appendChild(objs);

            for(int i = 0; i < m_objects_out.size(); i++)
            {
                Element obj = document.createElement("obj");

                objs.appendChild(obj);

                // set an attribute to staff element
                Attr attr = document.createAttribute("id");


                //the first doesn't have attribs all the rest do kinda bad assumption
                attr.setValue(String.valueOf(m_objects_out.get(i)));


                obj.setAttributeNode(attr);

                //you can also use staff.setAttribute("id", "1") for this

                // xPos element
                Element xPosit = document.createElement("xPos");
                xPosit.appendChild(document.createTextNode(xPos_out.get(i)));
                obj.appendChild(xPosit);

                // yPos element
                Element yPosit = document.createElement("yPos");
                yPosit.appendChild(document.createTextNode(yPos_out.get(i)));
                obj.appendChild(yPosit);
            }

        try {
                Transformer tr = TransformerFactory.newInstance().newTransformer();
                tr.setOutputProperty(OutputKeys.INDENT, "yes");
                tr.setOutputProperty(OutputKeys.METHOD, "xml");
                tr.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
                tr.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "pos.dtd");
                tr.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

                // send DOM to file
                tr.transform(new DOMSource(document),
                        new StreamResult(new FileOutputStream(xml)));

            } catch (Exception e) {
                java.lang.System.out.println(e.getMessage());
            }




    }

    private String getTextValue(String def, Element doc, String tag) {
        String value = def;
        NodeList nl;
        nl = doc.getElementsByTagName(tag);
        if (nl.getLength() > 0 && nl.item(0).hasChildNodes()) {
            value = nl.item(0).getFirstChild().getNodeValue();
        }
        return value;
    }

}
