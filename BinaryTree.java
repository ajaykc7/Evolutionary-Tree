/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evolutionarytrees;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 *
 * @author Ajay
 */
public class BinaryTree {

    Node root;
    private ArrayList<String> solution;

    class Node {

        private String data;
        private ArrayList<String> possibleAssignment;
        private int parsimonyScore;
        private Node left;
        private Node right;
        private Node parent;

        Node(String data) {
            this.data = data;
            right = null;
            left = null;
            parent = null;
            possibleAssignment = new ArrayList<String>();
            parsimonyScore = 0;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getData() {
            return data;
        }

        public void setLeft(Node left) {
            this.left = left;
        }

        public Node getLeft() {
            return left;
        }

        public void setRight(Node right) {
            this.right = right;
        }

        public Node getRight() {
            return right;
        }

        public Node getParent() {
            return parent;
        }

        public void setParent(Node parent) {
            this.parent = parent;
        }

        public void setParsimonyScore(int number) {
            parsimonyScore = number;
        }

        public int getParsimonyScore() {
            return parsimonyScore;
        }

        public void clearParsimonyScore() {
            parsimonyScore = 0;
        }

        public void setPossibleAssignment(String character) {
            possibleAssignment.add(character);
        }

        public ArrayList<String> getPossibleAssignment() {
            return possibleAssignment;
        }

        public void clearPossibleAssignment() {
            possibleAssignment.clear();
        }

        public boolean isLeaf() {
            if ((getRight() == null) || (getLeft() == null)) {
                return true;
            } else {
                return false;
            }
        }
    };

    class TreeIterator {

        Stack<Node> stack;

        public TreeIterator(Node root) {
            stack = new Stack<Node>();
            while (root != null) {
                stack.push(root);
                root = root.getLeft();
            }
        }

        public boolean hasNext() {
            return !stack.isEmpty();
        }

        public Node next() {
            Node node = stack.pop();
            Node tempNode = node;
            if (node.getRight() != null) {
                node = node.getRight();
                while (node != null) {
                    stack.push(node);
                    node = node.getLeft();
                }
            }
            return tempNode;
        }
    };

    public BinaryTree() {
        root = new Node(null);
        solution = new ArrayList<String>();
    }

    public BinaryTree(String data) {
        root = new Node(data);
        solution = new ArrayList<String>();
    }

    public void visitNode() {
        visitNode(root);
    }

    private void visitNode(Node node) {
        if (node.left != null) {
            visitNode(node.left);
        }
        if (node.right != null) {
            visitNode(node.right);
        }
        if (node.left == null && node.right == null) {
            System.out.println(node.getData());
        }
    }

    public int count() {
        return count(root);
    }

    private int count(Node n) {
        int count = 1;
        if (n == null) {
            return 0;
        } else {
            count = count + count(n.getLeft());
            count = count + count(n.getRight());
            return count;
        }
    }

    public void populate(String data, ArrayList<String> species) {

        populate(root, data, species, "N");//, tempCharacter);

    }

    private void populate(Node r, String data, ArrayList<String> species, String previousNode) {
        for (int i = 0; i < data.length(); i++) {

            //System.out.println(r.getData());
            String character;
            if (i == data.length() - 1) {
                character = data.substring(i);
            } else {
                character = data.substring(i, i + 1);
            }

            if (character.equals("L")) {
                r.setLeft(new Node("x"));
                Node parent = r;
                r = r.getLeft();
                r.setParent(parent);
            } else if (character.equals("R")) {
                r.setRight(new Node("x"));
                Node parent = r;
                r = r.getRight();
                r.setParent(parent);
            } else {
                r = r.getParent();
                if (previousNode.equals("L")) {
                    r.getLeft().setData(species.get(0));
                    species.remove(0);
                } else if (previousNode.equals("R")) {
                    r.getRight().setData(species.get(0));
                    species.remove(0);
                } else {

                }
            }
            previousNode = character;
        }
    }

    /*private void populate(Node r, Node parent, String data, ArrayList<String> species, char previousNode) {//, char tempCharacter) {

        if (data.length() == 0) {

        } else {
            char tempCharacter = data.charAt(0);
            if (tempCharacter == 'L') {
                r.setLeft(new Node("x"));
                r.setParent(parent);
                //System.out.println(data.length());
                data = data.substring(1);
                populate(r.getLeft(), r, data, species, tempCharacter);//, tempCharacter);
            }
            if (tempCharacter == 'R') {
                r.setRight(new Node("x"));
                r.setParent(parent);
                //System.out.println(data.length());
                data = data.substring(1);
                
                populate(r.getRight(), r, data, species, tempCharacter);//, tempCharacter);
            }
            if (tempCharacter == 'U') {
                if ((previousNode == 'L')) {
                    if (parent.getLeft().getData().equals("x")) {
                        parent.getLeft().setData(species.get(0));
                        species.remove(0);

                    }
                } else if (previousNode == 'R') {
                    if (parent.getRight().getData().equals("x")) {
                        parent.getRight().setData(species.get(0));
                        species.remove(0);
                    } else {

                    }

                    //, tempCharacter);
                }
                if (data.isEmpty()) {
                    data = "";
                } else {
                    
                    data = data.substring(1);
                }
                populate(parent, parent.getParent(), data, species, tempCharacter);
            }
        }
    }*/
    public void postorder() {
        postorder(root);
    }

