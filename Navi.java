class Navi{
    Graph graph;
    Navi(){
        graph = new Spielbrett().getGraph();
    }

    protected List<Vertex> dijkstra (Vertex start, Vertex ziel){
        List<Vertex> weg = new List<>();
        List<Vertex> unbesucht = graph.getVertices();
        unbesucht.toFirst();
        //Weise allen Knoten unendlich als Distanz zu und dem Startknoten die Distanz 0
        while(unbesucht.hasAccess()){
            if(unbesucht.getContent().getID().equals(start.getID())){
                unbesucht.getContent().setDistanz(0);
            } else {    
            unbesucht.getContent().setDistanz(Integer.MAX_VALUE);
            }
            unbesucht.next();
        }
        Vertex K = new Vertex("");
        start.setDistanz(0);
        //start.setMark(true);
        //Solange bis der gesuchte Knoten gefunden wurde
        while(!ziel.getID().equals(K.getID())){
        //while(!graph.allVerticesMarked()){
            unbesucht.toFirst();
            //System.out.println(K.getID());
            //Lösche alle markierten Knoten aus der Liste
            while(unbesucht.hasAccess()){
                if(unbesucht.getContent().isMarked()) unbesucht.remove();
                unbesucht.next();
            }
            //Finde Knoten mit kürzester Distanz
            unbesucht.toFirst();
            K = unbesucht.getContent();
            while(unbesucht.hasAccess()){
                Vertex temp = unbesucht.getContent();
                if(temp.getDistanz() < K.getDistanz()){
                    K = temp;
                } 
                unbesucht.next();
            }
            //markiere den Knoten mit der kürzesten Distanz
            K.setMark(true);
            //Für alle noch unbesuchten Nachbarknoten 
            List<Vertex> nachbarn = graph.getNeighbours(K);
            nachbarn.toFirst();
            while(nachbarn.hasAccess()){
                if(nachbarn.getContent().isMarked()) nachbarn.remove();
                nachbarn.next();
            }
            //wird die Summe des Kantengewichtes und der Distanz im aktuellen Knoten K berechnet
            nachbarn.toFirst();
            while(nachbarn.hasAccess()){
                Edge temp = graph.getEdge(K, nachbarn.getContent());
                double summe = temp.getWeight() + K.getDistanz();
                //wenn die Summe kleiner ist als die Distanz, wird diese aktualisiert und K wird als Vorgänger gesetzt
                if(summe < nachbarn.getContent().getDistanz()){
                    nachbarn.getContent().setVorgaenger(K);
                    nachbarn.getContent().setDistanz(summe);
                }
                nachbarn.next();
            }
        }

        //Errechne die Laenge und gib die Wegbeschreibung
        //Finde den Ziel Knoten K = ziel (aber mit Vorgänger)
        
        String beschreibung = "";
        double distanz = K.getDistanz();
        while(!K.getID().equals(start.getID())){
            beschreibung += K.getID() + " ";
            K = K.getVorgaenger();
        }
        beschreibung += start.getID();
        System.out.println("Weg: " + beschreibung + " Distanz: " + distanz);

        

        return weg;
    }

    public static void main(String[] args) {
        Vertex berlin = new Vertex("Barcelona");
        Vertex moskau = new Vertex("Hamburg");
        new Navi().dijkstra(berlin, moskau);
    }

}