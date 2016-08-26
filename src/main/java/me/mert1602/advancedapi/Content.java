package me.mert1602.advancedapi;

import lombok.Getter;
import lombok.Setter;

public class Content<T> implements ContentInterface<T> {

	@Getter @Setter private transient T content;

	public Content(T content) {
		this.content = content;
	}

}
