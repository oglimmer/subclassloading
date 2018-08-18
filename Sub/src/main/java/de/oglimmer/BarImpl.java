package de.oglimmer;

import de.oglimmer.api.Bar;
import de.oglimmer.api.ParamInterface;

public class BarImpl implements Bar {

    ParamInterface param;

    BarImpl(ParamInterface param) {
        this.param = param;
    }

    @Override
    public int getNo() {
        return Integer.parseInt(param.getName());
    }
}
