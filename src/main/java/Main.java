public class Main {
    public static void main(String[] args)  {
        TopologyAPI api = new TopologyAPI();
        // Read JSON
        if(api.readJSON("topology.json")==0) {
            System.out.println("Couldn't Open the file");
            return;
        }
        // Query Devices
        Component[] Devices =api.queryDevices("top1");
        for (Component c : Devices)
            System.out.println(c.getId()+": "+c.getType());

        //Write Topology
        api.writeJSON("top1");

        //Query Devices With Netlist Node
        Devices= api.queryDevicesWithNetlistNode("top1","n1");
        for (Component c : Devices)
            System.out.println(c.getId()+": "+c.getType());

        //Delete Topology
        api.DeleteTopology("top1");
        System.out.println(api.getTopologies().size());

    }
}


