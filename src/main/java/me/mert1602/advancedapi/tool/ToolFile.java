package me.mert1602.advancedapi.tool;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.config.file.FileConfiguration;

public class ToolFile {

	private ToolFile() {}

	public static boolean saveConfig(final FileConfiguration config, final File file){

		try {
			config.save(file);
			return true;
		} catch (final Throwable e) {return false;}

	}

	public static boolean loadConfig(final FileConfiguration config, final File file){

		try {
			config.load(file);
			return true;
		} catch (final Throwable e) {return false;}

	}

	public static void copy(File src, File dest){

		if(src.isDirectory()){

			if(!dest.exists()){
				dest.mkdir();
			}

			String files[] = src.list();

			for (String file : files) {

				File srcFile = new File(src, file);
				File destFile = new File(dest, file);

				ToolFile.copy(srcFile,destFile);

			}

		}else{

			InputStream in = null;
			OutputStream out = null;
			int length;

			try{

				in = new FileInputStream(src);
				out = new FileOutputStream(dest);

				while ((length = in.read(Tool.BUFFER)) > 0){
					out.write(Tool.BUFFER, 0, length);
				}

				in.close();
				out.close();

			}catch(Throwable e){
				Log.exception(e, ToolFile.class, Tool.getLineNumber());
			}finally{
				Tool.close(in, out);
			}

		}

	}

	public static void deleteFolderAndFiles(final File file){

		if(file.exists() == false) {
			return;
		}

		if(file.isDirectory()){

			for(final File folderFile : file.listFiles()){

				while(folderFile.exists()){
					ToolFile.deleteFolderAndFiles(folderFile);
				}

			}

		}

		while(file.exists()){
			file.delete();
		}

	}

	public static void setProperties(File file, String path, String value){

		Properties properties = new Properties();
		FileInputStream fis = null;
		FileOutputStream fos = null;

		try{

			fis = new FileInputStream(file);

			properties.load(fis);
			properties.setProperty(path, value);

			fos = new FileOutputStream(file);

			properties.store(fos, "");

		}catch(Throwable e){
			Log.exception(e, ToolFile.class, Tool.getLineNumber());
		}finally{
			Tool.close(fis, fos);
		}

	}

	public static void replaceString(File file, String text, String replacement){

		FileReader fReader = null;
		BufferedReader reader = null;
		FileWriter writer = null;

		try{

			fReader = new FileReader(file);
			reader = new BufferedReader(fReader);

			String line = "", oldtext = "";

			while((line = reader.readLine()) != null){
				oldtext += line + "\r\n";
			}

			String newtext = oldtext.replaceAll(text, replacement);
			writer = new FileWriter(file);

			writer.write(newtext);
			writer.flush();

		}catch(Throwable e){
			Log.exception(e, ToolFile.class, Tool.getLineNumber());
		}finally{
			Tool.close(fReader, reader, writer);
		}

	}

	public static File downloadFile(String link, String path, String filename, boolean log){

		File file = new File(path, filename);

		URL url = null;
		URLConnection connection = null;
		InputStream is = null;
		BufferedInputStream bis = null;
		ByteArrayOutputStream baos = null;
		FileOutputStream fos = null;
		byte[] response = null;

		try{

			url = new URL(link);
			connection = url.openConnection();

			if(file.exists()){

				if(file.length() != connection.getContentLength()){

					while(file.exists()){
						ToolFile.deleteFolderAndFiles(file);
					}

					return ToolFile.downloadFile(link, path, filename, log);
				}

				return file;
			}

			is = connection.getInputStream();
			bis = new BufferedInputStream(is);
			baos = new ByteArrayOutputStream();

			int n = 0;
			int currentProzent = -1;

			while (-1!=(n=bis.read(Tool.BUFFER))){

				baos.write(Tool.BUFFER, 0, n);

				float size = baos.size();
				float full = connection.getContentLength();
				int prozent = Math.round((size / full) * 100F);

				if(prozent != 0 && currentProzent != prozent){

					currentProzent = prozent;

					if(ToolNumber.isDivisible(currentProzent, 20) && log){
						Log.info("Downloading: '" + file.getName() + "' " + currentProzent + "%");
					}

				}

			}

			if(log) Log.info("'" + file.getName() + "' downloaded!");

			response = baos.toByteArray();
			fos = new FileOutputStream(file);
			fos.write(response);

			return file;

		}catch(Throwable e){
			Log.exception(e, ToolFile.class, Tool.getLineNumber());
			return null;
		}finally{
			Tool.close(is, bis, baos, fos);
		}

	}

}
