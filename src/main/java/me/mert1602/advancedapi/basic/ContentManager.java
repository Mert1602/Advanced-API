package me.mert1602.advancedapi.basic;

import java.util.List;

import me.mert1602.advancedapi.ContentInterface;

public interface ContentManager<T, C> extends ContentInterface<C>, Loadable, Startable, Reloadable, Stopable {

	public List<T> getList();

	public T register(T t);

	public void unregister(T t);

	public void unregisterAll();
	
	public void addDefault();
	
	public void addFolder();

}
