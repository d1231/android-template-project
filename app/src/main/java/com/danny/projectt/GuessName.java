package com.danny.projectt;

import org.apache.commons.lang3.StringUtils;

import java.text.Normalizer;

import rx.Observable;
import rx.subjects.BehaviorSubject;

public class GuessName {

    private final int totalGuesses;

    private final String name;

    private final String lowerCaseName;

    private final StringBuilder sb;

    private final BehaviorSubject<String> publishSubject;

    private int correctlyGuessed;

    public GuessName(String name) {

        name = StringUtils.stripAccents(name);
        this.name = name;
        this.lowerCaseName = name.toLowerCase();

        final int length = name.length();
        this.sb = new StringBuilder(length);
        int totalGuessTemp = 0;
        for (int i = 0; i < length; i++) {
            final char c = name.charAt(i);
            if (c != ' ') {
                sb.append('*');
                totalGuessTemp++;
            } else {
                sb.append(c);
            }
        }
        this.totalGuesses = totalGuessTemp;
        this.correctlyGuessed = 0;

        this.publishSubject = BehaviorSubject.create(sb.toString());

    }

    public Observable<String> textObservable() {

        return publishSubject;
    }

    public String text() {

        return sb.toString();

    }

    public boolean guessChar(char c) {

        c = Character.toLowerCase(c);

        int i = lowerCaseName.indexOf(c);

        boolean changed = false;
        while (i >= 0) {

            changed = true;
            correctlyGuessed++;

            sb.setCharAt(i, name.charAt(i));
            i = lowerCaseName.indexOf(c, i + 1);
        }

        if (changed) {
            publishSubject.onNext(sb.toString());
        }

        if (correctlyGuessed == totalGuesses) {
            publishSubject.onCompleted();
        }

        return changed;
    }
}
