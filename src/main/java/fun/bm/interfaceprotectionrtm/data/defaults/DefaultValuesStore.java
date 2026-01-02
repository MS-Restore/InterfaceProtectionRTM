package fun.bm.interfaceprotectionrtm.data.defaults;

import java.util.HashSet;
import java.util.Set;

public class DefaultValuesStore {
    public final Set<DefaultValue> values = new HashSet<>();

    public void add(DefaultValue value) {
        values.add(value);
    }
}
