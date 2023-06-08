package com.moio.utils;

import com.moio.common.enums.SystemWarnLanguageEnum;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * common util for keyboard
 *
 * @author molinchang
 */
public final class KeyBoardUtil {

    /**
     * key enter button
     *
     */
    public static void enter() {

        SystemWarnLanguageEnum.ENTER_CONTINUE.output();
        try {
            new BufferedReader(new InputStreamReader(System.in)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int minNumberOfFrogs(String croakOfFrogs) {

        if (croakOfFrogs.length() % 5 != 0) {
            return -1;
        }
        int res = 0, frogNum = 0;
        int[] cnt = new int[4];
        Map<Character, Integer> map = new HashMap<Character, Integer>() {{
            put('c', 0);
            put('r', 1);
            put('o', 2);
            put('a', 3);
            put('k', 4);
        }};
        for (int i = 0; i < croakOfFrogs.length(); i++) {
            char c = croakOfFrogs.charAt(i);
            int t = map.get(c);
            if (t == 0) {
                cnt[t]++;
                frogNum++;
                if (frogNum > res) {
                    res = frogNum;
                }
            } else {
                if (cnt[t - 1] == 0) {
                    return -1;
                }
                cnt[t - 1]--;
                if (t == 4) {
                    frogNum--;
                } else {
                    cnt[t]++;
                }
            }
        }
        if (frogNum > 0) {
            return -1;
        }
        return res;
    }

    public static void main(String[] args) {

        String frog = "crakkoocaaoarkcrcrorccaooakrakoocccooarokkrraokrkkcakororcrookaaoarckrckkaarkacorrakckaroocacccaaoaakkkkorkarcoaoaaccckcaocookkckkcrkcckkracocoarkorarookccarrocraaocacarorcoorkcracaarorarroarrccrcrcaccooackcaakckokkkkoaorcckakacccorkaarrkakcakcrorkccrrrkoacorcacrkakocorroakokkrrkkakrrckokacarorckracrrrocrrcccooorcararocrcocaaoccaakorcrcckokkkcokcacrkcckakkkkcaorooaorroccrrakcrcaacaokocaokkaorocorckrkokrrcaaarokaoaaroookorrorkoarorckacoaoakkokracaokaaokarooraraaacokrkkkaakoacookcrroakrkoacockkkkoccooarcaraarckcoaaaocakrororkrkorkckokrarkacokokrroacoccaookccrakkkrkoacarr";
        System.out.println(minNumberOfFrogs(frog));
    }
}
