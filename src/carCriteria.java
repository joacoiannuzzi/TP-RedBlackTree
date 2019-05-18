import java.util.Scanner;

public class carCriteria implements Condition {
    private String condition;


    public carCriteria(){
        Scanner scanner = new Scanner(System.in);
        condition = scanner.next();
    }

    @Override
    public boolean evaluate(Car car) {
        if(condition.equals("year")){
            return car.getYear() > 2000;
        } else if(condition.equals("model")){
            return car.getModel().compareTo("ford") == 0;
        } else {
            return car.getCarPlate() > 300;
        }
    }
}
