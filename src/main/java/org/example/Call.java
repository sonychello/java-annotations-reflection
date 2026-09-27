package org.example;

import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Call {
    Object[] params;
    void invokeAnnotatedMethods() throws
            NoSuchMethodException,
            InstantiationException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException,
            InaccessibleObjectException
    {
        Class<MyClass> mClassObject = MyClass.class;
        MyClass instance = mClassObject.getDeclaredConstructor().newInstance();
        Method[] methods = mClassObject.getDeclaredMethods();
        for (Method method : methods) {
            Annotation annotation = method.getAnnotation(Sun.class);
            if (annotation != null) {
                method.setAccessible(true);
                Class<?>[] paramTypes = method.getParameterTypes();
                params = createParameters(paramTypes, instance);
                for (int i = 0; i < ((Sun) annotation).value(); i++) {
                   method.invoke(instance, params);
                }
            }
        }
    }
    private Object[] createParameters(Class<?>[] paramTypes, Object instance) throws
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        Object[] params = new Object[paramTypes.length];

        for (int i = 0; i < paramTypes.length; i++) {
            params[i] = createParameter(paramTypes[i], instance);
        }

        return params;
    }


     // Рекурсивно создает один параметр заданного типа

    private Object createParameter(Class<?> paramType, Object instance) throws
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        // Примитивные типы или их классы обёртки
        if (paramType == int.class || paramType == Integer.class) {
            return 42; // произвольное число
        } else if (paramType == double.class || paramType == Double.class) {
            return 3.14;
        } else if (paramType == boolean.class || paramType == Boolean.class) {
            return true;
        } else if (paramType == long.class || paramType == Long.class) {
            return 100L;
        } else if (paramType == float.class || paramType == Float.class) {
            return 2.71f;
        } else if (paramType == char.class || paramType == Character.class) {
            return 'A';
        } else if (paramType == byte.class || paramType == Byte.class) {
            return (byte) 127;
        } else if (paramType == short.class || paramType == Short.class) {
            return (short) 1000;
        }
        // String
        else if (paramType == String.class) {
            return "steak";
        }
        // Массивы
        else if (paramType.isArray()) {
            return createArray(paramType);
        }
        // Коллекции
        else if (List.class.isAssignableFrom(paramType)) {
            return Arrays.asList("something", "something2", "something2");
        } else if (Set.class.isAssignableFrom(paramType)) {
            return new HashSet<>(Arrays.asList("set_something21", "set_something22"));
        } else if (Map.class.isAssignableFrom(paramType)) {
            Map<Object, Object> map = new HashMap<>();
            map.put(1, "ой");
            map.put(2, "ай");
            return map;
        }
        // Если параметр - тот же класс, что и вызываемый объект
        else if (paramType == instance.getClass()) {
            return instance;
        }
        // Enum
        else if (paramType.isEnum()) {
            Object[] enumConstants = paramType.getEnumConstants();
            return enumConstants.length > 0 ? enumConstants[0] : null;
        }
        // Пользовательские классы - пытаемся создать экземпляр
        else {
            return createCustomObject(paramType);
        }
    }


     // Создает массив заданного типа

    private Object createArray(Class<?> arrayType) throws
            ArrayIndexOutOfBoundsException,
            IllegalArgumentException,
            InvocationTargetException,
            InstantiationException,
            IllegalAccessException {
        Class<?> componentType = arrayType.getComponentType();

        // Создаем массив из 3 элементов
        Object array = Array.newInstance(componentType, 3);

        // Заполняем массив значениями
        for (int i = 0; i < 3; i++) {
            Object element = createParameter(componentType, null);
            Array.set(array, i, element);
        }

        return array;
    }


     //Пытается создать экземпляр пользовательского класса

    private Object createCustomObject(Class<?> clazz) throws
            InaccessibleObjectException,
            InstantiationException,
            IllegalAccessException,
            IllegalArgumentException,
            InvocationTargetException {
        try {
            // Пробуем конструктор по умолчанию
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            System.out.println("Не удалось создать экземпляр " + clazz.getSimpleName() + ", используется null");
            return null;
        }
    }
}
