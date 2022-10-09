import ru.netology.Products;

import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Products[] products = new Products[3];
        products[0] = new Products(75.99, "Milk");
        products[1] = new Products(50.00, "Bread");
        products[2] = new Products(152.35, "Cornflakes");

        double sumProducts = 0;
        int[] bucket = new int[3];
        System.out.println("List of possible items to buy:");
        for (int i = 0; i < products.length; i++) {
            System.out.println(i + 1 + ". " + products[i].getName() + " " + products[i].getPrice() + " rub/item");
        }
        while (true) {
            System.out.println("Select item and quantity or enter `end` ");
            String input = scanner.nextLine();
            if (input.equals("end")) {
                System.out.println("Your bucket: " + '\n');
                for (int i = 0; i < bucket.length; i++) {
                    if (bucket[i] != 0) {
                        System.out.println(products[i].getName() + " " + bucket[i] + " items " + products[i].getPrice() + " rub/item " + bucket[i] * products[i].getPrice() + " rubles in total");
                    }

                }
                System.out.println("Total " + sumProducts + " Rubles");
                break;

            }
            String[] choice = input.split(" ");
            int chosenProduct = Integer.parseInt(choice[0]) - 1;
            int amount = Integer.parseInt(choice[1]);
            bucket[chosenProduct] += amount;
            double currentPrice = products[chosenProduct].getPrice();
            sumProducts += currentPrice * bucket[chosenProduct];
        }
    }

}