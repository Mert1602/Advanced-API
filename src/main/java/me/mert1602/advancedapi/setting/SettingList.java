package me.mert1602.advancedapi.setting;

import java.util.List;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.tool.Tool;

public abstract class SettingList<T> extends Setting<List<T>> {

	private int nextCounter;

	public SettingList(final SettingManager<?> settingManager, final String name, final String path, final SettingType type, final boolean hasLanguage, final List<T> defaultObject) {
		super(settingManager, name, path, type, hasLanguage, defaultObject);

		this.nextCounter = -1;

	}



	public final T[] getArray(){
		return this.getArray(SettingLanguage.getDefaultLanguage());
	}

	public abstract T[] getArray(SettingLanguage lang);



	public final T getNext(){
		return this.getNext(SettingLanguage.getDefaultLanguage());
	}

	public T getNext(final SettingLanguage lang){

		try{

			List<T> list = null;

			if(this.hasLanguage()){
				list = this.get(lang);
			} else {
				list = this.get();
			}

			if(this.nextCounter == (list.size() - 1)){
				this.nextCounter = 0;
			}else{
				this.nextCounter++;
			}

			return list.get(this.nextCounter);

		}catch(final Throwable e){
			Log.error(e.getMessage());
			return this.getDefaultObject().get(0);
		}

	}



	public final T getRandom(){
		return this.getRandom(SettingLanguage.getDefaultLanguage());
	}

	public T getRandom(final SettingLanguage lang){

		try{

			return this.hasLanguage() ?
					this.get(lang).get(Tool.getRandom().nextInt(this.get(lang).size())) :
						this.get().get(Tool.getRandom().nextInt(this.get().size()));

		}catch(final Throwable e){
			Log.error(e.getMessage());
			return this.getDefaultObject().get(0);
		}

	}



	public final void add(final T t){
		this.add(t, SettingLanguage.getDefaultLanguage());
	}

	public final void add(final T t, final SettingLanguage lang){

		final List<T> list = this.get(lang);
		list.add(t);
		this.set(list, lang);

	}

	public final void remove(final T t){
		this.remove(t, SettingLanguage.getDefaultLanguage());
	}

	public final void remove(final T t, final SettingLanguage lang){

		final List<T> list = this.get(lang);
		list.remove(t);
		this.set(list, lang);

	}

	public final void remove(final int arg0){
		this.remove(arg0, SettingLanguage.getDefaultLanguage());
	}

	public final void remove(final int arg0, final SettingLanguage lang){

		final List<T> list = this.get(lang);
		list.remove(arg0);
		this.set(list, lang);

	}

	public final void clear(){
		this.clear(SettingLanguage.getDefaultLanguage());
	}

	public final void clear(final SettingLanguage lang){

		final List<T> list = this.get(lang);
		list.clear();
		this.set(list, lang);

	}

}
