package week_01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 第一周作业第一题：
 * 自定义一个 Classloader，加载一个 Hello.xlass 文件，执行 hello 方法，
 * 此文件内容是一个 Hello.class 文件所有字节（x=255-x）处理后的文件。文件群里提供。
 * -- 文件移动到data目录下
 */

public class HelloClassLoader extends ClassLoader {

    public static void main(String[] args) {
        try {
            Class<?> helloClazz = new HelloClassLoader().findClass("Hello");
            Object obj = helloClazz.newInstance();
            Method helloMethod = helloClazz.getMethod("hello");
            helloMethod.invoke(obj);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Class<?> findClass(String name) {
        String file = "01jvm/data/Hello.xlass";
        File f = new File(file);
        int length = (int) f.length();
        byte[] data = new byte[length];
        try {
            new FileInputStream(f).read(data);
            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (255 - data[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return defineClass(name,data,0,data.length);
    }
}
