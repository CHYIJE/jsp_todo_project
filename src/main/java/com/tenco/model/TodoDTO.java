package com.tenco.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TodoDTO {
	
	private int id;
	private int userId;
	private String title;
	private String description;
	private String dueDate;
	private String created;
	private String completed; // "1", "0"
	
	// completed를 데이터 변환하는 메서드를 만들어보자.
	public String completedToString() {
		return completed.equals("1")? "true" : "false";
	}
	
}
