package de.dewarim.goblin

/**
 * 
 */
class MessageWithArgs {
    
    String messageId
    List args


    @Override
    public String toString() {
        return "MessageWithArgs{" +
                "messageId='" + messageId + '\'' +
                ", args=" + args +
                '}';
    }
}
