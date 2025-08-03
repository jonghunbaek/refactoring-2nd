package com.example.refactoring;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TestSample {

    @Test
    void test() {
        Assertions.assertThat(solution(new String[] {"frodo", "fradi", "crodo", "abc123", "frodoc"}, new String[] {"fr*d*", "abc1**"})).isEqualTo(2);
    }

    // 재귀 형태로 구현
    // 상태 - real_banned_id, visited// 종료 조건 - real_banned_id가 banned_id와 길이가 같은 경우
    // 점화식 - 방문하지 않은 user_id를 순회하며 일치여부 확인해 일치하면 real_banned_id에 넣기
    public int solution(String[] user_id, String[] banned_id) {
        String[][] cases = new String[banned_id.length][];
        for (int i = 0; i < banned_id.length; i++) {
            List<String> ids = new ArrayList<>();
            for (int j = 0; j < user_id.length; j++) {
                if (isMatched(user_id[j], banned_id[i])) {
                    ids.add(user_id[j]);
                }
            }
            cases[i] = ids.toArray(String[]::new);
        }

        Set<Set<String>> result = new HashSet<>(); // 전체 결과를 담을 자료 구조
        Set<String> banned = new HashSet<>(); // 하나의 경우의 수에 해당하는 자료 구조
        boolean[] visited = new boolean[cases.length];
        count(cases, visited, banned, result);

        return result.size();
    }

    private void count(String[][] cases, boolean[] visited, Set<String> banned, Set<Set<String>> result) {
        if (banned.size() == cases.length) {
            result.add(new HashSet<>(banned));
            return;
        }

        for (int i = 0; i < cases.length; i++) {
            if (visited[i]) {
                continue;
            }

            visited[i] = true;
            for (int j = 0; j < cases[i].length; j++) {
                if (banned.contains(cases[i][j])) {
                    continue;
                }
                banned.add(cases[i][j]);
                count(cases, visited, banned, result);
                banned.remove(cases[i][j]);
            }

            visited[i] = false;
        }
    }

    private boolean isMatched(String user, String bannedUser) {
        if (user.length() != bannedUser.length()) {
            return false;
        }

        for (int i = 0; i < user.length(); i++) {
            if (bannedUser.charAt(i) == '*') {
                continue;
            }

            if  (user.charAt(i) != bannedUser.charAt(i)) {
                return false;
            }
        }

        return true;
    }
}
