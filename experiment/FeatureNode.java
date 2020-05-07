package experiment;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FeatureNode {
    private String name;
    private List<String> children;

    FeatureNode(String _name) {
        this.name = _name;
        this.children = new ArrayList<String>();
    }

    public void _addChildName(String childName) { this.children.add(childName); }

    public void printChildren() {
        List<String> ns = this.children;
        System.out.println("Father: " + this.name);
        System.out.print("Children: ");
        for (String child : ns) { System.out.print(child + " "); }
        System.out.println();
    }
}
