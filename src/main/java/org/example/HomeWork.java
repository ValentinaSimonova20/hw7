package org.example;


import java.util.ArrayList;
import java.util.List;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1439">https://acm.timus.ru/problem.aspx?space=1&num=1439</a>
     */
    public List<Integer> getOriginalDoorNumbers(int maxDoors, List<Action> actionList) {
        List<Integer> result = new ArrayList<>();
        Treap treap = new Treap();
        fillTreap(treap, maxDoors);
        actionList.forEach(action -> {
            if(action.isLook) {
                result.add(treap.get(action.doorNumber));
            } else {
                treap.updateNodes(action.doorNumber);
            }
        });
        return result;
    }

    private void fillTreap(Treap treap, int maxDoors) {
        for(int i = 1; i < maxDoors + 1; i++) {
            treap.add(i);
        }
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1521">https://acm.timus.ru/problem.aspx?space=1&num=1521</a><br/>
     * <h2>Пошагово</h2>
     * Для 5 3 входных данных:<br/>
     * _ -> 3 позиции<br/>
     * _ 1 2 <b>3</b> 4 5 => 3 <br/>
     * <b>1</b> 2 _ 4 5 => 1 <br/>
     * _ 2 4 <b>5</b> => 5 <br/>
     * <b>2</b> 4 _ => 2 <br/>
     * _ <b>4</b> => 4
     */
    public List<Integer> getLeaveOrder(int maxUnits, int leaveInterval) {
        List<Integer> result = new ArrayList<>();
        Treap treap = new Treap(leaveInterval, maxUnits);
        fillTreap(treap, maxUnits);
        for(int i = 1; i < maxUnits + 1; i++) {
            int kThElement = treap.takeKth();
            result.add(kThElement);
            treap.remove(kThElement);
        }

        return result;
    }


}