    private void postorder(Node r) {
        if (r != null) {

            postorder(r.getLeft());

            postorder(r.getRight());
            System.out.print(r.getData() + " ");

        }
    }

    public void createEvolutionTree(int numberOfSite) {

        int numberOfNodes = count();

        int totalParsimony = 0;

        TreeIterator iterator = new TreeIterator(root);

        for (int i = 0; i < numberOfSite; i++) {

            int count = 0;
            while (iterator.hasNext()) {

                Node node = iterator.next();
                node.clearPossibleAssignment();
                if (node.isLeaf()) {
                    node.setParsimonyScore(0);
                    if (i == (numberOfSite - 1)) {
                        //System.out.println(node.getData().substring(i));
                        node.setPossibleAssignment(node.getData().substring(i));
                    } else {
                        //System.out.println(node.getData().substring(i, i + 1));
                        node.setPossibleAssignment(node.getData().substring(i, i + 1));
                    }
                    count++;
                }
            }
            findPossibleAssignment(root);
            assignCharacter(root, i);
            iterator = new TreeIterator(root);

        }

        /*iterator = new TreeIterator(root);
        while (iterator.hasNext()) {
                Node node = iterator.next();
                System.out.println(node.getData());
                System.out.println(node.getParsimonyScore());
                ArrayList<String> tempString = node.getPossibleAssignment();
                for(int i=0;i<tempString.size();i++){
                    System.out.print(node.getPossibleAssignment().get(i)+" ");
                }
                System.out.println(" ");
                System.out.println(" ");
        }*/
    }

    private ArrayList<String> findPossibleAssignment(Node node) {
        if (node.getLeft() == null) {
            return node.getPossibleAssignment();
        } else {
            ArrayList<String> leftSubTreeSet = findPossibleAssignment(node.getLeft());
            ArrayList<String> rightSubTreeSet = findPossibleAssignment(node.getRight());

            /*ArrayList<String> commonCharacters = findCommonCharacters(node, leftSubTreeSet, rightSubTreeSet);
            for(int i=0;i<commonCharacters.size();i++){
                node.setPossibleAssignment(commonCharacters.get(i));
            }*/
            //return commonCharacters;
            return findCommonCharacters(node, leftSubTreeSet, rightSubTreeSet);
        }

    }

