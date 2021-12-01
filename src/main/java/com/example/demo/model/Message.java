package com.example.demo.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Message {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long msgId;
	private String time;
	private String content;
	
	public Message() {
		this.time = printDateTime(LocalDateTime.now());
	}
	
	public Message(String content) {
		this.time = printDateTime(LocalDateTime.now());
		this.content = content;
	}
	
	public String printDateTime(LocalDateTime ldt) {
		DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm" );
		return ldt.format(dateFormatter);
	}

	public Long getMsgId() {
		return msgId;
	}

	public void setMsgId(Long msgId) {
		this.msgId = msgId;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
