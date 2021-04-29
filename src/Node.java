import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import org.apache.commons.codec.binary.StringUtils;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.*;

public class Node extends Agent {
    private String arg0;

    private JButton BCcreation;
    private JButton Tcreation;
    private JButton showMR;

    /** Locally stored blockchain */
    private static List<Block> blockChain = new ArrayList<Block>();
    //Unpackaged transactions
    public static Block unpackBlock = new Block();
    public static Map<String,TransactionOutput> UTXOs = new HashMap<String,TransactionOutput>();
    /** Initial difficulty */
    public static final int difficulty = 3;
    /** Minimum transaction amount (is disabled in ds project)*/
    public static final float minimumTransaction = 0.1f;        // aKa tees
    public static Wallet walletA;
    public static Wallet walletB;
    private ArrayList<String> neighbors = new ArrayList<String>();
    //public static Transaction genesisTransaction;
    private static final String VERSION = "0.1";
    public static int counter = 0;
    public static long startTime = System.currentTimeMillis();
    //used to broadcast
    private boolean trigger = false;
    protected void setup() {


        Object[] args = this.getArguments();
        if (args != null) {
            System.out.println(args[0].toString());
            arg0 = args[0].toString();

            for (int i = 1; i < args.length; ++i) {
                if (!this.getLocalName().equals(args[i].toString())) {
                    neighbors.add(args[i].toString());
                }
            }
        }
        FSMBehaviour fsm = new FSMBehaviour(this);
        Behaviour mining = new TickerBehaviour(this, 5000) {
            @Override
            public void onTick() {
                String hash = "";
                try {
                    hash = unpackBlock.mineBlock();
                }catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
                System.out.println(hash+"\t\t agent"+this.getAgent().getLocalName());
            }
        };
       // addBehaviour(mining);


        Window window = new Window();
        window.setResizable(false);

        walletA = new Wallet();
        walletB = new Wallet();



        window.setVisible(true);

       // Node.unpackBlock.setTarget(3);
        /** creating a new transaction */
//        Transaction tx= walletA.sendFunds(walletB.publicKey, 100);
//        Node.unpackBlock.data.add(tx);




        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            System.out.println("hashe..."+digest.digest(StringUtils.getBytesUtf8("blabla")));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
//        fsm.registerFirstState(new SendTransaction(this, 50), "SendT");
//        fsm.registerState(new ReceiveTransaction(this, 50), "ReceiveT");
//        fsm.registerState(new SendBlock(), "SendB");
//        fsm.registerState(new ReceiveBlock(), "ReceiveB");

        ParallelBehaviour pb = new ParallelBehaviour();
        pb.addSubBehaviour(new SendBlock());
        pb.addSubBehaviour(new SendTransaction(this, 100));
        pb.addSubBehaviour(new ReceiveBlock());
        pb.addSubBehaviour(new ReceiveTransaction(this, 50));
        addBehaviour(pb);

        window.incrementtarget();
        window.decrementtarget();
        window.transactionCreation();
        window.merkleTree();
        window.blockInfo(Node.unpackBlock);
        window.hash(Node.unpackBlock);
        window.mine(Node.unpackBlock);


    }


    public String startMining(){
        try {
             return unpackBlock.mineBlock();
        }catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        }
        return "error";
    }

    class SendTransaction extends TickerBehaviour{


        public SendTransaction(Agent a, long period) {
            super(a, period);
        }

        @Override
        protected void onTick() {
            Random rn = new Random();
            broadcastT(new Transaction(rn.nextInt(100)), neighbors);
        }
    }

    class ReceiveTransaction extends TickerBehaviour{


        public ReceiveTransaction(Agent a, long period) {
            super(a, period);
        }

        public void onTick() {

            System.out.println(System.currentTimeMillis());
            receiv0Msge();

        }
    }
    class SendBlock extends OneShotBehaviour{

        @Override
        public void action() {

        }
    }
    class ReceiveBlock extends OneShotBehaviour{

        @Override
        public void action() {

        }
    }

    public void broadcastT(Transaction tx, ArrayList<String> destination){
        ACLMessage message = new ACLMessage(7);
        try {
            message.setContentObject((Serializable) tx);
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (String dest : destination){
            message.addReceiver(new AID(dest, false));
            send(message);
            System.out.println("Transaction "+tx.transactionId+" has been sent "+
                    "to "+ dest);
        }

    }

    public void receiv0Msge(){

            ACLMessage msg = receive();
            while (msg != null) {
                try {
                    Transaction t = (Transaction) msg.getContentObject();
                    counter++;
                    if ((System.currentTimeMillis()-startTime)==10000)
                        System.out.println(counter);
                } catch (UnreadableException e) {
                    e.printStackTrace();
                }
            }


    }

    public Block createBlock(){
        return  new Block();
    }

    public Transaction createTransaction(){
        return  new Transaction();
    }

}
