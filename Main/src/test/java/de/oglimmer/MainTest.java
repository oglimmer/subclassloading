package de.oglimmer;

import de.oglimmer.api.EntryPoint;
import de.oglimmer.api.Foo;
import de.oglimmer.api.ParamInterface;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class MainTest {

    private SubClassLoader subClassLoader;
    private EntryPoint ep;

    @Before
    public void warm() throws Exception {
        File file = new File("../Sub/target/Sub-0.1-SNAPSHOT.jar");

        subClassLoader = new SubClassLoader();
        ep = subClassLoader.loadJar(file);
    }

    @After
    public void cool() throws IOException {
        subClassLoader.shutdown();
        subClassLoader = null;
        ep = null;
    }

    @Test
    public void test1() {
        String result = ep.returnASimpleString("a param");
        Assert.assertEquals("a simple string in return for a param", result);
    }

    @Test
    public void test2() {
        String result = ep.returnAStringForGivenInterfaceParameter(new Foo() {
            @Override
            public String returnAString() {
                return "in foo";
            }
        });
        Assert.assertEquals("in foo", result);
    }

    @Test
    public void test3() {
        ParamInterface param = new ParamInterface() {

            String s;

            @Override
            public String getName() {
                return s;
            }

            @Override
            public void setName(String s) {
                this.s = s;
            }
        };
        param.setName("42");

        int result = ep.returnAnotherInterface(param).getNo();
        Assert.assertEquals(42, result);
    }
}