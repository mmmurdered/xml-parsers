package com.epam.rd.java.basic.task8.controller;

import com.epam.rd.java.basic.task8.entity.Flower;
import com.epam.rd.java.basic.task8.entity.Flowers;
import com.epam.rd.java.basic.task8.entity.growingParams.GrowingTips;
import com.epam.rd.java.basic.task8.entity.growingParams.Lighting;
import com.epam.rd.java.basic.task8.entity.growingParams.Temperature;
import com.epam.rd.java.basic.task8.entity.growingParams.Watering;
import com.epam.rd.java.basic.task8.entity.visualParams.AveLenFlower;
import com.epam.rd.java.basic.task8.entity.visualParams.VisualParameters;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.*;
import javax.xml.transform.stream.StreamSource;

/**
 * Controller for StAX parser.
 */
public class STAXController extends DefaultHandler {

	private String xmlFileName;

	private final static String TAG_FLOWERS = "flowers";
	private final static String TAG_FLOWER = "flower";

	private final static String TAG_NAME = "name";
	private final static String TAG_SOIL = "soil";
	private final static String TAG_ORIGIN = "origin";

	private final static String TAG_VISUAL_PARAMETERS = "visualParameters";
	private final static String TAG_STEM_COLOUR = "stemColour";
	private final static String TAG_LEAF_COLOUR = "leafColour";
	private final static String TAG_AVERAGE_LENGTH = "aveLenFlower";

	private final static String TAG_GROWING_TIPS = "growingTips";
	private final static String TAG_TEMPRETURE = "tempreture";
	private final static String TAG_LIGHTING = "lighting";
	private final static String TAG_LIGHTING_REQUIRE = "lightRequiring";
	private final static String TAG_WATERING = "watering";

	private final static String TAG_MULTIPLYING = "multiplying";

	private final static String TAG_MEASURE = "measure";

	private Flowers flowers;
	private Flower flower;
	private VisualParameters visualParameters;
	private GrowingTips growingTips;
	private AveLenFlower aveLenFlower;
	private Temperature tempreture;
	private Lighting lighting;
	private Watering watering;
	
	String currentElement;

	public STAXController(String xmlFileName) {
		this.xmlFileName = xmlFileName;
	}

	public Flowers getFlowers() {
		return flowers;
	}

	public void parse() throws XMLStreamException {
		XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
		//xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);
		xmlInputFactory.setProperty(XMLInputFactory.IS_NAMESPACE_AWARE, true);

		XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(new StreamSource(xmlFileName));

		while(xmlEventReader.hasNext()){
			XMLEvent xmlEvent = xmlEventReader.nextEvent();

			if (xmlEvent.isCharacters() && xmlEvent.asCharacters().isWhiteSpace()) {
				xmlEvent = xmlEventReader.nextEvent();
			}
			if (xmlEvent.isStartElement()) {
				processStartElement(xmlEvent);
			}
			if (xmlEvent.isEndElement()) {
				processEndElement(flower, xmlEvent);
			}
			if (xmlEvent.isCharacters()) {
				characterProcessing(flower, currentElement, xmlEvent);
			}
		}
		xmlEventReader.close();
	}

	private void processStartElement(XMLEvent xmlEvent) {
		StartElement startElement = xmlEvent.asStartElement();

		currentElement = startElement.getName().getLocalPart();

		if (TAG_FLOWERS.equals(currentElement)){
			flowers = new Flowers();
			return;
		}
		if (TAG_FLOWER.equals(currentElement)) {
			flower = new Flower();
			return;
		}
		if (TAG_VISUAL_PARAMETERS.equals(currentElement)) {
			visualParameters = new VisualParameters();
			return;
		}
		if (TAG_AVERAGE_LENGTH.equals(currentElement)) {
			aveLenFlower = new AveLenFlower();
			Attribute attribute = startElement.getAttributeByName(QName.valueOf(TAG_MEASURE));

			if (attribute != null) {
				aveLenFlower.setMeasure(attribute.getValue());
			}
			return;
		}
		if (TAG_GROWING_TIPS.equals(currentElement)) {
			growingTips = new GrowingTips();
			return;
		}
		if (TAG_TEMPRETURE.equals(currentElement)) {
			tempreture = new Temperature();
			Attribute attribute = startElement.getAttributeByName(QName.valueOf(TAG_MEASURE));

			if (attribute != null) {
				tempreture.setMeasure(attribute.getValue());
			}
			return;
		}
		if (TAG_LIGHTING.equals(currentElement)) {
			lighting = new Lighting();
			Attribute attribute = startElement.getAttributeByName(QName.valueOf(TAG_LIGHTING_REQUIRE));

			if (attribute != null) {
				lighting.setLightRequiring(attribute.getValue());
			}
			return;
		}
		if (TAG_WATERING.equals(currentElement)) {
			watering = new Watering();
			Attribute attribute = startElement
					.getAttributeByName(QName.valueOf(TAG_MEASURE));
			if (attribute != null) {
				watering.setMeasure(attribute.getValue());
			}
		}
	}

	private void processEndElement(Flower flower, XMLEvent xmlEvent) {
		EndElement endElement = xmlEvent.asEndElement();
		String localName = endElement.getName().getLocalPart();
		if (TAG_FLOWER.equals(localName)) {
			flowers.getFlowerList().add(flower);
			return;
		}
		if (TAG_VISUAL_PARAMETERS.equals(localName)) {
			flower.setVisualParameters(visualParameters);
			return;
		}
		if (TAG_GROWING_TIPS.equals(localName)) {
			flower.setGrowingTips(growingTips);
			return;
		}
		if (TAG_AVERAGE_LENGTH.equals(localName)) {
			visualParameters.setAveLenFlower(aveLenFlower);
			return;
		}
		if (TAG_TEMPRETURE.equals(localName)) {
			growingTips.setTemperature(tempreture);
			return;
		}
		if (TAG_LIGHTING.equals(localName)) {
			growingTips.setLighting(lighting);
			return;
		}
		if (TAG_WATERING.equals(localName)) {
			growingTips.setWatering(watering);
		}
	}

	private void characterProcessing(Flower flower, String currentElement, XMLEvent xmlEvent) {
		Characters characters = xmlEvent.asCharacters();
		if (TAG_NAME.equals(currentElement)) {
			flower.setName(characters.getData());
			return;
		}
		if (TAG_SOIL.equals(currentElement)) {
			flower.setSoil(characters.getData());
			return;
		}
		if (TAG_ORIGIN.equals(currentElement)) {
			flower.setOrigin(characters.getData());
			return;
		}
		if (TAG_MULTIPLYING.equals(currentElement)) {
			flower.setMultiplying(characters.getData());
			return;
		}
		if (TAG_STEM_COLOUR.equals(currentElement)) {
			visualParameters.setStemColour(characters.getData());
			return;
		}
		if (TAG_LEAF_COLOUR.equals(currentElement)) {
			visualParameters.setLeafColour(characters.getData());
			return;
		}
		if (TAG_AVERAGE_LENGTH.equals(currentElement)){
			aveLenFlower.setContent(Integer.parseInt(characters.getData()));
			return;
		}
		if (TAG_TEMPRETURE.equals(currentElement)){
			tempreture.setContent(Integer.parseInt(characters.getData()));
			return;
		}
		if (TAG_WATERING.equals(currentElement)){
			watering.setContent(Integer.parseInt(characters.getData()));
		}
	}

}