package com.apap.tutorial3.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.apap.tutorial3.model.CarModel;
import com.apap.tutorial3.service.CarService;

@Controller
public class CarController {
	
	public CarController() {
		
	}
	@Autowired
	private CarService mobilService;
	
	@RequestMapping("/car/add")
	public String add(@RequestParam(value = "id", required = true) String id,
					@RequestParam(value = "brand", required = true) String brand,
					@RequestParam(value = "type", required = true) String type,
					@RequestParam(value = "price", required = true) Long price,
					@RequestParam(value = "amount", required = true) Integer amount){
						
		CarModel car = new CarModel(id, brand, type, price, amount);
		mobilService.addCar(car);
		return "add";
						
					}

	@RequestMapping("/car/view")
	public String view(@RequestParam("id") String id, Model model) {
		CarModel archive = mobilService.getCarDetail(id);
		System.out.println(archive.getType());
		model.addAttribute("car",archive);
		return "view-car";
		
	}
	
	@RequestMapping("/car/viewall")
	public String viewall(Model model) {
		List <CarModel> archive = mobilService.getCarList();
		model.addAttribute("listCar", archive);
		return "viewall-car";
	}
	
	@RequestMapping(value = {"/car/view/{id}","/car/view/"})
	public String viewid(@PathVariable Optional<String> id, Model model) {
		String html = null;
		if(id.isPresent()) {
			CarModel archive = mobilService.getCarDetail(id.get());
			if(archive==null) {
				html = "result";
				model.addAttribute("result", "Id tidak ada");
			}
			else {
				model.addAttribute("car",archive);
				html= "view-car";
			}
		}
		else {
			model.addAttribute("result", "Tidak ada ID yang dimasukkan");
			html = "result";
		}
		return html;
	}
	
	@RequestMapping(value= {"/car/update/{id}/amount/{new_amount}", "/car/update/amount/", "/car/update/{id}/amount/", "/car/update/amount/{new_amount}/"}, method=RequestMethod.GET)
	public String updateById(@PathVariable Optional<String> id, @PathVariable (required = false)Integer new_amount,  Model model) {
		String html = "";
		if(id.isPresent()&& new_amount != null) {
			CarModel archive = mobilService.getCarDetail(id.get());
			if(archive == null) {
				model.addAttribute("result", "Id tidak ada");
				html = "result";
			}
			else {
				archive.setAmount(new_amount);
				model.addAttribute("car",archive);
				html = "view-car";
			}
		}
		else {
			model.addAttribute("result", "Tidak ada ID atau amount yang dimasukkan");
			html = "result";
		}
		return html;
	}
	
	@RequestMapping(value= {"/car/delete/{id}", "/car/delete/"}, method=RequestMethod.GET)
	public String DeleteById(@PathVariable Optional<String> id,  Model model) {
		if(id.isPresent()) {
			CarModel archiv = mobilService.getCarDetail(id.get());
			if(archiv==null) {//bila tidak ada id nya di list
				model.addAttribute("result", "Id tidak ada");
			}
			else {
				List <CarModel> archive = mobilService.getCarList();
				for(int i=0;i <archive.size();i++) {
					if(archive.get(i).getId().equals(id.get())) {
						archive.remove(i);
						model.addAttribute("result", "Data sudah dihapus");
					}
				}
			}
		}
		else {
			model.addAttribute("result", "Gagal melakukan penghapusan, masukan URL dengan benar");
		}
		return "result";
		
	}

	

	

}
