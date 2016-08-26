package me.mert1602.advancedapi.mysql;

import java.util.ArrayList;
import java.util.List;

public class TableCallback<T> {

	private final Object signal;

	private volatile TableCallbackFutureState state;
	private volatile T value;
	private volatile List<TableSuccessListener<T>> successList;
	private volatile List<TableFailListener<T>> failList;

	public TableCallback() {

		this.signal = new Object();

		this.state = TableCallbackFutureState.WAITING;
		this.successList = new ArrayList<TableSuccessListener<T>>();
		this.failList = new ArrayList<TableFailListener<T>>();

	}

	public T get() throws InterruptedException{

		while(this.state == TableCallbackFutureState.WAITING){

			synchronized (this.signal) {
				this.signal.wait();
			}

		}

		return this.value;
	}

	public T getUninterruptibly() {

		boolean interrupted = false;

		while (this.state == TableCallbackFutureState.WAITING) {

			synchronized (this.signal) {

				try {

					synchronized (this.signal) {
						this.signal.wait();
					}

				} catch (InterruptedException e) {
					interrupted = true;
				}

			}

		}

		if (interrupted) {
			Thread.currentThread().interrupt();
		}

		return this.value;
	}

	public boolean isDone(){
		return this.state != TableCallbackFutureState.WAITING;
	}

	public boolean isSucceeded(){
		return this.state != TableCallbackFutureState.SUCCEEDED;
	}

	public boolean isFailed(){
		return this.state != TableCallbackFutureState.FAILED;
	}

	public synchronized boolean set(T value){

		if(this.isDone()) return false;

		this.state = TableCallbackFutureState.SUCCEEDED;
		this.value = value;

		synchronized (this.signal) {
			this.signal.notifyAll();
		}

		for(TableSuccessListener<T> listener : this.successList){
			listener.onSuccess(this.value);
		}

		return true;
	}

	public synchronized boolean fail(){
		return this.fail(null);
	}

	public synchronized boolean fail(T value){

		if(this.isDone()) return false;

		this.state = TableCallbackFutureState.FAILED;
		this.value = value;

		synchronized (this.signal){
			this.signal.notifyAll();
		}

		for(TableFailListener<T> listener : this.failList){
			listener.onFail(this.value);
		}

		return true;
	}

	public synchronized TableCallback<T> onSuccess(TableSuccessListener<T> listener){
		this.successList.add(listener);
		return this;
	}

	public synchronized TableCallback<T> onFail(TableFailListener<T> listener){
		this.failList.add(listener);
		return this;
	}

}
