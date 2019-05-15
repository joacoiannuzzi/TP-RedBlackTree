public class Criteria implements Condition{

    @Override
    public boolean evaluate(Car car) {
        return car.getYear()>2000;
    }
}
