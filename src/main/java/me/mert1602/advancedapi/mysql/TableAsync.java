package me.mert1602.advancedapi.mysql;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import me.mert1602.advancedapi.Content;
import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.tool.Tool;
import me.mert1602.advancedapi.tool.ToolMySQL;

public class TableAsync extends Content<MySQL> implements TableInterface {

	@Getter private String name;
	private String primaryKey;
	private List<TableColumn<?>> columnList;
	@Getter private boolean online;
	@Getter @Setter private boolean debug;



	public TableAsync(final MySQL content, final String name, TableColumn<?>... columnArray) {
		this(content, name, null, columnArray);
	}

	public TableAsync(final MySQL content, final String name, final String primaryKey, TableColumn<?>... columnArray) {
		this(content, name, primaryKey, false, columnArray);
	}

	public TableAsync(final MySQL content, final String name, final String primaryKey, boolean debug, TableColumn<?>... columnArray) {
		super(content);

		this.name = name;
		this.primaryKey = primaryKey;
		this.columnList = new ArrayList<TableColumn<?>>();
		this.online = false;
		this.debug = debug;

		for(TableColumn<?> tc : columnArray){
			this.addColumn(tc).getUninterruptibly();
		}

	}

	@Override
	public List<TableColumn<?>> getColumnList(){
		return Collections.unmodifiableList(new ArrayList<TableColumn<?>>(this.columnList));
	}



	public TableCallback<TableAsync> create(){

		final TableCallback<TableAsync> result = new TableCallback<TableAsync>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				TableAsync.this.online = true;

				//Update Table
				if(ToolMySQL.hasTable(TableAsync.this.getContent(), TableAsync.this)){

					for(final TableColumn<?> column : TableAsync.this.getColumnList()){

						TableAsync.this.addOrUpdateColumn(column).getUninterruptibly();

						if(TableAsync.this.primaryKey == null) continue;
						if(!column.getName().equalsIgnoreCase(TableAsync.this.primaryKey)) continue;

						TableAsync.this.setPrimaryKey(column).getUninterruptibly();

					}

				//Create Table
				}else{

					final StringBuilder command = new StringBuilder();

					command.append("CREATE TABLE IF NOT EXISTS `" + TableAsync.this.name + "` ");
					command.append("(" + ToolMySQL.getSQL_Table_ColumnCreate(TableAsync.this));

					if(TableAsync.this.primaryKey != null){
						command.append(", PRIMARY KEY (`" + TableAsync.this.primaryKey + "`)");
					}

					command.append(")");

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + command.toString() + "\"");
					}

					Statement st = null;

