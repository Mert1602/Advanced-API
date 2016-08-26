package me.mert1602.advancedapi.basic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import me.mert1602.advancedapi.Content;
import me.mert1602.advancedapi.Removeable;

public abstract class BasicContentManager<T, C> extends Content<C> implements ContentManager<T, C> {

	private final List<T> list;

	BasicContentManager() {
		this(null);
	}

	public BasicContentManager(final C content) {
		super(content);

		this.list = new ArrayList<T>();

	}
	
	@Override
	public void load() {
		
		this.addFolder();
		this.unregisterAll();
		
	}
	
	@Override
	public void start() {
		
		this.addFolder();
		this.unregisterAll();
		this.addDefault();
		
	}
	
	@Override
	public void reload() {
		
		this.addFolder();
		
		for(T t : this.getList()){
			
			if((t instanceof Removeable) == false) continue;
			Removeable removeable = (Removeable) t;
			removeable.remove();
			
		}
		
		this.unregisterAll();
		this.addDefault();
		
	}
	
	@Override
	public void stop() {
		
		this.addFolder();
		
		for(T t : this.getList()){
			
			if((t instanceof Removeable) == false) continue;
			Removeable removeable = (Removeable) t;
			removeable.remove();
			
		}
		
		this.unregisterAll();
		
	}



	@Override
	public final List<T> getList(){
		return Collections.unmodifiableList(new ArrayList<T>(this.list));
	}



	@Override
	public T register(final T t) {

		if(this.list.contains(t)) return null;

		this.list.add(t);

		return t;
	}

	@Override
	public final void unregister(final T t) {

		if(this.list.contains(t)){
			this.list.remove(t);
		}

	}

	@Override
	public final void unregisterAll() {
		this.list.clear();
	}
	
	@Override
	public void addDefault() {}
	
	@Override
	public void addFolder() {}

}
