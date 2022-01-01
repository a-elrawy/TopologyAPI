#### 
# Topology API



>>an api to access, manage and store device topologies.

## Functionality

1. Read a topology from a given JSON file and store it in the memory.
2. Write a given topology from the memory to a JSON file.
3. Query about which topologies are currently in the memory.
4. Delete a given topology from memory.
5. Query about which devices are in a given topology.
6. Query about which devices are connected to a given netlist node in
a given topology.

## Example

```java
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

```

