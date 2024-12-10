package com.car.service;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.jfree.chart.JFreeChart;
import org.jfree.chart.encoders.EncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.car.entity.CarRegister;
import com.car.entity.RentalCar;
import com.car.entity.RentalCarLog;
import com.car.entity.User;
import com.car.repository.CarRegisterRepository;
import com.car.repository.CarRentalRepository;
import com.car.repository.RentalCarLogRepository;
import com.car.repository.UserRepository;
import com.car.request.AddReturnCarRequest;
import com.car.request.CarRegisterRequest;
import com.car.request.RentalCarRequest;
import com.car.response.DashboardCountResponse;
import com.car.response.DeletedUserResponse;
import com.car.response.DisplayCarBySearchFinalResponse;
import com.car.response.DisplayCarBySearchResponse;
import com.car.response.DisplayModelResponse;
import com.car.response.ReturnCarResponse;
import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class CarService {

	@Autowired
	private CarRegisterRepository carRegisterRepository;
	
	@Autowired
	private CarRentalRepository carRentalRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RentalCarLogRepository rentalCarLogRepository;
	
	
	//save car to the database
	
	
	private static String getMonthChar(int month) {
		
		String[] monthChars = {"J","F","M","A","M","J","J","A","S","O","N","D"};
		
		return monthChars[month-1];
	}
	
	
	
	public List<CarRegister> registerCar(List<CarRegisterRequest> request) {
		
		//get the current count of registered car
		
		long count = carRegisterRepository.count();
		
		List<CarRegister> carList = new ArrayList<>();
		for(CarRegisterRequest req : request)
		{
			CarRegister register;
			
			if(req.getId()!=null)
			{
				Optional<CarRegister> cars = carRegisterRepository.findById(req.getId());
				if(cars.isPresent()) {
					register = cars.get();
					 logChanges(register, req);
					register.setMake(req.getMake());
					register.setModel(req.getModel());
					register.setAvailable(req.getAvailable());
					register.setColor(req.getColor());
					register.setDescription(req.getDescription());
					register.setPrice(req.getPrice());
					register.setTransmission(req.getTransmission());
					register.setModelYear(req.getModelYear());
					register.setType(req.getType());
					}
				else
				{
					throw new IllegalArgumentException("no id present with:"+req.getId());
				}
		
			}
			else
			{
				register = new CarRegister();
				register.setMake(req.getMake());
				register.setModel(req.getModel());
				register.setAvailable(req.getAvailable());
				register.setColor(req.getColor());
				register.setDescription(req.getDescription());
				register.setPrice(req.getPrice());
				register.setTransmission(req.getTransmission());
				register.setModelYear(req.getModelYear());
				register.setType(req.getType());
				
				
				LocalDate date = LocalDate.now();
				
				String year = String.valueOf(date.getYear()).substring(2);
				
				String monthChar = getMonthChar(date.getMonthValue());				
				String identificationNumber  = String.format("%04d", count+1);
				
				String uniqueNumber = "C" + monthChar + year +identificationNumber;
				register.setIdentificationNumber(uniqueNumber);
				
			}
			
			carList.add(register);
			count++; 
		}
		
		return carRegisterRepository.saveAll(carList);
	}
	
	private void logChanges(CarRegister registerCar, CarRegisterRequest request)
	{
		if(!registerCar.getMake().equals(request.getMake()))
				{
			createLogs("make",registerCar.getMake(),request.getMake());
				}
		if(!registerCar.getModel().equals(request.getModel()))
				{
			createLogs("model",registerCar.getModel(),request.getModel());
				}
		if(!registerCar.getAvailable().equals(request.getAvailable()))
		{
			createLogs("available",registerCar.getAvailable().toString(),request.getAvailable().toString());
		}
		
	}
	
	private void createLogs(String fieldName, String oldValue, String newValue) {
		RentalCarLog logEntry = new RentalCarLog();
		logEntry.setEntityName("CarRegister");
		logEntry.setFieldName(fieldName);
		logEntry.setOldValue(oldValue);
		logEntry.setNewValue(newValue);
		rentalCarLogRepository.save(logEntry);
	}
	
	
	// list the car unique numbers
	
	public List<String> uniqueNumberList()
	{
	List<String> uniqueList = new ArrayList<>();
	
	List<String> uniqueCarList = carRegisterRepository.findAllUniqueIdentificationNumbers();
	
		if(uniqueCarList!=null)
		{
			for(String uniqueCar : uniqueCarList)
			{
				uniqueList.add(uniqueCar);
			}
		}
		
		return uniqueList;
		
	}
	
	// Rent the car
	
//	public Optional<RentalCar> carOnRentService(RentalCarRequest request){
//		RentalCar carRent = null;
//		
//		if(request.getId()==null)
//		{
//			carRent = new RentalCar();
//			carRent = new RentalCar();
//			carRent.setCarId(request.getCarId());
//			carRent.setCustId(request.getCustId());
//			carRent.setCustName(request.getCustName());
//			carRent.setRentalFees(request.getRentalFees());
//			
//			carRent.setDate(request.getDate());
//			carRent.setDueDate(request.getDueDate());
//		}
//		else
//		{
//
//			Optional<RentalCar> carRents = carRentalRepository.findById(request.getId());
//		
//			if(carRents.isPresent()) {
//				carRent = carRents.get();
//				carRent.setCarId(request.getCarId());
//				carRent.setCustId(request.getCustId());
//				carRent.setCustName(request.getCustName());
//				carRent.setRentalFees(request.getRentalFees());
//				
//				carRent.setDate(request.getDate());
//				carRent.setDueDate(request.getDueDate());
//			}
//			
//		}
//			RentalCar savedRentCar = carRentalRepository.save(carRent);	
//	
//			return Optional.ofNullable(savedRentCar);
//	}
	
	
	
	public Optional<RentalCar> carOnRentService(RentalCarRequest request) {
	    RentalCar carRent;

	    if (request.getId() == null) {
	        carRent = new RentalCar();
	    } else {
	        Optional<RentalCar> carRents = carRentalRepository.findById(request.getId());
	        if (carRents.isPresent()) {
	            carRent = carRents.get();
	            logChanges(carRent, request);
	        } else {
	            return Optional.empty(); // If not found, return empty Optional
	        }
	    }

	    // Set fields regardless of whether it's a new rental or an update
	    carRent.setCarId(request.getCarId());
	    carRent.setCustId(request.getCustId());
	    carRent.setCustName(request.getCustName());
	    carRent.setRentalFees(request.getRentalFees());
	    carRent.setDate(request.getDate());
	    carRent.setDueDate(request.getDueDate());

	    // Save the rental car
	    RentalCar savedRentCar = carRentalRepository.save(carRent);
	    return Optional.of(savedRentCar);
	}

	private void logChanges(RentalCar existingCar, RentalCarRequest request) {
	    if (!existingCar.getCarId().equals(request.getCarId())) {
	        createLog("carId", existingCar.getCarId(), request.getCarId());
	    }
	    if (!existingCar.getCustId().equals(request.getCustId())) {
	        createLog("custId", existingCar.getCustId(), request.getCustId());
	    }
	    if (!existingCar.getCustName().equals(request.getCustName())) {
	        createLog("custName", existingCar.getCustName(), request.getCustName());
	    }
	    if (!existingCar.getRentalFees().equals(request.getRentalFees())) {
	        createLog("rentalFees", existingCar.getRentalFees().toString(), request.getRentalFees().toString());
	    }
	    if (!existingCar.getDate().equals(request.getDate())) {
	        createLog("date", existingCar.getDate().toString(), request.getDate().toString());
	    }
	    if (!existingCar.getDueDate().equals(request.getDueDate())) {
	        createLog("dueDate", existingCar.getDueDate().toString(), request.getDueDate().toString());
	    }
	}

	private void createLog(String fieldName, String oldValue, String newValue) {
	    RentalCarLog logEntry = new RentalCarLog();
	    logEntry.setEntityName("RentalCar");
	    logEntry.setFieldName(fieldName);
	    logEntry.setOldValue(oldValue);
	    logEntry.setNewValue(newValue);

	    // Save the log entry to the repository
	    rentalCarLogRepository.save(logEntry);
	}

	
	
	// Return the car
	
	public List<ReturnCarResponse> returnTheCar(AddReturnCarRequest request) {
		
		List<ReturnCarResponse> carResponse = new ArrayList<>();
		
		Optional<RentalCar> carRental = carRentalRepository.findByCarId(request.getCarId());
		
		if(carRental.isPresent())
		{
			RentalCar carRentOn  = carRental.get();
			
			ReturnCarResponse response = new ReturnCarResponse();
			response.setCarId(carRentOn.getCarId());
			response.setCustId(carRentOn.getCustId());
			response.setDate(carRentOn.getDate());
			response.setDueDate(carRentOn.getDueDate());
			LocalDate dueDate = response.getDueDate();
			LocalDate currentDate = LocalDate.now();
			
			// Calculate the difference in days
			
			long daysElapsed  = ChronoUnit.DAYS.between(currentDate, dueDate);
			response.setDaysElapsed((int)Math.abs(daysElapsed));
			carResponse.add(response);	
			
			long finecalculate = Math.abs(daysElapsed) * 100;
			response.setFine(finecalculate);
			
			carRentOn.setDaysElapsed((int)Math.abs(daysElapsed));
			System.out.println(carRentOn.getDaysElapsed());
			carRentOn.setFine(finecalculate);
			carRentalRepository.save(carRentOn);
		}
		return carResponse;

	}
	
	// Return the car custId
	
		public List<ReturnCarResponse> returnTheCars(AddReturnCarRequest request) {
			
			List<ReturnCarResponse> carResponse = new ArrayList<>();
			
			Optional<RentalCar> carRental = carRentalRepository.findByCustId(request.getCustId());
			
			if(carRental.isPresent())
			{
				RentalCar carRentOn  = carRental.get();
				
				ReturnCarResponse response = new ReturnCarResponse();
				response.setCarId(carRentOn.getCarId());
				response.setCustId(carRentOn.getCustId());
				response.setDate(carRentOn.getDate());
				response.setDueDate(carRentOn.getDueDate());
				LocalDate dueDate = response.getDueDate();
				LocalDate currentDate = LocalDate.now();
				
				// Calculate the difference in days
				
				long daysElapsed  = ChronoUnit.DAYS.between(currentDate, dueDate);
				response.setDaysElapsed((int)Math.abs(daysElapsed));
				
				long finecalculate = Math.abs(daysElapsed) * 100;
				response.setFine(finecalculate);
				
				carResponse.add(response);	
				
				carRentOn.setDaysElapsed((int)Math.abs(daysElapsed));
				System.out.println(carRentOn.getDaysElapsed());
				carRentOn.setFine(finecalculate);
				carRentalRepository.save(carRentOn);
				
			}
			return carResponse;

		}

	
		
		//Display dashboard count
		
		public List<DashboardCountResponse> displayDashboardCount() {
			List<DashboardCountResponse> finalResponse = new ArrayList<>();
			  List<String> makerList = carRentalRepository.findByMake();
			  List<String> modelList = carRentalRepository.findByModel();
			  List<String> customerCount = userRepository.findByCustId();
			  if(makerList!=null && modelList!=null && customerCount!=null) {
				DashboardCountResponse response = new DashboardCountResponse();
				int makerCount = makerList.size();
				response.setMakerCount(makerCount);
				int modelCount = modelList.size();
				response.setModelCount(modelCount);
				int customerCount1 = customerCount.size();
				response.setCustomerCount(customerCount1);
				finalResponse.add(response);
			}
			return finalResponse;
			
		}
		
		
		// Display model and customer by maker
		
		public List<DisplayModelResponse> displayModelByCar(AddReturnCarRequest request)
		{
			List<DisplayModelResponse> modelList = new ArrayList<>();
			
			List<CarRegister> register = carRegisterRepository.findByMake(request.getMake());
			if(register!=null)
			{
				for(CarRegister reg : register)
				{
					DisplayModelResponse response = new DisplayModelResponse();
					response.setModel(reg.getModel());
					
		            List<RentalCar> rentals = carRentalRepository.findByCarIds(reg.getIdentificationNumber()); // Assuming reg has a method to get carId

		            // Use a Set to count unique custIds
		            Set<String> uniqueCustIds = new HashSet<>();
		            for (RentalCar rental : rentals) {
		                uniqueCustIds.add(rental.getCustId());
		            }
		            
		            // Set the count of unique custIds in the response
		            response.setCountOfCarBuy(uniqueCustIds.size());
		           
					modelList.add(response);
				}
			}
			return modelList;
		}	
		
		
		//delete the car
		
		public List<DeletedUserResponse> deleteTheCar(List<AddReturnCarRequest> ids){
			
			List<DeletedUserResponse> response = new ArrayList<>();
			for(AddReturnCarRequest id : ids ) {
				DeletedUserResponse deleted = new DeletedUserResponse();
				if(id!=null && id.getId()!=null)
				{
					Optional<RentalCar> car = carRentalRepository.findById(id.getId());
					if(car.isPresent())
					{
						RentalCar existingCar = car.get();
						existingCar.setIsDeleted(true);
						carRentalRepository.save(existingCar);
						deleted.setIsDeleted(existingCar.getIsDeleted());
						deleted.setId(existingCar.getId());
					}
					else
					{
						deleted.setIsDeleted(false);
						deleted.setId(id.getId());
					}
				}
				else
				{
					deleted.setIsDeleted(false);
					deleted.setId(id.getId());
				}
				response.add(deleted);
			}
			
			return response;
		}
		
		
		// search car by color, type, transmission, maker
		
		public DisplayCarBySearchFinalResponse displayCarByModelTypeColorTransmission(AddReturnCarRequest request)
		{
			DisplayCarBySearchFinalResponse finalResponse = new DisplayCarBySearchFinalResponse();
			
			List<DisplayCarBySearchResponse> modelList = new ArrayList<>();
			
			List<CarRegister> register = carRegisterRepository.findByMakeAndTransmission(request.getMake(),request.getTransmission());
			if(register!=null)
			{
				for(CarRegister reg : register)
				{
					DisplayCarBySearchResponse response = new DisplayCarBySearchResponse();
					response.setModel(reg.getModel());
					response.setMake(reg.getMake());
					response.setAvailable(reg.getAvailable());
					response.setColor(reg.getColor());
					response.setDescription(reg.getDescription());
					response.setModelYear(reg.getModelYear());
					response.setPrice(reg.getPrice());
					response.setTransmission(reg.getTransmission());
					response.setType(reg.getType());
					modelList.add(response);
					System.out.println(response);
					finalResponse.setFinalResponse(modelList);
				}
			}
			return finalResponse;
			
		}	
		
		
		
		public static void generateCarReport(ByteArrayOutputStream outputStream, List<DashboardCountResponse> carRent) throws Exception {
			
			  Document document = new Document();
			  PdfWriter.getInstance(document, outputStream);
			   
			  document.open();
			  
			  document.add(new Paragraph("Dashboard count"));
			  
			 JFreeChart chart = GraphUtil.generateGraph(carRent);
			 
			 BufferedImage bufferedImage = chart.createBufferedImage(200, 100);
			 
			 ByteArrayOutputStream chartOutputStream = new ByteArrayOutputStream();
			 EncoderUtil.writeBufferedImage(bufferedImage, "png", chartOutputStream);
			  
			  Image charTImage = Image.getInstance(chartOutputStream.toByteArray());
			  
			  document.add(charTImage);
			  
			  document.close();
			  
		}
		
		
		public static void generateCarReports(ByteArrayOutputStream outputStream, List<DashboardCountResponse> carRent) throws Exception {
			
			  Document document = new Document();
			  PdfWriter.getInstance(document, outputStream);
			   
			  document.open();
			  
			  document.add(new Paragraph("Dashboard count"));
			  
			 JFreeChart chart1 = GraphUtil.generateGraph(carRent);
			 BufferedImage bufferedImage1 = chart1.createBufferedImage(400, 300);
			 ByteArrayOutputStream chartOutputStream1 = new ByteArrayOutputStream();
			 EncoderUtil.writeBufferedImage(bufferedImage1, "png", chartOutputStream1);
			  
			  Image charTImage1 = Image.getInstance(chartOutputStream1.toByteArray());
			  document.add(new Paragraph("Chart 1 : Maker, Customer, and Model Counts"));
			  document.add(charTImage1);
			  document.add(Chunk.NEWLINE);		
			  
			  JFreeChart chart2 = GraphUtil.generateGraphs(carRent);
			  BufferedImage bufferedImage2 = chart2.createBufferedImage(400, 300);
			  ByteArrayOutputStream chartOutputStream2 = new ByteArrayOutputStream();
			  EncoderUtil.writeBufferedImage(bufferedImage2, "png", chartOutputStream2);
			  
			  Image chartImage2 = Image.getInstance(chartOutputStream2.toByteArray());
			  document.add(new Paragraph("Chart 2 : Maker and Customer Count"));
			  document.add(chartImage2);
			  
			  document.close();
			  
		}
		
		
		
		
		
		
		
		public static void generateCarReports2(ByteArrayOutputStream outputStream, List<DashboardCountResponse> carRent) throws Exception {
			
			  Document document = new Document();
			  PdfWriter.getInstance(document, outputStream);
			   
			  document.open();
			  
			  document.add(new Paragraph("Dashboard count"));
			  
			 JFreeChart chart1 = GraphUtil.generateGraph(carRent);
			 BufferedImage bufferedImage1 = chart1.createBufferedImage(400, 300);
			 ByteArrayOutputStream chartOutputStream1 = new ByteArrayOutputStream();
			 EncoderUtil.writeBufferedImage(bufferedImage1, "png", chartOutputStream1);
			 Image charTImage1 = Image.getInstance(chartOutputStream1.toByteArray());
				
			  
			  JFreeChart chart2 = GraphUtil.generateGraphs(carRent);
			  BufferedImage bufferedImage2 = chart2.createBufferedImage(400, 300);
			  ByteArrayOutputStream chartOutputStream2 = new ByteArrayOutputStream();
			  EncoderUtil.writeBufferedImage(bufferedImage2, "png", chartOutputStream2);
			  Image chartImage2 = Image.getInstance(chartOutputStream2.toByteArray());

			  
			  PdfPTable table = new PdfPTable(2);
			  table.setWidthPercentage(90);
			  table.setSpacingBefore(10f);
			  
			  
			  float imageWidth = 200f;  // Adjust this value based on your needs
			    float imageHeight = 150f; // Adjust this value based on your needs
			    charTImage1.scaleToFit(imageWidth, imageHeight);
			    chartImage2.scaleToFit(imageWidth, imageHeight);
			  
			  
			  PdfPCell cell1 = new PdfPCell(charTImage1);
			  cell1.setBorder(PdfPCell.NO_BORDER);
			  cell1.setPadding(0); 
			  table.addCell(cell1);
			  
			  PdfPCell cell2 = new PdfPCell(chartImage2);
			  cell2.setBorder(PdfCell.NO_BORDER);
			  cell2.setPadding(0); 
			  table.addCell(cell2);
			  
			  
			  document.add(table);
			  
			  document.add(new Paragraph("Charts: Maker, Customer, and Model Counts        Maker and Customer Count"));
			  
			  document.close();
			  
		}
				
}