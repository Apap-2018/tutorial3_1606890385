package com.apap.tutorial3.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.apap.tutorial3.model.CarModel;

@Service
public class CarInMemoryService implements CarService {
	private List<CarModel> archiveCar;

	public CarInMemoryService() {
		archiveCar = new ArrayList<>();
	}
	
	public void addCar(CarModel car) {
		// TODO Auto-generated method stub
		archiveCar.add(car);
	}


	public List<CarModel> getCarList() {
		// TODO Auto-generated method stub
		return archiveCar;
	}

	
	public CarModel getCarDetail(String id) {
		// TODO Auto-generated method stub
		CarModel mobil = new CarModel();
		for(CarModel car : archiveCar) {
			if(car.getId().equals(id)) {
				mobil =car;
			}
			else {
				mobil = null;
			}
		}
		return mobil;
	}
	

}
