package me.mert1602.advancedapi.mysql;

import lombok.Getter;

public class TableColumnDouble implements TableColumn<Double> {

	@Getter private final String name;
	@Getter private final Double defaultValue;

	public TableColumnDouble(final String name) {
		this(name, 0D);
	}

	public TableColumnDouble(final String name, final Double defaultValue) {

		this.name = name;
		this.defaultValue = defaultValue;

	}

	@Override
	public String getSQLSatement() {
		return "`" + this.name + "` DOUBLE NOT NULL DEFAULT '" + this.defaultValue + "'";
	}

}
