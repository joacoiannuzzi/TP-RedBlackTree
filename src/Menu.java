import java.io.*;
import java.util.Scanner;

public class Menu implements Serializable{

    private String file = "src/binaryTreeFile";
    Scanner scanner;

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.startMenu();
    }

    public void startMenu() {
        RedBlackTree<Car> tree = getFromDisk(file);
        if (tree == null)
            tree = new RedBlackTree<>();

        scanner = new Scanner(System.in);

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
                    System.out.println("Enter a: key, car plate, year, model");
                    insert(tree, new Car(scanner.nextInt(), scanner.nextInt(), scanner.nextInt(), scanner.next()));
                    break;
                case 1:
                    System.out.println("Enter a key to remove: ");
                    logicDelete(tree, new Car(scanner.nextInt()));
                    break;
                case 2:
                    System.out.println("Enter a key to modify: ");
                    Car car = new Car(scanner.nextInt());

                    System.out.println("Enter an option: ");
                    int o = 0;
                    while (o != 3) {
                        System.out.println("0 -> Change car plate");
                        System.out.println("1 -> Change year");
                        System.out.println("2 -> Change model");
                        System.out.println("3 -> Go back");
                        o = scanner.nextInt();
                        switch (o) {
                            case 0:
                                System.out.println("Enter a car plate: ");
                                changeCarPatent(tree, car, scanner.nextInt());
                                break;
                            case 1:
                                System.out.println("Enter a year: ");
                                changeYear(tree, car, scanner.nextInt());
                                break;
                            case 2:
                                System.out.println("Enter a model: ");
                                changeModel(tree, car, scanner.next());
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
                    System.out.println("Quantity: " +
                            amountOfElementsWithCondition(tree, conditionCreator()));
                    break;
                case 6:
                    printAllCars(tree);
                    break;
                case 7:
                    printAllCarsWithCondition(tree, conditionCreator());
                    break;
                case 8:
                    tree.compact();
                    saveOnDisk(tree, file);
                    break;
                case 9:
                    saveOnDisk(tree, file);
                    System.exit(0);
                    break;
            }
        }
    }

    public Condition conditionCreator() {
        System.out.println("Select a condition: ");
        System.out.println("0 -> Car Year");
        System.out.println("1 -> Car Model");
        System.out.println("2 -> Car plate");
        switch (scanner.nextInt()) {
            case 0:
                System.out.println("Enter year");
                int h = scanner.nextInt();
                return car -> car.getYear() > h;
            case 1:
                System.out.println("Enter model");
                String j = scanner.next();
                return car -> car.getModel().equals(j);
            case 2:
                System.out.println("Enter plate");
                int p = scanner.nextInt();
                return car -> car.getCarPlate() == p;
            default:
                return car -> true;
        }
    }

    private void printAllCarsWithCondition(RedBlackTree<Car> tree, Condition condition) {
        if(!tree.isEmpty()) {
            if(condition.evaluate(tree.getRoot()) && !tree.nodeIsDead()) {
                printCar(tree.getRoot());
            }
            printAllCarsWithCondition(tree.getLeft(), condition);
            printAllCarsWithCondition(tree.getRight(), condition);
        }
    }

    private void printAllCars(RedBlackTree<Car> tree) {
        if(!tree.isEmpty()) {
            if (!tree.nodeIsDead())
                 printCar(tree.getRoot());
            printAllCars(tree.getLeft());
            printAllCars(tree.getRight());
        }
    }

    public void insert(RedBlackTree<Car> tree, Car car) {
        tree.insert(car);
    }

    public void logicDelete(RedBlackTree<Car> tree, Car car) {
        tree.killNode(car);
    }

    public void changeCarPatent(RedBlackTree<Car> tree, Car car, int carPatent) {
        tree.search(car).setCarPlate(carPatent);
    }

    public void changeYear(RedBlackTree<Car> tree, Car car, int year) {
        tree.search(car).setYear(year);
    }

    public void changeModel(RedBlackTree<Car> tree, Car car, String model) {
        tree.search(car).setModel(model);
    }

    public void printCar(Car car) {
        System.out.println("Key: " + car.getKey());
        System.out.println("\tCar patent: " + car.getCarPlate());
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
        if(!tree.nodeIsDead())
            return 1 + amountOfElements(tree.getLeft()) + amountOfElements(tree.getRight());
        return amountOfElements(tree.getLeft()) + amountOfElements(tree.getRight());

    }

    public int amountOfElementsWithCondition(RedBlackTree<Car> tree, Condition condition) {
        if (tree.isEmpty()) {
            return 0;
        }
        if(condition.evaluate(tree.getRoot()) && !tree.nodeIsDead()){
            return 1 + amountOfElementsWithCondition(tree.getLeft(),condition) + amountOfElementsWithCondition(tree.getRight(), condition);
        }
        return amountOfElementsWithCondition(tree.getLeft(),condition) + amountOfElementsWithCondition(tree.getRight(), condition);
    }
}
