package Service;
import DAO.MessageDAO;

import java.util.List;

import DAO.AccountDAO;
import Model.Message;

public class MessageService {

    public MessageDAO messageDAO;
    public AccountDAO accountDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
        accountDAO = new AccountDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message addMessage(Message message){

        if(!message.getMessage_text().isBlank() 
            && message.getMessage_text().length() < 255
            && messageDAO.getAccountById(message.getPosted_by())
            ){
               Message newMsg =  messageDAO.insertMessage(message);
                return newMsg;
            }

        return null;
    }

    public List<Message> getAllMessages() {
        return messageDAO.getAllMessages();
    }

    public Message getMessageById(int id) {
        return messageDAO.getMessageById(id);
    }

    public Message deleteMessage(int id){
        Message checker = messageDAO.getMessageById(id);

        if (checker != null ){
            return messageDAO.deleteMessage(id);
        }

        return null;
        
    }

    public Message updateMessage(int id, Message msg){

        Message checker = messageDAO.getMessageById(id);

        if (checker != null && !msg.message_text.isBlank() && msg.getMessage_text().length() < 255){
            messageDAO.updateMessage(id, msg);
            return messageDAO.getMessageById(id);
        }

        return null;
    }

    public List<Message> getAllUserMessages(int id){
        return messageDAO.getAllUserMessages(id);
    }
    
}
