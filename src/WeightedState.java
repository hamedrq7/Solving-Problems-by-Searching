import java.util.ArrayList;
import java.util.LinkedList;

public class WeightedState  {

    private Graph graph;
    private int selectedNodeId;
    private WeightedState parentState;

    //
    public int depth;
    private int pathCost;
    //

    public WeightedState(Graph graph, int selectedNodeId, WeightedState parentState){
        this.graph= graph.copy();
        this.selectedNodeId= selectedNodeId;
        if(parentState != null){
            this.parentState= parentState;
        }else{
            this.parentState = null;
        }

        ////////updating path cost
        if(selectedNodeId==-1) this.pathCost = 0;
        else {
            if(parentState != null) this.pathCost = parentState.getPathCost();
            else this.pathCost = 0;

            if(this.getGraph().getNode(selectedNodeId).getColor()==Color.Red) pathCost += 1;
            else if(this.getGraph().getNode(selectedNodeId).getColor()==Color.Black) pathCost += 2;
            else pathCost += 3;
        }


    }

    public ArrayList<WeightedState> successor(){
        ArrayList<WeightedState> children= new ArrayList<>();

        for (int i = 0; i < this.graph.size(); i++) {
            int nodeId= this.graph.getNode(i).getId();

            if(nodeId != this.selectedNodeId){
                WeightedState newState = new WeightedState(this.graph.copy(), nodeId, this);

                //ADDED

                //update depth
                newState.depth = this.depth+1;

                //ADDED

                LinkedList<Integer> nodeNeighbors = newState.graph.getNode(nodeId).getNeighborsIds();
                for (int j = 0; j < nodeNeighbors.size(); j++) {
                    int neighborId=nodeNeighbors.get(j);
                    newState.graph.getNode(neighborId).reverseNodeColor();
                }
                if(newState.graph.getNode(nodeId).getColor() == Color.Black){
                    int greenNeighborsCount=0;
                    int redNeighborsCount=0;
                    int blackNeighborCount=0;
                    for (int j = 0; j < nodeNeighbors.size(); j++) {
                        int neighborId=nodeNeighbors.get(j);
                        if(newState.graph.getNode(neighborId).getColor()==Color.Green) {
                            greenNeighborsCount++;
                        }
                        else if (newState.graph.getNode(neighborId).getColor()==Color.Red) {
                            redNeighborsCount++;
                        }
                        else blackNeighborCount++;
                        /*
                        switch (newState.getGraph().getNode(neighborId).getColor()) {
                            case Green -> greenNeighborsCount++;
                            case Red -> redNeighborsCount++;
                            case Black -> blackNeighborCount++;
                        }*/
                    }
                    if(greenNeighborsCount > redNeighborsCount && greenNeighborsCount > blackNeighborCount){
                        newState.graph.getNode(nodeId).changeColorTo(Color.Green);
                    }else if(redNeighborsCount > greenNeighborsCount && redNeighborsCount > blackNeighborCount){
                        newState.graph.getNode(nodeId).changeColorTo(Color.Red);
                    }
                }else {
                    newState.graph.getNode(nodeId).reverseNodeColor();
                }
                children.add(newState);
            }
        }
        return children;
    }


    public String hash(){
        String result= "";
        for (int i = 0; i < graph.size(); i++) {
            /*
            switch (graph.getNode(i).getColor()) {
                case Green -> result += "g";
                case Red -> result += "r";
                case Black -> result += "b";
            }*/
            if(graph.getNode(i).getColor()==Color.Green) {
                result += "g";
            }
            else if(graph.getNode(i).getColor()==Color.Red) {
                result += "r";
            }
            else {
                result += "b";
            }
        }
        return result;
    }

    public String outputGenerator(){
        String result= "";
        for (int i = 0; i < graph.size(); i++) {
            String color = "";
            if(graph.getNode(i).getColor()==Color.Green) {
                color = "G";
            }
            else if(graph.getNode(i).getColor()==Color.Red) {
                color = "R";
            }
            else {
                color = "B";
            }
            /*
            String color = switch (graph.getNode(i).getColor()) {
                case Red -> "R";
                case Green -> "G";
                case Black -> "B";
            };*/
            result += graph.getNode(i).getId()+color+" ";
            for (int j = 0; j < graph.getNode(i).getNeighborsIds().size(); j++) {
                int neighborId=graph.getNode(i).getNeighborsId(j);
                String neighborColor = "";
                if(graph.getNode(neighborId).getColor()==Color.Green) {
                    neighborColor = "G";
                }
                else if(graph.getNode(neighborId).getColor()==Color.Red) {
                    neighborColor = "R";
                }
                else {
                    neighborColor = "B";
                }
                result += neighborId+neighborColor+" ";
            }
            result += ",";
        }
        return result;
    }

    public Graph getGraph(){
        return graph;
    }

    public WeightedState getParentState(){
        return parentState;
    }

    public  int getSelectedNodeId(){
        return selectedNodeId;
    }

    public int getPathCost() {
        return pathCost;
    }

}
