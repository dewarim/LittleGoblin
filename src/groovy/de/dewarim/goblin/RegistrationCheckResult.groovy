package de.dewarim.goblin

/**
 */
class RegistrationCheckResult {
    
    List<MessageWithArgs> problems = new ArrayList<>()
    
    boolean isOkay(){
        return problems.isEmpty()
    }
    
    void addProblem(String messageId){
        addProblem(messageId, [])
    }
    void addProblem(String messageId, List args){
        problems.add(new MessageWithArgs(messageId: messageId, args: args))
    }


    @Override
    public String toString() {
        return "RegistrationCheckResult{" +
                "problems=" + problems +
                '}';
    }
}
