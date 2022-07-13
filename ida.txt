import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class IDS {

    public static int temp = 0;

    public static void search(State initialState) {
        System.out.println("IDS...");

        int depth = 0;
        IDSResult result;
        while(true) {
            temp = 0;
            System.out.print("currDepth: "+ depth + "\t");
            result = tree_search(initialState, depth);
            System.out.println(result);
            if(result!= IDSResult.cutOff) {
                return;
            }
            depth++;
        }
    }


    /*
   Iterative deepening and depth-limited tree-like search. Iterative deepening repeatedly
applies depth-limited search with increasing limits. It returns one of three different
types of values: either a solution node; or failure, when it has exhausted all nodes and proved
there is no solution at any depth; or cutoff , to mean there might be a solution at a deeper depth
than `. This is a tree-like search algorithm that does not keep track of reached states, and thus
uses much less memory than best-first search, but runs the risk of visiting the same state multiple
times on different paths. Also, if the IS-CYCLE check does not check all cycles, then
the algorithm may get caught in a loop.
     */

    public static IDSResult graph_search(State initialState, int limit){
        Stack<State> frontier = new Stack<State>();
        frontier.add(initialState);

        Hashtable<String, Boolean> explored = new Hashtable<>();

        IDSResult result = IDSResult.failure;

        while (!frontier.isEmpty()){
            State tempState = frontier.pop();

            if(isGoal(tempState)) {
                result(tempState);
                result = IDSResult.Node;
                return result;
            }

            if(tempState.depth>limit) {
                result = IDSResult.cutOff;
            }
            else {
                explored.put(tempState.hash(),true);
                ArrayList<State> children = tempState.successor();

                for(int i = 0;i<children.size();i++){
                    if(!explored.containsKey(children.get(i).hash())) {
                        frontier.push(children.get(i));
                    }
                }
            }
        }
        return result;
    }


    //works very slowly
    public static IDSResult tree_search(State initialState, int limit){
        //Stack<State> frontier = new Stack<State>();
        Hashtable<String, Boolean> currentTree = new Hashtable<>();
        IDSResult result = IDSResult.failure;

        if(isGoal(initialState)) {
            result(initialState);
            result = IDSResult.Node;
            return result;
        }

        //frontier.add(initialState);

        result = recursive(initialState, currentTree, limit);

        return result;
    }


    public static IDSResult recursive(State state, Hashtable<String, Boolean> currentTree, int limit) {
        //System.out.println(currentTree.size());
        //State tempState = frontier.pop();

        IDSResult result0 = IDSResult.failure;

        if(state.depth>limit) {
            //do not put state in currentTree if its above limit
            return IDSResult.cutOff;
        }

        currentTree.put(state.hash(), true);

        ArrayList<State> children = state.successor();

        for(int i = 0; i < children.size(); i++) {

            // redundant state
            if(!(currentTree.containsKey(children.get(i).hash()))) {
                if (isGoal(children.get(i))) {
                    result(children.get(i));
                    System.out.println("currentTree size: "+currentTree.size());
                    return IDSResult.Node;
                }
                //frontier.push(children.get(i));
                result0 = recursive(children.get(i), currentTree, limit);
                if(result0== IDSResult.Node) return result0;
            }
        }

        currentTree.remove(state.hash());


        return result0;
    }


    public static boolean isRedundant(State state) {
        boolean result = false;
        State parent = state.getParentState();
        //System.out.println("CHECKING... ("+state.hash()+")");
        int limit = 5;
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
            FileWriter myWriter = new FileWriter("IdsResult.txt");
            System.out.println("initial state : ");
            while (!states.empty()){
                State tempState = states.pop();
                if(tempState.getSelectedNodeId() != -1) {
                    System.out.println("selected id : " + tempState.getSelectedNodeId()
                                        + "\t depth of current state: "+tempState.depth);
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


enum IDSResult {
    failure {
        @Override
        public String toString() {
            return "failure";
        }
    },
    cutOff {
        @Override
        public String toString() {
            return "cutOff";
        }
    },
    Node {
        @Override
        public String toString() {
            return "Found Node";
        }
    };
}
