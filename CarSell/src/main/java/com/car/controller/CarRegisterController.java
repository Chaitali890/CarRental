package com.car.controller;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.entity.CarRegister;
import com.car.entity.RentalCar;
import com.car.request.AddReturnCarRequest;
import com.car.request.CarRegisterRequest;
import com.car.request.RentalCarRequest;
import com.car.response.DashboardCountResponse;
import com.car.response.DeletedUserResponse;
import com.car.response.DisplayCarBySearchFinalResponse;
import com.car.response.DisplayModelResponse;
import com.car.response.ReturnCarResponse;
import com.car.service.CarService;

@RestController
@RequestMapping("/register")
public class CarRegisterController {

	@Autowired
	private CarService carService;
	
	@PostMapping("/addCar")
	public ResponseEntity<List<CarRegister>> addAllCar(@RequestBody List<CarRegisterRequest> request)
	{
	    System.out.println("Received request: " + request);

		List<CarRegister> register = carService.registerCar(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(register);
	}
	
	@GetMapping("/getCarListNumber")
	public ResponseEntity<List<String>> getListOfCarIdentificationNumber()
	{
		List<String> numberList = carService.uniqueNumberList();
		return ResponseEntity.ok(numberList);
	}
	
	
	@PostMapping("/rentCar")
	public ResponseEntity<Optional<RentalCar>> rentOnCar(@RequestBody RentalCarRequest request){
		
		Optional<RentalCar> carRent = carService.carOnRentService(request);
		return ResponseEntity.status(HttpStatus.CREATED).body(carRent);
	}
	
	@PostMapping("/getReturnCar")
	public ResponseEntity<List<ReturnCarResponse>> returnCarRental(@RequestBody AddReturnCarRequest request) {
	    List<ReturnCarResponse> carRentalResponses = carService.returnTheCar(request);
	    return ResponseEntity.ok(carRentalResponses);
	}
	
	@PostMapping("/getReturnCars")
	public ResponseEntity<List<ReturnCarResponse>> returnCarRentals(@RequestBody AddReturnCarRequest request) {
	    List<ReturnCarResponse> carRentalResponses = carService.returnTheCars(request);
	    return ResponseEntity.ok(carRentalResponses);
	}
	
	@GetMapping("/getMakerCount")
	public ResponseEntity<List<DashboardCountResponse>> getCountOfMaker()
	{
		List<DashboardCountResponse> countMaker = carService.displayDashboardCount();
		return ResponseEntity.ok(countMaker);
	}
	
	@PostMapping("/getmodel")
	public ResponseEntity<List<DisplayModelResponse>> displayModelByCars(@RequestBody AddReturnCarRequest request) {
	    List<DisplayModelResponse> modelResponses = carService.displayModelByCar(request);
	    return ResponseEntity.ok(modelResponses);
	}
	
	@PostMapping("/delete")
	public ResponseEntity<List<DeletedUserResponse>> deleteTheUser(@RequestBody List<AddReturnCarRequest> ids) {
	    List<DeletedUserResponse> deletedResponses = carService.deleteTheCar(ids);
	    
	    boolean anyDeleted = deletedResponses.stream().anyMatch(DeletedUserResponse::getIsDeleted);
	    
	    return anyDeleted ? ResponseEntity.ok(deletedResponses) : ResponseEntity.status(HttpStatus.NOT_FOUND).body(deletedResponses);
	}

	@PostMapping("/searchat")
	public ResponseEntity<DisplayCarBySearchFinalResponse> displayCarByModelTypeColorTransmission(@RequestBody AddReturnCarRequest request) {
	    DisplayCarBySearchFinalResponse modelResponses = carService.displayCarByModelTypeColorTransmission(request);
	    return ResponseEntity.ok(modelResponses);
	}
	
	
	@GetMapping("/generate-pdf")
	public ResponseEntity<byte[]> generatePdf(){
		try
		{
			List<DashboardCountResponse> carRent = carService.displayDashboardCount();
			
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			CarService.generateCarReports2(outputStream, carRent);
			
			
			
			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_PDF);
			headers.setContentDispositionFormData("attachment", "CarDashBoardReport.pdf");
			
		
			return ResponseEntity.ok()
					.headers(headers)
					.body(outputStream.toByteArray());
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}
	
}