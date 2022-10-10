import ru.netology.Basket;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Bread", "Cornflakes", "Milk", "Eggs", "Meat"};
        int[] prices = {50, 200, 100, 120, 500};
        Basket store = new Basket(prices, products);
        Basket.setArrayBasket(products.length);
        String textFileBasket = "D:\\Repository\\Products/basket.txt";
        File basketText = new File("basket.txt");
        if ((basketText).exists()) {
            System.out.println("You have previously created basket");
            store.loadFromTxtFile(basketText);
        } else {
            try {
                if (basketText.createNewFile()) {
                    System.out.println("New Basket crated");
                }
            } catch (IOException e) {
                System.out.println("Folder " + textFileBasket + " is missing.");
                throw new RuntimeException(e);
            }
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
                store.addToCart(productNumber, productCount);
                break;
            }
            String[] inputProduct = inputString.split(" ");
            productNumber = Integer.parseInt(inputProduct[0]) - 1;
            productCount = Integer.parseInt(inputProduct[1]);
            store.addToCart(productNumber, productCount);
            store.saveTxt(new File(textFileBasket));
        }
        store.printCart();

    }

}