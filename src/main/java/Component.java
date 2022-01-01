import java.util.HashMap;
import java.util.Map;

public class Component {
    private String type;
    private String id;
    private Map<String, Integer> properity;
    private HashMap<String,String> netlist;

    public Component(String type, String id, Map<String, Integer> resistance, HashMap<String, String> netlist) {
        this.type = type;
        this.id = id;
        this.properity = resistance;
        this.netlist = netlist;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }




    public HashMap<String, String> getNetlist() {
        return netlist;
    }

    public void setNetlist(HashMap<String, String> netlist) {
        this.netlist = netlist;
    }

    public Map<String, Integer> getProperity() {
        return properity;
    }

    public void setProperity(Map<String, Integer> properity) {
        this.properity = properity;
    }
}
