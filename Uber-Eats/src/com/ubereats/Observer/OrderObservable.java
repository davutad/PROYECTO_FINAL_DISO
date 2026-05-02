package com.ubereats.observer;

public interface OrderObservable {
	public void addObserver(OrderObserver orderObserver);
	public void deleteObserver(OrderObserver orderObserver);
	public void notifyObservers();
}
