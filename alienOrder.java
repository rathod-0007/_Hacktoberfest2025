import java.util.*;

class Solution {
    public String alienOrder(String[] words) {
        Map<Character, Set<Character>> graph = new HashMap<>();
        int[] inDegree = new int[26];

        buildGraph(words, graph, inDegree);

        StringBuilder sb = new StringBuilder();
        Queue<Character> queue = new LinkedList<>();
        
        for (char c : graph.keySet()) {
            if (inDegree[c - 'a'] == 0) {
                queue.offer(c);
                sb.append(c);
            }
        }

        while (!queue.isEmpty()) {
            char current = queue.poll();
            if (!graph.containsKey(current)) continue;
            for (char neighbor : graph.get(current)) {
                inDegree[neighbor - 'a']--;
                if (inDegree[neighbor - 'a'] == 0) {
                    queue.offer(neighbor);
                    sb.append(neighbor);
                }
            }
        }

        return sb.length() == graph.size() ? sb.toString() : "";
    }

    private void buildGraph(String[] words, Map<Character, Set<Character>> graph, int[] inDegree) {
        for (String word : words) {
            for (char c : word.toCharArray()) {
                graph.putIfAbsent(c, new HashSet<>());
            }
        }

        for (int i = 0; i < words.length - 1; i++) {
            String word1 = words[i];
            String word2 = words[i + 1];
            int minLength = Math.min(word1.length(), word2.length());

            for (int j = 0; j < minLength; j++) {
                char parent = word1.charAt(j);
                char child = word2.charAt(j);
                if (parent != child) {
                    if (!graph.get(parent).contains(child)) {
                        graph.get(parent).add(child);
                        inDegree[child - 'a']++;
                    }
                    break;
                }
            }
        }
    }
}
