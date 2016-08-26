package me.mert1602.advancedapi.tool;

import java.io.File;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import lombok.Getter;
import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.setting.SettingType;

public final class Tool {

	private Tool() {}

	public static final Boolean[] EMPTY_BOOLEAN_LIST = new Boolean[0];
	public static final Byte[] EMPTY_BYTE_LIST = new Byte[0];
	public static final Double[] EMPTY_DOUBLE_LIST = new Double[0];
	public static final Float[] EMPTY_FLOAT_LIST = new Float[0];
	public static final Integer[] EMPTY_INTEGER_LIST = new Integer[0];
	public static final Long[] EMPTY_LONG_LIST = new Long[0];
	public static final Short[] EMPTY_SHORT_LIST = new Short[0];

	public static final String[] EMPTY_STRING_LIST = new String[0];
	public static final SettingType[] EMPTY_SETTINGTYPE_LIST = new SettingType[0];

	public static final byte[] BUFFER = new byte[1024];

	private static int i = 0;

	@Getter private static final Random random = new Random();
	@Getter private static final ScheduledExecutorService executorService = Executors.newScheduledThreadPool(5, new ThreadFactory() {

		@Override
		public Thread newThread(Runnable r) {
			return new Thread(r, "Advanced-API-Thread-" + Tool.i);
		}

	});

	public static void addLibrary(File file) {

		try{

			Method method = URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
		    method.setAccessible(true);
		    method.invoke(ClassLoader.getSystemClassLoader(), new Object[]{file.toURI().toURL()});

		}catch(Throwable e){
			Log.error(e.getMessage());
		}

	}

	public static void close(Object... objects){

		if(objects == null) return;

		for(Object object : objects){

			if(object == null) continue;
			if(!(object instanceof AutoCloseable)) continue;

			AutoCloseable closeable = (AutoCloseable) object;

			try{
				closeable.close();
			}catch(Throwable e){}

		}

	}

	public static int getLineNumber() {
	    return Thread.currentThread().getStackTrace()[2].getLineNumber();
	}

}
