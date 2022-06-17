package ch.teachu.teachuapi.util;

import java.util.function.Supplier;

public class DoubleHolder extends Holder<Double> {

    public DoubleHolder() {
        t = 0.0;
    }

    public DoubleHolder(Double t) {
        super(t);
    }

    public double plus(double toAdd) {
        return t + toAdd;
    }

    public double plus(Supplier<Double> toAdd) {
        return t + toAdd.get();
    }

    public double minus(double toRemove) {
        return t - toRemove;
    }

    public double minus(Supplier<Double> toRemove) {
        return t - toRemove.get();
    }

    public double plusSet(double toAdd) {
        return t += toAdd;
    }

    public double plusSet(Supplier<Double> toAdd) {
        return t += toAdd.get();
    }

    public double minusSet(double toRemove) {
        return t -= toRemove;
    }

    public double minusSet(Supplier<Double> toRemove) {
        return t -= toRemove.get();
    }

    public double multiply(double toMultiply) {
        return t * toMultiply;
    }

    public double multiply(Supplier<Double> toMultiply) {
        return t * toMultiply.get();
    }

    public double multiplySet(double toMultiply) {
        return t *= toMultiply;
    }

    public double multiplySet(Supplier<Double> toMultiply) {
        return t *= toMultiply.get();
    }

    public double divide(double toDivide) {
        return t / toDivide;
    }

    public double divide(Supplier<Double> toDivide) {
        return t / toDivide.get();
    }

    public double divideSet(double toDivide) {
        return t /= toDivide;
    }

    public double divideSet(Supplier<Double> toDivide) {
        return t /= toDivide.get();
    }
}
