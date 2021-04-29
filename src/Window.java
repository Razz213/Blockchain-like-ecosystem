import javax.swing.*;

import javax.swing.border.EmptyBorder;
import java.awt.event.ActionEvent;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Random;


public class Window extends JFrame {

    private JPanel contentPane;
    private JTextArea BCinfo;
    private JTextArea currentBlock;
    private JTextField proofOfWork;
    private JTextField logs;
    private JButton BCcreation;
    private JButton Tcreation;
    private JButton showMR;
    private JButton blockInfo;
    private JButton Hash;
    private JButton mine;
    private JButton increment;
    private JButton decrement;
    Random rn = new Random();

    public void setBCinfo(String BCinfo) {
        this.BCinfo.setText(BCinfo);
    }

    public String getBCinfo() {
        return BCinfo.getText();
    }
    public String getCurrentBlock() {
        return currentBlock.getText();
    }
    public String getProofOfWork() {
        return proofOfWork.getText();
    }
    public String getLogs() {
        return logs.getText();
    }

    public void setCurrentBlock(String currentBlock) {
        this.currentBlock.setText(currentBlock);
    }

    public void setProofOfWork(String proofOfWork) {
        this.proofOfWork.setText(proofOfWork);
    }

    public void setLogs(String logs) {
        this.logs.setText(logs);
    }




    public Window() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        BCinfo = new JTextArea();
        BCinfo.setBounds(120, 30, 700, 125);
        contentPane.add(BCinfo);
        BCinfo.setColumns(10);

        currentBlock = new JTextArea();
        currentBlock.setBounds(120, 185, 260, 450);
        contentPane.add(currentBlock);
        currentBlock.setColumns(10);

        proofOfWork = new JTextField();
        proofOfWork.setBounds(435, 185, 390, 156);
        contentPane.add(proofOfWork);
        proofOfWork.setColumns(10);

        logs = new JTextField();
        logs.setBounds(435, 356, 390, 285);
        contentPane.add(logs);
        logs.setColumns(10);

        BCcreation = new JButton("Block");
        BCcreation.setBounds(10,30,95,60);
        blockCreation();
        contentPane.add(BCcreation);


        Tcreation = new JButton("Transaction");
        Tcreation.setBounds(10,105,95,60);
        contentPane.add(Tcreation);


        showMR = new JButton("Show MR");
        showMR.setBounds(10,180,95,60);
        contentPane.add(showMR);

        blockInfo = new JButton("Block info");
        blockInfo.setBounds(10,255,95,60);
        contentPane.add(blockInfo);

        Hash = new JButton("mine once");
        Hash.setBounds(10,330,95,60);
        contentPane.add(Hash);

        mine = new JButton("Mine !");
        mine.setBounds(10,405,95,60);
        contentPane.add(mine);

        increment = new JButton("increment D");
        increment.setBounds(10,480,95,60);
        contentPane.add(increment);

        decrement = new JButton("decrement D");
        decrement.setBounds(10,555,95,60);
        contentPane.add(decrement);


        JLabel lblMessagesRecus = new JLabel("Information on the blockchain");
        lblMessagesRecus.setBounds(308, 5, 300, 14);
        contentPane.add(lblMessagesRecus);

        JLabel lblMessagesEnvoys = new JLabel("current block");
        lblMessagesEnvoys.setBounds(190, 170, 104, 14);
        contentPane.add(lblMessagesEnvoys);

        JLabel lblValeurChoisie = new JLabel("proof of work");
        lblValeurChoisie.setBounds(510, 170, 184, 14);

        contentPane.add(lblValeurChoisie);

        JLabel lblValeurLocale = new JLabel("logs");
        lblValeurLocale.setBounds(550, 340, 90, 14);
        contentPane.add(lblValeurLocale);



    }
    public JButton getBCcreation() {
        return BCcreation;
    }

    public JButton getTcreation() {
        return Tcreation;
    }

    public JButton getShowMR() {
        return showMR;
    }


    public void setBCcreation(JButton BCcreation) {
        this.BCcreation = BCcreation;
    }

    public void setTcreation(JButton tcreation) {
        Tcreation = tcreation;
    }

    public void setShowMR(String showMR) {
        this.showMR.setText(showMR);
    }

    public void blockCreation(){
        Wallet walletA = new Wallet();
        Wallet walletB = new Wallet();

        BCcreation.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Block b = new Block();
                for(Transaction t : Node.unpackBlock.data){
                    b.data.add(t);
                }

                String mr = "!";
                System.out.println("Block created successfully");
            }
        });
    }

    public void transactionCreation(){


        Tcreation.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Transaction t0= new Transaction(rn.nextInt(100));
                t0.setTransactionId(""+rn.nextInt(30));
                Node.unpackBlock.data.add(t0);
                System.out.println("Transaction created successfully");
            }
        });
    }

    public void blockInfo(Block block){
        blockInfo.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentBlock.setText("");
                for (Transaction t : block.data){
                    currentBlock.append("ID "+t+" S "+t.sender+" R "+t.reciepient+" V "+t.value+"\n");

                }
            }
        });
    }

    public void hash(Block block){
        Hash.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String h="";
                    h = Block.calculateHash(block);
                    proofOfWork.setText(h);
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }


    public void merkleTree(){

        showMR.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String mr = "";

                try {
                    mr = CommonUtils.getMerkleRoot(Node.unpackBlock.data);
                    System.out.println(mr);
                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
                setBCinfo("");
                setBCinfo(mr);
            }
        });


    }

    public void incrementtarget(){
        increment.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dif = Node.unpackBlock.getTarget();
                System.out.println(dif);
                Node.unpackBlock.setTarget(dif+1);
                System.out.println(Node.unpackBlock.getTarget());
            }
        });

    }

    public void decrementtarget(){
        decrement.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int dif = Node.unpackBlock.getTarget();
                System.out.println(dif);
                Node.unpackBlock.setTarget(dif-1);
                System.out.println(Node.unpackBlock.getTarget());

            }
        });
    }

    public void mine(Block block){
        mine.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {

                    proofOfWork.setText(block.mineBlock());
                    /*
                    transaction t = new Transaction(wallet.publicKey, 5)

                    */


                } catch (NoSuchAlgorithmException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

}