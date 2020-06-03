package example.training.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import example.training.model.employee.Employee;
import example.training.model.employee.EmployeeList;
import example.training.model.employee.criteria.EmployeeListCriteria;
import example.training.model.employee.criteria.EmployeeListCriteriaFactory;
import example.training.service.EmployeeService;

@Controller
@RequestMapping("employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeSevice;

	@Autowired
	private EmployeeListCriteriaFactory employeeListCriteriaFactory;

	@GetMapping
	public String employees(Model model) {
		EmployeeListCriteria criteria = employeeListCriteriaFactory.create();
		EmployeeList employeeList = employeeSevice.listOf(criteria);
		model.addAttribute("criteria", criteria);
		model.addAttribute("employeelist", employeeList);
		return "employee/employee-list";
	}

	@PostMapping("search")
	public String employeesSearch(@ModelAttribute EmployeeListCriteria criteria,
			Model model) {
		EmployeeList employeeList = employeeSevice.listOf(criteria);
		model.addAttribute("criteria", criteria);
		model.addAttribute("employeelist", employeeList);
		return "employee/employee-list";
	}

	@GetMapping("{employeeId:\\d+}")
	public String employee(
			@PathVariable Integer employeeId,
			Model model) {
		Employee employee = employeeSevice.findById(employeeId);
		model.addAttribute("employee", employee);
		return "employee/employee";
	}
}