    private ArrayList<String> findCommonCharacters(Node node, ArrayList<String> first, ArrayList<String> second) {

        ArrayList<String> commonCharacter = new ArrayList<String>();
        /*boolean aFirst = false;
        boolean cFirst = false;
        boolean tFirst = false;
        boolean gFirst = false;

        boolean aSecond = false;
        boolean cSecond = false;
        boolean tSecond = false;
        boolean gSecond = false;

        ArrayList<String> commonCharacter = new ArrayList<String>();
        System.out.println("Node name " + node.getLeft().getData());
        for (int i = 0; i < first.size(); i++) {
            String character = first.get(i);
            switch (character) {
                case "A":
                    aFirst = true;
                    break;
                case "C":
                    cFirst = true;
                    break;
                case "T":
                    tFirst = true;
                    break;
                case "G":
                    gFirst = true;
                    break;

            }
        }

        for (int i = 0; i < second.size(); i++) {
            String character = second.get(i);
            
            switch (character) {
                
                case "A":
                    aSecond = true;
                    break;
                case "C":
                    cSecond = true;
                    break;
                case "T":
                    tSecond = true;
                    break;
                case "G":
                    gSecond = true;
                    break;

            }
        }
        if ((aFirst == aSecond) || (cFirst == cSecond) || (tFirst == tSecond) || (gFirst == gSecond)) {
            System.out.println("Some are equal");
            if (aFirst == aSecond) {
                commonCharacter.add("A");
            }
            if (cFirst == cSecond) {
                commonCharacter.add("C");
            }
            if (tFirst == tSecond) {
                commonCharacter.add("T");
            }
            if (gFirst == gSecond) {
                commonCharacter.add("G");
            }
            node.setParsimonyScore(node.getLeft().getParsimonyScore() + node.getRight().getParsimonyScore());
        } else {
            if (aFirst != aSecond) {
                commonCharacter.add("A");
            }
            if (cFirst != cSecond) {
                commonCharacter.add("C");
            }
            if (tFirst != tSecond) {
                commonCharacter.add("T");
            }
            if (gFirst != gSecond) {
                commonCharacter.add("G");
            }
            node.setParsimonyScore(node.getLeft().getParsimonyScore() + node.getRight().getParsimonyScore() + 1);
        }
         */
        //commonCharacter.re
        boolean isCommon = false;
        for (int i = 0; i < first.size(); i++) {
            for (int j = 0; j < second.size(); j++) {
                if (first.get(i).equals(second.get(j))) {
                    commonCharacter.add(first.get(i));
                    isCommon = true;
                }
            }
        }

        if (!isCommon) {/*
            for (int i = 0; i < first.size(); i++) {
                commonCharacter.add(first.get(i));
            }
            for (int i = 0; i < second.size(); i++) {
                for (int j = 0; j < commonCharacter.size(); j++) {
                    if (second.get(i).equals(commonCharacter.get(j))) {

                    } else {
                        commonCharacter.add(second.get(i));
                    }

                }

            }*/
            commonCharacter.addAll(first);
            commonCharacter.addAll(second);
            Collections.sort(commonCharacter);
            Set<String> set = new HashSet<>();
            set.addAll(commonCharacter);
            commonCharacter.clear();
            commonCharacter.addAll(set);
        }
        /*try {
            if (node.getRight().getLeft().getLeft().getLeft().getData().equals("TCTACGGTCG")) {
                System.out.println("Here");
                System.out.println("Node name " + node.getData());
                for (int i = 0; i < commonCharacter.size(); i++) {
                    System.out.print(commonCharacter.get(i) + " ");
                }
                System.out.println();
            }
        } catch (NullPointerException d) {

        }

        try {
            if (node.getLeft().getLeft().getLeft().getLeft().getData().equals("CTCAAAGCCT")) {
                System.out.println("Here");
                for (int i = 0; i < first.size(); i++) {
                    System.out.print(first.get(i) + " ");
                }
                System.out.println();
                for (int i = 0; i < second.size(); i++) {
                    System.out.print(second.get(i) + " ");
                }
                System.out.println();

                System.out.println("Node name " + node.getData());
                for (int i = 0; i < commonCharacter.size(); i++) {
                    System.out.print(commonCharacter.get(i) + " ");
                }
                System.out.println();
            }
        } catch (NullPointerException d) {

        }*/
 /*for (int i = 0; i < commonCharacter.size(); i++) {
            if (!node.getPossibleAssignment().isEmpty()) {
                for (int j = 0; j < node.getPossibleAssignment().size(); j++) {
                    if (commonCharacter.get(i).equals(node.getPossibleAssignment().get(j))) {

                    } else {
                        node.setPossibleAssignment(commonCharacter.get(i));
                    }

                }
            } else {
                node.setPossibleAssignment(commonCharacter.get(i));
            }
        }*/
        node.clearPossibleAssignment();
        for (int i = 0; i < commonCharacter.size(); i++) {
            node.setPossibleAssignment(commonCharacter.get(i));
        }
        return commonCharacter;
    }

    private void assignCharacter(Node node, int index) {

        String currentData = node.getData();
        if (node.getParent() != null) {
            if (!node.isLeaf()) {
                //System.out.println(node.getParent().getData());
                String parentAssignedcharacter = node.getParent().getData().charAt(index) + "";
                //System.out.println(parentAssignedcharacter);
                String tempData = node.getData();
                if (tempData.equals("x")) {
                    node.setData(node.getPossibleAssignment().get(0));

                } else {
                    node.setData(node.getData() + node.getPossibleAssignment().get(0));
                }

                for (int i = 0; i < node.getPossibleAssignment().size(); i++) {
                    if (parentAssignedcharacter.equals(node.getPossibleAssignment().get(i))) {
                        node.setData(node.getData().substring(0, index) + parentAssignedcharacter);
                        break;
                    }
                }
            }
        } else {
            node.setData(currentData.replaceFirst("-", root.getPossibleAssignment().get(0)));
        }

        if (node.getLeft() != null) {
            assignCharacter(node.getLeft(), index);
        }
        if (node.getRight() != null) {
            assignCharacter(node.getRight(), index);
        }

    }

    public ArrayList<String> getSolution() {
        getSolution(root, "");
        return solution;
    }

    private void getSolution(Node node, String position) {
        if (node != null) {

            solution.add(position + ":" + node.getData());

            getSolution(node.getLeft(), position + 0);
            getSolution(node.getRight(), position + 1);
        }

    }

    public void display() {

        TreeIterator iterator = new TreeIterator(root);
        while (iterator.hasNext()) {
            Node node = iterator.next();
            System.out.println(node.getData());
            System.out.println(node.getParsimonyScore());
            ArrayList<String> tempString = node.getPossibleAssignment();
            for (int i = 0; i < tempString.size(); i++) {
                System.out.print(node.getPossibleAssignment().get(i) + " ");
            }
            System.out.println(" ");
            System.out.println(" ");
        }
    }

}
