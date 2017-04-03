package jp.smaphonia.app.division;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import jp.smaphonia.domain.model.Division;
import jp.smaphonia.domain.service.division.DivisionService;

@Controller
@RequestMapping("divisions")
public class DivisionController {

	@Autowired
	DivisionService divisionService;
	
	@RequestMapping(method=RequestMethod.GET)
	String listDivisions(Model model) {
		List<Division> divisions = divisionService.findAllDivision();
		model.addAttribute("divisions", divisions);
		return "division/listDivisions";
	}
	
	@RequestMapping(method=RequestMethod.POST, params="edit")
	String edit(@RequestParam("divisionId") String divisionId, Model model) {
		Division division = divisionService.findDivision(divisionId);
		model.addAttribute("division", division);
		model.addAttribute("method", "edit");
		return "division/editDivision";
	}
	@RequestMapping(method=RequestMethod.POST, params="add")
	String add(Model model) {
		Division division = divisionService.newDivision();
		model.addAttribute("division", division);
		model.addAttribute("method", "add");
		return "division/editDivision";
	}	
	@RequestMapping(method=RequestMethod.POST, params="update")
	String update(@RequestParam("divisionId") String divisionId, @RequestParam("name") String DivisionName, Model model) {
		divisionService.update(divisionId, DivisionName);
		
		List<Division> Divisions = divisionService.findAllDivision();
		model.addAttribute("divisions", Divisions);
		return "division/listDivisions";
	}	
	@RequestMapping(method=RequestMethod.POST, params="cancel")
	String cancel(@RequestParam("divisionId") String divisionId, Model model) {
		divisionService.cancel(divisionId);
		
		List<Division> divisions = divisionService.findAllDivision();
		model.addAttribute("divisions", divisions);
		return "division/listDivisions";
	}

}
