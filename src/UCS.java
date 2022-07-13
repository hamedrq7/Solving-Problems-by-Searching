import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class UCS {

    public static void search(State initialState){
        WeightedState WeightedInitialState = new WeightedState(initialState.getGraph(), initialState.getSelectedNodeId(), null);

        PriorityQueue<WeightedState> frontier = new PriorityQueue<>(new WeightedStateComparator());

        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        frontier.add(WeightedInitialState);
        inFrontier.put(WeightedInitialState.hash(),true);

        while (!frontier.isEmpty()){

            //lowest path cost in frontier
            WeightedState tempState = frontier.poll();
            //System.out.println("Printing tempState cost: "+tempState.getPathCost());
            if(isGoal(tempState)){
                System.out.println("GOAL STATE COST: " + tempState.getPathCost());
                result(tempState);
                return;
            }

            inFrontier.remove(tempState.hash());
            explored.put(tempState.hash(),true);

            //update pathCost of children
            ArrayList<WeightedState> children = tempState.successor();

            for(int i = children.size()-1;i>=0;i--){
                if(!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    frontier.add(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }
    }

    private static boolean isGoal(WeightedState state){
        for (int i = 0; i < state.getGraph().size(); i++) {
            if(state.getGraph().getNode(i).getColor() == Color.Red
                    || state.getGraph().getNode(i).getColor() == Color.Black){
                return false;
            }
        }
        return true;
    }

    private static void result(WeightedState state){
        Stack<WeightedState>  states = new Stack<WeightedState>();
        while (true){
            states.push(state);
            if(state.getParentState() == null){
                break;
            }
            else {
                state = state.getParentState();
            }
        }
        int ans = states.size();
        try {
            FileWriter myWriter = new FileWriter("UcsResult.txt");
            System.out.println("initial state : ");
            while (!states.empty()){
                WeightedState tempState = states.pop();
                if(tempState.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + tempState.getSelectedNodeId());
                }
                tempState.getGraph().print();

                myWriter.write(tempState.getSelectedNodeId()+" ,");
                myWriter.write(tempState.outputGenerator()+"\n");
            }
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
            System.out.println("SIZE: "+ ans);

        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}


class WeightedStateComparator implements Comparator<WeightedState> {

    @Override
    public int compare(WeightedState o1, WeightedState o2) {
        if(o1.getPathCost() < o2.getPathCost()) return -1;
        else if(o1.getPathCost() == o2.getPathCost()) return 0;
        else return +1;
    }
}