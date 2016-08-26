package me.mert1602.advancedapi.mysql;

import lombok.Getter;

public class TableColumnInteger implements TableColumn<Integer> {

	@Getter private final String name;
	@Getter private final Integer defaultValue;

	public TableColumnInteger(final String name) {
		this(name, 0);
	}

	public TableColumnInteger(final String name, final Integer defaultValue) {

		this.name = name;
		this.defaultValue = defaultValue;

	}

	@Override
	public String getSQLSatement() {
		return "`" + this.name + "` INT NOT NULL DEFAULT '" + this.defaultValue + "'";
	}

}
