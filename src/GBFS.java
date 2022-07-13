import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class GBFS {

    public static void search(State initialState){

        PriorityQueue<State> frontier = new PriorityQueue<>(new GBFSStateComparator());

        Hashtable<String, Boolean> inFrontier = new Hashtable<>();
        Hashtable<String, Boolean> explored = new Hashtable<>();

        frontier.add(initialState);
        inFrontier.put(initialState.hash(),true);

        while (!frontier.isEmpty()){

            //LOWEST path cost in frontier
            State tempState = frontier.poll();

            if(isGoal(tempState)){
                result(tempState);
                return;
            }

            inFrontier.remove(tempState.hash());
            explored.put(tempState.hash(),true);

            ArrayList<State> children = tempState.successor();

            for(int i = children.size()-1;i>=0;i--){
                if(!(inFrontier.containsKey(children.get(i).hash()))
                        && !(explored.containsKey(children.get(i).hash()))) {
                    frontier.add(children.get(i));
                    inFrontier.put(children.get(i).hash(), true);
                }
            }
        }
    }

    private static boolean isGoal(State state){
        for (int i = 0; i < state.getGraph().size(); i++) {
            if(state.getGraph().getNode(i).getColor() == Color.Red
                    || state.getGraph().getNode(i).getColor() == Color.Black){
                return false;
            }
        }
        return true;
    }

    private static void result(State state){
        Stack<State>  states = new Stack<State>();
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
            FileWriter myWriter = new FileWriter("GbfsResult.txt");
            System.out.println("initial state : ");
            while (!states.empty()){
                State tempState = states.pop();
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


//BETTER ---> LOWER
//MORE OPTIMISTIC ---> LOWER
class GBFSStateComparator implements Comparator<State> {

    @Override
    public int compare(State o1, State o2) {
        //if o1 is more optimistic       -> it should be polled down of frontier faster
        // -> it should have lower value -> o1 < o2

        //so if o1 is more optimistic than o2 --> o1<o2 --> return -1

        //o1 is more optimistic == has lower  h(n) == o1 < o2
        // h(n) = number of blacks

        if(eval(o1) < eval(o2)) return -1;
        else if(eval(o1) == eval(o2)) return 0;
        else return +1;
    }

    public static int eval(State state) {
        return heuristic(state);
    }

    // simply # of blacks
    public static int heuristic(State state) {
        int numBlack = 0;
        for(int i = 0; i < state.getGraph().size(); i++) {
            if(state.getGraph().getNode(i).getColor()==Color.Black) numBlack++;
        }
        return numBlack;
    }
}
