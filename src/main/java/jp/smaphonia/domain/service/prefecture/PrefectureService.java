package jp.smaphonia.domain.service.prefecture;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import jp.smaphonia.domain.model.Prefecture;
import jp.smaphonia.domain.repository.prefecture.PrefectureRepository;

@Service
public class PrefectureService {

	@Autowired
	PrefectureRepository prefectureRepository;
	
	public List<Prefecture> findAllPrefecture() {
		List<Order> orders = new ArrayList<>();
		orders.add(new Order(Direction.ASC, "id"));
		return prefectureRepository.findAll(new Sort(orders));
	}

}
