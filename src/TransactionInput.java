public class TransactionInput {
    public int inCounter; // ask the teacher why is it used
    public String transactionOutputId; //suppose that it refers to TransactionOutputs -> transactionId. Because validations sucks
        //to change to previous tindex
    public TransactionOutput UTXO; //Contains the Unspent transaction output
     //to change to



    public TransactionInput(String transactionOutputId) {

        this.transactionOutputId = transactionOutputId;

    }

}
