package de.oglimmer.api;

public interface EntryPoint {

  String returnAStringForGivenInterfaceParameter(Foo foo);

  Bar returnAnotherInterface(ParamInterface param);

  String returnASimpleString(String param);

}
