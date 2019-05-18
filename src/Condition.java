public interface Condition<T>{

    boolean evaluate(T element);
}