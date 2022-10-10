package ru.netology;

import java.io.*;

public class Basket implements Serializable {
    protected int[] prices;
    protected String[] products;
    public int[] arrayBasket;
    private int sumProducts = 0;

    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
        this.arrayBasket = new int[products.length];
    }

    public void addToCart(int productNum, int amount) {
        sumProducts += prices[productNum] * amount;
        arrayBasket[productNum] += amount;
    }

    public void printCart() {
        System.out.println("Your Basket contains:" + "\n");
        for (int i = 0; i < products.length; i++) {
            if (arrayBasket[i] > 0) {
                int sumProduct = arrayBasket[i] * prices[i];
                System.out.println(products[i] + " " + arrayBasket[i] + " item " + prices[i] + " rub/item. " + " Total: " + sumProduct );
            }
        }
        System.out.println("\n" + "Total: " + sumProducts + " rub" + "\n");
    }

    public void saveBin(File file) throws IOException {
        try (ObjectOutputStream saveBinFile = new ObjectOutputStream(new FileOutputStream(file))) {
            saveBinFile.writeObject(this);
            System.out.println("File: D:\\Repository\\Products/basket.bin created");
        }
    }

    public static Basket loadFromBinFile(File file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream loadBinFile = new ObjectInputStream(new FileInputStream(file))) {
            Basket cart = (Basket) loadBinFile.readObject();
            loadBinFile.close();
            System.out.println("File: D:\\Repository\\Products/basket.bin loaded");
            return cart;
            }
        }
    }
