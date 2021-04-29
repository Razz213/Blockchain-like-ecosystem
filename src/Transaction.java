import java.io.Serializable;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

//import java.security.PrivateKey;
//import java.security.PublicKey;
//import java.util.ArrayList;
//import java.util.List;
//
public class Transaction implements Serializable {

    public String transactionId; //Contains a hash of transaction*
    private int version =1;
    private String timeStamp;
    public PublicKey sender; //Senders address/public key.
    public PublicKey reciepient; //Recipients address/public key.
    public float value; //Contains the amount we wish to send to the recipient.
    public byte[] signature; // besh msr9oukch lol

    public List<TransactionInput> inputs = new ArrayList<TransactionInput>();
    public List<TransactionOutput> outputs = new ArrayList<TransactionOutput>();

    private static int sequence = 0; // zyada..

    //constructor 1
    public Transaction(PublicKey from, PublicKey to, float value,  List<TransactionInput> inputs) {
        this.sender = from;
        this.reciepient = to;
        this.value = value;
        this.inputs = inputs;

    }
    // constructor 2
    public Transaction(){

    }
    // constructor 3
    public Transaction(int value){
        this.value = value;
    }


    public boolean processTransaction() throws NoSuchAlgorithmException {



//        if(verifySignature() == false) {
//
//            System.out.println("#Transaction Signature failed to verify");
//
//            return false;
//
//        }



        //Gathers transaction inputs (Making sure they are unspent)... a revoir !!
        for(TransactionInput i : inputs) {

            i.UTXO = Node.UTXOs.get(i.transactionOutputId);

        }


        // checkk $$$$$

//        if(getInputsValue() < Node.minimumTransaction) {
//            System.out.println("Transaction Inputs too small: " + getInputsValue());
//            System.out.println("Please enter the amount greater than " + Node.minimumTransaction);
//            return false;
//        }



        //Generate transaction outputs:
        float leftOver = getInputsValue() - value; //get value of inputs then the left over change:
        transactionId = calulateHash();
        outputs.add(new TransactionOutput( this.reciepient, value,transactionId)); //send value to recipient
        outputs.add(new TransactionOutput( this.sender, leftOver,transactionId)); //send the left over 'change' back to sender


        //Add outputs to Unspent list

        for(TransactionOutput o : outputs) {
            Node.UTXOs.put(o.id , o);
        }


        //Remove transaction inputs from UTXO lists as spent:
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it
            Node.UTXOs.remove(i.UTXO.id);
        }



        return true;

    }



    public float getInputsValue() {
        float total = 0;
        for(TransactionInput i : inputs) {
            if(i.UTXO == null) continue; //if Transaction can't be found skip it todo ask about it
            total += i.UTXO.value;
        }
        return total;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

//    public void generateSignature(PrivateKey privateKey) {
//        String data = CommonUtils.getStringFromKey(sender) + CommonUtils.getStringFromKey(reciepient) + Float.toString(value)	;
//        signature = CommonUtils.applyECDSASig(privateKey,data);
//    }



//    public boolean verifySignature() {
//        String data = CommonUtils.getStringFromKey(sender) + CommonUtils.getStringFromKey(reciepient) + Float.toString(value)	;
//        return CommonUtils.verifyECDSASig(sender, data, signature);
//
//    }



    public float getOutputsValue() {
        float total = 0;
        for(TransactionOutput o : outputs) {
            total += o.value;
        }
        return total;

    }



    private String calulateHash() throws NoSuchAlgorithmException {

        sequence++; //increase the sequence to avoid 2 identical transactions having the same hash

        return CommonUtils.applySha256(CommonUtils.getStringFromKey(sender) + CommonUtils.getStringFromKey(reciepient) +
                        Float.toString(value) + sequence

        );

    }

}