import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.nio.file.Files;
import java.nio.file.Paths;
 
public class MyClassLoader extends ClassLoader {

    protected Class<?> findClass(String name) throws ClassNotFoundException {
        Class clazz = null;
        String classFilename = name + ".xlass";
        File classFile = new File(classFilename);
        if (classFile.exists()) {
        	System.out.println("yes");
        	try {
				byte[] b = Files.readAllBytes(Paths.get(classFilename));
				byte[] newb = new byte[b.length];
				for(int i = 0; i< b.length; i++) {
					
					byte x = (byte) (255-b[i]);
                    newb[i] = x;					
				}
				FileOutputStream fos = new FileOutputStream("D:\\eclipse\\workspace\\workspace-learn\\jvm-learn\\Hello.class");
                fos.write(newb);
                clazz = this.defineClass(newb, 0, b.length);
                
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        	
        }
		
		if (clazz == null) { throw new ClassNotFoundException(name); }
		 
        return clazz;
    }
 
    public static void main(String[] args) throws Exception{
        MyClassLoader myClassLoader = new MyClassLoader();
        Class clazz = myClassLoader.findClass("Hello");
        Object  obj = clazz.newInstance();
        Method sayHello = clazz.getMethod("hello");
        sayHello.invoke(obj);
    }
}