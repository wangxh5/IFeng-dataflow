package bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class JsonStruction {
	@Expose
	@SerializedName("name")
	private String name; // ID

	@Expose
	@SerializedName("value")
	private String[] value; // ID

	public void setValue(String[] value) {
		this.value = value;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
