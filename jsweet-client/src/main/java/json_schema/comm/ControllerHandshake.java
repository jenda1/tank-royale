package json_schema.comm;

public class ControllerHandshake extends Message2 {

	public static final String TYPE = "controllerHandshake";

	public ControllerHandshake() {
		super(TYPE);
	}

	public ControllerHandshake(String type) {
		super(type);
	}

	public void setName(String name) {
		$set("name", name);
	}

	public void setVersion(String version) {
		$set("version", version);
	}

	public void setAuthor(String author) {
		$set("author", author);
	}
}