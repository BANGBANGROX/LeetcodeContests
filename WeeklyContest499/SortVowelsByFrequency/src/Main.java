import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

class Solution {
    public String sortVowels(final String s) {
        final List<Character> vowels = new ArrayList<>();
        final Map<Character, Integer> count = new HashMap<>();

        for (final char ch : s.toCharArray()) {
            if (isVowel(ch)) {
                if (!count.containsKey(ch)) {
                    count.put(ch, 1);
                    vowels.add(ch);
                } else {
                    count.put(ch, count.get(ch) + 1);
                }
            }
        }

        vowels.sort((a, b) -> count.get(b) - count.get(a));

        int vowelPtr = 0;
        final StringBuilder answer = new StringBuilder();

        for (final char ch : s.toCharArray()) {
            if (isVowel(ch)) {
                if (count.get(vowels.get(vowelPtr)) == 0) {
                    ++vowelPtr;
                }
                answer.append(vowels.get(vowelPtr));
                count.put(vowels.get(vowelPtr), count.get(vowels.get(vowelPtr)) - 1);
            } else {
                answer.append(ch);
            }
        }

        return answer.toString();
    }

    private boolean isVowel(final char ch) {
        return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u';
    }
}


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(final String[] args) {
        final Scanner scanner = new Scanner(System.in);
        final String s = scanner.next();

        System.out.println(new Solution().sortVowels(s));
    }
}