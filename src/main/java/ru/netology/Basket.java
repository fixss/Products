package ru.netology;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;


public class Basket {
    protected int[] prices;
    protected String[] products;
    private static int[] arrayBasket;
    private int sumProducts = 0;

    public Basket(int[] prices, String[] products) {
        this.prices = prices;
        this.products = products;
    }

    public void setArrayBasket(int productsLength) {
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
                System.out.println(products[i] + " " + arrayBasket[i] + " item " + prices[i] + " rub/item. " + " Total: " + sumProduct);
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

    public static void loadFromTxtFile(File textFile) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(textFile))) {
            String[] itemSplit = in.readLine().split(" ");
            for (int i = 0; i < itemSplit.length; i++) {
                arrayBasket[i] = Integer.parseInt(itemSplit[i]);
            }
        }catch (IOException e) {
            System.out.println("File: D:\\Repository\\Products/basket.txt is damaged or wrong path");
            throw new RuntimeException(e);
        }
    }

    public void saveJson(File jsonFile) throws IOException {
        try (PrintWriter out = new PrintWriter(new FileWriter(jsonFile))) {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.excludeFieldsWithModifiers(java.lang.reflect.Modifier.TRANSIENT);
            Gson gson = gsonBuilder.create();
            String json = gson.toJson(this);
            out.println(json);
        }catch (IOException e) {
            System.out.println("File: D:\\Repository\\Products/basket.json is missing or wrong path");
            throw new RuntimeException(e);
        }
    }
    public static Basket loadFromJsonFile(File jsonFile) throws IOException {
        try (BufferedReader in = new BufferedReader(new FileReader(jsonFile))) {
            Gson gsonBuilder = new Gson();
            Basket basket = gsonBuilder.fromJson(in, Basket.class);
            return basket;
            }catch (IOException e) {
            System.out.println("File: D:\\Repository\\Products/basket.json is damaged or wrong path");
            throw new RuntimeException(e);
        }
        }
    }
