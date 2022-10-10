import ru.netology.Basket;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Scanner;

class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        String[] products = {"Bread", "Cornflakes", "Milk", "Eggs", "Meat"};
        int[] prices = {50, 200, 100, 120, 500};
        Basket cart = new Basket(prices, products);
        String binFileBasket = "basket.bin";
        File basketBin = new File("D:\\Repository\\Products/basket.bin");
        if (Files.exists(Path.of(String.valueOf(binFileBasket)))) {
            System.out.println("You have previously created basket");
            cart = Basket.loadFromBinFile(basketBin);
            cart.printCart();
        } else {
            try {
                if (basketBin.createNewFile()) {
                    System.out.println("New Basket crated");
                }
            } catch (IOException e) {
                System.out.println("Folder " + binFileBasket + " is missing.");
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
            System.out.println("Enter product number and amount or press `end` for finishing" + "\n");
            String inputString = scanner.nextLine();
            if (inputString.equals("end")) {
                cart.printCart();
                cart.saveBin(basketBin);
                System.out.println("File: D:\\Repository\\Products/basket.bin saved");
                break;
            }
            String[] inputProduct = inputString.split(" ");
            productNumber = Integer.parseInt(inputProduct[0]) - 1;
            productCount = Integer.parseInt(inputProduct[1]);
            cart.addToCart(productNumber, productCount);
            cart.printCart();
        }
    }
}