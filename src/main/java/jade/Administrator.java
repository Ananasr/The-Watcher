package jade;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;

/**
 * Take the command line parameter
 */
public class Administrator extends Agent {

    @Override
    protected void setup() {
        System.out.println("Hello ! My name is " + getLocalName());
        System.out.println("AID : " + getAID().getName());

        parseArguments();

        addBehaviour(new CyclicBehaviour(this) {
            @Override
            public void action() {
                System.out.println("Waiting for message");
                ACLMessage message = myAgent.receive();
                if (message != null) {
                    // process the received message
                    System.out.println("Receive message : " + message.getContent());
                    //
                    ACLMessage reply = message.createReply();
                    reply.setPerformative(ACLMessage.CONFIRM);
                    reply.setContent("ACK");
                    send(reply);
                } else {
                    // block the behaviour while receiving message
                    System.out.println("block");
                    block();
                }
            }
        });
    }

    private void parseArguments() {
        // get arguments
        Object[] args = getArguments();
        String s;

        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                s = (String) args[i];
                System.out.println("p" + i + ": " + s);
            }
        }
    }

    @Override
    protected void takeDown() {
        System.out.println("Good bye");
    }
}
