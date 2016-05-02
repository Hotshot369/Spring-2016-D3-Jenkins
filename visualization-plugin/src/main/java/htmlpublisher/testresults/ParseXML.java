package htmlpublisher.testresults;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.ArrayList;

public class ParseXML {
    DocumentBuilderFactory factory;
    DocumentBuilder builder;
    Document document;

    ParseXML(String inputFilePath) throws Exception {
        factory = DocumentBuilderFactory.newInstance();
        builder = factory.newDocumentBuilder();
        document = builder.parse(new File(inputFilePath));

		/*
		 * TODO: Remove this old code
		Element root = document.getDocumentElement();
		System.out.println("No. of tests = " + root.getAttribute("tests") + "\nNo. of failures = " + root.getAttribute("failures"));
		NodeList nodeList = root.getElementsByTagName("testcase");
		for(int i=0; i<nodeList.getLength(); i++){
			Element temp = (Element)nodeList.item(i);
			System.out.println("Time taken to run testcase no. "+ (i+1) +" = "+ temp.getAttribute("time"));
			if(temp.getElementsByTagName("failure").getLength()>0){
				System.out.println("Testcase "+(i+1)+" has a failure with the message: "+((Element)temp.getElementsByTagName("failure").item(0)).getAttribute("message"));
			}
		}
		*/
    }

    public ArrayList<TestCase> getTestcases(){
        ArrayList<TestCase> tcArrayList = new ArrayList<TestCase>();
        Element root = document.getDocumentElement();
        NodeList nodeList = root.getElementsByTagName("testcase");
        for(int i=0; i<nodeList.getLength(); i++){
            Element temp = (Element)nodeList.item(i);
            TestCase t;
            if(temp.getElementsByTagName("failure").getLength()>0){
                // Then this testcase is a failure
                t = new TestCase(temp.getAttribute("name"), temp.getAttribute("classname"), Float.parseFloat(temp.getAttribute("time")), false);
                t.failureMessage = ((Element)temp.getElementsByTagName("failure").item(0)).getAttribute("message");
                t.failureType = ((Element)temp.getElementsByTagName("failure").item(0)).getAttribute("type");
            }
            else{
                t = new TestCase(temp.getAttribute("name"), temp.getAttribute("classname"), Float.parseFloat(temp.getAttribute("time")), true);
            }
            tcArrayList.add(t);
        }
        return tcArrayList;
    }

    public int[] getNoOfPassFailErrorTests(){
        Element root = document.getDocumentElement();
        int[] nPassFailError = new int[4];
        nPassFailError[0] = Integer.parseInt(root.getAttribute("tests")) - Integer.parseInt(root.getAttribute("failures")) - Integer.parseInt(root.getAttribute("errors")) - Integer.parseInt(root.getAttribute("skipped"));
        nPassFailError[1] = Integer.parseInt(root.getAttribute("failures"));
        nPassFailError[2] = Integer.parseInt(root.getAttribute("errors"));
        nPassFailError[3] = Integer.parseInt(root.getAttribute("tests"));
        return nPassFailError;
    }

    /*
    public static void main(String[] args) {
        try {
            //ParseXML p1 = new ParseXML("resources/uiPassFail.xml");
            TestSequence t1 = new TestSequence("resources/uiPassFail.xml");
            t1.writeTestSequenceDatatoCSVs();
        } catch (Exception e) {
            System.out.println("Error. WTH !!!!\nSee this stacktrace -");
            e.printStackTrace();
        }
    }
    */
}