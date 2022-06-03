package ch.teachu.teachuapi.grade;

import java.util.UUID;

public class AverageObjectHolder<T> {

    private UUID id;
    private T object;
    private double weight = 0.0;
    private double sum = 0.0;
    private boolean forceNew = false;

    public AverageObjectHolder() {
    }

    protected AverageObjectHolder(UUID id, T object, double weight, double sum, boolean forceNew) {
        this.id = id;
        this.object = object;
        this.weight = weight;
        this.sum = sum;
        this.forceNew = forceNew;
    }

    public void forceNew() {
        forceNew = true;
    }

    public double getAverage() {
        return sum / weight;
    }

    public T get() {
        return object;
    }

    public void newObject(T object, UUID id) {
        this.object = object;
        this.id = id;
        weight = 0.0;
        sum = 0.0;
    }

    public boolean isNewId(UUID id) {
        boolean newId = forceNew || !id.equals(this.id);
        forceNew = false;
        return newId;
    }

    protected void add(double mark, double weight) {
        this.sum += mark * weight;
        this.weight += weight;
    }

    public AverageObjectHolder<T> copy() {
        return new AverageObjectHolder<>(id, object, weight, sum, forceNew);
    }
}
