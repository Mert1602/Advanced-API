package me.mert1602.advancedapi.setting;

public interface SettingNumeric<T extends Number> {

	public void add(T value);

	public void remove(T value);

	public void multiply(T value);

	public void divide(T value);

}
