package com.cherry.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

@RestController
public class ExecutionController {

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        System.out.println("Hello called");
        return String.format("Hello %s!", name);
    }

    @GetMapping("/execute")
    public String Execute(@RequestParam String action) {

        try {
            // Path to the JAR file
            String jarFilePath = "C:\\dev\\java\\Cherry\\libs\\RestLib\\target\\RestLib-1.0-SNAPSHOT.jar";

            // Create a URL pointing to the JAR file
            URL jarUrl = new File(jarFilePath).toURI().toURL();

            // Create a URLClassLoader with the JAR file URL as its classpath
            URLClassLoader classLoader = new URLClassLoader(new URL[]{jarUrl});

            // Load the class dynamically
            String className = "Restlib.Actions"; // Replace with your class name
            Class<?> loadedClass = classLoader.loadClass(className);

            // Instantiate an object of the dynamically loaded class
            Object instance = loadedClass.getDeclaredConstructor().newInstance();

            // Call a method on the dynamically loaded class
            Method method = loadedClass.getMethod("Ping"); // Replace with your method name
            Object ret = method.invoke(instance);

            System.out.println("Method invoked " + ret.toString());
            System.out.println("Hello called");
            return String.format("Hello %s!", "world");
        } catch (Exception ex) {
            System.out.println("Exception" + ex.toString());

        }
        return "";
    }

    @GetMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }
}
