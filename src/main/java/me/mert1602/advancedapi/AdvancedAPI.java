package me.mert1602.advancedapi;

import me.mert1602.librarymanager.LibraryList;
import me.mert1602.librarymanager.LibraryManager;

public class AdvancedAPI {

	private static boolean librariesAdded = false;

	private AdvancedAPI() {}

	public static void init(){

		AdvancedAPI.addLibraries();

	}

	private static void addLibraries(){

		if(AdvancedAPI.librariesAdded) return;
		AdvancedAPI.librariesAdded = true;

		Log.info("Adding Advanced-API Libraries...");

		LibraryList list = LibraryManager.createLibraryList("Advanced-API-Libraries.gson");

		list.addLibrary("commons-lang-2.6", "http://ni952812_2.fastdownload.nitrado.net/dev/commons-lang-2.6.jar");
		list.addLibrary("guava-19.0", "http://ni952812_2.fastdownload.nitrado.net/dev/guava-19.0.jar");
		list.addLibrary("snakeyaml-1.17", "http://ni952812_2.fastdownload.nitrado.net/dev/snakeyaml-1.17.jar");

		LibraryManager.registerLibraryList(list, false);

		Log.info("Advanced-API Libraries added!");

	}

}
