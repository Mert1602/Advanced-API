package me.mert1602.advancedapi.mysql;

public interface TableColumn<T> {

	public String getName();

	public T getDefaultValue();

	public String getSQLSatement();

}
