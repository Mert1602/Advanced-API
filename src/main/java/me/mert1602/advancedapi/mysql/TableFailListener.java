package me.mert1602.advancedapi.mysql;

public interface TableFailListener<T> {
	
	public void onFail(T value);

}
