package dim.kal.model;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Represents a message response for the greetings service.
 * This class encapsulates the message text along with optional age and name details.
 * Fields with {@code null} values will not be included in the JSON output.
 */
public class Message {
    private String msg;
    private Integer age;
    private String name;


    /**
     * Default constructor required for JSON deserialization.
     */
    public Message() {
    }

    /**
     * Constructs a Message with the specified message text.
     *
     * @param msg the message text
     */
    public Message(String msg) {
        this.msg = msg;
    }

    /**
     * Returns the message text.
     *
     * @return the message text
     */
    public String getMsg() {
        return msg;
    }

    /**
     * Sets the message text.
     *
     * @param msg the message text to set
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * Returns the age associated with the message.
     *
     * @return the age, or {@code null} if not set
     */
    public Integer getAge() {
        return age;
    }

    /**
     * Sets the age associated with the message.
     *
     * @param age the age to set
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * Returns the name associated with the message.
     *
     * @return the name, or {@code null} if not set
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name associated with the message.
     *
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }
}