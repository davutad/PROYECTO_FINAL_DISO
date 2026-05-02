package com.ubereats.observer;

import com.ubereats.Order;

public interface OrderObserver {
	public void update(Order order);
}
