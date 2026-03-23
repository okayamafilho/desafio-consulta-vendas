package com.devsuperior.dsmeta.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.devsuperior.dsmeta.dto.SaleMinDTO;
import com.devsuperior.dsmeta.dto.SaleSumDTO;
import com.devsuperior.dsmeta.entities.Sale;
import com.devsuperior.dsmeta.repositories.SaleRepository;

@Service
public class SaleService {

	@Autowired
	private SaleRepository repository;
	
	public SaleMinDTO findById(Long id) {
		Optional<Sale> result = repository.findById(id);
		Sale entity = result.get();
		return new SaleMinDTO(entity);
	}

	public Page<SaleMinDTO> searchReport(String minDate, String maxDate, String name, Pageable pageable) {
		LocalDate[] minMaxDate  = getMinMaxDate(minDate, maxDate);
		String sellerName = getName(name);

		return repository.searchByFilters(minMaxDate[0], minMaxDate[1], sellerName, pageable);
		
	}

	public List<SaleSumDTO> searchSummary(String minDate, String maxDate, String name) {
		LocalDate[] minMaxDate  = getMinMaxDate(minDate, maxDate);
		String sellerName = getName(name);

		return repository.summaryByFilters(minMaxDate[0], minMaxDate[1], sellerName);
	}

	private LocalDate[] getMinMaxDate(String minDate, String maxDate) {
		LocalDate today = LocalDate.now();
		LocalDate min   = minDate.equals("") ? today.minusYears(1) : LocalDate.parse(minDate);
		LocalDate max   = maxDate.equals("") ? today : LocalDate.parse(maxDate);
		return new LocalDate[] {min, max};
	}

	private String getName(String name) {
		return name == null ? "" : name;
	}
	
}
