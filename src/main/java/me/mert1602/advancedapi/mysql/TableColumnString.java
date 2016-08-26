package me.mert1602.advancedapi.mysql;

import lombok.Getter;

public class TableColumnString implements TableColumn<String> {

	@Getter private final String name;
	@Getter private final int maxStringLength;
	@Getter private final String defaultValue;

	public TableColumnString(final String name) {
		this(name, 64, "");
	}

	public TableColumnString(final String name, final int maxStringLength) {
		this(name, maxStringLength, "");
	}

	public TableColumnString(final String name, final int maxStringLength, final String defaultValue) {

		this.name = name;
		this.maxStringLength = maxStringLength;
		this.defaultValue = defaultValue;

	}

	@Override
	public String getSQLSatement() {
		return "`" + this.name + "` VARCHAR(" + this.maxStringLength + ") NOT NULL DEFAULT '" + this.defaultValue + "'";
	}

}
