import java.io.*;
import java.util.Scanner;

public class Menu {

    private String file = "/Users/joacoiannuzzi/Documents/Austral/Prog/Algoritmos y estructuras de datos/u6/TP-RedBlackTree/src/BinaryTreeFile";

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.startMenu();
    }

    public void startMenu() {
        RedBlackTree<Car> tree = getFromDisk(file);
        if (tree == null)
            tree = new RedBlackTree<>();

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Options: ");
            System.out.println("0 -> Add a Car");
            System.out.println("1 -> Remove a Car");
            System.out.println("2 -> Modify a Car");
            System.out.println("3 -> Show info of car");
            System.out.println("4 -> Quantity of elements");
            System.out.println("5 -> Quantity of elements with condition");
            System.out.println("6 -> List of elements");
            System.out.println("7 -> List of elements with condition");
            System.out.println("8 -> Compact and save");
            System.out.println("9 -> Exit");
            int option = scanner.nextInt();
            switch (option) {
                case 0:
                    System.out.println("Enter a: key, car patent, year, model");
                    insert(tree, new Car(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.next()));
                    break;
                case 1:
                    System.out.println("Enter a key to remove: ");
                    delete(tree, new Car(scanner.nextInt()));
                    break;
                case 2:
                    System.out.println("Enter a key to modify: ");
                    Car car = new Car(scanner.nextInt());

                    System.out.println("Enter an option: ");
                    int o = 0;
                    while (o != 3) {
                        System.out.println("0 -> Change car patent");
                        System.out.println("1 -> Change year");
                        System.out.println("2 -> Change model");
                        System.out.println("3 -> Go back");
                        o = scanner.nextInt();
                        switch (o) {
                            case 0:
                                System.out.println("Enter a car patent: ");
                                changeCarPatent(tree, car, scanner.nextInt());
                                break;
                            case 1:
                                System.out.println("Enter a year: ");
                                changeYear(tree, car, scanner.nextInt());
                                break;
                            case 2:
                                System.out.println("Enter a model: ");
                                changeModel(tree, car, scanner.nextLine());
                                break;
                            case 3:
                                o = 3;
                        }
                    }
                    break;
                case 3:
                    System.out.println("Enter key: ");
                    printCar(tree.search(new Car(scanner.nextInt())));
                    break;
                case 4:
                    System.out.println("Quantity: " + amountOfElements(tree));
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    saveOnDisk(tree, file);
                    System.exit(0);
                    break;
            }
        }
    }

    public void insert(RedBlackTree<Car> tree, Car car) {
        tree.insert(car);
    }

    public void delete(RedBlackTree<Car> tree, Car car) {
        tree.deleteNode(car);
    }

    public void changeCarPatent(RedBlackTree<Car> tree, Car car, int carPatent) {
        tree.search(car).setCarPatent(carPatent);
    }

    public void changeYear(RedBlackTree<Car> tree, Car car, int year) {
        tree.search(car).setYear(year);
    }

    public void changeModel(RedBlackTree<Car> tree, Car car, String model) {
        tree.search(car).setModel(model);
    }

    public void printCar(Car car) {
        System.out.println("\tCar patent: " + car.getCarPatent());
        System.out.println("\tYear: " + car.getYear());
        System.out.println("\tModel: " + car.getModel());
    }

    //salva en disco al arbol binario
    public void saveOnDisk(RedBlackTree<Car> a, String path) {
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    new FileOutputStream(new File(path)));
            outputStream.writeObject(a);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //recupera del disco el arbol binario
    public RedBlackTree<Car> getFromDisk(String path) {
        RedBlackTree<Car> binaryTree = null;
        try {
            ObjectInputStream inputStream = new ObjectInputStream(
                    new FileInputStream(new File(path)));
            binaryTree = (RedBlackTree<Car>) inputStream.readObject();
            inputStream.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return binaryTree;
    }

    public int amountOfElements(RedBlackTree<Car> tree) {
        if (tree.isEmpty()) {
            return 0;
        }
        return 1 + amountOfElements(tree.getLeft()) + amountOfElements(tree.getRight());
    }
}
