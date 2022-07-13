import java.util.ArrayList;
import java.util.LinkedList;

public class State {

    private Graph graph;
    private int selectedNodeId;
    private State parentState;

    //
    public int depth;
    public int f_variable;
    //

    public State(Graph graph, int selectedNodeId, State parentState){
        this.graph= graph.copy();
        this.selectedNodeId= selectedNodeId;
        if(parentState != null){
            this.parentState= parentState;
            this.depth = parentState.depth+1;
        }else{
            this.parentState = null;
            depth = 0;
        }
    }

    public ArrayList<State> successor(){
        ArrayList<State> children= new ArrayList<State>();

        for (int i = 0; i < this.graph.size(); i++) {
            int nodeId= this.graph.getNode(i).getId();

            boolean isRedundantState = true;
            /*if(parentState!=null) {
                if (nodeId != selectedNodeId || parentState.getGraph().getNode(selectedNodeId).getColor() == Color.Black) {
                    isRedundantState = false;
                }
            }
            else {
                if(nodeId!= selectedNodeId) isRedundantState = false;
            }*/

            // original function, uses above term for if condition
            //if(nodeId != selectedNodeId){
            if(true) {
                State newState = new State(this.graph.copy(), nodeId, this);

                LinkedList<Integer> nodeNeighbors = newState.getGraph().getNode(nodeId).getNeighborsIds();
                for (int j = 0; j < nodeNeighbors.size(); j++) {
                    int neighborId=nodeNeighbors.get(j);
                    newState.getGraph().getNode(neighborId).reverseNodeColor();
                }
                if(newState.getGraph().getNode(nodeId).getColor() == Color.Black){
                    int greenNeighborsCount=0;
                    int redNeighborsCount=0;
                    int blackNeighborCount=0;
                    for (int j = 0; j < nodeNeighbors.size(); j++) {
                        int neighborId=nodeNeighbors.get(j);
                        if(newState.getGraph().getNode(neighborId).getColor()==Color.Green) {
                            greenNeighborsCount++;
                        }
                        else if (newState.getGraph().getNode(neighborId).getColor()==Color.Red) {
                            redNeighborsCount++;
                        }
                        else blackNeighborCount++;
                    }
                    if(greenNeighborsCount > redNeighborsCount && greenNeighborsCount > blackNeighborCount){
                        newState.getGraph().getNode(nodeId).changeColorTo(Color.Green);
                    }else if(redNeighborsCount > greenNeighborsCount && redNeighborsCount > blackNeighborCount){
                        newState.getGraph().getNode(nodeId).changeColorTo(Color.Red);
                    }
                }else {
                    newState.getGraph().getNode(nodeId).reverseNodeColor();
                }
                if(!newState.hash().equals(newState.parentState.hash())) children.add(newState);
                //children.add(newState);
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

    public State getParentState(){
        return parentState;
    }
    public void setParentState(State parentState){
        this.parentState = parentState;
    }

    public  int getSelectedNodeId(){
        return selectedNodeId;
    }
    public  void setSelectedNodeId(int s){
        this.selectedNodeId = s;
    }
}
