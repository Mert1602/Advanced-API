package me.mert1602.advancedapi.mysql;

import java.util.List;

import me.mert1602.advancedapi.ContentInterface;

public interface TableInterface extends ContentInterface<MySQL> {

	public String getName();

	public List<TableColumn<?>> getColumnList();

	public boolean isOnline();

	public boolean isDebug();

	public void setDebug(boolean value);

}
