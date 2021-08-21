/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */
 
package problems;

import java.util.*;

public class TasksAssignment implements Problem {

    // Number of Tasks shouldn't be higher than number of Workers!
    public static final int[][] costs = {
            {90, 76, 75, 70, 50, 74, 12, 68},
            {35, 85, 55, 65, 48, 101, 70, 83},
            {125, 95, 90, 105, 59, 120, 36, 73},
            {45, 110, 95, 115, 104, 83, 37, 71},
            {60, 105, 80, 75, 59, 62, 93, 88},
            {45, 65, 110, 95, 47, 31, 81, 34},
            {38, 51, 107, 41, 69, 99, 115, 48},
            {47, 85, 57, 71, 92, 77, 109, 36},
            {39, 63, 97, 49, 118, 56, 92, 61},
            {47, 101, 71, 60, 88, 109, 52, 90}};
    public static final int numOfWorkers = costs.length;
    public static final int numOfTasks = costs[0].length;

    public static class Assignment {
        public int idWorker;
        public int idTask;

        public Assignment(int idWorker, int idTask) {
            this.idWorker = idWorker;
            this.idTask = idTask;
        }
    }

    public static class StateAssignments implements State {
        public Assignment[] assignments = new Assignment[numOfTasks];
        public List<Integer> workersFreeOfTasks;
        public int[] costsAssigned = new int[numOfTasks];
        public double totalCost = 0;


        @Override
        public double getResult() {//TODO:
            return totalCost;
        }

        @Override
        public double getFitness() {
            // Adding +1 for a nonsense case of totalCost equals 0..
            return 1 / (totalCost + 1);
        }

        public void setAssignments(Integer[] nAssignments) {
            int idWorker;
            for (int idTask = 0; idTask < numOfTasks; idTask++) {
                idWorker = nAssignments[idTask];
                assignments[idTask] = new Assignment(idWorker, idTask);
                costsAssigned[idTask] = costs[idWorker][idTask];
                totalCost += costsAssigned[idTask];
            }
        }

        // Randomly choosing workers that are free of doing tasks
        public void setRandomWorkersFreeOfTasks() {
            workersFreeOfTasks = new LinkedList<>();
            int randomWorker;
            for (int i = 0; i < numOfWorkers - numOfTasks; i++) {
                randomWorker = (int) (Math.random() * numOfWorkers);
                while (workersFreeOfTasks.contains(randomWorker))
                    randomWorker = (int) (Math.random() * numOfWorkers);
                workersFreeOfTasks.add(randomWorker);
            }
        }
    }

    @Override
    public State generateNewState(Operators operatorType, State current) {
        StateAssignments cState = (StateAssignments) current;
        StateAssignments nState = new StateAssignments();

        if (numOfWorkers > numOfTasks)
            nState.setRandomWorkersFreeOfTasks();
        Integer[] assignments = null;

        // Applying operator
        switch (operatorType) {
            case NEAR_BY:
                assignments = doNearByOperation(nState, cState);
                break;
            case RANDOM:
                assignments = doRandomOperation(nState);
                break;
        }

        nState.setAssignments(assignments);
        return nState;
    }

    @Override
    public double getEvaluation(State current, State next) {
        return current.getResult() - next.getResult();
    }

