package com.mitocode.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MediaFile {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idFile;
	
	@Column(length = 50, nullable = false)
	private String fileName;
	
	@Column(length = 20, nullable = false)
	private String fileType;
	
	@Column(name = "content", nullable = false)
	private byte[] value;
}
