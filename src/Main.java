public class Main {

    public static void main(String[] args) {


        //-----------------------------------------------> test-1 :
/*
        Graph initialGraph= new Graph(4);
        initialGraph.addNode(new Node(0, Color.Black));
        initialGraph.addNode(new Node(1, Color.Red));
        initialGraph.addNode(new Node(2, Color.Black));
        initialGraph.addNode(new Node(3, Color.Green));
        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(1));
        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(2));
        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(3));
*/

        //-----------------------------------------------> test0 :

        Graph initialGraph0 = new Graph(4);
        initialGraph0.addNode(new Node(0, Color.Black));
        initialGraph0.addNode(new Node(1, Color.Green));
        initialGraph0.addNode(new Node(2, Color.Green));
        initialGraph0.addNode(new Node(3, Color.Green));
        initialGraph0.addLinkBetween(initialGraph0.getNode(0), initialGraph0.getNode(1));
        initialGraph0.addLinkBetween(initialGraph0.getNode(1), initialGraph0.getNode(2));
        initialGraph0.addLinkBetween(initialGraph0.getNode(1), initialGraph0.getNode(3));
        //initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(2));
        //initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(3));
        //initialGraph.addLinkBetween(initialGraph.getNode(3), initialGraph.getNode(4));
        //initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(4));



//        //-----------------------------------------------> test1 :

        Graph initialGraph1 = new Graph(5);
        initialGraph1.addNode(new Node(0, Color.Red));
        initialGraph1.addNode(new Node(1, Color.Red));
        initialGraph1.addNode(new Node(2, Color.Red));
        initialGraph1.addNode(new Node(3, Color.Green));
        initialGraph1.addNode(new Node(4, Color.Red));
        initialGraph1.addLinkBetween(initialGraph1.getNode(0), initialGraph1.getNode(1));
        initialGraph1.addLinkBetween(initialGraph1.getNode(0), initialGraph1.getNode(2));
        initialGraph1.addLinkBetween(initialGraph1.getNode(0), initialGraph1.getNode(3));
        initialGraph1.addLinkBetween(initialGraph1.getNode(1), initialGraph1.getNode(2));
        initialGraph1.addLinkBetween(initialGraph1.getNode(3), initialGraph1.getNode(4));

        //-----------------------------------------------> test2 :

        Graph initialGraph2= new Graph(7);
        initialGraph2.addNode(new Node(0, Color.Red));
        initialGraph2.addNode(new Node(1, Color.Black));
        initialGraph2.addNode(new Node(2, Color.Green));
        initialGraph2.addNode(new Node(3, Color.Red));
        initialGraph2.addNode(new Node(4, Color.Red));
        initialGraph2.addNode(new Node(5, Color.Green));
        initialGraph2.addNode(new Node(6, Color.Red));
        initialGraph2.addLinkBetween(initialGraph2.getNode(0), initialGraph2.getNode(4));
        initialGraph2.addLinkBetween(initialGraph2.getNode(1), initialGraph2.getNode(2));
        initialGraph2.addLinkBetween(initialGraph2.getNode(1), initialGraph2.getNode(3));
        initialGraph2.addLinkBetween(initialGraph2.getNode(1), initialGraph2.getNode(4));
        initialGraph2.addLinkBetween(initialGraph2.getNode(3), initialGraph2.getNode(5));
        initialGraph2.addLinkBetween(initialGraph2.getNode(4), initialGraph2.getNode(5));
        initialGraph2.addLinkBetween(initialGraph2.getNode(5), initialGraph2.getNode(6));


        //-----------------------------------------------> test3 :

        Graph initialGraph3 = new Graph(15);
        initialGraph3.addNode(new Node(0, Color.Red));
        initialGraph3.addNode(new Node(1, Color.Black));
        initialGraph3.addNode(new Node(2, Color.Black));
        initialGraph3.addNode(new Node(3, Color.Black));
        initialGraph3.addNode(new Node(4, Color.Red));
        initialGraph3.addNode(new Node(5, Color.Green));
        initialGraph3.addNode(new Node(6, Color.Green));
        initialGraph3.addNode(new Node(7, Color.Red));
        initialGraph3.addNode(new Node(8, Color.Red));
        initialGraph3.addNode(new Node(9, Color.Green));
        initialGraph3.addNode(new Node(10, Color.Red));
        initialGraph3.addNode(new Node(11, Color.Red));
        initialGraph3.addNode(new Node(12, Color.Red));
        initialGraph3.addNode(new Node(13, Color.Green));
        initialGraph3.addNode(new Node(14, Color.Red));

        initialGraph3.addLinkBetween(initialGraph3.getNode(0), initialGraph3.getNode(1));
        initialGraph3.addLinkBetween(initialGraph3.getNode(0), initialGraph3.getNode(2));
        initialGraph3.addLinkBetween(initialGraph3.getNode(1), initialGraph3.getNode(14));
        initialGraph3.addLinkBetween(initialGraph3.getNode(1), initialGraph3.getNode(2));
        initialGraph3.addLinkBetween(initialGraph3.getNode(1), initialGraph3.getNode(3));
        initialGraph3.addLinkBetween(initialGraph3.getNode(2), initialGraph3.getNode(5));
        initialGraph3.addLinkBetween(initialGraph3.getNode(2), initialGraph3.getNode(6));
        initialGraph3.addLinkBetween(initialGraph3.getNode(2), initialGraph3.getNode(7));
        initialGraph3.addLinkBetween(initialGraph3.getNode(3), initialGraph3.getNode(13));
        initialGraph3.addLinkBetween(initialGraph3.getNode(3), initialGraph3.getNode(14));
        initialGraph3.addLinkBetween(initialGraph3.getNode(3), initialGraph3.getNode(7));
        initialGraph3.addLinkBetween(initialGraph3.getNode(4), initialGraph3.getNode(6));
        initialGraph3.addLinkBetween(initialGraph3.getNode(4), initialGraph3.getNode(11));
        initialGraph3.addLinkBetween(initialGraph3.getNode(5), initialGraph3.getNode(10));
        initialGraph3.addLinkBetween(initialGraph3.getNode(5), initialGraph3.getNode(12));
        initialGraph3.addLinkBetween(initialGraph3.getNode(6), initialGraph3.getNode(11));
        initialGraph3.addLinkBetween(initialGraph3.getNode(7), initialGraph3.getNode(8));
        initialGraph3.addLinkBetween(initialGraph3.getNode(7), initialGraph3.getNode(9));
        initialGraph3.addLinkBetween(initialGraph3.getNode(8), initialGraph3.getNode(14));

        Graph initialGraph= new Graph(10);
        initialGraph.addNode(new Node(0, Color.Red));
        initialGraph.addNode(new Node(1, Color.Red));
        initialGraph.addNode(new Node(2, Color.Red));
        initialGraph.addNode(new Node(3, Color.Green));
        initialGraph.addNode(new Node(4, Color.Red));
        initialGraph.addNode(new Node(5, Color.Green));
        initialGraph.addNode(new Node(6, Color.Green));
        initialGraph.addNode(new Node(7, Color.Red));
        initialGraph.addNode(new Node(8, Color.Black));
        initialGraph.addNode(new Node(9, Color.Black));
        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(1));
        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(3));
        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(2));
        initialGraph.addLinkBetween(initialGraph.getNode(0), initialGraph.getNode(9));
        initialGraph.addLinkBetween(initialGraph.getNode(6), initialGraph.getNode(5));

        initialGraph.addLinkBetween(initialGraph.getNode(8), initialGraph.getNode(0));
        initialGraph.addLinkBetween(initialGraph.getNode(4), initialGraph.getNode(8));
        initialGraph.addLinkBetween(initialGraph.getNode(9), initialGraph.getNode(8));
        initialGraph.addLinkBetween(initialGraph.getNode(7), initialGraph.getNode(8));
        initialGraph.addLinkBetween(initialGraph.getNode(1), initialGraph.getNode(5));


        State initialState0 = new State(initialGraph , -1, null);
        State initialState1 = new State(initialGraph1 , -1, null);
        State initialState2 = new State(initialGraph2 , -1, null);
        State initialState3 = new State(initialGraph3 , -1, null);

        //DFS.graph_search(initialState0);
        //DFS.graph_search(initialState1);
        //DFS.graph_search(initialState2);
        //DFS.graph_search(initialState3);

        //DFS.graph_search(initialState0);
        //DFS.graph_search(initialState1);
        //DFS.graph_search(initialState2);
        //DFS.graph_search(initialState3);

        //DFS.tree_search(initialState0);
        //DFS.tree_search(initialState1);
        //DFS.tree_search(initialState2);
        //DFS.tree_search(initialState3);

        //BFS.search(initialState2);
        //BFS.search(initialState3);

        IDS.search(initialState0);
        //IDS.search(initialState1);
        //IDS.search(initialState2);
        //IDS.search(initialState3);

        //UCS.search(initialState0);
        RBFS.search(initialState0);
        //RBFS.search(initialState1);
        //RBFS.search(initialState2);
        //RBFS.search(initialState3);

        BIBF.search(initialState0);
        //BIBF.search(initialState1);
        //BIBF.search(initialState2);
        //BIBF.search(initialState3);

        AStar.search(initialState0);
        //AStar.search(initialState1);
        //AStar.search(initialState2);
        //AStar.search(initialState3);

        GBFS.search(initialState0);
        //GBFS.search(initialState1);
        //GBFS.search(initialState2);
        //GBFS.search(initialState3);

        IDAStar.search(initialState0);
        //IDAStar.search(initialState1);
        //IDAStar.search(initialState2);
        //IDAStar.search(initialState3);

    }

}
