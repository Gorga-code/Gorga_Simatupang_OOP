package com.gorga.frontend.observers;

public interface Subject {
    abstract void addObserver(Observer observer);
    abstract void removeObserver(Observer observer);
    abstract void notifyObservers(int score);
}
