package com.epam.rd.java.basic.task8;

import com.epam.rd.java.basic.task8.controller.*;
import com.epam.rd.java.basic.task8.entity.Flowers;
import com.epam.rd.java.basic.task8.util.Sorter;

public class Main {
	
	public static void main(String[] args) throws Exception {
		if (args.length != 1) {
			return;
		}
		
		String xmlFileName = args[0];
		System.out.println("Input ==> " + xmlFileName);
		
		////////////////////////////////////////////////////////
		// DOM
		////////////////////////////////////////////////////////

		DOMController domController = new DOMController(xmlFileName);
		Flowers domFlowers = domController.parse();

		Sorter.sortByName(domFlowers);
		String outputXmlFile = "output.dom.xml";

		DOMController.writeXML(domFlowers, outputXmlFile);
		////////////////////////////////////////////////////////
		// SAX
		////////////////////////////////////////////////////////

		SAXController saxController = new SAXController(xmlFileName);
		saxController.parse();

		Flowers saxFlowers = saxController.getFlowers();
		Sorter.sortBySoil(saxFlowers);

		outputXmlFile = "output.sax.xml";
		DOMController.writeXML(saxFlowers, outputXmlFile);


		////////////////////////////////////////////////////////
		// StAX
		////////////////////////////////////////////////////////
		
		// get
		STAXController staxController = new STAXController(xmlFileName);
		staxController.parse();

		Flowers staxFlowers = staxController.getFlowers();

		outputXmlFile = "output.stax.xml";
		DOMController.writeXML(staxFlowers, outputXmlFile);


	}

}
