import jade.Boot;
public class Main {

    public static void main(String[] args) {
        String[] commande = new String[3];
        String argument = "";
        argument = argument + "n0:Node(ini,n1,n2,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n1:Node(slave,n2,n0,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n2:Node(slave,n1,n0,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n3:Node(slave,n1,n0,n2,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n4:Node(slave,n1,n0,n3,n2,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n5:Node(slave,n1,n0,n2,n4,n3,n6,n7,n8,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n6:Node(slave,n1,n0,n3,n4,n5,n2,n7,n8,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n7:Node(slave,n1,n0,n2,n4,n5,n6,n3,n8,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n8:Node(ini,n1,n2,n3,n4,n5,n6,n7,n0,n9,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n9:Node(slave,n2,n0,n3,n4,n5,n6,n7,n8,n1,n10,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n10:Node(slave,n1,n0,n3,n4,n5,n6,n7,n8,n9,n2,n11,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n11:Node(slave,n1,n0,n2,n4,n5,n6,n7,n8,n9,n10,n3,n12,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n12:Node(slave,n1,n0,n3,n4,n5,n6,n7,n8,n9,n10,n11,n2,n13,n14,n15)";
        argument = argument + ";";
        argument = argument + "n13:Node(slave,n1,n0,n2,n4,n5,n6,n7,n8,n9,n10,n11,n12,n3,n14,n15)";
        argument = argument + ";";
        argument = argument + "n14:Node(slave,n1,n0,n3,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n2,n15)";
        argument = argument + ";";
        argument = argument + "n15:Node(slave,n1,n0,n2,n4,n5,n6,n7,n8,n9,n10,n11,n12,n13,n14,n3)";


        commande[0] = "-cp";
        commande[1] = "jade.boot";
        commande[2] = argument;
        Boot.main(commande);
    }
}
