package com.epam.rd.java.basic.task8.controller;

import com.epam.rd.java.basic.task8.entity.Flower;
import com.epam.rd.java.basic.task8.entity.Flowers;
import com.epam.rd.java.basic.task8.entity.growingParams.GrowingTips;
import com.epam.rd.java.basic.task8.entity.growingParams.Lighting;
import com.epam.rd.java.basic.task8.entity.growingParams.Temperature;
import com.epam.rd.java.basic.task8.entity.growingParams.Watering;
import com.epam.rd.java.basic.task8.entity.visualParams.AveLenFlower;
import com.epam.rd.java.basic.task8.entity.visualParams.VisualParameters;
import com.epam.rd.java.basic.task8.util.Sorter;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;

/**
 * Controller for SAX parser.
 */
public class SAXController extends DefaultHandler {

    private String xmlFileName;
    private String currentTagName;

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


    public SAXController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Flowers getFlowers() {
        return flowers;
    }

    public void parse() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        saxParserFactory.setNamespaceAware(true);
        SAXParser saxParser = saxParserFactory.newSAXParser();
        saxParser.parse(xmlFileName, this);

    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        uri = "";
        currentTagName = localName;
        if (TAG_FLOWERS.equals(currentTagName)) {
            //System.out.println("STARTFLOWER S");
            flowers = new Flowers();
            return;
        }
        if (TAG_FLOWER.equals(currentTagName)) {
            //ystem.out.println("STARTFLOWER");
            flower = new Flower();
            return;
        }
        if (TAG_VISUAL_PARAMETERS.equals(currentTagName)) {
            visualParameters = new VisualParameters();
            return;
        }
        if (TAG_GROWING_TIPS.equals(currentTagName)) {
            growingTips = new GrowingTips();
            return;
        }
        if (TAG_AVERAGE_LENGTH.equals(currentTagName)) {
            readAveLen(uri, attributes);
            return;
        }
        if (TAG_TEMPRETURE.equals(currentTagName)) {
            readTempreture(uri, attributes);
            return;
        }
        if (TAG_LIGHTING.equals(currentTagName)) {
            readLighting(uri, attributes);
            return;
        }
        if (TAG_WATERING.equals(currentTagName)) {
            readWatering(uri, attributes);
        }

    }

    private void readAveLen(String uri, Attributes attributes) {
        aveLenFlower = new AveLenFlower();

        if(attributes.getLength() > 0)
            aveLenFlower.setMeasure(attributes.getValue(uri, TAG_MEASURE));
    }

    private void readTempreture(String uri, Attributes attributes) {
        tempreture = new Temperature();

        if(attributes.getLength() > 0)
            tempreture.setMeasure(attributes.getValue(uri, TAG_MEASURE));
    }

    private void readLighting(String uri, Attributes attributes) {
        lighting = new Lighting();

        if(attributes.getLength() > 0)
            lighting.setLightRequiring(attributes.getValue(uri, TAG_LIGHTING_REQUIRE));
    }

    private void readWatering(String uri, Attributes attributes) {
        watering = new Watering();

        if(attributes.getLength() > 0)
            watering.setMeasure(attributes.getValue(uri, TAG_MEASURE));
    }

    @Override
    public void endElement(String uri, String localName, String qName) {
        //System.out.println("ENDELEM");
        if (TAG_FLOWER.equals(localName)) {
            //System.out.println("ENDFLOWER");
            flowers.getFlowerList().add(flower);
            //flowers.getFlowerList().add(flower);
            return;
        }
        if (TAG_VISUAL_PARAMETERS.equals(localName)) {
            //System.out.println("ENDVISUAL");
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
            //System.out.println("ENDLIGHT");
            growingTips.setLighting(lighting);
            return;
        }
        if (TAG_WATERING.equals(localName)) {
            //System.out.println("ENDWATER");
            growingTips.setWatering(watering);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) {
        String newString = new String(ch, start, length).trim();

        if(newString.isEmpty()){
            return;
        }
        /*if(newString.isBlank()){
            return;
        }*/
        if (TAG_NAME.equals(currentTagName)) {
            flower.setName(newString);
            return;
        }
        if (TAG_SOIL.equals(currentTagName)) {
            flower.setSoil(newString);
            return;
        }
        if (TAG_ORIGIN.equals(currentTagName)) {
            flower.setOrigin(newString);
            return;
        }
        if (TAG_STEM_COLOUR.equals(currentTagName)) {
            visualParameters.setStemColour(newString);
            return;
        }
        if (TAG_LEAF_COLOUR.equals(currentTagName)) {
            visualParameters.setLeafColour(newString);
            return;
        }
        if (TAG_MULTIPLYING.equals(currentTagName)) {
            flower.setMultiplying(newString);
            return;
        }
        if (TAG_AVERAGE_LENGTH.equals(currentTagName)) {
            aveLenFlower.setContent(Integer.parseInt(newString));
            return;
        }
        if (TAG_TEMPRETURE.equals(currentTagName)) {
            tempreture.setContent(Integer.parseInt(newString));
            return;
        }
        if (TAG_WATERING.equals(currentTagName)) {
            watering.setContent(Integer.parseInt(newString));
        }
    }

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        SAXController saxController = new SAXController("input.xml");
        saxController.parse();

        Flowers flowers1 = saxController.getFlowers();
        //System.out.println(flowers1.getFlowerList().size());
        System.out.println(flowers1.getFlowerList().get(0));
        System.out.println(flowers1.getFlowerList().get(1));
    }
}