import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

class RbfsResult {
    public int f_value;
    public boolean isFailure;

    public RbfsResult(int f_value, boolean isFailure) {
        this.f_value = f_value;
        this.isFailure = isFailure;
    }
}
public class RBFS {

    public static void search(State initialState){
        RbfsResult res = new RbfsResult((int)Double.POSITIVE_INFINITY, true);

        if(isGoal(initialState)) {
            result(initialState);
            return;
        }
        res = recursive(initialState, (int)Double.POSITIVE_INFINITY);
    }

    public static RbfsResult recursive(State state, int f_limit) {
        if(isGoal(state)) {
            result(state);
            System.exit(-1);
            return new RbfsResult(-1, false);
        }

        ArrayList<State> children = state.successor();
        if(children.isEmpty()) {
            return new RbfsResult((int)Double.POSITIVE_INFINITY, true);
        }

        //update back up value
        for(int i = 0; i < children.size(); i++) {
            children.get(i).f_variable = Math.max(AStarStateComparator.eval(children.get(i)), state.f_variable);
        }

        while(true) {
            //sort to choose best child
            Collections.sort(children, new AStarStateComparator());

            // chose best child
            State currBestChild = children.get(0);
            //
            if(currBestChild.f_variable>f_limit) {
                return new RbfsResult(currBestChild.f_variable, true);
            }
            //if(children.size()>1) {
                int alternative = children.get(1).f_variable;
                RbfsResult bestChildResult = recursive(currBestChild, Math.min(f_limit, alternative));

                currBestChild.f_variable = bestChildResult.f_value;
                if(!bestChildResult.isFailure) {
                    return new RbfsResult(bestChildResult.f_value, bestChildResult.isFailure);
                }
            //}

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
            FileWriter myWriter = new FileWriter("RbfsResult.txt");
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


