package com.ubereats.Observer;

public interface OrderObservable {
	public void addObserver(OrderObserver orderObserver);
	public void deleteObserver(OrderObserver orderObserver);
	public void notifyObservers();
}
