import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class DFS {

    //make the for loop recursive, it will work
    public static void graph_search(State initialState){
        Hashtable<String, Boolean> currentTree = new Hashtable<>();
        if(isGoal(initialState)) {
            result(initialState);
            return;
        }
        recursive(initialState, currentTree);
    }

    public static boolean recursive(State state, Hashtable<String, Boolean> currentTree) {

        //if(state.depth%10==0 ) System.out.println(currentTree.size()+", "+state.depth);


        currentTree.put(state.hash(), true);

        ArrayList<State> children = state.successor();
        //if(state.depth==59) System.out.println(children.size());
        for(int i = 0; i < children.size(); i++) {

            // CHECK FOR REDUNDANT PATH(STATE)
            if(!(currentTree.containsKey(children.get(i).hash()))) {
                ////check
                if (isGoal(children.get(i))) {
                    result(children.get(i));
                    System.out.println("currentTree size: "+currentTree.size());
                    return true;
                }

                boolean foundGoal = recursive(children.get(i), currentTree);
                if(foundGoal) return true;
            } else {
                System.out.println(children.get(i).depth+" <--- depth of redundant child");
            }
        }

        currentTree.remove(state.hash());

        return false;
    }

    // works with the cap on the depth
    public static void tree_search(State initialState){
        Stack<State> frontier = new Stack<State>();

        if(isGoal(initialState)){
            result(initialState);
            return;
        }
        frontier.add(initialState);

        while (!frontier.isEmpty()){
            State tempState = frontier.pop();
            if(tempState.depth%100000==0) System.out.println("-->"+tempState.depth);
            ArrayList<State> children = tempState.successor();
            for(int i = 0;i<children.size();i++){
                if (isGoal(children.get(i))) {
                    result(children.get(i));
                    return;
                }

                // cap limit, dont expand if its a redundant state
                if(!isRedundant(children.get(i)) && children.get(i).depth<400) {
                    frontier.push(children.get(i));
                } else{
                    //System.out.println("Redundant Path detected\n");
                }
            }
        }
    }

    // follows up the chain of parents, to see if state is redundant or not ( cap for chaining up )
    public static boolean isRedundant(State state) {
        boolean result = false;
        State parent = state.getParentState();
        //System.out.println("CHECKING... ("+state.hash()+")");
        int limit = 10;
        int depth = 0;
        while(parent!=null&&depth<limit) {
            if(parent.hash().equals(state.hash())) {
                //System.out.println("Parent of redundant: " + parent.hash());
                result=true;
                break;
            }
            parent = parent.getParentState();
            depth++;
        }
        return result;
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
            FileWriter myWriter = new FileWriter("DfsResult.txt");
            System.out.println("initial state : ");
            while (!states.empty()){
                State tempState = states.pop();
                if(tempState.getSelectedNodeId() != -1) {
                    //System.out.println("selected id : " + tempState.getSelectedNodeId());
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


