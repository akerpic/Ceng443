public class IronConstructor extends Constructor {

    public IronConstructor(int id, int ingotType, int capacity, int interval, HW2Logger hw2Logger) {
        super(id, ingotType, capacity, interval, hw2Logger);
        requiredIngot = 2;
    }

}