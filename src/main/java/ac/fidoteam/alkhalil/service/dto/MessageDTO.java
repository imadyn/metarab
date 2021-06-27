package ac.fidoteam.alkhalil.service.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * A DTO representing a user, with his authorities.
 */
public class MessageDTO {

    @NotBlank
    private String type;

    @NotNull
    private Long from;

    @Size(max = 50)
    private String fromUserName;

    private String message;

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the from
	 */
	public Long getFrom() {
		return from;
	}

	/**
	 * @param from the from to set
	 */
	public void setFrom(Long from) {
		this.from = from;
	}

	/**
	 * @return the fromUserName
	 */
	public String getFromUserName() {
		return fromUserName;
	}

	/**
	 * @param fromUserName the fromUserName to set
	 */
	public void setFromUserName(String fromUserName) {
		this.fromUserName = fromUserName;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return String.format("MessageDTO [type=%s, from=%s, fromUserName=%s, message=%s]", type, from, fromUserName,
				message);
	}

    
}
