package model;

public class SeqObject {

	private String objectName;
	private String className;

	public SeqObject(String objectName, String className) {
		this.objectName = objectName;
		this.className = className;
	}

	public String getObjectName() {
		return this.objectName;
	}

	public String getClassName() {
		return this.className;
	}

}