					try{

						st = TableAsync.this.getContent().getConnection().createStatement();
						st.executeUpdate(command.toString());

					}catch(Throwable e){

						Log.exception(e, this.getClass(), Tool.getLineNumber());
						result.fail(TableAsync.this);

					}finally{
						Tool.close(st);
					}

				}

				result.set(TableAsync.this);

			}

		}).start();

		return result;
	}



	public TableCallback<String> getPrimaryKey(){

		final TableCallback<String> result = new TableCallback<String>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				if(TableAsync.this.online == false){
					result.set(TableAsync.this.primaryKey);
					return;
				}

				DatabaseMetaData md = null;
				ResultSet rs = null;

				try{

					md = TableAsync.this.getContent().getConnection().getMetaData();
					rs = md.getPrimaryKeys(null, null, TableAsync.this.name);

					if(rs.next()) TableAsync.this.primaryKey = rs.getString("COLUMN_NAME");

					result.set(TableAsync.this.primaryKey);

				}catch(Throwable e){

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(TableAsync.this.primaryKey);

				}finally{
					Tool.close(rs);
				}

			}

		}).start();

		return result;

	}

	public TableCallback<Boolean> hasPrimaryKey(){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {
				result.set(TableAsync.this.getPrimaryKey().getUninterruptibly() != null);
			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> removePrimaryKey(){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				TableAsync.this.primaryKey = null;

				if(!TableAsync.this.online){
					result.set(true);
					return;
				}

				Statement st = null;
				String sql = "ALTER TABLE `" + TableAsync.this.name + "` DROP PRIMARY KEY";

				try {

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					result.set(st.executeUpdate(sql) == 1 && (TableAsync.this.primaryKey == null));

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				} finally {
					Tool.close(st);
				}

			}

		}).start();

		return result;

	}

	public TableCallback<Boolean> setPrimaryKey(final TableColumn<?> column){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				TableAsync.this.removePrimaryKey().getUninterruptibly();
				TableAsync.this.primaryKey = column.getName();

				if(!TableAsync.this.online){
					result.set(true);
					return;
				}

				Statement st = null;
				String sql = "ALTER TABLE `" + TableAsync.this.name + "` ADD PRIMARY KEY (`" + column.getName() + "`)";

				try{

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					result.set(st.executeUpdate(sql) == 1 && TableAsync.this.primaryKey.equals(column.getName()));

				}catch(Throwable e){

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				}finally{
					Tool.close(st);
				}

			}

		}).start();

		return result;

	}



	public TableColumn<?> getColumn(String columnName){

		if(columnName == null) return null;

		for(TableColumn<?> otherColumn : this.getColumnList()){

			if(!otherColumn.getName().equals(columnName)) continue;

			return otherColumn;

		}

		return null;
	}

	public TableColumn<?> getColumn(TableColumn<?> column){

		if(column == null) return null;

		for(TableColumn<?> otherColumn : this.getColumnList()){

			if(!otherColumn.getName().equals(column.getName())) continue;

			return otherColumn;

		}

		return null;
	}

	public TableCallback<Boolean> hasColumn(String columnName){
		return this.hasColumn(this.getColumn(columnName));
	}

	public TableCallback<Boolean> hasColumn(final TableColumn<?> column){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				//Check if null
				if(column == null){

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" Column is null!");
					result.fail(false);

					return;
				}

				//Check Offline
				if(!TableAsync.this.online){
					result.set(TableAsync.this.getColumn(column) != null);
					return;
				}

				//Check Online and Offline
				DatabaseMetaData md = null;
				ResultSet rs = null;

				try {

					md = TableAsync.this.getContent().getConnection().getMetaData();
					rs = md.getColumns(null, null, TableAsync.this.name, column.getName());

					result.set(rs.next() && (TableAsync.this.getColumn(column) != null));

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				} finally {
					Tool.close(rs);
				}

			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> addColumn(final TableColumn<?> column){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				//Already in
				if(TableAsync.this.hasColumn(column).getUninterruptibly()){
					result.set(false);
					return;
				}

				//Add Offline
				if(TableAsync.this.getColumn(column) == null){
					TableAsync.this.columnList.add(column);
				}

				if(!TableAsync.this.online){
					result.set(true);
					return;
				}

				//Add Online
				Statement st = null;
				String sql = "ALTER TABLE `" + TableAsync.this.name + "` ADD " + column.getSQLSatement();

				try {

					//Online
					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					result.set(st.executeUpdate(sql) == 1 && (TableAsync.this.getColumn(column) != null));

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				} finally {
					Tool.close(st);
				}

			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> removeColumn(String columnName){
		return this.removeColumn(this.getColumn(columnName));
	}

	public TableCallback<Boolean> removeColumn(final TableColumn<?> column){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				//Check if null
				if(column == null){

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" Column is null!");
					result.fail(false);

					return;
				}

				//Already In
				if(!TableAsync.this.hasColumn(column).getUninterruptibly()){
					result.set(false);
					return;
				}

				//Remove Offline
				if(TableAsync.this.getColumn(column) != null){
					TableAsync.this.columnList.remove(TableAsync.this.getColumn(column));
				}

				if(!TableAsync.this.online){
					result.set(true);
					return;
				}

				//Remove Online
				Statement st = null;
				String sql = "ALTER TABLE `" + TableAsync.this.name + "` DROP `" + column.getName() + "`";

				try {

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					result.set(st.executeUpdate(sql) == 1 && (TableAsync.this.getColumn(column) == null));

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				} finally {
					Tool.close(st);
				}

			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> updateColumn(final TableColumn<?> column){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				//Check if in
				if(!TableAsync.this.hasColumn(column).getUninterruptibly()){
					result.set(false);
					return;
				}

				//Update Offline
				if(TableAsync.this.getColumn(column) != null){
					TableAsync.this.columnList.remove(TableAsync.this.getColumn(column));
				}

				TableAsync.this.columnList.add(column);

				if(!TableAsync.this.online){
					result.set(true);
					return;
				}

				//Update Online
				Statement st = null;
				String sql = "ALTER TABLE `" + TableAsync.this.name + "` CHANGE `" + column.getName() + "` "+ column.getSQLSatement();

				try {

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					result.set(st.executeUpdate(sql) == 1);

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				} finally {
					Tool.close(st);
				}

			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> addOrUpdateColumn(final TableColumn<?> column){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				if(TableAsync.this.hasColumn(column).getUninterruptibly()){
					result.set(TableAsync.this.updateColumn(column).getUninterruptibly());
				}else{
					result.set(TableAsync.this.addColumn(column).getUninterruptibly());
				}

			}

		}).start();

		return result;
	}



	public TableCallback<List<Entry>> getEntries(){

		final TableCallback<List<Entry>> result = new TableCallback<List<Entry>>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				List<Entry> list = new ArrayList<Entry>();

				if(TableAsync.this.online == false){

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" was not created!");
					result.fail(list);

					return;
				}

				Statement st = null;
				ResultSet rs = null;
				String sql = "SELECT * FROM `" + TableAsync.this.name + "`";

				try{

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					rs = st.executeQuery(sql);

					while(rs.next()){

						Entry entry = new Entry(TableAsync.this);

						for(final TableColumn<?> column : entry.getMap().keySet()){
							entry.setValue(column.getName(), rs.getObject(column.getName()));
						}

						list.add(entry);

					}

					result.set(list);

				}catch(Throwable e){

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(list);

				}finally{
					Tool.close(st, rs);
				}

			}

		}).start();

		return result;
	}

	public TableCallback<Entry> getEntry(final String columnName, final Object value){

		final TableCallback<Entry> result = new TableCallback<Entry>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				Entry entry = new Entry(TableAsync.this);

				if(TableAsync.this.online == false){

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" was not created!");
					result.fail(entry);

					return;
				}

				Statement st = null;
				ResultSet rs = null;
				String sql = "SELECT " + ToolMySQL.getSQL_Entry_ColumnNames(entry) + " FROM `" + TableAsync.this.name + "` WHERE `" + columnName + "`='" + value + "'";

				try {

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					rs = st.executeQuery(sql);

					while(rs.next()){

						entry = new Entry(TableAsync.this);

						for(final TableColumn<?> column : entry.getMap().keySet()){
							entry.setValue(column.getName(), rs.getObject(column.getName()));
						}

						break;
					}

					result.set(entry);

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(entry);

				} finally {
					Tool.close(st, rs);
				}

			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> hasEntry(final String columnName, final Object value){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				if(TableAsync.this.online == false){

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" was not created!");
					result.fail(false);

					return;
				}

				Statement st = null;
				ResultSet rs = null;
				String sql = "SELECT * FROM `" + TableAsync.this.name + "` WHERE `" + columnName + "`='" + value + "'";

				try {

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					rs = st.executeQuery(sql);

					result.set(rs.next());

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				} finally {
					Tool.close(st, rs);
				}

			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> addOrUpdateEntry(final Entry entry){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				if(TableAsync.this.online == false){

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" was not created!");
					result.fail(false);

					return;
				}

				if(TableAsync.this.hasPrimaryKey().getUninterruptibly()){

					String primaryKey = TableAsync.this.getPrimaryKey().getUninterruptibly();

					for(TableColumn<?> tc : entry.getMap().keySet()){

						if(!tc.getName().equals(primaryKey)) continue;

						if(TableAsync.this.hasEntry(tc.getName(), entry.getMap().get(tc)).getUninterruptibly()){
							result.set(TableAsync.this.updateEntry(entry, tc.getName(), entry.getMap().get(tc)).getUninterruptibly());
						}else{
							result.set(TableAsync.this.insertEntry(entry).getUninterruptibly());
						}

						return;
					}

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" PrimaryKey: \"" + primaryKey + "\" is not in the Entry Map!");
					result.fail(false);

				}else result.set(TableAsync.this.insertEntry(entry).getUninterruptibly());

			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> updateEntry(final Entry newEntry, final String columnName, final Object value){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				if(TableAsync.this.online == false){

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" was not created!");
					result.fail(false);

					return;
				}

				final StringBuilder updateSB = new StringBuilder();
				final Iterator<TableColumn<?>> i = newEntry.getMap().keySet().iterator();

				if(i.hasNext()){
					final String name1 = i.next().getName();
					updateSB.append("`" + name1 + "`=" + (newEntry.getValue(name1) instanceof Boolean ? ((Boolean)newEntry.getValue(name1)).toString().toUpperCase() : "'" + newEntry.getValue(name1) + "'"));
				}

				while(i.hasNext()){
					final String name2 = i.next().getName();
					updateSB.append(", `" + name2 + "`=" + (newEntry.getValue(name2) instanceof Boolean ? ((Boolean)newEntry.getValue(name2)).toString().toUpperCase() : "'" + newEntry.getValue(name2) + "'"));
				}

				Statement st = null;
				String sql = "UPDATE `" + TableAsync.this.name + "` SET " + updateSB.toString() + " WHERE `" + columnName + "`=" +
				(value instanceof Boolean ? ((Boolean)value).toString().toUpperCase() : "'" + value.toString() + "'");

				try {

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					result.set(st.executeUpdate(sql) == 1);

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				} finally {
					Tool.close(st);
				}

			}

		}).start();

		return result;
	}

	public TableCallback<Boolean> insertEntry(final Entry entry){

		final TableCallback<Boolean> result = new TableCallback<Boolean>();

		new Thread(new Runnable() {

			@Override
			public void run() {

				if(TableAsync.this.online == false){

					Log.error("MySQL: Table \"" + TableAsync.this.name + "\" was not created!");
					result.fail(false);

					return;
				}

				Statement st = null;
				String sql = "INSERT INTO `" + TableAsync.this.name + "`(" + ToolMySQL.getSQL_Entry_ColumnNames(entry) + ") VALUES (" + ToolMySQL.getSQL_Entry_ColumnValues(entry) + ")";

				try {

					st = TableAsync.this.getContent().getConnection().createStatement();

					if(TableAsync.this.debug){
						Log.debug("MySQL: \"" + sql + "\"");
					}

					result.set(st.executeUpdate(sql) == 1);

				} catch (final Throwable e) {

					Log.exception(e, this.getClass(), Tool.getLineNumber());
					result.fail(false);

				} finally {
					Tool.close(st);
				}

			}

		}).start();

		return result;
	}

}
