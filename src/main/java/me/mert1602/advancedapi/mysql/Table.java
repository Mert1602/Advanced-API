package me.mert1602.advancedapi.mysql;

import java.util.List;

import lombok.Getter;
import me.mert1602.advancedapi.Content;

public class Table extends Content<MySQL> implements TableInterface {

	@Getter private TableAsync asyncTable;

	public Table(MySQL content, String name, TableColumn<?>... columnArray) {
		super(content);

		this.asyncTable = new TableAsync(content, name, columnArray);

	}

	public Table(MySQL content, String name, String primaryKey, TableColumn<?>... columnArray) {
		super(content);

		this.asyncTable = new TableAsync(content, name, primaryKey, columnArray);

	}

	public Table(MySQL content, String name, String primaryKey, boolean debug, TableColumn<?>... columnArray) {
		super(content);

		this.asyncTable = new TableAsync(content, name, primaryKey, debug, columnArray);

	}



	@Override
	public String getName() {
		return this.asyncTable.getName();
	}

	@Override
	public List<TableColumn<?>> getColumnList() {
		return this.asyncTable.getColumnList();
	}

	@Override
	public boolean isOnline() {
		return this.asyncTable.isOnline();
	}

	@Override
	public boolean isDebug() {
		return this.asyncTable.isDebug();
	}

	@Override
	public void setDebug(boolean value) {
		this.asyncTable.setDebug(value);
	}



	public Table create(){
		this.asyncTable.create().getUninterruptibly();
		return this;
	}



	public String getPrimaryKey(){
		return this.asyncTable.getPrimaryKey().getUninterruptibly();
	}

	public boolean hasPrimaryKey(){
		return this.asyncTable.hasPrimaryKey().getUninterruptibly();
	}

	public boolean removePrimaryKey(){
		return this.asyncTable.removePrimaryKey().getUninterruptibly();
	}

	public boolean setPrimaryKey(TableColumn<?> column){
		return this.asyncTable.setPrimaryKey(column).getUninterruptibly();
	}



	public TableColumn<?> getColumn(String columnName){
		return this.asyncTable.getColumn(columnName);
	}

	public TableColumn<?> getColumn(TableColumn<?> column){
		return this.asyncTable.getColumn(column);
	}

	public boolean hasColumn(String columnName){
		return this.asyncTable.hasColumn(columnName).getUninterruptibly();
	}

	public boolean hasColumn(TableColumn<?> column){
		return this.asyncTable.hasColumn(column).getUninterruptibly();
	}

	public boolean addColumn(TableColumn<?> column){
		return this.asyncTable.addColumn(column).getUninterruptibly();
	}

	public boolean removeColumn(String columnName){
		return this.asyncTable.removeColumn(columnName).getUninterruptibly();
	}

	public boolean removeColumn(TableColumn<?> column){
		return this.asyncTable.removeColumn(column).getUninterruptibly();
	}

	public boolean updateColumn(TableColumn<?> column){
		return this.asyncTable.updateColumn(column).getUninterruptibly();
	}

	public boolean addOrUpdateColumn(TableColumn<?> column){
		return this.asyncTable.addOrUpdateColumn(column).getUninterruptibly();
	}



	public List<Entry> getEntries(){
		return this.asyncTable.getEntries().getUninterruptibly();
	}

	public Entry getEntry(String columnName, Object value){
		return this.asyncTable.getEntry(columnName, value).getUninterruptibly();
	}

	public boolean hasEntry(String columnName, Object value){
		return this.asyncTable.hasEntry(columnName, value).getUninterruptibly();
	}

	public boolean addOrUpdateEntry(Entry entry){
		return this.asyncTable.addOrUpdateEntry(entry).getUninterruptibly();
	}

	public boolean updateEntry(Entry newEntry, String columnName, Object value){
		return this.asyncTable.updateEntry(newEntry, columnName, value).getUninterruptibly();
	}

	public boolean insertEntry(Entry entry){
		return this.asyncTable.insertEntry(entry).getUninterruptibly();
	}

}
