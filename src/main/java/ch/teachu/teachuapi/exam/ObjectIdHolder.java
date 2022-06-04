package ch.teachu.teachuapi.exam;

import ch.teachu.teachuapi.util.Holder;

import java.util.UUID;

public class ObjectIdHolder<T> extends Holder<T> {
    private UUID id;
    private boolean forceNew = false;

    public ObjectIdHolder() {
    }

    protected ObjectIdHolder(UUID id, T object, boolean forceNew) {
        super(object);
        this.id = id;
        this.forceNew = forceNew;
    }

    public void newObject(T object, UUID id) {
        this.t = object;
        this.id = id;
    }

    public void forceNew() {
        forceNew = true;
    }

    public boolean isNewId(UUID id) {
        boolean newId = forceNew || !id.equals(this.id);
        forceNew = false;
        return newId;
    }
}
