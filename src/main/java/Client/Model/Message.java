package Client.Model;

public class Message {
    public final static int REGISTER = 0;
    public final static int LOGIN = 1;
    public final static int ADD_CONTACT = 2;
    public final static int REMOVE_CONTACT = 3;
    public final static int TEXT_MESSAGE = 4;
    public final static int ARCHIVE_MESSAGE = 5;
    
    private int action;
    private String[] inputs;
    private String content;

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
    
    
    
    
    
}
