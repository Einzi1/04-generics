package ohm.softa.a04;

import java.util.function.Function;

public interface SimpleList<T> extends Iterable<T> {
    /**
     * Add a given object to the back of the list.
     */
    void add(T t);

    /**
     * @return current size of the list
     */
    int size();

    default void addDefault(Class<T> clazz) {
        try {
            this.add(clazz.newInstance());
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    default SimpleList<T> filter(SimpleFilter<T> filter) {
        SimpleList<T> result;
        try {
            result = getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            result = new SimpleListImpl<>();
        }
        for (T o : this) {
            if (filter.include(o)) {
                result.add(o);
            }
        }
        return result;
    }

    default <R> SimpleList<R> map(Function<T, R> transform) {
        SimpleList<R> result;
        try {
            result = (SimpleList<R>) this.getClass().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            result = new SimpleListImpl<>();
        }
        for (T t : this) {
            result.add(transform.apply(t));
        }
        return result;
    }
}
