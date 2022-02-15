package Server.Model;

public class Message {
    public final static int REGISTER = 0;
    public final static int LOGIN = 1;
    public final static int ADD_CONTACT = 2;
    public final static int REMOVE_CONTACT = 3;
    public final static int TEXT_MESSAGE = 4;
    public final static int UPDATE_CONTACTS = 6;
    public final static int GET_CONTACTS = 9;
    public final static int GET_USER = 10;
    
    public final static String SUCCESS = "success";
    public final static String FAIL = "fail";

    private int action;
    private String[] inputs;
    private String content;
    private String usernameSender;
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getInputs() {
        return inputs;
    }

    public String getUsernameSender() {
        return usernameSender;
    }

    public void setUsernameSender(String usernameSender) {
        this.usernameSender = usernameSender;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAction() {
        return action;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setInputs(String... inputs) {
        this.inputs = inputs;
    }
    
    public void setInputWithArray(String[] inputs){
        this.inputs = inputs;
    }
    
    
    
    
    
}
