package de.oglimmer;

import de.oglimmer.api.EntryPoint;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

public class SubClassLoader {

    private URLClassLoader child;

    private String getEntryPointClassName(File file) throws IOException {
        try (JarInputStream jarStream = new JarInputStream(new FileInputStream(file))) {
            Manifest mf = jarStream.getManifest();
            return mf.getMainAttributes().getValue("entryPoint");
        }
    }

    public EntryPoint loadJar(File file) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException, IOException {
        child = new URLClassLoader(new URL[]{file.toURI().toURL()}, getClass().getClassLoader());
        Class<?> classEntryPoint = Class.forName(getEntryPointClassName(file), true, child);
        Object entryPointRawObj = classEntryPoint.getConstructor().newInstance();
        return (EntryPoint) Proxy.newProxyInstance(EntryPoint.class.getClassLoader(), new Class[]{EntryPoint.class}, (proxy, method, args) -> {
            Class<?>[] mappedArgTypes = new Class<?>[args == null ? 0 : args.length];
            Object[] mappedArgs = new Object[mappedArgTypes.length];
            Class<?>[] sourceTypes = method.getParameterTypes();
            for (int i = 0; args != null && i < mappedArgTypes.length; i++) {
                mappedArgTypes[i] = sourceTypes[i];
                mappedArgs[i] = args[i];
            }
            Method realMethod = entryPointRawObj.getClass().getMethod(method.getName(), mappedArgTypes);
            return realMethod.invoke(entryPointRawObj, mappedArgs);
        });
    }

    public void shutdown() throws IOException {
        child.close();
    }
}