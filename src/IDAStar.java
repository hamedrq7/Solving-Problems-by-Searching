
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Stack;

public class IDAStar {

    /*
Iterative-deepening A search (IDA) is to A what iterative-deepening search is to Iterative-deepening
A search
depth-first: IDA gives us the benefits of A without the requirement to keep all reached
states in memory, at a cost of visiting some states multiple times. It is a very important and
commonly used algorithm for problems that do not fit in memory.
In standard iterative deepening the cutoff is the depth, which is increased by one each
iteration.

In IDA the cutoff is the f -cost (g+h); at each iteration, the cutoff value is the
smallest f -cost of any node that exceeded the cutoff on the previous iteration.

In other words, each iteration exhaustively searches an f -contour, finds a node just beyond that contour, and
uses that node’s f -cost as the next contour.
 */

    public static void search(State initialState) {
        Result res = new Result("failure");
        System.out.println(res);

        while(true) {

            tree_search(initialState, res);
            if(!res.isCutOff()) {
                return;
            }
            System.out.println(res);

            //what if f-cost is same?
            // what if h() returned 0?
            res.setCurrCutOff(res.getNextCutOff());
        }
    }

    //works very slow
    public static void tree_search(State initialState, Result res){

        Hashtable<String, Boolean> currentTree = new Hashtable<>();

        if(isGoal(initialState)) {
            result(initialState);
            res.setNode(true);
            return;
        }

        // nextCutOff off should be updated to this point
        recursive(initialState, currentTree, res);

        return;
    }


    public static void recursive(State state, Hashtable<String, Boolean> currentTree, Result res) {

        if(AStarStateComparator.eval(state)>res.getCurrCutOff()) {
            //do not put state in currentTree if its above limit
            if(res.getNextCutOff() > AStarStateComparator.eval(state)) res.setNextCutOff(AStarStateComparator.eval(state));
            res.setCutOff(true);
            return;
        }

        currentTree.put(state.hash(), true);

        ArrayList<State> children = state.successor();

        for(int i = 0; i < children.size(); i++) {

            if(!(currentTree.containsKey(children.get(i).hash()))) {

                if (isGoal(children.get(i))) {
                    result(children.get(i));
                    System.out.println("currentTree size: "+currentTree.size());
                    res.setNode(true);
                    return;
                }
                recursive(children.get(i), currentTree, res);
                if(res.isNode()) {
                    return;
                }
            }
        }
        currentTree.remove(state.hash());

        return;
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
            FileWriter myWriter = new FileWriter("IdastarResult.txt");
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

class Result {
    private boolean isCutOff;
    private boolean isFailure;
    private boolean isNode;

    private int currCutOff;
    private int nextCutOff;


    public Result(String status) {
        if(status.equals("node")) {
            isCutOff = false;
            isNode = true;
            isFailure = false;
        }
        else if(status.equals("failure")) {
            isFailure = true;
            isNode = false;
            isCutOff = false;
        }
        else if(status.equals("cutoff")) {
            isNode = false;
            isCutOff = true;
            isFailure = false;
        }
        else {
            System.out.println("ERROR RESULT IDAStar CLASS");
        }

        currCutOff = 0;
        nextCutOff = (int) Double.POSITIVE_INFINITY;


    }
    @Override
    public String toString() {
        String s = "";
        if(isCutOff) s+= "cutOff" ;
        else if(isNode) s+= "node ";
        else if(isFailure) s+= "failure ";
        else s+= "EEEEEEEEEEEEROR";
        s += ", currCutOff: "+this.currCutOff+"\t";
        s += ", nextCutOff: "+this.nextCutOff+"\n";
        return s;
    }

    public int getCurrCutOff() {
        return currCutOff;
    }

    public void setCurrCutOff(int currCutOff) {
        this.currCutOff = currCutOff;
        this.nextCutOff = (int) Double.POSITIVE_INFINITY;
    }

    public int getNextCutOff() {
        return nextCutOff;
    }

    public void setNextCutOff(int nextCutOff) {
        this.nextCutOff = nextCutOff;
    }

    public void setCutOff(boolean cutOff) {
        isCutOff = cutOff;
        isNode = false;
        isFailure = false;
    }

    public void setFailure(boolean failure) {
        isFailure = failure;
        isNode = false;
        isCutOff = false;
    }

    public boolean isCutOff() {
        return isCutOff;
    }

    public boolean isFailure() {
        return isFailure;
    }

    public boolean isNode() {
        return isNode;
    }

    public void setNode(boolean node) {
        isNode = node;
        isCutOff = false;
        isFailure = false;
    }

}


