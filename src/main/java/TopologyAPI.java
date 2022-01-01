import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class TopologyAPI {
    private ArrayList<Topology> topologies;

    public TopologyAPI(){
        topologies = new ArrayList<>();
    }
    public TopologyAPI(ArrayList<Topology> topologies) {
        this.topologies = topologies;
    }

    // Read Jsonfile and store the result in topologies arraylist
    public int readJSON(String filename) {
        try {
            JsonReader reader = new JsonReader(new FileReader(filename));
            Gson gson = new Gson();
            JsonArray ja = gson.fromJson(reader, JsonArray.class);
            ArrayList<Topology> topologies= new ArrayList<>();
            for (int i = 0; i < ja.size(); i++) {
                topologies.add(parse_topology(ja.get(i)));
            }
            this.topologies = topologies;
            return 1;
        }catch (Exception e){
            try {
                this.topologies.add(parse_topology(filename));
                return 1;
            }catch (Exception e2){
                return 0;
            }
        }
    }
    // Write the selected topology in JSON file
    public int writeJSON(String TopologyID){
        Gson gson = new Gson();
        int id = searchTopology(TopologyID);
        if (id == -1) return 0;
        try (FileWriter writer = new FileWriter(TopologyID+".json")) {
            gson.toJson(topologies.get(id), writer);
            return 1;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
    // Delete the selected topology
    public int DeleteTopology(String TopologyID){
        int id = searchTopology(TopologyID);
        if (id == -1) return 0;
        topologies.remove(id);
        return 1;
    }
    //Query all the connected devices to the topology
    public Component[] queryDevices(String TopologyID){
        int id = searchTopology(TopologyID);
        if (id == -1) return null;
        return topologies.get(id).getComponents();
    }
    //Query all the connected devices to the topology with the specified netlist node
    public Component[] queryDevicesWithNetlistNode(String TopologyID, String NetlistNodeID){
        int id = searchTopology(TopologyID);
        if (id == -1) return null;
        Topology topology = topologies.get(id);
        Component[] components = new Component[topology.getComponents().length];int counter=0;
        for (Component component: topology.getComponents()){
            for (Map.Entry<String, String> entry : component.getNetlist().entrySet()) {
               if (Objects.equals(entry.getValue(), NetlistNodeID))
                   components[counter++]=component;
            }
        }
        return components;
    }
    public int searchTopology(String TopologyID){
        for (int i = 0; i < topologies.size(); i++) {
            if (Objects.equals(topologies.get(i).getId(), TopologyID))
                return i;
        }
        return -1;
    }
    public static Topology parse_topology(JsonElement obj){
        Gson gson = new Gson();
        Topology topology = gson.fromJson(obj,Topology.class);
        for (int i = 0; i < topology.getComponents().length; i++) {
            if (topology.getComponents()[i].getProperity() == null){
                Map<?, ?> map = gson.fromJson(obj, Map.class);// Whole JSON File
                Map.Entry<?, ?> components = (Map.Entry<?, ?>) map.entrySet().toArray()[1];// Components
                Map<?, ?>  entry = (Map<?, ?> ) ((ArrayList<?>)(components.getValue())).get(i);// Components Fields
                Map.Entry<?, ?>  entry1 = (Map.Entry<?, ?>) entry.entrySet().toArray()[2]; // Values of property
                entry = (Map<?, ?>) entry1.getValue();
                topology.getComponents()[i].setProperity((Map<String, Integer>) entry); // Assign the missing values;
            }
        }
        return topology;
    }
    public static Topology parse_topology(String filename) throws FileNotFoundException {
        JsonReader reader = new JsonReader(new FileReader(filename));
        Gson gson = new Gson();
        JsonElement obj = gson.fromJson(reader,JsonElement.class);
        return parse_topology(obj);
    }


    public ArrayList<Topology> getTopologies() {
        return topologies;
    }

    public void setTopologies(ArrayList<Topology> topologies) {
        this.topologies = topologies;
    }
}
