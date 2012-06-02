package org.test.streaming;

import java.io.Serializable;

import org.apache.commons.lang.builder.ToStringBuilder;

public class CachoRequest implements Serializable {

	private String movieId;
	private String fileName;
	private MovieCacho cacho;

	public CachoRequest() {
	}

	public CachoRequest(String movieId, String fileName, int firstByte, int length) {
		super();
		this.setMovieId(movieId);
		this.setFileName(fileName);
		this.setCacho(new MovieCacho(firstByte, length));
	}

	public int getFirstByteIndex() {
		return this.getCacho().getFirstByteIndex();
	}

	public int getLength() {
		return this.getCacho().getLength();
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	public String getMovieId() {
		return movieId;
	}

	public void setMovieId(String movieId) {
		this.movieId = movieId;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public MovieCacho getCacho() {
		return cacho;
	}

	public void setCacho(MovieCacho cacho) {
		this.cacho = cacho;
	}

}
