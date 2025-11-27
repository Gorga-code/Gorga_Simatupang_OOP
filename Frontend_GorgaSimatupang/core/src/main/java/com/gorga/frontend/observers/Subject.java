package com.gorga.frontend.observers;

/**
 * Subject interface for the Observer Pattern implementation
 */
public interface Subject {
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(int score);
}