    @Override
    public State doCrossover(State p1, State p2) {
        int start = (int) (Math.random() * numOfTasks);
        int end = (int) (Math.random() * (numOfTasks - (start + 1))) + start + 1;

        Integer[] nStateAssignments = new Integer[numOfTasks];

        StateAssignments p1State = (StateAssignments) p1;
        StateAssignments p2State = (StateAssignments) p2;
        Assignment[] p1AssignmentsSliced = Arrays.copyOfRange(p1State.assignments, start, end);

        List<Integer> idWorkersP1 = new LinkedList<>();
        List<Integer> idTasksP1 = new LinkedList<>();
        // Dividing workers and respective tasks in to two separate lists
        for (Assignment assignment : p1AssignmentsSliced) {
            idWorkersP1.add(assignment.idWorker);
            idTasksP1.add(assignment.idTask);
            nStateAssignments[assignment.idTask] = assignment.idWorker;
        }

        int idWorkerP2, idTaskP2, countAddedTasks = 0;
        for (Assignment assignment : p2State.assignments) {
            idWorkerP2 = assignment.idWorker;
            idTaskP2 = assignment.idTask;

            // Ignoring P2 worker because we prioritize P1 free workers
            if (numOfWorkers > numOfTasks && p1State.workersFreeOfTasks.contains(idWorkerP2))
                continue;

            // Prioritize P1 assignment
            if (!idWorkersP1.contains(idWorkerP2)) {
                // If the task is already assigned, we must find a new one that isn't
                if (idTasksP1.contains(idTaskP2)) {
                    int idTaskUnused = 0;
                    while (idTasksP1.contains(idTaskUnused))
                        idTaskUnused++;
                    nStateAssignments[idTaskUnused] = idWorkerP2;
                    idWorkersP1.add(idWorkerP2);
                    idTasksP1.add(idTaskUnused);
                } else {
                    nStateAssignments[idTaskP2] = idWorkerP2;
                    idWorkersP1.add(idWorkerP2);
                    idTasksP1.add(idTaskP2);
                }
                countAddedTasks++;
                if (countAddedTasks == numOfTasks)
                    break;
            }
        }

        // Converting some P2 free workers to active workers of the new State, if needed
        if (countAddedTasks < numOfTasks) {
            for (int idP2FreeWorker : p2State.workersFreeOfTasks) {
                if (!p1State.workersFreeOfTasks.contains(idP2FreeWorker) && !idWorkersP1.contains(idP2FreeWorker)) {
                    int idTaskUnused = 0;
                    while (idTasksP1.contains(idTaskUnused))
                        idTaskUnused++;

                    nStateAssignments[idTaskUnused] = idP2FreeWorker;
                    idWorkersP1.add(idP2FreeWorker);
                    idTasksP1.add(idTaskUnused);
                    countAddedTasks++;
                    if (countAddedTasks == numOfTasks)
                        break;
                }
            }
        }

        StateAssignments nState = new StateAssignments();
        if (numOfWorkers > numOfTasks) {
            nState.workersFreeOfTasks = new LinkedList<>();
            nState.workersFreeOfTasks.addAll(p1State.workersFreeOfTasks);
        }
        nState.setAssignments(nStateAssignments);
        return nState;
    }

    @Override
    public void doMutation(State state) {
        StateAssignments mState = (StateAssignments) state;
        Assignment a1 = mState.assignments[(int) (Math.random() * numOfTasks)];
        Assignment a2 = mState.assignments[(int) (Math.random() * numOfTasks)];
        int a1IdWorkerTemp = a1.idWorker;
        a1.idWorker = a2.idWorker;
        a2.idWorker = a1IdWorkerTemp;
    }

    private Integer[] doNearByOperation(StateAssignments nState, StateAssignments cState) {
        Integer[] assignments = new Integer[numOfTasks];
        if (numOfWorkers > numOfTasks) {
            List<Integer> cWorkersFreeOfTasks = new LinkedList<>(cState.workersFreeOfTasks);
            for (int i = 0; i < numOfTasks; i++) {
                if (nState.workersFreeOfTasks.contains(cState.assignments[i].idWorker)) {
                    // Prioritizing nState free workers by converting cState
                    // free workers to active workers of nState
                    for (Integer idFreeWorker : cWorkersFreeOfTasks) {
                        // Checking if cState free worker is also a free worker of nState
                        if (!nState.workersFreeOfTasks.contains(idFreeWorker)) {
                            assignments[i] = idFreeWorker;
                            cWorkersFreeOfTasks.remove(idFreeWorker);
                            break;
                        }
                    }
                } else
                    assignments[i] = cState.assignments[i].idWorker;
            }
        } else
            for (int i = 0; i < numOfTasks; i++)
                assignments[i] = cState.assignments[i].idWorker;

        swap(assignments, (int) (Math.random() * numOfTasks), (int) (Math.random() * numOfTasks));
        swap(assignments, (int) (Math.random() * numOfTasks), (int) (Math.random() * numOfTasks));
        return assignments;
    }

    private Integer[] doRandomOperation(StateAssignments nState) {
        Integer[] assignments = new Integer[numOfTasks];
        if (numOfWorkers > numOfTasks) {
            for (int i = 0, j = 0; i < assignments.length; i++, j++) {
                if (!nState.workersFreeOfTasks.contains(j))
                    assignments[i] = j;
                else
                    --i; // Skipping free worker
            }
        } else { // If there are no free workers, numOfWorkers = numOfTasks
            for (int i = 0; i < assignments.length; i++)
                assignments[i] = i;
        }
        List<Integer> intList = Arrays.asList(assignments);
        Collections.shuffle(intList);
        intList.toArray(assignments);
        return assignments;
    }


    private void swap(Integer[] assignments, int i, int j) {
        int temp = assignments[i];
        assignments[i] = assignments[j];
        assignments[j] = temp;
    }

}
