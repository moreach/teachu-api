package ch.teachu.teachuapi.grade;

import ch.teachu.teachuapi.exam.ObjectIdHolder;

import java.util.UUID;

public class AverageObjectIdHolder<T> extends ObjectIdHolder<T> {

    private double weight = 0.0;
    private double sum = 0.0;

    public AverageObjectIdHolder() {
        super();
    }

    protected AverageObjectIdHolder(UUID id, T object, double weight, double sum, boolean forceNew) {
        super(id, object, forceNew);
        this.weight = weight;
        this.sum = sum;
    }

    public double getAverage() {
        return sum / weight;
    }

    public void newObject(T object, UUID id) {
        super.newObject(object, id);
        weight = 0.0;
        sum = 0.0;
    }

    protected void add(double mark, double weight) {
        this.sum += mark * weight;
        this.weight += weight;
    }
}
