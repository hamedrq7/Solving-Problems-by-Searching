
import javax.crypto.spec.PSource;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BIBF {

    public static void search(State initialStateF) {

        Queue<State> frontierF = new LinkedList<>();
        frontierF.add(initialStateF);

        //making the GOAL STATE
        State initialStateB = generateGoalState(initialStateF);
        Queue<State> frontierB = new LinkedList<>();
        frontierB.add(initialStateB);

        Hashtable<String, Boolean> inFrontierF = new Hashtable<>();
        inFrontierF.put(initialStateF.hash(), true);
        Hashtable<String, Boolean> inFrontierB = new Hashtable<>();
        inFrontierB.put(initialStateB.hash(), true);

        //adding states to reached in EXPANSION
        Hashtable<String, State> reachedF = new Hashtable<>();
        reachedF.put(initialStateF.hash(), initialStateF);

        Hashtable<String, State> reachedB = new Hashtable<>();
        reachedB.put(initialStateB.hash(), initialStateB);

        boolean result = false;

        /*
        System.out.println("GGGG backwardSuccessor: ");
        ArrayList<State> temp1 = backwardSuccessor(initialStateB);
        for(int i = 0; i < temp1.size(); i++) {
            System.out.println("hash: " + temp1.get(i).hash());
            System.out.println("-------->: " + temp1.get(i).depth);
        }
        System.out.println("initial backWardSuccessor: ");
        ArrayList<State> temp2 = backwardSuccessor(initialStateF);
        for(int i = 0; i < temp2.size(); i++) {
            System.out.println("hash: " + temp2.get(i).hash());
        }*/



        while(result == false) {
            //System.out.println("forward peek depth: "+frontierF.peek().depth + ", backward peek depth: " + frontierB.peek().depth);
            System.out.println("Forward reached size: "+ reachedF.size() + ", Backward reached size: "  + reachedB.size());
            if(frontierF.peek().depth <= frontierB.peek().depth) {
                //proceed F
                result = proceed("F", frontierF, reachedF, inFrontierF, frontierB, reachedB, inFrontierB);
                System.out.println("FORWARD PROGRESS");
            }
            else {
                //proceed B
                result = proceed("B", frontierF, reachedF, inFrontierF, frontierB, reachedB, inFrontierB);
                System.out.println("BACKWARD PROGRESS");
            }
        }

    }


    public static Boolean proceed(String dir, Queue<State> frontierF, Hashtable<String, State> reachedF,
                               Hashtable<String, Boolean> inFrontierF,
                                Queue<State> frontierB, Hashtable<String, State> reachedB,
                               Hashtable<String, Boolean> inFrontierB){
        Boolean result = false;

        if(dir.equals("F")) {
            State tempState = frontierF.poll();
            inFrontierF.remove(tempState.hash());
            //tempState has been added to reachedF before(when? when it expanded from its parent)

            ArrayList<State> children = tempState.successor();
            for(int i = 0; i < children.size(); i++) {

                // check redundant state
                if(!reachedF.containsKey(children.get(i).hash())) {
                    frontierF.add(children.get(i));
                    inFrontierF.put(children.get(i).hash(), true);

                    //***
                    reachedF.put(children.get(i).hash(), children.get(i));

                    ///JOIN NODE
                    //*******
                    if(inFrontierB.containsKey(children.get(i).hash())) {
                        State sameStateInB = reachedB.get(children.get(i).hash());

                        //***************************************************************************
                        //push left selected node id
                        State tempIterator = sameStateInB;
                        int selectedNodeIdTemp = sameStateInB.getSelectedNodeId();
                        while (tempIterator!=null) {
                            if(tempIterator.getParentState()!=null) {
                                int tempInt = tempIterator.getParentState().getSelectedNodeId();
                                tempIterator.getParentState().setSelectedNodeId(selectedNodeIdTemp);
                                selectedNodeIdTemp = tempInt;
                            }
                            tempIterator=tempIterator.getParentState();
                        }


                        customResult(sameStateInB, "backward");
                        customResult(children.get(i), "forward");


                        State currStateInBackward = sameStateInB.getParentState();
                        State currStateInForward = children.get(i);

                        //System.out.println("Redundant State in FORWARDS selectedNodeId: "+currStateInForward.getSelectedNodeId());
                        //System.out.println("Redundant State in BACKWARDS selectedNodeId: "+sameStateInB.getSelectedNodeId());

                        while (currStateInBackward!=null) {
                            State temp0 = currStateInBackward.getParentState();
                            currStateInBackward.setParentState(currStateInForward);

                            currStateInForward = currStateInBackward;
                            currStateInBackward = temp0;
                        }

                        result(currStateInForward);
                        result = true;
                        return result;
                    }
                }
                //add later maybe?
                //else if(PATH-COST(child) < PATH-COST(reached[s]))
            }

        }
        else if(dir.equals("B")) {
            State tempState = frontierB.poll();
            inFrontierB.remove(tempState.hash());

            ArrayList<State> children = backwardSuccessor(tempState);

            for(int i = 0; i < children.size(); i++) {

                if(!reachedB.containsKey(children.get(i).hash())) {
                    frontierB.add(children.get(i));
                    inFrontierB.put(children.get(i).hash(), true);

                    //***
                    reachedB.put(children.get(i).hash(), children.get(i));

                    ///JOIN NODE
                    //*******
                    if(inFrontierF.containsKey(children.get(i).hash())) {
                        State sameStateInF = reachedF.get(children.get(i).hash());
                        //sameStateInB is children.get(i)


                        //***************************************************************************
                        //push left selected node id
                        State tempIterator = children.get(i);
                        int selectedNodeIdTemp = tempIterator.getSelectedNodeId();
                        while (tempIterator!=null) {
                            if(tempIterator.getParentState()!=null) {
                                int tempInt = tempIterator.getParentState().getSelectedNodeId();
                                tempIterator.getParentState().setSelectedNodeId(selectedNodeIdTemp);
                                selectedNodeIdTemp = tempInt;
                            }
                            tempIterator=tempIterator.getParentState();
                        }

                        customResult(sameStateInF, "forward");
                        customResult(children.get(i), "backward");

                        //JOIN NODE
                        State currStateInForward = sameStateInF;
                        State currStateInBackward = children.get(i).getParentState();

                        //System.out.println("sameStateInF snid: "+sameStateInF.getSelectedNodeId());
                        //System.out.println("currStateInBackward snid: "+children.get(i).getSelectedNodeId());

                        while (currStateInBackward!=null) {
                            State temp0 = currStateInBackward.getParentState();
                            currStateInBackward.setParentState(currStateInForward);

                            currStateInForward = currStateInBackward;
                            currStateInBackward = temp0;
                        }

                        result(currStateInForward);
                        result = true;
                        return result;
                    }
                }
                //add later maybe?
                //else if(PATH-COST(child) < PATH-COST(reached[s]))
            }
        }

        return result;
    }


    //gets the Previous Selected Node's state(psn), and generates all possible(valid) backward children it could had
    public static ArrayList<State> generateBackwardChildren(State psn) {
        ArrayList<State> backChildren = new ArrayList<>();
        if(psn.getGraph().getNode(psn.getSelectedNodeId()).getColor()==Color.Green) {

            //--------------------------------
            //////green
            //////impossible
            //State psnWasGreen = new State(psn.getParentState().getGraph().copy(), psn.getSelectedNodeId(), psn.getParentState());

            //--------------------------------
            //////red
            //////always possible
            //change psn color to red
            State psnWasRed = new State(psn.getParentState().getGraph().copy(), psn.getSelectedNodeId(), psn.getParentState());
            psnWasRed.getGraph().getNode(psnWasRed.getSelectedNodeId()).changeColorTo(Color.Red);

            //reverseColor psn neighbours
            LinkedList<Integer> nodeNeighbors = psnWasRed.getGraph().getNode(psnWasRed.getSelectedNodeId()).getNeighborsIds();
            for(int j = 0; j < nodeNeighbors.size(); j++) {
                int neighbourId = nodeNeighbors.get(j);
                psnWasRed.getGraph().getNode(neighbourId).reverseNodeColor();
            }
            backChildren.add(psnWasRed);

            //---------------------------------
            //black
            //only possible if neighbours Green count is greater than other colors (rightNow, not after reverse, because we are going backwards)
            State psnWasBlack = new State(psn.getParentState().getGraph().copy(), psn.getSelectedNodeId(), psn.getParentState());

            int greenNeighborsCount=0;
            int redNeighborsCount=0;
            int blackNeighborCount=0;
            for (int j = 0; j < nodeNeighbors.size(); j++) {
                int neighborId = nodeNeighbors.get(j);
                if(psnWasBlack.getGraph().getNode(neighborId).getColor()==Color.Green) {
                    greenNeighborsCount++;
                }
                else if (psnWasBlack.getGraph().getNode(neighborId).getColor()==Color.Red) {
                    redNeighborsCount++;
                }
                else blackNeighborCount++;
            }
            if(greenNeighborsCount > redNeighborsCount && greenNeighborsCount > blackNeighborCount){
                //change color of psnWasBlack
                psnWasBlack.getGraph().getNode(psnWasBlack.getSelectedNodeId()).changeColorTo(Color.Black);

                //reverse color of neighbours
                for(int j = 0; j < nodeNeighbors.size(); j++) {
                    int neighbourId = nodeNeighbors.get(j);
                    psnWasBlack.getGraph().getNode(neighbourId).reverseNodeColor();
                }

                backChildren.add(psnWasBlack);
            }

        }
        else if(psn.getGraph().getNode(psn.getSelectedNodeId()).getColor()==Color.Red) {
            //--------------------------------
            //////green
            //////always possible
            State psnWasGreen = new State(psn.getParentState().getGraph().copy(), psn.getSelectedNodeId(), psn.getParentState());

            //change psn color to green
            psnWasGreen.getGraph().getNode(psnWasGreen.getSelectedNodeId()).changeColorTo(Color.Green);

            //reverseColor psn neighbours
            LinkedList<Integer> nodeNeighbors = psnWasGreen.getGraph().getNode(psnWasGreen.getSelectedNodeId()).getNeighborsIds();
            for(int j = 0; j < nodeNeighbors.size(); j++) {
                int neighbourId = nodeNeighbors.get(j);
                psnWasGreen.getGraph().getNode(neighbourId).reverseNodeColor();
            }
            backChildren.add(psnWasGreen);

            //--------------------------------
            //////red
            //////impossible

            //--------------------------------
            ///////black
            //only possible if neighbours Red count is greater than other colors (rightNow, not after reverse, because we are going backwards)
            State psnWasBlack = new State(psn.getParentState().getGraph().copy(), psn.getSelectedNodeId(), psn.getParentState());

            int greenNeighborsCount=0;
            int redNeighborsCount=0;
            int blackNeighborCount=0;
            for (int j = 0; j < nodeNeighbors.size(); j++) {
                int neighborId = nodeNeighbors.get(j);
                if(psnWasBlack.getGraph().getNode(neighborId).getColor()==Color.Green) {
                    greenNeighborsCount++;
                }
                else if (psnWasBlack.getGraph().getNode(neighborId).getColor()==Color.Red) {
                    redNeighborsCount++;
                }
                else blackNeighborCount++;
            }
            if(redNeighborsCount > greenNeighborsCount && redNeighborsCount > blackNeighborCount){
                //change color of psnWasBlack
                psnWasBlack.getGraph().getNode(psnWasBlack.getSelectedNodeId()).changeColorTo(Color.Black);

                //reverse color of neighbours
                for(int j = 0; j < nodeNeighbors.size(); j++) {
                    int neighbourId = nodeNeighbors.get(j);
                    psnWasBlack.getGraph().getNode(neighbourId).reverseNodeColor();
                }

                backChildren.add(psnWasBlack);
            }
        }
        else {
            //psn color is black:

            //psn has selected and is black rn
            //only way possible, is when
            //1- red count == green count
            //2- black count > green/red count

            //----------------------------
            //////red
            //////impossible

            //----------------------------
            //////green
            //////impossible

            //----------------------------
            //////black
            //////only possible when
            //1- red count == green count
            //2- black count > green/red count
            //(counts after the changed, which means rightNow for backwards
            State psnWasBlack = new State(psn.getParentState().getGraph().copy(), psn.getSelectedNodeId(), psn.getParentState());

            LinkedList<Integer> nodeNeighbors = psnWasBlack.getGraph().getNode(psnWasBlack.getSelectedNodeId()).getNeighborsIds();
            int greenNeighborsCount=0;
            int redNeighborsCount=0;
            int blackNeighborCount=0;
            for (int j = 0; j < nodeNeighbors.size(); j++) {
                int neighborId = nodeNeighbors.get(j);
                if(psnWasBlack.getGraph().getNode(neighborId).getColor()==Color.Green) {
                    greenNeighborsCount++;
                }
                else if (psnWasBlack.getGraph().getNode(neighborId).getColor()==Color.Red) {
                    redNeighborsCount++;
                }
                else blackNeighborCount++;
            }
            if(redNeighborsCount == greenNeighborsCount ||
                    (blackNeighborCount > greenNeighborsCount && blackNeighborCount > redNeighborsCount)) {

                //reverse the color of neighbours
                for(int j = 0; j < nodeNeighbors.size(); j++) {
                    int neighbourId = nodeNeighbors.get(j);
                    psnWasBlack.getGraph().getNode(neighbourId).reverseNodeColor();
                }

                backChildren.add(psnWasBlack);
            }

        }

        return backChildren;
    }

    public static ArrayList<State> backwardSuccessor(State parentState) {
        ArrayList<State> children= new ArrayList<State>();

        for (int i = 0; i <  parentState.getGraph().size(); i++) {
            int nodeId=  parentState.getGraph().getNode(i).getId();

            if(true){

                //check all three colors for newState
                State newState = new State(parentState.getGraph().copy(), nodeId,  parentState);
                newState.depth =  parentState.depth+1;

                //////PREVIOUSLY SELECTED NODE (PSN) == newState
                //children.addAll(generateBackwardChildren(newState));
                for(State child : generateBackwardChildren(newState)) {
                    if(!child.hash().equals(child.getParentState().hash())) children.add(child);
                }
            }
        }
        return children;
    }


    public static boolean terminated(boolean result, Queue<State> frontierF, Queue<State> frontierB) {
        boolean terminate = false;

        return terminate;
    }


    public static State generateGoalState(State initialState) {
        State goalState = new State(initialState.getGraph(), -2, null);
        for(int i = 0; i < goalState.getGraph().size(); i++) {
            goalState.getGraph().getNode(i).changeColorTo(Color.Green);
        }
        return goalState;
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


    private static void customResult(State state, String name) {
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
            FileWriter myWriter = new FileWriter(name+".txt");
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
            FileWriter myWriter = new FileWriter("BibfResult.txt");
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


