package ch.teachu.teachuapi.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.function.Supplier;

@NoArgsConstructor
@AllArgsConstructor
public class Holder<T> implements Supplier<T> {
    protected T t = null;

    public T get() {
        return t;
    }

    public void set(T t) {
        this.t = t;
    }

    public void set(Supplier<T> s) {
        t = s.get();
    }
}
