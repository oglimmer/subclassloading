package de.oglimmer;

import de.oglimmer.api.EntryPoint;
import de.oglimmer.api.Foo;
import de.oglimmer.api.Bar;
import de.oglimmer.api.ParamInterface;

public class HiddenImplementation implements EntryPoint {

    @Override
    public String returnAStringForGivenInterfaceParameter(Foo foo) {
        return foo.returnAString();
    }

    @Override
    public Bar returnAnotherInterface(ParamInterface param) {
        return new BarImpl(param);
    }

    @Override
    public String returnASimpleString(String param) {
        return "a simple string in return for " + param;
    }
}
