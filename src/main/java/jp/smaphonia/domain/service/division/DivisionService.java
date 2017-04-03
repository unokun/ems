package jp.smaphonia.domain.service.division;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import jp.smaphonia.domain.model.Division;
import jp.smaphonia.domain.repository.division.DivisionRepository;

@Service
public class DivisionService {

	@Autowired
	DivisionRepository divisionRepository;
	
	public Division findDivision(String id) {
		return divisionRepository.findOne(id);
	}
	
	public List<Division> findAllDivision() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.ASC, "id"));
		return divisionRepository.findAll(new Sort(orders));
	}
	public Division newDivision() {
		String maxId = divisionRepository.getMaxId();
		int index = Integer.parseInt(maxId.substring("D".length())) + 1;
		String newId = "D" + String.format("%02d", index);
		Division Division = new Division();
		Division.setId(newId);
		return Division;
	}

	public void update(String id, String name) {
		Division division = divisionRepository.findOne(id);
		if (division == null) {
			division = new Division();
			division.setId(id);
		}
		division.setName(name);
		divisionRepository.save(division);
		
	}
	public void cancel(String id) {
		Division division = divisionRepository.findOne(id);
		divisionRepository.delete(division);
	}

}
