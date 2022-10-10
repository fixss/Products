package ru.netology;

import java.io.*;

public class Basket {
    protected final int[] prices;
    protected final String[] products;
    private static int[] arrayBasket;
    private int sumProducts = 0;

    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
    }

    public static void setArrayBasket(int productsLength) {
        arrayBasket = new int[productsLength];
    }

    public void addToCart(int productNum, int amount) {
        sumProducts += prices[productNum] * amount;
        arrayBasket[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Your Basket:");
        for (int i = 0; i < products.length; i++) {
            if (arrayBasket[i] > 0) {
                int sumProduct = arrayBasket[i] * prices[i];
                System.out.println(products[i] + " " + arrayBasket[i] + " item " + prices[i] + " rub/item. " + " Total: " + sumProduct );
            }
        }
        System.out.println("Total: " + sumProducts + " rub");
    }

    public void saveTxt(File textFile) throws IOException {

        try (PrintWriter out = new PrintWriter(new FileWriter(textFile))) {

            for (int e : arrayBasket)
                out.print(e + " ");
        } catch (IOException e) {
            System.out.println("File: D:\\Repository\\Products/basket.txt is missing or wrong path");
            throw new RuntimeException(e);
        }
    }

    public static void loadFromTextFile(File textFile) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(textFile))) {
            String[] itemSplit = in.readLine().split(" ");
            for (int i = 0; i < itemSplit.length; i++) {
                arrayBasket[i] = Integer.parseInt(itemSplit[i]);
            }
        }
    }

    protected void saveBin(File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(this);
            System.out.println("Файл Basket.bin created.");
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream loadBinFile = new ObjectInputStream(new FileInputStream(file))) {
            Basket baskets = (Basket) loadBinFile.readObject();
            loadBinFile.close();
            System.out.println("Файл Basket.bin read by using Deserialization method");
            return baskets;
        }
    }
}