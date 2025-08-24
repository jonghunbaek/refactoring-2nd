package com.example.refactoring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.stream.Collectors;

public class TestSample {

    @Test
    void test() {
        Assertions.assertThat(solution(new String[] {
                        "java backend junior pizza 150",
                        "python frontend senior chicken 210",
                        "python frontend senior chicken 150",
                        "cpp backend senior pizza 260",
                        "java backend junior chicken 80",
                        "python backend senior chicken 50"},
                new String[] {
                        "java and backend and junior and pizza 100",
                        "python and frontend and senior and chicken 200",
                        "cpp and - and senior and pizza 250",
                        "- and backend and senior and - 150",
                        "- and - and - and chicken 100",
                        "- and - and - and - 150"})).isEqualTo(new int[] {1,1,1,1,2,4});
    }


    // 시간 복잡도 -> On2 (쿼리 10만개 * 정보 5만개)
    // 1. 입력 데이터를 가공해 객체에 매핑
    // 2. 가공된 데이터를 코딩 테스트 점수에 맞게 오름차순 정렬
    // 3. 쿼리를 가공해 코딩 테스트 점수에 맞게 이진 탐색하여 조건 확인
    //  -> 이 방식의 경우 특정 점수 이상의 모든 조건을 일일이 확인해야 함.
    // 그러므로 미리 각 조건별로 분리한 다음 점수 순으로 정렬하는 것이 좋을 듯
    // 이걸 어떻게 처리할 수 있을까?
    // 모든 조합을 미리 만들어 둬서 점수대로 분류
    // 각 조합을 키로, 키에 해당 하는 점수를 value로
    public int[] solution(String[] info, String[] query) {
        Map<String, List<Integer>> scoresByCondition = createAllConditions();
        putScoreByCondition(info, scoresByCondition);

        scoresByCondition.values().forEach(value -> value.sort(Comparator.naturalOrder()));

        List<Integer> results = new ArrayList<>();
        for (String s : query) {
            String[] split = s.split(" ");
            String key = split[0] + split[2] + split[4] + split[6];
            List<Integer> scores = scoresByCondition.get(key);

            if (scores.isEmpty()) {
                results.add(0);
                continue;
            }

            int idx = findScoreIndex(scores, Integer.parseInt(split[7]));
            results.add(scores.size() - idx);
        }

        return results.stream()
                .mapToInt(Integer::intValue)
                .toArray();
    }

    private Map<String, List<Integer>> createAllConditions() {
        String[] languages = new String[] {"java", "python", "cpp", "-"};
        String[] categories = new String[] {"backend", "frontend", "-"};
        String[] careers = new String[] {"senior", "junior", "-"};
        String[] soulFoods = new String[] {"pizza", "chicken", "-"};

        Map<String, List<Integer>> scoresByCondition = new HashMap<>();
        for (String language : languages) {
            for (String category : categories) {
                for (String career : careers) {
                    for (String soulFood : soulFoods) {
                        String target = language + category + career + soulFood;
                        scoresByCondition.put(target, new ArrayList<>());
                    }
                }
            }
        }

        return scoresByCondition;
    }

    private void putScoreByCondition(String[] info, Map<String, List<Integer>> scoresByCondition) {
        for (String target : info) {
            String[] split = target.split(" ");
            List<String> keys = new ArrayList<>();
            createKeys(0, Arrays.copyOfRange(split, 0, 4), "", keys);
            keys.add(String.join("", Arrays.copyOfRange(split, 0, 4)));
            for (String key : keys) {
                scoresByCondition.get(key).add(Integer.parseInt(split[4]));
            }
        }
    }

    private int findScoreIndex(List<Integer> scores, int score) {
        int start = 0;  // inclusive
        int end = scores.size() - 1;  // inclusive

        while (end > start) {
            int mid = (start + end) / 2;

            if (scores.get(mid) >= score) {
                end = mid;
            } else {
                start = mid + 1;
            }
        }

        if (scores.get(start) < score) {
            return scores.size();
        }
        return start;
    }

    private void createKeys(int depth, String[] conditions, String currentKey, List<String> keys) {
        if (depth == 4) {
            keys.add(currentKey);
            return;
        }

        createKeys(depth + 1, conditions, currentKey + "-", keys);
        createKeys(depth + 1, conditions, currentKey + conditions[depth], keys);
    }
}
