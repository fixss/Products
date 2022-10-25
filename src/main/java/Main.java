import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import ru.netology.Basket;
import ru.netology.ClientLog;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Bread", "Cornflakes", "Milk", "Eggs", "Meat"};
        int[] prices = {50, 200, 100, 120, 500};
        Basket store = new Basket(prices, products);
        ClientLog clientLog = new ClientLog();
        String txtFileJournal = "D:\\Repository\\Products/log.csv";
        File journal = new File("log.csv");
        store.setArrayBasket(products.length);
        String textFileBasket = "D:\\Repository\\Products/basket.txt";
        File basketText = new File("basket.txt");
        String jsonFileBasket = "D:\\Repository\\Products/basket.json";
        File basketJson = new File("basket.json");


        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        try {
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new File("shop.xml"));
            doc.getDocumentElement().normalize();
            NodeList nList = doc.getElementsByTagName("config");
            System.out.println("============================");

            boolean loadFileEnabled = false;
            String loadFileName = "";
            String loadFileFormat = "";
            ;
            boolean saveFileEnabled = false;
            String saveFileName = "";
            String saveFileFormat = "";
            boolean logFileEnabled = false;
            String logFileName = "";
            ;

            for (int temp = 0; temp < nList.getLength(); temp++) {
                Node node = nList.item(temp);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) node;
                    loadFileEnabled = Boolean.parseBoolean(eElement.getElementsByTagName("enabled").item(0).getTextContent());
                    loadFileName = eElement.getElementsByTagName("fileName").item(0).getTextContent();
                    loadFileFormat = eElement.getElementsByTagName("format").item(0).getTextContent();
                    saveFileEnabled = Boolean.parseBoolean(eElement.getElementsByTagName("enabled").item(1).getTextContent());
                    saveFileName = eElement.getElementsByTagName("fileName").item(1).getTextContent();
                    saveFileFormat = eElement.getElementsByTagName("format").item(1).getTextContent();
                    logFileEnabled = Boolean.parseBoolean(eElement.getElementsByTagName("enabled").item(2).getTextContent());
                    logFileName = eElement.getElementsByTagName("fileName").item(2).getTextContent();

                }
            }
            if (loadFileEnabled) {
                System.out.println("Scanning for previous Basket - Enabled");
                if (loadFileFormat.equals("json")) {
                    System.out.println("Trying to find Basket.json");
                    if (loadFileName.equals("basket.json")) {
                        if ((basketJson).exists()) {
                            System.out.println("You have previously created Json Basket");
                            store = store.loadFromJsonFile(basketJson);
                        } else {
                            try {
                                if (basketJson.createNewFile()) {
                                    System.out.println("New Json Basket created");
                                }
                            } catch (IOException e) {
                                System.out.println("Folder " + jsonFileBasket + " is missing.");
                                throw new RuntimeException(e);
                            }
                        }
                    } else System.out.println("Unknown Basket name");
                } else {
                    if (loadFileName.equals("basket.txt")) {
                        if ((basketText).exists()) {
                            System.out.println("You have previously created txt Basket");
                            store.loadFromTxtFile(basketText);
                        } else {
                            try {
                                if (basketText.createNewFile()) {
                                    System.out.println("New txt Basket created");
                                }
                            } catch (IOException e) {
                                System.out.println("Folder " + textFileBasket + " is missing.");
                                throw new RuntimeException(e);
                            }
                        }
                    } else System.out.println("Unknown Basket name");
                }
            } else {
                System.out.println("Scanning for previous Basket - Disabled");
            }

            System.out.println("Products available for purchase: ");
            for (int i = 0; i < products.length; i++) {
                System.out.println((i + 1) + ". " + products[i] + " " + prices[i] + " rub/item");
            }
            while (true) {
                int productNumber = 0;
                int productCount = 0;
                System.out.println("Enter product number and amount or press `end` for finishing");
                String inputString = scanner.nextLine();
                if (inputString.equals("end")) {
//                store.addToCart(productNumber, productCount);
                    if (logFileEnabled) {
                        if (logFileName.equals("client.csv")) {
                            clientLog.exportAsCSV(journal);
                            System.out.println("Products available for purchase: ");
                        } else System.out.println("Unknown Log file name");
                    }


                    break;
                }
                String[] inputProduct = inputString.split(" ");
                productNumber = Integer.parseInt(inputProduct[0]) - 1;
                productCount = Integer.parseInt(inputProduct[1]);
                store.addToCart(productNumber, productCount);
                clientLog.log(productNumber + 1, productCount); //было внизу
                if (saveFileEnabled) {
                    if (saveFileFormat.equals("json")) {
                        if (saveFileName.equals("basket.json")) {
                            store.saveJson(new File(jsonFileBasket));
                        } else System.out.println("Unknown Basket name");
                    } else store.saveTxt(new File(textFileBasket));
                }
            }

            store.printCart();

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}