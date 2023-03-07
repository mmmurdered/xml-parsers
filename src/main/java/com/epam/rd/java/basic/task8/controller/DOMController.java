package com.epam.rd.java.basic.task8.controller;


import com.epam.rd.java.basic.task8.entity.*;
import com.epam.rd.java.basic.task8.entity.growingParams.GrowingTips;
import com.epam.rd.java.basic.task8.entity.growingParams.Lighting;
import com.epam.rd.java.basic.task8.entity.growingParams.Temperature;
import com.epam.rd.java.basic.task8.entity.growingParams.Watering;
import com.epam.rd.java.basic.task8.entity.visualParams.AveLenFlower;
import com.epam.rd.java.basic.task8.entity.visualParams.VisualParameters;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Controller for DOM parser.
 */
public class DOMController {
    private String xmlFileName;
    private Flowers flowers;

    public DOMController(String xmlFileName) {
        this.xmlFileName = xmlFileName;
    }

    public Flowers getFlowers() {
        return flowers;
    }

    public Document getDocument() {
        File file = new File(xmlFileName);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        Document document = null;

        try {
            document = documentBuilderFactory.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return document;
    }

    private Flower getFlower(Node flowerNode) {
        Node tempNode;
        Element element = (Element) flowerNode;

        String nameFlower = element.getElementsByTagName("name").item(0).getTextContent();
        String soilFlower = element.getElementsByTagName("soil").item(0).getTextContent();
        String originFlower = element.getElementsByTagName("origin").item(0).getTextContent();
        String multiplyingFlower = element.getElementsByTagName("multiplying").item(0).getTextContent();

        Flower flower = new Flower();

        flower.setName(nameFlower);
        flower.setSoil(soilFlower);
        flower.setOrigin(originFlower);
        flower.setGrowingTips(getGrowingTips(element.getElementsByTagName("growingTips").item(0)));
        flower.setVisualParameters(getVisualParameters(element.getElementsByTagName("visualParameters").item(0)));
        flower.setMultiplying(multiplyingFlower);

        return flower;
    }

    public Flowers parse() throws ParserConfigurationException {
        File file = new File(xmlFileName);

        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setFeature("http://xml.org/sax/features/validation", true);
        documentBuilderFactory.setFeature("http://apache.org/xml/features/validation/schema", true);

        Document document = null;

        try {
            document = documentBuilderFactory.newDocumentBuilder().parse(file);
        } catch (Exception e) {
            e.printStackTrace();
        }



        Flowers flowers = new Flowers();
        NodeList flowerNode = document.getElementsByTagName("flower");
        for(int i = 0; i < flowerNode.getLength(); i++){
            Flower flower = getFlower(flowerNode.item(i));
            flowers.getFlowerList().add(flower);
        }

        return flowers;
    }

    private GrowingTips getGrowingTips(Node growingTipsNode) {
        GrowingTips growingTips = new GrowingTips();
        Element growingTipsElement = (Element) growingTipsNode;

        NodeList temperatureNodes = growingTipsElement.getElementsByTagName("tempreture");
        for (int i = 0; i < temperatureNodes.getLength(); i++) {
            Temperature temperature = new Temperature();
            Element measureElement = (Element) temperatureNodes.item(i);

            temperature.setContent(Integer.parseInt(temperatureNodes.item(i).getTextContent()));
            temperature.setMeasure(measureElement.getAttribute("measure"));

            growingTips.setTemperature(temperature);
        }

        NodeList lightingNodes = growingTipsElement.getElementsByTagName("lighting");
        for (int i = 0; i < lightingNodes.getLength(); i++) {
            Lighting lighting = new Lighting();
            Element element = (Element) lightingNodes.item(i);

            lighting.setLightRequiring(element.getAttribute("lightRequiring"));

            growingTips.setLighting(lighting);
        }

        NodeList wateringNodes = growingTipsElement.getElementsByTagName("watering");
        for (int i = 0; i < wateringNodes.getLength(); i++) {
            Watering watering = new Watering();
            Element measureElement = (Element) wateringNodes.item(i);

            watering.setContent(Integer.parseInt(measureElement.getTextContent()));
            watering.setMeasure(measureElement.getAttribute("measure"));

            growingTips.setWatering(watering);
        }

        return growingTips;
    }


    private VisualParameters getVisualParameters(Node visualParametersNode) {
        VisualParameters visualParameters = new VisualParameters();
        Element element = (Element) visualParametersNode;
        Node tempNode;

        tempNode = element.getElementsByTagName("stemColour").item(0);
        visualParameters.setStemColour(tempNode.getTextContent());

        tempNode = element.getElementsByTagName("leafColour").item(0);
        visualParameters.setLeafColour(tempNode.getTextContent());

        NodeList aveLenNodeList = element.getElementsByTagName("aveLenFlower");
        //Node aveLenNodeList1 = (Node) element.getElementsByTagName("aveLenFlower");
        AveLenFlower aveLenFlower = new AveLenFlower();
        for(int i = 0; i < aveLenNodeList.getLength(); i++){
            Element element1 = (Element) aveLenNodeList.item(i);
            aveLenFlower.setMeasure(element1.getAttribute("measure"));
            aveLenFlower.setContent(Integer.parseInt(element1.getTextContent()));
        }
        visualParameters.setAveLenFlower(aveLenFlower);

        return visualParameters;
    }

    public static void writeXML(Flowers flowers, String outputXMLFileName) throws IOException, TransformerException {
        File outputFile = new File(outputXMLFileName);
        outputFile.createNewFile();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document document = null;
        try {
            document = dbf.newDocumentBuilder().newDocument();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        Element flowersElement = document.createElement("flowers");
        document.appendChild(flowersElement);

        for(int i = 0; i < flowers.getFlowerList().size(); i++){
            writeFlower(document, flowers.getFlowerList().get(i), flowersElement);
        }

        Transformer transformer = TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
        Result output = new StreamResult(new File(outputXMLFileName));
        Source input = new DOMSource(document);

        transformer.transform(input, output);
    }

    private static void writeFlower(Document document, Flower flower, Element flowersElement){
        Element flowerElement = document.createElement("flower");
        flowersElement.appendChild(flowerElement);

        Element nameElement = document.createElement("name");
        nameElement.setTextContent(flower.getName());
        flowerElement.appendChild(nameElement);

        Element soilElement = document.createElement("soil");
        soilElement.setTextContent(flower.getSoil());
        flowerElement.appendChild(soilElement);

        Element originElement = document.createElement("origin");
        originElement.setTextContent(flower.getOrigin());
        flowerElement.appendChild(originElement);

        writeVisualParameters(document, flower, flowerElement);
        writeGrowingTips(document, flower, flowerElement);

        Element multiplyingElement = document.createElement("multiplying");
        multiplyingElement.setTextContent(flower.getMultiplying());
        flowerElement.appendChild(multiplyingElement);
    }

    private static void writeGrowingTips(Document document, Flower flower, Element flowerElement){
        GrowingTips gt = flower.getGrowingTips();
        Element gtElement = document.createElement("growingTips");

        Element tElement = document.createElement("tempreture");
        tElement.setTextContent(String.valueOf(gt.getTemperature().getContent()));
        tElement.setAttribute("measure", gt.getTemperature().getMeasure());
        gtElement.appendChild(tElement);

        Element lElement = document.createElement("lighting");
        lElement.setAttribute("lightRequiring", gt.getLighting().getLightRequiring());
        gtElement.appendChild(lElement);

        Element wElement = document.createElement("watering");
        wElement.setTextContent(String.valueOf(gt.getWatering().getContent()));
        wElement.setAttribute("measure", gt.getWatering().getMeasure());
        gtElement.appendChild(wElement);

        flowerElement.appendChild(gtElement);
    }

    private static void writeVisualParameters(Document document, Flower flower, Element flowerElement){
        VisualParameters vp = flower.getVisualParameters();
        Element vpElement = document.createElement("visualParameters");

        Element scElement = document.createElement("stemColour");
        scElement.setTextContent(vp.getStemColour());
        vpElement.appendChild(scElement);

        Element lcElement = document.createElement("leafColour");
        lcElement.setTextContent(vp.getLeafColour());
        vpElement.appendChild(lcElement);

        Element alfElement = document.createElement("aveLenFlower");
        alfElement.setTextContent(String.valueOf(vp.getAveLenFlower().getContent()));
        alfElement.setAttribute("measure", vp.getAveLenFlower().getMeasure());
        vpElement.appendChild(alfElement);

        flowerElement.appendChild(vpElement);
    }

    public static void main(String[] args) throws IOException, ParserConfigurationException, SAXException {
        /*DOMController domController = new DOMController("input.xml");

        System.out.println(domController.getDocument());
        //Node node = domController.getDocument().getFirstChild();
        //System.out.println(node);
        NodeList flowersNodeList = domController.getDocument().getElementsByTagName("flower");

        System.out.println(flowersNodeList.item(0));
        System.out.println(flowersNodeList.item(1));

        //System.out.println(node);

        Node node = domController.getDocument().getFirstChild();
        Node node123 = domController.getDocument().getLastChild();

        System.out.println(domController.getFlower(node).toString());

        System.out.println(domController.getFlower(flowersNodeList.item(0).));
        System.out.println(domController.getFlower(flowersNodeList.item(0)).toString());*/
        DOMController domController = new DOMController("input.xml");
        Flowers flowers = domController.parse();
        System.out.println(flowers.getFlowerList().get(0));
        System.out.println(flowers.getFlowerList().get(1));

        try {
            DOMController.writeXML(flowers, "output.dom.xml");
        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
